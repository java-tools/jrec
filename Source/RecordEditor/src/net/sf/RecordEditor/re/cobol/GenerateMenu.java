package net.sf.RecordEditor.re.cobol;

import javax.swing.JMenu;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.SwingUtils;


public class GenerateMenu {
	public final JMenu generateMenu = SwingUtils.newMenu("Generate");
	
	public void bldMenu(ReMainFrame f) {
	    JMenu javaGen = new JMenu("Java");
	    JMenu pythonGen = new JMenu("Python/Ruby");
	    generateMenu.add(new JRecCodeGenAction());
	    generateMenu.add(javaGen);
	    generateMenu.add(pythonGen);
	    generateMenu.addSeparator();
	    generateMenu.add(new ReMainFrame.ShowURI("Generate Information", Common.formatHelpURI(Common.HELP_GENERATE)));

	    
	    javaGen.add(f.addAction(new CodeGenAction("JRecord IoBuilder", "javaJRec", false, CodeGenAction.ST_ANY)));
	    javaGen.add(f.addAction(new CodeGenAction("JRecord LineWrapper", "javaJRecWrapper", false, CodeGenAction.ST_ANY)));
	    javaGen.add(f.addAction(new CodeGenAction("JRecord Pojo", "javaJRecPojo", false, CodeGenAction.ST_ANY)));
	    
	    pythonGen.add(f.addAction(new CodeGenAction("Python Csv IO", "pyCsvIO", false, CodeGenAction.ST_TEXT_CSV)));
	    pythonGen.add(f.addAction(new CodeGenAction("Python Pandas Csv IO", "pyCsvPandas", false, CodeGenAction.ST_TEXT_CSV)));
	    pythonGen.add(f.addAction(new CodeGenAction("Python Fixed IO", "pyFixedIO", false, CodeGenAction.ST_TEXT_FIXED)));
	    pythonGen.add(f.addAction(new CodeGenAction("Python Fixed Pandas", "pyFixedPandas", false, CodeGenAction.ST_TEXT_FIXED)));
	    pythonGen.addSeparator();
	    pythonGen.add(f.addAction(new CodeGenAction("Ruby Csv IO", "rubyCsvIO", false, CodeGenAction.ST_TEXT_CSV)));

	}
}
