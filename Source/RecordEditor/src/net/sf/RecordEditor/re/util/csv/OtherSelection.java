package net.sf.RecordEditor.re.util.csv;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.IGetSchema;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.tip.def.TipField;
import net.sf.RecordEditor.tip.def.TipLayoutMgr;
import net.sf.RecordEditor.utils.StringMatch;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class OtherSelection implements FilePreview {

	private static final String OTHER_ID = "OTHER";
	private static final String PO_ID = "PO";
	private static final String TIP_ID = "TIPS";
	private static final String[] SUB_TYPES = {PO_ID, TIP_ID, ""};
	private static final int TYPE_PO = 0;
	private static final int TYPE_TIP = 1;
	private static final int TYPE_OTHER = 2;
	
	private StringMatch[][] fields = {PoField.getAllfields(), TipField.getAllfields()};

	
	private int type = TYPE_OTHER;
	private BaseHelpPanel pnl = new BaseHelpPanel();

	private JLabel typeLabel = new JLabel();
	private JButton editBtn = SwingUtils.newButton("Edit");

	private JTable fileTable = new JTable();

//	private  JTextComponent msg;

	private String fontName = "";
//	private String filename = "";
//	private int fileStructure;
//	private int recordLength = 100;

	private byte[] lastData;



	public OtherSelection(JTextComponent message) {
//		msg = message;

		pnl.setAddFillToEnd(true);

		fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		
		
		pnl.addLineRE("", typeLabel, editBtn)
		      .setGapRE(BasePanel.GAP1);
		  
		
		pnl.addComponentRE(
				1,  6, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        fileTable);

	}


	@Override
	public BaseHelpPanel getPanel() {
		return pnl;
	}

	@Override
	public JButton getGoButton() {
		return editBtn;
	}

	@Override
	public boolean setData(String filename, byte[] data, boolean checkCharset, String layoutId) {

		if (lastData == data) {
			return type != TYPE_OTHER;
		}
		lastData = data;

		analyseData(null, data);

		return buildFileLayout(filename, data);
	}

	private boolean buildFileLayout(String filename, byte[] data) {
		try {
			LayoutDetail layout = getLocalLayout(null, data);

			if (layout == null) {
				System.out.println("No Layout Generated !!!");
				fileTable.setVisible(false);
				editBtn.setEnabled(false);
				return false;
			}
			int fileStructure = layout.getFileStructure();
			fontName = layout.getFontName();
			//recordLength = layout.getMaximumRecordLength();

			ByteArrayInputStream is = new ByteArrayInputStream(data);
			LineIOProvider iop = LineIOProvider.getInstance();
			@SuppressWarnings("unchecked")
			AbstractLineReader<LayoutDetail> r = iop.getLineReader(fileStructure);
			FileView view = new FileView(layout, iop, false);
			AbstractLine l;
			int i=0;

			r.open(is, layout);
			try {
				while ( i++ < 60 && (l = r.read()) != null) {
					view.add(l);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				is.close();
				r.close();
			}

			fileTable.setModel(view);
			int height = SwingUtils.TABLE_ROW_HEIGHT;
			if (type == TYPE_TIP) {
				height = 3 * SwingUtils.TABLE_ROW_HEIGHT;
			} 
			fileTable.setRowHeight(height);
			view.fireTableStructureChanged();

			TableColumnModel tcm = fileTable.getColumnModel();
			TableColumn tc = tcm.getColumn(0);
			HeaderRender headerRender = new HeaderRender();

	        for (i = 2; i < tcm.getColumnCount(); i++) {
	            tcm.getColumn(i).setHeaderRenderer(headerRender);
	        }
			if (tc != null) {
				fileTable.getColumnModel().removeColumn(tc);
			}

			Rectangle visbleRect = fileTable.getVisibleRect();
			if (visbleRect != null && visbleRect.width > 0) {
				Common.calcColumnWidths(fileTable, 0);
			} else if (type == TYPE_TIP) {
				fileTable.getColumnModel().getColumn(2).setPreferredWidth(SwingUtils.CHAR_FIELD_WIDTH * 55);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		lastData = data;
		return true;
	}


	@Override
	public String getSeperator() {
		return "";
	}

	@Override
	public String getQuote() {
		return "";
	}

	@Override
	public boolean setLines(byte[][] newLines, String font, int numberOfLines) {

		return false;
	}

	@Override
	public void setLines(String[] newLines, String font, int numberOfLines) {

	}

//	@Override
//	public int getColumnCount() {
//		return fileTable.getColumnCount();
//	}
//
//	@Override
//	public String getColumnName(int idx) {
//		return null;
//	}
//
	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		return getLocalLayout(font, lastData);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.FilePreview#getSchemaCheckType()
	 */
	@Override
	public int getSchemaCheckType() {
		return IGetSchema.ST_OTHER_SCHEMA;
	}


	@Override
	public String getFileDescription() {

		return OTHER_ID	+ SEP + SUB_TYPES[type]
						+ SEP + NULL_STR;
	}

	@Override
	public void setFileDescription(String val) {
//		StringTokenizer tok = new StringTokenizer(val, SEP, false);
//
//		try {
//			tok.nextToken();
//			type = TYPE_PO;
//			if (TIP_ID.equals((getStringTok(tok)))) {
//				type = TYPE_TIP;
//			}
//		} catch (Exception e) {
//
//		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.FilePreview#isMyLayout(java.lang.String)
	 */
	@Override
	public int isMyLayout(String layoutId, String filename, byte[] data) {

		setData(filename, data, false, layoutId);
		
		return type != TYPE_OTHER? LIKELY : NO;
	}



//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.BasicLayoutCallback#setRecordLayout(int, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void setRecordLayout(int layoutId, String layoutName, String filename) {
////		layoutFile.setText(layoutName);
//	}


	private LayoutDetail getLocalLayout(String charset, byte[] data) {
		analyseData(charset, data);
		
		return getLayout();
	}
	
	private LayoutDetail getLayout() {
		LayoutDetail ret = null;

		if (type == TYPE_PO) {
			ret = PoLayoutMgr.getPoLayout();
		} else if (type == TYPE_TIP) {
			ret = TipLayoutMgr.getTipLayout();
		}
		return ret;
	}
	
	private void analyseData(String charset, byte[] data) {
		type = TYPE_OTHER;
		
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			InputStreamReader ir;
			if (charset == null || "".equals(charset)) {
				ir = new InputStreamReader(is);
			} else {
				ir = new InputStreamReader(is, charset);
			}
			BufferedReader r = new BufferedReader(ir);
			String s;
			int c = 0;
			int[] counts = {0, 0};
			
			try {
				while ((s = r.readLine()) != null) {
					if (!"".equals(s)) {
						c += 1;
						s = s.trim();
						for (int i = 0; i < fields.length; i++) {
							for (StringMatch m : fields[i]) {
								if (m.isMatch(s)) {
									counts[i] += 1;
								}
							}
						}
					}
				}
					
				int idx = 0;
				if (counts[1] > counts[0]) {
					idx = 1;
				}
				
				if (counts[idx] * 10 > c) {
					type = idx;
				}
			
			} finally {
				r.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		typeLabel.setText(SUB_TYPES[type]);
//		System.out.println("--> " + SUB_TYPES[type] + " " + typeLabel.getText());
		typeLabel.revalidate();
		fileTable.setVisible(type != TYPE_OTHER);
		editBtn.setEnabled(type != TYPE_OTHER);
	}

//	private String getStr(String s) {
//		if (s == null || "".equals(s)) {
//			s = NULL_STR;
//		}
//
//		return s;
//	}
//
//
//	private String getStringTok(StringTokenizer tok) {
//
//		String s = tok.nextToken();
//		if (s == null || NULL_STR.equals(s)) {
//			s = "";
//		}
//		return s;
//	}
//
//	private int getIntTok(StringTokenizer tok) {
//		int ret = 0;
//		try {
//			ret = Integer.parseInt(tok.nextToken());
//		} catch (Exception e) {
//		}
//
//		return ret;
//	}

	@Override
	public String getFontName() {
		return fontName;
	}


    public void setLayoutFile(String lFile) {
	}


//	public void setFilename(String filename) {
//		this.filename = filename;
//	}



	@SuppressWarnings("serial")
	private static class HeaderRender extends JPanel implements TableCellRenderer {

        /**
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
         * 		(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(
            JTable tbl,
            Object value,
            boolean isFldSelected,
            boolean hasFocus,
            int row,
            int column) {

            removeAll();
            setLayout(new GridLayout(2, 1));

            if (column >= 0 && value != null) {

                String s = (String) value;
                String first = s;
                String second = "";
                int pos = s.indexOf(Common.COLUMN_LINE_SEP);
                if (pos > 0) {
                    first = s.substring(pos + 1);
                    second = s.substring(0, pos);
                }
                JLabel label = new JLabel(first);
                add(label);
                if ((!second.equals(""))) {
                    label = new JLabel(second);

                    add(label);
                }
            }
            this.setBorder(BorderFactory.createEtchedBorder());

            return this;
        }
    }

}
