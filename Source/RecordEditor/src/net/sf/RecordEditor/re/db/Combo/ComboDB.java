package net.sf.RecordEditor.re.db.Combo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.jdbc.AbsDB;

/**
 * This class provides DB Access using
 *
 *   <pre>
 *       Select Combo_Id,
 *              System,
 *              Combo_Name,
 *              Column_Type
 *       From   Tbl_C_Combos
 *
 *   </pre>
 * it also provides Insert / Update / Delete routines (depending on the options selected)
 *
 * @Author Generated by BuildJava.Rexx by Bruce Martin
 */

public class ComboDB  extends AbsDB<ComboRec> {


  private static final String[] COLUMN_NAMES = {
                   "System"
                 , "Combo_Name"
  };

  private PreparedStatement getMaxKey = null;
  protected PreparedStatement getSql = null;


  public ComboDB() {

      resetSearch();

      sSQL = " Select  Combo_Id, System, Combo_Name, Column_Type";
      sFrom = "  From   Tbl_C_Combos";
      sWhereSQL = " ";
      sOrderBy = " ";
      updateSQL = "Update Tbl_C_Combos  "
                   +  " Set Combo_Id= ? "
                   +  "   , System= ? "
                   +  "   , Combo_Name= ? "
                   +  "   , Column_Type= ? "
                   +  " Where Combo_Id= ? " 
                        ;

      deleteSQL = "Delete From  Tbl_C_Combos  "
                   +  " Where Combo_Id= ? " 
                        ;

      insertSQL = "Insert Into  Tbl_C_Combos  ("
                      + "    Combo_Id"
                      + "  , System"
                      + "  , Combo_Name"
                      + "  , Column_Type"
                      + ") Values ("
                      +    "     ?   , ?   , ?   , ?"
                      + ")";

      super.columnNames = ComboDB.COLUMN_NAMES;
  }


//  /**
//   *  This method returns the next record (AbsRecord) from the cursor
//   */
//  public AbsRecord absFetch() {
//      return fetch();
//  }


  /**
   *  This method returns the next record from the cursor
   */
  public ComboRec fetch() {
	  return fetch(rsCursor);
  }
  
  /**
   * get the next cursor entry
   * @param results SQL resultset
   * @return record
   */
  public ComboRec fetch(ResultSet results) {
  ComboRec ret = null;

      try {
          if (results.next()) {
             ret = new ComboRec(
                        results.getInt(1)
                      , results.getInt(2)
                      , results.getString(3)
                      , results.getInt(4)
                   );
          }
          message = "";
      } catch (Exception ex) {
           setMessage(ex.getMessage(), ex);
      }

      return ret;
  }
  
  
  public ComboRec get(String name) {
	  String sql = sSQL + sFrom + " Where Combo_Name = ? ;";
	  ComboRec ret = null;
	  ResultSet rs = null;
	  
	  try {
		  if (isPrepareNeeded(getSql)) {
			  getSql = connect.getConnection().prepareStatement(sql);
		  }
		  getSql.setString(1, correctStr(name));
		  
		  rs = getSql.executeQuery();
		  
		  try {		  
			  ret = fetch(rs);
		  } finally {
			  rs.close();
		  }
		  
	  } catch (Exception ex) {
		  setMessage(sql, ex.getMessage(), ex);
	  } finally {
		  freeConnection();
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
  protected int setSQLParams(PreparedStatement statement, ComboRec value, boolean insert, int idx)
                             throws SQLException {

      statement.setInt(idx++, value.getComboId());
      statement.setInt(idx++, value.getSystem());
      statement.setString(idx++, correctStr(value.getComboName()));
      statement.setInt(idx++, value.getColumnType());


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
  protected void setWhere(PreparedStatement statement, ComboRec value, int idx)
                          throws SQLException {

      statement.setInt(idx++, value.initCombo_Id);
  }

  
  /**
   *  This method inserts one record
   *
   *  @param val value to be inserted
   */
  public void insert(ComboRec value) {
      //RecordRec val = (RecordRec) value;

      int i = 0;
      int key = getNextKey();

      value.setComboId(key++);
      while ((i++ < 10) && (! tryToInsert(value))) {
          value.setComboId(key++);
      }
  }

  /**
   * This method gets the next key
   */
  private int getNextKey() {
      final String sql = "Select max(Combo_Id) From Tbl_C_Combos "
                 ;
      int ret = 1;

      try {
          if (isPrepareNeeded(getMaxKey)) {
              getMaxKey = connect.getConnection().prepareStatement(sql);
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
  
	public void fullClose() {
		super.fullClose();
		
		closeStatement(getMaxKey);
		closeStatement(getSql);
	}

	

	
	  /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.AbsDB#delete(net.sf.RecordEditor.utils.jdbc.AbsRecord)
	 */
	@Override
	public void delete(ComboRec val) {

		String updSql  = 
				  "Delete from  Tbl_CI_ComboItems "
				 + "where Combo_Id = "  + val.getComboId();

		try {
			connect.getUpdateConnection().createStatement().execute(updSql);
		} catch (Exception e) {
			Common.logMsg(updSql, null);
			Common.logMsg("Update Failed: " + e.getClass().getName() + " " + e.getMessage(), e);
			e.printStackTrace();
		}
		
		
		super.delete(val);
	}


	/**
	   *  This method sets a search argument for RecordType
	   *
	   * @param operator operator to be used in the where clause
	   * @param val value to be used in the search
	   */
	  public void setSearchComboName(String operator, String val) {

		  setSearchStrArg("Combo_Name", operator, val);
	  }

	  
		
	  /**
	   *  This method sets a search argument for RecordType
	   *
	   * @param operator operator to be used in the where clause
	   * @param val value to be used in the search
	   */
	  public void setSearchSystem(String operator, int val) {

	      sWhere = sWhere + sep + "System" + operator + val;
	      sep = "   and ";

	  }

	  public void resetSearch() {
		   
		   super.resetSearch();

		   sep = " Where ";    
	  }

}
