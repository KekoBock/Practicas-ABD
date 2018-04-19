package com.example.demo.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

import es.ucm.fdi.bd.utils.JdbcUtils;
import oracle.jdbc.driver.OracleSQLException;

@Repository
public class SecureUserRepository implements UserRepository {
	private static Connection connection;

	@Autowired
	public SecureUserRepository() throws ClassNotFoundException, SQLException {
		HashMap<String, String> conf = Application.getDataSourceProps();
		if (conf == null)
			throw new OracleSQLException("Hey! We don't have a default configuration for DB");
		SecureUserRepository.connection = connect(conf.get("driver-class-name"), conf.get("url"), conf.get("username"),
				conf.get("password"));
		// NOTICE: use getConnection to manage connection... transactions...
		SecureUserRepository.connection.setAutoCommit(false);
		SecureUserRepository.connection.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE);
	}

	@Override
	public ResultSet create(User user) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet r = null;
		// Savepoint save1 = SecureUserRepository.connection.setSavepoint();

		try {
			stmt = SecureUserRepository.connection.prepareStatement(
					"INSERT INTO users (id, username, password) VALUES (seq_users_id.nextval, ? , ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			r = stmt.executeQuery();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
			// SecureUserRepository.connection.rollback(save1);
		}
		
		closeStatement(stmt);

		return r;
	}

	@Override
	public User read(Long id) throws SQLException {
		User u = null;
		String query = "SELECT * FROM users WHERE id = ?";

		PreparedStatement stmt = SecureUserRepository.connection.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		
		
		if (rs.next())
			u = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
		
		closeResultSet(rs);
		closeStatement(stmt);
		return u;
	}

	@Override
	public ResultSet update(User user) throws SQLException {
		String query = "UPDATE users SET username = ?, password = ? WHERE id = ?";
		ResultSet rs = null;
		
		PreparedStatement stmt = SecureUserRepository.connection.prepareStatement(query);
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPassword());
		stmt.setLong(1, user.getId());
		rs = stmt.executeQuery();
		
		return rs;
	}

	@Override
	public ResultSet delete(Long id) throws SQLException {
		String query = "DELETE users WHERE id = ?";
		ResultSet rs = null;
		
		PreparedStatement stmt = SecureUserRepository.connection.prepareStatement(query);
		stmt.setLong(1, id);
		
		rs = stmt.executeQuery();
		
		closeStatement(stmt);
		return rs;
	}

	@Override
	public List<User> getAll() throws SQLException {
		ArrayList<User> usrs = new ArrayList<User>();
		PreparedStatement s = SecureUserRepository.connection.prepareStatement("SELECT * FROM users");
		ResultSet rs = s.executeQuery();

		while (rs.next())
			usrs.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
		
		closeResultSet(rs);
		closeStatement(s);
		
		return usrs;
	}

	private void closeStatement(Statement s) throws SQLException {
		if (s != null)
			s.close();
	}
	private void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}

	public static Connection getConnection() {
		return SecureUserRepository.connection;
	}
}
