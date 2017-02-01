package net.sf.RecordEditor.utils.swing.filechooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.IEnvironmentValues;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Panel to Display File Chooser With a Recent file selection
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class JRFileChooserPnlX extends JPanel {

	private static boolean showHiddenDefault = false;
	private boolean showHidden = showHiddenDefault;
	//private static boolean LastShowRecent = true;
	
	private boolean showRecent;
	
	private JFileChooser filechooser = null;
	private final JPanel extraPnl = new JPanel();
	private final DefaultMutableTreeNode    top = new DefaultMutableTreeNode("root");
	private final DefaultMutableTreeNode     pc = new DefaultMutableTreeNode("This Computer");
	private final DefaultMutableTreeNode recent = new DefaultMutableTreeNode("Recent");
	private final DefaultTreeModel treeModel = new DefaultTreeModel(top);

	private DefaultMutableTreeNode last;
		
	private JTree actionTree = null;// = new JTree(top);
	private JScrollPane treePane; //= new JScrollPane(actionTree);
	
	private JPopupMenu popup = new JPopupMenu();
	private JPopupMenu fileChooserPopup = null;
	private JCheckBox    showHiddenChk  = new JCheckBox("Show Hidden Files");
	private JCheckBox    showHiddenChk1 = new JCheckBox("Show Hidden Files");
	private JCheckBox    showRecentChk  = new JCheckBox("Show Recent Pane");
	private JCheckBox    showRecentChk1 = new JCheckBox("Show Recent Pane");

	private final String initalFile;
	
	private Boolean resizeWindow = null;
	

	public JRFileChooserPnlX(String initalFile, File[] recentDirs, boolean showRecent) {
		this.initalFile = initalFile;
		this.showRecent = showRecent;
		
		init_100_Setup(recentDirs);
		//init_200_LayoutScreen();
		//init_300_Finalise();
	}

	private void init_100_Setup(File[] recentDirs) {
		
		new AddNodes(recentDirs);
		
		showHiddenChk .setSelected(showHidden);
		showHiddenChk1.setSelected(showHidden);
		showRecentChk .setSelected(showRecent);
		showRecentChk1.setSelected(showRecent);
		
		popup.add(showHiddenChk);
		popup.add(showRecentChk);
	}

	public JRFileChooserPnlX doTheLayout() {
		if (filechooser == null) {
			init_200_LayoutScreen();
			init_300_Finalise();
		}

		return this;
	}
	
	private void init_200_LayoutScreen() {
		actionTree = new JTree(treeModel);
		filechooser = new JFileChooser();
		
		if (initalFile != null && initalFile.length() > 0) {
			filechooser.setSelectedFile(new File(initalFile));
		}
		
		treePane = new JScrollPane(actionTree);
		actionTree.setRootVisible(false);
		
		//extraPnl.add(new JScrollPane(actionTree));
		extraPnl.add(treePane);
		
//		this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(actionTree), filechooser));
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, extraPnl);
		this.add(BorderLayout.CENTER, filechooser);
		
		extraPnl.setVisible(showRecent);
	}
	
	private void init_300_Finalise() {
		actionTree.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent me) {
		    	  TreePath tp = actionTree.getPathForLocation(me.getX(), me.getY());
		    	  Object o;
		    	  if (tp != null && (o =  tp.getLastPathComponent()) instanceof FileNode) {
		    		File file = new File(((FileNode) o).path);
		    		if (file.isDirectory()) {
		    			file = new File(file.getPath() + Common.FILE_SEPERATOR + "*");
		    		}
					filechooser.setSelectedFile(file);
		    	  }
		      }
		});
		
		actionTree.setCellRenderer(new FileRender());
		ToolTipManager.sharedInstance().registerComponent(actionTree);
		
//		System.out.println("Popup Menu: " + (filechooser.getComponentPopupMenu() != null));
		checkComponent(filechooser);
		
		
//		filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
		filechooser.setDialogType(JFileChooser.OPEN_DIALOG);
		setScrollpaneSize();
		
		filechooser.setFileHidingEnabled(! showHidden);
		
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("mouse released: " + e.isPopupTrigger() + " " + e.getX());
				if (e.isPopupTrigger()) {
					int x = e.getX(),
			        	y = e.getY();
					
					popup.show(e.getComponent(),
		                       x, y);
				}
			}
			
		};
		ActionListener updateHidden = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				showHidden = ! showHidden;
				showHiddenDefault = showHidden;
				showHiddenChk.setSelected(showHidden);
				showHiddenChk1.setSelected(showHidden);
				//System.out.println("Check box: " + showHiddenChk.isSelected() + " " + showHidden);
				
				filechooser.setFileHidingEnabled(! showHidden);
				Object source = e.getSource();
				
				if (source == showHiddenChk) {
					popup.setVisible(false);
				} else if (fileChooserPopup != null ){
					fileChooserPopup.setVisible(false);
				}
			}
		};

		this.addMouseListener(ma);
		extraPnl.addMouseListener(ma);
		filechooser.addMouseListener(ma);
		actionTree.addMouseListener(ma);
		
		
		showHiddenChk.addActionListener(updateHidden);
		showHiddenChk1.addActionListener(updateHidden);
		
		expandTree(last);
		
