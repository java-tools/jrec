package net.sf.RecordEditor.re.file;


import java.io.IOException;

import javax.swing.SwingWorker;

import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.common.Common;


public class FileWriterBackground extends SwingWorker<Void, Void> {

	FileWriter writer;
	
	public FileWriterBackground(FileWriter pWriter) 
	throws RecordException {
		writer = pWriter;
	}
	
	@Override
	protected Void doInBackground() throws IOException {
		try {
			doWrite();
		} catch(IOException e) {
			e.printStackTrace();
			Common.logMsg("Write Failed: " + e, e);
			throw e;
		} catch (RuntimeException e) {
			e.printStackTrace();
			Common.logMsg("Write Failed: " + e, e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			Common.logMsg("Write Failed: " + e, e);
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	public void doWrite() throws IOException {
		writer.doWrite();
	    
	    firePropertyChange("Finished", null, null);
	}
}
