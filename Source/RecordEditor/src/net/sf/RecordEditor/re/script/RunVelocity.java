/*
 * @Author Bruce Martin
 * Created on 31/01/2007 version 0.60
 *
 * Purpose:
 * Run velocity on a Record Based File
 */
package net.sf.RecordEditor.re.script;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.re.script.extensions.LanguageTrans;
import net.sf.RecordEditor.utils.TypeNameArray;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Run velocity on a Record Based File
 *
 * @author Bruce Martin
 *
 */
public class RunVelocity {

    private static RunVelocity instance = null;


    /**
     * process a Record Oriented File using a Velocity Template
     *
     * @param layout Record layout of the file
     * @param inputFile input file to process
     * @param template Velocity template to use
     * @param outputFile output filename
     *
     * @throws Exception any error that occurs in running velocity
     */
    public void processFile(@SuppressWarnings("rawtypes") AbstractLayoutDetails layout,
			String inputFile,
			String template,
			String outputFile)
    throws Exception {

        int[] records = {};

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf8"));
        processFile(layout,
                new DefaultLineProvider(),
                records,
                inputFile,
                outputFile,
                template,
                writer);
        writer.close();


    }


    /**
     * process a Record Oriented File using a Velocity Template
     *
     * @param layout Record layout of the file
     * @param lineProvider line provider to use to create Lines
     *        note use null for the standard Line
     * @param records Records to select
     * @param inputFile input file to process
     * @param template Velocity template to use
     * @param writer output writer where the generated template is to
     *        be written
     *
     * @throws Exception any error that occurs in running velocity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void processFile(AbstractLayoutDetails layout,
            				LineProvider lineProvider,
            				int[] records,
            				String inputFile,
               				String outputFile,
               				String template,
            				Writer writer)
    throws Exception {
        int  preferedLayout, i, recordsToCheck;
        AbstractLine line;
        ArrayList<AbstractLine> recordList = new ArrayList<AbstractLine>();
        boolean saveRecord;

        VelocityContext context   = new VelocityContext();
        AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();
        AbstractLineReader      reader = ioProvider.getLineReader(layout.getFileStructure(),
                											 lineProvider);

        ioProvider = new LineIOProvider(lineProvider);
        reader = ioProvider.getLineReader(layout.getFileStructure());

        recordsToCheck = 0;
        if (records != null) {
            recordsToCheck = records.length;
        }


        if (inputFile.endsWith(".gz")) {
            reader.open(new GZIPInputStream(new FileInputStream(inputFile)),
                    layout);
        } else {
            reader.open(inputFile, layout);
        }

        while ((line = reader.read()) != null) {
            preferedLayout = line.getPreferredLayoutIdx();

            saveRecord = recordsToCheck == 0;

            for (i = 0; i < recordsToCheck; i++) {
                if (preferedLayout == records[i]) {
                    saveRecord = true;
                    break;
                }
            }

            if (saveRecord) {
                recordList.add(line);
            }
        }

        reader.close();

        if (recordList.size() > 0) {
            context.put("records",  recordList);
            context.put("fileName", inputFile);
            context.put("layout",   layout);
            context.put("recordLayout",   layout);
            context.put("outputFile",   outputFile);
            context.put("recordLayout", layout);
            context.put("typeNames",    new TypeNameArray());
            context.put("onlyData",   Boolean.TRUE);
            context.put("showBorder", Boolean.TRUE);
            context.put("recordIdx",  Integer.valueOf(recordList.get(0).getPreferredLayoutIdx()));

            genSkel(template, writer, context);
         }
     }

    /**
     * Generate a Velocity template with the supplied records
     * @param template template file
     * @param recordList list of lines (or records to use)
     * @param inputFile input file the records came from
     * @param writer writer used to write the generated skelto
     *
     * @throws Exception any error that occurs
     */
    public final void genSkel(String template,
            ExternalRecord layout,
            TypeNameArray typeNames,
            String outputFile,
            Writer writer)
    throws Exception {
    	VelocityContext context   = new VelocityContext();

        context.put("outputFile",   outputFile);
        context.put("typeNames",    typeNames);
        context.put("recordLayout", layout);

        genSkel(template, writer,   context);
    }


    /**
     * Generate a Velocity template with the supplied records
     * @param template template file
     * @param data Data to pass to the Velocity template
     * @param writer writer used to write the generated skelto
     *
     * @throws Exception any error that occurs
     */
    public final void genSkel(String template,
    		ScriptData data,
            Writer writer)
    throws Exception {

        if (data.view != null) {
            VelocityContext context   = new VelocityContext();

            LanguageTrans.clear();

            context.put("records",    data.selectedLines);
            context.put("file",       data.fileLines);
            context.put("view",       data.viewLines);
            context.put("treeRoot",   data.root);
            context.put("treeNodes",  data.nodes);
            context.put("treeDepth",  data.treeDepth);
            context.put("fileName",   data.inputFile);
            context.put("outputFile", data.outputFile);
            context.put("layout",     data.view.getLayout());
            context.put("onlyData",   Boolean.valueOf(data.onlyData));
            context.put("showBorder", Boolean.valueOf(data.showBorder));
            context.put("recordIdx",  Integer.valueOf(data.recordIdx));
            context.put("RecordEditorData", data);

            genSkel(template, writer, context);
            //l.getPreferredLayoutIdx()
           // l.getFieldHex(recordIdx, fieldIdx)
           // l.getLayout().getRecord(0).getFieldCount();
           // l.getLayout().getField(0, 0).getType()
        }

    }

    /**
     * Generate a velocity skelton
     *
     * @param templateFile file to be generated
     * @param writer output writer
     * @param context variable definitions
     *
     * @throws Exception any error that occurs
     */
    public final void genSkel(String templateFile, Writer writer, VelocityContext context)
    throws Exception {

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
            VelocityEngine e = new VelocityEngine();
            int idx = templateFile.lastIndexOf(Common.FILE_SEPERATOR);
            if (idx > 0) {
                String s1 = templateFile.substring(0, idx);
                e.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, s1);
                templateFile = templateFile.substring(idx + 1);
                // System.out.println("}}} " + s1 + "< >" + templateFile);
            }
            e.init();

            template = e.getTemplate(templateFile, "UTF8");
        } catch (ResourceNotFoundException rnfe) {
            String msg = LangConversion.convert("Error - cannot find template:") + " " + templateFile;
            Common.logMsgRaw(msg, rnfe);
            throw new RecordException(true, msg);
        } catch (ParseErrorException pee) {
            String msg = LangConversion.convert("Syntax error in template") + " "
                + templateFile + ":" + pee;
            Common.logMsgRaw(msg, pee);
            throw new RecordException(true, msg);
        }

        /*
         *  Now have the template engine process your template using the
         *  data placed into the context.  Think of it as a  'merge'
         *  of the template and the data to produce the output stream.
         */


        if (template != null) {
            template.merge(context, writer);
        }

        /*
         *  flush and cleanup
         */

        writer.flush();
    }

    /**
     * @return Returns the instance.
     */
    public static RunVelocity getInstance() {
        if (instance == null) {
            instance = new RunVelocity();
        }
        return instance;
    }
}
