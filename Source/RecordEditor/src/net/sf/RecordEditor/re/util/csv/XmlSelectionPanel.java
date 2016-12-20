package net.sf.RecordEditor.re.util.csv;


import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;
import net.sf.RecordEditor.re.tree.LineTreeTabelModel;
import net.sf.RecordEditor.re.tree.TreeParserXml;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeTable.JTreeTable;


@SuppressWarnings("serial")
public class XmlSelectionPanel extends BaseHelpPanel implements FilePreview {

	private static final String XML_ID = "XML";

	private static final int MINIMUM_TREE_COLUMN_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 22;

	private static final String LAYOUT_XML_STR =
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"XML - Build Layout\" COPYBOOK=\"\" DELIMITER=\"|\" "
			+           "DESCRIPTION=\"XML file, build the layout based on the files contents\" FILESTRUCTURE=\"XML_Build_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"Y\" "
			+           "QUOTE=\"'\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
			+ "<FIELDS>"
			+ "<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "</FIELDS></RECORD>";
	private static final byte[] LAYOUT_XML_BYTES = LAYOUT_XML_STR.getBytes();

    private JTextComponent message;

    private JTreeTable treeTable;
	private JScrollPane treeTablePane = new JScrollPane();
	private LineTreeTabelModel model;

	public JButton go = SwingUtils.newButton("Edit");


	private String headingStr;

