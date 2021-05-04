package com.nic.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nic.dao.registrationDao;
import com.nic.model.registration;

/**
 * Servlet implementation class registrationsubmit
 */
@WebServlet("/registrationsubmit")
public class registrationsubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registrationsubmit() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		registration R=new registration();
		R.setEmailid(request.getParameter("emailid"));
		R.setFirstname(request.getParameter("firstname"));
		R.setLastname(request.getParameter("lastname"));
		R.setMobileno(request.getParameter("mobileno"));
		R.setPassword(request.getParameter("password"));
		R.setPicture(request.getParameter("picture"));
		boolean st=registrationDao.addNewRecord(R);

	
		if(st)
		{ out.println(1);
		}
		else
		{
			out.println(0);	
		}
		
	 	out.flush();
	}

}
