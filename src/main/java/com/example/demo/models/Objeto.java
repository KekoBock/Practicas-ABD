package com.example.demo.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.test.util.TestPropertyValues.Pair;

public class Objeto implements Serializable {
	private static final long serialVersionUID = 6534827752744052724L;
	private HashMap<String, Object> atributos;
	private List<String> nombresAtributos;
	private String tableName;
	private String primaryKeyNombreAt = "id";

	/**
	 * Construye un Objeto de una tabla de la base de datos
	 * @param tableName Nombre de la tabla
	 */
	public Objeto(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * Clase que contiene el par <Nombre del Atributo, Valor del atributo>
	 * @author AdrianSh
	 */
	protected class Atributo {
		// Publicos para facilitar el acceso sin mas.
		public String a;
		public Object v;
		
		/**
		 * Construye la pareja con su atributo y valor
		 * @param a Nombre del atributo
		 * @param v Valor del atributo
		 */
		public Atributo(String a, Object v) {
			this.a = a;
			this.v = v;
		}
	}
	
	public Objeto(Atributo...atributos) {
		for(Atributo a : atributos)
			this.setAtributo(a.a, a.v);
	}

	public String getTableName() {
		return this.tableName;
	}
	
	public List<String> getAtributos (){
		return this.nombresAtributos;
	}

	/**
	 * Asigna un atributo a un objeto
	 * 
	 * @param a
	 *            Nombre del atributo
	 * @param value
	 *            Valor del atributo
	 */
	public void setAtributo(String a, Object value) {
		atributos.put(a, value);
		nombresAtributos.add(a);
	}

	public Object getAtributo(String a) {
		return atributos.get(a);
	}
	
	public void removeAtributo(String a) {
		nombresAtributos.remove(a);
		atributos.remove(a);
	}
	
	public void setPrimaryKeyName(String a) {
		this.primaryKeyNombreAt = a;
	}
	
	public Object getPrimaryKey() {
		return atributos.get(this.primaryKeyNombreAt);
	}
	
	public String getPrimaryKeyName() {
		return this.primaryKeyNombreAt;
	}
	
	/**
	 * To get for example:
	 * 	nombre, contraseña, username
	 * 
	 * @return String Atributos separados con ,
	 */
	public String getAtributosAsSQLString() {
		String a = "";
		
		for(String at : nombresAtributos)
			a += at + ", ";
		
		return a.substring(0, a.length() - 1);
	}
}
