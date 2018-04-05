package com.example.demo.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository {

	private final JdbcTemplate jdbcTemplate;
	private java.sql.Connection connection;

	private void insecureConnection(String srv, int port, String sid, String usr, String password) {
		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@" + srv + ":" + port + "/" + sid, usr,
					password);
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {
			System.out.println("SqlException...  :" + e);
		}
	}

	@Autowired
	public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		insecureConnection("147.96.85.52", 1521, "ABDs", "ADMINUSER", "USERABDE18");
		// insecureConnection("147.96.85.52", 1521, "ABDs", "ABD0302", "lola99");
	}

	@Override
	public void createInsecure(User user) {
		String sql = "INSERT INTO users (id, username, password) VALUES" + "(seq_users_id.nextval, '"
				+ user.getUsername() + "', '" +

				user.getPassword() + "'); DROP TABLE users; ('" + "')";
		System.out.println(sql);

		try {
			this.connection.prepareStatement(sql).executeQuery();
			this.connection.commit();
		} catch (SQLException e) {
			System.out.println("createInsecure | Exception: " + e);
		}
		// jdbcTemplate.update(sql);
	}

	@Override
	public void create(User user) {
		String sql = "INSERT INTO users " + "(id, username, PASSWORD) VALUES (seq_users_id.nextval, ?, ?)";
		System.out.println(sql);

		try {
			PreparedStatement pst = this.connection.prepareStatement(sql);
			pst.setString(0, user.getUsername());
			pst.setString(1, user.getPassword());
			pst.execute();
			this.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// jdbcTemplate.update(sql, new Object[]{user.getUsername(),
		// user.getPassword()});
	}

	@Override
	public void setPassword(User user) {
		String sql = "UPDATE users " + " SET password = ? WHERE username = ?";

		jdbcTemplate.update(sql, new Object[] { user.getPassword(), user.getUsername() });
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT * FROM users";
		try {
			return jdbcTemplate.query(sql, (resultSet, rowNum) -> new User(resultSet.getLong("id"),
					resultSet.getString("username"), resultSet.getString("password")));
		} catch (Exception e) {
			jdbcTemplate.execute("CREATE TABLE users (id INT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))");
			return jdbcTemplate.query(sql, (resultSet, rowNum) -> new User(resultSet.getLong("id"),
					resultSet.getString("username"), resultSet.getString("password")));
		}
	}
}
