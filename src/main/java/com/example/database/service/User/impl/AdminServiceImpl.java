package com.example.database.service.User.impl;

import com.example.database.dto.AdminDTO;
import com.example.database.dto.LoginDTO;
import com.example.database.entity.Admin;
import com.example.database.entity.Project;
import com.example.database.entity.User;
import com.example.database.repository.AdminRepository;
import com.example.database.repository.ProjectRepository;
import com.example.database.repository.UserRepository;
import com.example.database.service.User.AdminService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Internal;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;


    @Override
    public Admin adminLogin(LoginDTO loginDTO) {
        Admin admin = adminRepository.findByEmail(loginDTO.getEmail());
        if(admin == null) {return null;}
        else {
            if(admin.getPassword().equals(loginDTO.getPassword())) {
                return admin;
            }
            else return null;
        }
    }

    @Override
    public Admin createAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setUsername(adminDTO.getUserName());
        admin.setRole("Admin");
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public void addExcelUser(MultipartFile file, String role) {
        try (InputStream in = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(in);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();

                if (rowIterator.hasNext()) {
                    rowIterator.next(); // Skip header row
                }
                int m = 0;
                int n = 0;

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (isRowEmpty(row, 4)) {
                        continue;
                    }

                    boolean userExists = false;
                    Cell emailCell = row.getCell(0);
                    Cell userNumberCell = row.getCell(2);

                    String userNumber = getCellStringValue(userNumberCell);
                    String email = getCellStringValue(emailCell);

                    for (User u : userRepository.findAll()) {
                        if (u.getUserNumber().equals(userNumber) || u.getEmail().equals(email)) {
                            n++;
                            userExists = true;
                            break;
                        }
                    }
                    if (userExists) {
                        m++;
                        continue;
                    }

                    User user = new User();
                    user.setRole(role);
                    user.setEmail(email);
                    user.setUserNumber(userNumber);
                    user.setPassword(userNumber);

                    Cell userNameCell = row.getCell(1);
                    if(userNameCell != null) {
                        user.setUserName(userNameCell.getStringCellValue());
                    }

                    Cell phoneNumberCell = row.getCell(3);
                    if(phoneNumberCell != null) {
                        user.setPhoneNumber(phoneNumberCell.getStringCellValue());
                    }

                    Cell dateOfBirthCell = row.getCell(4);
                    if (dateOfBirthCell != null) {
                        LocalDate dateOfBirth = getCellLocalDateValue(dateOfBirthCell);
                        if (dateOfBirth != null) {
                            user.setDateOfBirth(dateOfBirth);
                        }
                    }

                    Cell projectCell = row.getCell(5);
                    if (projectCell != null) {
                        String projectString = getCellStringValue(projectCell);
                        if (projectString != null) {
                            List<Integer> projectArray = parseProjectList(projectString);
                            user.setProjectList(projectArray);
                        }
                    }
                    userRepository.save(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    private LocalDate getCellLocalDateValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                String date = cell.getStringCellValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(date, formatter);
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate();
                }
            default:
                return null;
        }
    }

    private List<Integer> parseProjectList(String projectString) {
        List<Integer> projectList = new ArrayList<>();
        String[] stringArray = projectString.split(",");
        for (String s : stringArray) {
            try {
                projectList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid project number: " + s);
            }
        }
        return projectList;
    }

    private boolean isRowEmpty(Row row, int lastCellNum) {
        for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void addExcelClass(MultipartFile file) {
        try(InputStream in = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(in);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (isRowEmpty(row, 1)) {
                        continue;
                    }

                    Cell projectNameCell = row.getCell(0);
                    Cell classCodeCell = row.getCell(1);
                    String projectName = getCellStringValue(projectNameCell);
                    String classCode = getCellStringValue(classCodeCell);

                    try{
                        int classCodeInt = Integer.parseInt(classCode);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    Project project1 = projectRepository.findByProjectNameAndClassCode(projectName, Integer.parseInt(classCode));
                    if(project1 != null) {
                        continue;
                    }
                    Project project = new Project();
                    List<String> stuNumber = new ArrayList<>();
                    project.setProjectName(projectName);
                    project.setClassCode(Integer.parseInt(classCode));

                    User lecture = userRepository.findByRoleAndProjectListContaining("Lecture", Integer.parseInt(classCode));

                    if(lecture != null) {
                        project.setLectureNumber(lecture.getUserNumber());
                    }


                    List<User> students = userRepository.findAllByRoleAndProjectListContains("Student", Integer.parseInt(classCode));
                    if(!students.isEmpty()) {
                        for(User student : students) {
                            stuNumber.add(student.getUserNumber());
                        }
                        project.setStudentList(stuNumber);
                    }
                    projectRepository.save(project);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
