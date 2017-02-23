package databaseUtils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MySqlDBUtils {
	private static String COMMON_PROP_LOCATION = "/properties/common.properties";
	private static String ENV_PROP_LOCATION = "/properties/";
	private Properties prop = new Properties();
	
	
	 Logger log=Logger.getLogger(MySqlDBUtils.class);
		   // JDBC driver name and database URL
		   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   static final String DB_URL = "jdbc:mysql://localhost/";
		   //  Database credentials
		   static final String USER = "root";
		   static final String PASS = "root";
		   
	 MySqlDBUtils() throws IOException{
			    
			   prop = new Properties();
		        prop.load(MySqlDBUtils.class.getResourceAsStream(COMMON_PROP_LOCATION));
			   if (System.getProperty("env") != null) {
		            prop.load(MySqlDBUtils.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
		        } else {
		            prop.load(MySqlDBUtils.class.getResourceAsStream(ENV_PROP_LOCATION + "at" + ".properties"));
		        }
		        
		   }
		   
			   
 public void creteSQLStatement() {
			   Connection conn = null;
			   Statement stmt = null;
			   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL, USER, PASS);

			      //STEP 4: Execute a query
			      System.out.println("Creating database...");
			      stmt = conn.createStatement();
			      
			      String sql = "CREATE DATABASE STUDENTS";
			      stmt.executeUpdate(sql);
			      System.out.println("Database created successfully...");
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   System.out.println("Goodbye!");
			}//end main
			}//end JDBCExample   


//create,update,insert// executeUpdate

//select //executequery