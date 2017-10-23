package net.sf.RecordEditor.diff.xml;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.diff.DoCompare;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.editProperties.CommonCode;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class XmlCompareDir {
	
	private static String[] TBL_COLUMN_NAMES = {
			"File Name",
			"Left Date",
			"Right Date"
	};
	private ReFrame frame = new ReFrame("", "Directory Compare", null);
	//private HashMap<String, AFile> 
	
	private List<AFile> fileList;
	private int compareCount = 0;
	
	private DirectoryCompareMdl fileListTblMdl;
	private JTable fileListTbl;
	private JEditorPane tipsPane;
	

	public XmlCompareDir(String dir1, String dir2, String filter) {
		
		init_100_LoadData(dir1, dir2, filter);
		
		init_200_LayoutScreen();
		init_300_finalise();
	}

	private void init_100_LoadData(String dir1, String dir2, String filter) {
		
		try {
			init_110_createFileList(dir1, dir2, filter);
			
			init_120_prepareForDisplay();
			
		} catch (IOException e) {
			e.printStackTrace();
			Common.logMsg("Error creating Xml File list: " + e, null);
		}
		
		fileListTblMdl = new DirectoryCompareMdl(TBL_COLUMN_NAMES, fileList);
		fileListTbl = new JTable(fileListTblMdl);
	}

	private void init_110_createFileList(String dir1, String dir2, String filter) throws IOException {
		FileWalkerMatcher walker = new FileWalkerMatcher(filter);
		HashMap<String, AFile> map = new HashMap<String, AFile>();
		AFile af;
		Path dir1Path = Paths.get(dir1);
		Path dir2Path = Paths.get(dir2);
		
		tipsPane = new JEditorPane(
						"text/html", 
						UtMessages.DIRECTORY_COMPARE_MSG.get(
								dir1Path.getParent().toString(), 
								dir2Path.getParent().toString()));;

		
		Files.walkFileTree(dir1Path, walker);
		
		for (Path p : walker.list) {
			af = new AFile(p, null);
			map.put(af.keyName, af);
		}
		
		walker.list.clear();
		Files.walkFileTree(dir2Path, walker);
		
		for (Path p : walker.list) {
			AFile afNew = new AFile(null, p);
			af = map.get(afNew.keyName);
			if (af == null) {
				map.put(afNew.keyName, afNew);
			} else {
				af.setRightFile(p);
			}
		}
		
		fileList = new ArrayList<AFile>(map.values());
	}
	

	private void init_120_prepareForDisplay() {
		Collections.sort(fileList, new Comparator<AFile>() {
			@Override public int compare(AFile o1, AFile o2) {
				return o1.keyName.compareToIgnoreCase(o2.keyName);
			}	
		});
		for (int i = 0; i < 3; i++) {
			new Thread(new CompareFiles()).start();
		}
	}


	private void init_200_LayoutScreen() {
		BaseHelpPanel pnl = new BaseHelpPanel("XmlDiffFileList");
		
		pnl.addComponentRE(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
		        tipsPane)

		   .addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP, BasePanel.FULL, BasePanel.FULL, fileListTbl);
		
		fileListTbl.setRowHeight(fileListTbl.getRowHeight() * 3 / 2);
		fileListTbl.setShowGrid(false);
		
	
		frame.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
		frame.addMainComponent(pnl);
	}
	
	private void init_300_finalise() {
		int width = SwingUtils.CHAR_FIELD_WIDTH * 90;
		int screenWidth = ReFrame.getDesktopWidth();
		if (screenWidth > 0 && screenWidth < width) {
			width = screenWidth;
		}
		int x = frame.getX();
		frame.setBounds(x, frame.getY(), width, ReFrame.getDesktopHeight() - x - 1);
		frame.setVisible(true);

		ColorRendor cellRenderer = new ColorRendor(fileList);
		TableColumnModel columnModel = fileListTbl.getColumnModel();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setCellRenderer(cellRenderer);
		}
		
		fileListTbl.addMouseListener(new MouseAdapter() {
						
			@Override public void mousePressed(MouseEvent e) {
                int row = fileListTbl.rowAtPoint(e.getPoint());
                if (row >= 0 && row < fileList.size()) {
					AFile af = fileList.get(row);
					
	                try {
						DoCompare.getInstance().compare1Layout(
								StandardLayouts.getInstance().getXmlLayout(), 
								af.leftFile.toString(), af.getRightFile().toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
                }
			}
	
		});
	}
	
	/**
	 * Get a file to check if there are differences
	 * @return
	 */
	private AFile getFileToCheck() {
		if (fileListTblMdl != null && ((compareCount > 8 && compareCount % 4 == 0) || compareCount >= fileList.size())) {
			fileListTblMdl.fireTableDataChanged();
		}
		if (compareCount >= fileList.size()) {
			return null;
		} else {
			synchronized (fileList) {
				return fileList.get(compareCount++);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static  class DirectoryCompareMdl extends AbstractTableModel {
		final String[] columnNames;
		final List<AFile> fileList;
		
		public DirectoryCompareMdl(String[] columnNames, List<AFile> fileList) {
			super();
			this.columnNames = columnNames;
			this.fileList = fileList;
		}

		@Override
		public int getRowCount() {
			return fileList.size();
		}

		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return fileList.get(rowIndex).getFieldNoDir(columnIndex);
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}
		
	}
	
	
	/**
	 * This class compares files in the left / right directory
	 * @author Bruce Martin
	 *
	 */
	private class CompareFiles implements Runnable {

		@Override
		public void run() {
			DoCompare doCompare = DoCompare.getInstance();
			StandardLayouts layoutBuilder = StandardLayouts.getInstance();
			AFile af;
			while ((af = getFileToCheck())!= null) {
				if (af.leftFile != null && af.getRightFile() != null) {
					try {
						if (af.getCompareResult() != AFile.YET_TO_COMPARE) {
						} else if (doCompare.isDifferent1Layout(
								layoutBuilder.getXmlLayout(), 
								af.leftFile.toString(), af.getRightFile().toString())) {
							af.setCompareResult(AFile.FILES_DIFFERENT);
						} else {
							af.setCompareResult(AFile.FILES_THE_SAME);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private static class FileWalkerMatcher extends SimpleFileVisitor<Path>{
		final ArrayList<Path> list = new ArrayList<Path>();
		final PathMatcher matcher;
		FileWalkerMatcher(String filter) {
			if (filter != null && filter.length() > 0 && (! "*".equals(filter))) {
				matcher = FileSystems.getDefault().getPathMatcher("glob:" + filter);
			} else {
				matcher = null;
			}
		}
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			
			if (matcher == null || matcher.matches(file.getFileName())) {
				list.add(file);
			}
			
			return super.visitFile(file, attrs);
		}
	}
	
	private static class ColorRendor implements TableCellRenderer {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		final List<AFile> fileList;
		
		public ColorRendor(List<AFile> fileList) {
			super();
			this.fileList = fileList;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (fileList.get(row).getCompareResult() == AFile.FILES_DIFFERENT) {
				//System.out.print("\ty " + row);
	    		r.setBackground(Color.ORANGE);
			} else {
				//System.out.print("\tw " + row);
	    		r.setBackground(Color.WHITE);
			}
			
			return r;
		}
		
	}
}
