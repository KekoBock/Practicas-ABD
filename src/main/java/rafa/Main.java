package rafa;

import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import rafa.sa.SAOferta;
import rafa.view.View;

public class Main
{

	private static final String USUARIO_POR_DEFECTO = "";
	private static final String PASSWORD_POR_DEFECTO = "";
	private static final String NOMBRE_SERVIDOR_POR_DEFECTO = "//147.96.85.52";
	private static final String NUMERO_PUERTO_POR_DEFECTO = "1521";
	private static final String SID_POR_DEFECTO = "ABDs";
	
	public static void main(String[] args)
	{
		SAOferta sa = iniciarSAOferta();

		if (sa != null)
		{
			try
			{
				final SAOferta saView = sa;
				
				SwingUtilities.invokeAndWait
				(
						new Runnable()
						{
					
							@Override
							public void run() 
							{
								new View(saView);
							}
						}
				);
			}
			catch (InvocationTargetException | InterruptedException e)
			{
				
				System.err.println("No se ha podido crear la ventana: " + e.getMessage());
				System.exit(0);
			}
		}
		
	}
	
	/**
	 * Para inicializar el Servicio de Aplicaci�n y la conexi�n a la base de datos
	 * 
	 * @return servicio de aplicaci�n de oferta
	 */
	private static SAOferta iniciarSAOferta()
	{
		SAOferta sa = null;
		int result = JOptionPane.OK_OPTION;
		
		do
		{
			JTextField usuarioTF = new JTextField(10);
			JTextField passwordTF = new JTextField(10);
			JTextField nombreServidorTF = new JTextField(10);
			JTextField numeroPuertoTF = new JTextField(10);
			JTextField sidTF = new JTextField(10);
			
			usuarioTF.setText(USUARIO_POR_DEFECTO);
			passwordTF.setText(PASSWORD_POR_DEFECTO);
			nombreServidorTF.setText(NOMBRE_SERVIDOR_POR_DEFECTO);
			numeroPuertoTF.setText(NUMERO_PUERTO_POR_DEFECTO);
			sidTF.setText(SID_POR_DEFECTO);

		    JPanel panelIniciarSesion = new JPanel();
		    panelIniciarSesion.add(new JLabel("Usuario:"));
		    panelIniciarSesion.add(usuarioTF);
		    panelIniciarSesion.add(Box.createHorizontalStrut(15));
		    panelIniciarSesion.add(new JLabel("Password: "));
		    panelIniciarSesion.add(passwordTF);
		    panelIniciarSesion.add(Box.createHorizontalStrut(15));
		    panelIniciarSesion.add(new JLabel("Servidor: "));
		    panelIniciarSesion.add(nombreServidorTF);
		    panelIniciarSesion.add(Box.createHorizontalStrut(15));
		    panelIniciarSesion.add(new JLabel("Puerto: "));
		    panelIniciarSesion.add(numeroPuertoTF);
		    panelIniciarSesion.add(Box.createHorizontalStrut(15));
		    panelIniciarSesion.add(new JLabel("Sid: "));
		    panelIniciarSesion.add(sidTF);
		    
		    result = JOptionPane.showConfirmDialog(null, panelIniciarSesion, 
		               "Iniciar sesi�n", JOptionPane.OK_CANCEL_OPTION);
		    
		    if (result == JOptionPane.OK_OPTION) 
		    {
		    	sa = new SAOferta(usuarioTF.getText(), passwordTF.getText(), nombreServidorTF.getText(),
		    			numeroPuertoTF.getText(), sidTF.getText());
		    }
		    else
		    {
		    	sa = null;
		    }
		    
		}while(result != JOptionPane.CANCEL_OPTION && !sa.conectar());
		
		return sa;
	}


}
