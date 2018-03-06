package com.cusat.uploadimage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize=169999999)
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UploadImage() {
        super();        
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "jdbc:mysql://localhost:3306/db";
		String user = "root";
		String password = "root";
		
		String first_name=request.getParameter("first_name");
		String last_name=request.getParameter("last_name");
		Part filePart=request.getPart("photo");
		InputStream inputStream=null;
		if(filePart!=null)
		{
			long fileSize=filePart.getSize();
			String fileContent=filePart.getContentType();
			inputStream=filePart.getInputStream();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				String sql = "INSERT INTO person (first_name, last_name, photo) values (?, ?, ?)";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1,first_name);
				statement.setString(2,last_name);
				//statement.setBlob(3,inputStream);
				statement.setBinaryStream(3,inputStream,inputStream.available());
				int updatedRecord=statement.executeUpdate();
				if(updatedRecord!=0)
				{
					System.out.println("File uploaded successfully");
				}
				else
				{
					System.out.println("Error occured!");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
