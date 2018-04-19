package com.example.demo.repositories;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import com.example.demo.models.Objeto;

public interface ObjectRepositoryI {

	default Connection connect(String driverClassName, String url, String usr, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName(driverClassName);
		Connection c = java.sql.DriverManager.getConnection(url, usr, password);
		return c;
	}

	public ResultSet create(Objeto Objeto) throws SQLException;

	public Objeto read(Long id) throws SQLException;

	public ResultSet update(Objeto Objeto) throws SQLException;

	public ResultSet delete(Long id) throws SQLException;
	
	/**
	 * Note that for now this will load all rows into memory and return. This
	 * obviously will not work out if your data set is extremely large. Need to
	 * implement a streaming version of this to work with large data sets.
	 * 
	 * @return List<Objeto>
	 */
	public List<Objeto> getAll() throws SQLException;
	
	default Connection getConn() {
		return com.example.demo.repositories.impl.ObjectRepository.getConnection();
	}
	
	default DatabaseMetaData getMetaData() throws SQLException {
		return this.getConn().getMetaData();
	}
	
	default Savepoint savePoint() throws SQLException {
		return this.getConn().setSavepoint();
	}
	
	default void commit() throws SQLException {
		this.getConn().commit();
	}
}
