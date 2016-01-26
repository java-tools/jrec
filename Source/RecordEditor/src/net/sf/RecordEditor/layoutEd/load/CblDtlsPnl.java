package net.sf.RecordEditor.layoutEd.load;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class CblDtlsPnl {
	public final BaseHelpPanel cblPnl = new BaseHelpPanel("cblDtl");
	
	private JEditorPane tips;

	public final JScrollPane scrollPane; 
	
	private final CblLoadData cblDtls;
	
	public CblDtlsPnl(CblLoadData cblDtls) {
		this.cblDtls = cblDtls;
		
		init_100_setupFields();
		init_200_LayoutScreen();
		
		scrollPane = new JScrollPane(cblPnl);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
	}
	
	private void init_100_setupFields() {
	    
	    tips = new JEditorPane("text/html", LeMessages.COBOL_IMPORT_MSG.get());
	    tips.setEditable(false);
	    
	    tips.setCaretPosition(1);
//	    tipsPane = new JScrollPane(tips);
//	    tipsPane.setPreferredSize(new Dimension(
//	    		SwingUtils.STANDARD_FONT_WIDTH * 120,
//	    		SwingUtils.STANDARD_FONT_HEIGHT * 12));

	}
	private void init_200_LayoutScreen() {
		JPanel goPnl = new JPanel(new BorderLayout());
		goPnl.add(BorderLayout.EAST, cblDtls.go);
		
		cblPnl.addComponentRE(1, 4, SwingUtils.STANDARD_FONT_HEIGHT * 18,
						BasePanel.GAP2,  BasePanel.FULL, BasePanel.FULL, tips)
			  .addLineRE(        "Cobol Copybook", cblDtls.copybookFileCombo)
			  .addLineRE("Sample Cobol Data File", cblDtls.sampleFileCombo)
			  	                                             .setGapRE(BasePanel.GAP1)
			  .addLineRE(  "Copybook Line format", cblDtls.getCopybookFormatCombo())
			  .addLineRE(        "Split Copybook", cblDtls.getSplitOptionsCombo())
			  .addLineRE(             "Font Name", cblDtls.fontNameCombo)
			  .addLineRE(         "Cobol Dialect", cblDtls.getDialectCombo())

			  .addLineRE(        "File Structure", cblDtls.getFileStructureCombo())
			  .addLineRE(                "System", cblDtls.system)
			                                                 .setGapRE(BasePanel.GAP2)
		      .addLineRE(                     "", goPnl);
		
		cblPnl.done();
	}

}
