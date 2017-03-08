package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;





import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.sf.RecordEditor.re.util.FileStructureDtls.FileStructureOption;
import net.sf.RecordEditor.utils.charsets.FontCombo;


/** 
 * This  class will build `Test interfaces` for 
 * java junit testing of Swing panels.
 * It generates Test object that expose internal workings for testing
 * 
 * @author Bruce Martin
 *
 */
public class ZTstSaveAsBuildTestData {

	public static SaveAsPnlFileStructureTst getSaveAsPnlFileStructureTst(CommonSaveAsFields commonSaveAsFields) {
		return new SaveAsPnlFileStructureTst(new SaveAsPnlFileStructure(commonSaveAsFields));
	}
	
	public static final class SaveAsPnlBaseTst {
		public final SaveAsPnlBase saveAs;
		public final JTable fieldSelectionTbl;
		public final CommonSaveAsFields commonSaveAsFields;
		public final JButton selectBtn;
		public final JButton deSelectBtn;

		
		public SaveAsPnlBaseTst(SaveAsPnlBase base) {
			this.saveAs = base; 
			this.fieldSelectionTbl = saveAs.fieldTbl;
			this.commonSaveAsFields = saveAs.commonSaveAsFields;
			this.selectBtn = saveAs.selectBtn;
			this.deSelectBtn = saveAs.deSelectBtn;
		}
		
		public final AbstractTableModel fieldSelectionMdl() {
			return saveAs.fieldModel;
		}
	}
	
	public static final class SaveAsPnlFileStructureTst {
		@SuppressWarnings("rawtypes")
		public final JComboBox  fileStructures; //= FileStructureDtls.getFileStructureCombo();
		public final FontCombo fontCombo ;
		FileStructureOption[] itms;
		
		public final SaveAsPnlFileStructure saveAsFS;

		private SaveAsPnlFileStructureTst(SaveAsPnlFileStructure saveAsFS) {
			this.fileStructures = saveAsFS.fileStructures;
			this.fontCombo = saveAsFS.fontCombo;
			
			this.saveAsFS = saveAsFS;
		}
		
		
		public FileStructureOption[] getComboItems() {
			itms = new FileStructureOption[fileStructures.getItemCount()];
			
			if (itms != null) {
				for (int i = 0; i < itms.length; i++) {
					itms[i] = (FileStructureOption) fileStructures.getItemAt(i);
				}
			}
			
			return itms;
		}
		
		
		public void save(String selection, OutputStream outStream) throws Exception {
			saveAsFS.save(selection, outStream);
		}
	}
}
