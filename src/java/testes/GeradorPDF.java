/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.TableWrapper;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "geradorPDF")
@ViewScoped
public class GeradorPDF {

    public void download() {

        downloadFile("teste.pdf", "/", "pdf", FacesContext.getCurrentInstance());
        // deve ser passado o nome do arquivo+extensão  teste.txt     


    }

    public static synchronized void downloadFile(String filename, String fileLocation, String mimeType,
            FacesContext facesContext) {

        ExternalContext context = facesContext.getExternalContext(); // Context    
        String path = fileLocation; // Localizacao do arquivo    
        String fullFileName = path + filename;
        File file = new File(fullFileName); // LINHA ALTERADA    

        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\""); //aki eu seto o header e o nome q vai aparecer na hr do donwload    
        response.setContentLength((int) file.length()); // O tamanho do arquivo    
        response.setContentType(mimeType); // e obviamente o tipo    

        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            byte[] buf = new byte[(int) file.length()];
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            in.close();
            out.flush();
            out.close();
            facesContext.responseComplete();
        } catch (IOException ex) {
            System.out.println("Error in downloadFile: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
