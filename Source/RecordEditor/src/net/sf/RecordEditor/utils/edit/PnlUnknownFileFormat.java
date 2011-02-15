package net.sf.RecordEditor.utils.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.ByteIO.TextReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.LineIOProvider;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.tblModels.LineArrayModel;

/**
 * This class will <ul>
 * <li>Try to work out what type of file structure it is 
 * <li>Let the user make the final selection
 * </ul>
 * @author bm
 *
 */
@SuppressWarnings("serial")
public class PnlUnknownFileFormat extends BaseHelpPanel {
//    private static final int STANDARD_TABLE_CELL_WIDTH = 20;
//    private static final int COLUMNS_TO_NUMER = 10;
	
	private static final int TIP_HEIGHT  = SwingUtils.STANDARD_FONT_HEIGHT * 12;
    private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 14;
    private static final int COLUMNS_TO_NUMER = 10;
    
    private static final int LAST_7_BITS_SET = 127;
    private static final boolean[] ASCII_CHAR = init(new boolean[128], false);
    private static final boolean[] EBCDIC_CHAR = init(new boolean[256], false);
    
    static {
    	String s = Common.STANDARD_CHARS + "\n\t ;:?{}_=~|/!#";
    	for (int i = 0; i < s.length(); i++) {
    		ASCII_CHAR[s.charAt(i)] = true;
    	}
    	
    	try {
			byte[] bytes = s.getBytes("cp037");
			int j;
			for (int i = 0; i < bytes.length; i++) {
				j = toInt(bytes[i]);
				if (j > 32) {
					EBCDIC_CHAR[j] = true;
				}
	    	}
		} catch (UnsupportedEncodingException e) {
		}
    }

 //   private JEditorPane tips;
    private LineArrayModel fileMdl;
    
    private checkFile[] fileChecks = {
     		new VbDumpCheck(),
    		new StandardCheckFile(Constants.IO_VB, "cp037"),
    		new StandardCheckFile(Constants.IO_VB_FUJITSU),
    		new StandardCheckFile(Constants.IO_VB_OPEN_COBOL),
    		new BinTextCheck(),
    		new StandardCheckFile(Constants.IO_MICROFOCUS),
//    		new StandardCheckFile(Constants.IO_UNICODE_TEXT),
    };

    public final BmKeyedComboBox structureCombo = new BmKeyedComboBox(
    		new ManagerRowList(LineIOProvider.getInstance(), false), 
    		false
    );
    public final JButton goBtn = new JButton("Go");
    
    public final JTextField lengthTxt = new JTextField();
    public final JTextField fontNameTxt = new JTextField();
    private JTable fileTbl = new JTable();
    
    public final JTextArea message = new JTextArea();
    

    private byte[] fileData;
    
    protected int textPct;

    private ActionListener changed = new ActionListener() {
 	   public void actionPerformed(ActionEvent e) {
 		   
 		  readFile();
 	   }
    };

    private FocusAdapter focusHandler = new FocusAdapter() {
    	public void focusLost(FocusEvent e) {
    		readFile();
    	}
    };

    public PnlUnknownFileFormat() {
    	init_100_setupScreen(false);
    }
    
    public PnlUnknownFileFormat(final String fileName) throws IOException {
       	init_100_setupScreen(true);
        
    	open(fileName);
    }
    
    public PnlUnknownFileFormat(final BufferedInputStream in) throws IOException {
       	init_100_setupScreen(true);
        open(in);
   }
   
    public void open(final String fileName) throws IOException {
    	open(new BufferedInputStream(new FileInputStream(fileName)));
    }
        
   
   public void open(final BufferedInputStream in) throws IOException {	
    	init_200_loadFile(in);
    	
    }
    
   /**
    * Setup the screen
    */
    private void init_100_setupScreen(boolean showGoBtn) {
    	JEditorPane tips = new JEditorPane("text/html", "<h3>File Structure</h3>" 
    			+ "<p>This screen lets you select the File structure. <br>For Standard Windows / Unix files "
    			+ "use <b>Text IO</b>. <br>For Fixed width files, You can click on the Start of the second record "
    			+ "to set the length.");
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		this.setGap(BasePanel.GAP1);
 
    	this.addLine("File Structure", structureCombo);
    	this.addLine("Length", lengthTxt);
       	this.addLine("Font Name", fontNameTxt);
		this.setGap(GAP1);
		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				fileTbl);
		
		if (showGoBtn) {
			this.setGap(GAP2);
			this.addLine("", null, goBtn);
		}
		this.setGap(GAP1);
		this.addMessage(message);
		
