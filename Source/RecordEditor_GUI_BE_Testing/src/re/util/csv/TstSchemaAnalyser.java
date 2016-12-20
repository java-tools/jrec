package re.util.csv;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.RecordEditor.re.util.csv.SchemaAnalyser;

/**
 * Check that the SchemaAnalyser class gets the correct schemaType
 * 
 * @author Bruce Martin
 *
 */
public class TstSchemaAnalyser {

	SchemaDtls[] schemas = {
			new SchemaDtls(CopybookLoaderFactory.CB2XML_LOADER,
					"<copybook filename=\"AMSLOCATION.cbl\">"
					),
			new SchemaDtls(CopybookLoaderFactory.CB2XML_LOADER,
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"	<copybook filename=\"RightJust.cbl\">\n" +
					"		<item display-length=\"60\" level=\"01\" name=\"Tst-Record\" position=\"1\" storage-length=\"60\">"
					),
			new SchemaDtls(CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER,
					"<?xml version=\"1.0\" ?>" +
					"<RECORD RECORDNAME=\"ams PO Download\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" " +
					"RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\"><RECORDS>"
					),
			new SchemaDtls(CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER,
					"<RECORD RECORDNAME=\"ams PO Download\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" " +
					"RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\"><RECORDS>"
					),
			new SchemaDtls(CopybookLoaderFactory.COBOL_LOADER,
					"000900        03  DTAR020-KCODE-STORE-KEY. \n" +                                     
					"001000            05 DTAR020-KEYCODE-NO      PIC X(08).\n" +                       
					"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.  \n" +          
					"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.  \n"             
					),
			new SchemaDtls(CopybookLoaderFactory.COBOL_LOADER,
					"000900    01  DTAR020. \n" +                                     
					"000900        03  DTAR020-KCODE-STORE-KEY. \n" +                                     
					"001000            05 DTAR020-KEYCODE-NO      PIC X(08).\n" +                       
					"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.  \n" +          
					"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.  \n"             
					),
			new SchemaDtls(CopybookLoaderFactory.COBOL_LOADER,
					"        01  RD430-PROD-RECORD.\n" + 
					"            03  RD430-PROD-NO-X.\n" +                                      
					"                05 RD430-PROD-NO                 PIC 9(14).\n" +       
					"            03  RD430-PROD-BRAND-ID              PIC X(3).\n" +            
					"                88 RD430-PROD-BRAND-COLES        VALUE 'TAR'.\n"             
					),
	};
	
	@Test
	public void testSchemaIdentification() {
		for (SchemaDtls dtls : schemas) {
			assertEquals(dtls.text, dtls.schemaId, SchemaAnalyser.checkSchemaText(-1, dtls.text));
		}
	}

	
	private static class SchemaDtls {
		final String text;
		final int schemaId;
		
		public SchemaDtls(int schemaId, String text) {
			super();
			this.text = text;
			this.schemaId = schemaId;
		}
		
	}
}
