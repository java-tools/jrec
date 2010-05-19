/*
 * @Author Bruce Martin
 * Created on 28/09/2005
 *
 * Purpose:
 * This is an example of formatting selected files from a directory
 * using Velocity
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.io.File;
import java.io.FilenameFilter;

import net.sf.RecordEditor.examples.genCode.AmsPoDownload;
import net.sf.RecordEditor.examples.genCode.AmsPoDownloadProvider;
import net.sf.RecordEditor.examples.genCode.AmsReceipt;
import net.sf.RecordEditor.examples.genCode.AmsReceiptProvider;
import net.sf.RecordEditor.examples.genCode.EdiPo;
import net.sf.RecordEditor.examples.genCode.EdiPoProvider;
import net.sf.RecordEditor.utils.CopyBookDbReader;


/**
 * This is an example of formatting selected files from a directory
 * using Velocity
 *
 * @author Bruce Martin
 *
 */
public class XmplFileToVelocity1 {


    /**
     * Testing Velocity
     *
     * @param args program args
     */
    public static void main(String[] args) {
    	String backupDir = TstConstants.DATA_FILE_DIRECTORY;
        CopyBookDbReader copybookInterace = new CopyBookDbReader();
        FileToVelocity fileToVelocity     = new FileToVelocity(
                			    TstConstants.SOURCE_RESOURCE,
        					    TstConstants.VELOCITY_OUTPUT_DIR);

        AmsPoDownload.setCopyBook(copybookInterace);
        EdiPo.setCopyBook(copybookInterace);
        AmsReceipt.setCopyBook(copybookInterace);

        int[] useEdiPO   = {EdiPo.getIvr0075hIndex()};
        int[] useAmsPO   = {AmsPoDownload.getAmsPoDownloadHeaderIndex()};
         int[] useReceipt = {AmsReceipt.getAmsReceiptRhReceiptHeaderIndex()};

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String filename) {
                return filename.startsWith("tgtorders");
            }
        };
        FilenameFilter filterAmsPo = new FilenameFilter() {
            public boolean accept(File f, String filename) {
                return filename.startsWith("Ams_PODownload");
            }
        };
        FilenameFilter filterAmsReceipt = new FilenameFilter() {
            public boolean accept(File f, String filename) {
                return filename.startsWith("Ams_Receipt");
            }
        };



        fileToVelocity.processDirectory(
                copybookInterace.getLayout("EDI PO"),
                new EdiPoProvider(),
                useEdiPO,
                backupDir,
                filter,
                "EdiPoHtml",
                TstConstants.TEMP_DIRECTORY + "EdiPO.html");


        fileToVelocity.processDirectory(
                copybookInterace.getLayout("ams PO Download"),
                new AmsPoDownloadProvider(),
                useAmsPO,
                backupDir,
                filterAmsPo,
                "AmsPoHtml",
                TstConstants.TEMP_DIRECTORY + "AmsPO.html");

        fileToVelocity.processDirectory(
                copybookInterace.getLayout("ams Receipt"),
                new AmsReceiptProvider(),
                useReceipt,
                backupDir,
                filterAmsReceipt,
                "AmsReceiptHtml",
                TstConstants.TEMP_DIRECTORY +  "AmsReceiptPO.html");

        fileToVelocity.processDirectory(
                copybookInterace.getLayout("ams Receipt"),
                null,
                useReceipt,
                backupDir,
                filterAmsReceipt,
                "AmsReceiptHtml2",
                TstConstants.TEMP_DIRECTORY + "AmsReceiptPO2.html");
    }

}