		lengthTxt.setText("100");
		lengthTxt.addFocusListener(focusHandler);
		fontNameTxt.addFocusListener(focusHandler);
		structureCombo.addActionListener(changed);
		
		fileTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		fileTbl.addMouseListener(new MouseAdapter() {

			/**
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
	           int  row = fileTbl.rowAtPoint(e.getPoint());
	           int  length = fileTbl.columnAtPoint(e.getPoint());
	           
	           for (int i = 0; i < row; i++) {
	        	   //System.out.println(" --> " + i + " " + length);
	        	   length += fileMdl.getLine(i).length;
	           }
        	   //System.out.println(" --> " + (row - 1)  + " "+ length);
        	   
	           lengthTxt.setText(Integer.toString(length));
	           if (getFileStructure() == Constants.IO_FIXED_LENGTH) {
	        	   readFile();
	           }
			}
		});
    }
    
    /**
     * Load the file
     * @param in input file
     * @throws IOException any error that occurs
     */
    private void init_200_loadFile(final BufferedInputStream in) throws IOException {

    	if (in == null) {
    		fileData = new byte[0];
    		return;
    	}
    	int ebcdicCount, asciiCount;
    	fileData = new byte[Math.min(34000, in.available())];
    		
    	in.mark(fileData.length);
    	in.read(fileData);
    	in.reset();
    	
    	ebcdicCount = countTextChars(fileData, EBCDIC_CHAR);
    	asciiCount  = countTextChars(fileData, ASCII_CHAR);
    	if (fileData.length > 0) {
    		textPct = 100 * Math.max(ebcdicCount, asciiCount) / fileData.length;
    	}
    		
    	for (int i = 0; i < fileChecks.length; i++) {
			if (fileChecks[i].check(fileData)) {
				if (! "".equals(fileChecks[i].getFontName())) {
					fontNameTxt.setText(fileChecks[i].getFontName());
				}
				structureCombo.removeActionListener(changed);
				structureCombo.setSelectedItem(Integer.valueOf(fileChecks[i].getFileStructure()));
				structureCombo.addActionListener(changed);
				readFile();
				return;
			}
		}
    	structureCombo.removeActionListener(changed);
		structureCombo.setSelectedItem(Integer.valueOf(Constants.IO_FIXED_LENGTH));
		structureCombo.addActionListener(changed);
		
		if ((countTextChars(fileData, EBCDIC_CHAR) - countTextChars(fileData, ASCII_CHAR)) * 20 / fileData.length > 3) {
			fontNameTxt.setText("cp037");
		}
		
		readFile();
    }
    
    private void readFile() {
    	int structure = getFileStructure();
    	byte[][] lines = new byte[30][];
    	AbstractByteReader reader;
    	int count = 0;
        TableColumnModel tcm;
        TableColumn tc;
        String s;
   	
        
    	try {
    		switch (structure) {
    		case (Constants.IO_FIXED_LENGTH): 
    			if ("".equals(lengthTxt.getText())) {
    				message.setText("You Must enter a Length for fixed Length files");
    				lengthTxt.requestFocus();
    				return;
    			}
    			reader = new FixedLengthByteReader(Integer.parseInt(lengthTxt.getText()));
    			break;
    		case (Constants.IO_TEXT_LINE): 
       		case (Constants.IO_UNICODE_TEXT): 
       		case (Constants.IO_NAME_1ST_LINE): 
       		case (Constants.IO_XML_USE_LAYOUT): 
       		case (Constants.IO_XML_BUILD_LAYOUT): 
       			reader = new TextReader(fontNameTxt.getText());
       			break;
    		default:
    			reader = ByteIOProvider.getInstance().getByteReader(structure);
    		}
    		
    		if (reader == null) {
    			message.setText("Selected File Structue is not supported here");
    		} else {
	    		reader.open(new ByteArrayInputStream(fileData));
		    	while (count < lines.length && (lines[count++] = reader.read()) != null) {
		    	}
		    	reader.close();
		    	
		    	fileMdl = new LineArrayModel(lines, fontNameTxt.getText(), count - 1);
		    	
		    	fileTbl.setModel(fileMdl);
		    	
			    tcm  = fileTbl.getColumnModel();
	
		        for (int i = 0; i < fileMdl.getColumnCount(); i++) {
		            //colorInd[i] = 0;
		            tc = tcm.getColumn(i);
		        	//System.out.println(" ~~~> " + i + " " + tc.getCellRenderer());
		            tc.setPreferredWidth(SwingUtils.ONE_CHAR_TABLE_CELL_WIDTH);
		            
		            switch ((i + 1) % COLUMNS_TO_NUMER) {
	            	case (0): s = "" + ((i + 1) / COLUMNS_TO_NUMER); break;
	            	case (5): s = "+";                         break;
	            	default : s = " ";
		            }
		            tc.setHeaderValue(s);
		        }
		    	fileMdl.fireTableDataChanged();
    		}
	   	} catch (Exception e) {
			message.setText("Error Reading File: " + e.getMessage());
			e.printStackTrace();
		}
	   	
	   	lengthTxt.setEnabled(structure == Constants.IO_FIXED_LENGTH);
    }
    
    public int getFileStructure() {
    	return ((Integer) structureCombo.getSelectedItem()).intValue();
    }
 
    public int getLength() {
    	int ret = 10;
    	
    	try {
    		ret = Integer.parseInt(lengthTxt.getText());
    	} catch (Exception e) {
		}
    	return ret;
    }
    
    private static boolean[] init(boolean[] b, boolean val) {
    	for (int i = 0; i < b.length; i++) {
    		b[i] = val;
    	}
    	
    	return b;
    }
    
    private final static int countTextChars(byte[] data, boolean[] check) {
		int count = 0;
		int j;
		boolean last = false;
		int cc = 0;
		
		for (int i = 0; i < data.length; i++) {
			j = toInt(data[i]);
			if (j >= check.length) {
				last = false;
			} else {
    			if (last && check[j]) {
    				count += 1;
    				if (cc == 0) {
    					count += 1;
    				}
    				
    				cc += 1;
    			}
    			last = check[j];
			}
			if (! last) {
				cc = 0;
			}
		}
		
		return count;
    }

    
    private static int toInt(byte b) {
   	int i = b;
    	if (i < 0) {
    		i += 256;
    	}
    	return i;
    }
    
    
    private static interface checkFile {
    	public boolean check(byte[] data);
    	public int getFileStructure();
    	public String getFontName();
    }
    
    private static class StandardCheckFile implements checkFile {
    	private int structure;
    	private String font = "";
    	
       	/**
		 * @param fileStructure
		 */
		public StandardCheckFile(int fileStructure) {
			this.structure = fileStructure;
		}

		public StandardCheckFile(int fileStructure, String fontName) {
			this.structure = fileStructure;
			this.font = fontName;
		}

		public boolean check(byte[] data) {
       		boolean ret = true;
       		try {
       			int len = 0;
       			byte[] bytes;
       			AbstractByteReader reader = ByteIOProvider.getInstance().getByteReader(structure);
       			reader.open(new ByteArrayInputStream(data));
       			
       			for (int i = 0; 
       					i < 20 && len < data.length && ((bytes = reader.read()) != null);
       					i++) {
       				len += bytes.length;
       			}
       			reader.close();
       		} catch (Exception e) {
				ret = false;
			} 
       		return ret;
       	}
       	
    	public int getFileStructure() {
    		return structure;
    	}
    	
    	public String getFontName() {
    		return font;
    	}
    }
    
    private static class BinTextCheck extends StandardCheckFile {
    	public BinTextCheck() {
    		super(Constants.IO_BIN_TEXT);
    	}
    	
    	@Override
    	public boolean check(byte[] data) {
    		boolean ret = false;
    		if (data != null) {
	    		int count = countTextChars(data, ASCII_CHAR);
	    		
	    		//System.out.println(" }}} " + (count * 100 / data.length));
	    		if (count * 100 / data.length >= 70) {
	    			ret = super.check(data);
	    		}
    		}
    		
    		return ret;
    	}
    }
    
    private class VbDumpCheck extends StandardCheckFile {
    	
    	public VbDumpCheck() {
    		super(Constants.IO_VB_DUMP, "cp037");
    	}
    	
    	@Override
    	public boolean check(byte[] data) {
    		boolean ret = false;
    		if (data == null || data.length < 4) {
    		} else if (data[6] == 0 && data[7] ==0) {
    			int blockLength;
    			byte[] bdwLength;
       			if (data[0] >= 0) {
       				bdwLength = new byte[2];
    	            bdwLength[0] = data[0];
    	            bdwLength[1] = data[1];

    			} else {
      				bdwLength = new byte[4];
      				bdwLength[0] = (byte) (data[0] & LAST_7_BITS_SET);
       	            bdwLength[1] = data[1];
       	            bdwLength[2] = data[2];
       	            bdwLength[3] = data[3];
    			}
       			blockLength = (new BigInteger(bdwLength)).intValue();
       			if (blockLength + 8 < data.length) {
       				if (data[blockLength + 7] == 0 && data[blockLength + 8] ==0) {
       					ret = super.check(data);
       				}
       			} else {
       				ret = super.check(data);
       			}
    		}
    		return ret;
    	}
    }
}
