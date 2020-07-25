package com.nic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.nic.model.tracking;

public class TrackingController{

static	String provider="jdbc:mysql://localhost:3306/test";
static String uid="root";
static String pwd="789";
public static boolean addNewRecord(tracking T)
{try{ Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection cn=DriverManager.getConnection(provider,uid,pwd);
String q="insert into tracking(mobileno,latitude,longitude,tracking_time,tracking_date,address) values('"+T.getMobileno()+"','"+T.getLatitude()+"','"+T.getLongitude()+"','"+T.getTracking_time()+"','"+T.getTracking_date()+"','"+T.getAddress()+"')";
	//String query="insert into register values('"+R.getEmailid()+"','"+R.getFirstname()+"','"+R.getLastname()+"','"+R.getMobileno()+"','"+R.getPassword()+"','"+R.getPicture()+"')";
boolean s=DBHelper.executeUpdate(q, cn);
return(s);
}catch(Exception e)
{System.out.println(e);
return(false);
}
}
public static ResultSet searchByMobileNo(String  mobileno)
{ try{
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	 Connection cn=DriverManager.getConnection(provider,uid,pwd);
	String q="select * from tracking where mobileno='"+mobileno+"' order by id desc limit 1,10";
	System.out.println(q);
	ResultSet rs=DBHelper.executeQuery(cn, q);
	return(rs);
}catch(Exception e){
	
	System.out.println(e);
	return(null);
}
	
}
public static ResultSet searchbydate(String mobileno,String date,String timeone,String timetwo)
{
	try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		 Connection cn=DriverManager.getConnection(provider,uid,pwd);
		 String q="select * from tracking where mobileno='"+mobileno+"' and tracking_date='"+date+"' and tracking_time>='"+timeone+"' and tracking_time<='"+timetwo+"'";
			System.out.println(q);
			ResultSet rs=DBHelper.executeQuery(cn, q);
			return(rs);
		}catch(Exception e){
			
			System.out.println(e);
			return(null);
		} 
	}
}

