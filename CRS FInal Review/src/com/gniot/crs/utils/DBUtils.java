package com.gniot.crs.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	
	 private static final String URL = "jdbc:mysql://localhost:3306/src_schema";
	    private static final String USER = "root";
	    private static final String PASSWORD = "Asif@700";

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	        
	        
	        
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static Connection getConnection() {
//		Connection connection = null;
//        if (connection != null)
//            return connection;
//        else 
//        {
//            try 
//            {
//            	
//            	Properties prop = new Properties();
//                InputStream inputStream = new FileInputStream("G:./config.properties");
//                prop.load(inputStream);
//                String driver = prop.getProperty("driver");
//                String url = prop.getProperty("jdbc:mysql://localhost:3306/src_schema");
//                String user = prop.getProperty("root");
//                String password = prop.getProperty("Asif@700");
//                Class.forName(driver);
//                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/src_schema", "root", "Asif@700");
//                
//            }
//            catch (ClassNotFoundException e){
//                e.printStackTrace();
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//            catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            return connection;
//        }
//
//    }
}