package com.huella.proyectohuella.Services.Impl;

import com.huella.proyectohuella.Models.Turno;
import com.huella.proyectohuella.Repositories.TurnoRepository;
import com.huella.proyectohuella.Services.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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
        headerRow.createCell(0).setCellValue("Empleado");
        headerRow.createCell(1).setCellValue("Turno");
        headerRow.createCell(2).setCellValue("Lunes");
        headerRow.createCell(3).setCellValue("Martes");
        headerRow.createCell(4).setCellValue("Miércoles");
        headerRow.createCell(5).setCellValue("Jueves");
        headerRow.createCell(6).setCellValue("Viernes");
        headerRow.createCell(7).setCellValue("Sábado");
        headerRow.createCell(8).setCellValue("Domingo");

        // Rellenar las filas con los datos de los turnos
        int rowNum = 1;
        for (Turno turno : turnos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(turno.getEmployee().getNombreCompleto()); // Nombre del empleado
            row.createCell(1).setCellValue(turno.getTurno()); // Turno asignado

            // Obtener la fecha de asignación
            LocalDateTime fechaAsignacion = turno.getFechaAsignacion();
            String lunes = "Sin asignar";
            String martes = "Sin asignar";
            String miercoles = "Sin asignar";
            String jueves = "Sin asignar";
            String viernes = "Sin asignar";
            String sabado = "Sin asignar";
            String domingo = "Sin asignar";

            // Asignar la hora correspondiente al día de la semana
            if (fechaAsignacion != null) {
                DayOfWeek dayOfWeek = fechaAsignacion.getDayOfWeek();
                String horaAsignada = fechaAsignacion.toLocalTime().toString();

                switch (dayOfWeek) {
                    case MONDAY:
                        lunes = horaAsignada;
                        break;
                    case TUESDAY:
                        martes = horaAsignada;
                        break;
                    case WEDNESDAY:
                        miercoles = horaAsignada;
                        break;
                    case THURSDAY:
                        jueves = horaAsignada;
                        break;
                    case FRIDAY:
                        viernes = horaAsignada;
                        break;
                    case SATURDAY:
                        sabado = horaAsignada;
                        break;
                    case SUNDAY:
                        domingo = horaAsignada;
                        break;
                }
            }

            // Llenar las celdas con las horas asignadas
            row.createCell(2).setCellValue(lunes);
            row.createCell(3).setCellValue(martes);
            row.createCell(4).setCellValue(miercoles);
            row.createCell(5).setCellValue(jueves);
            row.createCell(6).setCellValue(viernes);
            row.createCell(7).setCellValue(sabado);
            row.createCell(8).setCellValue(domingo);
        }

        // Escribir el archivo Excel a la respuesta
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}