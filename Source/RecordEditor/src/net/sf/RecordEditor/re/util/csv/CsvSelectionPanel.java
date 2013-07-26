package net.sf.RecordEditor.re.util.csv;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimiterCombo;
import net.sf.RecordEditor.utils.swing.ComboBoxs.QuoteCombo;

@SuppressWarnings("serial")
public class CsvSelectionPanel extends BaseHelpPanel implements FilePreview {

	public static final String NORMAL_CSV_STRING  = "CSV";
	public static final String UNICODE_CSV_STRING = "UNICODECSV";

	//private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 27 / 2;


	private boolean isByteBased = true;

	private ParserManager parserManager = ParserManager.getInstance();
	private MenuPopupListener popup;
	private String lastFont;
//	private String[] lines = null;
//	private int lines2display = 0;


	private BmKeyedComboModel styleModel = new BmKeyedComboModel(
												new ManagerRowList(parserManager, false));
    public DelimiterCombo fieldSeparator;
    public JTextField fieldSepTxt = new JTextField(5);
    public QuoteCombo quoteCombo = QuoteCombo.newCombo();
    public JTextField fontTxt = new JTextField();

    public BmKeyedComboBox parseType  = new BmKeyedComboBox(styleModel, false);

    public JCheckBox fieldNamesOnLine = new JCheckBox();
    public JTextField nameLineNoTxt = new JTextField();
    public JCheckBox checkTypes = new JCheckBox();

    public JButton go = SwingUtils.newButton("Go");
    public JButton cancel = SwingUtils.newButton("Cancel");

    private JTable linesTbl = new JTable();

    private AbstractCsvTblMdl tblMdl;
    private JTextComponent message;

    private boolean doAction = true;
    private byte[][] dataLines = null;
    private byte[] data;

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

