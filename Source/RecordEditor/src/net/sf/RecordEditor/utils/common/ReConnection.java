package net.sf.RecordEditor.utils.common;

import java.sql.Connection;

public class ReConnection implements AbsConnection {

	private int dbIndex;
	
	public ReConnection(int dbId) {
		dbIndex = dbId; 
	}
	
	@Override
	public void free() {
		Common.freeConnection(dbIndex);
	}

	@Override
	public Connection getConnection() {
		return Common.getDBConnectionLogErrors(dbIndex);
	}

	@Override
	public Connection getUpdateConnection() {

		return Common.getUpdateConnection(dbIndex);
	}

	@Override
	public boolean isTempUpdateConnection() {

		return false;
	}

}
