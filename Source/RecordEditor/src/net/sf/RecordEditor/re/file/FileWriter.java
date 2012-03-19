package net.sf.RecordEditor.re.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.fileStorage.DataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;

public class FileWriter {
	private static final long INTERVAL = 1500000000l;
	
	private List<AbstractLine> lines;
	private String  fileName;
	private boolean backup, isGZip;
	private AbstractLineWriter writer;
	private AbstractLayoutDetails layout;
	private long lastTime;
	private int count;
	
	private boolean done = false;
	
	
	public FileWriter(List<AbstractLine> pLines,
			AbstractLayoutDetails pLayout,
	        String  pFileName,
	        boolean pBackup,
	        boolean pIsGZip,
	        AbstractLineWriter pWriter) 
	throws RecordException {
		lines  = pLines;
		layout = pLayout;
		fileName = pFileName;
		backup = pBackup;
		isGZip = pIsGZip;
		writer = pWriter;

        if (writer == null) {
            throw new RecordException("No file writer available");
        }
	}
	
	
	public void doWrite() throws IOException {
		String oFname = fileName + ".~tmp~";
	    if (isGZip) {
	        FileOutputStream fs = new FileOutputStream(oFname);
	        writer.open(new GZIPOutputStream(fs));

	        writeToFile(writer, lines);
	        fs.close();
	    } else {
	        writer.open(oFname);
	        writeToFile(writer, lines);
	    }
	    
	    if (lines instanceof DataStoreLarge) {
	    	((DataStoreLarge) lines).rename(fileName, fileName + "~");
	    } else if (backup) {
	    	Parameters.renameFile(fileName, fileName + "~");		    
	    }


    	Parameters.renameFile(oFname, fileName);
	    
	   // firePropertyChange("Finished", null, null);
	}
	
	
	/**
	 * Writes the lines to using supplied writer
	 * @param writer writer to write lines to
	 * @param pLines linesto be written
	 *
	 * @throws IOException any error that occurs
	 */
	private void writeToFile(AbstractLineWriter writer, List<AbstractLine> pLines)
	throws IOException {
	    int i, numLines;
	    int k = 0;
	    double pct;
	    ProgressDisplay progress = null;
	    
	    lastTime = System.nanoTime();
	    numLines = pLines.size();
    	if (numLines > 30000) {
    		progress = new ProgressDisplay("Writing", fileName);
    	}

	    writer.setLayout(layout);
	    
	    if (pLines instanceof DataStore) {
	    	DataStore ds = (DataStore) pLines;
	
		    for (i = 0; i < pLines.size(); i++) {
		        writer.write(ds.getTempLine(i));
		        check(progress, i, numLines);
		    }	
	    } else {
		    for (i = 0; i < pLines.size(); i++) {
		        writer.write(pLines.get(i));
		        check(progress, i, numLines);
		    }  	
	    }

	    writer.close();
	    
	    if (progress != null) {
	    	progress.done();
	    }
	    
	    done = true;
	}

	
	private void check(ProgressDisplay progress, int soFar, int total) {

		if (progress != null && count++ > 2000) {
			count = 0;

			long t = System.nanoTime();
			double ratio;

			if (t - lastTime > INTERVAL) {
				ratio = (((double) soFar) / ((double) total));
				progress.updateDisplay(
						    " Written: " + soFar
						+ "\n   Total: " + total,
						(int) (100 * ratio));
				lastTime = t;
			}
		}
	}


	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

}
