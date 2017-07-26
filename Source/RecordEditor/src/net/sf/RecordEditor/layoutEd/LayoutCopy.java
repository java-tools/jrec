/**
 *
 */
package net.sf.RecordEditor.layoutEd;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LayoutCopy extends ReFrame  implements ActionListener {

	private Pane from = new Pane(true);
	private Pane to = new Pane(false);
	private static String[] dbs = Common.getSourceId();
	private static CopybookWriterManager writerManager = CopybookWriterManager.getInstance();


	private JButton go = SwingUtils.newButton("Copy");;

	private JTextArea msgField = new JTextArea();
	private	Dimension dimTo = to.getPreferredSize();
	private	Dimension dimFrom = from.getPreferredSize();

	/**
	 * Copy layout function
	 *
	 */
	public LayoutCopy() {
		super("", "Copy Layouts", null);

		JSplitPane pnl;
		BaseHelpPanel main = new BaseHelpPanel();
		Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();

		main.setHelpURLre(Common.formatHelpURL(Common.HELP_COPY_LAYOUT));

		dimFrom.setSize(dimFrom.getWidth(), dimTo.getHeight());
		from.setPreferredSize(dimFrom);

//		to.setHeight(dim.getHeight());
		go.addActionListener(this);
		to.dbCombo.addActionListener(this);

		pnl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, from, to);

		main.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
		        pnl);

		main.addLineRE("", null, go);
		main.setGapRE(BasePanel.GAP3);
		main.addComponentRE(1, 5, 90 , BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(msgField));
		this.getContentPane().add(main);


		this.pack();
