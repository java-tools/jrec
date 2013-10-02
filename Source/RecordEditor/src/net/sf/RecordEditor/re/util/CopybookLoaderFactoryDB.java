/*
 * @Author Bruce Martin
 * Created on 22/01/2007
 *
 * Purpose:
 * Generate a copybook loader based on the
 * loader id (index) supplied by the calling program.
 * It will also provide a Copybook loader name if required
 */
package net.sf.RecordEditor.re.util;

import java.util.HashMap;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalConversion;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.External.Def.AbstractConversion;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.re.db.Table.TableList;
import net.sf.RecordEditor.re.db.Table.TypeList;
import net.sf.RecordEditor.re.jrecord.types.ReTypeManger;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

/**
 * Generate a copybook loader based on the
 * loader id (index) supplied by the calling program.
 * It will also provide a Copybook loader name if required
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("unchecked")
public class CopybookLoaderFactoryDB extends CopybookLoaderFactory
								  implements AbstractConversion {

   //public static final int COBOL_LOADER = 1;

	public static int currentDB = -1;


	private static HashMap<String, Integer>[] typeConv = new HashMap[Constants.NUMBER_OF_COPYBOOK_SOURCES];
	private static HashMap<String, Integer>[] formatConv = new HashMap[Constants.NUMBER_OF_COPYBOOK_SOURCES];
	private static String[][] typeNames = new String[Constants.NUMBER_OF_COPYBOOK_SOURCES][];
	private static String[][] formatNames = new String[Constants.NUMBER_OF_COPYBOOK_SOURCES][];
//	private static ArrayList<BasicKeyedField>[] allTypes = new ArrayList[Constants.NUMBER_OF_COPYBOOK_SOURCES];

	static {
		for (int i = 0; i < typeConv.length; i++) {
			typeConv[i]   = null;
			formatConv[i] = null;
			typeNames[i]  = null;
			formatNames[i]= null;
//			allTypes[i] = null;
		}
	}

   //private String[] loaderName;
   //private int numberLoaded = 0;


//private static CopybookLoaderFactoryDB instance = new CopybookLoaderFactoryDB();


   /**
    * Create instance of copybook loader
    *
    */
   public CopybookLoaderFactoryDB() {
       this(NUMBER_OF_LOADERS);
   }


   /**
    * Create a Factory with a specified number of loaders
    * @param maximumNumberOfLoaders maximum number of loaders allowed
    */
   private CopybookLoaderFactoryDB(final int maximumNumberOfLoaders) {
       super(maximumNumberOfLoaders);

       ExternalConversion.setStandardConversion(this);

       String s1 =  Parameters.getString(Parameters.INVALID_FILE_CHARS);
       String s2 = Parameters.getString(Parameters.FILE_REPLACEMENT_CHAR);
       ExternalConversion.setInvalidFileChars(s1, s2);
    		 // Parameters.getString(Parameters.INVALID_FILE_CHARS),
    		 //  Parameters.getString(Parameters.FILE_REPLACEMENT_CHAR));
   }


   /**
    * Register all loaders
    */
   protected void registerAll() {

       String copybookLoaderName;
       String copybookloaderClass;

       register("cb2xml XML Copybook (DB)", XmlCopybookLoaderDB.class, "");
       if (CobolCopybookLoader.isAvailable()) {
           register("Cobol Copybook (DB)", CobolCopybookLoaderDB.class, "");
       } else {
    	   register("Empty - Cobol placeholder", XmlCopybookLoaderDB.class, "");
       }
 	   register("RecordEditor XML Copybook",RecordEditorXmlLoader.class, "");

       registerStandardLoaders2();
  //     register("RecordEditor Csv Copybook (Comma Seperator)",CsvLayoutParser.Comma.class, "");
  //     register("RecordEditor Csv Copybook (Tab Seperator)",CsvLayoutParser.Tab.class, "");

       for (int i = 0; i < Parameters.NUMBER_OF_LOADERS; i++) {
           copybookLoaderName = Parameters.getString("CopybookLoaderName." + i);
           if (copybookLoaderName != null && ! "".equals(copybookLoaderName)) {
        	   try {
	               copybookloaderClass = Parameters.getString("CopybookloaderClass." + i);
	               if (copybookloaderClass != null && ! "".equals(copybookloaderClass)) {
	                   try {
	                       register(copybookLoaderName, (Class<? extends CopybookLoader>) Class.forName(copybookloaderClass), "");
	                   } catch (Exception e) {
	                       Common.logMsgRaw(e.getMessage(), e);
	                   }
	               }
        	   } catch (Exception e) {
				 Common.logMsgRaw(e.getMessage(), e);
        	   }
           }
       }
   }



   /**
    * @see net.sf.JRecord.External.Def.AbstractConversion#getFormat(int, java.lang.String)
    */
   @Override
   public int getFormat(int idx, String format) {
	   int val = 0;
	   idx = fixDbIdx(idx);
	   format = format.toLowerCase();

	   loadTypesFormats(idx);
       if (typeConv[idx].containsKey(format)) {
           val = (typeConv[idx].get(format)).intValue();
       }

	   return val;
   }


   /**
    * @see net.sf.JRecord.External.Def.AbstractConversion#getType(int, java.lang.String)
    */
   @Override
   public int getType(int idx, String type) {
	   int typeVal = 0;
	   idx = fixDbIdx(idx);
       type = type.toLowerCase();

       loadTypesFormats(idx);
       if (typeConv[idx].containsKey(type)) {
           typeVal = (typeConv[idx].get(type)).intValue();
       }

	   return typeVal;
   }


   public static int fixDbIdx(int idx) {

	   if (idx < 0) {
		   idx = Common.getConnectionIndex();
	   }
	   return idx;
   }


