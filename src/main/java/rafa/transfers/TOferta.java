package rafa.transfers;

public class TOferta
{
	private String codOferta;
	private String clase;
	private Double total;
	
	/**
	 * Constructora por defecto
	 */
	public TOferta() {}
	
	/**
	 * Constructora a partir del c�digo de la oferta
	 * 
	 * @param codOfertaE: c�digo de la oferta
	 */
	public TOferta(String codOfertaE)
	{
		codOferta = codOfertaE;
	}
	
	/**
	 * Constructora del transfer a partir de todos sus atributos
	 * 
	 * @param codOfertaE: el c�digo de entrada
	 * @param claseE: la clase de entrada
	 * @param totalE: el total de entrada
	 */
	public TOferta(String codOfertaE, String claseE, Double totalE)
	{
		codOferta = codOfertaE;
		clase = claseE;
		total = totalE;
	}
	
	/**
	 * Devuelve el c�digo de la oferta
	 * 
	 * @return codOferta
	 */
	public String getCodOferta()
	{
		return codOferta;
	}
	
	/**
	 * Devuelve la clase de la oferta
	 * 
	 * @return clase
	 */
	public String getClase()
	{
		return clase;
	}
	
	/**
	 * Devuelve el total de la oferta
	 * 
	 * @return total
	 */
	public Double getTotal()
	{
		return total;
	}
	
	/**
	 * Actualiza el valor del c�digo de oferta
	 * 
	 * @param codOfertaE: c�digo de entrada
	 */
	public void setCodOferta(String codOfertaE)
	{
		codOferta = codOfertaE;
	}

	/**
	 * Actualiza el valor de la clase de oferta
	 * 
	 * @param claseE: clase de entrada
	 */
	public void setClase(String claseE)
	{
		clase = claseE;
	}
	
	/**
	 * Actualiza el valor del total de oferta
	 * 
	 * @param totalE: total de entrada
	 */
	public void setTotal(Double totalE)
	{
		total = totalE;
	}
	
	@Override
	public String toString()
	{
		return "Oferta " + codOferta + System.getProperty("line.separator") +
				"   � " + clase + System.getProperty("line.separator") +
				"   � " + total;
	}
}
