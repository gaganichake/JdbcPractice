package gagan.practice.batchjob;

import gagan.practice.dao.jdbc.BatchJob;
import gagan.practice.dao.jdbc.JdbcTemplate;
import gagan.practice.util.ReadWriteFile;

import java.sql.SQLException;
import java.sql.Types;

public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BatchJob batchJob = new BatchJob(2);

		ReadWriteFile.readTabSaperatedFile("data\\EmployeeList.txt", batchJob);
		
		batchJob.getTargetSqlType()[0] = Types.INTEGER;
		batchJob.getTargetSqlType()[1] = Types.CHAR;
		
		int batchSize = 100;
		
		String sql = "insert into employee (id, name) values (?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test", "root", "root");
		
		try {
			jdbcTemplate.batchUpdate(sql,batchJob, batchSize);
			System.out.println("Successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
