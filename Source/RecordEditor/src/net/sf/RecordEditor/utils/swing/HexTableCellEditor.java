package net.sf.RecordEditor.utils.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;




@SuppressWarnings("serial")
public class HexTableCellEditor
extends AbstractCellEditor implements TableCellEditor {

	private static final byte[] EMPTY_BYTE_ARRAY = {};

	private AbstractHexDisplay component;
	private byte[] lastValue = EMPTY_BYTE_ARRAY;

	public HexTableCellEditor(AbstractHexDisplay displayItem) {
		component = displayItem;
	}

	@Override
	public Object getCellEditorValue() {

		try {
			return component.getBytes(lastValue);
		} catch (Exception e) {
			return new ErrorDialog(component.clone(), lastValue, e.getMessage());
		}
	}
	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {

		if (arg1 instanceof byte[]) {
			lastValue = (byte[]) arg1;
			component.setHex(lastValue);
		}

		return component.getComponent();
	}

	/**
	 * Hex Error Dialog
	 * @author Bruce Martin
	 *
	 */
	private static class ErrorDialog  implements ActionListener, DelayedFieldValue {
		public boolean exit;

		private JDialog dialog;
		private JTextField rulerField = new JTextField();
		private JButton goBtn = SwingUtils.newButton("go");
		private JButton exitBtn = SwingUtils.newButton("exit");

		public final JTextField msgTxt = new JTextField();

		private AbstractHexDisplay hexDisplay;
		private BasePanel pnl = null;
		private final byte[] lastValue;
		private Object initialMessage;

		/**
		 * Create Error Dialog
		 * @param displ field to display
		 * @param initialValue initial value
		 * @param initialMsg initial message
		 */
		public ErrorDialog(AbstractHexDisplay displ, byte[] initialValue, Object initialMsg) {

			hexDisplay = displ;
			lastValue = initialValue;
			initialMessage = initialMsg;
		}

		private void setupScreen(JFrame parentFrame) {

			if (pnl == null) {
				String s;
				StringBuilder buf = new StringBuilder();
				int num;
				JPanel tp = new JPanel(new BorderLayout());
				pnl = new BasePanel();
				pnl.setGap(BasePanel.GAP1);

				tp.add(BorderLayout.CENTER, hexDisplay.getComponent());
				if (hexDisplay instanceof JTextComponent) {
					tp.add(BorderLayout.SOUTH, rulerField);

					hexDisplay.setFont(SwingUtils.getMonoSpacedFont());
					rulerField.setFont(SwingUtils.getMonoSpacedFont());

					s = ((JTextComponent) hexDisplay).getText();
					num = s.indexOf("\n");
					if (num < 0) {
						num = s.length();
					}

					if (hexDisplay.isTwoBytesPerCharacter()) {
						num = num / 20;
						for (int i = 1; i < num + 1; i++) {
							s = "         " + i;
							buf.append("         +").append(s.substring(s.length() - 10 ));
						}
					} else {
						num = num / 10;
						for (int i = 1; i < num + 1; i++) {
							s = "    " + i;
							buf.append("    +").append(s.substring(s.length() - 5 ));
						}
					}
					rulerField.setText(buf.toString());
				}


				pnl.addLine("Hex Value in error", new JScrollPane(tp));
				pnl.setHeight(BasePanel.HEIGHT_1P1 * 4);
				pnl.setGap(BasePanel.GAP1);
				pnl.addLine("", null, goBtn);
				pnl.addLine("", null, exitBtn);
				pnl.setGap(BasePanel.GAP2);
				pnl.addMessage(msgTxt);

				dialog = new JDialog(parentFrame, true);
				dialog.getContentPane().add(pnl);
				dialog.pack();

				goBtn.addActionListener(this);
				exitBtn.addActionListener(this);
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.swing.DelayedFieldValue#getValue()
		 */
		public Object getValue(JFrame parentFrame) {
			Object o = initialMessage;

			setupScreen(parentFrame);
			do {
				exit = true;
				msgTxt.setText(o.toString());
				dialog.setVisible(true);
				System.out.println("Checking exit " + exit + " "
						+ ((o = getValue(hexDisplay)) instanceof byte[]));
				if (exit) {
					return lastValue;
				}
			} while (! ((o = getValue(hexDisplay)) instanceof byte[]));

			return o;
		}

		/**
		 * get fields value
		 * @param display hex field being displayed
		 * @return value
		 */
		private Object getValue(AbstractHexDisplay display) {
			try {
				return display.getBytes(lastValue);
			} catch (Exception e) {
				return e.getMessage();
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {

			System.out.println("ap 1 " + (event.getSource() == goBtn) );
			if (event.getSource() == goBtn) {
				System.out.println("ap 2 " + (event.getSource() == goBtn) );
				exit = false;
			}
			dialog.setVisible(false);
		}
	}

}
