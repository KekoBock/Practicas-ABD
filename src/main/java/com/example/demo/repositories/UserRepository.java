package com.example.demo.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.models.User;

public interface UserRepository {

	default Connection connect(String driverClassName, String url, String usr, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName(driverClassName);
		Connection c = java.sql.DriverManager.getConnection(url, usr, password);
		return c;
	}

	public ResultSet create(User user);

	public User read(Long id);

	public ResultSet update(User user);

	public ResultSet delete(Long id);
	
	public Connection getConnection();

	/**
	 * Note that for now this will load all rows into memory and return. This
	 * obviously will not work out if your data set is extremely large. Need to
	 * implement a streaming version of this to work with large data sets.
	 * 
	 * @return List<User>
	 */
	public List<User> getAll();
}
