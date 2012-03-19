/**
 * 
 */
package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.tree.TreeNode;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.RunVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * @author mum
 *
 */
public class SaveAsPnlVelocity extends SaveAsPnlBase {

	
	/**
	 * @param extension
	 * @param panelFormat
	 * @param extensionType
	 * @param template
	 */
	public SaveAsPnlVelocity(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".html", CommonSaveAsFields.FMT_VELOCITY, RecentFiles.RF_VELOCITY, new FileChooser(true, "get Template"));
		
		addHtmlFields();
		
        
        template.setText(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());
        panel.addLine("Velocity Template", template, template.getChooseFileButton());
    }
	
	public void save(String selection, String outFile) throws Exception {
	       RunVelocity velocity = RunVelocity.getInstance();

	        FileWriter w = new FileWriter(outFile);
	        AbstractLineNode root = null;
	        List<List<TreeNode>> nodeList = new ArrayList<List<TreeNode>>();
	        int treeDepth=0;
	        @SuppressWarnings("rawtypes")
			List<List<AbstractLine>> lines = new ArrayList<List<AbstractLine>>();
	        
	        lines.add(saveFile_getLines(selection));
	        lines.add(commonSaveAsFields.file.getBaseFile().getLines());
	        lines.add(commonSaveAsFields.file.getLines());
	        
	        if (commonSaveAsFields.getTreeFrame() != null) {
	        	root = commonSaveAsFields.getTreeFrame().getRoot();
	        	treeDepth = save_100_checkLevels(nodeList, root, 0);
	        }
	        
	        velocity.genSkel(template.getText(), 
	        		lines, 
	        		root,
	        		nodeList,
	        		treeDepth,
	        		onlyData.isSelected(), showBorder.isSelected(),
	        		commonSaveAsFields.getRecordFrame().getLayoutIndex(),
	        		commonSaveAsFields.file.getFileName(), outFile, w);
	        w.close();
	}
	
	
	
	private int save_100_checkLevels(List<List<TreeNode>> nodeList, AbstractLineNode node, int lvl) {
		int ret = lvl + 1;
		List<TreeNode> nodes = Arrays.asList(node.getPath());
		if (nodes==null) {
			nodes=new ArrayList<TreeNode>(1);
			nodes.add(node);
		}
		
		nodeList.add((nodes));
		for (int i = 0; i < node.getChildCount(); i++) {
			ret = Math.max(ret, save_100_checkLevels(nodeList, (AbstractLineNode) node.getChildAt(i), lvl));
		}
		
		return ret;
	}
}
