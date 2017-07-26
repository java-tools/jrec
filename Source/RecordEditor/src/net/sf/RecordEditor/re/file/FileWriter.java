package net.sf.RecordEditor.re.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.params.Parameters;


/**
 * Write (and backup a file
 * @author Bruce Martin
 *
 */
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
	
	
	@SuppressWarnings("rawtypes")
	public void doWrite() throws IOException {
		String oFname = fileName + ".~tmp~";
		int defaultBufSize = 256 * 256;
		long totalSize = ((long) lines.size()) * (layout.getMaximumRecordLength()>0?layout.getMaximumRecordLength():20);
		int bufSize = calcBufferSize(defaultBufSize, totalSize);
		
		if (Common.OPTIONS.overWriteOutputFile.isSelected() || (! backup) ) {
			if (backup && Parameters.JAVA_VERSION > 1.69999) {
				copyFile(fileName, fileName + "~");
			}

			write(fileName, defaultBufSize, bufSize);
			return;
		}
		
		write(oFname, defaultBufSize, bufSize);
	   	    
	    try {
			if (lines instanceof DataStoreLarge) {
				((DataStoreLarge) lines).rename(fileName, fileName + "~");
			} else if (backup) {
				Parameters.renameFile(fileName);		    
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	    lines = null;

	   
    	try {
			Parameters.renameFile(oFname, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			Common.logMsg("Error renaming new file to old file, file not saved:\n" + e,  e);
			
			copyFile(oFname, fileName);
		}
	    
	    
	   // firePropertyChange("Finished", null, null);
	}


	/**
	 * @param oFname
	 */
	private void copyFile(String oFname, String newName) {
		if (Parameters.JAVA_VERSION > 1.69999) {
			try {
				java.nio.file.Files.copy(java.nio.file.Paths.get(oFname), java.nio.file.Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void write(String oFname, int defaultBufSize, int bufSize) throws IOException {
	    if (isGZip) {
	        FileOutputStream fs = new FileOutputStream(oFname);
	        writer.open(new GZIPOutputStream(fs, bufSize));

	        writeToFile(writer, lines);
	        fs.close();
	    } else if (defaultBufSize == bufSize) {    	
	        writer.open(oFname);
	        writeToFile(writer, lines);
	    } else {
	    	FileOutputStream fs = new FileOutputStream(oFname);
	    	writer.open(new BufferedOutputStream(fs, bufSize));
	    	writeToFile(writer, lines);
	    }

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
	    int i = 0, numLines;
	    
	    System.out.println("Starting to write the file !!!");

	    ProgressDisplay progress = null;
	    
	    lastTime = System.nanoTime();
	    numLines = pLines.size();
	    
	    try {
	    	if (numLines > 30000) {
	    		progress = new ProgressDisplay("Writing", fileName);
	    		System.out.println("  -- Adding Progress !!!");
	    	}
	
		    writer.setLayout(layout);
		    
		    if (pLines instanceof IDataStore) {
		    	@SuppressWarnings("rawtypes")
				IDataStore ds = (IDataStore) pLines;
		    	System.out.println("  -- Is a datastore");
		
			    for (i = 0; i < pLines.size(); i++) {
			        writer.write(ds.getTempLineRE(i));
			        check(progress, i, numLines);
			    }	
		    } else {
		    	System.out.println("  -- Is a List");
			    for (i = 0; i < pLines.size(); i++) {
			        writer.write(pLines.get(i));
			        check(progress, i, numLines);
			    }  	
		    }
		    System.out.println("  -- File Written");
	    } catch (IOException e) {
	    	System.out.println();
	    	System.out.println("    **** Line:" + i + "IOError: " + e);
	    	System.out.println();
	    } catch (RuntimeException e) {
	    	System.out.println();
	    	System.out.println("    **** Line: " + i + "RunTime Error: " + e);
	    	System.out.println();
	    } finally {
		    writer.close();
		    
		    if (progress != null) {
		    	progress.done();
		    }
		    
		    done = true;
	    }
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

	/**
	 * @param bufSize
	 * @param fileLength
	 * @return
	 */
	public static int calcBufferSize(int bufSize, long fileLength) {
		if (fileLength > 600000000) {
			bufSize = 256 * 256 * 64;
		} else if (fileLength > 30000000) {
			bufSize = 256 * 256 * 16;
		} else if (fileLength > 5000000) {
			bufSize = 256 * 256 * 4;
		}
		return bufSize;
	}

}
