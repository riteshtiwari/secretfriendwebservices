package com.cgs.ws.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailActivateImpl  extends HttpServlet{
	
	SecretFrientDAO  secretFrientDAO;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			System.out.println("Inside servlet doget");
			String emailId=request.getParameter("email");
			secretFrientDAO.emailActivate(emailId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I m complete doGet.........");
	}

	// Redirect POST request to GET request.
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
