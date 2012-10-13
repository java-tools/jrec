package net.sf.RecordEditor.utils.swing.saveRestore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public class LoadButton<What> extends JButton implements ActionListener {

	private final IUpdateDetails<What> saveCallBack;
	private final String dir;
	private JFileChooser fileChooser = null;
	@SuppressWarnings("rawtypes")
	private final Class dtlsClass;

	public LoadButton(IUpdateDetails<What> save, String directory, @SuppressWarnings("rawtypes") Class classOfWhat) {
		super(LangConversion.convert(LangConversion.ST_BUTTON, "Load"), Common.getRecordIcon(Common.ID_OPEN_ICON));

		saveCallBack = save;
		dir = directory;
		dtlsClass = classOfWhat;

		this.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this) {
			try {
				if (fileChooser == null) {
					fileChooser = new JFileChooser();
				}

				if (dir != null) {
					fileChooser.setSelectedFile(new File(dir));
				}

				int ret = fileChooser.showOpenDialog(this);

				if (ret == JFileChooser.APPROVE_OPTION) {
					net.sf.RecordEditor.jibx.JibxCall<What> jibx
					= new net.sf.RecordEditor.jibx.JibxCall<What>(dtlsClass);

					System.out.println(fileChooser.getSelectedFile().getPath());
					What saveDetails = jibx.marshal(fileChooser.getSelectedFile().getPath());

					saveCallBack.update(saveDetails);
				}
			} catch (NoClassDefFoundError e0) {
				e0.printStackTrace();
				Common.logMsg("Jibx Call Failed: Class not loaded", null);
			} catch (Exception ex) {
				ex.printStackTrace();
				Common.logMsg("Execute Error", ex);
			}
		}
	}


}
