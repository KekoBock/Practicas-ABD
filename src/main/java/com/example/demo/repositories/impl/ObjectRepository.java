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
import com.example.demo.repositories.ObjectRepositoryI;

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

		List<String> atributos = o.getAtributos();
		try {
			String sql = "INSERT INTO " + o.getTableName() + " ( " + o.getAtributosAsSQLString() + " ) VALUES (";
			for (int i = 0; i < atributos.size(); i++)
				sql += " ?,";
			sql = sql.substring(0, sql.length() - 1) + ")";

			stmt = ObjectRepository.connection.prepareStatement(sql);

			// Ponemos el valor de cada atributo en la consulta preparada
			for (int j = 1; j <= atributos.size(); j++)
				stmt.setString(j, o.getAtributo(atributos.get(j)).toString());

			r = stmt.executeQuery();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}

		closeStatement(stmt);

		return r;
	}

	@Override
	public Objeto read(Objeto o) throws SQLException {
		Objeto u = null;
		String query = "SELECT * FROM " + o.getTableName() + " WHERE " + o.getPrimaryKeyName() + " = ?";

		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);
		stmt.setString(1, o.getPrimaryKey().toString());
		ResultSet rs = stmt.executeQuery();

		// Construimos el resultado
		if (rs.next()) {
			u = this.buildFromResultSet(o, rs);
		}

		closeResultSet(rs);
		closeStatement(stmt);
		return u;
	}

	@Override
	public ResultSet update(Objeto o) throws SQLException {
		// Construye la query de actualizar a partir del objeto
		String query = "UPDATE " + o.getTableName() + " SET ";
		String pkName = o.getPrimaryKeyName();
		List<String> atributos = o.getAtributos();

		for (String a : atributos)
			if (!a.equalsIgnoreCase(pkName))
				query += a + " = ?, ";
		query = query.substring(0, query.length() - 2) + " WHERE " + pkName + " = ?";

		ResultSet rs = null;
		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);

		// Asignamos el valor de los atributos
		int i = 1;
		for (i = 1; i <= atributos.size(); i++) {
			String a = atributos.get(i - 1);
			if (!a.equalsIgnoreCase(pkName))
				stmt.setString(i, o.getAtributo(a).toString());
		}

		stmt.setString(i, o.getPrimaryKey().toString());
		rs = stmt.executeQuery();

		return rs;
	}

	@Override
	public ResultSet delete(Objeto o) throws SQLException {
		String query = "DELETE " + o.getTableName() + " WHERE " + o.getPrimaryKeyName() + " = ?";
		ResultSet rs = null;

		PreparedStatement stmt = ObjectRepository.connection.prepareStatement(query);
		stmt.setString(1, o.getPrimaryKey().toString());

		rs = stmt.executeQuery();

		closeStatement(stmt);
		return rs;
	}

	@Override
	public List<Objeto> getAll(Objeto sample) throws SQLException {
		ArrayList<Objeto> obs = new ArrayList<Objeto>();
		PreparedStatement s = ObjectRepository.connection.prepareStatement("SELECT * FROM " + sample.getTableName());
		ResultSet rs = s.executeQuery();

		while (rs.next())
			obs.add(buildFromResultSet(sample, rs));

		closeResultSet(rs);
		closeStatement(s);

		return obs;
	}

	private Objeto buildFromResultSet(Objeto sample, ResultSet rs) throws SQLException {
		List<String> atributos = sample.getAtributos();
		Objeto r = new Objeto(sample.getTableName());
		for (String a : atributos)
			r.setAtributo(a, rs.getObject(a));

		return r;
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
