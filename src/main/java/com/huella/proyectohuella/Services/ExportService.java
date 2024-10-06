package com.huella.proyectohuella.Services;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    void exportarTurnos(HttpServletResponse response) throws IOException;
}
