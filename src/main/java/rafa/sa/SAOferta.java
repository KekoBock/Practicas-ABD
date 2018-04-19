package rafa.sa;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import rafa.dao.DAOOferta;
import rafa.transfers.TOferta;

public class SAOferta
{
	private DAOOferta dao;

	/**
	 * Constructora por defecto
	 */
	public SAOferta(String usuario, String password, String nombreServidor, String numeroPuerto, String sid)
	{
		dao = new DAOOferta(usuario, password, nombreServidor, numeroPuerto, sid);
	}
	
	/**
	 * Obtiene una oferta de la base de datos a partir de un c�digo
	 * 
	 * @param codigo: c�digo de la oferta
	 * @return oferta correspondiente al c�digo
	 */
	public TOferta obtenerOferta(String codigo)
	{
		return dao.obtenerOferta(codigo);
	}

	/**
	 * Guarda una oferta en la base de datos
	 * 
	 * @param oferta: la oferta a guardar
	 * @return resultado de la operaci�n
	 */
	public SAResultados crearOferta(TOferta oferta)
	{		
    	if (dao.obtenerOferta(oferta.getCodOferta()) == null)
    	{
        	dao.crearOferta(oferta);  	

        	return SAResultados.CrearOfertaOK;
    	}
    	else
    	{
    		return SAResultados.CrearOfertaYaExiste;
    	}
	}

	/**
	 * Eliminar una oferta de la base de datos
	 * 
	 * @param oferta: la oferta a eliminar
	 * @return resultado de la operaci�n
	 */
	public SAResultados eliminarOferta(String codigo)
	{
    	if (dao.obtenerOferta(codigo) != null)
    	{
        	dao.eliminarOferta(codigo);
        	JOptionPane.showMessageDialog(null, "Oferta eliminada correctamente.");
        	return SAResultados.EliminarOfertaOK;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Esa oferta no existe en la base de datos.");
    		return SAResultados.EliminarOfertaNoExiste;
    	}
	}

	/**
	 * Actualiza una oferta de la base de datos
	 * 
	 * @param oferta: la oferta a actualizar
	 * @return resultado de la operaci�n
	 */	
	public SAResultados actualizarOferta(TOferta oferta)
	{
		if (dao.obtenerOferta(oferta.getCodOferta()) != null)
		{
			dao.actualizarOferta(oferta);
			return SAResultados.ActualizarOfertaOK;
		}
		else
		{
			return SAResultados.ActualizarOfertaNoExiste;
		}
	}

	/**
	 * Obtiene todas las ofertas almacenadas en la base de datos
	 * 
	 * @return
	 */
	public ArrayList<TOferta> obtenerTodasOfertas()
	{
		return dao.obtenerTodasOfertas();
	}

	/**
	 * Solicita al DAO que se conecte a la base de datos y devuelve un booleano que indica si ha habido �xito (true) o no (false)
	 * 
	 * @return true si se ha podido conectar y false en otro caso
	 */
	public boolean conectar()
	{
		return dao.conectar(false);
	}

	/**
	 * Guarda los cambios en la base de datos
	 * 
	 * @return resultado de la operaci�n
	 */
	public SAResultados guardar()
	{
		if (dao.guardar())
		{
			return SAResultados.GuardarOK;
		}
		else
		{
			return SAResultados.GuardarNOK;
		}
	}
	
	/**
	 * Deshace las operaciones realizadas
	 * 
	 * @return resultado de la operaci�n
	 */
	public SAResultados deshacer()
	{
		if (dao.deshacer())
		{
			return SAResultados.DeshacerOK;
		}
		else
		{
			return SAResultados.DeshacerNOK;
		}
	}
	
	/**
	 * Solicita al DAO que cierre la conexi�n 
	 */
	public void cerrar()
	{
		dao.desconectar();
	}
}
