package utils.swing;

import static org.junit.Assert.*;

import javax.swing.table.TableModel;

import org.junit.Test;

import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.re.util.BuildTypeComboList;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.FieldListManager;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;

public class TstFixedWidthFieldSelection {

	private static final int LINE_LENGTH = 60;
	private static final int FIELD_NAME_COLUMN = FixedWidthFieldSelection.FIELD_NAME_COLUMN;
	private static final int POSITION_COLUMN = FixedWidthFieldSelection.POSITION_COLUMN;
	public static final int TYPE_COLUMN = FixedWidthFieldSelection.TYPE_COLUMN;
	public static final int DECIMAL_COLUMN = FixedWidthFieldSelection.DECIMAL_COLUMN;
	
	private static final String SAMPLE_FILE_DATA 
		= "123456789 123456789 123456789 123456789 123456789 \n"
		+ "123456789 123456789 123456789 123456789 123456789 \n"
		+ "123456789 123456789 123456789 123456789 123456789 \n"
		+ "123456789 123456789 123456789 123456789 123456789 \n"
		+ "123456789 123456789 123456789 123456789 123456789 \n";
	private TreeComboItem[] comboDtls = BuildTypeComboList.getTextTypes(
			new AbsRowList(0, 1, false, false).loadData(ExternalConversion.getTypes(0)));

