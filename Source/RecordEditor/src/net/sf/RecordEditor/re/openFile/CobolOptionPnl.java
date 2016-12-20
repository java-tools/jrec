package net.sf.RecordEditor.re.openFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.protoGen.cobolOpt.CobolCopybookOption;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;

public class CobolOptionPnl {

	private final BaseHelpPanel pnl = new BaseHelpPanel("CblOpt");

	private ComputerOptionCombo numericFormat;

	private BmKeyedComboBox   fileStructure;
	private final BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
			LineIOProvider.getInstance(), false));
	private final FontCombo fontTxt = new FontCombo();
	private final JRadioButton noneChk, redefChk, levelChk, repeatingLevelChk;
	{
		ButtonGroup grp = new ButtonGroup();
		noneChk = generateRadioButton(grp, "No Split");
		redefChk = generateRadioButton(grp, "Split on redefin");
		levelChk = generateRadioButton(grp, "Split on 01");
		repeatingLevelChk = generateRadioButton(grp, "Highest Repeating Level");

		noneChk.setSelected(true);
	}

	private final JButton goBtn = new JButton("Go");
	private final JButton cancelBtn = new JButton("Cancel");
	private CobolCopybookOption cobolOpt;
	private ActionListener closeAction = null;

	private boolean ok = false;

	private static final JRadioButton generateRadioButton(ButtonGroup grp, String s) {
    	JRadioButton btn = new JRadioButton(LangConversion.convert(LangConversion.ST_FIELD,s));

    	grp.add(btn);
    	return btn;
    }

	public CobolOptionPnl(CobolCopybookOption cobOpt, boolean use01) {

		init_100_setupVariables(use01);
		init_200_setupScreen();

		set(cobOpt);
	}

	private void init_100_setupVariables(boolean use01) {

		fileStructure  = new BmKeyedComboBox(structureModel, false);
		numericFormat  = new ComputerOptionCombo();

//		numericFormat.setSelectedValue(Common.OPTIONS.cobolDialect.get());
		if (use01) {
			levelChk.setSelected(true);
		}
	}



	private void init_200_setupScreen() {

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

		p.add(noneChk);
		p.add(levelChk);
		p.add(redefChk);
		p.add(repeatingLevelChk);


		pnl.addLineFullWidthRE("Cobol Dialect", numericFormat);
		pnl.addLineFullWidthRE("File Structure", fileStructure);
		pnl.addLineFullWidthRE("Font", fontTxt)
			.setGapRE(BasePanel.GAP0);
		pnl.addLineFullWidthRE("Split", p)
			.setGapRE(BasePanel.GAP1);
		pnl.addLineRE("", null, goBtn)
			.setGapRE(BasePanel.GAP0);
		pnl.addLineRE("", null, cancelBtn);

		ActionListener actionListner = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				ok = e.getSource() == goBtn;
				if (closeAction != null) {
					closeAction.actionPerformed(e);
				}
			}
		};
		goBtn.addActionListener(actionListner);
		cancelBtn.addActionListener(actionListner);
	}

	public final BaseHelpPanel getPanel() {
		return pnl;
	}

	public final void set(CobolCopybookOption cobOpt) {
		if (cobOpt != null) {
			numericFormat.setSelectedValue(cobOpt.getCobolDialect());
			fileStructure.setSelectedItem(cobOpt.getFileStructure());
			fontTxt.setText(cobOpt.getFont());

			switch (cobOpt.getSplitOption()) {
			case CopybookLoader.SPLIT_NONE:			noneChk.setSelected(true);		break;
			case CopybookLoader.SPLIT_01_LEVEL:		levelChk.setSelected(true);		break;
			case CopybookLoader.SPLIT_REDEFINE:		redefChk.setSelected(true);		break;
			case CopybookLoader.SPLIT_HIGHEST_REPEATING:		repeatingLevelChk.setSelected(true);		break;
			}
			cobolOpt = cobOpt;
		}

	}

	public final CobolCopybookOption getCobolOptions() {
		int split = CopybookLoader.SPLIT_NONE;
		if (levelChk.isSelected()) {
			split = CopybookLoader.SPLIT_01_LEVEL;
		} else if (redefChk.isSelected()) {
			split = CopybookLoader.SPLIT_REDEFINE;
		} else if (repeatingLevelChk.isSelected()) {
			split = CopybookLoader.SPLIT_HIGHEST_REPEATING;
		}

		return CobolCopybookOption.newBuilder()
						.setCobolDialect(numericFormat.getSelectedValue())
						.setCopybookDateTime(cobolOpt.getCopybookDateTime())
						.setCopybookDir(cobolOpt.getCopybookDir())
						.setCopybookName(cobolOpt.getCopybookName())
						.setFileStructure((Integer)fileStructure.getSelectedItem())
						.setFont(fontTxt.getText())
						.setSplitOption(split)
					.build();
	}

	/**
	 * @param closeAction the closeAction to set
	 */
	public final void setCloseAction(ActionListener closeAction) {
		this.closeAction = closeAction;
	}

	/**
	 * @return the ok
	 */
	public final boolean isOk() {
		return ok;
	}

}
