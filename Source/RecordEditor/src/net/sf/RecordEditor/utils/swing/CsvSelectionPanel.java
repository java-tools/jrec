package net.sf.RecordEditor.utils.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;

public class CsvSelectionPanel extends BaseHelpPanel {
	
	private static final int FILE_HEIGHT = 230;
	
	private ParserManager parserManager = ParserManager.getInstance();
//	private String[] lines = null;
//	private int lines2display = 0;
	

	private BmKeyedComboModel styleModel = new BmKeyedComboModel(new ManagerRowList(
			parserManager, false));
    public JComboBox fieldSeparator = new JComboBox(Common.FIELD_SEPARATOR_LIST);
    public JTextField fieldSepTxt = new JTextField(8);
    public JComboBox quote = new JComboBox(Common.QUOTE_LIST);
    
    public BmKeyedComboBox parseType  = new BmKeyedComboBox(styleModel, false);

    public JCheckBox fieldNamesOnLine = new JCheckBox();
    
    public JButton go = new JButton("Go");
    public JButton cancel = new JButton("Cancel");
    
    private JTable linesTbl = new JTable();
    
    private CsvSelectionTblMdl tblMdl;
    
    private boolean doAction = true;
    
    private ActionListener changed = new ActionListener() {
    	   public void actionPerformed(ActionEvent e) {
    		   
    		   if (doAction) {
    			   valueChanged();
    		   }
    	   }
       };
    private ActionListener fieldNamesChanged = new ActionListener() {
   	   public void actionPerformed(ActionEvent e) {

	   		if (doAction) {
	   		   tblMdl.setSeperator(getSeperator());
	   		   tblMdl.setHideFirstLine(fieldNamesOnLine.isSelected());
	
	   		   valueChanged();
	   		}
   	   }
    };
      
    private FocusAdapter focusHandler = new FocusAdapter() {
    	public void focusLost(FocusEvent e) {
    		valueChanged();
    	}
    };
	
	public CsvSelectionPanel(byte[][] dataLines, String font, boolean showCancel) {
		
		setUpSeperator(dataLines, -1);
		tblMdl = new CsvSelectionTblMdl(parserManager);
		tblMdl.setLines(dataLines, font);
		
		init_100_SetupFields();
		init_200_LayoutScreen(showCancel);

	}
	
	/** 
	 * Setup screen fields
	 *
	 */
	private void init_100_SetupFields() {
		
		linesTbl.setModel(tblMdl);
		fieldSeparator.addFocusListener(focusHandler);
		fieldSepTxt.addFocusListener(focusHandler);
		quote.addFocusListener(focusHandler);
		parseType.addFocusListener(focusHandler);
		fieldNamesOnLine.addFocusListener(focusHandler);
		
		fieldSeparator.addActionListener(changed);
		quote.addActionListener(changed);
		parseType.addActionListener(changed);
		fieldNamesOnLine.addActionListener(fieldNamesChanged);
	}

