/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.reportes;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Rene
 */
public class ReportesUtiles {

//    public static ByteArrayOutputStream getOutputStreamFromReport(List list, Map map, String filename) throws Exception {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
//
//        URL u = ReportesUtiles.class.getResource("jrxml"
//                + File.separator + filename + ".jasper");
//
//        JasperPrint jp = JasperFillManager.fillReport(u.getFile(), map, dataSource);
//
//        JasperExportManager.exportReportToPdfStream(jp, os);
//        os.flush();
//        os.close();
//        return os;
//    }

    public static ByteArrayOutputStream getOutputStreamFromReport(Map map, String filename) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        URL u = ReportesUtiles.class.getResource("jrxml"
                + File.separator + filename + ".jasper");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tesis", "postgres", "pedro");
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        JasperPrint jp = JasperFillManager.fillReport(u.getFile(), map, conn);

        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();
        return os;
    }

//    public static StreamedContent getStreamContentFromOutputStream(ByteArrayOutputStream os, String contentType, String nameFile) throws Exception {
//        StreamedContent file = null;
//        InputStream is = new ByteArrayInputStream(os.toByteArray());
//        file = new DefaultStreamedContent(is, contentType, nameFile);
//        return file;
//    }
//
//    public static StreamedContent getStreamContentReport(List list, Map map, String pathJasper, String nameFilePdf) throws Exception {
//        StreamedContent pdf = null;
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
//
//        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, dataSource);
//
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        JasperExportManager.exportReportToPdfStream(jp, os);
//        os.flush();
//        os.close();
//
//        InputStream is = new ByteArrayInputStream(os.toByteArray());
//        pdf = new DefaultStreamedContent(is, "application/pdf", nameFilePdf);
//        return pdf;
//    }
//
//    public void generar(String filename, Map<String, Object> map) {
//        try {
//
//            ByteArrayOutputStream outputStream = getOutputStreamFromReport(map, filename);
//            StreamedContent media = getStreamContentFromOutputStream(outputStream, "application/pdf", reportName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
