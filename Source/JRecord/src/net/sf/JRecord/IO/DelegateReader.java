package net.sf.JRecord.IO;

import java.io.IOException;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LineProvider;


@SuppressWarnings("rawtypes")
public abstract class DelegateReader<Layout extends AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail>>
extends AbstractLineReader<Layout> {

	private AbstractLineReader<Layout> reader;


	public DelegateReader() {
		super();
	}

	public DelegateReader(LineProvider<Layout> provider) {
		super(provider);
	}

	/**
	 * @return
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#read()
	 */
	public AbstractLine<Layout> read() throws IOException {
		return reader.read();
	}


	/**
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#close()
	 */
	public void close() throws IOException {
		reader.close();
	}


	/**
	 * @return
	 * @see net.sf.JRecord.IO.AbstractLineReader#canWrite()
	 */
	public boolean canWrite() {
		return reader.canWrite();
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(AbstractLineReader<Layout> reader) {
		this.reader = reader;
		super.setLayout(reader.getLayout());
	}



}
