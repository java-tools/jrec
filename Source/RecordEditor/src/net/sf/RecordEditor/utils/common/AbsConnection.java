package net.sf.RecordEditor.utils.common;

import java.sql.Connection;

public interface AbsConnection {

	public Connection getConnection() ;

	public Connection getUpdateConnection() ;

	
	public boolean isTempUpdateConnection();

	public void free();

	public void checkPoint();

}
