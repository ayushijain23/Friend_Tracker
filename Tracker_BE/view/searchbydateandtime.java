package com.nic.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.nic.dao.DBHelper;
import com.nic.dao.TrackingController;

/**
 * Servlet implementation class searchbydateandtime
 */
@WebServlet("/searchbydateandtime")
public class searchbydateandtime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchbydateandtime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		try{
			ResultSet rs=TrackingController.searchbydate(request.getParameter("MOBILENO"),request.getParameter("DATE"),request.getParameter("TIMEONE"),request.getParameter("TIMETWO"));
		
			 ArrayList<JSONObject>L=DBHelper.JsonEngine(rs);
	            out.println(L);
			}catch(Exception e)
			{
				out.println(e);
		}

	}
	}