	public XmlSelectionPanel(String heading, JTextComponent msg) {
		message = msg;
		headingStr = heading;

		layoutScreen();
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
		return "";
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setData(byte[], boolean)
	 */
	@Override
	public boolean setData(String filename, byte[] data, boolean checkCharset, String layoutId) {
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		LayoutDetail layout = getLayout("", null);
		LineIOProvider ioProvider = LineIOProvider.getInstance();
		@SuppressWarnings("unchecked")
		AbstractLineReader<LayoutDetail> reader = ioProvider.getLineReader(layout);
		AbstractLine l;
		AbstractLineNodeTreeParser parser = TreeParserXml.getInstance();
		FileView view = new FileView(layout, ioProvider, false);

		try {
			reader.open(is, layout);
			while ((l = reader.read()) != null) {
				view.add(l);
			}
		} catch (Exception e) {

		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		view.setLayout(reader.getLayout());

		//treeTablePane.setVisible(false);
		model = new LineTreeTabelModel(view, parser.parse(view), 1, view.getLayout().isMapPresent());
		treeTable = new JTreeTable(model);

		treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		treeTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		treeTable.getTree().setRootVisible(false);
		treeTable.getTree().setShowsRootHandles(true);

		//treeTablePane = new JScrollPane(treeTable);

		removeColumn(0);
		removeColumn(1);
		removeColumn(1);


		for (int i = 0; i < treeTable.getTree().getRowCount() && treeTable.getTree().getRowCount() < 50; i++) {
			treeTable.getTree().expandRow(i);
		}

//		System.out.println(" }}}}-- 1 " + treeTable.getPreferredSize().getHeight()
//				+ " " + treeTable.getBounds().height
//				+ " -- " + this.getPreferredSize().getHeight()
//				+ " " + this.getBounds().height);
		Common.calcColumnWidths(treeTable, 0);
		treeTablePane.getViewport().removeAll();
		treeTablePane.getViewport().add(treeTable);
		this.doLayout();

		//treeTablePane.setVisible(true);

		TableColumn tc = treeTable.getColumnModel().getColumn(0);
		tc.setPreferredWidth(Math.max(tc.getPreferredWidth(), MINIMUM_TREE_COLUMN_WIDTH));
		//layoutScreen();
//		System.out.println(" }}}}-- 2 " + treeTable.getPreferredSize().getHeight()
//				+ " " + treeTable.getBounds().height
//				+ " -- " + this.getPreferredSize().getHeight()
//				+ " " + this.getBounds().height);

		return true;
	}

	private void removeColumn(int idx) {
		TableColumn tc = treeTable.getColumnModel().getColumn(idx);
		if (tc != null) {
			treeTable.getColumnModel().removeColumn(tc);
		}
	}

	/**
	 * Setup screen fields
	 *
	 */

	private void layoutScreen() {
		if (headingStr != null && ! "".equals(headingStr)) {
			JLabel headingLabel = new JLabel("  " + headingStr + "  ");
			Font font = headingLabel.getFont();
			headingLabel.setBackground(Color.WHITE);
			headingLabel.setOpaque(true);
			headingLabel.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize() +2));

			this.addHeadingComponentRE(headingLabel);
			this.setGapRE(GAP0);
		}




		addLineRE("", null, go);


		this.addComponentRE(
				1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        treeTablePane);

		if (message == null) {
			message = new JTextField();

			this.setGapRE(GAP1);
			this.addMessage(message);
			this.setHeightRE(HEIGHT_1P4);
		} else {
			super.setMessageRE(message);
		}
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getSeperator()
	 */
	@Override
	public final String getSeperator() {


		return "";
	}

//	/**
//	 * Check if field seperator is valid
//	 * @return wether the field seperator is valid
//	 */
//	private boolean isSepValid() {
//
//		return true;
//	}
//
//	private boolean isBinarySep() {
//
//		return false;
//	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getQuote()
	 */
	@Override
	public final String getQuote() {

		return "";
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setLines(byte[][], java.lang.String, int)
	 */
	@Override
	public boolean setLines(byte[][] newLines, String font, int numberOfLines) {


		return true;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setLines(java.lang.String[], java.lang.String, int)
	 */
	@Override
	public void setLines(String[] newLines, String font, int numberOfLines) {

	}

//	private void setUpSeperator(CsvAnalyser analyse) {
//
//	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getLayout(java.lang.String, byte[])
	 */
	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		LayoutDetail layout = null;
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
		ByteArrayInputStream is = new ByteArrayInputStream(LAYOUT_XML_BYTES);

		try {
			layout = loader.loadCopyBook(is, "Xml Layout").asLayoutDetail();
			is.close();
		} catch (Exception e) {
			String s = LangConversion.convert("Creation of XML Description Failed");
			message.setText(s);
			Common.logMsgRaw(s, null);
		}
		return layout;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#getFileDescription()
	 */
	@Override
	public String getFileDescription() {
		String csv = XML_ID;
		return csv	+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR
					+ SEP + NULL_STR;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.FilePreview#setFileDescription(java.lang.String)
	 */
	@Override
	public void setFileDescription(String val) {
//		StringTokenizer tok = new StringTokenizer(val, SEP, false);
//
//		try {
//			System.out.print(tok.nextToken());
//			getIntTok(tok);
//			fieldSepTxt.setText(getStringTok(tok));
//			quote.setSelectedItem(getStringTok(tok));
//			parseType.setSelectedIndex(getIntTok(tok));
//			fieldNamesOnLine.setSelected(getBoolTok(tok));
//			checkTypes.setSelected(getBoolTok(tok));
//			fontTxt.setText(getStringTok(tok));
//			nameLineNoTxt.setText(getStringTok(tok));
//		} catch (Exception e) {
//
//		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.FilePreview#isMyLayout(java.lang.String)
	 */
	@Override
	public boolean isMyLayout(String layoutId, String filename, byte[] data) {
		boolean ret =  layoutId != null && layoutId.startsWith(XML_ID);
		if (ret) {
			setData(filename, data, false, layoutId);
		}
		
		return ret;
	}

//	private String getStr(String s) {
//		if (s == null || "".equals(s)) {
//			s = NULL_STR;
//		}
//
//		return s;
//	}
//
//	private int getIntTok(StringTokenizer tok) {
//		int ret = 0;
//		try {
//			ret = Integer.parseInt(tok.nextToken());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return ret;
//	}
//
//
//	private boolean getBoolTok(StringTokenizer tok) {
//
//		return "Y".equalsIgnoreCase(tok.nextToken());
//	}
//
//
//
//	private String getStringTok(StringTokenizer tok) {
//
//		String s = tok.nextToken();
//		if (s == null) {
//			s = "";
//		}
//		return s;
//	}
}
