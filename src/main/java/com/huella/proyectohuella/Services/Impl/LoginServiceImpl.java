package com.huella.proyectohuella.Services.Impl;

import com.huella.proyectohuella.Models.User;
import com.huella.proyectohuella.Repositories.LoginRepository;
import com.huella.proyectohuella.Services.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Optional<User> user = loginRepository.findByUsernameAndPassword(username, password);
        return user.isPresent() && user.get().isActivo();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }
}
