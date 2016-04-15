package net.sf.RecordEditor.test.io;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import junit.framework.TestCase;

/**
 * Run through and read the Point of Sale files
 * This will check there are no obvious errors in the
 * record read objects
 * 
 * @author Bruce Martin
 *
 */
public class TstRead4680 extends TestCase {
	CopyBookDbReader cr = CopyBookDbReader.getInstance();
	
	
	public void testSPL() throws Exception {
		tstRead("SPL", TstConstants.SAMPLE_DIRECTORY + "Pos_Spl_1.bin");
	}
	
	
	public void testPrice() throws Exception {
		tstRead("Price", TstConstants.SAMPLE_DIRECTORY + "Pos_Price_1.bin");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void tstRead(String schemaXml, String filename) throws RecordException, Exception {
		LayoutDetail schema = cr.getLayout(schemaXml); 
		
		AbstractLineReader lineReader = LineIOProvider.getInstance().getLineReader(schema);
		AbstractLine l;
		
		lineReader.open(filename, schema);
		int expected = 0, diff = 0, unknown = 0;
		
		while ((l = lineReader.read()) != null) {
			int pref = l.getPreferredLayoutIdx();
			
			if (pref < 0) {
				unknown += 1;
			} else if (l.getData().length == schema.getRecord(pref).getLength() ) {
				expected += 1;
			} else {
				diff += 1;
			}
		}
		
		System.out.println(filename + "\t" + unknown + "\t" + expected + "\t" + diff);
		lineReader.close();
		assertTrue(5 > unknown);
		assertEquals(0, diff);
	}
}
