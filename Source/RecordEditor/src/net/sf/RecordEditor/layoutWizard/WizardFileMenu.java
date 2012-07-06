package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.UIManager;

import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class WizardFileMenu implements ActionListener {

	private JButton wizBtn = new JButton("*");
	private JButton copyBtn = new JButton("*");

	public WizardFileMenu() {
		ReFrame frame = new ReFrame("Menu");
		BasePanel pnl = new BasePanel();

		pnl.setGap(BasePanel.GAP3);
		pnl.addMenuItem("Layout Wizard (For File copybooks) ", wizBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addMenuItem("Convert Layout ", copyBtn);
		pnl.setGap(BasePanel.GAP3);

		frame.addMainComponent(pnl);

		wizBtn.addActionListener(this);
		copyBtn.addActionListener(this);
		frame.setVisible(true);

		new WizardFileLayout("");
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == wizBtn) {
			new WizardFileLayout(new ReFrame("", "File Wizard","", null), "", null, true, true);
		} else {
			new ConvertLayout();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 new ReMainFrame("Wizard - Generate Copybooks", "", "Wiz");
		// JFrame.setDefaultLookAndFeelDecorated(true);
		 try {
		 UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		 } catch (Exception e) {
		 }
		 new WizardFileMenu();
	}

}
