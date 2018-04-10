package com.example.demo.repositories.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.Application;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import oracle.jdbc.driver.OracleSQLException;

@Repository
public class InsecureUserRepository implements UserRepository {
	private Connection connection;

	@Autowired
	public InsecureUserRepository() throws ClassNotFoundException, SQLException {
		HashMap<String, String> conf = Application.getDataSourceProps();

		if (conf == null)
			throw new OracleSQLException("Hey! We don't have a default configuration for DB");
		this.connection = connect(conf.get("driver-class-name"), conf.get("url"), conf.get("username"),
				conf.get("password"));
		// NOTICE: use getConnection to manage connection... transactions...
		this.connection.setAutoCommit(true);
		this.connection.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE);
	}

	@Override
	public ResultSet create(User user) {
		String query = "INSERT INTO users (id, username, password) VALUES (seq_users_id.nextval, '" + user.getUsername() + "', '" + user.getPassword() + "')";
		try {
			Statement a = this.connection.createStatement();
			return a.executeQuery(query);
			// return this.connection.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			System.out.println("QUERY: " + query + " \ncreate insecure | SQLException: " + e);
		}
		return null;
	}

	@Override
	public User read(Long id) {
		User u = null;
		String query = "SELECT * FROM users WHERE id = '" + id + "'";

		try {
			ResultSet rs = this.connection.prepareStatement(query).executeQuery();

			if (rs.next())
				return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
		} catch (SQLException e) {
			System.out.println("QUERY: " + query + " \ncreate | SQLException: " + e);
		}

		return u;
	}

	@Override
	public ResultSet update(User user) {
		String query = "UPDATE users SET " + "username = '" + user.getUsername() + "', " + "password = '"
				+ user.getPassword() + "' " + "WHERE id = '" + user.getId() + "'";
		try {
			return this.connection.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			System.out.println("QUERY: " + query + " \nupdate | SQLException: " + e);
		}
		return null;
	}

	@Override
	public ResultSet delete(Long id) {
		String query = "DELETE users WHERE id = '" + id + "'";
		try {
			return this.connection.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			System.out.println("QUERY: " + query + " \ndelete | SQLException: " + e);
		}
		return null;
	}

	@Override
	public List<User> getAll() {
		ArrayList<User> usrs = new ArrayList<User>();
		try {
			ResultSet rs = this.connection.prepareStatement("SELECT * FROM users").executeQuery();

			while (rs.next())
				usrs.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
		} catch (SQLException e) {
			System.err.println("SQLException while loading users: " + e);
		}

		return usrs;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}
}
