package net.sf.RecordEditor.test.text;

import javax.swing.text.BadLocationException;

import junit.framework.TestCase;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.params.Parameters;


/**
 * Test DsContent (with Char based DataStoreLarge
 * 
 * @author Bruce Martin
 *
 */
public class TstDSContent02Vb  extends TestCase {

	TstDSContent02_Common tst = new TstDSContent02_Common(TstConstants.DS_VB);
	


	/**
	 * Test with fixed Seed
	 *
	 * @throws BadLocationException
	 */
	public void test1() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		System.out.println();
		System.out.println("Test 1 Fixed Seed");
		System.out.println();
		tst.runTest(1331, 3000, TstConstants.SIZE_SMALL);
	}


	/**
	 * Test with fixed Random Seed
	 *
	 * @throws BadLocationException
	 */
	public void test2() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		System.out.println();
		System.out.println("Test 2 Random Seed");
		System.out.println();
		tst.runTest((int) System.currentTimeMillis(), 2000, TstConstants.SIZE_SMALL);
	}


	/**
	 * Test with fixed Seed
	 *
	 * @throws BadLocationException
	 */
	public void test3() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.testPosition = true;

		System.out.println();
		System.out.println("Test 3 Fixed Seed");
		System.out.println();
		tst.runTest(2345678, 4000, TstConstants.SIZE_SMALL);
	}

	/**
	 * Test with random Seed
	 *
	 * @throws BadLocationException
	 */
	public void test4() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.testPosition = true;

		System.out.println();
		System.out.println("Test 4 Random Seed");
		System.out.println();
		tst.runTest((int) System.currentTimeMillis(), 500, TstConstants.SIZE_SMALL);
	}

	
	/**
	 * Test with fixed Seed
	 *
	 * @throws BadLocationException
	 */
	public void test21() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		
		System.out.println();
		System.out.println("Test 21 Fixed Seed");
		System.out.println();
		tst.runTest(1331, 7000, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}



	/**
	 * Test with fixed Seed
	 *
	 * @throws BadLocationException
	 */
	public void test23() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.testPosition = true;

		System.out.println();
		System.out.println("Test 23 Fixed Seed");
		System.out.println();
		tst.runTest(2345678, 500, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

}
