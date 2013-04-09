package net.sf.RecordEditor.test.general;

import javax.swing.JFrame;

import net.sf.RecordEditor.utils.swing.treeCombo.TreeCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;

public class TestTreeCombo {

	JFrame frame = new JFrame();
	TreeComboItem[] treeDef = {
			new TreeComboItem(10, "1", "", new TreeComboItem[] {
					new TreeComboItem(11, "1", ""),
					new TreeComboItem(12, "2", ""),
					new TreeComboItem(13, "3", ""),
			}),
			new TreeComboItem(20, "2", "", new TreeComboItem[] {
					new TreeComboItem(21, "1", ""),
					new TreeComboItem(22, "2", ""),
					new TreeComboItem(23, "3", ""),
					new TreeComboItem(24, "4", ""),
			}),
			new TreeComboItem(30, "3", "", new TreeComboItem[] {
					new TreeComboItem(31, "1", ""),
					new TreeComboItem(32, "2", ""),
					new TreeComboItem(33, "3", ""),
					new TreeComboItem(34, "4", ""),
					new TreeComboItem(35, "5", ""),
			}),
	};

	TreeCombo treeCmb= new TreeCombo(treeDef);

	public TestTreeCombo() {
		frame.getContentPane().add(treeCmb);
		treeCmb.setSelectedItem(treeDef[0]);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new TestTreeCombo();
	}

}
