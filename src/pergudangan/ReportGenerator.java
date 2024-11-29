/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pergudangan;

import net.sf.jasperreports.engine.*;
/**
 *
 * @author Fedro Maulana
 */
public class ReportGenerator {
    public static void compileReport() {
        try {
            String sourceFile = "C:/Users/Fedro Maulana/OneDrive/Documents/NetBeansProjects/pergudangan/src/pergudangan/BarangReport.jrxml";
            String outputFile = "C:/Users/Fedro Maulana/OneDrive/Documents/NetBeansProjects/pergudangan/src/pergudangan/BarangReport.jasper";
            // Lokasi file .jrxml
//            String sourceFile = "path/to/BarangReport.jrxml"; 
            // Lokasi hasil file .jasper
//            String outputFile = "path/to/BarangReport.jasper"; 

            // Kompilasi file .jrxml menjadi .jasper
            JasperCompileManager.compileReportToFile(sourceFile, outputFile);

            System.out.println("File berhasil dikompilasi: " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ReportGenerator.compileReport();
    }
}
