package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.chat.db.DatabaseConnector;

public class UpdateDp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//for error file
		String servletname = this.getClass().getName();
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		DatabaseConnector db = new DatabaseConnector();
		
		String uploadPath=db.getPropertiesPath("profielPicPath");

		String filename=null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			
			List<FileItem> fileitems = new ArrayList<FileItem>();
			fileitems=fileUpload.parseRequest(request);
			
			FileItem fileObject = fileitems.get(0);
			
			String uploadedImageName = fileObject.getName();
			String fileExtension = FilenameUtils.getExtension(uploadedImageName);
			
			if(isValid(fileExtension)) {
				File newFile = new File(uploadPath+user.getUsername()+"."+fileExtension);
				fileObject.write(newFile);
				filename=newFile.getName();
			} else {
				response.sendRedirect("userloggedin.jsp?msg=Only .jpg image allowed..");
				return;
			}
			
		} catch (Exception e) {
			db.logToFile(e, servletname);
		}
		
		boolean recordUpdateStatus = false;
		if(filename!=null) {
			UserController control = new UserController();
			String sql="update client set DPURL='"+filename+"' where id='"+user.getId()+"'";
			recordUpdateStatus = control.dpRecordUpdate(sql);
		}
		
		if (recordUpdateStatus) {
			response.sendRedirect("userloggedin.jsp?msg=Picture Successfully Uploaded..");
		} else {
			response.sendRedirect("userloggedin.jsp?msg=Picture Upload Failed..");			
		}
		
	}
	
	public boolean isValid(String ext) {
		ext=ext.toLowerCase();
		if(ext.equals("jpg")) {
			return true;
		}
		return false;
	}
}
