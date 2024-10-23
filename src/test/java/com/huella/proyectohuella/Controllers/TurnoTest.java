package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;  // Importar LocalDateTime

import static org.junit.jupiter.api.Assertions.*;

class TurnoTests {
    private Turno turno;

    @BeforeEach
    void setUp() {
        turno = new Turno();
        turno.setTurno("Turno1");
        turno.setFechaAsignacion(LocalDateTime.now());  // Usar LocalDateTime
    }

    @Test
    void testTurnoCreation() {
        assertNotNull(turno);
        assertEquals("Turno1", turno.getTurno());
        assertNotNull(turno.getFechaAsignacion());
    }
}