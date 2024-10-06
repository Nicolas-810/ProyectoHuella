package com.huella.proyectohuella.Services;

import com.huella.proyectohuella.Models.User;
import java.util.Optional;

public interface LoginService {
    boolean authenticate(String username, String password);
    Optional<User> findByUsername(String username);
}
