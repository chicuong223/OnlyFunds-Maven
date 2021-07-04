/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author ASUS GAMING
 */
public class UploadFile {

    public String uploadFile(HttpServletRequest request, Part filePart) throws IOException, ServletException {
        String fileName = "";
        try
        {
//            Part filePart = request.getPart("avatar");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + "images" + File.separator + "avatars" + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try
            {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1)
                {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                fileName = "";
            } finally
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
        } catch (Exception e)
        {
            fileName = "";
        }
        return fileName;
    }

    public String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";"))
        {
            if (content.trim().startsWith("filename"))
            {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public String postAttachmentUpload(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try
        {
            Part filePart = request.getPart("attachment");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + "post_file" + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try
            {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1)
                {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
                fileName = "";
            } finally
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
        } catch (IOException | ServletException e)
        {
            fileName = "";
        }
        return fileName;
    }

    public void deleteFile(HttpServletRequest request, String filename) {
        try
        {
            String filepath = request.getServletContext().getRealPath("") + File.separator + "post_file" + File.separator + filename;
            File file = new File(filepath);
            if (!file.exists())
                throw new Exception("File not found");
             else
                file.delete();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
