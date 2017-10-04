package gagan.practice.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {
	
	Connection connection = null;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public JdbcTemplate(String driverClassName, String ConnectionUrl, String user, String passsword){
		
		try {

			// Register the Jdbc MySQL driver
			Class.forName(driverClassName);

			// Obtain a connection
			this.connection = DriverManager.getConnection(
					ConnectionUrl, user, passsword);

		} catch (java.lang.ClassNotFoundException cnfE) {
			System.err.println("Exception: " + cnfE.getMessage());
		} catch (java.sql.SQLException sqlE) {
			System.err.println("Exception: " + sqlE.getMessage());
		}
		
	}
	
	public boolean batchUpdate(String sql, BatchJob batchJob, final int batchSize) throws SQLException{
		
		Connection connection = getConnection();
		
		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		 
		int count = 0;
		 
		for (BatchJob.BatchEntry batchEntry : batchJob.getBatchEntries()) {
		 
			Object[] entry = batchEntry.getEntry();
			
			for(int i = 0; i < entry.length; i++){
				
				int parameterIndex = i + 1;
				preparedStatement.setObject(parameterIndex, entry[i], batchJob.getTargetSqlType()[i]);
			}
			
			preparedStatement.addBatch();
			
		    if(++count % batchSize == 0) {
		    	preparedStatement.executeBatch(); // Execute 1000 records
		    }
		}
		preparedStatement.executeBatch(); // Execute remaining records
		preparedStatement.close();
		connection.close();		
		return true;
	}

}
