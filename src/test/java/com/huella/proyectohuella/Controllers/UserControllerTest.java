package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.User;
import com.huella.proyectohuella.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar el view resolver para las vistas
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        // Inicializamos MockMvc con el controlador que vamos a probar
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testMostrarFormularioCrearUsuario() throws Exception {
        mockMvc.perform(get("/user/crear"))
                .andExpect(status().isOk())
                .andExpect(view().name("inicio"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("accion"));
    }

    @Test
    public void testGuardarUsuario() throws Exception {
        mockMvc.perform(post("/user/guardar")
                .flashAttr("user", new User()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/EstadoUsuario"));

        // Verificar que el servicio se haya llamado
        verify(userService, times(1)).create(any(User.class));
    }

    @Test
    public void testListarUsuarios() throws Exception {
        List<User> usuarios = Arrays.asList(new User(), new User());
        when(userService.findAll()).thenReturn(usuarios);

        mockMvc.perform(get("/user/EstadoUsuario"))
                .andExpect(status().isOk())
                .andExpect(view().name("EstadoUsuario"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", usuarios));

        // Verificar que el servicio se haya llamado
        verify(userService, times(1)).findAll();
    }

    @Test
    public void testActualizarUsuario() throws Exception {
        User usuarioExistente = new User();
        when(userService.findById(1L)).thenReturn(usuarioExistente);

        mockMvc.perform(post("/user/actualizar/1")
                .flashAttr("user", new User()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/EstadoUsuario"));

        // Verificar que el servicio de actualización fue llamado
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    public void testCambiarEstadoUsuario() throws Exception {
        User usuario = new User();
        usuario.setActivo(true);
        when(userService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(post("/user/cambiarEstado/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/EstadoUsuario"));

        // Verificar que el estado del usuario fue cambiado y que se llamó al servicio de actualización
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    public void testCambiarContrasena() throws Exception {
        User usuario = new User();
        when(userService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(post("/user/cambiarContrasena/1")
                .param("nuevaContrasena", "nuevaPassword123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/EstadoUsuario"));

        // Verificar que la contraseña del usuario fue cambiada
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).update(any(User.class));
    }
}