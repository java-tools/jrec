/*
 * @Author Bruce Martin
 * Created on 26/09/2005
 *
 * Purpose:
 * Process Ams Receipt File, this extends the Generated (by XmplLineBuilder)
 * class AmsReceiptProcess
 *
 * Requirements:
 * -------------
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import net.sf.RecordEditor.examples.genCode.AmsReceipt;
import net.sf.RecordEditor.examples.genCode.AmsReceiptProcess;

/**
 * Process Ams Receipt File, this extends the Generated (by XmplLineBuilder)
 * class AmsReceiptProcess
 * 
 * @author Bruce Martin
 *
 */
public class XmplProcessAmsReceipt extends AmsReceiptProcess {

    private String lastPO = "";

    private String[] poList = {
    		"380655", "374274", "386808", "386803", "386805", "373628",
			"372413", "372701", "386767", "386617", "388906", "391738",
			"373150", "391794", "370994", "372515", "386863", "372618",
			"372559", "386777", "385365", "371244", "371260", "372562",
			"386863", "372425", "384815", "377134", "389863", "372625",
			"375612", "380652", "372625", "376886", "388433", "372559",
			"373150", "373651", "392965", "386398", "373133", "390306",
			"372575", "373809", "375863", "392958", "383490", "390208",
			"374367", "394538", "374180", "394745", "374687", "377134",
			"385852", "370911", "371112", "387436", "388149", "387455",
			"370741", "370271", "370268", "370911", "387436", "388149",
			"393064", "374687", "370271", "389767", "388433"
    };

    private HashMap<String, String> poMap = new HashMap<String, String>();
    private BufferedWriter writer;

    /**
     * process receipt files
     *
     */
    public XmplProcessAmsReceipt() {
    	int i;
    	String id = "archive"; // "backup"
    	String dirName = TstConstants.EXAMPLE_FILE_DIRECTORY;
    	File dir = new File(dirName);
    	String[] fileName = dir.list();

    	for (i = 0; i < poList.length; i++) {
    		poMap.put(poList[i], poList[i]);
    	}

        try {
        	FileWriter fileWriter = new FileWriter("c:/t/POout_" + id + ".txt");
        	writer = new BufferedWriter(fileWriter);

        	for (i = 0; i < fileName.length; i++) {
        		if (fileName[i].startsWith("Ams_Receipt")) {
                    super.process(dirName + fileName[i]);
        		}
        	}
        	writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @see net.sf.RecordEditor.examples.genCode.AmsReceiptProcess#processAmsReceiptRsReciptStore
     * (examples.genCode.AmsReceipt)
     */
    public void processAmsReceiptRsReciptStore(AmsReceipt line)
    throws IOException {
        int rsRecordIdx = AmsReceipt.getAmsReceiptRsReciptStoreIndex();
        String po = line.getField(rsRecordIdx,
          	  AmsReceipt.R3_ORDER_NO).toString().trim();

        if (poMap.containsKey(po)) {
        	writer.write(
                         po
                + "\t" + line.getField(AmsReceipt.getAmsReceiptRsReciptStoreIndex(),
                        			   AmsReceipt.R3_PROD_NO)
                + "\t" + line.getField(rsRecordIdx,
         			   AmsReceipt.R3_STORE_NO)
                + "\t" + line.getField(rsRecordIdx,
         			   AmsReceipt.R3_OUTSTANDING_ORD_QTY)
                + "\t" + line.getField(rsRecordIdx,
         			   AmsReceipt.R3_ACT_RECV_QTY)
            );
        	writer.newLine();
        } else if (!lastPO.equals(po)) {
            lastPO = po;
        }
    }

    /**
     * process files
     *
     * @param args none
     */
    public static void main(String[] args) {
    		new XmplProcessAmsReceipt();
    }
}
