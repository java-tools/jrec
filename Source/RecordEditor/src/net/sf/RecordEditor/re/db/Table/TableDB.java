package net.sf.RecordEditor.re.db.Table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.sf.RecordEditor.utils.jdbc.AbsDB;
//import net.sf.RecordEditor.utils.jdbc.AbsRecord;



/**
 * This class provides DB Access using
 *
 *   <pre>
 *       Select TblKey, Details
 *       From   Tbl_TI_IntTbls
 *       Where  TblId = ?
 *       Order By TblKey
 *
 *   </pre>
 * it also provides Insert / Update / Delete routines (depending on the options selected)
 *
 * @Author Generated by BuildJava.Rexx by Bruce Martin
 */

public final class TableDB  extends AbsDB<TableRec> {


  private static final String[] COLUMN_NAMES = {
                   "Row Key"
                 , "Details"
  };


  private int paramTblId;

  public TableDB() {

      resetSearch();

      sSQL = " Select  TblKey, Details";
      sFrom = "  From   Tbl_TI_IntTbls";
      sWhereSQL = "  Where  TblId = ?";
      sOrderBy = "  Order By TblKey";
      updateSQL = "Update Tbl_TI_IntTbls  "
                   +  " Set TblKey= ? "
                   +  "   , Details= ? "
                   +  " Where TblId= ? "
                   +  "   and TblKey= ? "
                        ;

      deleteSQL = "Delete From  Tbl_TI_IntTbls  "
                   +  " Where TblId= ? "
                   +  "   and TblKey= ? "
                        ;

      insertSQL = "Insert Into  Tbl_TI_IntTbls  ("
                      + "    TblKey"
                      + "  , Details"
                      + "  , TblId"
                      + ") Values ("
                      +    "     ?   , ?   , ?"
                      + ")";

      super.columnNames = TableDB.COLUMN_NAMES;
  }


  /**
   * sets up the DB parameters
   *
   * @param TblId
   *
   */
  public void setParams( int TblId) {

      paramTblId = TblId;
  }

  /**
   *  This method opens a SQL query
   */
  public void open() {

      prepareCursor();

      try {
          sqlCursor.setInt(1, paramTblId);

          setStringArgs(1);


          rsCursor  = sqlCursor.executeQuery();
          message = "";
      } catch (Exception ex) {
           setMessage(ex.getMessage(), ex);
      }
  }


  /**
   *  This method returns the next record (AbsRecord) from the cursor
   */
 // public AbsRecord absFetch() {
 //     return fetch();
 // }


  /**
   *  This method returns the next record from the cursor
   */
  public TableRec fetch() {
  TableRec ret = null;

      try {
          if (rsCursor.next()) {
             ret = new TableRec(
                        rsCursor.getInt(1)
                      , rsCursor.getString(2)
                   );
          }
          message = "";
      } catch (Exception ex) {
           setMessage(ex.getMessage(), ex);
      }

      return ret;
  }


  /**
   *  Get the number of columns returned by the SQL
   */
  public int getColumnCount() {
      return 2;
  }





  /**
   *
   * @param statement statement that needs parameteres set
   * @param value Value to assign to the Statement parameters
   * @param idx parameter index
   *
   * @return updated index
   * @throws SQLException SQL error
   */
  protected int setSQLParams(PreparedStatement statement, TableRec value, boolean insert, int idx)
                             throws SQLException {
 
	  statement.setInt(idx++, value.getTblKey());
      statement.setString(idx++, correctStr(value.getDetails()));

      if (insert) {
    	  statement.setInt(idx++, paramTblId);
      }

      return idx;
  }


  /**
   * Setup the where parameters
   *
   * @param statement SQL statement
   * @param value Value to assign to the Statement parameters
   * @param idx current index
   * @throws SQLException SQL error
   */
  protected void setWhere(PreparedStatement statement, TableRec value, int idx)
                          throws SQLException {

      statement.setInt(idx++, paramTblId);
      statement.setInt(idx, value.initTblKey);
  }

}