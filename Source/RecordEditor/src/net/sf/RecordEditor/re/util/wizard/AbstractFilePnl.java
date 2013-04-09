package net.sf.RecordEditor.re.util.wizard;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.jibx.compare.BaseCopyDif;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.openFile.AbstractOpenFilePnl;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

@SuppressWarnings("serial")
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


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractOpenFilePnl#processFile(java.lang.String, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.JRecord.IO.AbstractLineIOProvider, boolean)
	 */
	@Override
	protected void processFile(String sFileName, AbstractLayoutDetails layout,
			AbstractLineIOProvider lineIoProvider, boolean pBrowse)
			throws Exception {
	}




	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#isMore()
	 */
	@Override
	public boolean skip() {
		return false;
	}

}
