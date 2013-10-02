package net.sf.JRecord.IO;

import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Details.LineProvider;

public abstract class BasicIoProvider implements AbstractLineIOProvider {



    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(net.sf.JRecord.Common.IBasicFileSchema)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(IBasicFileSchema schema) {
		int fileStructure = schema.getFileStructure();
		return getLineReader(fileStructure, schema, getLineProvider(fileStructure));
	}

   /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(IBasicFileSchema schema,
			LineProvider lineProvider) {
		return getLineReader(schema.getFileStructure(), schema, lineProvider);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(int fileStructure,
			IBasicFileSchema schema, LineProvider lineProvider) {
		return getLineReader(schema.getFileStructure(), lineProvider);
	}
}
