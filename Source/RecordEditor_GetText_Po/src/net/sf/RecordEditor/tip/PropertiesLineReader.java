/**
 *
 */
package net.sf.RecordEditor.tip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineReader;



/**
 * @author mum
 *
 */
public class PropertiesLineReader extends AbstractLineReader<LayoutDetail> {

	private final String varPrefix;
	private Properties props;
	private int idx = 0;



	public PropertiesLineReader(String varPrefix) {

		this.varPrefix = varPrefix;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.io.InputStream, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void open(InputStream inputStream, LayoutDetail pLayout)
			throws IOException, RecordException {
		super.setLayout(pLayout);

		idx = 0;

		props = new Properties();

		props.load(inputStream);
		pLayout.setExtraDetails(props);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#read()
	 */
	@Override
	public AbstractLine read() throws IOException {
		boolean found = false;
		LayoutDetail l = getLayout();
		RecordDetail r = l.getRecord(0) ;
		String s;
		String v;
		ArrayListLine<FieldDetail, RecordDetail, LayoutDetail> line = new ArrayListLine<FieldDetail, RecordDetail, LayoutDetail>(l, 0, 1);
		String key;

		for (int j = 0; j < 5; j++) {
			v = varPrefix + "." + idx + ".";
			for (int i = 0; i < r.getFieldCount(); i++) {
				key = v + r.getField(i).getName();
				s = props.getProperty(key);
				if (s != null) {
					try {
						line.setField(0, i, s);
						found = true;
					} catch (Exception e) {
					}
					props.remove(key);
				}
			}

			idx += 1;
			if (found) {
				return line;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#close()
	 */
	@Override
	public void close() throws IOException {

	}

}
