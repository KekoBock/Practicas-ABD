package es.ucm.fdi.bd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

class ejemplosbatchenJDBC
{
/* ------- TROZO CODIGO DE EJEMPLO PARA USAR procesamiento en lotes o  BATCH 


/** vas a�adiendo cada Insert usando addBatch().
    Cuando quieres ejecutar todo, llamas a la funci�n executeBatch().
    RECORDAR que hay que deshabilitar el autocommit y HACER el commit al final.
    SE EVITA  hacer conexiones cada instrucci�n. 
*/

	private void lolita() throws SQLException {
		// ---------------- PREPARESTATEMENT
		Connection dbConnection = null;
		
		dbConnection.setAutoCommit(false);// commit trasaction manually

		String insertTableSQL = "INSERT INTO DBUSER" + "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
				+ "(?,?,?,?)";
		PreparedStatement prepStmt = dbConnection.prepareStatement(insertTableSQL);

		prepStmt.setInt(1, 101);
		prepStmt.setString(2, "mkyong101");
		prepStmt.setString(3, "system");
		prepStmt.setTimestamp(4, getCurrentTimeStamp());
		prepStmt.addBatch();

		prepStmt.setInt(1, 102);
		prepStmt.setString(2, "mkyong102");
		prepStmt.setString(3, "system");
		prepStmt.setTimestamp(4, getCurrentTimeStamp());
		prepStmt.addBatch();
		prepStmt.executeBatch();

		dbConnection.commit();

		// ---------------- STATEMENT

		// Esta forma tambi�n est� en el objeto Statement. EJEMPLO:

		// Create statement object
		Statement stmt = dbConnection.createStatement();

		// Set auto-commit to false
		dbConnection.setAutoCommit(false);

		// Create SQL statement
		String SQL = "INSERT INTO Employees (id, first, last, age) " + "VALUES(200,'Zia', 'Ali', 30)";
		// Add above SQL statement in the batch.
		stmt.addBatch(SQL);

		// Create one more SQL statement
		String SQL2 = "INSERT INTO Employees (id, first, last, age) " + "VALUES(201,'Raj', 'Kumar', 35)";
		// Add above SQL statement in the batch.
		stmt.addBatch(SQL2);

		// Create one more SQL statement
		String SQL3 = "UPDATE Employees SET age = 35 " + "WHERE id = 100";
		// Add above SQL statement in the batch.
		stmt.addBatch(SQL3);

		// Create an int[] to hold returned values
		int[] count = stmt.executeBatch();

		// Explicitly commit statements to apply changes
		dbConnection.commit();

	}

	private Timestamp getCurrentTimeStamp() {
		// TODO Auto-generated method stub
		return null;
	}
}