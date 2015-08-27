package net.sf.RecordEditor.layoutWizard;

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

import javax.swing.JButton;
import javax.swing.JEditorPane;
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
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.LangConversion;
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

    private LineArrayModel fileMdl;

    public final BmKeyedComboBox structureCombo = new BmKeyedComboBox(
    		new ManagerRowList(LineIOProvider.getInstance(), false),
    		false
    );
    public final JButton goBtn = SwingUtils.newButton("Go");

    public final JTextField lengthTxt = new JTextField();
    public final FontCombo fontNameCombo = new FontCombo();
    private JTable fileTbl = new JTable();

    //public final JTextArea message = new JTextArea();


    private byte[] fileData;

    protected int textPct;


    private ActionListener changed = new ActionListener() {
 	   public void actionPerformed(ActionEvent e) {
 		   if (getFileStructure() == Constants.IO_FIXED_LENGTH) {
 			 FileAnalyser fa = FileAnalyser.getFixedAnalyser(fileData, lengthTxt.getText());

 			 lengthTxt.setText(Integer.toString(fa.getRecordLength()));
 	    	 if (! "".equals(fa.getFontName())) {
 				fontNameCombo.setText(fa.getFontName());
 			 }
 		   }
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
    	JEditorPane tips = new JEditorPane(
    			"text/html",
    			LangConversion.convertId(LangConversion.ST_MESSAGE, "UnknownFileFormatTip",
    				  "<h3>File Structure</h3>"
    				+ "<p>This screen lets you select the File structure. <br>For Standard Windows / Unix files "
    				+ "use <b>Text IO</b>. <br>For Fixed width files, You can click on the Start of the second record "
    				+ "to set the length."));
		this.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		this.setGapRE(BasePanel.GAP1);

    	this.addLineRE("File Structure", structureCombo);
    	this.addLineRE("Length", lengthTxt);
       	this.addLineRE("Font Name", fontNameCombo);
		this.setGapRE(GAP1);
		this.addComponentRE(1, 5, FILE_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				fileTbl);

		if (showGoBtn) {
			this.setGapRE(GAP2);
			this.addLineRE("", null, goBtn);
		}
		this.setGapRE(GAP1);
		this.addMessage(new JTextArea());

		//lengthTxt.setText("100");
		lengthTxt.addFocusListener(focusHandler);
		fontNameCombo.addFocusListener(focusHandler);
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
     	fileData = new byte[Math.min(34000, in.available())];

    	in.mark(fileData.length);
    	in.read(fileData);
    	in.reset();


    	FileAnalyser fa = FileAnalyser.getAnaylser(fileData, lengthTxt.getText());

    	if (! "".equals(fa.getFontName())) {
			fontNameCombo.setText(fa.getFontName());
		}
		structureCombo.removeActionListener(changed);
		structureCombo.setSelectedItem(Integer.valueOf(fa.getFileStructure()));
		structureCombo.addActionListener(changed);

		if (fa.getFileStructure() == Common.IO_FIXED_LENGTH) {
			lengthTxt.setText(Integer.toString(fa.getRecordLength()));
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
    				setMessageTxtRE("You Must enter a Length for fixed Length files");
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
       			reader = new TextReader(fontNameCombo.getText());
       			break;
    		default:
    			reader = ByteIOProvider.getInstance().getByteReader(structure);
    		}

    		if (reader == null) {
    			setMessageTxtRE("Selected File Structue is not supported here");
    		} else {
	    		reader.open(new ByteArrayInputStream(fileData));
		    	while (count < lines.length && (lines[count++] = reader.read()) != null) {
		    	}
		    	reader.close();

		    	fileMdl = new LineArrayModel(lines, fontNameCombo.getText(), count - 1);

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
	   		setMessageTxtRE("Error Reading File:", e.getMessage());
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







}
