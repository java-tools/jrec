package net.sf.RecordEditor.re.cobol;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class CblDtlsPnl {
	
	public final JSplitPane   cblPnl;// = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private final BaseHelpPanel cblDtlPnl = new BaseHelpPanel("cblDtl");
	
	private JEditorPane tips;

	public final JScrollPane scrollPane; 
	
	private final CblLoadData cblDtls;
	
	public CblDtlsPnl(CblLoadData cblDtls, boolean isCobolLoad, JButton actionBtn) {
		this.cblDtls = cblDtls;
		
		init_100_setupFields(isCobolLoad);
		init_200_LayoutScreen(isCobolLoad, actionBtn);
		
		scrollPane = new JScrollPane(cblDtlPnl);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		cblPnl = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				new JScrollPane(tips), 
				scrollPane);
		//cblPnl.setTopComponent(new JScrollPane(tips));
		//cblPnl.setBottomComponent(scrollPane);
		cblPnl.setDividerLocation(SwingUtils.STANDARD_FONT_HEIGHT * 18);
		//cblPnl.setDividerSize(newSize);

	}
	
	private void init_100_setupFields(boolean isCobolLoad) {
	    String s;
	    
	    if (isCobolLoad) {
	    	s = UtMessages.COBOL_IMPORT_MSG.get();
	    } else {
	    	s = UtMessages.CODE_GEN_MAIN_MSG.get();
	    }
	    tips = new JEditorPane("text/html", s);
	    tips.setEditable(false);
	    
	    tips.setCaretPosition(1);
	    tips.setPreferredSize(new Dimension(tips.getPreferredSize().width, SwingUtils.STANDARD_FONT_HEIGHT * 18));
//	    tipsPane = new JScrollPane(tips);
//	    tipsPane.setPreferredSize(new Dimension(
//	    		SwingUtils.STANDARD_FONT_WIDTH * 120,
//	    		SwingUtils.STANDARD_FONT_HEIGHT * 12));

	}
	private void init_200_LayoutScreen(boolean addLoadBtn, JButton actionBtn) {
		
		cblDtlPnl
//		       .addComponentRE(1, 4, SwingUtils.STANDARD_FONT_HEIGHT * 18,
//						BasePanel.GAP2,  BasePanel.FULL, BasePanel.FULL, tips)
			  .addLineRE(        "Cobol Copybook", cblDtls.copybookFileCombo)
			  .addLineRE("Sample Cobol Data File", cblDtls.sampleFileCombo)
			  	                                             .setGapRE(BasePanel.GAP1)
			  	                                             .setFieldsToActualSize();
		cblDtlPnl
			  .addLineRE(  "Copybook Line format", cblDtls.getCopybookFormatCombo())
			  .addLineRE(        "Split Copybook", cblDtls.getSplitOptionsCombo())
			  .addLineRE(             "Font Name", cblDtls.fontNameCombo)
			  .addLineRE(         "Cobol Dialect", cblDtls.getDialectCombo())

			  .addLineRE(        "File Structure", cblDtls.getFileStructureCombo());
		
		BaseHelpPanel.setToCommonWidth(0, 0, cblDtls.getCopybookFormatCombo(), cblDtls.getSplitOptionsCombo(), cblDtls.fontNameCombo,
				cblDtls.getDialectCombo(), cblDtls.getFileStructureCombo());
		JPanel genPnl = new JPanel(new BorderLayout());
		if (addLoadBtn) {
			genPnl.add(BorderLayout.EAST, cblDtls.genJRecordBtn);
			JPanel goPnl = new JPanel(new BorderLayout());
			goPnl.add(BorderLayout.EAST, cblDtls.goBtn);
			
			cblDtlPnl
			  .addLineRE(                "System", cblDtls.system)
                   .setGapRE(BasePanel.GAP2)                                               
		      .addLineRE(                     "", genPnl)    .setGapRE(BasePanel.GAP0)
	          .addLineRE(                     "", goPnl);
		} else if (actionBtn != null) {
			genPnl.add(BorderLayout.EAST, actionBtn);
			cblDtlPnl 
				 .setGapRE(BasePanel.GAP1)
              .addLineRE(                     "", cblDtls.dropCopybookNameChk)
                 .setGapRE(BasePanel.GAP1)					 .setFieldsToFullSize();
            cblDtlPnl
		      .addLineRE(                     "", genPnl)    .setGapRE(BasePanel.GAP0);
		}
		
//		cblDtlPnl.done();
	}

}
