package net.sf.RecordEditor.layoutEd.Record;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ReConnection;

public class LoadCobolIntoDB {

	private Pattern pattern;
	public void load(int dbIdx, String dir, int binaryFormat, int split, int systemId, String fontname, String regExp) throws Exception {
        ExternalRecord rec;
		CopybookLoader loader = CopybookLoaderFactoryDB.getInstance()
			.getLoader(CopybookLoaderFactoryDB.COBOL_LOADER);
		
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (pattern != null) {
					if (pattern.matcher(name).find()) {
						return true;
					}
					return false;
				}
				return ! name.toLowerCase().endsWith(".xml");
			}
		};


		File dirFile = new File(stripStar(dir));
		
		ExtendedRecordDB dbTo = new ExtendedRecordDB();
		dbTo.setConnection(new ReConnection(dbIdx));
		boolean free = dbTo.isSetDoFree(false);
		
		pattern = null;
		
		if (regExp != null && ! "".equals(regExp)) {
			pattern = Pattern.compile(regExp);
		}
		
		System.out.println("Pattern: " + regExp);
		
		try {

			if (dirFile.isDirectory()) {
				for (File cobolFile : dirFile.listFiles(filter)) {
					try {
						rec = loader.loadCopyBook(cobolFile.getPath(),
							split,
			                dbIdx,
			                fontname,
			                binaryFormat,
			                systemId,
			                Common.getLogger());
						dbTo.checkAndUpdate(rec);
						
						Common.logMsg(AbsSSLogger.SHOW, "Loading " + cobolFile.getPath(), null);
					} catch (Exception e) {
						Common.logMsg(e.getMessage(), e);
					}
				}
		
			} else if (dirFile.isFile()) {
				System.out.println("File: " + dir);
				rec = loader.loadCopyBook(dirFile.getCanonicalPath(),
						split,
		                dbIdx,
		                fontname,
		                binaryFormat,
		                systemId,
		                Common.getLogger());
					dbTo.checkAndUpdate(rec);

			} else {
				System.out.println("Unsupported: " + dir + " " + dirFile.isFile() + " " + dirFile.getCanonicalPath());
			}
		} finally {
			dbTo.setDoFree(free);
		}
	}
	
	
	public static int getSystemId(int db, String systemName) {
		int system = Integer.MIN_VALUE;
		
		if (systemName == null || "".equals(systemName)) return 0;
		
		TableRec aTbl;
		TableDB tblsDB = new TableDB();
		boolean found = false;
		int maxKey = 0;
		int cKey;
		
		try {
			tblsDB.setConnection(new ReConnection(db));
			tblsDB.resetSearch();
			tblsDB.setParams(Common.TI_SYSTEMS);
			tblsDB.open();
			while ((aTbl = tblsDB.fetch()) != null) {
				cKey = aTbl.getTblKey();
				
				if (systemName.equalsIgnoreCase(aTbl.getDetails())) {
					system = cKey;
					found = true;
					break;
				}
				if (maxKey < cKey) {
					maxKey = cKey;
				}
			}
		
			if (! found) {
				system = maxKey + 1;
				tblsDB.insert(new TableRec(system, systemName));
			}
		} finally {
			tblsDB.close();
		}

		return system;
	}
	
	private static String stripStar(String dir) {
		return Parameters.dropStar(dir);
	}
}
