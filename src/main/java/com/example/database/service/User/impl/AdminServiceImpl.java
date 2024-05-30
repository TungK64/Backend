package com.example.database.service.User.impl;

import com.example.database.dto.AdminDTO;
import com.example.database.dto.LoginDTO;
import com.example.database.entity.Admin;
import com.example.database.repository.AdminRepository;
import com.example.database.service.User.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

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
}
