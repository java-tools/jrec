/**
 * 
 */
package edit.open;

import javax.swing.JButton;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.StandardLineIOProvider;
import net.sf.RecordEditor.edit.open.OpenFileEditPnl;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.re.openFile.TstLayoutSelectionDbFields;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

/**
 * @author bruce
 *
 */
@SuppressWarnings("serial")
public class XTstOpenFileEditPnl extends OpenFileEditPnl {

	public final AbstractLayoutSelection layoutSelection;
	private AbstractLayoutDetails lastSchema;
	private AbstractLineIOProvider lastIoProvider;
	
	
	/**
	 * @param format
	 * @param pInFile
	 * @param pInitialRow
	 * @param pIoProvider
	 * @param layoutCreate1
	 * @param layoutCreate2
	 * @param propertiesFiles
	 * @param helpScreen
	 * @param newLayoutSelection
	 */
	public XTstOpenFileEditPnl(int format, String pInFile, int pInitialRow, AbstractLineIOProvider pIoProvider,
			JButton layoutCreate1, JButton layoutCreate2, String propertiesFiles, String helpScreen,
			AbstractLayoutSelection newLayoutSelection) {
		super(format, pInFile, pInitialRow, pIoProvider, layoutCreate1, layoutCreate2, propertiesFiles, helpScreen,
				newLayoutSelection);
		
		layoutSelection = newLayoutSelection;
	}
	
	/**
	 * Get the Data File Name combo
	 * 
	 * @return Data File Name combo
	 */
	public TreeComboFileSelect getFileNameCombo() {
		return fileName;
	}

	/**
	 * @return schema from the last process File
	 */
	public AbstractLayoutDetails getLastSchema() {
		return lastSchema;
	}

	/**
	 * 
	 * @return ioProvider from the last process File
	 */
	public AbstractLineIOProvider getLastIoProvider() {
		return lastIoProvider;
	}

	/**
	 * Clear the schema and provider
	 */
	public void clearLastSchemaAndProvider() {
		lastSchema = null;
		lastIoProvider = null;
	}

	@Override
	public void processFile(String sFileName, AbstractLayoutDetails layoutDetails, AbstractLineIOProvider ioProvider,
			boolean pBrowse) throws Exception {
		
		lastSchema = layoutDetails;
		lastIoProvider = ioProvider;
	}


	public static class TstDBSelection {
		public final TstLayoutSelectionDbFields selectionFields = new TstLayoutSelectionDbFields();
		public final LayoutSelectionDB dbSelection;
		public final XTstOpenFileEditPnl openFile;
		
		public TstDBSelection(CopyBookInterface copybookReader,   String propertiesFiles) {
			dbSelection = new LayoutSelectionDB(selectionFields, copybookReader, false);
			openFile = new XTstOpenFileEditPnl( 
				OpenFileEditPnl.NEW_FILE_FORMAT, null, 0,  
				new StandardLineIOProvider(), null, null, propertiesFiles, "", dbSelection);
		}
	}
}