	private void init_200_LayoutScreen(boolean showCancel) {
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnl1 = new JPanel();
		JLabel orLbl = new JLabel("   or ");
		linesTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		pnl1.add(orLbl);
		//pnl1.add(fieldSepTxt);
		orLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		//pnl.setLayout();
		pnl.add(BorderLayout.WEST, fieldSeparator);
		pnl.add(BorderLayout.CENTER, pnl1);
	   //pnl.add(BorderLayout.CENTER, orLbl);
		pnl.add(BorderLayout.EAST, fieldSepTxt);
		//pnl1.add(BorderLayout.WEST, pnl);
		
		addComponent("Field Seperator", pnl);
		setHeight(HEIGHT_1P1);
		setGap(GAP1);
		
		addComponent("Quote Character", quote);
		addComponent("Parser", parseType);
		addComponent("Fields on First Line", fieldNamesOnLine, go);
		setGap(BasePanel.GAP1);
		
		if (showCancel) {
			addComponent("", null, cancel);
		}
		
		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JScrollPane(linesTbl));
	}

	
	private void valueChanged() {
		String quote = getQuote();

		try {
			doAction = false;
			tblMdl.setParserType(((Integer) parseType.getSelectedItem()).intValue());
			tblMdl.setQuote(quote);
			tblMdl.setSeperator(getSeperator());
			
			try {
				String l = tblMdl.getLine(0).trim();
				
				if (fieldNamesOnLine.isSelected() && ! "".equals(quote) 
				&& l.startsWith(quote) && l.endsWith(quote) 
				&& parseType.getSelectedIndex() == 0) {
					parseType.setSelectedIndex(3);
					tblMdl.setParserType(((Integer) parseType.getSelectedItem()).intValue());
			   		tblMdl.setHideFirstLine(fieldNamesOnLine.isSelected());
				}
			} catch (Exception e) {
			}
			tblMdl.setupColumnCount();
			tblMdl.fireTableStructureChanged();
		} finally {
			doAction = true;
		}
	}
	/**
	 * Get The field seperator 
	 * @return field seperator 
	 */
	public final String getSeperator() {
		
		String sep = fieldSepTxt.getText();
		
		if (sep == null || "".equals(sep) || ! isSepValid()) {
			sep = fieldSeparator.getSelectedItem().toString().trim();
		}
		
		if ("<Space>".equals(sep)) {
			sep = " ";
		} else if ("<Tab>".equals(sep)) {
			sep = "\t";
		} else if ("<Default>".equals(sep)) {
			sep = ",";
		}
		
		return sep;
	}
	
	/**
	 * Check if field seperator is valid
	 * @return wether the field seperator is valid
	 */
	private boolean isSepValid() {
		boolean ret = false;
		String v = fieldSepTxt.getText();
		
		if (v.length() < 2) { 
			ret = true;
		} else if (isBinarySep()) {
			try {
				Conversion.getByteFromHexString(v);
				ret = true;
			} catch (Exception e) {
				Common.logMsg("Invalid Delimiter - Invalid  hex string: " + v.substring(2, 3), null);
			}
		} else {
			Common.logMsg("Invalid Delimiter, should be a single character or a hex character", null);
			//fieldSepTxt.requestFocus();
		}
		
		return ret;
	}
	
	private boolean isBinarySep() {
		String v = fieldSepTxt.getText();
		return ((v.length() == 5) && v.toLowerCase().startsWith("x'") && v.endsWith("'"));
	}

	
	/**
	 * Get The Quote
	 * @return Quote
	 */
	public final String getQuote() {
		String quoteStr = quote.getSelectedItem().toString().trim();
		
		if ("<None>".equals(quoteStr)) {
			quoteStr = "";
		} else if ("<Default>".equals(quoteStr)) {
			quoteStr = "'";
		}
		
		return quoteStr;
	}



	/**
	 * @param newLines the lines to set
	 * @param numberOfLines the actual number of lines used
	 */
	public void setLines(byte[][] newLines, String font, int numberOfLines) {
		
		tblMdl.setLines(newLines, font);
		tblMdl.setLines2display(numberOfLines);
		setUpSeperator(newLines, numberOfLines);
		valueChanged();
	}
	
	private void setUpSeperator(byte[][] lines, int numberOfLines) {
		
		if (lines != null) {
			int i,j, k, max, idxMax;
			String s;
			int[] count = new int[Common.FIELD_SEPARATOR_LIST.length];
			String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST.clone();
			byte[] sepByte = new byte[sep.length];
			if (numberOfLines < 0) {
				numberOfLines = lines.length;
			}
			sep[0] = ",";
			sep[1] = "\t";
			
			for (i = 0; i < count.length; i++) {
				count[i] = 0;
			}
			for (k = 0; k < sep.length; k++) {
				sepByte[k] = 0;
				if (sep[k].startsWith("x'")) {
					sepByte[k] = Conversion.getByteFromHexString(sep[k]);
				}
			}
			
			for (i = Math.max(0, numberOfLines - 5); i < numberOfLines; i++) {
				s = new String(lines[i]);
				if (s != null) {
					for (j = 0; j < s.length(); j++) {
						for (k = 0; k < sep.length; k++) {
							if (sep[k].startsWith("x'")) {
								if (lines[i][j] == sepByte[k]) {
									count[k] += 1;
									break;
								}
							} else if (sep[k].equals(s.substring(j, j+1))) {
								count[k] += 1;
								break;
							}
						}
					}
				}
			}
			
			idxMax = 0;
			max = -1;
			for (i = 0; i < count.length; i++) {
				if (max < count[i]) {
					max = count[i];
					idxMax = i;
				}
			}
			
			fieldSeparator.setSelectedIndex(idxMax);
		}
	}

	/**
	 * @return column count
	 * @see net.sf.RecordEditor.utils.swing.CsvSelectionTblMdl#getColumnCount()
	 */
	public int getColumnCount() {
		return tblMdl.getColumnCount();
	}

	/**
	 * @return the TableModel
	 */
	public CsvSelectionTblMdl getTableModel() {
		return tblMdl;
	}
}
