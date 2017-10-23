package net.sf.RecordEditor.diff.xml;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.FileTreeComboItem;

public class XmlCompareDirOptions implements ActionListener {

	private ReFrame frame = new ReFrame("", "Directory Compare", null);
	public final BaseHelpPanel pnl = new BaseHelpPanel("DiffXml");
	
	public final FileSelectCombo dir1, dir2;
	public final JTextField filterTxt = new JTextField(11);
	public final JButton compareBtn = SwingUtils.newButton("compare");
	
	private final boolean directoryCompare;
	
	public XmlCompareDirOptions(boolean directoryCompare) {
		
		this.directoryCompare = directoryCompare;
		this.dir1 = new FileSelectCombo(Parameters.XML_DIR_LIST, 25, true, directoryCompare);
		this.dir2 = new FileSelectCombo(Parameters.XML_DIR_LIST1, 25, true, directoryCompare);
		
		init_200_LayoutScreen();
		init_300_finalise();
	}

	private void init_200_LayoutScreen() {
		String prompt = "File ";
		if (directoryCompare) {
			prompt = "Firectory ";
		}
		
		pnl.addLineRE(prompt + "1" , dir1)
		   .addLineRE(prompt + "2" , dir2)
		   	 .setGapRE(BaseHelpPanel.GAP1);
		
		if (directoryCompare) {
			pnl .setFieldsToActualSize();
			pnl	.addLineRE("Filter", filterTxt)
		   			.setGapRE(BaseHelpPanel.GAP1)
		   			.setFieldsToFullSize();
		}
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createEmptyBorder());
		p.add(BorderLayout.EAST, compareBtn);

		pnl.addLineRE("", p);
	}
	
	private void init_300_finalise() {
		int width = SwingUtils.CHAR_FIELD_WIDTH * 90;
		int screenWidth = ReFrame.getDesktopWidth();
		if (screenWidth > 0 && screenWidth < width) {
			width = screenWidth;
		}

		filterTxt.setText(Parameters.getString(Parameters.XML_DIR_FILTER));
		
		pnl.setPreferredSize(new Dimension(width, pnl.getPreferredSize().height));
		frame.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
		frame.addMainComponent(pnl);

		frame.setVisible(true);
		

		compareBtn.addActionListener(this);
		
		setFileName(dir1);
		setFileName(dir2);
	}

	private void setFileName(FileSelectCombo directoryCombo) {
		List<FileTreeComboItem> list = directoryCombo.getFileComboList();
		if (list != null && list.size() > 0) {
			directoryCombo.setText(list.get(0).getFullname());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String dir1Str = dir1.getText();
		String dir2Str = dir2.getText();
		if (dir1Str == null || dir1Str.length() == 0 || dir2Str == null || dir2Str.length() == 0) {
			Common.logMsg("You must enter both directories", null);
		} else {
			Parameters.setProperty(Parameters.XML_DIR_FILTER, filterTxt.getText());
			new XmlCompareDir(dir1Str, dir2Str, filterTxt.getText());
		}
	}
	
	
}
