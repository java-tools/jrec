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
		
//		if (schema instanceof LayoutDetail) 	System.out.println("### getLineReader: " + ((LayoutDetail)schema).getLayoutName());
		return getLineReader(fileStructure, schema, getLineProvider(schema));
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
		if (lineProvider == null) {
			lineProvider = getLineProvider(schema);
		}
		return getLineReader(schema.getFileStructure(), lineProvider);
	}

	@Override
	public int getNumberOfEntries() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getManagerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getKey(int idx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public LineProvider getLineProvider(IBasicFileSchema schema) {
		return getLineProvider(schema.getFileStructure(), schema.getFontName(), schema.isBinary());
	}
	
	
	@SuppressWarnings("rawtypes")
	protected LineProvider getLineProvider(int fileStructure, String charset, boolean binary) {
		return getLineProvider(fileStructure, charset);
	}
	
}
