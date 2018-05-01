package com.upmc.twister.servlets.user;

import com.upmc.twister.services.Response;
import com.upmc.twister.services.UserServices;
import netscape.javascript.JSObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Servlet qui permet de creer un utilisateur
 *
 * @author march
 */
public class UploadPictureServlet extends HttpServlet {
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (!isMultipart) {
            out.println(Response.BAD_REQUEST.parse());
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            String key = null;
            FileItem fileItem = null;
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {

                    String fileName = fi.getName();
                    String uuid = UUID.randomUUID().toString();
                    String extention = fileName.substring(fileName.lastIndexOf("."));
                    System.out.println(extention);
                    fileName = uuid + extention;
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fileItem =fi;
                } else {
                    key = fi.getString();
                }
            }

            JSONObject json = UserServices.uploadPic(fileItem,file,key);
            out.println(json);
        } catch (Exception ex) {
            out.println(Response.INTERNAL_SERVER_ERROR.parse());
        }
    }

    public void init() {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload");
    }
}

