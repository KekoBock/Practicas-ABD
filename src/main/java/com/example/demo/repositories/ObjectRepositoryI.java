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

	/**
	 * Inserta un objeto pasado dentro de la base de datos
	 * 
	 * @param Objeto
	 *            Objeto con su definicion y atributos
	 * @return Resultado de la consulta
	 * @throws SQLException
	 */
	public ResultSet create(Objeto Objeto) throws SQLException;

	/**
	 * Lee un objeto de la base de datos
	 * 
	 * @param o
	 *            Objeto que contiene la clave primaria a consultar
	 * @return Objeto leido
	 * @throws SQLException
	 */
	public Objeto read(Objeto o) throws SQLException;

	/**
	 * Actualiza el objeto en la base de datos
	 * 
	 * @param o
	 *            Objeto a actualizar
	 * @return Resultado de la consulta
	 * @throws SQLException
	 */
	public ResultSet update(Objeto o) throws SQLException;

	/**
	 * Elimina el objeto de la base de datos
	 * 
	 * @param o
	 *            Objeto a eliminar con su clave primaria
	 * @return Resultado de la consulta
	 * @throws SQLException
	 */
	public ResultSet delete(Objeto o) throws SQLException;

	/**
	 * Obtiene todos los objetos del mismo tipo que el sample Nota: No funcionara
	 * con tablas muy extensas. Para eso se necesita una version que gestione
	 * flujos.
	 * 
	 * @param sample
	 *            Objeto de ejemplo a cargar
	 * @return Lista de objetos cargados de la base de datos
	 * @throws SQLException
	 */
	public List<Objeto> getAll(Objeto sample) throws SQLException;

	/**
	 * Devuelve la conexion actual
	 * @return Conexion
	 */
	default Connection getConn() {
		return com.example.demo.repositories.impl.ObjectRepository.getConnection();
	}

	/**
	 * Devuelve los metadatos de la base de datos
	 * @return Metadatos de la base de datos
	 * @throws SQLException
	 */
	default DatabaseMetaData getMetaData() throws SQLException {
		return this.getConn().getMetaData();
	}

	/**
	 * Devuelve un punto guardado (transacciones)
	 * @return Punto guardado
	 * @throws SQLException
	 */
	default Savepoint savePoint() throws SQLException {
		return this.getConn().setSavepoint();
	}

	/**
	 * Compromete los cambios en la base de datos
	 * @throws SQLException
	 */
	default void commit() throws SQLException {
		this.getConn().commit();
	}
}
