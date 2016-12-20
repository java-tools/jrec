package re.util.fw;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.table.TableModel;

import org.junit.Test;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionPane;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionView;
import net.sf.RecordEditor.re.util.fw.UpdateableFileSummayModel;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import zCommon.ZCommonCode;


/**
 * Do Basic Test on the Fixed Width Model, View and Controller
 *  
 * @author Bruce Martin
 *
 */
public class TstFixedWidthMVC {
	
	public static final int NAME_COLUMN = 0;
	public static final int DECIMAL_COLUMN = 3;
	public static final int INCLUDE_COLUMN = 4;
	
	public static final String[] FW_FILES = ZCommonCode.FW_FILES;
	
	public static final ColumnDetails[] UPDATED_AMS_PO_HEADER_FIELDS = ZCommonCode.UPDATED_AMS_PO_HEADER_FIELDS;
	
	public static final ColumnDetails[][] FILE_COLUMNS = ZCommonCode.FILE_COLUMNS;
	public static final String[] CHARCTER_SETS = ZCommonCode.CHARCTER_SETS;
	
	
	/**
	 * Check if Fixed-Width details are loaded correctly
	 */
	@Test
	public void testFwModel() {
		UpdateableFileSummayModel mdl = new UpdateableFileSummayModel();
		FixedWidthFieldSelection fldSelect = new FixedWidthFieldSelection(new TreeComboItem[0], mdl);
		for (int i = 0; i < FW_FILES.length; i++) {
			String fw = FW_FILES[i];
			
			assertTrue(mdl.setData(fw, ZCommonCode.readDataFile(fw), true));
			fldSelect.reloadFromFileModel();

			checkModel(mdl, i);
		}
	}

	/**
	 * Test Model / View
	 */
	@Test
	public void testFwView01() {
		UpdateableFileSummayModel mdl = new UpdateableFileSummayModel();
		FixedWidthSelectionView fldView = FixedWidthSelectionView.newFWView(
				new TreeComboItem[0], mdl, true, new JButton());
		for (int i = 0; i < FW_FILES.length; i++) {
			String fw = FW_FILES[i];
			
			assertTrue(mdl.setData(fw, ZCommonCode.readDataFile(fw), true));
			fldView.reloadFromFileModel();
			fldView.setSchemaFileName(FW_FILES[i] + ".xml");

			checkView(mdl, fldView, i);
			
			fldView.setSchemaFileName("asdfg");
			assertEquals("asdfg", fldView.getSchemaFileName());
		}
	}
	
	

	/**
	 * Test Model / View / Controller (pane)
	 */
	@Test
	public void testFwPane01() {
		UpdateableFileSummayModel mdl = new UpdateableFileSummayModel();
		JButton goBtn = new JButton();
		FixedWidthSelectionView fldView = FixedWidthSelectionView.newFWView(
				new TreeComboItem[0], mdl, true, goBtn);
		FixedWidthSelectionPane pane = new FixedWidthSelectionPane(fldView, mdl, goBtn);
		for (int i = 0; i < FW_FILES.length; i++) {
			String fw = FW_FILES[i];
			
			assertTrue(pane.setData(fw, ZCommonCode.readDataFile(fw), true, ""));

			checkView(mdl, fldView, i);
			checkPane(pane, i);
		}

		for (int i = 0; i < FW_FILES.length; i++) {
			String fw = FW_FILES[i];
			
			assertTrue(pane.isMyLayout("", fw, ZCommonCode.readDataFile(fw)));
			
			checkView(mdl, fldView, i);
			checkPane(pane, i);
		}
		assertTrue(goBtn == pane.getGoButton());
	}
			

	@Test
	public void testFwPane02() {
		UpdateableFileSummayModel mdl = new UpdateableFileSummayModel();
		JButton goBtn = new JButton();
		TreeComboItem[] typeComboTree = new TreeComboItem[0];

		FixedWidthFieldSelection.TestDetails tstDtls = FixedWidthFieldSelection.newTestDetails(typeComboTree, mdl);
		FixedWidthSelectionView fldView = new FixedWidthSelectionView(
				new BaseHelpPanel("fwFieldSelection"), typeComboTree, 
				mdl, 
				tstDtls.fieldSelection, 
				true, goBtn);
		FixedWidthSelectionPane pane = new FixedWidthSelectionPane(fldView, mdl, goBtn);
		String fw = ZCommonCode.PO_HEADER_FILE;
		
		assertTrue(pane.setData(fw, ZCommonCode.readDataFile(fw), true, ""));
		
		tstDtls.doClickOnField(1);
		tstDtls.doClickOnField(2);
		tstDtls.doClickOnField(16);
		tstDtls.doClickOnField(23);
		tstDtls.doClickOnField(50);
		tstDtls.doClickOnField(59);
		tstDtls.doClickOnField(68);

		TableModel fieldTableMdl = tstDtls.getFieldTableMdl();
		for (int i = 0; i < UPDATED_AMS_PO_HEADER_FIELDS.length; i++) {
			String name = UPDATED_AMS_PO_HEADER_FIELDS[i].name;
			if ("".equals(name)) {
				fieldTableMdl.setValueAt(false, i, INCLUDE_COLUMN);
			} else {
				fieldTableMdl.setValueAt(name, i, NAME_COLUMN);
			}
		}
		fieldTableMdl.setValueAt(2, 1, DECIMAL_COLUMN);

		chkColumnDetails(UPDATED_AMS_PO_HEADER_FIELDS, mdl.getFieldListManager().getFieldSelection());
		chkColumnDetails(UPDATED_AMS_PO_HEADER_FIELDS, fldView.getFieldSelection());
		
		chkXRecord(UPDATED_AMS_PO_HEADER_FIELDS, mdl.asExtenalRecord(fw, ""));
		chkLayout(UPDATED_AMS_PO_HEADER_FIELDS, pane.getLayout("", null));
		
		
//TODO		
//TODO		assertTrue(pane.isMyLayout("", fw, ZCommonCode.readDataFile(fw)));
		

	}
	
