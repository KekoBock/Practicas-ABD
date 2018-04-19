package com.example.demo.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Objeto implements Serializable {
	private static final long serialVersionUID = 6534827752744052724L;
	private HashMap<String, Object> atributos;
	private List<String> nombresAtributos;
	private String tableName;

	public Objeto(String tableName) {
		this.tableName = tableName;
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
