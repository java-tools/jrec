package net.sf.RecordEditor.re.util.csv;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.layoutWizard.WizardFileLayout;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


/**
 * This class lets the user select a <i>file schema</i> that defines a file
 * 
 * @author Bruce Martin
 *
 */
public class SchemaSelection implements FilePreview, BasicLayoutCallback, ChangeListener {

	//private static final String SCHEMA_ID = "SCHEMA";
	private BaseHelpPanel pnl = new BaseHelpPanel();

	private FileSelectCombo layoutFile = new FileSelectCombo(Parameters.SCHEMA_LIST, 25, true, false);
	private final JLabel dialectLbl = new JLabel("Cobol Dialect");
	private final JLabel fontLbl = new JLabel("Font");
	private final ComputerOptionCombo dialectCombo = new ComputerOptionCombo();
	private final FontCombo fontCombo = new FontCombo();

	private JButton editBtn = SwingUtils.newButton("Edit");
	private JButton schemaWizardBtn = SwingUtils.newButton("Wizard", Common.getRecordIcon(Common.ID_WIZARD_ICON));
	

	private JTable fileTable = new JTable();

	private  JTextComponent msg;

	private String fontName = "";
	private String filename = "";
	private int fileStructure;
//	private int recordLength = 0;

	private byte[] lastData;
	private String lastLayoutFile = null;


	/**
	 * This class lets the user select a <i>file schema</i> that defines a file
	 * 
	 * @param message message field where message are to be written
	 */
	public SchemaSelection(JTextComponent message) {
		msg = message;

		fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		layoutFile.setText(Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY));

		
		
		pnl.addLineRE("Layout File", layoutFile, schemaWizardBtn)
			  .setFieldsToActualSize();
		pnl.addLineRE(   dialectLbl, dialectCombo)
		   .addLineRE(      fontLbl, fontCombo)
		      .setGapRE(BasePanel.GAP1)
		      .setFieldsToFullSize();
		pnl.addLineRE(null, null, editBtn);
//		pnl.addComponentRE(
//				1, BasePanel.LAST_USED_COLUMN, BasePanel.PREFERRED, BasePanel.GAP,
//				 BasePanel.RIGHT, BasePanel.FULL,
//		        editBtn);
		
		pnl.addComponentRE(
				1,  BasePanel.LAST_USED_COLUMN, BasePanel.FILL, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
		        fileTable);
		
		layoutFile.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				if (lastLayoutFile == null || ! lastLayoutFile.equals(layoutFile.getText())) {
					buildFileLayout(lastData);
				}
				setCobolVisible();
			}
		});
