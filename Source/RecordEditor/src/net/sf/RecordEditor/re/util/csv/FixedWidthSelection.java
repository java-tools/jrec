package net.sf.RecordEditor.re.util.csv;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.ByteArrayInputStream;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FileStructureAnalyser;
import net.sf.RecordEditor.layoutWizard.WizardFileLayout;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class FixedWidthSelection implements FilePreview {

	private BaseHelpPanel pnl = new BaseHelpPanel();
	
	private FileChooser layoutFile = new FileChooser();
	private JButton editBtn = new JButton("Edit");
	
	private JTable fileTable = new JTable();
	private String fontName = "";
	private String filename = "";
	private int fileStructure;
	private int recordLength = 100;
	
	private byte[] lastData;
	
	
	public FixedWidthSelection() {
		JLabel layoutLbl = new JLabel("Layout File");
		
		pnl.oneColumn();
		pnl.addComponent(1, 3, SwingUtils.TABLE_ROW_HEIGHT, BasePanel.GAP, BasePanel.FULL, BasePanel.FULL, layoutLbl);
		pnl.addLine(layoutFile, layoutFile.getChooseFileButton());
		pnl.setGap(BasePanel.GAP1);
		pnl.addLine((String) null, editBtn);
		pnl.addComponent(
				1, 5, BasePanel.FILL, BasePanel.GAP1,
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
	public boolean setData(byte[] data, boolean checkCharset) {
		
		if (lastData == data) {
			return true;
		}
		try {
			LayoutDetail layout = null;
			
			if (! "".equals(layoutFile.getText())) {
				try {
					RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
					
					ExternalRecord rec = loader.loadCopyBook(layoutFile.getText(), 0, 0, "", 0, 0, Common.getLogger());
					layout = rec.asLayoutDetail();
				} catch (Exception e) {
				}
			}

			if (layout == null) {
				FileStructureAnalyser analyser = FileStructureAnalyser.getAnaylser(data, "");
				layout = analyser.getLayoutDetails(true).asLayoutDetail();
			}
			if (layout == null) {
				System.out.println("No Layout Generated !!!");
				return false;
			}
			fileStructure = layout.getFileStructure();
			fontName = layout.getFontName();
			recordLength = layout.getMaximumRecordLength();
			
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			LineIOProvider iop = LineIOProvider.getInstance();
			AbstractLineReader<LayoutDetail> r = iop.getLineReader(fileStructure);
			FileView<LayoutDetail> view = new FileView<LayoutDetail>(layout, iop, false);
			AbstractLine<LayoutDetail> l;
			int i=0;
			
			r.open(is, layout);
			try {
				while ( i++ < 60 && (l = r.read()) != null) {
					view.add(l);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		
			fileTable.setModel(view);
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
			r.close();
			is.close();
			
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

	@Override
	public int getColumnCount() {
		return fileTable.getColumnCount();
	}

	@Override
	public String getColumnName(int idx) {
		return null;
	}

	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		if (! "".equals(layoutFile.getText())) {
			try {
				RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
				
				ExternalRecord rec = loader.loadCopyBook(layoutFile.getText(), 0, 0, font, 0, 0, Common.getLogger());
				return rec.asLayoutDetail();
			} catch (Exception e) {
			}
		}
		
		JDialog d = new JDialog(ReMainFrame.getMasterFrame(), true);
		WizardFileLayout wiz = new WizardFileLayout(d, filename, null, false, false);

		Details details = wiz.getWizardDetails();
		details.fileStructure = fileStructure;
		details.fontName = font;
		details.recordLength = recordLength;
		details.generateFieldNames = true;
				
		wiz.changePanel(WizardFileLayout.FORWARD, false);
		if (wiz.getPanelNumber() == 1) {
			wiz.changePanel(WizardFileLayout.FORWARD, false);
		}
		
		d.setVisible(true);
		
		ExternalRecord rec = wiz.getExternalRecord();
		if (rec != null) {
			try {
				return rec.asLayoutDetail();
			} catch (Exception e) {
				Common.logMsg("Layout Generation Failed: " + e.getMessage(), null);
			}
		}

		return null;
	}


	@Override
	public String getFileDescription() {
		String csv = "FIXED";
		return csv	+ SEP + getStr(layoutFile.getText())
					+ SEP + NULL_STR;
	}

	@Override
	public void setFileDescription(String val) {
		StringTokenizer tok = new StringTokenizer(val, SEP, false);
		
		try {
			tok.nextToken();
			layoutFile.setText(getStringTok(tok));
		} catch (Exception e) {
			
		}
	}


	private String getStr(String s) {
		if (s == null || "".equals(s)) {
			s = NULL_STR;
		}
		
		return s;
	}
	
	
	private String getStringTok(StringTokenizer tok) {

		String s = tok.nextToken();
		if (s == null || NULL_STR.equals(s)) {
			s = "";
		}
		return s;
	}
	
	@Override
	public String getFontName() {
		return fontName;
	}

	
    public void setLayoutFile(String lFile) {
		this.layoutFile.setText(lFile);
	}


	public void setFilename(String filename) {
		if (!this.filename.equals(filename)) {
			this.layoutFile.setText("");
		}
		this.filename = filename;
	}


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
