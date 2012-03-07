package net.sf.RecordEditor.utils.Combo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.sf.RecordEditor.utils.jdbc.AbsDB;

/**
 * This class provides DB Access using
 *
 *   <pre>
 *       Select Combo_Code, Combo_Value
 *       From   Tbl_CI_ComboItems
 *       Where  Combo_Id = ?
 *       Order By Combo_Code
 *
 *   </pre>
 * it also provides Insert / Update / Delete routines (depending on the options selected)
 *
 * @Author Generated by BuildJava.Rexx by Bruce Martin
 */

public class ComboValuesDB  extends AbsDB<ComboValuesRec> {


  private static final String[] COLUMN_NAMES = {
                   "Combo Code"
                 , "Combo Value"
  };


  private int paramCombo_Id;
  
  private int columnCount = 2 ;
  
  private PreparedStatement delAllRecordFields = null;
  
  

  public ComboValuesDB() {

      resetSearch();

      sSQL = " Select  Combo_Code, Combo_Value";
      sFrom = "  From   Tbl_CI_ComboItems";
      sWhereSQL = "  Where  Combo_Id = ?";
      sOrderBy = "  Order By Combo_Code";
      updateSQL = "Update Tbl_CI_ComboItems  "
                   +  " Set Combo_Code= ? "
                   +  "   , Combo_Value= ? "
                   +  " Where Combo_Id= ? " 
                   +  "   and Combo_Code= ? " 
                        ;

      deleteSQL = "Delete From  Tbl_CI_ComboItems  "
                   +  " Where Combo_Id= ? " 
                   +  "   and Combo_Code= ? " 
                        ;

      insertSQL = "Insert Into  Tbl_CI_ComboItems  ("
                      + "    Combo_Code"
                      + "  , Combo_Value"
                      + "  , Combo_Id"
                      + ") Values ("
                      +    "     ?   , ?   , ?"
                      + ")";

      super.columnNames = ComboValuesDB.COLUMN_NAMES;
  }


  /**
   * sets up the DB parameters
   *
   * @param Combo_Id
   *
   */
  public void setParams(int Combo_Id) {

      paramCombo_Id = Combo_Id;
  }

  /**
   *  This method opens a SQL query
   */
  public void open() {

      prepareCursor();

      try {
          sqlCursor.setInt(1, paramCombo_Id);

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
//  public AbsRecord absFetch() {
//      return fetch();
//  }


  /**
   *  This method returns the next record from the cursor
   */
  public ComboValuesRec fetch() {
  ComboValuesRec ret = null;

      try {
          if (rsCursor.next()) {
             ret = new ComboValuesRec(
                        rsCursor.getString(1)
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
      return columnCount;
  }



  /**
   * @param columnCount the columnCount to set
   */
  public void setColumnCount(int columnCount) {
	  this.columnCount = columnCount;
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
  protected int setSQLParams(PreparedStatement statement, ComboValuesRec value, boolean insert, int idx)
                             throws SQLException {

      statement.setString(idx++, correctStr(value.getCombo_Code()));
      statement.setString(idx++, correctStr(value.getCombo_Value()));

      if (insert) {
    	  statement.setInt(idx++, paramCombo_Id);
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
  protected void setWhere(PreparedStatement statement, ComboValuesRec value, int idx)
                          throws SQLException {

      statement.setInt(idx++, paramCombo_Id);
      statement.setString(idx, correctStr(value.initCombo_Code));
  }
  


  /**
   *  This method deletes all records matching the parameters
   */
  public void deleteAll() {

      try {
          if (isPrepareNeeded(delAllRecordFields)) {
              delAllRecordFields = connect.getUpdateConnection().prepareStatement(
                   "Delete From  Tbl_CI_ComboItems  "
                   +  " Where Combo_Id= ? "
                        );
          }

          delAllRecordFields.setInt(1, paramCombo_Id);

          delAllRecordFields.executeUpdate();
          message = "";
      } catch (Exception ex) {
           setMessage(ex.getMessage(), ex);
      } finally {
     	 freeConnection();
      }
  }


}
