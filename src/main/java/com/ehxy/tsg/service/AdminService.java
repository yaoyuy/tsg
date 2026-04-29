package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Admin;
import com.ehxy.tsg.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin selectById(Long id) {
        return adminMapper.selectById(id);
    }

    public Admin selectByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    public int insert(Admin admin) {
        return adminMapper.insert(admin);
    }

    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }

    public Admin login(String username, String password) {
        Admin admin = adminMapper.selectByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
