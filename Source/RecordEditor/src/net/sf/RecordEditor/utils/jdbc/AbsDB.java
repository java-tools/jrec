/*
 * Created on 17/08/2004
 *
 * Basic Database Access Class
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Added support fr or in SQL
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - JRecord creation changes
 *   - return " " instead of name forthe first column
 *     to overcome problem in some LookAndFeels
 */
package net.sf.RecordEditor.utils.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;


/**
 * Base for DB access routines
 *
 * @author Bruce Martin
 */
public abstract class AbsDB<record extends AbsRecord> {

	public static final String opLike = " Like ";
	public static final String opEquals = "=";
	public static final String opNE = "<>";
	public static final String opLT = "<";
	public static final String opLE = "<=";
	public static final String opGT = ">";
	public static final String opGE = ">=";
	public static final String opIn = " in ";

	public static final String nullStr = "";
	public static final byte   nullBytes[] = {};
	protected static final String UPDATE_FAILED = LangConversion.convert("Update Failed:");

//	public String sqlID;

	protected AbsConnection connect;

	protected String message = nullStr;

	protected String sSQL;
	protected String sFrom  = nullStr;
	protected String sWhereSQL = nullStr;
	protected String sWhere = nullStr;
	protected String sOrderBy = nullStr;

	protected String updateSQL = nullStr;
	protected String deleteSQL = nullStr;
	protected String insertSQL = nullStr;

	protected String sep = " Where ";
//	protected String orderBy = nullStr;
//	protected    int numParms = 0;

	private int readLimit = 2000000000;


	private static AbsSSLogger systemLog   = null;
	private AbsSSLogger functionLog = null;

	private boolean autoClose = false;

	protected String[] columnNames = null;
	protected int[]    columnTypes;
	protected int      numCols = 0;


	protected PreparedStatement sqlCursor = null;
	protected PreparedStatement updateStatement = null;
	protected PreparedStatement deleteStatement = null;
	protected PreparedStatement insertStatement = null;
	private PreparedStatement getMaxKey = null;
	protected ResultSet rsCursor;

	private ArrayList<String> parmList = new ArrayList<String>();

	private boolean doFree = true;


	/**
	 *  This method prepares the SQL statement for retrieval
	 *
	 */
	protected void prepareCursor() {

	    if (isPrepareNeeded(sqlCursor)) {
	        try {
	            sqlCursor = connect.getConnection().prepareStatement(
	                          sSQL
	                        + sFrom
	                        + sWhereSQL
	                  	    + sWhere
	                  	    + sOrderBy
	                       );
	            message = "";
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            System.out.println("prepare Cursor >> " + (connect == null));
	            setMessage(ex.getMessage(), ex);
//	        } finally {
//	        	connect.free();
	        }
	    }
	}

