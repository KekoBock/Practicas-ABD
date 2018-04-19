package rafa.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rafa.sa.*;
import rafa.transfers.TOferta;

public class View extends JFrame
{
	private static final long serialVersionUID = 2118299654730994785L;

	private JButton botonMostrarOferta;
	private JButton botonCrearOferta;
	private JButton botonEliminarOferta;
	private JButton botonActualizarOferta;
	private JButton botonMostrarTodasOfertas;
	private JButton botonGuardar;
	private JButton botonDeshacer;
	private JButton botonSalir;
	private SAOferta sa;
	
	/**
	 * Constructora de la vista
	 * 
	 * @param saE: el servicio de aplicaci�n
	 */
	public View(SAOferta saE)
	{
		sa = saE;
		initGUI();
	}
	
	/**
	 * M�todo para inicializar la GUI en la constructora
	 */
	private void initGUI()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(8, 1));
		
		crearBotonMostrarOferta(mainPanel);
		crearBotonCrearOferta(mainPanel);
		crearBotonEliminarOferta(mainPanel);
		crearBotonActualizarOferta(mainPanel);
		crearBotonMostrarTodasOfertas(mainPanel);
		crearBotonGuardar(mainPanel);
		crearBotonDeshacer(mainPanel);
		crearBotonSalir(mainPanel);
		
		this.setContentPane(mainPanel);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(710, 750);
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	}
	
	/**
	 * Crea un bot�n para mostrar una oferta y lo a�ade al mainPanel
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonMostrarOferta(JPanel mainPanel)
	{
		botonMostrarOferta = new JButton("Mostrar oferta");
		mainPanel.add(botonMostrarOferta);
		botonMostrarOferta.setVisible(true);
		botonMostrarOferta.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JTextField codOfertaTF = new JTextField(10);

					    JPanel panelMostrarOferta = new JPanel();
					    panelMostrarOferta.add(new JLabel("Introducir el c�digo:"));
					    panelMostrarOferta.add(codOfertaTF);


					    int result = JOptionPane.showConfirmDialog(null, panelMostrarOferta, 
					               "Mostrar Oferta", JOptionPane.OK_CANCEL_OPTION);
					    
					    if (result == JOptionPane.OK_OPTION) 
					    {
					    	String codigo = codOfertaTF.getText();
					         
					        if(codigo.length() > 0)
					        {
					        	TOferta oferta = View.this.sa.obtenerOferta(codigo);
					        	
					        	if (oferta != null)
					        	{
					        		JOptionPane.showMessageDialog(null, oferta.toString());
					        	}
					        	else
					        	{
					        		JOptionPane.showMessageDialog(null, "No existe ninguna oferta con ese c�digo en la base de datos.");
					        	}
					        }
					        else
					        {
					        	JOptionPane.showMessageDialog(null, "Escribe alg�n c�digo para mostrarlo.");
					        }
					    }
					    
					}
				}
		);
	}

	/**
	 * Crea un bot�n para crear una oferta y lo a�ade al mainPanel
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonCrearOferta(JPanel mainPanel)
	{
		botonCrearOferta = new JButton("Crear oferta");
		mainPanel.add(botonCrearOferta);
		botonCrearOferta.setVisible(true);
		botonCrearOferta.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JTextField codOfertaTF = new JTextField(10);
						JTextField claseTF = new JTextField(10);
						JTextField totalTF = new JTextField(10);

					    JPanel panelCrearOferta = new JPanel();
					    panelCrearOferta.add(new JLabel("Introducir el c�digo:"));
					    panelCrearOferta.add(codOfertaTF);
					    panelCrearOferta.add(Box.createHorizontalStrut(15));
					    panelCrearOferta.add(new JLabel("Introducir la clase: "));
					    panelCrearOferta.add(claseTF);
					    panelCrearOferta.add(Box.createHorizontalStrut(15));
					    panelCrearOferta.add(new JLabel("Introducir el total: "));
					    panelCrearOferta.add(totalTF);



					    int result = JOptionPane.showConfirmDialog(null, panelCrearOferta, 
					               "Crear Oferta", JOptionPane.OK_CANCEL_OPTION);
					    
					    if (result == JOptionPane.OK_OPTION) 
					    {
					    	String codigo = codOfertaTF.getText();
					    	String clase = claseTF.getText();
					    	
					    	try
					    	{
						    	Double total = Double.parseDouble(totalTF.getText());
						        
						    	if(codigo.length() > 0 && clase.length() > 0)
						        {
						    		TOferta oferta = new TOferta(codigo, clase, total);
						    		
						    		switch(sa.crearOferta(oferta))
						    		{
						    		case CrearOfertaOK: JOptionPane.showMessageDialog(null, "Oferta creada correctamente."); break;
						    		case CrearOfertaYaExiste: JOptionPane.showMessageDialog(null, "Esa oferta ya existe en la base de datos."); break;
									default: JOptionPane.showMessageDialog(null, "Ha ocurrido alg�n error."); break;
						    		}
						        }
						        else
						        {
						        	JOptionPane.showMessageDialog(null, "Se deben rellenar todos los campos.");
						        }

					    	}
					    	catch(Exception exception)
					    	{
					    		JOptionPane.showMessageDialog(null, "El total debe ser un real decimal.");
					    	}
					    }
					    
					}
				}
		);
	}

	/**
	 * Crea un bot�n para eliminar una oferta y lo a�ade al mainPanel
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonEliminarOferta(JPanel mainPanel)
	{
		botonEliminarOferta = new JButton("Eliminar oferta");
		mainPanel.add(botonEliminarOferta);
		botonEliminarOferta.setVisible(true);
		botonEliminarOferta.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JTextField codOfertaTF = new JTextField(10);

					    JPanel panelEliminarOferta = new JPanel();
					    panelEliminarOferta.add(new JLabel("Introducir el c�digo:"));
					    panelEliminarOferta.add(codOfertaTF);


					    int result = JOptionPane.showConfirmDialog(null, panelEliminarOferta, 
					               "Eliminar Oferta", JOptionPane.OK_CANCEL_OPTION);
					    
					    if (result == JOptionPane.OK_OPTION) 
					    {
					    	String codigo = codOfertaTF.getText();
					        
					    	
					        if(codigo.length() > 0)
					        {
					        	switch (View.this.sa.eliminarOferta(codigo))
					        	{
					        	case EliminarOfertaOK: JOptionPane.showMessageDialog(null, "Oferta eliminada correctamente."); break;
					        	case EliminarOfertaNoExiste: JOptionPane.showMessageDialog(null, "Esa oferta no existe en la base de datos."); break;
								default: break;
					        	}
					        }
					        else
					        {
					        	JOptionPane.showMessageDialog(null, "Escribe alg�n c�digo para eliminar la oferta.");
					        }
					    }
					    
					}
				}
		);
	}

	/**
	 * Crea un bot�n para actualizar una oferta y lo a�ade al mainPanel
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonActualizarOferta(JPanel mainPanel)
	{
		botonActualizarOferta = new JButton("Actualizar oferta");
		mainPanel.add(botonActualizarOferta);
		botonActualizarOferta.setVisible(true);
		botonActualizarOferta.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JTextField codOfertaTF = new JTextField(10);
						JTextField claseTF = new JTextField(10);
						JTextField totalTF = new JTextField(10);

					    JPanel panelCrearOferta = new JPanel();
					    panelCrearOferta.add(new JLabel("Introducir el c�digo:"));
					    panelCrearOferta.add(codOfertaTF);
					    panelCrearOferta.add(Box.createHorizontalStrut(15));
					    panelCrearOferta.add(new JLabel("Introducir la clase: "));
					    panelCrearOferta.add(claseTF);
					    panelCrearOferta.add(Box.createHorizontalStrut(15));
					    panelCrearOferta.add(new JLabel("Introducir el total: "));
					    panelCrearOferta.add(totalTF);



					    int result = JOptionPane.showConfirmDialog(null, panelCrearOferta, 
					               "Actualizar Oferta", JOptionPane.OK_CANCEL_OPTION);
					    
					    if (result == JOptionPane.OK_OPTION) 
					    {
					    	String codigo = codOfertaTF.getText();
					    	String clase = claseTF.getText();
					    	
					    	try
					    	{
						    	Double total = Double.parseDouble(totalTF.getText());
						         
						        if(codigo.length() > 0 && clase.length() > 0)
						        {
						        	switch(sa.actualizarOferta(new TOferta(codigo, clase, total)))
						        	{
						        	case ActualizarOfertaOK: JOptionPane.showMessageDialog(null, "Oferta actualizada correctamente."); break;
						        	case ActualizarOfertaNoExiste: JOptionPane.showMessageDialog(null, "No existe esa oferta en la base de datos."); break;
						        	default: JOptionPane.showMessageDialog(null, "Ha ocurrido alg�n error."); break;
						        	}
						        }
						        else
						        {
						        	JOptionPane.showMessageDialog(null, "Se deben rellenar todos los campos.");
						        }
					    	}
					    	catch(Exception exception)
					    	{
					    		JOptionPane.showMessageDialog(null, "El total debe ser un real decimal.");
					    	}
					    }
					    
					}
				}
		);
	}

	/**
	 * Crea un bot�n para mostrar una oferta y lo a�ade al mainPanel
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonMostrarTodasOfertas(JPanel mainPanel)
	{
		botonMostrarTodasOfertas = new JButton("Mostrar todas ofertas");
		mainPanel.add(botonMostrarTodasOfertas);
		botonMostrarTodasOfertas.setVisible(true);
		botonMostrarTodasOfertas.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
					    String ofertasStr =
					    		"*************" + System.getProperty("line.separator") +
					    		"*OFERTAS*" + System.getProperty("line.separator") +
					    		"*************" + System.getProperty("line.separator") + System.getProperty("line.separator");
					    ArrayList<TOferta> ofertas = sa.obtenerTodasOfertas();
					    
					    for(TOferta oferta : ofertas)
					    {
					    	ofertasStr +=
					    			System.getProperty("line.separator") + System.getProperty("line.separator") +
					    			"----------------" +
					    			System.getProperty("line.separator") + System.getProperty("line.separator") +
					    			oferta.toString();
					    }
						
					    JTextArea text = new JTextArea(30, 20);
					    text.setText(ofertasStr);
					    JScrollPane panel = new JScrollPane(text);
						
					    
					    JFrame frame = new JFrame();
					    
						frame.setContentPane(panel);

						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.pack();
						frame.setVisible(true);
					    
					}
				}
		);
	}
	
	/**
	 * Crea un bot�n para guardar los cambios
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonGuardar(JPanel mainPanel)
	{
		botonGuardar = new JButton("Guardar");
		mainPanel.add(botonGuardar);
		botonGuardar.setVisible(true);
		botonGuardar.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{				
						switch(sa.guardar())
						{
						case GuardarOK: JOptionPane.showMessageDialog(null, "Se han guardado los cambios correctamente."); break;
						case GuardarNOK: JOptionPane.showMessageDialog(null, "No se han podido guardar los cambios correctamente."); break;
						default: break;
						}
					}
				}
		);
	}

	/**
	 * Crea un bot�n para deshacer las operaciones
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonDeshacer(JPanel mainPanel)
	{
		botonDeshacer = new JButton("Deshacer");
		mainPanel.add(botonDeshacer);
		botonDeshacer.setVisible(true);
		botonDeshacer.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{				
						switch(sa.deshacer())
						{
						case DeshacerOK: JOptionPane.showMessageDialog(null, "Deshacer realizado con �xito."); break;
						case DeshacerNOK: JOptionPane.showMessageDialog(null, "No se ha podido deshacer correctamente."); break;
						default: break;
						}
					}
				}
		);
	}

	
	/**
	 * Crea un bot�n para salir de la aplicaci�n
	 * 
	 * @param mainPanel: panel principal de la vista
	 */
	private void crearBotonSalir(JPanel mainPanel)
	{
		botonSalir = new JButton("Salir");
		mainPanel.add(botonSalir);
		botonSalir.setVisible(true);
		botonSalir.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						View.this.sa.cerrar();
						View.this.dispose();
					}
				}
		);
	}


}
