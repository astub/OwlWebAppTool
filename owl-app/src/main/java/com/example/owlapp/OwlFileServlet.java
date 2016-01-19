package com.example.owlapp;

import org.semanticweb.owlapi.model.OWLAxiom;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

@WebServlet(
        name = "OwlFileServlet",
        urlPatterns = {"/owl"}
)
@MultipartConfig()
public class OwlFileServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        
        if (action != null) {
        	uploadOwlFile(req, resp);
        } else {
        	forwardListOwls(req, resp);
        }
        
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
            uploadOwlFile(req, resp);  
    }
    
    
    private void forwardListOwls(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nextJSP = "/jsp/list-owlfiles.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        //req.setAttribute("employeeList", employeeList); Use this to set data
        dispatcher.forward(req, resp);
    }
    
    private void uploadOwlFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Part filePart = req.getPart("file-upload");
		String fileName = getSubmittedFileName(filePart);
		InputStream fileContent = filePart.getInputStream();
		System.out.println(fileName);
    }
    
    private static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
    	return null;
	}
    

}