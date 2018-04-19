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
import com.example.demo.models.Objeto;
import com.example.demo.models.User;
import com.example.demo.repositories.ObjectRepositoryI;
import com.example.demo.repositories.UserRepository;

import es.ucm.fdi.bd.utils.JdbcUtils;
import oracle.jdbc.driver.OracleSQLException;

@Repository
public class ObjectRepository implements ObjectRepositoryI {
	private static Connection connection;

	@Autowired
	public ObjectRepository() throws ClassNotFoundException, SQLException {
		HashMap<String, String> conf = Application.getDataSourceProps();
		if (conf == null)
			throw new OracleSQLException("Hey! We don't have a default configuration for DB");
		ObjectRepository.connection = connect(conf.get("driver-class-name"), conf.get("url"), conf.get("username"),
				conf.get("password"));
		// NOTICE: use getConnection to manage connection... transactions...
		ObjectRepository.connection.setAutoCommit(false);
		ObjectRepository.connection.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE);
	}

	@Override
	public ResultSet create(Objeto o) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet r = null;
		// Savepoint save1 = SecureUserRepository.connection.setSavepoint();
		
		List<String> atributos = o.getAtributos();
		try {
			String sql = "INSERT INTO " + o.getTableName() + " ( " + o.getAtributosAsSQLString() + " ) VALUES (";
			for (int i = 0; i < atributos.size(); i++)
				sql += " ?,";
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			stmt = ObjectRepository.connection.prepareStatement(sql);
			
			for (String a : atributos) {
				AQUIII CONTINUAR
			}
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
	public Objeto read(Long id) throws SQLException {
		Objeto u = null;
		String query = "SELECT * FROM users WHERE id = ?";

		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		
		
		if (rs.next())
			u = new Objeto(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
		
		closeResultSet(rs);
		closeStatement(stmt);
		return u;
	}

	@Override
	public ResultSet update(Objeto user) throws SQLException {
		String query = "UPDATE users SET username = ?, password = ? WHERE id = ?";
		ResultSet rs = null;
		
		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);
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
		
		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);
		stmt.setLong(1, id);
		
		rs = stmt.executeQuery();
		
		closeStatement(stmt);
		return rs;
	}

	@Override
	public List<Objeto> getAll() throws SQLException {
		ArrayList<Objeto> usrs = new ArrayList<Objeto>();
		PreparedStatement s = ObjectRepository.connection.prepareStatement("SELECT * FROM Objetos");
		ResultSet rs = s.executeQuery();

		while (rs.next())
			usrs.add(new Objeto(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
		
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
		return ObjectRepository.connection;
	}
}
