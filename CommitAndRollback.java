package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommitAndRollback {
	
	private static final String URL = "jdbc:mysql://localhost:3306/mydb";
	private static final String USER = "xxx";
	private static final String PASSWORD = "xxx";
	private static Connection connection;
	
	public static void main(String[] args) throws SQLException {
		try {
			
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			// change auto commit status false
			connection.setAutoCommit(false);
			
			// Execute update query 
			updateQuery();
			
			if(isCurrentActiveTransaction()) {
				System.out.println("There is an active transaction.");
				System.out.println("Nothing will be printed in the database.");
			} else {
				System.out.println("There is no active transaction.");
				connection.commit();
				System.out.println("Commit successful.");
			}
			
			// Commit - is needed, because we set AutoCommit to false
			//connection.commit();
			//System.out.println("Commit successful!");
			
		} catch(Exception e) {
			try {
				// rollback
				connection.rollback();
				System.out.println("Rolling back!");
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			connection.close();
		}
		

	}
	
	private static void updateQuery() throws SQLException {
		
		String sql = "INSERT INTO Employees2 (Name, Age) VALUES ('Alf', 77)";
		
		try(Statement statement = connection.createStatement()) {
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			throw e;
		}
	}
	
	private static boolean isCurrentActiveTransaction() throws SQLException {
		
		String sql = "SELECT COUNT(1) AS count "
				+ "FROM INFORMATION_SCHEMA.INNODB_TRX "
				+ "WHERE trx_mysql_thread_id = CONNECTION_ID()";
		
		try(Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			if(resultSet.next()) {
				int count = resultSet.getInt("count");
				return count > 0;
			}
		}
		
		return false;
	}

}
