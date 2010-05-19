/*
 * @Author Bruce Martin
 * Created on 24/09/2005
 *
 * Purpose:
 *    Demonstrate creation of Lines
*
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.RecordEditor.utils.CopyBookDbReader;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


/**
 *
 * @author Bruce Martin
 *
 */
public class XmplLineBuilder {

    public static final String[] NULL_ARRAYS = {};

    private CopyBookDbReader copybookInterace = new CopyBookDbReader();

    private String sourceResource = TstConstants.SOURCE_RESOURCE;
    private String outputDir      = TstConstants.VELOCITY_OUTPUT_DIR;
    private String packageName    = "net.sf.RecordEditor.examples.genCode";
    //private String outputDir = "C:/t/genOutput/";


    private static String symbols = " :-_!@#$%^&*()[]{}~\'\"\\/+|<>.,?";
    private HashMap<String,Integer> fieldMap = new HashMap<String,Integer>();
    private String ending;

    private int arrayIndex, arrayListIndex;

    private String[] arrayList = null;

    private typeDetails[] arrayFields = null;


    /**
     * Generates classes to read / write and interface to a Field based
     * records and files. i.e. it generates the following classes
     * <ul compact>
     *   <li>A Line Class with optional getters an setters based on the
     *   <li>A line provider for generate line
     *   <li>A process class to process a file of the file of the supplied
     *       layout
     * </ul>
     *
     * @param typeName type name to be generated
     * @param extendProcess what class the generated process class
     *        should extend
     * @param arrays list of arrays to watch for
     * @param getAndSet wether to generate getters and setters
     * @param defineFields wether to define all the fields
     */
    public void genType(String typeName,
            			String extendProcess,
            			String[] arrays,
            			boolean getAndSet,
            			boolean defineFields) {
        int i;
        String jName;
        typeDetails dtls;

        String extendsValue       = "";
        ArrayList<typeDetails> recordList    = new ArrayList<typeDetails>();
        VelocityContext context = new VelocityContext();
        LayoutDetail details     = copybookInterace.getLayout(typeName);
        //RecordDetail[] records  = details.getRecords();

        arrayList = null;

        if (arrays != null && arrays.length > 0) {
            arrayList   = new String[arrays.length];
            arrayFields = new typeDetails[arrays.length];

            for (i = 0; i < arrays.length; i++) {
                arrayList[i] = arrays[i].toUpperCase();
            }
        }

        if (! extendProcess.equals("")) {
        	extendsValue = "extends " + extendProcess;
        }

        for (i = 0; i < details.getRecordCount(); i++) {
            //recName = details.getRecord(i).getRecordName();

            dtls = getRecordDetails(i, details.getRecord(i));
            recordList.add(dtls);

        }

        jName = convertToJavaName(details.getLayoutName(), false);
        jName = jName.substring(0, 1).toUpperCase() + jName.substring(1);

        context.put("records", recordList);
        context.put("recName", jName);
        context.put("dbRecName", details.getLayoutName());
        context.put("package", packageName);
        context.put("getAndSet", new Boolean(getAndSet));
        context.put("typeName", typeName);
        context.put("extendsValue", extendsValue);
        context.put("layout", details);
        context.put("defineFields", new Boolean(defineFields));

        try {
            Velocity.init(sourceResource + "velocity.properties");

            genSkel("Line",         jName + ".java",      context);
            //genSkel("Layout",       jName + "Layout.java",   context);
            genSkel("LineProvider", jName + "Provider.java", context);
            genSkel("LineProcess",  jName + "Process.java",  context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * create typeDetails record from a RecordLayout
     *
     * @param idx record index
     * @param record input record layout
     *
     * @return typeDetails record
     */
    private typeDetails getRecordDetails(int idx, RecordDetail record) {
        int j;
        typeDetails dtls, fld;
        String fldName;
        String recName = record.getRecordName();
        FieldDetail fieldDef;
       // FieldDetail[] fields = record.getFields();

        dtls = new typeDetails(recName, convertToJavaConst(recName),
                idx,
                record);

        dtls.list = new ArrayList<typeDetails>();
        dtls.arrayRecords  = new ArrayList<typeDetails>();

        //record.getFieldCount()
        for (j = 0; j < record.getFieldCount(); j++) {
            fieldDef = record.getField(j);
            fldName = fieldDef.getName();
            if ("FILLER".equals(fldName.toUpperCase())) {
            } else if (isArray(fldName)) {
                fldName = fldName.substring(0, arrayList[arrayListIndex].length());
                if (arrayIndex == 1) {
                    arrayFields[arrayListIndex]
                        = new typeDetails(fldName,
                                          "R" + idx + "_" + convertToJavaConst(fldName),
                                          j,
                                          fieldDef);
                    arrayFields[arrayListIndex].arrayPosIncrement
					= fieldDef.getPos();

                    arrayFields[arrayListIndex].arrayStart = j;
                } else if (arrayIndex == 2
                        && arrayFields[arrayListIndex] != null) {
                    arrayFields[arrayListIndex].arrayIncrement
                    	= j - arrayFields[arrayListIndex].arrayStart;
                    arrayFields[arrayListIndex].arrayPosIncrement
						= fieldDef.getPos() - arrayFields[arrayListIndex].arrayPosIncrement;

                    dtls.arrayRecords.add(arrayFields[arrayListIndex]);
                }
            } else {
                fld = new typeDetails(fldName,
                        "R" + idx + "_" + convertToJavaConst(fldName),
                        j,
                        fieldDef);
                dtls.list.add(fld);
            }
        }

        return dtls;
    }


    /**
     * Checks if supplied field is an array-field by comparing
     * the name with supplied Array-Fields
     *
     * @param name name of field to check
     *
     * @return wether it is an array
     */
    private boolean isArray(String name) {
        boolean ret = false;
        String rest;
        String nameUC = name.toUpperCase();
        int i;

        if (arrayList != null) {
            for (i = 0; i < arrayList.length; i++) {
                if (nameUC.startsWith(arrayList[i])) {
                    try {
                        rest = nameUC.substring(arrayList[i].length() + 1).trim();
                        arrayIndex = Integer.parseInt(rest);

                         arrayListIndex = i;
                        ret = true;
                        break;
                    } catch (Exception e) {
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Generate a velocity skelton
     *
     * @param templateFile file to be generated
     * @param outputFile output file
     * @param context variable definitions
     */
    private void genSkel(String templateFile, String outputFile, VelocityContext context) {
        try {
            /*
             *  get the Template object.  This is the parsed version of your
             *  template input file.  Note that getTemplate() can throw
             *   ResourceNotFoundException : if it doesn't find the template
             *   ParseErrorException : if there is something wrong with the VTL
             *   Exception : if something else goes wrong (this is generally
             *        indicative of as serious problem...)
             */

            Template template =  null;

            try {
                template = Velocity.getTemplate(sourceResource + templateFile + ".vm");
            } catch (ResourceNotFoundException rnfe) {
                System.out.println("Example : error : cannot find template " + templateFile);
            } catch (ParseErrorException pee) {
                System.out.println("Example : Syntax error in template "
                        + templateFile + ":" + pee);
            }

            /*
             *  Now have the template engine process your template using the
             *  data placed into the context.  Think of it as a  'merge'
             *  of the template and the data to produce the output stream.
             */

            BufferedWriter writer = new BufferedWriter(
                //new OutputStreamWriter(System.out));
            	new FileWriter(outputDir + outputFile));

            if (template != null) {
                template.merge(context, writer);
            }

            /*
             *  flush and cleanup
             */

            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Get Java Name
     *
     * @param name name to be converted
     *
     * @return java name
     */
    private String convertToJavaName(String name, boolean isField) {
        int i;
        StringBuffer buf = new StringBuffer(name.toLowerCase());
        String fldName, baseName;
        int count = 1;

        for (i = 0; i < symbols.length(); i++) {
            replace(buf, symbols.substring(i, i + 1));
        }
        buf.replace(0, 1, buf.substring(0, 1).toUpperCase());

        fldName = buf.toString();
        baseName = fldName;

        ending = "";

        if (isField && fieldMap.containsKey(fldName)) {
        	count = ((Integer) fieldMap.get(fldName)).intValue() + 1;
        	ending  = "" + count;
        	fldName += ending;
        }
        fieldMap.put(baseName, new Integer(count));

        return fldName;
    }


    /**
     * Replaces on string with another in a String bugffer
     *
     * @param in String buffer to be updated
     * @param from seqarch string
     */
    private static void replace(StringBuffer in, String from) {
        int start, j;
        String to;

        j = 0;
        start = in.indexOf(from, 0);
        while (start > 0) {
            j += 1;
            to = in.substring(start + 1, start + 2).toUpperCase();
            in.replace(start, start + 2, to);
            start = in.indexOf(from, start + 1);
        }
    }


    /**
     * Get Java Name
     *
     * @param name name to be converted
     *
     * @return java name
     */
    private String convertToJavaConst(String name) {
        int i;
        StringBuffer buf = new StringBuffer(name.toUpperCase());

        for (i = 0; i < symbols.length(); i++) {
            replace4const(buf, symbols.substring(i, i + 1));
        }
        replace4const(buf, "____");
        replace4const(buf, "___");
        replace4const(buf, "__");

        return buf.toString() + ending;
    }

    /**
     * Replaces on string with another in a String bugffer
     *
     * @param in String buffer to be updated
     * @param from seqarch string
     */
    private static void replace4const(StringBuffer in, String from) {
        int start, j;
        int len = from.length();
        String to = "_";

        j = 0;
        start = in.indexOf(from, 0);
        while (start > 0) {
            j += 1;
            in.replace(start, start + len, to);
            start = in.indexOf(from, start + len);
        }
    }


    /**
     * Stores details for generating lines
     *
     *
     * @author Bruce Martin
     *
     */
    public class typeDetails {
        public String name;
        public String javaName;
        public String javaConst;
        public int index;
        public Object details;
        public ArrayList<typeDetails> list     = null;
        public ArrayList<typeDetails> arrayRecords;

        public int arrayStart        = -1;
        public int arrayIncrement    = 0;
        public int arrayPosIncrement = 0;


        /**
         * Create type details for use by velicty
         *
         * @param pName Name
         * @param pIndex index
         * @param pDetails details
         */
        public typeDetails(final String pName,
                		   final String pJavaConst,
                		   final int pIndex,
                		   final Object pDetails) {

            name      = pName;
            javaName  = convertToJavaName(name, true);
            javaConst = pJavaConst; // convertToJavaConst(name);
            index     = pIndex;
            details   = pDetails;
        }


        /**
         * @return Returns the index.
         */
        public int getIndex() {
            return index;
        }


        /**
         * @return Returns the javaName.
         */
        public String getJavaName() {
            return javaName;
        }


        /**
         * @return Returns the details.
         */
        public Object getDetails() {
            return details;
        }


        /**
         * @return Returns the list.
         */
        public ArrayList<typeDetails> getList() {
            return list;
        }


        /**
         * @return Returns the javaConst.
         */
        public String getJavaConst() {
            return javaConst;
        }


        /**
         * @return Returns the arrayRecords.
         */
        public ArrayList<typeDetails> getArrayRecords() {
            return arrayRecords;
        }


        /**
         * @return Returns the arrayIncrement.
         */
        public int getArrayIncrement() {
            return arrayIncrement;
        }


        /**
         * @return Returns the arrayStart.
         */
        public int getArrayStart() {
            return arrayStart;
        }

        /**
         * Get the position increment
         *
         * @return position increment
         */
//		private int getArrayPosIncrement() {
//			return arrayPosIncrement;
//		}

		/**
		 * get the name
		 * @return name
		 */
        public String getName() {
            return name;
        }
    }


    /**
     * Testing line builder
     * @param args program args
     */
    public static void main(String[] args) {
        XmplLineBuilder builder = new XmplLineBuilder();

        builder.genType("CSV_Layout", "", XmplLineBuilder.NULL_ARRAYS, false, true);

//        builder.genType("ams PO Download", "", amsPoArrayFields, true, false);
//        builder.genType("ams Receipt", "", XmplLineBuilder.NULL_ARRAYS, false, true);
//        builder.genType("EDI PO", "", ediPoArrayFields, true, false);
    }

}
