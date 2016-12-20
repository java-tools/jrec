package net.sf.RecordEditor.layoutEd.schema.ImportExport;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.JRecord.External.base.RecordEditorXmlWriter;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.common.SuppliedSchemas;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.params.Parameters;

public class SchemaBackup {

	private static final String BACKUP_DATE_VAR = "LastBackupDate";
	
	public static void backupSchema(final int dbIdx, final int recordId) {
		doSchemaBackup(dbIdx, recordId);
		
		new Thread(new Runnable() {	
			@Override public void run() {
				backupUsageOfSchema(dbIdx, recordId);
			}
		}).start();
	}
	
	private static void doSchemaBackup(int dbIdx, int recordId) {
		try {
			RecordRec rec = ExtendedRecordDB.getRecord(dbIdx, recordId);
			
			if (rec != null) {
				CopybookWriter writer = new RecordEditorXmlWriter();
				String buDirectory = Common.OPTIONS.schemaBuDir.get();
				Common.createDirectory(new File(buDirectory));
				
				writer.writeCopyBook(buDirectory, rec.getValue(), Common.getLogger());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void backupUsageOfSchema(int dbIdx, int recordId) {
		ChildRecordsDB childDb = new ChildRecordsDB();
		List<Integer> l;
		try {
			childDb.setConnection(new ReConnection(dbIdx));
			l = childDb.getRecordsThatUse(recordId);

			for (Integer rId : l) {
//				System.out.println(" ~~~ " + rId);
				doSchemaBackup(dbIdx, rId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			childDb.close();
		}

	}
	
	public static void backupAllSchemas() {
	
		try {
			String lbds = Parameters.getString(BACKUP_DATE_VAR);
			if (lbds == null || "".equals(lbds)) {
				runBu();
			} else {
				Date cd = new Date();
				int oneDay = 1000 * 60 * 60 * 24;
				long days = (cd.getTime() - Long.parseLong(lbds)) / oneDay;
				
				if (days > 14) {
					runBu();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void runBu() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				Set<String> suppliedSchemas = new HashSet<String>(SuppliedSchemas.getLcSuppliedSchemas());
				RecordRec r;
				String buDirectory = Common.OPTIONS.schemaBuDir.get();
				CopybookWriter writer = CopybookWriterManager.getInstance()
						.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER);

				ExtendedRecordDB dbFrom = new ExtendedRecordDB();
		
				int connectionIndex = Common.getConnectionIndex();
				CopybookLoaderFactoryDB.setCurrentDB(connectionIndex);
		
				boolean free = dbFrom.isSetDoFree(false);
		
				try {
					dbFrom.setConnection(new ReConnection(connectionIndex));
					dbFrom.resetSearch();
					dbFrom.setSearchListChar(AbsDB.opEquals, "Y");
		
					dbFrom.open();
		
		
					while ((r = dbFrom.fetch()) != null) {
						if (!suppliedSchemas.contains(r.getRecordName().toLowerCase())) {
							try {
								writer.writeCopyBook(buDirectory, r.getValue(), Common.getLogger());
							} catch (Exception e) {
								System.out.println("Backup of " + r.getRecordName() + " failed " + e);
							}
						}
					}
		
					Parameters.setProperty(BACKUP_DATE_VAR, Long.toString(new Date().getTime()));
				}	finally {
					dbFrom.setDoFree(free);
				}
			}
		}).start();
	}
}
