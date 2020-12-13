package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Category;
import com.proje.model.queries.CategoryQueries;
import com.proje.repository.CategoryRepository;

public class CategoryRepositoryImpl implements CategoryRepository{

	private final Logger LOGGER = LogManager.getLogger();
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	@Override
	public Category findCategoryById(int id) {
		connection = DBConnection.getConnection();
		
		Category category = null;
		
		try {
			preparedStatement = connection.prepareStatement(CategoryQueries.findCategoryByIdQuery);
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");
				
				category = new Category(categoryId, categoryName);
			}
			
		} catch (SQLException e) {
			LOGGER.warn("Category aranırken hata meydana geldi. HATA : " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return category;
	}
	
	@Override
	public List<Category> findCategories() {
		connection = DBConnection.getConnection();
		
		List<Category> categorys = new ArrayList<Category>();
		
		try {
			preparedStatement = connection.prepareStatement(CategoryQueries.findCategoriesQuery);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");
				
				Category category = new Category(categoryId, categoryName);
				categorys.add(category );
			}
			
		} catch (SQLException e) {
			LOGGER.warn("Category listesi alınırken hata meydana geldi. HATA : " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return categorys;
	}
}
