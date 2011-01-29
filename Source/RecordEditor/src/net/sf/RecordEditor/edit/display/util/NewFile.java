package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.edit.file.storage.DataStoreStd;
import net.sf.RecordEditor.edit.open.StartEditor;
import net.sf.RecordEditor.utils.csv.NewCsvFile;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class NewFile {

	private final ReFrame frame = new ReFrame("","New Csv File", null);
	private JTabbedPane tab = new JTabbedPane();
	private JButton    goBtn	     = new JButton("Create");
	private JTextField msgTxt	     = new JTextField();
	private AbstractLayoutSelection<?> layoutSelect;
	

	public NewFile(AbstractLayoutSelection<?> layoutSelection) {
		
		layoutSelect = layoutSelection;

		init_100_Setup();
		init_200_LayoutScreen();
		init_300_Listners();
		
		frame.setVisible(true);
	}

	private void init_100_Setup() {
		

	}
	
	private void init_200_LayoutScreen() {
		BasePanel pnl = new BasePanel();
		BaseHelpPanel panel = new BaseHelpPanel();
		NewCsvFile csvFile = new NewCsvFile(frame);
				
		panel.setGap(BasePanel.GAP2);

		layoutSelect.addLayoutSelection(panel, null, null, null, null);

		panel.setGap(BasePanel.GAP1);
		panel.addLine(null, null, goBtn);
		panel.setGap(BasePanel.GAP4);
		panel.addMessage(msgTxt);
		panel.setHeight(BasePanel.HEIGHT_1P6);
		
		tab.addTab("Normal File", panel);
		tab.addTab("Csv File", csvFile.panel);
		
		pnl.setGap(2);
		pnl.addComponent(0, 6, BasePanel.FILL,
		        2,
		        BasePanel.FULL, BasePanel.FULL,
		        tab); 

		frame.addMainComponent(pnl);
		frame.setSize(
				Math.min(frame.getWidth() + 80, 
						 ReFrame.getDesktopWidth() - 2), 
				Math.min(frame.getHeight(), 
						 ReFrame.getDesktopHeight() - 2));

		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	}
	
	
	
	private void init_300_Listners() {
		goBtn.addActionListener(new ActionListener() {

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AbstractLayoutDetails<?, ?> layout = layoutSelect.getRecordLayout("");
					
					if (layout == null) {
						msgTxt.setText("Error Retrieving Layout");
					} else {
						FileView<?> file = new FileView(
								new DataStoreStd(layout),
								null,
								null,
								false);
						StartEditor.doOpen(file, 0, false);
						
						frame.setVisible(false);
						frame.doDefaultCloseAction();
					}
				} catch (Exception ex) {
					msgTxt.setText("Error Creating File: " + ex.getMessage());
				}
			}
		});
	}

}
