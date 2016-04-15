package net.sf.RecordEditor.test.bldSalesArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.ICopybookDialects;




//
//   Program to write a Test File.
//   It uses a version of cb2xml used in the RecordEditor
//   to Read a Cobol File (the file also comes in the RecordEditor).
//


public class WriteSales5 {

	private static final int SKU_COUNT  = 15;
	private static final int SKU_SALE_COUNT  = 5;
	private static final int DEPT_COUNT = 5;
	private static final int ORDER_COUNT = 30;

	
	
	private static final int KEYCODE_IDX = 3;
	private static final int DEPARTMENT_IDX = 2;
	private static final int STORE_IDX = 1;


    private String installDir      = "G:\\Users\\Bruce01\\RecordEditor_HSQL\\SampleFiles\\";
    private String salesFile       = installDir + "DTAR020_Sorted.bin";
    private String salesFileOut    = installDir + "StoreSales5.txt";
    private String inCopybookName  = getFullName("DTAR020.cbl");
    private String outCopybookName = getFullName("StoreDtls.cbl");
    
    private AbstractLineWriter writer;
    private LayoutDetail outLayout;
    private AbstractLine outLine;
    private String spaceStr;
    
    private int skuSaleIdx = -1;
    private int skuIdx = 0;
    private int deptIdx = -1;
    private int orderIdx = -1;
   // FileOutputStream out;

    //Writer w ;

    //HtmlGenerator generator;


//    StoreSales5.Store.Builder bldStore = null;
//    StoreSales5.Deptartment.Builder bldDept = null;
//    StoreSales5.Product.Builder bldProd = null;
//    StoreSales5.Order.Builder order = null;
//    //StoreSales5.Summary.Builder deptSummary = null;
//    //StoreSales5.Summary.Builder storeSummary= null;
//
//
//    StoreSales5.Store aSale = null;

    int lastStore = -1;
    int lastDept = -1;
    int lastKeycode = -1;
    int store, dept, qty, keycode;
    BigDecimal price;

    int[] sumQty = {0, 0, 0, 0};
    int[] sumCount = {0, 0, 0, 0};
    long[] sumPrice = {0, 0, 0, 0};

    /**
     * Example of LineReader / LineWrite classes
     */
    @SuppressWarnings("unchecked")
	private WriteSales5() {
        super();

        String saleType;
//        StoreSales5.Product.SaleType saleType;
        int lineNum = 0;

        AbstractLine saleRecord;

        try {
            int fileStructure = Constants.IO_FIXED_LENGTH;
            CobolIoProvider ioProvider = CobolIoProvider.getInstance();
            AbstractLineReader<LayoutDetail> reader  = ioProvider.getLineReader(
                   fileStructure, ICopybookDialects.FMT_MAINFRAME,
                   CopybookLoader.SPLIT_NONE, inCopybookName, salesFile
            );
            int fldNum = 0;
            writer = ioProvider.getLineWriter(Constants.IO_TEXT_LINE, salesFileOut);
            outLayout = new CobolCopybookLoader().loadCopyBook(outCopybookName, CopybookLoader.SPLIT_NONE, 0, "", ICopybookDialects.FMT_MAINFRAME, 0, null)
            		.asLayoutDetail();
            char[] spaces = new char[outLayout.getMaximumRecordLength()];
   

            AbstractRecordDetail.FieldDetails keycodeField = reader.getLayout().getField(0, fldNum++);
            AbstractRecordDetail.FieldDetails storeField = reader.getLayout().getField(0, fldNum++);
            
            fldNum++;
            AbstractRecordDetail.FieldDetails deptField = reader.getLayout().getField(0, fldNum++);

            AbstractRecordDetail.FieldDetails qtyField     = reader.getLayout().getField(0, fldNum++);
            AbstractRecordDetail.FieldDetails salesField   = reader.getLayout().getField(0, fldNum++);

            Arrays.fill(spaces, ' ');
            spaceStr = new String(spaces);
            outLine = new Line(outLayout);
            outLine.setData(spaceStr);
            
            for (int i = 0; i < outLayout.getRecord(0).getFieldCount(); i++) {
            	System.out.println("\t" + outLayout.getRecord(0).getField(i).getName());
            }

            //out = new FileOutputStream(salesFileOut);

            //w = new FileWriter(salesFileOut + ".html");

            //generator = new HtmlGenerator(w);


            while ((saleRecord = reader.read()) != null) {
            	store = saleRecord.getFieldValue(storeField).asInt();
            	dept = saleRecord.getFieldValue(deptField).asInt();
            	keycode = saleRecord.getFieldValue(keycodeField).asInt();
            	qty = saleRecord.getFieldValue(qtyField).asInt();
                price = saleRecord.getFieldValue(salesField).asBigDecimal();

            	if (store != lastStore) {
            		storeChanged();
            	} else if (dept != lastDept) {
            		 deptChanged() ;
             	} else if (keycode != lastKeycode) {
             		keycodeChanged() ;
             	}

            	saleType = "S";
            	if (qty ==0) {
            		saleType = "O";
            	} else if (qty < 0) {
            		saleType = "R";
            	}

            	skuSaleIdx += 1;
            	System.out.println("--> " + saleType.toString() + " " + saleType + " " + keycode + ">> " + deptIdx + ", " + skuIdx + ", " + skuSaleIdx);
            	if (deptIdx < DEPT_COUNT && skuIdx < SKU_COUNT && skuSaleIdx < SKU_SALE_COUNT) {
            		lineNum += 1;
            		String idxStr = " (" + deptIdx + ", " + skuIdx + ", " + skuSaleIdx + ")";
            	
            		outLine.getFieldValue("keycode-1 (" + deptIdx + ", " + skuIdx + ")").set(keycode);
            		outLine.getFieldValue("Qty-1" + idxStr).set(qty);
            		outLine.getFieldValue("Price-1" + idxStr).set(price);
            		outLine.getFieldValue("trans-type" + idxStr).set(saleType);

                    sumCount[KEYCODE_IDX] += 1;
                    sumQty[KEYCODE_IDX] += qty;
                    sumPrice[KEYCODE_IDX] += price.longValue();
               }
 
                lastStore = store;
                lastDept = dept;
                lastKeycode = keycode;
           }

            storeChanged();

            reader.close();
            writer.close();
            System.out.println("Written: " + lineNum );
            //generator.print("</body></html>");
            //w.close();
         } catch (Exception e) {
            System.out.println("~~> " + lineNum + " " + e.getMessage());
            System.out.println();

            e.printStackTrace();
        }
    }