//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.External.AbstractConversion#getTypes(int)
//	 */
//	@Override
//	public java.util.List<BasicKeyedField> getTypes(int idx) {
//		loadTypesFormats(idx);
//		return allTypes[idx];
//	}


/**
    * Load The Types
    * @param dbIdx database index
    */
   private static void loadTypesFormats(int dbIdx) {
   	if (typeConv[dbIdx] == null) {
   		ReTypeManger typeMgr = ReTypeManger.getInstance();
   		Integer key;
   		TypeList types = new TypeList(dbIdx, false, false, false);
   		String str;
   		//BasicKeyedField b;

   		typeConv[dbIdx] = new HashMap<String, Integer>();
   		typeNames[dbIdx] = new String[typeMgr.getNumberOfTypes()];
   		//allTypes[dbIdx] = new ArrayList<BasicKeyedField>(300);
   		types.loadData();
   		for (int i = 0; i < types.getSize(); i++) {
   			key = (Integer) types.getKeyAt(i);
   			str = toString(types.getFieldAt(i));
   			typeConv[dbIdx].put(str.toLowerCase(), key);
   			typeConv[dbIdx].put(key.toString(), key);
   			typeNames[dbIdx][typeMgr.getIndex(key.intValue())] = str;

//   			b = new BasicKeyedField();
//   			b.key = key;
//   			b.name = str;
//   			allTypes[dbIdx].add(b);
   		}

   		TableList formats =
   		new TableList(dbIdx, Common.TI_FORMAT, false, false,
	                Parameters.FORMAT_NUMBER_PREFIX, Parameters.FORMAT_NAME_PREFIX,
	                Parameters.NUMBER_OF_FORMATS);

   		formatConv[dbIdx] = new HashMap<String, Integer>();
   		formatNames[dbIdx] = new String[typeMgr.getNumberOfFormats()];
   		formats.loadData();
   		for (int i = 0; i < formats.getSize(); i++) {
   			key = (Integer) formats.getKeyAt(i);
   			str = toString(formats.getFieldAt(i));
   			formatConv[dbIdx].put(str.toLowerCase(), key);
   			formatConv[dbIdx].put(key.toString(), key);

   			formatNames[dbIdx][typeMgr.getFormatIndex(key.intValue())] = str;
   		}

   	}
   }


    @Override
	public String getFormatAsString(int idx, int format) {
		ReTypeManger typeMgr = ReTypeManger.getInstance();
		idx = fixDbIdx(idx);
		loadTypesFormats(idx);

		return formatNames[idx][typeMgr.getFormatIndex(format)];
	}


    /**
     * @see net.sf.JRecord.External.Def.AbstractConversion#getTypeAsString(int, int)
     */
	@Override
	public String getTypeAsString(int idx, int type) {
		int id = fixDbIdx(idx);
		ReTypeManger typeMgr = ReTypeManger.getInstance();

		if (id == ExternalConversion.USE_DEFAULT_DB) {
			id = currentDB;
			if (id < 0) {
				id = Common.getConnectionIndex();
			}
		}
		loadTypesFormats(id);

		return typeNames[id][typeMgr.getIndex(type)];
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.External.Def.AbstractConversion#isValid(int, int)
	 */
	@Override
	public boolean isValid(int idx, int type) {
		String s = getTypeAsString(idx, type);
		return s != null && ! "".equals(s);
	}

	private static String toString(Object o) {
		String ret = "";

		if (o != null) {
			ret = o.toString();
		}
		return ret;
	}

	/**
	 * @param currentDB the currentDB to set
	 */
	public static final void setCurrentDB(int currentDB) {
		CopybookLoaderFactoryDB.currentDB = currentDB;
	}

   /**
    * Cobol copybookloader that uses existing DB values
    * for the copybook
    *
    * @author Bruce Martin
    *
    */
   public static class CobolCopybookLoaderDB extends CobolCopybookLoader {

       /**
        * Cobol copybookloader that uses existing DB values
        * for the copybook
        */
       public CobolCopybookLoaderDB() {
           super(new XmlCopybookLoaderDB());
       }
   }


}
