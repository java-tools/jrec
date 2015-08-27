package net.sf.RecordEditor.re.editProperties;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class StyleEdit  implements ActionListener {

	private JDialog dialog;
	private final BaseHelpPanel panel = new BaseHelpPanel("EditProps_StyleEdit");
	private final Color orginalColor, origiginalBackgroundColor;
	
	private final JTextField typeJTxt = new JTextField();
	private final JCheckBox colorChk = new JCheckBox("Forground Color");
	private final JCheckBox backgroundColorChk = new JCheckBox("Backgroundground Color");
	private final ColorButton colorBtn, backgroundColorBtn;
	private final JButton 	okBtn = SwingUtils.newButton("Ok"),
							cancelBtn = SwingUtils.newButton("Cancel");
	
	private boolean ok = true;
	
	public StyleEdit(JFrame w, JComponent locationComp, String type,  Color currentColor, Color backGroundColor) {
		this.orginalColor = currentColor;
		this.origiginalBackgroundColor = backGroundColor;
		
		typeJTxt.setText(type);
		colorBtn = new ColorButton(currentColor, "Choose Foreground Color");
		backgroundColorBtn = new ColorButton(backGroundColor, "Choose Background Color");
		dialog = new JDialog(w);
		
		init_100_setup();
		init_200_LayoutScreen();
		init_300_Finalise(locationComp);
 
	}
	
	private void init_100_setup() {
		
		colorChk.setSelected(true);
		colorChk.setEnabled(false);
		backgroundColorChk.setSelected(origiginalBackgroundColor != null);
//		backgroundColorChk.setEnabled(true);
		typeJTxt.setEnabled(false);
	}
	
	
	private void init_200_LayoutScreen() {
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(okBtn);
		btnPanel.add(cancelBtn);
		
		panel.setGapRE(BasePanel.GAP2);
		panel.addLineRE("", typeJTxt)
			 .setGapRE(BasePanel.GAP2);
		panel.addLineRE("", colorChk, colorBtn);
		panel.addLineRE("", backgroundColorChk, backgroundColorBtn)
			 .setGapRE(BasePanel.GAP2);
		
		panel.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
		        btnPanel);

		dialog.getContentPane().add(panel);
		dialog.pack();
	}
	
	
	private void init_300_Finalise(JComponent locationComp) {	
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		 
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(locationComp);
		dialog.setVisible(true);
	}

	/**
	 * @return the color
	 */
	public final Color getColor() {
		if (ok) {
			return colorBtn.getColor();
		}
		return orginalColor;
	}

	/**
	 * @return the color
	 */
	public final Color getBackgroundColor() {
		
		if (ok) {
			if (! backgroundColorChk.isSelected()) {
				return null;
			}
			return backgroundColorBtn.getColor();
		}
		return origiginalBackgroundColor;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ok = e.getSource() == okBtn;
		dialog.setVisible(false);
	}
	
	
}
