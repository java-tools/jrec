package edit.display.saveAs;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlCsv;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlFixed;
import net.sf.RecordEditor.edit.display.SaveAs.ZTstSaveAsBuildTestData;

import org.junit.Test;

import xCommon.DDetails;
import xCommon.XSchemas;

/**
 * Test action of:<ol>
 *   <li>select/de-select buttons for SaceCsv and SavedFixed screens
 * </ol>
 * 
 * @author Bruce Martin
 *
 */
public class TstSaveAsCsvFixed {

	
	@Test
	public void testSaveCsvBtns() throws InterruptedException, IOException {
		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC);
		doTest(new SaveAsPnlCsv(dtls.saveAsFields));
	}
	
	@Test
	public void testSaveFixedBtns() throws InterruptedException, IOException {
		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC);
		doTest(new SaveAsPnlFixed(dtls.saveAsFields));
	}
	

	public void doTest(SaveAsPnlBase base) throws InterruptedException {
		ZTstSaveAsBuildTestData.SaveAsPnlBaseTst tst = new ZTstSaveAsBuildTestData.SaveAsPnlBaseTst(base);
		TblMdlListner listner = new TblMdlListner();
		
		tst.fieldSelectionMdl().addTableModelListener(listner);
		
		check(true, base.getIncludeFields());
		
		
		tst.deSelectBtn.doClick();
		checkBtnClick(base, listner, false);
		
		tst.selectBtn.doClick();
		checkBtnClick(base, listner, true);
	}

	/**
	 * @param base
	 * @param listner
	 * @param expected
	 * @throws InterruptedException
	 */
	private void checkBtnClick(SaveAsPnlBase base, TblMdlListner listner,
			boolean expected) throws InterruptedException {
		
		Thread.sleep(100);
		check(expected, base.getIncludeFields());
		assertTrue(listner.tableChanged);
		
		listner.tableChanged = false;
	}

	private void check(boolean expected, boolean[] selected) {
		
		for (int i = 0; i < selected.length; i++) {
			assertTrue(i + " " + expected, expected == selected[i]);
		}
	}
	
	private static class TblMdlListner implements TableModelListener {
		
		boolean tableChanged = false;

		/* (non-Javadoc)
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent e) {
			tableChanged = true;
		}
		
	}
}
