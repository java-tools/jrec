package net.sf.RecordEditor.utils.wizards;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.jibx.compare.BaseCopyDif;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.openFile.AbstractOpenFilePnl;

public abstract class AbstractFilePnl<save extends BaseCopyDif>
extends AbstractOpenFilePnl
implements AbstractWizardPanel<save> {



	private JPanel goPanel = new JPanel();

	public AbstractFilePnl(AbstractLayoutSelection selection, String recentFiles) {
		super("", LineIOProvider.getInstance(), null, null,
				Parameters.getApplicationDirectory() + recentFiles,
		        "", selection);
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	protected JPanel getGoPanel() {
		return goPanel;
	}

	@Override
	protected void processFile(String filename, 
			AbstractLayoutDetails fileLayout,
			AbstractLineIOProvider ioProvider, boolean browse) throws Exception {

	}


	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#isMore()
	 */
	@Override
	public boolean skip() {
		return false;
	}

}