	@Test
	public void testRule() {
		assertEquals( "-", FixedWidthFieldSelection.newRuler(1));
		assertEquals( "--", FixedWidthFieldSelection.newRuler(2));
		assertEquals( "----+", FixedWidthFieldSelection.newRuler(5));
		assertEquals( "----+---", FixedWidthFieldSelection.newRuler(8));
		assertEquals( "----+----", FixedWidthFieldSelection.newRuler(9));
		assertEquals( "----+----1", FixedWidthFieldSelection.newRuler(10));

		assertEquals( "----+----1-", FixedWidthFieldSelection.newRuler(11));
		assertEquals( "----+----1--", FixedWidthFieldSelection.newRuler(12));
		assertEquals( "----+----1----+", FixedWidthFieldSelection.newRuler(15));
		assertEquals( "----+----1----+---", FixedWidthFieldSelection.newRuler(18));
		assertEquals( "----+----1----+----", FixedWidthFieldSelection.newRuler(19));
		assertEquals( "----+----1----+----2", FixedWidthFieldSelection.newRuler(20));

		assertEquals( "----+----1----+----2-", FixedWidthFieldSelection.newRuler(21));
		assertEquals( "----+----1----+----2----+", FixedWidthFieldSelection.newRuler(25));
		assertEquals( "----+----1----+----2----+----", FixedWidthFieldSelection.newRuler(29));
		assertEquals( "----+----1----+----2----+----3", FixedWidthFieldSelection.newRuler(30));

		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10", 
				FixedWidthFieldSelection.newRuler(100));

		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10-", 
				FixedWidthFieldSelection.newRuler(101));
		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10--", 
				FixedWidthFieldSelection.newRuler(102));
		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10----+----", 
				FixedWidthFieldSelection.newRuler(109));
		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10----+---11", 
				FixedWidthFieldSelection.newRuler(110));
		assertEquals( "----+----1----+----2----+----3----+----4----+----5----+----6----+----7----+----8----+----9----+---10----+---11-", 
				FixedWidthFieldSelection.newRuler(111));
	}
	
	/**
	 * basic tests with 1 column
	 */
	@Test
	public void testAddingAndRemovingColumns1() {
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, new FileSumary());
		FixedWidthFieldSelection fw = tstDtls.fieldSelection;
		TableModel tblMdl = tstDtls.getFieldTableMdl();
		String fieldName = "Field 1";
		
		assertEquals(1, fw.getFieldSelection().length);
		assertEquals(1, tblMdl.getRowCount());
		assertEquals(5, tblMdl.getColumnCount());
		assertEquals(0, tblMdl.getValueAt(0, DECIMAL_COLUMN));
		
		tblMdl.setValueAt(fieldName, 0, 0);
		
		String fieldDisplayText = tstDtls.getFieldDisplayText();
		String s = fieldDisplayText.substring(0, LINE_LENGTH);
		assertEquals(fieldName, s.trim());
		assertTrue(">" + s + "<>" + fieldName + "<", s.startsWith(fieldName));
		
		checkTextAfterColumnHeadings(fieldDisplayText);
		
	}

	private void checkTextAfterColumnHeadings(String fieldDisplayText) {
		assertEquals(SAMPLE_FILE_DATA, fieldDisplayText.substring(LINE_LENGTH*2 + 2));
		assertEquals(FixedWidthFieldSelection.newRuler(LINE_LENGTH), fieldDisplayText.substring(LINE_LENGTH+1, LINE_LENGTH*2 + 1));
		assertEquals('\n', fieldDisplayText.charAt(LINE_LENGTH*2+1));
		assertEquals('\n', fieldDisplayText.charAt(LINE_LENGTH));
	}

	/**
	 * Testing adding and removing columns
	 * 
	 */
	@Test
	public void testSettingLastColumnName() { 
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, new FileSumary());
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		int col = 52;
		String fieldName = "Fld 2";
		String fieldNameTst = fieldName + "   \n----+";
		
		tstDtls.doClickOnField(col);
		tblMdl.setValueAt(fieldName, 1, 0);
		
		String fieldDisplayText = tstDtls.getFieldDisplayText();
		String fn = fieldDisplayText.substring(52, LINE_LENGTH + 6);
		assertEquals(fieldNameTst, fn);
		
		checkTextAfterColumnHeadings(fieldDisplayText);
	}

	/**
	 * Testing adding and removing columns
	 * 
	 */
	@Test
	public void testAddingAndRemovingColumns2() { 
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, new FileSumary());
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		int col = 30;
		int colp1 = col + 1;
		
		tstDtls.doClickOnField(col);
					
		assertEquals(2, tblMdl.getRowCount() );
		assertEquals(colp1, tblMdl.getValueAt(1, POSITION_COLUMN));
		
		tstDtls.doClickOnField(col);
		assertEquals(1, tblMdl.getRowCount() );
		
		tstDtls.doClickOnField(col);
		
		ColumnDetails[] fieldSelection = tstDtls.fieldSelection.getFieldSelection();
		assertEquals(2, tblMdl.getRowCount() );
		assertEquals(colp1, tblMdl.getValueAt(1, POSITION_COLUMN));
		assertEquals(2, fieldSelection.length );
		assertEquals(colp1, fieldSelection[1].getStart());
		
		String fieldName1 = "Field 1";
		String fieldName2 = "Field 2";
		
		tblMdl.setValueAt(fieldName1, 0, 0);
		tblMdl.setValueAt(fieldName2, 1, 0);

		chkColumnHeadings2(tstDtls, col, 2, fieldName1, fieldName2);
		
		tstDtls.doClickOnField(20);
		chkColumnHeadings2(tstDtls, col, 3, fieldName1, fieldName2);
		
		tstDtls.doClickOnField(25);
		chkColumnHeadings2(tstDtls, col, 4, fieldName1, fieldName2);
		
		tstDtls.doClickOnField(20);
		chkColumnHeadings2(tstDtls, col, 3, fieldName1, fieldName2);

		tstDtls.doClickOnField(25);
		chkColumnHeadings2(tstDtls, col, 2, fieldName1, fieldName2);
	}

	
	private void chkColumnHeadings2(
				FixedWidthFieldSelection.TestDetails tstDtls, int col, int rowCount,
				String fieldName1, String fieldName2) {
		String fieldDisplayText = tstDtls.getFieldDisplayText();
		String s = fieldDisplayText.substring(0, col - 1);
		
		assertEquals(fieldName1, s.trim());
		assertTrue(">" + s + "<>" + fieldName1 + "<", s.startsWith(fieldName1));
		
		s = fieldDisplayText.substring(col, LINE_LENGTH);
		assertEquals(fieldName2, s.trim());
		assertTrue(s.startsWith(fieldName2));
		
		//assertEquals(SAMPLE_FILE_DATA, fieldDisplayText.substring(LINE_LENGTH + 1));
		checkTextAfterColumnHeadings(fieldDisplayText);
		
		ColumnDetails[] fieldSelection = tstDtls.fieldSelection.getFieldSelection();
		
		assertEquals(rowCount, fieldSelection.length);
		assertEquals(fieldName1, fieldSelection[0].name);
		assertEquals(1, fieldSelection[0].getStart());
		if (rowCount == 2) {
			assertEquals(col, fieldSelection[0].length);
		}
		
		for (int i = 0; i < rowCount; i++) {
			assertEquals(Type.ftChar, fieldSelection[i].type);
			assertEquals(0, fieldSelection[i].decimal);
		}
		
		assertEquals(fieldName2, fieldSelection[rowCount-1].name);
		assertEquals(col+1, fieldSelection[rowCount-1].getStart());
		assertEquals(LINE_LENGTH - col, fieldSelection[rowCount-1].length);
		
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		assertEquals(5, tblMdl.getColumnCount() );
		assertEquals(rowCount, tblMdl.getRowCount());
		
		assertEquals(fieldName1, tblMdl.getValueAt(0, 0));
		assertEquals(fieldName2, tblMdl.getValueAt(rowCount-1, 0));

		assertEquals(1, tblMdl.getValueAt(0, POSITION_COLUMN));
		assertEquals(col+1, tblMdl.getValueAt(rowCount-1, POSITION_COLUMN));
		
		assertTrue(tblMdl.isCellEditable(0, 0));
		assertTrue(tblMdl.isCellEditable(1, 0));
		assertTrue(tblMdl.isCellEditable(rowCount-1, 0));
		
		assertFalse(tblMdl.isCellEditable(0, POSITION_COLUMN));
		assertFalse(tblMdl.isCellEditable(1, POSITION_COLUMN));
		assertFalse(tblMdl.isCellEditable(rowCount-1, POSITION_COLUMN));
	}
	
	
	/**
	 * Testing column updates (use integer for type)
	 */
	@Test
	public void testColumnUpdates01() {
		int[] types = {Type.ftNumAnyDecimal, Type.ftNumRightJustified, Type.ftAssumedDecimal, Type.ftAssumedDecimalPositive};
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, new FileSumary());
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		for (int i = 0; i < types.length; i++) {
			tstDtls.doClickOnField(i * 10 + 5);
			int index = i+1;
			tblMdl.setValueAt(types[i], index, TYPE_COLUMN);
			tblMdl.setValueAt(i, index, DECIMAL_COLUMN);
			tblMdl.setValueAt("Field" + i, index,  FIELD_NAME_COLUMN);
			
			chkArray(types, tstDtls, 0, i);
		}
	}
	
	/**
	 * Testing column updates (use integer for type)
	 */
	@Test
	public void testColumnUpdates02() {
		int[] types = {Type.ftNumAnyDecimal, Type.ftNumRightJustified, Type.ftAssumedDecimal, Type.ftAssumedDecimalPositive};
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, new FileSumary());
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		for (int i = 0; i < types.length; i++) {
			tstDtls.doClickOnField(i * 10 + 5);
			int index = i+1;
			tblMdl.setValueAt(searchForType(types[i], comboDtls), index, TYPE_COLUMN);
			tblMdl.setValueAt(i, index, DECIMAL_COLUMN);
			tblMdl.setValueAt("Field" + i, index,  FIELD_NAME_COLUMN);
			
			chkArray(types, tstDtls, 0, i);
		}
	}
	
	/** 
	 * Testing updating Column names
	 */

	String[] alpha = {
			"abcdefghijklmnopqrstuvwxyz",
			"abcdefghijklmnopqrStUvWxYz23"
	};

	@Test
	public void testColumnNameUpdates() {
		int[] lengths = {3, 4, 5, 6, 22, 12};
		FileSumary fileDetails = new FileSumary();
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, fileDetails);
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		int pos = 0;
		for (int i = 0; i < lengths.length; i++) {
			pos += lengths[i];
			tstDtls.doClickOnField(pos);
		}
		ColumnDetails[] fieldSelection = tstDtls.fieldSelection.getFieldSelection();
		int end = fieldSelection.length - 1;
		pos = 1;
		for (int i = 0; i < lengths.length; i++) {
			assertEquals(pos, fieldSelection[i].getStart());
			assertEquals(lengths[i], fieldSelection[i].length);
			pos += lengths[i];
		}
		assertEquals(pos, fieldSelection[end].getStart());
		assertEquals(fileDetails.getMaxLineLength() - pos + 1, fieldSelection[end].length);
		
		for (int i = 0; i < lengths.length; i++) {
			checkNameAssignment(tstDtls, tblMdl, fieldSelection, i, lengths[i]);
		}
	}

	private void checkNameAssignment(FixedWidthFieldSelection.TestDetails tstDtls, TableModel tblMdl,
			ColumnDetails[] fieldSelection, int i, int length) {
		for (int j = length -2; j <= length + 2; j++) {
			checkOneAssignment(length, tstDtls, tblMdl, fieldSelection, i, j);
		}
		for (int j = length + 2; j >= length -2; j--) {
			checkOneAssignment(length, tstDtls, tblMdl, fieldSelection, i, j);
		}
	}

	/**
	 * 
	 * @param lengths
	 * @param tstDtls
	 * @param tblMdl
	 * @param fieldSelection
	 * @param i
	 * @param j
	 */
	private void checkOneAssignment(int length, FixedWidthFieldSelection.TestDetails tstDtls, TableModel tblMdl,
			ColumnDetails[] fieldSelection, int i, int j) {
		String s = alpha[j%2];
		s = s.substring(s.length() - j);
		tblMdl.setValueAt(s, i,  FIELD_NAME_COLUMN);
		
		int column = fieldSelection[i].getStart() - 1;
		String fldHeading = tstDtls.getFieldDisplayText()
								   .substring(column, column + length);
		if (j <= length - 1) {
			assertEquals(s, fldHeading.trim());
			assertTrue(fldHeading.startsWith(s));
		} 
		if (j >= length - 1) {
			assertEquals(s.substring(0, length - 1) + " ", fldHeading);
			assertTrue(length + " >" +s + "< >"+ fldHeading + "<", s.startsWith(fldHeading.trim()));
		}
	}
	
	@Test
	public void testColumnNamesAfterColumnInsertsAdnDeletes() {
		FileSumary fileDetails = new FileSumary();
		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(comboDtls, fileDetails);
		TableModel tblMdl =  tstDtls.getFieldTableMdl();
		
		String colHeading1 = "123456789";
		String colHeading2 = "abcdefg";
		
		tstDtls.doClickOnField(15);
		tblMdl.setValueAt(colHeading1, 0,  FIELD_NAME_COLUMN);
		
		String fldHeading = tstDtls.getFieldDisplayText()
				   .substring(0, 17);
		assertEquals(colHeading1 + "        ", fldHeading);

		tstDtls.doClickOnField(4);
		fldHeading = tstDtls.getFieldDisplayText()
				   .substring(0, 15);
		assertEquals("123            ", fldHeading);

		tblMdl.setValueAt(colHeading2, 1,  FIELD_NAME_COLUMN);
		
		fldHeading = tstDtls.getFieldDisplayText()
				   .substring(0, 17);
		assertEquals("123 " + colHeading2 + "      ", fldHeading);
		
		tstDtls.doClickOnField(4);
		fldHeading = tstDtls.getFieldDisplayText()
				   .substring(0, 17);
		assertEquals(colHeading1 + "        ", fldHeading);
	}

	
	private TreeComboItem searchForType(int type, TreeComboItem[] comboArray) {
		
		TreeComboItem ret = null;
		for (int i = 0 ; i < comboArray.length && ret == null; i++) {
			TreeComboItem[] children = comboArray[i].getChildren();
			if (children != null && children.length > 0) {
				ret = searchForType(type, children);
			} else if (comboArray[i].key != null && comboArray[i].key.intValue() == type) {
				ret = comboArray[i];
			}
		}
		return ret;
	}


	private void chkArray(int[] types, FixedWidthFieldSelection.TestDetails tstDtls, int start, int id) {
		ColumnDetails[] fieldSelection = tstDtls.fieldSelection.getFieldSelection();
		assertEquals(id+2, fieldSelection.length);
		int len = start==0? 5 : 10;
		for (int j = start; j <= id; j++) {
			int idx = j+1;

			assertEquals("Field" + j, len, fieldSelection[j].length);
			
			assertEquals("Field" + j, fieldSelection[idx].name);
			assertEquals(j * 10 + 6, fieldSelection[idx].getStart());
			assertEquals(types[j], fieldSelection[idx].type);
			assertEquals(j, fieldSelection[idx].decimal);
			len = 10;
		}
	}
			
			
	
	/**
	 * 
	 * @author Bruce Martin
	 *
	 */
	private static class  FileSumary implements FixedWidthFieldSelection.IFileSummaryModel {
		private FixedWidthFieldSelection.FieldListManager fieldMgr = new FixedWidthFieldSelection.FieldListManager();

		@Override
		public int[][] getColumnDetails() {
			return null;
		}

		@Override
		public String getFileDisplay() {
			return 	  SAMPLE_FILE_DATA;
		}

		@Override
		public int getMaxLineLength() {
			return LINE_LENGTH;
		}

		@Override
		public int getType(int col, int len) {
			return Type.ftChar;
		}

		@Override
		public FieldListManager getFieldListManager() {
			return fieldMgr;
		}
		
		
	};
}
