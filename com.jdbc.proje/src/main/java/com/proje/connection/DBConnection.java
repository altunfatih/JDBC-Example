package com.proje.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	static {
		
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream("src/main/resources/database.properties");
			properties.load(inputStream);
			
			driver = properties.getProperty("db_driver");
			url = properties.getProperty("db_url");
			username = properties.getProperty("db_username");
			password = properties.getProperty("db_password");
			
			LOGGER.info("database.properties'den veriler çekildi");
			
		} catch (IOException e) {
			LOGGER.warn("database.properties dosyasından veriler çekilmedi HATA: " + e);
		}
	}
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName(driver);
			LOGGER.info("Driver'a başarılı bir şekilde bağlandı");
		} catch (ClassNotFoundException e) {
			LOGGER.warn("Driver'a bağlanırken hata oluştu HATA: " + e);
		}
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			LOGGER.info("Bağlantı başarılı");
		} catch (SQLException e) {
			LOGGER.warn("Bağlantı oluşturulurken hata meydana geldi! HATA: " + e);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		
		if (resultSet != null)
			try {
				resultSet.close();
				LOGGER.info("ResultSet kapatıldı");
			} catch (SQLException e) {
				LOGGER.warn("ResultSet kapatılırken hata meydana geldi HATA: " + e);
			}
		
		if(preparedStatement != null) 
			try {
				preparedStatement.close();
				LOGGER.info("PreparedStatement kapatıldı");
			} catch (SQLException e) {
				LOGGER.warn("PreparedStatement kapatılırken hata meydana geldi HATA: " + e);

			}
		
		if(connection != null)
			try {
				connection.close();
				LOGGER.info("Connection kapatıldı");
			} catch (SQLException e) {
				LOGGER.warn("Connection kapatılırken hata meydana geldi HATA: " + e);
			}
	}
}
