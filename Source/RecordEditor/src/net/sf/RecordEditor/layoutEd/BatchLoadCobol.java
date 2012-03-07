package net.sf.RecordEditor.layoutEd;

import net.sf.RecordEditor.layoutEd.Record.LoadCobolIntoDB;
import net.sf.RecordEditor.layoutEd.utils.LoadCobolParseArgs;
import net.sf.RecordEditor.utils.common.Common;

public class BatchLoadCobol {


	/**
	 * Load cobol File / directory into the RecordEditor DB's
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			writeMessage();
			return;
		}

		int db = Common.getConnectionIndex();
		
		LoadCobolParseArgs a = new LoadCobolParseArgs(args);
		LoadCobolIntoDB load = new LoadCobolIntoDB();
		int sys = LoadCobolIntoDB.getSystemId(db, a.systemName);
		String file = a.file;
		
		if (a.dir == null || "".equals(a.dir)) {
			if (a.file == null || "".equals(a.file)) {
				writeMessage();
				return;
			}
		} else {
			file =  a.dir;
		}
		System.out.println("Loading: " + file 
				         + " RegExp" + a.regExp
				         + " Split: " + a.split 
				         + " Font: " + a.font
				         + " Compiler:" + a.compiler );
		load.load(db, file, a.compiler, a.split, sys, a.font, a.regExp);
		
		Common.setDoFree(true, db);
		Common.closeConnection();
	}
	
	public static void writeMessage() {
		System.out.println();
		System.out.println("Required Parameters:");
		System.out.println("--------------------");
		System.out.println("");
		System.out.println("    -f file                or    -d directory");
		System.out.println("");
		System.out.println("Optional Parameters:");
		System.out.println("--------------------");
		System.out.println("");
		System.out.println("    -font        font name (leave out for normal text, cp037 for EBCIDIC)");
		System.out.println("    -s           system name");
		System.out.println("    -o, -split   split options  01 or redefines");
		System.out.println("    -r           regular expression to check against member name");
		System.out.println("    -c           compiler (or binary format) current values:");
		System.out.println("                    Intel");
		System.out.println("                    Mainframe");
		System.out.println("                    Fujitsu");
		System.out.println("                    Big-Endian_(Old)");
		System.out.println("                    Open_Cobol_Little_Endian_(Intel)");
		System.out.println("                    Open_Cobol_bs2000_Little_Endian_(Intel)");
		System.out.println("                    Open_Cobol_MVS_Little_Endian_(Intel)");
		System.out.println("                    Open_Cobol_Micro_Focus_(Intel)");
		System.out.println("                    Open_Cobol_Big_Endian");
		System.out.println("                    Open_Cobol_bs2000_Big_Endian");
		System.out.println("                    Open_Cobol_MVS_Big_Endian");
		System.out.println("                    Open_Cobol_Micro_Focus_Big_E");
	}

}
