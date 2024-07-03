package com.example.database.service.User;

import com.example.database.dto.AdminDTO;
import com.example.database.dto.LoginDTO;
import com.example.database.entity.Admin;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AdminService {

    Admin adminLogin(LoginDTO loginDTO);

    Admin createAdmin(AdminDTO addAdminDTO);

    void addExcelUser(MultipartFile file, String role);

    void addExcelClass(MultipartFile file);
}
