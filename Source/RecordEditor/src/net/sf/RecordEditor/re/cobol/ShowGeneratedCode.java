package net.sf.RecordEditor.re.cobol;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.BasicCsvLineParser;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.cg.velocity.GeneratedSkelDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class ShowGeneratedCode {

	private JTree tree;
	private final RSyntaxTextArea textArea = new RSyntaxTextArea();
    private final RTextScrollPane textScrollPane = new RTextScrollPane(textArea, true);
    
    private JTextField fileNameTxt = new JTextField();
    private JTextField descriptionTxt = new JTextField();
    private JButton saveBtn = SwingUtils.newButton("Save", Common.getRecordIcon(Common.ID_SAVE_ICON));
   
    private GeneratedSkelDetails currentSkel;
 
    public ShowGeneratedCode(List<GeneratedSkelDetails> skels, String schemaName) {
    	init_100_setup(skels);
    	init_200_layoutScreen(schemaName);
    	init_300_listners();
    }
    
    private void init_100_setup(List<GeneratedSkelDetails> skels) {
    	HashMap<String, SkelNode> nodes = new HashMap<String, ShowGeneratedCode.SkelNode>(40);
    	BasicCsvLineParser p = new BasicCsvLineParser(false);
    	CsvDefinition csvDef = new CsvDefinition("/", "'");
    	SkelNode root = new SkelNode(null, "");
    	
    	for (GeneratedSkelDetails skel :  skels) {
    		String[] names = p.split(Conversion.replace(skel.javaFilename, "//", "/").toString(), csvDef, 0);
    		init_110_addNode(root, nodes, skel, names, names.length - 1);
    	}
    	
    	tree = new JTree(root);
    	tree.setRootVisible(false);
//    	tree.expandRow(1);
//    	tree.expandRow(tree.getRowCount()-1);
    	
    	tree.expandRow(0);  	
//    	tree.expandRow(1);
    	for (int i = 0; i < tree.getRowCount(); i++) {
    		tree.expandRow(i);
    	}
    	
	    textArea.setTabSize(3);
	    textArea.setCaretPosition(0);
//	    textArea.addHyperlinkListener(this);
	    textArea.requestFocusInWindow();
	    textArea.setMarkOccurrences(true);
	    textArea.setCodeFoldingEnabled(true);
	    textArea.setClearWhitespaceLinesEnabled(false);

	    Gutter gutter = textScrollPane.getGutter();
	    gutter.setBookmarkingEnabled(true);
	    
	   	setSkel(skels.get(0));

	   	this.fileNameTxt.setEditable(false);
	   	this.descriptionTxt.setEditable(false);
    }
    
    private SkelNode init_110_addNode(SkelNode root, HashMap<String, SkelNode> nodes, GeneratedSkelDetails skel, String[] names, int idx) {
    	SkelNode n = new SkelNode(skel, names[idx]);
    	SkelNode parent = root;
    	if (idx > 0) {
    		parent = nodes.get(names[idx - 1]);
    		if (parent == null) {
    			parent = init_110_addNode(root, nodes, null, names, idx - 1);
    			nodes.put(names[idx-1], parent);
    		}
    	}
    	parent.add(n);
    	return n;
    }
    
    private void init_200_layoutScreen(String name) {
    	BaseHelpPanel codePnl = new BaseHelpPanel("GenCode");
    	JPanel savePnl = new JPanel(new BorderLayout());
    	savePnl.add(BorderLayout.EAST, saveBtn);
    	
    	codePnl
    		.addLineRE("File Name", this.fileNameTxt)
    		.addLineRE("Description", this.descriptionTxt)
    			.setGapRE(BaseHelpPanel.GAP0)
    		.addLineRE("", savePnl)
				.setGapRE(BaseHelpPanel.GAP1)
			.addComponentRE(
		         	1, 3, BasePanel.FILL, BasePanel.GAP0,
			        BasePanel.FULL, BasePanel.FULL,
			        textScrollPane);
   	
    	JSplitPane p = new JSplitPane(
    			JSplitPane.HORIZONTAL_SPLIT, 
    			new JScrollPane(tree), 
    			codePnl);
    	
    	ReFrame f = new ReFrame(name, "Generated Code", null);
    	
    	f.getContentPane().add(p);
     	f.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
    	Dimension ps = f.getPreferredSize();
    	int width = Math.max(ps.width, 140 * SwingUtils.CHAR_FIELD_WIDTH);
    	
    	f.setPreferredSize(new Dimension(
    			Math.min(width, ReFrame.getDesktopWidth()),
    			Math.min(ps.height, ReFrame.getDesktopHeight())
    			));
//    	ps = f.getPreferredSize();
//     	ps = textScrollPane.getPreferredSize();
//    	
//    	textScrollPane.setPreferredSize(new Dimension(
//    			Math.min(ps.width, ReFrame.getDesktopWidth()),
//    			Math.min(ps.height, ReFrame.getDesktopHeight())
//    			));
       	
       	f.pack();
       	f.setVisible(true);
       	f.setToMaximum(false);
   }
    
    private void init_300_listners() {
    	tree.addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (path != null) {
					Object[] p = path.getPath();
					
					if (p != null && p.length > 0) {
						Object o = p[p.length - 1];
						if (o instanceof SkelNode) {
							SkelNode skel = (SkelNode) o;
							if (currentSkel != null) {
								currentSkel.setGeneratedCode(textArea.getText());
							}
							
							if (skel.skel != null) {
								setSkel(skel.skel);
							}
						}
					}
				}
			}
		});
    	
    	saveBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				writeCode();
			}
		});
    }
    
    private void setSkel(GeneratedSkelDetails skel) {
    	String s = skel.getGeneratedCode();
    	if (s == null) {
	    	FileReader fr;
	    	StringBuilder b = new StringBuilder(9000);
	    	char[] cb = new char[4096];
	    	int len;
	    	try {
				fr = new FileReader(skel.filename);
				
				while ((len = fr.read(cb)) > 0) {
					b.append(cb, 0, len);
				}
				fr.close();
				
				s = b.toString();
				skel.setGeneratedCode(s);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
    	}
    	
    	currentSkel = skel;
    	textArea.setText(s);
	    textArea.setCaretPosition(0);
	    fileNameTxt.setText(skel.filename);
	    descriptionTxt.setText(skel.description);
  	
    	int idx = skel.filename.lastIndexOf('.');
    	if (idx > 0) {
    		String mimeType = skel.filename.substring(idx + 1);
    		if ("jjs".equals(mimeType)) {
    			mimeType="javascript";
    			//style = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT
    		} else if ("rb".equals(mimeType)) {
    			mimeType="ruby";
    		} else if ("py".equals(mimeType)) {
    			mimeType="python";
    		} else if ("sh".equals(mimeType)) {
    			mimeType="unix";
    		} else if ("html".equals(mimeType)) {
    			SwingUtils.showInBrowser(new File(skel.filename).toURI());
    		}
			String style = "text/" + mimeType;
    		textArea.setSyntaxEditingStyle(style);
    	}
    }
    
    private void writeCode() {
    	FileWriter w;
    	
    	try {
			w = new FileWriter(currentSkel.filename);
			w.write(textArea.getText());
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
			Common.logMsg("Error Writing file: " + e, e);
		}
    }
    
    @SuppressWarnings("serial")
	private static class SkelNode extends DefaultMutableTreeNode {
    	private final GeneratedSkelDetails skel;

		protected SkelNode(GeneratedSkelDetails skel, String name) {
			super(name);
			this.skel = skel;
		}
    }
}