//		from.setHeight(to.getHeight());
		if (this.getWidth() >= screenSize.getWidth()) {
			this.setBounds(getY(), getX(), (int) screenSize.getWidth() - getX(), getHeight());
		}
		setVisible(true);
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {


		if (event.getSource() == go) {
			ap_100_Copy();
			//to.setEnabledValue();
		} else if (event.getSource() == to.dbCombo) {
			to.setEnabledValue();
		}
	}

	/**
	 * Copy Copybook's between DB's / Files
	 */
	private void ap_100_Copy() {
		boolean free = Common.isSetDoFree(false);
		int fromIdx = from.dbCombo.getSelectedIndex();
		int toIdx = to.dbCombo.getSelectedIndex();
		String name = from.layoutName.getText();

		if (fromIdx == toIdx) {
			msgField.setText("From and To Database can not be the same");
		} else if ("".equals(name.trim())) {
			msgField.setText("You must enter the copybook name (you can use %)");
		} else {
			try {
				RecordRec r;
				ExternalRecord rec;
				StringBuffer msg = new StringBuffer("Copied:");

				ExtendedRecordDB dbFrom = new ExtendedRecordDB();

				CopybookLoaderFactoryDB.setCurrentDB(fromIdx);
				dbFrom.setConnection(new ReConnection(fromIdx));
				dbFrom.resetSearch();
				dbFrom.setSearchRecordName(AbsDB.opLike, name);
				if (from.listBox.isSelected()) {
					dbFrom.setSearchListChar(AbsDB.opEquals, "Y");
				}
				dbFrom.open();

				r = dbFrom.fetch();

				if (toIdx < to.fileOption) {
					ExtendedRecordDB dbTo = new ExtendedRecordDB();
					dbTo.setConnection(new ReConnection(toIdx));

					while (r != null) {
						rec = r.getValue();
						rec.setNew(true);
						dbTo.checkAndUpdate(rec);

						msg.append("\n      ").append(r.getRecordName());
						r = dbFrom.fetch();

					}

		        	dbTo.close();
				} else if (toIdx == to.fileOption) {
					CopybookWriter writer = writerManager.get(to.outputFormat.getSelectedIndex());
					String dir = fixDir(to.outputDirectory.getText());
//					if (dir != null && (dir.endsWith("/*") || dir.endsWith("\\*"))) {
//						dir = dir.substring(0,dir.length() - 1);
//					}
//					if (dir != null && (! "".equals(dir)) && (! dir.endsWith("/")) && (! dir.endsWith("\\"))) {
//						dir = dir + Common.FILE_SEPERATOR;
//					}

					while (r != null) {
						writer.writeCopyBook(dir, r.getValue(), Common.getLogger());

						msg.append("\n      ").append(r.getRecordName());
						r = dbFrom.fetch();
					}
				} else {
					net.sf.RecordEditor.layoutEd.utils.LayoutVelocity velocity
							= new net.sf.RecordEditor.layoutEd.utils.LayoutVelocity(fromIdx);

					String outputFile;
					String ext = to.extension.getText();
					if (!"".equals(ext)) {
						ext = "." + ext;
					}

					String dir = fixDir(to.outputDirectory.getText());

					while (r != null) {
						outputFile = dir
						  + ExternalConversion.copybookNameToFileName(r.getRecordName())
						  + ext;

						velocity.run(to.template.getText(), r, outputFile);

						msg.append("\n      ").append(r.getRecordName());
						r = dbFrom.fetch();
					}
				}
	        	msgField.setText(msg.toString());
	        	dbFrom.close();
	        	from.setPreferredSize(dimFrom);
	        	to.setPreferredSize(dimTo);
			} catch (Exception e) {
				Common.logMsgRaw(e.getMessage(), e);
				e.printStackTrace();
				msgField.setText(e.getMessage());
			}
		}

		Common.setDoFree(free, toIdx);
		Common.setDoFree(free, toIdx);
	}


	protected String fixDir(String dir) {
		if (dir == null) {
			dir = "";
		}
		if (dir.endsWith("*")) {
			dir = dir.substring(0, dir.length() -1);
		}
		if ((! dir.endsWith("\\")) || (! dir.endsWith("/"))) {
			dir = dir + Common.FILE_SEPERATOR;
		}
		return dir;
	}



	/**
	 * Panel to Display Source / Destination for copy
	 *
	 *
	 * @author Bruce Martin
	 *
	 */
	private static class Pane extends BaseHelpPanel {
		private static final String FILE_VALUE = LangConversion.convertComboItms("Layout Copy", "File");
		protected JComboBox  dbCombo     = new JComboBox();
		protected JComboBox  outputFormat= null;
		protected FileSelectCombo outputDirectory;
		protected JTextField layoutName;
		protected JTextField extension = null;
		protected FileSelectCombo template;
		protected int fileOption       = -1;
		protected int velocityOption   = -1;
		protected JCheckBox listBox    = new JCheckBox();

		/**
		 * Panel to Display Source / Destination for copy
		 *
		 * @param isFrom is it the from pane
		 */
		public Pane(boolean isFrom) {
			int i;

			setHelpURLre(Common.formatHelpURL(Common.HELP_COPY_LAYOUT));

			String header = "Destination";
			if (isFrom) {
				header = "Source";
			}
			addHeadingRE(header);
			addLineRE("Data Base", dbCombo);

			for (i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
				dbCombo.addItem(dbs[i]);
			}

			if (isFrom) {
				dbCombo.setSelectedIndex(Common.getConnectionIndex());
				setGapRE(BasePanel.GAP1);

				layoutName = new JTextField(20);
				addLineRE("Layout", layoutName);
				addLineRE("Only Listed", listBox);
			} else {
				//String s;
				//outputFormat = new JComboBox();
			/*	System.out.println();
				System.out.println("@@ "  + writerManager.getNumberUsed());
				System.out.println("@@ "  + writerManager.getNumberUsed());*/
//				for (i = 0; i < 3 + writerManager.getNumberUsed(); i++) {
//					s = writerManager.getName(i);
//					if (s != null && ! "".equals(s)) {
//						outputFormat.addItem(s);
//					}
//				}

				outputFormat = new BmKeyedComboBox(new ManagerRowList(writerManager, false), false);
				outputFormat.setSelectedIndex(Common.getCopybookWriterIndex());

				outputDirectory = new FileSelectCombo(Parameters.XML_EXPORT_DIRS, 9, true, true, true);
				//outputDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				outputDirectory.setText(Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY));
				addLineRE("Output Format", outputFormat /*, go*/);
				addLineRE("Output Directory", outputDirectory);

				fileOption = dbCombo.getItemCount();
				dbCombo.addItem(FILE_VALUE);

		       if (Common.isVelocityAvailable()) {
		    	   template  = new FileSelectCombo(Parameters.VELOCITY_SCHEMA_SKELS_LIST, 9, true, false);
		    	   extension = new JTextField();
		    	   template.setText(Common.OPTIONS.copybookVelocityDirectory.getWithStar());
		    	   extension.setText("htm");

		    	   addLineRE("Extension", extension);
		    	   setGapRE(BasePanel.GAP1);
			       addLineRE("Velocity Template", template);
			       setGapRE(BasePanel.GAP1);

			       velocityOption = dbCombo.getItemCount();
			       dbCombo.addItem("Velocity");
		        }

				setEnabledValue();
			}

			this.setBorder(BorderFactory.createEtchedBorder());
		}

		public void setEnabledValue() {
			if (outputFormat != null) {
				int idx = dbCombo.getSelectedIndex() ;

				outputFormat.setEnabled(idx == fileOption);
				outputDirectory.setEnabled(idx >= fileOption);

				if (extension != null) {
					boolean enabled = idx == velocityOption;
					extension.setEnabled(enabled);
					template.setEnabled(enabled);
				}
			}
		}
	}

}
