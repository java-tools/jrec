package net.sf.RecordEditor.tip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineWriter;

public class PropertiesLineWriter extends AbstractLineWriter {

	private final String varPrefix;
	private OutputStream outStream;
	@SuppressWarnings("rawtypes")
	private AbstractLayoutDetails layout = null;
	private Properties props = null;

	private int idx = 1;

	public PropertiesLineWriter(String varPrefix) {
		this.varPrefix = varPrefix;
	}


	@Override
	public void open(OutputStream outputStream) throws IOException {
		outStream = outputStream;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void write(AbstractLine line) throws IOException {

		if (layout == null) {
			layout = line.getLayout();
			if (layout instanceof LayoutDetail) {
				LayoutDetail l = (LayoutDetail) layout;
				if (l.getExtraDetails() !=  null && l.getExtraDetails() instanceof Properties) {
					props = (Properties) l.getExtraDetails();
				}
			}

			if (props == null) {
				props = new Properties();
			}
		}

		AbstractRecordDetail r = layout.getRecord(0);

		Object o;
		String v = varPrefix + "." + idx + ".";
		for (int i = 0; i < r.getFieldCount(); i++) {
			o = line.getField(0, i);
			if (o != null && ! "".equals(o)) {
				props.put(v + r.getField(i).getName(), o);
			}
		}
		idx += 1;
	}

	@Override
	public void close() throws IOException {
		props.store(outStream, "Updated by RecordEditor - TipWriter");
		outStream.close();
	}

}
