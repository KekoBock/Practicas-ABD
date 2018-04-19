package es.ucm.fdi.bd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import es.ucm.fdi.bd.utils.JdbcUtils;
import es.ucm.fdi.bd.utils.PropertiesUtils;
import oracle.jdbc.pool.OracleDataSource;

public class JdbcMetaDataTest {
	
	public static void main(String args[]) throws SQLException {
        JdbcMetaDataTest test = new JdbcMetaDataTest();
        test.test();
    }

    /**
     * Prueba la conexión al SGBD.
     */
    private void test() {
        Connection conn = null;

        try {
            conn = getConnectionFromDataSource();

            ejecutaConsulta2(conn);

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            /*
             * Cerramos la conexión con el SGBD.
             */
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JdbcUtils.printSQLException(e);
            }
        }

    }
    
    /**
     * Ejemplo de uso de las clases de JDBC para lanzar una consulta SELECT contra el SGBD.
     * 
     * @param conn {@link Connection} con el SGBD que se utiliza para lanzar la consulta.
     */
    @SuppressWarnings("resource")
	private void ejecutaConsulta2(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Crea el objeto que representa la consulta SQL
            stmt = conn.createStatement();
            
            // Obtenemos la información de las tablas
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            
            // Leemos las tablas que hay
//            rs = databaseMetaData.getSchemas();
//            
//            System.out.println("SCHEMAS:");
//            while(rs.next())
//            	System.out.println(rs.getString(1));
            
            // Miro las tablas que tengo en mi catálogo
            rs = databaseMetaData.getTables(null, "MiUSUARIO", null, new String[] {"TABLE"});
            
            System.out.println("Las tablas existentes son:");
            
            while(rs.next()) 
            	System.out.println(rs.getString("TABLE_NAME"));
            
            System.out.println("*******");
            System.out.println("*******");
            System.out.println("*******");
            
            // Voy a imprimir la información de la tabla cliente
            rs = databaseMetaData.getColumns(null, "MiUSUARIO", "CLIENTE", null);
            
            System.out.println("Las columnas asociadas a la tabla cliente son:");
            while(rs.next()) {
            	System.out.println("*******");
            	System.out.println("- Nombre: " + rs.getString("COLUMN_NAME"));
            	System.out.println("- Tipo: " + rs.getString("DATA_TYPE"));
            	System.out.println("- Tamaño: " + rs.getString("COLUMN_SIZE"));
            	//System.out.println("- Autoicrement?: " + rs.getString("IS_AUTOINCREMENT"));
            }
            
            
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            /*
             * Cierra el objeto que representa el resultado de la consulta
             */
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                JdbcUtils.printSQLException(e);
            }
            
            /*
             * Cierra el objeto que representa la consulta SQL
             */
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                JdbcUtils.printSQLException(e);
            }
        }
    }

    /**
     * Ejemplo de uso de las clases de JDBC para lanzar una consulta SELECT contra el SGBD.
     * 
     * @param conn {@link Connection} con el SGBD que se utiliza para lanzar la consulta.
     */
    private void ejecutaConsulta(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Crea el objeto que representa la consulta SQL
            stmt = conn.createStatement();
            
            // Ejecuta la consulta SQL
            //stmt.execute("SELECT 2+2 FROM DUAL");
            
            stmt.execute("SELECT * from OFERTA");
            
            // Obtiene el objeto ResultSet, que representa el resultado de la consulta SQL
            rs = stmt.getResultSet();
            
            // Obetenemos la metainformación de la respuesta
            ResultSetMetaData md = rs.getMetaData(); 
            
            int numCol = md.getColumnCount();
            System.out.println("Número de columnas: " + numCol);
            
            for(int i = 1; i <= numCol; i++) { // ¡OJO! - Las columnas en JDBC están numeradas desde el 1
            	System.out.println("Columna " + (i));
            	System.out.println("\t- Label: " + md.getColumnLabel(i));
            	System.out.println("\t- Type: " + md.getColumnTypeName(i));
            	
            	if (md.getColumnTypeName(i).equalsIgnoreCase("CHAR"))
            		System.out.println("\t- Size: " + md.getColumnDisplaySize(i));
            	
            	System.out.println("\t- Autoicrement?: " + md.isAutoIncrement(i));
            	System.out.println("\t- Nullable: " + md.isNullable(i));
            }
            
            
            // Recorre y muestra el resultado de la consulta
            /*while (rs.next()) {
               System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) );
            }*/
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            /*
             * Cierra el objeto que representa el resultado de la consulta
             */
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                JdbcUtils.printSQLException(e);
            }
            
            /*
             * Cierra el objeto que representa la consulta SQL
             */
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                JdbcUtils.printSQLException(e);
            }
        }
    }

    /**
     * Crea una {@link Connection} con el SGBD.
     * 
     * @return la {@link Connection} al SGBD.
     * 
     * @throws SQLException
     *             si falla la conexión con el SGBD.
     */
    private Connection getConnectionFromDataSource() throws SQLException {
        /*
         * Los datos de la conexión se cargan de un archivo
         */
        Properties properties = PropertiesUtils.cargaProperties("database.properties");

        OracleDataSource ds = new OracleDataSource();
        ds.setURL(properties.getProperty("jdbc.url"));
        ds.setUser(properties.getProperty("jdbc.username"));
        ds.setPassword(properties.getProperty("jdbc.password"));

        Connection conn = ds.getConnection();

        return conn;
    }

}