//		layoutFile.addFcFocusListener(new FocusAdapter() {
//			@Override public void focusLost(FocusEvent e) {
//				if (lastLayoutFile == null || ! lastLayoutFile.equals(layoutFile.getText())) {
//					buildFileLayout(lastData);
//				}
//				setCobolVisible();
//			}
//		});

		dialectCombo.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				buildFileLayout(lastData);

				setCobolVisible();
			}
		});
		schemaWizardBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new WizardFileLayout(new ReFrame("", "File Wizard", "", null), filename, SchemaSelection.this, false, true);
			}
		});
		fontCombo.addTextChangeListner(this);

		setCobolVisible();
	}

	@Override public void stateChanged(ChangeEvent e) {
		buildFileLayout(lastData);
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
			return true;
		}

		if (layoutId != null && ! "".equals(layoutId)) {
			setFileDescription(layoutId);
		}

		lastData = data;
		return buildFileLayout(data);
	}

	private boolean buildFileLayout(byte[] data) {
		try {
			LayoutDetail layout = getLocalLayout(null, data);

			lastLayoutFile = layoutFile.getText();

//			if (layout == null && data != null) {
//				FileAnalyser analyser = FileAnalyser.getAnaylser(data, "");
//				layout = analyser.getLayoutDetails(true).asLayoutDetail();
//			}
			if (layout == null) {
				System.out.println("No Layout Generated !!!");
				return false;
			}
			fileStructure = layout.getFileStructure();
			fontName = layout.getFontName();
//			recordLength = layout.getMaximumRecordLength();

			if (data == null) {return false;}
			
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
				r.close();
				is.close();
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
//			r.close();
//			is.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

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

	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		LayoutDetail l = getLocalLayout(font, null);
		if (l != null) {
			return l;
		}

		JDialog d = new JDialog(ReMainFrame.getMasterFrame(), true);
		WizardFileLayout wiz = new WizardFileLayout(d, filename, this, false, false);

		Details details = wiz.getWizardDetails();
		details.fileStructure = fileStructure;
		details.fontName = font;
//		details.recordLength = recordLength;
		details.generateFieldNames = true;

		wiz.changePanel(WizardFileLayout.FORWARD, false);
		if (wiz.getPanelNumber() == 1
		&& (details =wiz.getWizardDetails()) != null
		&& (details.recordLength > 0 || details.fileStructure != Constants.IO_FIXED_LENGTH)) {
			wiz.changePanel(WizardFileLayout.FORWARD, false);
		}

		d.setVisible(true);

		ExternalRecord rec = wiz.getExternalRecord();
		if (rec != null) {
			try {
				return rec.asLayoutDetail();
			} catch (Exception e) {
				Common.logMsg(AbsSSLogger.ERROR, "Layout Generation Failed:", e.getMessage(), null);
			}
		}

		return null;
	}


	@Override
	public String getFileDescription() {

		return SCHEMA_ID+ SEP + getStr(layoutFile.getText())
						+ SEP + dialectCombo.getSelectedIndex()
						+ SEP + getFontName()
						+ SEP + NULL_STR;
	}

	@Override
	public void setFileDescription(String val) {
		StringTokenizer tok = new StringTokenizer(val, SEP, false);

		try {
			tok.nextToken();
			layoutFile.setText(Parameters.expandVars(getStringTok(tok)));
			dialectCombo.setSelectedIndex(getIntTok(tok));
			fontName = getStringTok(tok);
		} catch (Exception e) {

		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.FilePreview#isMyLayout(java.lang.String)
	 */
	@Override
	public boolean isMyLayout(String layoutId, String filename, byte[] data) {
		boolean ret = layoutId != null && layoutId.startsWith(SCHEMA_ID);
		if (ret) {
			setData(filename, data, false, layoutId);
		}
		
		return ret;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.BasicLayoutCallback#setRecordLayout(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void setRecordLayout(int layoutId, String layoutName, String filename) {
		layoutFile.setText(layoutName);
	}


	private LayoutDetail getLocalLayout(String font, byte[] data) {

		String layoutFileName = layoutFile.getText();
		if (! "".equals(layoutFileName)) {
			File f = new File(layoutFileName);

			if (f.exists() && ! f.isDirectory()) {
				try {
//					if (isCobol() && CobolCopybookLoader.isAvailable()) {
//						loader = new CobolCopybookLoader();
//						if (font == null && data != null) {
//							FileAnalyser analyser = FileAnalyser.getAnaylserNoLengthCheck(data, "");
//							font = analyser.getFontName();
//							fileStructure = analyser.getFileStructure();
//						}
//					} else {
//						loader = new RecordEditorXmlLoader();
//					}
					int schemaId = new SchemaAnalyser().schemaType(layoutFileName);
					if (schemaId >= 0) {
						fileStructure = -1;
						if (schemaId == CopybookLoaderFactory.COBOL_LOADER && CobolCopybookLoader.isAvailable()) {
							if (font == null && data != null) {
								FileAnalyser analyser = FileAnalyser.getAnaylserNoLengthCheck(data, "");
								if (font == null && data != null) {
									font = analyser.getFontName();
								}
								fileStructure = analyser.getFileStructure();
								try {
									fontCombo.removeTextChangeListner(this);
									fontCombo.setText(font);
									fontCombo.setFontList(analyser.getCharsetDetails().likelyCharsets);
								} finally {
									fontCombo.addTextChangeListner(this);
								}
							}
						}
						CopybookLoader loader = CopybookLoaderFactory.getInstance().getLoader(schemaId);
						//System.out.println("*) *** " + schemaId + " ~ " + loader.getClass().getName());
						ExternalRecord rec = loader.loadCopyBook(layoutFileName, 0, 0, font, dialectCombo.getSelectedValue(), 0, Common.getLogger());
						if (fileStructure >= 0) {
							rec.setFileStructure(fileStructure);
						}
						return rec.asLayoutDetail();
					}
				} catch (Exception e) {
					msg.setText("Layout Gen failed: " + e);
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void setCobolVisible() {
		boolean cobol = isCobol();
		dialectCombo.setVisible(cobol);
		dialectLbl.setVisible(cobol);
		fontCombo.setVisible(cobol);
		fontLbl.setVisible(cobol);
	}

	private boolean isCobol() {
		String layoutFileName = layoutFile.getText();

		return CobolCopybookLoader.isAvailable()
			&& new SchemaAnalyser().schemaType(layoutFileName) == CopybookLoaderFactory.COBOL_LOADER;
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

	private int getIntTok(StringTokenizer tok) {
		int ret = 0;
		try {
			ret = Integer.parseInt(tok.nextToken());
		} catch (Exception e) {
		}

		return ret;
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
