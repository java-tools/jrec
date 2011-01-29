/**
 * 
 */
package net.sf.RecordEditor.layoutEd;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.External.CopybookWriter;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalConversion;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.edit.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.layoutEd.Record.ExtendedRecordDB;
import net.sf.RecordEditor.layoutEd.Record.RecordRec;
import net.sf.RecordEditor.layoutEd.Record.TypeList;
import net.sf.RecordEditor.utils.RunVelocity;
import net.sf.RecordEditor.utils.TypeNameArray;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.FileChooser;


/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LayoutCopy extends ReFrame  implements ActionListener {

	private pane from = new pane(true);
	private pane to = new pane(false);
	private static String[] dbs = Common.getSourceId();
	private static CopybookWriterManager writerManager = CopybookWriterManager.getInstance();

	
	private JButton go = new JButton("Copy");;

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

		main.setHelpURL(Common.formatHelpURL(Common.HELP_COPY_LAYOUT));
		
		dimFrom.setSize(dimFrom.getWidth(), dimTo.getHeight());
		from.setPreferredSize(dimFrom);

//		to.setHeight(dim.getHeight());
		go.addActionListener(this);
		to.dbCombo.addActionListener(this);
		
		pnl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, from, to);

		main.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
		        pnl);
		
		main.addLine("", null, go);
		main.setGap(BasePanel.GAP3);
		main.addComponent(1, 5, 90 , BasePanel.GAP0,
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
					String dir = to.outputDirectory.getText();
					if (dir != null && (dir.endsWith("/*") || dir.endsWith("\\*"))) {
						dir = dir.substring(0,dir.length() - 1);
					}
					if (dir != null && (! "".equals(dir)) && (! dir.endsWith("/")) && (! dir.endsWith("\\"))) {
						dir = dir + Common.FILE_SEPERATOR;
					}
					
					while (r != null) {
						writer.writeCopyBook(dir, r.getValue(), Common.getLogger());
						
						msg.append("\n      ").append(r.getRecordName());
						r = dbFrom.fetch();
					}						
				} else {
					RunVelocity velocity = RunVelocity.getInstance();
					int key;
					FileWriter w;
					String s, e;
			  		TypeList types = new TypeList(fromIdx, false, false);
			  		TypeNameArray typeNames = new TypeNameArray();
			  		for (int i = 0; i < types.getSize(); i++) {
			  			key = ((Integer) types.getKeyAt(i)).intValue();
			  		
			  			typeNames.set(key, String.valueOf(types.getFieldAt(i)));
			  		}
			  		 
					while (r != null) {
						e = to.extension.getText();
						if (!"".equals(e)) {
							e = "." + e;
						}
						s = to.outputDirectory.getText() 
						  + ExternalConversion.copybookNameToFileName(r.getRecordName())
						  + e;
					    w = new FileWriter(s);
					        //System.out.println(Velocity.FILE_RESOURCE_LOADER_PATH);
					    velocity.genSkel(to.template.getText(), r.getValue(), typeNames, s, w);
					    w.close();
						
						msg.append("\n      ").append(r.getRecordName());
						r = dbFrom.fetch();
					}						
				}
	        	msgField.setText(msg.toString());
	        	dbFrom.close();
	        	from.setPreferredSize(dimFrom);
	        	to.setPreferredSize(dimTo);
			} catch (Exception e) {
				Common.logMsg(e.getMessage(), e);
				e.printStackTrace();
				msgField.setText(e.getMessage());
			}
		}
		
		Common.setDoFree(free, toIdx);
		Common.setDoFree(free, toIdx);
	}



	/**
	 * Panel to Display Source / Destination for copy
	 * 
	 * 
	 * @author Bruce Martin
	 *
	 */
	private static class pane extends BaseHelpPanel {
		protected JComboBox  dbCombo     = new JComboBox();
		protected JComboBox  outputFormat= null;
		protected FileChooser outputDirectory;
		protected JTextField layoutName;
		protected JTextField extension = null;
		protected FileChooser template;
		protected int fileOption       = -1;
		protected int velocityOption   = -1;
		protected JCheckBox listBox    = new JCheckBox();
		
		/**
		 * Panel to Display Source / Destination for copy
		 * 
		 * @param isFrom is it the from pane
		 */
		public pane(boolean isFrom) {
			int i;
			
			setHelpURL(Common.formatHelpURL(Common.HELP_COPY_LAYOUT));
			
			String header = "Destination";
			if (isFrom) {
				header = "Source";
			}
			addHeading(header);
			addLine("Data Base", dbCombo);
			
			for (i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
				dbCombo.addItem(dbs[i]);
			}

			if (isFrom) {
				dbCombo.setSelectedIndex(Common.getConnectionIndex());
				setGap(BasePanel.GAP1);
				
				layoutName = new JTextField(20);
				addLine("Layout", layoutName);
				addLine("Only Listed", listBox);
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
				
				outputDirectory = new FileChooser();
				outputDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				outputDirectory.setText(Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY));
				addLine("Output Format", outputFormat /*, go*/);
				addLine("Output Directory", outputDirectory, outputDirectory.getChooseFileButton());
				
				fileOption = dbCombo.getItemCount();
				dbCombo.addItem("File");
				
		       if (Common.isVelocityAvailable()) {
		    	   template  = new FileChooser(true, "Select");
		    	   extension = new JTextField();
		    	   template.setText(Parameters.getFileName(Parameters.VELOCITY_COPYBOOK_DIRECTORY));
		    	   extension.setText("htm");
		    	   
		    	   addLine("Extension", extension);
		    	   setGap(BasePanel.GAP1);
			       addLine("Velocity Template", template, template.getChooseFileButton());
			       setGap(BasePanel.GAP1);
			       
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
