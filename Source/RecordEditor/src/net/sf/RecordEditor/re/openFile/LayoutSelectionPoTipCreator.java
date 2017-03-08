package net.sf.RecordEditor.re.openFile;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.tip.def.TipLayoutMgr;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

public class LayoutSelectionPoTipCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionBasicGenerated> {

	private final boolean isPo;
	
	public static LayoutSelectionPoTipCreator newPoCreator() {
		return new LayoutSelectionPoTipCreator(true);
	}
	

	public static LayoutSelectionPoTipCreator newTipCreator() {
		return new LayoutSelectionPoTipCreator(false);
	}

	private LayoutSelectionPoTipCreator(boolean isPo) {
		super();
		this.isPo = isPo;
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionBasicGenerated create() {
		if (isPo) {
			return new LayoutSelectionPo();
		}
		return new LayoutSelectionTip();
	}
	
	
	public static class LayoutSelectionPo extends LayoutSelectionBasicGenerated  {



		/**
		 * Add layout Selection to panel
		 * @param pnl panel to be updated
		 * @param goPanel panel holding go button
		 * @param layoutCreate1 layout create button1
		 * @param layoutCreate2  layout create button2
		 * @param layoutFile layout file
		 */
		protected void addLayoutSelection(BasePanel pnl, JPanel goPanel,
				JButton layoutCreate1, JButton layoutCreate2, FileSelectCombo layoutFile) {
	  	    

		}


//		public void notifyFileNameChanged(String newFileName) {
//			super.notifyFileNameChanged(newFileName);
//		}


		@Override
		public String getLayoutName() {
			return    "PoSchema" + SEPERATOR; 
		}


		/**
		 * Get Layout details
		 * @return record layout
		 */
		@Override
		public final AbstractLayoutDetails getRecordLayout(String fileName) {
			return PoLayoutMgr.getPoLayout();
		}
	}
	
	
	public static class LayoutSelectionTip extends LayoutSelectionBasicGenerated  {



		/**
		 * Add layout Selection to panel
		 * @param pnl panel to be updated
		 * @param goPanel panel holding go button
		 * @param layoutCreate1 layout create button1
		 * @param layoutCreate2  layout create button2
		 * @param layoutFile layout file
		 */
		protected void addLayoutSelection(BasePanel pnl, JPanel goPanel,
				JButton layoutCreate1, JButton layoutCreate2, FileSelectCombo layoutFile) {
		}


//		public void notifyFileNameChanged(String newFileName) {
//			super.notifyFileNameChanged(newFileName);
//		}


		@Override
		public String getLayoutName() {
			return    "TipSchema" + SEPERATOR; 
		}


		/**
		 * Get Layout details
		 * @return record layout
		 */
		@Override
		public final AbstractLayoutDetails getRecordLayout(String fileName) {
			return TipLayoutMgr.getTipLayout();
		}
	}

}
