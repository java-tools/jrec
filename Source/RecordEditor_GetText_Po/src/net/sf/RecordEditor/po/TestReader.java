package net.sf.RecordEditor.po;

import java.io.IOException;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.edit.EditRec;
import net.sf.RecordEditor.edit.display.DisplayBuilder;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.po.def.PoLine;
import net.sf.RecordEditor.po.display.PoDisplayBuilder;
import net.sf.RecordEditor.po.display.PoList;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.DataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;


public class TestReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, RecordException {
		PoMessageLineReader r = new PoMessageLineReader();
		PoLine l;
		LayoutDetail layoutDtls = PoLayoutMgr.PO_LAYOUT;
		DataStore<PoLine> ds = new DataStoreStd<PoLine>(layoutDtls);

		DisplayBuilderFactory.register(new PoDisplayBuilder());

		r.open("C:\\JavaPrograms\\RecordEdit\\HSQLDB\\lang\\ReMsgs_cn.po");

		while ((l = r.read()) != null) {
			ds.add(l);
		}

		r.close();

		FileView<LayoutDetail> v = new FileView<LayoutDetail>("ReMsgs_cn.po", ds, layoutDtls);

		//new ReMainFrame("PoEdit", "", "po");
		new EditRec(true);
		//DisplayBuilder.newLineList(null, layoutDtls, v, v);

		DisplayBuilder.addToScreen(null, new PoList(v, true));
	}

}
