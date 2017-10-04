package gagan.practice.blob;

import gagan.practice.dao.jdbc.JdbcTemplate;
import gagan.practice.jaxb.beans.generated.CandidateSearchCriteria;
import gagan.practice.util.BlobConverter;
import gagan.practice.util.JaxbUtil;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

/**
 * @author Gagan
 *
 */
public class HelloWorld {
	
	public static void main(String arg[]){

		CandidateSearchCriteria searchCriteria = new CandidateSearchCriteria();		
		searchCriteria.setCity("pune");
		searchCriteria.getTechSkill().add("java");
		searchCriteria.getTechSkill().add("hibernate");
		searchCriteria.getTechSkill().add("javascript");
		searchCriteria.setMinExp(new BigInteger("2"));
		searchCriteria.setMaxExp(new BigInteger("5"));
		
		// Get XML from Object
		String searchCriteriaXML = null;
		try {
			searchCriteriaXML = JaxbUtil.generateXMLStringFromObject(CandidateSearchCriteria.class, searchCriteria);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		System.out.println("CandidateSearchCriteria XML :\n" + searchCriteriaXML);
		
		Blob blob = BlobConverter.covertStringToBlob(searchCriteriaXML);
		
		//Save the XML into database
		JdbcTemplate jdbcTemplate = new JdbcTemplate("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test", "root", "root");
		
		String searchCriteriaXMLFromBlob = null;
		
		try {
			Connection connection = jdbcTemplate.getConnection();
			
			//SQL query
			String sql = "insert into search_criteria (criteria_blob) values(?)";
			//columnIndexes an array of column indexes indicating the columns that should be returned from the inserted row or rows 
			int[] columnIndexes = new int[]{1};
			
			PreparedStatement preparedStatement  = connection.prepareStatement(sql, columnIndexes);
			preparedStatement.setBlob(1, blob);			
			preparedStatement.execute();			
			System.out.println("Save Successful");
			
			ResultSet keyResultSet = preparedStatement.getGeneratedKeys();
			ResultSet resultSet = null;
			
			if(keyResultSet.next()){
				resultSet = preparedStatement.executeQuery("select criteria_blob from search_criteria where id="+keyResultSet.getInt(1));
			}
			
			Blob blobFromDatabase = null;
			
			if(resultSet.next()){
				
				System.out.println("Getting Result Set");
				blobFromDatabase = resultSet.getBlob(1);
			}
			
			searchCriteriaXMLFromBlob = BlobConverter.covertBlobToString(blobFromDatabase.getBinaryStream());
			
			keyResultSet.close();
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// Get Object from XML
		CandidateSearchCriteria searchCriteriaFromBlob = null;
		try {
			searchCriteriaFromBlob = (CandidateSearchCriteria)JaxbUtil.generateObjectFromXMLString(CandidateSearchCriteria.class, searchCriteriaXMLFromBlob);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		System.out.println("CandidateSearchCriteria Object :\n" +
				", City=" + searchCriteriaFromBlob.getCity() + 
				", TechSkill=" + searchCriteriaFromBlob.getTechSkill() +
				", MinExp=" + searchCriteriaFromBlob.getMinExp() + 
				", MaxExp=" + searchCriteriaFromBlob.getMaxExp());
	}

}
