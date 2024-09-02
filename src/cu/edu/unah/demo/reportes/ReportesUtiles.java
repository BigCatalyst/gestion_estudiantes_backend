/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.reportes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Rene
 */
public class ReportesUtiles {

    public static ResponseEntity<byte[]> generarReporte(String data[][]) throws DocumentException, FileNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        File f=new File("tablas.pdf");
//        System.out.println(f.getAbsolutePath());
//        System.out.println(f.getParentFile());
        Document document = new Document();
        PdfWriter.getInstance(document,byteArrayOutputStream);//new FileOutputStream(f));// byteArrayOutputStream);
        document.open();
        
        int cantidad_de_columnas=data[0].length;
        
        // Crear la tabla con el número de columnas de la matriz
        PdfPTable table = new PdfPTable(cantidad_de_columnas);

        float[] columnWidths = new float[cantidad_de_columnas]; // Cambia los tamaños según tus necesidades
        for (int i = 0; i < data[1].length; i++) {
            String ancho_str=data[1][i];
            columnWidths[i]=Float.parseFloat(ancho_str);
        }
        
        
        table.setWidths(columnWidths);

        // Fuente para los títulos
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        // Colores para el fondo del título
        BaseColor bgColor = new BaseColor(200, 200, 200); // Color gris claro

        // Añadir los encabezados
        for (String title : data[0]) {
            PdfPCell cell = new PdfPCell(new Paragraph(title, titleFont));
            cell.setBackgroundColor(bgColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Estilo para el contenido de la tabla
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 11);

        // Añadir el resto de los datos
        for (int i = 2; i < data.length; i++) {
            for (String value : data[i]) {
                PdfPCell cell = new PdfPCell(new Paragraph(value, contentFont));
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
                
//                table.addCell(new Paragraph(value, contentFont));
            }
        }

        // Agregamos la tabla al documento            
        document.add(table);

        document.close();

//        // Crear el documento PDF
//        PdfWriter pdfWriter = new PdfWriter// new PdfWriter(byteArrayOutputStream);
//        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//        Document document = new Document(pdfDocument);
//
//        // Agregar contenido al documento
//        document.add(new Paragraph("¡Hola, Mundo! Este es un PDF generado con iText."));
        // Cerrar el documento
        document.close();

        // Obtener los bytes del PDF
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();

        // Preparar la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=document.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