	private void chkColumnDetails(ColumnDetails[] expected, ColumnDetails[] cmp) {
		if (expected == null) {
			for (ColumnDetails f : cmp) {
				System.out.println("\t\tnewColDtls(" 
						+ "\"" + f.name + "\""
						+ ", " + f.getStart()  
						+ ", " + f.length
						+ ", " + f.type + "),");
			}
		} else {
			int i = 0;
			for (ColumnDetails f : cmp) {
				ColumnDetails c = expected[i++];
				assertEquals(c.name, f.name);
				assertEquals(c.getStart(), f.getStart());
				assertEquals(c.getLength(), f.length);
				assertEquals(c.name + " " + i, c.getType(), f.type);
				assertEquals(c.decimal, f.decimal);
				
				assertEquals(c.name.length() > 0, f.include);
			}
		}
	}

	
	private void chkXRecord(ColumnDetails[] expected, ExternalRecord xRec) {
		
		int i = 0;
		for (int j = 0; j < expected.length; j++) {
			if (expected[j].name.length() > 0) {
				ExternalField fld = xRec.getRecordField(i++);
				assertEquals(expected[j].getStart(),fld.getPos());
				assertEquals(expected[j].getLength(),fld.getLen());
				assertEquals(expected[j].getType(), fld.getType());
				assertEquals(expected[j].decimal, fld.getDecimal());
			}
		}
	}
	
	
	private void chkLayout(ColumnDetails[] expected, LayoutDetail layout) {
		
		int i = 0;
		for (int j = 0; j < expected.length; j++) {
			if (expected[j].name.length() > 0) {
				IFieldDetail fld = layout.getField(0, i++);
				assertEquals(expected[j].getStart(),fld.getPos());
				assertEquals(expected[j].getLength(),fld.getLen());
				assertEquals(expected[j].getType(), fld.getType());
				assertEquals(expected[j].decimal, fld.getDecimal());
			}
		}
	}


	public static void checkPane(FixedWidthSelectionPane pane, int fldIdx) {
		ZCommonCode.checkPane(pane, FILE_COLUMNS[fldIdx], CHARCTER_SETS[fldIdx]);
	}

	private void checkView(UpdateableFileSummayModel mdl, FixedWidthSelectionView fldView, int fileIdx) {
		checkModel(mdl, fileIdx);
		
		assertEquals(CHARCTER_SETS[fileIdx], fldView.getFont());

		String expectedSchemaName = FW_FILES[fileIdx] + ".xml";
		fldView.setSchemaFileName(expectedSchemaName);
		String schemaName = fldView.getSchemaFileName();
		assertEquals(expectedSchemaName, schemaName.substring(schemaName.length() - expectedSchemaName.length()));
//			assertTrue(fldView.getSchemaFileName().endsWith(FW_FILES[i] + ".xml"));
		
		ColumnDetails[] fieldSelection = fldView.getFieldSelection();
		checkFieldSelectionArray(FILE_COLUMNS[fileIdx], fieldSelection);	
		

	}

	/**
	 * Check the model has been update correctly
	 * @param mdl model
	 * @param fileIdx file index
	 */
	private void checkModel(UpdateableFileSummayModel mdl, int fileIdx) {
		int[][] columnDetails = mdl.getColumnDetails();
		ColumnDetails[] expectedColumns = FILE_COLUMNS[fileIdx];
		ColumnDetails lastField = expectedColumns[expectedColumns.length - 1];
		
		assertEquals(expectedColumns.length, columnDetails[0].length);
		assertEquals(lastField.getStart() + lastField.getLength() - 1, mdl.getMaxLineLength());
		assertEquals(CHARCTER_SETS[fileIdx], mdl.getCharsetDetails().charset);
								
		ColumnDetails[] fieldSelection = mdl.getFieldListManager().getFieldSelection();
		for (int j = 0; j < expectedColumns.length; j++) {
			assertEquals(expectedColumns[j].getStart(), columnDetails[0][j] + 1);
			assertEquals(expectedColumns[j].getType(), columnDetails[1][j]);
		}
		
		checkFieldSelectionArray(expectedColumns, fieldSelection);
		
		ExternalRecord xRec = mdl.asExtenalRecord(FW_FILES[fileIdx], CHARCTER_SETS[fileIdx]);

		for (int j = 0; j < expectedColumns.length; j++) {
			ExternalField fld = xRec.getRecordField(j);
			assertEquals(expectedColumns[j].getStart(),fld.getPos());
			assertEquals(expectedColumns[j].getLength(),fld.getLen());
			assertEquals(expectedColumns[j].getType(), fld.getType());
			assertEquals(0, fld.getDecimal());
		}
	}

	private void checkFieldSelectionArray(ColumnDetails[] expectedColumns, ColumnDetails[] fieldSelection) {
		for (int j = 0; j < expectedColumns.length; j++) {
			ColumnDetails fld = fieldSelection[j];
			assertEquals(expectedColumns[j].getStart(),fld.getStart());
			assertEquals(expectedColumns[j].getLength(),fld.length);
			assertEquals(expectedColumns[j].getType(), fld.type);
			assertEquals(true, fld.include);
			assertEquals(0, fld.decimal);
		}
	}
}