//		filechooser.addActionListener(new ActionListener() {		
//			@Override public void actionPerformed(ActionEvent e) {
//				System.out.println("Action: " + e.getActionCommand() + " " + e.paramString() + " ~ " + e.getID() + " " + e.getModifiers());
//			}
//		});
		
		ActionListener updateRecent = new ActionListener() {
			Container parentWindow = null;
			@Override public void actionPerformed(ActionEvent e) {
//				extraPnl.setCollapsed(showRecent);
				showRecent = ! showRecent;
				showRecentChk.setSelected(showRecent);
				showRecentChk1.setSelected(showRecent);
				
				//showHiddenChk1.setSelected(showHidden);
				//System.out.println("Check box: " + showHiddenChk.isSelected() + " " + showHidden);
				
				Object source = e.getSource();

				if (source == showRecentChk) {
					popup.setVisible(false);
				} else if (fileChooserPopup != null ){
					fileChooserPopup.setVisible(false);
				}
				int widthAdjust;
				if (showRecent) {
					extraPnl.setVisible(showRecent);
					//extraPnl.doLayout();
					widthAdjust = extraPnl.getPreferredSize().width;
				} else {
					widthAdjust = -extraPnl.getPreferredSize().width;
					extraPnl.setVisible(showRecent);
				}
				extraPnl.invalidate();
				
				Dimension d = JRFileChooserPnlX.this.getPreferredSize();
				JRFileChooserPnlX.this.setPreferredSize(new Dimension(d.width + widthAdjust, d.height));
				JRFileChooserPnlX.this.invalidate();
//				FileChooserPnlX.this.revalidate();
				//extraPnl.repaint();
				//FileChooserPnlX.this.repaint();
				
				if (parentWindow == null) {
					Container c = JRFileChooserPnlX.this; 
					
					while (c.getParent() != null) {
						System.out.print("======> " + c.getClass().getName() + " " + c.getComponentCount());
						if (c instanceof JRootPane) {
							c = c.getParent();

							System.out.println(" >> " + c.getComponentCount() );
							if (resizeWindow == null) {
								resizeWindow = c.getComponentCount() == 1;
							}
							
							break;
						}
						System.out.println();
						c = c.getParent();
					}
					
					parentWindow = c;
				}
				
				if (resizeWindow != null && resizeWindow) {
					d = parentWindow.getSize();
					parentWindow.setSize(new Dimension(d.width + widthAdjust, d.height));
				}
				parentWindow.invalidate();
				parentWindow.repaint();
			}
		};
		
		showRecentChk.addActionListener(updateRecent);
		showRecentChk1.addActionListener(updateRecent);
	}
	
	private void checkComponent(Container c) {
		Component[] components = c.getComponents();
		JPopupMenu p;
		
		
		if (c instanceof JComponent && (p = ((JComponent)c).getComponentPopupMenu()) != null) {
			System.out.print(p.getComponentCount());
			p.add(showHiddenChk1);
			p.add(showRecentChk1);
			fileChooserPopup = p;
			return;
		}
		for (Component sc : components) {
			if (sc instanceof Container) {
				checkComponent((Container) sc);
				if (fileChooserPopup != null ) {
					return;
				}
			}
		}
	}
	
	public void setRecentList(File[] recentDirs) {
		if (recentDirs != null 
		&& (recentDirs.length != recent.getChildCount() || (! recentDirs[0].getPath().equals(((FileNode)recent.getChildAt(0)).path)))) {
			
			recent.removeAllChildren();
			loadRecent(recentDirs, 0);
			expandTree(last);
			
		}
	}
	

	protected final JFileChooser getFilechooser() {
		doTheLayout();
		return filechooser;
	}

	private final void setScrollpaneSize() {
			Dimension d = extraPnl.getPreferredSize();
			
			if (d != null && d.width > 0) {
//				System.out.println("Width: " + extraPnl.getPreferredSize().width + " " + SwingUtils.STANDARD_FONT_WIDTH
//						+ " " + (extraPnl.getPreferredSize().width / SwingUtils.STANDARD_FONT_WIDTH)
//						);
				int width = Math.min( 
								Math.max(d.width, 20 * SwingUtils.STANDARD_FONT_WIDTH),
								35 * SwingUtils.STANDARD_FONT_WIDTH
							);
				treePane.setPreferredSize(new Dimension(width, d.height));
//				System.out.println("Width: " + extraPnl.getPreferredSize().width + " " + SwingUtils.STANDARD_FONT_WIDTH
//						+ " " + (extraPnl.getPreferredSize().width / SwingUtils.STANDARD_FONT_WIDTH)
//						);
			}
	}
	
	
	private void expandTree(DefaultMutableTreeNode last) {
		if (last != null && actionTree != null) {
			actionTree.fireTreeExpanded(new TreePath(last.getPath()));
	
			if (treePane.isVisible()) {
				treePane.invalidate();
				treePane.repaint();
			}
		}
	}
	
	private void loadRecent(File[] recentDirs, int  start) {
		if (recentDirs != null) {
			for (int i = start; i < recentDirs.length; i++) {
				File r = recentDirs[i]; 
				last = add(recent, r.getName(), r, last);
			}
			
			treeModel.nodeStructureChanged(recent);
		}
	}

	private DefaultMutableTreeNode add(DefaultMutableTreeNode node, String  name, File f, DefaultMutableTreeNode last) {
		
		if (f != null && f.exists()) {
			last = new FileNode(name, f.getPath());
			node.add(last);
//			treeModel.insertNodeInto(last, node, node.getChildCount());
//		} else if (f != null) {
//			System.out.println(" --> Does not exist: " + f.getPath());
		}
		
		return last;
	}

	
	private class AddNodes implements Runnable {
		//DefaultMutableTreeNode pc = new DefaultMutableTreeNode("pc");
		//DefaultMutableTreeNode recent = new DefaultMutableTreeNode("Recent");
		
		FileSystemView fsv = FileSystemView.getFileSystemView();
		IEnvironmentValues e = Parameters.getEnvironmentDefaults();
		//DefaultMutableTreeNode last;
		
		private AddNodes(File[] recentDirs) {
	
			last = add(pc, "home", new File(Parameters.getUserhome()), null);
			last = add(pc, "Document", e.getDocumentDirectory(), last);
			last = add(pc, "Desktop",  e.getDesktopDirectory(), last);
			
			loadPaths(e.getStandardMountPoints(), last);
			
			loadRecent(recentDirs, 0);
//			if (recentDirs != null) {
//				for (File r : recentDirs) {
//					last = add(recent, r.getName(), r, last);
//				}
//			}
			top.add(pc);
			if (recent.getChildCount() > 0) {
				top.add(recent);
				javax.swing.SwingUtilities.invokeLater(this);
			} else {
				run();
			}
		}
		
		private void loadPaths(File[] paths, DefaultMutableTreeNode last) {
			String s;
			
			// for each pathname in pathname array
			if (paths != null) {
				for(File path:paths) {
					s = fsv.getSystemDisplayName(path);
					if (s != null && s.length() > 0) {
						last = add(pc, s, path, last);
					}
				}
			}
//			expandTree(last);
			if (last != null && actionTree != null) {
				actionTree.fireTreeCollapsed(new TreePath(last.getPath()));
			}
		}
		
		
		@Override
		public void run() {

			loadPaths(e.getOtherMountPoints(), null);
			
			if (pc.getChildCount() > 0 && pc.getChildCount() < 4) {
				expandTree((DefaultMutableTreeNode)pc.getChildAt(pc.getChildCount() - 1));
			}
			e = null;
			fsv = null;		
		}
	}
	
	
	/**
	 * Tree node holding a directory ...
	 * 
	 * @author Bruce Martin
	 *
	 */
	public static class FileNode extends DefaultMutableTreeNode {
		public final String path;
		public FileNode(Object name, String path) {
			super(name);
			
			this.path = path;
		}
	}
	
	/**
	 * A Tree Cell render where<ul> 
	 *  <li>Icon is Directory open
	 *  <li>Tool tip displays the actual directory
	 * </ul>
	 *  
	 * @author Bruce Martin
	 *
	 */
	private static class FileRender extends DefaultTreeCellRenderer {
		public FileRender() {
			super.setLeafIcon(Common.getRecordIcon(Common.ID_OPEN_ICON));
		}
    	@Override public Component getTreeCellRendererComponent(JTree tree, Object value,
    			boolean sel, boolean expanded, boolean leaf, int row,
    			boolean hasFocus) {
    		
    		Object o =  null;
    		TreePath tp = null;
    		if (leaf && tree != null 
    		&& (tp = tree.getPathForRow(row)) != null
    		&& (o =  tp.getLastPathComponent()) instanceof FileNode) {
    			setToolTipText(((FileNode)o).path);
     		}

    		return super.getTreeCellRendererComponent(tree, value, sel,
    				expanded, leaf, row, hasFocus);
    	}
    }
//	public static void main(String[] a) {
//
//		
//		File[] r = {
//				new File("F:\\Work\\RecordEditor\\Build\\Instalation\\GeneralDb\\GenericDB"), 
//				new File("F:\\Work\\RecordEditor\\Build\\Instalation\\hsqldb_izpack\\lib"),
//				new File("F:\\Shared")
//		};
////		
//		JRFileChooserPnlX p = new JRFileChooserPnlX("C:\\Users", r, true).doTheLayout();
//		JFrame f = new JFrame();
//		
//		f.getContentPane().add(p);
//		f.pack();
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		//p.collapsePcNode();
//		f.setVisible(true);
//	}
}
