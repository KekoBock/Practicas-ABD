package rafa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import rafa.transfers.TOferta;

public class DAOOferta
{
	private static String usuario;
	private static String password;
	private static String nombreServidor;
	private static String numeroPuerto;
	private static String sid;
	private static String url;
	private static final String nombreDriver = "oracle.jdbc.driver.OracleDriver";
	private Connection conexion;
	
	/**
	 * Constructora de la clase a partir de los datos necesarios para establecer la conexi�n
	 * 
	 * @param usuarioE: el nombre de usuario
	 * @param passwordE: la contrase�a del usuario
	 * @param nombreServidorE: el nombre del servidor
	 * @param numeroPuertoE: el n�mero del puerto
	 * @param sidE: el sid
	 */
	public DAOOferta(String usuarioE, String passwordE, String nombreServidorE, String numeroPuertoE, String sidE)
	{
		usuario = usuarioE;
		password = passwordE;
		nombreServidor = nombreServidorE;
		numeroPuerto = numeroPuertoE;
		sid = sidE;
		url  = "jdbc:oracle:thin:@" + nombreServidor + ":" + numeroPuerto + "/" + sid;
	}
	
	/**
	 * Obtiene una oferta a partir de su c�digo
	 * 
	 * @param codigo: el c�digo de la oferta
	 * @return oferta: la oferta con el c�digo indicado o null si no existe en la base de datos
	 */
	public TOferta obtenerOferta(String codigo)
	{
		TOferta oferta = null;
		
		try
		{
			String query = "SELECT * FROM Oferta WHERE CodOferta = '" + codigo + "'";
			ResultSet rs = conexion.prepareStatement(query).executeQuery();
			
			if (rs.next())
			{
				String codOferta = rs.getString(1);
				String clase = rs.getString(2);
				Double total = rs.getDouble(3);
				oferta = new TOferta(codOferta, clase, total);
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
		return oferta;
	}
	
	/**
	 * Obtiene todas las ofertas de la base de datos
	 * 
	 * @return ofertas: todas las ofertas de la base de datos
	 */
	public ArrayList<TOferta> obtenerTodasOfertas()
	{
		ArrayList<TOferta> ofertas = new ArrayList<TOferta>();
		
		try
		{
			String query = "SELECT * FROM Oferta";
			ResultSet rs = conexion.prepareStatement(query).executeQuery();
			
			while (rs.next())
			{
				String codOferta = rs.getString(1);
				String clase = rs.getString(2);
				Double total = rs.getDouble(3);
				TOferta oferta = new TOferta(codOferta, clase, total);
				ofertas.add(oferta);
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
		return ofertas;
	}
	
	/**
	 * Introduce una nueva fila con una oferta en la base de datos
	 * 
	 * @param oferta: la oferta a introducir
	 */
	public void crearOferta(TOferta oferta)
	{
		try
		{
			String query = "INSERT INTO Oferta VALUES("
					+ "'" + oferta.getCodOferta() + "', "
					+ "'" + oferta.getClase() + "', "
					+ oferta.getTotal() + ")";
			conexion.prepareStatement(query).executeQuery();
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Elimina una oferta a partir de su c�digo
	 * 
	 * @param codigo: el c�digo de la oferta
	 */
	public void eliminarOferta(String codigo)
	{
		
		try
		{
			String query = "DELETE FROM Oferta WHERE CodOferta = '" + codigo + "'";
			conexion.prepareStatement(query).executeQuery();
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Actualiza una oferta
	 * 
	 * @param oferta: la nueva oferta
	 */
	public void actualizarOferta(TOferta oferta)
	{	
		try
		{
			String query =
					"UPDATE Oferta SET " +
					"Clase = '" + oferta.getClase() + "', " +
					"Total = " + oferta.getTotal() +
					"WHERE CodOferta = '" + oferta.getCodOferta() + "'";
			conexion.prepareStatement(query).executeQuery();
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Se conecta a la base de datos y devuelve un booleano que indica si ha habido �xito (true) o no (false)
	 * 
	 * @param autocommit: valor que debe tomar el autocommit
	 * @return boolean
	 */
	public boolean conectar(boolean autocommit)
	{
		try
		{
			// Carga el driver JDBC
			Class.forName(nombreDriver);
		
			// Crear una conexi�n a la base de datos
			conexion = DriverManager.getConnection(url, usuario, password);
			conexion.setAutoCommit(autocommit);
			conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		}
		catch (ClassNotFoundException e)
		{
			// Si no se encontr� el driver de la base de datos
			System.err.println("ClassNotFoundException : "+e.getMessage());
			return false;
		}
		catch (SQLException e)
		{
			// No se pudo conectar a la base de datos
			System.err.println("SQLException: " + e.getMessage());
			return false;
		}
		
		return true;
	}

	/**
	 * Cierra la conexi�n a la base de datos
	 */
	public void desconectar()
	{
		try
		{
			conexion.close();
		}
		catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
	}
	
	/**
	 * Cuando el autocommit est� a false, guarda los cambios en la base de datos
	 * 
	 * @return true si se han guardado los cambios exitosamente y false en caso contrario
	 */
	public boolean guardar()
	{
		try
		{
			conexion.commit();
			return true;
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Cuando el autocommit est� a false, deshace las operaciones
	 * 
	 * @return true si se han deshecho las operaciones exitosamente y false en caso contrario
	 */
	public boolean deshacer()
	{
		try
		{
			conexion.rollback();
			return true;
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
}
