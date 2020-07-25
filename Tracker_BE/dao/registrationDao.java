package com.nic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.nic.model.registration;
public class registrationDao {

static	String provider="jdbc:mysql://localhost:3306/test";
static String uid="root";
static String pwd="789";
public static boolean addNewRecord(registration R)
{try{ Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection cn=DriverManager.getConnection(provider,uid,pwd);
	String query="insert into register values('"+R.getEmailid()+"','"+R.getFirstname()+"','"+R.getLastname()+"','"+R.getMobileno()+"','"+R.getPassword()+"','"+R.getPicture()+"')";
boolean s=DBHelper.executeUpdate(query, cn);
return(s);
}catch(Exception e)
{System.out.println(e);
return(false);
}
}
public static ResultSet Checklogin(String userid,String userpassword)
{try{ Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection cn=DriverManager.getConnection(provider,uid,pwd);
	String q="select * from register where (emailid='"+userid+"' or mobilno='"+userid+"') and password='"+userpassword+"'"; 
	System.out.println(q);
	ResultSet rs=DBHelper.executeQuery(cn, q);
	return(rs);
}catch(Exception e)
{System.out.println(e);
return(null);
}
}

}