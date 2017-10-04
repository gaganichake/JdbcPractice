package gagan.practice.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

public class BatchJob {

	private int columns;
	private List<BatchEntry> batchEntries;	
	private int[] targetSqlType;
	

	public List<BatchEntry> getBatchEntries() {
		return batchEntries;
	}
	
	public int[] getTargetSqlType() {
		return targetSqlType;
	}
	
	public BatchJob(int columns){
		
		this.columns = columns;
		this.targetSqlType = new int[columns];
		this.batchEntries = new ArrayList<BatchEntry>();
	}
	
	public class BatchEntry {

		Object[] entry = null;
		
		public BatchEntry(){
			this.entry = new Object[columns];
		}

		public Object[] getEntry() {
			return entry;
		}

		public void setEntry(Object[] entry) {
			this.entry = entry;
		}
	}
}
