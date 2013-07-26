package net.sf.RecordEditor.re.install;

import javax.swing.JFrame;

import net.sf.RecordEditor.utils.wizards.AbstractWizard;


public class InstallerH2 extends  AbstractWizard<InstallDetails> {

	private final IInstallerPnl[] panels;

	public  InstallerH2(/*InstallDetails details*/) {
		this(new JFrame());
	}
	private  InstallerH2(JFrame frame) {
		super(frame, new InstallDetails());

		boolean existingInstall = true;
		IInstallerPnl[] panels = {
					new InstallPnl1GetDataSource(existingInstall),
					new InstallPnl2WarnExistingInstall(),
					new InstallPnl3InstallType(),
					new InstallPnl4RunSql(),
		};

		super.getWizardDetails().existingDefinition = existingInstall;


		this.panels = panels;
		super.setPanels(panels);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#finished(java.lang.Object)
	 */
	@Override
	public void finished(InstallDetails details) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new InstallerH2();
	}

}