		   if (e.getSource() == fontTxt && (lastFont == null || ! lastFont.equals(fontTxt.getText()))) {
			   if (dataLines == null) {
				   setData("", data, false, "");
			   } else {
				   setData(dataLines, fontTxt.getText());
			   }
		   }
		   valueChanged();
    	}
    };


	public CsvSelectionPanel(byte[][] dataLines, String font,
			boolean showCancel, JTextComponent msg) {
		this(dataLines, font, showCancel, "", msg, false);
    };


	public CsvSelectionPanel(byte[][] dataLines, String font,
			boolean showCancel, String heading, JTextComponent msg,
			boolean adjustableTblHeight) {
		fieldSeparator = DelimiterCombo.NewDelimCombo();
		message = msg;
		setData(dataLines, font);

		init_100_SetupFields();
		init_200_LayoutScreen(showCancel, heading, adjustableTblHeight);
	};


	public CsvSelectionPanel(byte[] data, String font,
			boolean showCancel, String heading, JTextComponent msg,
			boolean adjustableTblHeight) {
		message = msg;
		isByteBased = false;
		fieldSeparator = DelimiterCombo.NewDelimCombo();

		setData("", data, true, null);

		init_100_SetupFields();
		init_200_LayoutScreen(showCancel, heading, adjustableTblHeight);
		valueChanged();
	};

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getContainer()
	 */
	@Override
	public BaseHelpPanel getPanel() {
		return this;
	}


	@Override
	public JButton getGoButton() {
		return go;
	}


	@Override
	public String getFontName() {
		return fontTxt.getText();
	}


	private void setData(byte[][] dataLines, String font) {
		CsvAnalyser anaylyser = new CsvAnalyser(dataLines, -1, "");
		setUpSeperator(anaylyser);

		this.dataLines = dataLines;

		tblMdl = new CsvSelectionTblMdl(parserManager);
		tblMdl.setLines(dataLines, font);
		tblMdl.setFieldLineNo(getFieldLineNo());

		linesTbl.setModel(tblMdl);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setData(byte[], boolean)
	 */
	@Override
	public boolean setData(String filename, byte[] data, boolean checkCharset, String layoutId) {
		String font = fontTxt.getText();
		CsvAnalyser anaylyser;
		CsvSelectionStringTblMdl tableMdl = new CsvSelectionStringTblMdl(parserManager);

		this.lastFont = font;
		this.data = data;
		if (checkCharset) {
			font = CheckEncoding.determineCharSet(data);
		}

		fontTxt.setText(font);
		tableMdl.setDataFont(data, font);

		anaylyser = new CsvAnalyser(tableMdl.getLinesString(), -1, font);

		setUpSeperator(anaylyser);
		tblMdl = tableMdl;
		tblMdl.setFieldLineNo(getFieldLineNo());

		linesTbl.setModel(tblMdl);
		valueChanged();

		return anaylyser.isValidChars();
	}

	/**
	 * Setup screen fields
	 *
	 */
	private void init_100_SetupFields() {

		nameLineNoTxt.setText("1");
		fieldSeparator.addFocusListener(focusHandler);
		fieldSepTxt.addFocusListener(focusHandler);
		quoteCombo.addFocusListener(focusHandler);
		parseType.addFocusListener(focusHandler);
		fontTxt.addFocusListener(focusHandler);
		fieldNamesOnLine.addFocusListener(focusHandler);
		nameLineNoTxt.addFocusListener(focusHandler);

		fieldSeparator.addActionListener(changed);
		quoteCombo.addActionListener(changed);
		parseType.addActionListener(changed);
		fontTxt.addActionListener(changed);
		fieldNamesOnLine.addActionListener(fieldNamesChanged);
	}

	private void init_200_LayoutScreen(boolean showCancel, String heading, boolean adjustableTblHeight) {
		double fileTblHeight = SwingUtils.TABLE_ROW_HEIGHT * 27 / 2;
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

		addLine("Quote Character", quoteCombo);

		if (! isByteBased) {
			addLine("Font", fontTxt);
		}
		if (adjustableTblHeight) {
			fileTblHeight = BasePanel.FILL;
		} else if (! isByteBased) {
			fileTblHeight -= SwingUtils.TABLE_ROW_HEIGHT * 2;
		}
		addLine("Parser", parseType);
		addLine("Names on Line", fieldNamesOnLine);

		if (showCancel) {
			addLine("Line Number of Names", nameLineNoTxt, go);
			setGap(BasePanel.GAP);
			addLine("set Column Types", checkTypes, cancel);
			setGap(BasePanel.GAP);
		} else {
			addLine("Line Number of Names", nameLineNoTxt);
			addLine("set Column Types", checkTypes, go);
			setGap(BasePanel.GAP1);
		}

		this.addComponent(
				1, 5, fileTblHeight, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JScrollPane(linesTbl));

		if (message == null) {
			message = new JTextField();

			this.setGap(GAP1);
			this.addMessage(message);
			this.setHeight(HEIGHT_1P4);
		} else {
			super.setMessage(message);
		}

		popup = new MenuPopupListener();
		popup.setTable(linesTbl);
		popup.getPopup().add(new ReAbstractAction("set as Field Name Row") {
			public void actionPerformed(ActionEvent e) {
				int inc = 1;
				if (fieldNamesOnLine.isSelected() && getFieldLineNo() == 1) {
					inc = 2;
				}
//				System.out.println("Set Row " + fieldNamesOnLine.isSelected()
//						+ " " + getFieldLineNo()
//						+ " " + inc
//						+ " " + popup.getPopupRow());
				fieldNamesOnLine.setSelected(true);
				nameLineNoTxt.setText(Integer.toString(popup.getPopupRow() + inc));
				valueChanged();
			}
		});
		linesTbl.addMouseListener(popup);
	}


	private void valueChanged() {

		try {
			doAction = false;
			String quoteStr = getQuote();

			tblMdl.setParserType(((Integer) parseType.getSelectedItem()).intValue());
			tblMdl.setQuote(quoteStr);
			tblMdl.setSeperator(getSeperator());

			if (tblMdl.getRowCount() > 0) {
				try {
					String l = tblMdl.getLine(0).trim();

					tblMdl.setFieldLineNo(getFieldLineNo());
					if (fieldNamesOnLine.isSelected() && ! "".equals(quoteStr)
					&& l.startsWith(quoteStr) && l.endsWith(quoteStr)
					&& parseType.getSelectedIndex() == 0) {
						parseType.setSelectedIndex(3);
						tblMdl.setParserType(((Integer) parseType.getSelectedItem()).intValue());
					}

			 		tblMdl.setHideFirstLine(fieldNamesOnLine.isSelected());
				} catch (Exception e) {
				}
			}
			tblMdl.setFont(fontTxt.getText());
			tblMdl.setupColumnCount();
			tblMdl.fireTableStructureChanged();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doAction = true;
		}
	}
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getSeperator()
	 */
	@Override
	public final String getSeperator() {

		String sep = fieldSepTxt.getText();

		if (sep == null || "".equals(sep) || ! isSepValid()) {
			sep = fieldSeparator.getSelectedEnglish();
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
				setMessageTxt("Invalid Delimiter - Invalid  hex string:", v.substring(2, 3));
			}
		} else if (isByteBased) {
			setMessageTxt("Invalid Delimiter, should be a single character or a hex character");
		} else {
			setMessageTxt("Invalid Delimiter, should be a single character");
		}

		return ret;
	}

	private boolean isBinarySep() {
		String v = fieldSepTxt.getText();
		return ((v.length() == 5) && v.toLowerCase().startsWith("x'") && v.endsWith("'"));
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getQuote()
	 */
	@Override
	public final String getQuote() {
		return quoteCombo.getSelectedKey();
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setLines(byte[][], java.lang.String, int)
	 */
	@Override
	public boolean setLines(byte[][] newLines, String font, int numberOfLines) {
		CsvAnalyser analyser = new CsvAnalyser(newLines, numberOfLines, "");
		tblMdl.setLines(newLines, font);
		tblMdl.setLines2display(numberOfLines);

		setUpSeperator(analyser);
		tblMdl.setFieldLineNo(getFieldLineNo());
		valueChanged();

		return analyser.isValidChars();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setLines(java.lang.String[], java.lang.String, int)
	 */
	@Override
	public void setLines(String[] newLines, String font, int numberOfLines) {

		tblMdl.setLines(newLines);
		tblMdl.setLines2display(numberOfLines);
		setUpSeperator(new CsvAnalyser(newLines, numberOfLines, ""));
		valueChanged();
	}

	private void setUpSeperator(CsvAnalyser analyse) {

		fieldSeparator.setSelectedIndex(analyse.getSeperatorIdx());
		fieldSepTxt.setText("");
		quoteCombo.setSelectedIndex(analyse.getQuoteIdx());

		switch (analyse.getColNamesOnFirstLine()) {
		case CsvAnalyser.COLUMN_NAMES_NO:
			fieldNamesOnLine.setSelected(false);
			nameLineNoTxt.setText("1");
			break;
		case CsvAnalyser.COLUMN_NAMES_YES:
			fieldNamesOnLine.setSelected(true);
			nameLineNoTxt.setText(Integer.toString(analyse.getFieldNameLineNo()));
			break;
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return tblMdl.getColumnCount();
	}



	@Override
	public String getColumnName(int idx) {
		// TODO Auto-generated method stub
		return tblMdl.getColumnName(idx);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getLayout(java.lang.String, byte[])
	 */
	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		LayoutDetail layout;

	    int numCols = getColumnCount();
	    int ioId = Constants.IO_BIN_TEXT;
        int format    = 0;
        int i         = 0;
        int fldLineNo = getFieldLineNo();
        String param  = "";
	    String s;

	    int fieldType ;
	    int[] fieldTypes = null;

        RecordDetail.FieldDetails[] flds = new RecordDetail.FieldDetails[numCols];
        RecordDetail[] recs = new RecordDetail[1];

        if (isByteBased) {
        	if (fieldNamesOnLine.isSelected() && fldLineNo < 2) {
        		ioId = Constants.IO_BIN_NAME_1ST_LINE;
        	}
        } else {
        	ioId = Constants.IO_UNICODE_TEXT;
        	if (fieldNamesOnLine.isSelected() && fldLineNo < 2) {
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
		    s = getColumnName(i);
            flds[i] = new RecordDetail.FieldDetails(s, s, fieldType, 0,
                        font, format, param);
            flds[i].setPosOnly(i + 1);
	    }

        recs[0] = new RecordDetail("GeneratedCsvRecord", "", "", Constants.rtDelimited,
        		getSeperator(),  getQuote(), font, flds,
        		((Integer)parseType.getSelectedItem()).intValue(), 0);

        layout  =
            new LayoutDetail("GeneratedCsv", recs, "",
                Constants.rtDelimited,
                recordSep, "", font, null,
                ioId
            );

        layout.setUseThisLayout(true);
		return layout;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getFileDescription()
	 */
	@Override
	public String getFileDescription() {
		String csv = UNICODE_CSV_STRING;
		if (isByteBased) {
			csv = NORMAL_CSV_STRING;
		}
		return csv	+ SEP + fieldSeparator.getSelectedIndex()
					+ SEP + getStr(fieldSepTxt.getText())
					+ SEP + quoteCombo.getSelectedEnglish()
					+ SEP + parseType.getSelectedIndex()
					+ SEP + getBool(fieldNamesOnLine)
					+ SEP + getBool(checkTypes)
					+ SEP + getStr(fontTxt.getText())
					+ SEP + getStr(nameLineNoTxt.getText());
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setFileDescription(java.lang.String)
	 */
	@Override
	public void setFileDescription(String val) {
		StringTokenizer tok = new StringTokenizer(val, SEP, false);

		try {
			System.out.print(tok.nextToken());
			fieldSeparator.setSelectedIndex(getIntTok(tok));
			fieldSepTxt.setText(getStringTok(tok));
			quoteCombo.setEnglish(getStringTok(tok));
			parseType.setSelectedIndex(getIntTok(tok));
			fieldNamesOnLine.setSelected(getBoolTok(tok));
			checkTypes.setSelected(getBoolTok(tok));
			fontTxt.setText(getStringTok(tok));
			nameLineNoTxt.setText(getStringTok(tok));
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

	private int getFieldLineNo() {
		int ret = 1;
		String s = nameLineNoTxt.getText();
		if (! "".equals(s)) {
			try {
				ret = Integer.parseInt(s);

				if (ret < 1) {
					ret = 1;
					setMessageTxt("Field Line Number should be one or more and not",  s);
				}
			} catch (Exception e) {
				setMessageTxt("Invalid Field Line Number:", s);
			}
		}


		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.FilePreview#isMyLayout(java.lang.String)
	 */
	@Override
	public boolean isMyLayout(String layout) {
		return false;
	}
}
