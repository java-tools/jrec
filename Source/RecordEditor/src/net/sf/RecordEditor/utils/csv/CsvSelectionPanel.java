package net.sf.RecordEditor.utils.csv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.StringTokenizer;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class CsvSelectionPanel extends BaseHelpPanel {
	
	public static final String NORMAL_CSV_STRING  = "CSV";
	public static final String UNICODE_CSV_STRING = "UNICODECSV";
	
	private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 27 / 2;
	
	private static final String SEP = "~";
	private static final String NULL_STR = "Empty";
	
	private boolean isByteBased = true;
	
	private ParserManager parserManager = ParserManager.getInstance();
//	private String[] lines = null;
//	private int lines2display = 0;
	

	private BmKeyedComboModel styleModel = new BmKeyedComboModel(new ManagerRowList(
			parserManager, false));
    public JComboBox fieldSeparator;
    public JTextField fieldSepTxt = new JTextField(8);
    public JComboBox quote = new JComboBox(Common.QUOTE_LIST);
    public JTextField fontTxt = new JTextField();
    
    public BmKeyedComboBox parseType  = new BmKeyedComboBox(styleModel, false);

    public JCheckBox fieldNamesOnLine = new JCheckBox();
    public JCheckBox checkTypes = new JCheckBox();
    
    public JButton go = new JButton("Go");
    public JButton cancel = new JButton("Cancel");
    
    private JTable linesTbl = new JTable();
    
    private AbstractCsvTblMdl tblMdl;
    private JTextComponent message;
    
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
    
	
	public CsvSelectionPanel(byte[][] dataLines, String font, 
			boolean showCancel, JTextComponent msg) {
		this(dataLines, font, showCancel, "", msg);
    };
    
	
	public CsvSelectionPanel(byte[][] dataLines, String font, 
			boolean showCancel, String heading, JTextComponent msg) {
		fieldSeparator = new JComboBox(Common.FIELD_SEPARATOR_LIST1);
		message = msg;
		setData(dataLines, font);

		init_100_SetupFields();
		init_200_LayoutScreen(showCancel, heading);
	};

	
	public CsvSelectionPanel(byte[] data, String font, 
			boolean showCancel, String heading, JTextComponent msg) {
		message = msg;
		isByteBased = false;
		fieldSeparator = new JComboBox(Common.FIELD_SEPARATOR_TEXT_LIST);
		
		setData(data, true);

		init_100_SetupFields();
		init_200_LayoutScreen(showCancel, heading);
		valueChanged();
	};

	public void setData(byte[][] dataLines, String font) {
		setUpSeperator(new CsvAnalyser(dataLines, -1, ""));
		
		tblMdl = new CsvSelectionTblMdl(parserManager);
		tblMdl.setLines(dataLines, font);
		
		linesTbl.setModel(tblMdl);
	}
	
	public boolean setData(byte[] data, boolean checkCharset) {
		String font = fontTxt.getText();
		CsvAnalyser anaylyser;
		CsvSelectionStringTblMdl tableMdl = new CsvSelectionStringTblMdl(parserManager);

		if (checkCharset) {
			font = CheckEncoding.determineCharSet(data);
		}

		fontTxt.setText(font);
		tableMdl.setData(data);
		tableMdl.setFont(font);
		
		anaylyser = new CsvAnalyser(tableMdl.getLinesString(), -1, font);
		setUpSeperator(anaylyser);
		
		tblMdl = tableMdl;
		
		linesTbl.setModel(tblMdl);
		valueChanged();
		
		return anaylyser.isValidChars();
	}
	
	/** 
	 * Setup screen fields
	 *
	 */
	private void init_100_SetupFields() {
		
		fieldSeparator.addFocusListener(focusHandler);
		fieldSepTxt.addFocusListener(focusHandler);
		quote.addFocusListener(focusHandler);
		parseType.addFocusListener(focusHandler);
		fontTxt.addFocusListener(focusHandler);
		fieldNamesOnLine.addFocusListener(focusHandler);
		
		fieldSeparator.addActionListener(changed);
		quote.addActionListener(changed);
		parseType.addActionListener(changed);
		fontTxt.addActionListener(changed);
		fieldNamesOnLine.addActionListener(fieldNamesChanged);
	}

	private void init_200_LayoutScreen(boolean showCancel, String heading) {
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
		pnl.setMinimumSize(new Dimension(pnl.getPreferredSize().width, SwingUtils.TABLE_ROW_HEIGHT));
		//pnl1.add(BorderLayout.WEST, pnl);
		
		if (heading != null && ! "".equals(heading)) {
			JLabel headingLabel = new JLabel("  " + heading + "  ");
			Font font = headingLabel.getFont();
			headingLabel.setBackground(Color.WHITE);
			headingLabel.setOpaque(true);
			headingLabel.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize() +2));
			
			this.addHeadingComponent(headingLabel);
			this.setGap(GAP0);
		}
		addLine("Field Seperator", pnl);
		setGap(GAP1);
		
		addLine("Quote Character", quote);
		
		if (! isByteBased) {
			addLine("Font", fontTxt);
		}
		addLine("Parser", parseType);
		
		if (showCancel) {
			addLine("Fields on First Line", fieldNamesOnLine, go);
			setGap(BasePanel.GAP);
			addLine("Evaluate Column Types", checkTypes, cancel);
			setGap(BasePanel.GAP);
		} else {
			addLine("Fields on First Line", fieldNamesOnLine);
			addLine("Evaluate Column Types", checkTypes, go);
			setGap(BasePanel.GAP1);
		}
		
		this.addComponent(
				1, 5, FILE_HEIGHT, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JScrollPane(linesTbl));
		
		if (message == null) {
			message = new JTextField();
			
			this.setGap(GAP1);
			this.addMessage(message);
			this.setHeight(HEIGHT_1P4);
		}
	}

	
	private void valueChanged() {

		try {
			doAction = false;
			String quote = getQuote();
			
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
				}
		 		tblMdl.setHideFirstLine(fieldNamesOnLine.isSelected());
			} catch (Exception e) {
			}
			tblMdl.setFont(fontTxt.getText());
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
		} else if (isByteBased && isBinarySep()) {
			try {
				Conversion.getByteFromHexString(v);
				ret = true;
			} catch (Exception e) {
				message.setText("Invalid Delimiter - Invalid  hex string: " + v.substring(2, 3));
			}
		} else if (isByteBased) {
			message.setText("Invalid Delimiter, should be a single character or a hex character");
		} else {
			message.setText("Invalid Delimiter, should be a single character");
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
	public boolean setLines(byte[][] newLines, String font, int numberOfLines) {
		CsvAnalyser analyser = new CsvAnalyser(newLines, numberOfLines, "");
		tblMdl.setLines(newLines, font);
		tblMdl.setLines2display(numberOfLines);
		setUpSeperator(analyser);
		valueChanged();
		
		return analyser.isValidChars();
	}

	/**
	 * @param newLines the lines to set
	 * @param numberOfLines the actual number of lines used
	 */
	public void setLines(String[] newLines, String font, int numberOfLines) {
		
		tblMdl.setLines(newLines);
		tblMdl.setLines2display(numberOfLines);
		setUpSeperator(new CsvAnalyser(newLines, numberOfLines, ""));
		valueChanged();
	}

	private void setUpSeperator(CsvAnalyser analyse) {
		
		fieldSeparator.setSelectedIndex(analyse.getSeperatorIdx());
		quote.setSelectedIndex(analyse.getQuoteIdx());
		
		switch (analyse.getColNamesOnFirstLine()) {
		case CsvAnalyser.COLUMN_NAMES_NO: 
			fieldNamesOnLine.setSelected(false);
			break;
		case CsvAnalyser.COLUMN_NAMES_YES: 
			fieldNamesOnLine.setSelected(true);
			break;
		}
	}

	/**
	 * @return column count
	 * @see net.sf.RecordEditor.utils.csv.CsvSelectionTblMdl#getColumnCount()
	 */
	public int getColumnCount() {
		return tblMdl.getColumnCount();
	}

	/**
	 * @return the TableModel
	 */
	public AbstractCsvTblMdl getTableModel() {
		return tblMdl;
	}
	
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		LayoutDetail layout;
		
	    int numCols = getColumnCount();
	    int ioId = Constants.IO_BIN_TEXT;
        int format    = 0;
        int i         = 0;
        String param  = "";
	    String s;
	   
	    int fieldType ;
	    int[] fieldTypes = null;
	    
        FieldDetail[] flds = new FieldDetail[numCols];
        RecordDetail[] recs = new RecordDetail[1];
   
        if (isByteBased) {
        	if (fieldNamesOnLine.isSelected()) {
        		ioId = Constants.IO_BIN_NAME_1ST_LINE;
        	}
        } else {
        	ioId = Constants.IO_UNICODE_TEXT;
        	if (fieldNamesOnLine.isSelected()) {
        		ioId = Constants.IO_UNICODE_NAME_1ST_LINE;
        	}
        }
	    
        if (checkTypes.isSelected() 
        || parseType.getSelectedIndex() == 2 
        || parseType.getSelectedIndex() == 5) {
         	fieldTypes = tblMdl.getAnalyser()
        					   .getTypes();
        }

	    for (i = 0; i < numCols; i++) {
	    	fieldType = Type.ftChar;
	    	if (fieldTypes != null) {
	    		fieldType = fieldTypes[i];
	    	}
		    s = getTableModel().getColumnName(i);
            flds[i] = new FieldDetail(s, s, fieldType, 0,
                        font, format, param);
            flds[i].setPosOnly(i + 1);
	    }

        recs[0] = new RecordDetail("GeneratedCsvRecord", "", "", Constants.rtDelimited,
        		getSeperator(),  getQuote(), font, flds, 
        		((Integer)parseType.getSelectedItem()).intValue());
        
        layout  =
            new LayoutDetail("GeneratedCsv", recs, "",
                Constants.rtDelimited,
                recordSep, "", font, null,
                ioId
            );

		return layout;
	}
	
	public String getFileDescription() {
		String csv = UNICODE_CSV_STRING;
		if (isByteBased) {
			csv = NORMAL_CSV_STRING;
		}
		return csv	+ SEP + fieldSeparator.getSelectedIndex()
					+ SEP + getStr(fieldSepTxt.getText())
					+ SEP + quote.getSelectedItem()
					+ SEP + parseType.getSelectedIndex()
					+ SEP + getBool(fieldNamesOnLine)
					+ SEP + getBool(checkTypes)
					+ SEP + getStr(fontTxt.getText());
	}
	
	public void setFileDescription(String val) {
		StringTokenizer tok = new StringTokenizer(val, SEP, false);
		
		try {
			System.out.print(tok.nextToken());
			fieldSeparator.setSelectedIndex(getIntTok(tok));
			fieldSepTxt.setText(getStringTok(tok));
			quote.setSelectedItem(getStringTok(tok));
			parseType.setSelectedIndex(getIntTok(tok));
			fieldNamesOnLine.setSelected(getBoolTok(tok));
			checkTypes.setSelected(getBoolTok(tok));
			fontTxt.setText(getStringTok(tok));
		} catch (Exception e) {
			
		}
	}
	
	private String getBool(JCheckBox chk) {
		String s = "N";
		if (chk.isSelected()) {
			s = "Y";
		}
		return s;
	}
	
	private String getStr(String s) {
		if (s == null || "".equals(s)) {
			s = NULL_STR;
		}
		
		return s;
	}
	
	private int getIntTok(StringTokenizer tok) {
		int ret = 0;
		try {
			ret = Integer.parseInt(tok.nextToken());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ret;
	}
	
	
	private boolean getBoolTok(StringTokenizer tok) {

		return "Y".equalsIgnoreCase(tok.nextToken());
	}
	
	
	
	private String getStringTok(StringTokenizer tok) {

		String s = tok.nextToken();
		if (s == null || NULL_STR.equals(s)) {
			s = "";
		}
		return s;
	}
}
