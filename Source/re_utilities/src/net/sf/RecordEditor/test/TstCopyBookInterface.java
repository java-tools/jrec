/*
 * Created on 12/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.sf.RecordEditor.test;

import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;
import net.sf.RecordEditor.utils.common.Common;





import junit.framework.TestCase;

/**
 * @author bymartin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TstCopyBookInterface extends TestCase {

	private int dbIdx = TstConstants.DB_INDEX;

	//private int amsPoDownload = 358;
	private int amsRcptUploadHeader = 360;

	private int rt_RecordLayout  = 1;
	//private int rt_GroupOfRecord = 9;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();

	public static void main(String[] args) {
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		Common.setConnectionId(dbIdx);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		Common.closeConnection();
	}

	public void testGetSystems() throws SQLException {
		ArrayList<SystemItem> l = copybookInt.getSystems();

		assertTrue("Get Systems: " + l.size(), l.size() > 2);
		SystemItem dtls = l.get(0);

		if (!"ODBMS -  EDI".equals(dtls.description)) {
			assertEquals("Get Systems (wrong system): " + dtls.description + "<",
		        "Ams", dtls.description);
		}
	}

	public void testLoadLayouts() {
		ArrayList<LayoutItem> l = new ArrayList<LayoutItem>();
		copybookInt.loadLayouts(l);

		assertTrue("Get Systems: " + l.size(), l.size() > 2);
	}

	public void testGetGroup() {
		String recordName = "ams PO Download";
		LayoutDetail g = copybookInt.getLayout(recordName);

		assertNotNull("Get Group: ", g);
		assertEquals("Get Group: " + g.getLayoutName(), recordName, g.getLayoutName());
	}

	public void testGetFields() {
		FieldDetail[] flds = copybookInt.getFields(amsRcptUploadHeader, rt_RecordLayout, "");

		assertNotNull("Get Fields: ", flds);
		assertTrue("Get Fields (size): " + flds.length, flds.length > 0);

		assertEquals("Get Fields: ", flds[0].getName(), "Record Type");
	}

}