    private void storeChanged() throws IOException, RecordException {
    	
    	if (lastStore >= 0) {
    		writer.write(outLine);
    	}

 		outLine.setData(spaceStr);
 		
 		deptIdx = -1;
 		orderIdx = -1;
    	deptChanged();
    	

		//bldStore = StoreSales5.Store.newBuilder();
 		outLine.getFieldValue("Store-Num").set(store);
 		outLine.getFieldValue("Store-Name").set("Store: " + store);
 		
		inc(STORE_IDX);
    }

    private void deptChanged() throws RecordException {
    	keycodeChanged();
  		
		deptIdx += 1;
    	if (deptIdx < DEPT_COUNT) {
    		outLine.getFieldValue("Department-Num (" + deptIdx + ")").set(dept);
    		outLine.getFieldValue("Department-Name (" + deptIdx + ")").set("Department: " + dept);
    	}

		skuIdx = 0;
		orderIdx = -1;
		inc(DEPARTMENT_IDX);
    }

    private void keycodeChanged() throws RecordException {
    	
    	if (lastKeycode >= 0 && skuIdx < SKU_COUNT) {
    		orderIdx += 1;
    		if (sumQty[KEYCODE_IDX] > 0 && orderIdx < ORDER_COUNT) {
        			outLine.getFieldValue("keycode-3 (" + orderIdx + ")").set(lastKeycode);
    			outLine.getFieldValue("Qty-3 (" + orderIdx + ")").set(sumQty[KEYCODE_IDX]);
     		}
//			outLine.getFieldValue("keycode-1 (" + deptIdx + ", " + skuIdx  + ")").set(lastKeycode);
 //           bldDept.addProduct(bldProd);

    		skuSaleIdx = -1;
    		skuIdx += 1;
    		inc(KEYCODE_IDX);
    	}
 
    }

//    private StoreSales5.Summary.Builder buildSummary(int idx) {
//    	return StoreSales5.Summary.newBuilder()
//    		.setCount(sumCount[idx])
//    		.setPrice(sumPrice[idx])
//    		.setQuantity(sumQty[idx]);
//    }

    private void inc(int idx) {

  		sumQty[idx - 1] += sumQty[idx];
		sumPrice[idx - 1] += sumPrice[idx];
		sumCount[idx - 1] += sumCount[idx];

		sumQty[idx] = 0;
		sumPrice[idx] = 0;
		sumCount[idx] = 0;
    }
    

    public static String getFullName(String filename) {
    	return WriteSales5.class.getResource(filename).getFile();
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new WriteSales5();
	}

}