	/**
	 * Check if new prepare is needed
	 * @param statement statment to check
	 * @return wether to rebuild
	 */
	protected final boolean isPrepareNeeded(PreparedStatement statement) {
		boolean ret = true;

		try {
			if (statement != null
			&& statement.getConnection() != null) {
				ret = statement.getConnection().isClosed();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}


		return ret;
	}

	/**
	 *  This method opens a SQL query
	 */
	public void open() {

	    prepareCursor();

	    try {
	        setStringArgs(0);
	        rsCursor  = sqlCursor.executeQuery();
	        message = "";
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	        setMessage(ex.getMessage(), ex);
	    }
	}


	 /**
	   *  This method updates one record
	   *
	   *  @param val value to be updated
	   */
	 public void update(record val) {

	     if (nullStr.equals(updateSQL)) {
	         return;
	     }

	     try {
	    	 doUpdate(val);

	         message = "";
	         val.setUpdateStatus(AbsRecord.UNCHANGED);
	     } catch (Exception ex) {
	         setMessage(updateSQL, ex.getMessage(), ex);
	        // ex.printStackTrace();
	     }
	 }


	 protected final void doUpdate(record val) throws Exception {
	     int idx;

	     //TODO
	    try {
	         if (isPrepareNeeded(updateStatement)) {
	             updateStatement = connect.getUpdateConnection().prepareStatement(updateSQL);
	         }

	         idx = setSQLParams(updateStatement, val, false, 1);
	         setWhere(updateStatement, val, idx);

	         updateStatement.executeUpdate();
	    } catch (Exception e) {
	    	e.printStackTrace();
			throw e;
		} finally {
	         if (connect.isTempUpdateConnection()) {
	        	 updateStatement = null;
	        	 freeConnection();
	         }
		}

	 }



	 /**
	   * This method updates one record
	   *
	   * @param val value to be updated
	   *
	   * @return wether the record was inserted or not
	   */
	 public final boolean tryToInsert(record val) {

	     if (nullStr.equals(insertSQL)) {
	         return false;
	     }

	     try {
	         if (isPrepareNeeded(insertStatement)) {
	             insertStatement = connect.getUpdateConnection().prepareStatement(insertSQL);
	         }

	         setSQLParams(insertStatement, val, true, 1);

	         insertStatement.executeUpdate();
	         message = "";
	         val.setUpdateStatus(AbsRecord.UNCHANGED);
	         val.setNew(false);
	     } catch (Exception ex) {
	         setMessage(insertSQL, ex.getMessage(), ex);
	         return false;
	     } finally {
	         if (connect.isTempUpdateConnection()) {
	        	 insertStatement = null;
	        	 freeConnection();
	         }
	     }
	     return true;
	 }


	 /**
	   *  This method updates one record
	   *
	   *  @param val value to be updated
	   */
	 public void insert(record val) {
		 tryToInsert(val);
	 }

	 /**
	   *  This method deletes one record
	   *
	   *  @param val value to be deleted
	   */
	 public void delete(record val) {
	     if (nullStr.equals(deleteSQL)) {
	         return;
	     }

	     try {
	         if (isPrepareNeeded(deleteStatement)) {
	             deleteStatement = connect.getUpdateConnection().prepareStatement(deleteSQL);
	         }

	         setWhere(deleteStatement, val, 1);

	         deleteStatement.executeUpdate();

	         message = "";
	         val.setUpdateStatus(AbsRecord.UNCHANGED);
	     } catch (Exception ex) {
	         setMessage(deleteSQL, ex.getMessage(), ex);
	     } finally {
	         if (connect.isTempUpdateConnection()) {
	        	 deleteStatement = null;
	        	 freeConnection();
	         }
	     }
	 }



	 /**
	  *
	  * @param statement statement that needs parameteres set
	  * @param val Value to assign to the Statement parameters
	  * @param insert wether this is an insert
	  * @param idx parameter index
	  *
	  * @return updated index
	  * @throws SQLException SQL error
	  */
	 protected int setSQLParams(PreparedStatement statement,
			 					record val,
	         					boolean insert,
	         					int idx)
						throws SQLException {
	    return idx;
	 }


	 /**
	  * Setup the where parameters
	  *
	  * @param statement SQL statement
	  * @param val Value to assign to the Statement parameters
	  * @param idx current index
	  *
	  * @throws SQLException SQL error
	  */
	 protected void setWhere(PreparedStatement statement, record val, int idx)
							throws SQLException  {
	 }


	/**
	 * Set String Parameters in the sql Cursor
	 *
	 * @param start which parameter to start assigniong
	 *
	 * @throws SQLException any SQL errors experienced
	 */
	public void setStringArgs(int start) throws SQLException {
	    int i;

	    for (i = 0; i < parmList.size(); i++) {
	        sqlCursor.setString(++start, parmList.get(i));
	    }
	}


	/**
	 *  This method closes the cursor
	 */
	public void close() {

	    rsCursor  = null;

	    if (autoClose) {
	        fullClose();
	    }
	    freeConnection();
	}

	public void freeConnection() {
		if (doFree && connect != null) {
			connect.free();
		}
	}



	/**
	 * Close the prepared statment
	 *
	 */
	public void fullClose() {

	    closeStatement(sqlCursor);
	    closeStatement(updateStatement);
	    closeStatement(deleteStatement);
	    closeStatement(insertStatement);
	    closeStatement(getMaxKey);

	    rsCursor  = null;
	}


	/**
	 * Close a statement saftly - ignore any errors
	 *
	 * @param pStatement statement to close
	 */
	protected void closeStatement(PreparedStatement pStatement) {

         if (pStatement != null) {
             try {
                pStatement.close();
             } catch (Exception ex) {
                setMessage(ex.getMessage(), ex);
             }
         }
    }


	/**
	 * Set the Database Conection
	 *
	 * @param con Database connection
	 */
	public void setConnection(AbsConnection con) {
		connect = con;
	}


	/**
	 * This method resets the search Arguments
	 *
	 */
    public void resetSearch() {

        parmList.clear();
 	    sWhere = nullStr;
	    sep = " And ";
	    //numParms = 0;

	    if (sqlCursor != null) {
		    try {
		    	sqlCursor.close();
		    } catch (Exception e) {
			}
	    }
	    sqlCursor = null;
	}


    /**
     * This method stores a string parameter
     *
     * @param field field being checked
     * @param operator operator being applied
     * @param val value being checked for
     */
    protected void setSearchStrArg(String field, String operator, String val) {

        parmList.add(val);
        sWhere = sWhere + sep + field + operator + "?";
        sep = " and ";

    }


    /**
     * Add Generic search argument
     *
     * @param field     DB field
     * @param operator  operator
     * @param val       value to search for
     */
    public void setSearchArg(String field, String operator, String val) {

        sWhere = sWhere + sep + field + operator + val;
        sep = " and ";
       // System.out.println(">> " + sWhere);

    }



    /**
     * This method stores a string parameter
     *
     * @param field field being checked
     * @param operator operator being applied
     * @param val value being checked for
     */
    protected void setSearchArg(String field, String operator, int val) {

        sWhere = sWhere + sep + field + operator + val;
        sep = " and ";

    }


    /**
     * Assigns Column details using a SQL result set's meta data
     *
     * @param rsResult result set to retrieve meta data from
     */
    protected void setColumnDetails(ResultSet rsResult) {
    	int i;

    	try {
    		ResultSetMetaData meta = rsResult.getMetaData();
    		numCols = meta.getColumnCount();
    		columnNames = new String[numCols];
    		columnTypes = new int[numCols];

    		for (i = 1; i <= numCols; i++) {
    			columnNames[i - 1] = meta.getColumnName(i);
    			columnTypes[i - 1] = meta.getColumnType(i);
    		}
    	} catch (Exception ex) {
    		setMessage(ex.getMessage(), ex);
    	}
    }


//	/**
//	 * Fetch one record from the cursor
//	 * @return Record Fetched
//	 * @deprecated no longer needed
//	 */
//	public AbsRecord absFetch() {
//		return fetch();
//	}

	/**
	 * Fetch one record from the cursor
	 * @return Record Fetched
	 */
	public abstract record fetch();


	/**
	 * Get requested record
	 *
	 * @return request record
	 *
	 * @deprecated not used
	 */
	public final record absGet() {
		return null;
	}



	/**
	 * Check to see if the record has been changed before updating the
	 * DB (if necessary
	 *
	 * @param val Updated record
	 */
	public void checkAndUpdate(record val) {

		if (val.isNew()) {
		    //System.out.println("~~~> inserting ");
			if  (val.getUpdateStatus() !=  AbsRecord.BLANK_RECORD) {
				insert(val);
			}
		} else if (val.getUpdateStatus() == AbsRecord.UPDATED) {
		    //System.out.println("~~~> updating ");
			update(val);
		}
	}


	/**
	 * Retrieve all records from a cursor as a array list
	 *
	 * @return All records in the cursor
	 */
	public ArrayList<record> fetchAll() {
		int i = 0;
		ArrayList<record> ret = new ArrayList<record>();
		open();
		record rec = fetch();

		while ((i++ < readLimit) && (rec != null)) {
			ret.add(rec);
			rec = fetch();
		}

		close();

		return ret;
	}


	/**
	 * Get the number of columns
	 *
	 * @return number of columns
	 */
	public int getColumnCount() {
	    return 0;
	}


	/**
	 * The class of a column
	 *
	 * @param defaultClass default class to be used no other class is known
	 * @param column column the user wants the class for
	 *
	 * @return columns class
	 */
	@SuppressWarnings({ "rawtypes" })
	public Class getColumnClass(Class defaultClass, int column) {

		return defaultClass;
	}

	/**
	 * Get a  column name
	 *
	 * @param column column to get the name for
	 * @return column name
	 */
	public String getColumnName(int column) {

	    if (columnNames == null || column < 0 || column >= columnNames.length) {
	        return " ";
	    }

	    return columnNames[column];
	}


	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return Returns the readLimit.
	 */


	/**
	 * get the read limit (ie the maximum number of records to be read)
	 *
	 * @return The current Read Limit
	 */
	public int getReadLimit() {
		return readLimit;
	}

	/**
	 * @param newReadLimit The readLimit to set.
	 */
	public void setReadLimit(int newReadLimit) {
		this.readLimit = newReadLimit;
	}


	/**
	 * Set the error message
	 *
	 * @param pMessage The message to set.
	 * @param ex Exception recieved
	 */
	public void setMessage(String pMessage, Exception ex) {
		this.message = pMessage;

        setMessageInt("SQL:\n" + sSQL + "\n"
                	+ sFrom + "\n"
                	+ sWhereSQL + sWhere + "\n" + sOrderBy, null);
		setMessageInt(pMessage, ex);
	}


	/**
	 * Set the error message
	 *
	 * @param sql SQL to be printed
	 * @param pMessage The message to set.
	 * @param ex Exception recieved
	 */
	public void setMessage(String sql, String pMessage, Exception ex) {
		this.message = pMessage;

        setMessageInt(sql, null);
		setMessageInt(pMessage, ex);
	}



	/**
	 * Internal set message
	 *
	 * @param pMessage The message to set.
	 * @param ex Exception recieved
	 */
	private void setMessageInt(String pMessage, Exception ex) {

		if (pMessage != null &&  (! nullStr.equals(pMessage))) {
			if (functionLog != null) {
				functionLog.logMsg(AbsSSLogger.ERROR, pMessage);
			}

			if (systemLog != null) {
				systemLog.logMsg(AbsSSLogger.ERROR, pMessage);
			}
		}

		if (ex != null) {
			if (functionLog != null) {
				functionLog.logException(AbsSSLogger.ERROR, ex);
			}

			if (systemLog != null) {
				systemLog.logException(AbsSSLogger.ERROR, ex);
			}
		}
	}


	/**
	 * Assign Function log
	 *
	 * @param newFunctionLog The functionLog to set.
	 */
	public void setFunctionLog(AbsSSLogger newFunctionLog) {
		this.functionLog = newFunctionLog;
	}


	/**
	 * Assign a System wide log
	 *
	 * @param newSystemLog The systemLog to set.
	 */
	public static void setSystemLog(AbsSSLogger newSystemLog) {
		AbsDB.systemLog = newSystemLog;
	}


	/**
	 * @return Returns the systemLog.
	 */
	public static AbsSSLogger getSystemLog() {
		return systemLog;
	}


	/**
	 * Corrects a string (ie null is converted to "")
	 *
	 * @param s String to Test
	 *
	 * @return Corrected String
	 */
	public String correctStr(String s) {
		return s == null ? nullStr
		        		 : s;
	}


	/**
	 * Ensure a byte array is not null
	 *
	 * @param b byte array to check
	 * @return non null byte array
	 */
	public byte[] correctBytes(byte[] b) {
		return b == null ? nullBytes
		        		 : b;
	}


    /**
     * Set the SQL order by fields
     *
     * @param newOrderBy The orderBy to set.
     */
    public void setOrderBy(String newOrderBy) {

        if (newOrderBy == null || "".equals(newOrderBy)) {
        	sOrderBy = "";
        } else {
            this.sOrderBy = newOrderBy;
        }
    }


    /**
     * Assign Auto Close (ie do full close automatically)
     *
     * @param newAutoClose The autoClose to set.
     */
    public void setAutoClose(boolean newAutoClose) {
        this.autoClose = newAutoClose;
    }


    /**
     * indicates wether SQL is ready to be retrieved (ie connection set)
     *
     * @return wether the DB is ready for access
     */
    public boolean isReady() {
        return connect != null;
    }

    /**
     * @param operator The SQL operator to set (And / OR).
     */
    public final void setSqlOperator(String operator) {
        this.sep = operator;
    }


    /**
     * Print the current SQL
     *
     */
    public final void printSQL() {
        System.out.println(sSQL);
        System.out.println(sFrom);
        System.out.println(sWhereSQL
          	    + sWhere
          	    + sOrderBy
        );
    }

	/**
	 * @return the doFree
	 */
	public final boolean isDoFree() {
		return doFree;
	}

	/**
	 * @return the doFree
	 */
	public final boolean isSetDoFree(boolean free) {
		boolean ret = doFree;
		doFree = free;
		return ret;
	}

	/**
	 * @param free the doFree to set
	 */
	public final void setDoFree(boolean free) {
		this.doFree = free;

		freeConnection();
	}

	/**
	 * This method gets the next key
	 */
	protected final int getNextIntSubKey(String sql, int recordId) {
		return getNextIntSubKey(sql, recordId, Integer.MIN_VALUE);
	}

	/**
	 * This method gets the next key
	 */
	protected final int getNextIntSubKey(String sql, int recordId, int key2) {
		int ret = 1;

		try {
			if (isPrepareNeeded(getMaxKey)) {
				getMaxKey = connect.getConnection().prepareStatement(sql);
			}

			getMaxKey.setInt(1, recordId);
			if (key2 != Integer.MIN_VALUE) {
				getMaxKey.setInt(1, key2);
			}

			ResultSet rsKey = getMaxKey.executeQuery();
			if (rsKey.next()) {
				ret = rsKey.getInt(1) + 1;
			}
			rsKey.close();
			message = "";
		} catch (Exception ex) {
			setMessage(sql, ex.getMessage(), ex);
		}

		return ret;
	}

	/**
	 * @return the connect
	 */
	public AbsConnection getConnect() {
		return connect;
	}

	public void rollback() {
		if (connect != null) {
			try {
				connect.getUpdateConnection().rollback();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}

	public final boolean setAutoCommit(boolean commit) {
		boolean ok = false;
		if (connect != null) {
			try {
				connect.getUpdateConnection().setAutoCommit(commit);
				ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}
}
