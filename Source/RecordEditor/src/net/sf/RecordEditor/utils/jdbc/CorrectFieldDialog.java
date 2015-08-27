/*
 * Created on 31/08/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * @author Bruce Martin
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class CorrectFieldDialog extends JDialog implements ActionListener {

    private static final int FIELD_LENGTH = 15;
    private JTextField field   = new JTextField(FIELD_LENGTH);

	private JTextField oldVal  = new JTextField(FIELD_LENGTH);
	private JTextField newVal  = new JTextField(FIELD_LENGTH);
	private JTextField message = new JTextField();

	private JButton next   = SwingUtils.newButton("Next >");
	private JButton stop   = SwingUtils.newButton("Stop");
	private JPanel  btnPnl = new JPanel();

	private  BasePanel dtls = new BasePanel();

	private  CorrectCallBack callBack;




	/**
	 * Correct a Records value
	 *
	 * @param frame frame being displayed
	 * @param parent parent
	 * @param msg initial error message
	 */
	public CorrectFieldDialog(final JFrame frame,
	        				  final CorrectCallBack parent,
	        				  final String msg)  {
		super(frame, true);

		callBack = parent;

		field.setEnabled(false);
		oldVal.setEnabled(false);

		next.addActionListener(this);
		stop.addActionListener(this);
		btnPnl.add(next);
		btnPnl.add(stop);
		message.setText(msg);

		dtls.addLineRE("Field", field);
		dtls.setGapRE(BasePanel.GAP2);

		dtls.addLineRE("Old Value", oldVal);
		dtls.addLineRE("New Value", newVal);
		dtls.setGapRE(BasePanel.GAP2);


		dtls.addComponentRE(1, 3, BasePanel.PREFERRED, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL, btnPnl);


		dtls.addMessage(message);
		//dtls.done();
		getContentPane().add(dtls);
		setResizable (true);
		pack();
	}


	/**
	 * Set the values of the screen fields
	 *
	 * @param currVal current field value
	 * @param nVal new value that is in error
	 * @param name field name
	 */
	public void setFields(String currVal, String nVal, String name)  {

		field.setText(name);
		oldVal.setText(currVal);
		newVal.setText(nVal);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == next) {
			String msg = callBack.getErrorMsg(newVal.getText());
			if ("".equals(msg)) {
				setVisible(false);
			} else {
				message.setText(msg);
			}
		} else {
			callBack.processStop();
			setVisible(false);
		}

	}


//	public static void setJfp(JFrame jfp) {
//		CorrectFieldDialog.jfp = jfp;
//	}
}
