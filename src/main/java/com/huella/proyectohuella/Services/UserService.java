package com.huella.proyectohuella.Services;

import com.huella.proyectohuella.Models.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User create(User user);
    User findById(Long id);
    User update(User user);
    User updatePassword(Long id, String newPassword);
}