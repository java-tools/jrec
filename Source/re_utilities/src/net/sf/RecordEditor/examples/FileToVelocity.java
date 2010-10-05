/*
 * @Author Bruce Martin
 * Created on 28/09/2005
 *
 * Purpose:
 * This class will format a Record oriented File (ie a file of Lines)
 * using a velocity Template. You can use either generic Line class
 * or your own lines (requires a LineProvider)
 *
 */
package net.sf.RecordEditor.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.StandardLineReader;
import net.sf.JRecord.IO.LineIOProvider;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * This class will format<ul compact>
 * <li>a Record oriented File (ie a file of Lines) using
 * a velocity Template.
 * <li>Selected files in a directory using velocity
 * </ul>
 *
 * @author Bruce Martin
 *
 */
public class FileToVelocity {

    private String sourceResource = TstConstants.SOURCE_RESOURCE;
    //private String outputDir      = TstConstants.VELOCITY_OUTPUT_DIR;

    /**
     * This class will format a Record oriented File (ie a file of Lines)
     * using a velocity Template. You can use either
     * <ul compact>
     *  <li>Your own lines (you need to supply a LineProvider)
     *  <li>Standard Line's (LineProvider = NULL or system LineProvider)
     * </ul>
     */
    public FileToVelocity() {
        super();
        
        try {
            Velocity.init(sourceResource + "velocity.properties");
        } catch (Exception e) {
            System.out.println("Error Initialicing Velocity " + e.getMessage());
        }
    }



    /**
    * This class will format a Record oriented File (ie a file of Lines)
     * using a velocity Template. You can use either
     * <ul compact>
     *  <li>Your own lines (you need to supply a LineProvider)
     *  <li>Standard Line's (LineProvider = NULL or system LineProvider)
     * </ul>
     *
     * @param resource source resource directory
     * @param outputDirectory Output directory
     */
    public FileToVelocity(final String resource,
            			  final String outputDirectory) {
        this();

        sourceResource = resource;
    }


    /**
     * This method will format all files (Record Oriented Files)
     * in a directory a  using a Velocity Template
     *
     * @param layout Record layout of the file
     * @param lineProvider line provider to create the lines.
     *        <b>note</b> use null for the standard LineProvider
     * @param records Records to be sent to the supplied Template.
     *        This parameter allows you select which records to
     *        format with the Template
     * @param inputDirectory input directory to process
     * @param filter filename filter used to select which files
     *        to format
     * @param template Velocity template to format the files with
     * @param outputFile output filename that is to be written
     */
    public void processDirectory(LayoutDetail layout,
			LineProvider lineProvider,
			int[] records,
			String inputDirectory,
			FilenameFilter filter,
			String template,
			String outputFile) {

        try {
            int i;
            File dir = new File(inputDirectory);
            String[] fileList;
            BufferedWriter writer = new BufferedWriter(
                     new FileWriter(outputFile));

            if (filter == null) {
                fileList = dir.list();
            } else {
                fileList = dir.list(filter);
            }

            //System.out.println();
            //System.out.println(dir.getPath()
            //        + "    Found Files ~ " + (fileList != null));
            if (fileList != null) {
                for (i = 0; i < fileList.length; i++) {
                    //System.out.print("   " + fileList[i]);
                    processFile(layout,
                            	lineProvider,
                            	records,
                            	dir.getPath() + "\\" + fileList[i],
                            	template,
                            	writer);
                }
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * process a Record Oriented File using a Velocity Template
     *
     * @param layout Record layout of the file
     * @param lineProvider line provider to use
     * @param records Records to be sent to the supplied Template.
     *        This parameter allows you select which records to
     *        format with the Template
     * @param inputFile input file to process
     * @param template Velocity template to use
     * @param outputFile output filename
     */
    public void processFile(LayoutDetail layout,
			LineProvider lineProvider,
			int[] records,
			String inputFile,
			String template,
			String outputFile) {

        try {
            BufferedWriter writer = new BufferedWriter(
                     new FileWriter(outputFile));
            processFile(layout,
                    lineProvider,
                    records,
                    inputFile,
                    template,
                    writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

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
     */
    public void processFile(LayoutDetail layout,
            				LineProvider lineProvider,
            				int[] records,
            				String inputFile,
            				String template,
            				Writer writer) {
        int  preferedLayout, i, recordsToCheck;
        AbstractLine line;
        ArrayList<AbstractLine> recordList = new ArrayList<AbstractLine>();
        boolean saveRecord;

        VelocityContext context   = new VelocityContext();
        LineIOProvider ioProvider = LineIOProvider.getInstance();
        AbstractLineReader      reader = ioProvider.getLineReader(layout.getFileStructure(),
                											 lineProvider);

        ioProvider = new LineIOProvider(lineProvider);
        reader = ioProvider.getLineReader(layout.getFileStructure());

        recordsToCheck = 0;
        if (records != null) {
            recordsToCheck = records.length;
        }

        try {
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

            	genSkel(template, writer, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Generate a velocity skelton
     *
     * @param templateFile file to be generated
     * @param writer output writer
     * @param context variable definitions
     */
    private void genSkel(String templateFile, Writer writer, VelocityContext context) {
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


            if (template != null) {
                template.merge(context, writer);
            }

            /*
             *  flush and cleanup
             */

            writer.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
