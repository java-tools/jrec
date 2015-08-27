package net.sf.RecordEditor.utils.common;

import java.util.ArrayList;
import java.util.List;

public final class SuppliedSchemas {

	private static final String[] SYSTEM_SCHEMAS = {
		"Unknown Format",
		"Unknown Fujitsu VB",
		"Unknown Mainframe VB",
		"Unknown Mainframe VB Dump",
		"Unknown Open Cobol VB",
		"Unknown Text IO",
		"Unknown Text UTF-16",
		"Unknown Text UTF-8",
		"Comma Delimited, names on the first line",
		"Comma Delimited, names on the first line, Quote=d",
		"Comma Delimited, names on the first line, Quote=s",
		"Comma Delimited names on the first line",
		"Comma Delimited names on the first line, Quote=d",
		"Comma Delimited names on the first line, Quote=s",
		"Generic CSV - enter details",
		"Tab Delimited, names on the first line",
		"Tab Delimited, names on the first line, quote=d",
		"Tab Delimited, names on the first line, quote=s",
		"Tab Delimited names on the first line",
		"Tab Delimited names on the first line, quote=d",
		"Tab Delimited names on the first line, quote=s",
		"XML - Build Layout",
		"FileWizard",
		"GetText_PO",
		"TipDetails",
	};
	private static final String[] SAMPLE_SCHEMAS = {
		"Line_Test_Group",
		"Line_Test_Record",
		"XMPLDECIDER-Product-Detail-1",
		"XMPLDECIDER-Product-Detail-2",
		"XMPLDECIDER-Product-Header",
		"XmplDecider",
		"XmplEditType1",
		"DCR0470 I51",
		"DCR0470 I52",
		"DCR0470 O21",
		"DCR0470 P41",
		"DCR0470 S11",
		"DCR0470 S12",
		"DCR0470 S13",
		"DCR0470 S14",
		"DCR0470 T31",
		"EDI ASN (DCR0470)",
		"EDI PO",
		"EDI Sales",
		"IVR0075D",
		"IVR0075H",
		"IVR0075S",
		"PO Detail",
		"PO Master",
		"SAR4180A",
		"SAR4180B",
		"SAR4180C",
		"Price",
		"PriceR 1",
		"PriceR 2",
		"PriceR 3",
		"PriceR 4",
		"PriceR 5",
		"PriceR 6",
		"PriceR 8",
		"PriceR 9",
		"PriceR D",
		"PriceR F",
		"PriceR L",
		"SPL",
		"SPL 1",
		"SPL 3",
		"SPL 4",
		"SPL 6",
		"SPL 8",
		"SPL End",
		"SPL HD",
		"SPL M",
		"SPL N",
		"AmsLocation",
		"PO Head",
		"ams PO Download",
		"ams PO Download: Allocation",
		"ams PO Download: Detail",
		"ams PO Download: Header",
		"ams Rct Upload FH Header",
		"ams Rct Upload: FT footer",
		"ams Rct Upload: RD",
		"ams Rct Upload: RH",
		"ams Receipt",
		"ams Receipt (Taret Fields only)",
		"ams Receipt AP",
		"ams Receipt AR",
		"ams Receipt AS",
		"ams Receipt FH Header",
		"ams Receipt FT File Trailer",
		"ams Receipt RD Recipt Product",
		"ams Receipt RH Receipt Header",
		"ams Receipt RS Recipt Store",
		"ams Receipt SC",
		"ams Receipt SO",
		"ams Shipping Upload",
		"ams Store",
		"ams Vendor Download",
		"ams shp Upload AP",
		"ams shp Upload AR",
		"ams shp Upload DH",
		"ams shp Upload DI",
		"ams shp Upload DO",
		"ams shp Upload DP",
		"ams shp Upload DS",
		"ams shp Upload FH Header",
		"ams shp Upload FT",
		"zzAms PO Download",
		"Master_Record",
		"Rental_Record",
		"Transaction_Record",
		"XfeDTAR020",
		"XfeDTAR020_reverse",
		"DTAR020",
		"DTAR1000 VB",
		"DTAR1000 VB Dump",
		"DTAR107",
		"DTAR119",
		"DTAR192",
		"Mainframe FB80",
		"Mainframe FB80 record",
		"Mainframe Text",
		"xDTAR1000 VB",
		"zzzCsvTest3",
		"zzzCsvTest4",
		"zzzCsvTest5",
	};
	
	private SuppliedSchemas() { }

	
	public static List<String> getSuppliedSchemas() {
		ArrayList<String> ret = new ArrayList<String>(SYSTEM_SCHEMAS.length + SAMPLE_SCHEMAS.length);
		
		addToList(ret, SYSTEM_SCHEMAS);
		addToList(ret, SAMPLE_SCHEMAS);
		
		return ret;
	}
	
	public static List<String> getLcSuppliedSchemas() {
		List<String> ret = getSuppliedSchemas();
		
		for (int i = 0; i < ret.size(); i++) {
			ret.set(i, ret.get(i).toLowerCase());
		}
		
		return ret;
	}
	
	public static String getSampleSchemasAsSqlList() {
		StringBuilder b = new StringBuilder();
		String sep = "";
		
		for (String d : SAMPLE_SCHEMAS) {
			b.append(sep)
			 .append("'").append(d).append("'")
			;
			sep = ",";
		}
		return b.toString();
	}
	
	
	private static void addToList(List<String> list, String[] data) {
		for (String d : data) {
			list.add(d);
		}
	}
	
}
