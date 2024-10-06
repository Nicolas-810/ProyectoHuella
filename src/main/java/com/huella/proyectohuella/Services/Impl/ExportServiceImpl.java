package com.huella.proyectohuella.Services.Impl;

import com.huella.proyectohuella.Models.Turno;
import com.huella.proyectohuella.Repositories.TurnoRepository;
import com.huella.proyectohuella.Services.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    private final TurnoRepository turnoRepository;

    public ExportServiceImpl(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Override
    public void exportarTurnos(HttpServletResponse response) throws IOException {
        // Establecer el tipo de contenido para la respuesta
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=turnos_asignados.xlsx";
        response.setHeader(headerKey, headerValue);

        // Obtener los turnos de la base de datos
        List<Turno> turnos = turnoRepository.findAll();

        // Crear un libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Turnos Asignados");

        // Crear el encabezado de la tabla
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Empleado");
        headerRow.createCell(2).setCellValue("Turno");
        headerRow.createCell(3).setCellValue("Fecha Asignación");

        // Rellenar las filas con los datos de los turnos
        int rowNum = 1;
        for (Turno turno : turnos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(turno.getId());
            row.createCell(1).setCellValue(turno.getEmployee().getNombreCompleto());  // Suponiendo que el modelo Employee tiene un campo nombre
            row.createCell(2).setCellValue(turno.getTurno());
            row.createCell(3).setCellValue(turno.getFechaAsignacion().toString());
        }

        // Escribir el archivo Excel a la respuesta
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
