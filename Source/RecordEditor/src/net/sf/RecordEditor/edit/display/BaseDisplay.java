/*
 * Created on 14/05/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting, removed unused vars
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Printing, Full-Line Display and Selected views added
 * # Version 0.62 Bruce Martin 26/04/2007
 *   - Split from BaseLineFrame
 */
package net.sf.RecordEditor.edit.display;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.BasicChildDefinition;
import net.sf.RecordEditor.diff.DoCompare;
import net.sf.RecordEditor.diff.LineBufferedReader;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAs3;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAs4;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.common.ILayoutChanged;
import net.sf.RecordEditor.edit.display.util.AddAttributes;
import net.sf.RecordEditor.edit.display.util.GotoLine;
import net.sf.RecordEditor.edit.display.util.OptionPnl;
import net.sf.RecordEditor.edit.display.util.Search;
import net.sf.RecordEditor.edit.display.util.SortFrame;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.AbstractExecute;
import net.sf.RecordEditor.re.file.filter.ExecuteSavedFile;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.re.jrecord.types.RecordFormats;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.script.IDisplayBuilder;
import net.sf.RecordEditor.re.script.IDisplayFrame;
import net.sf.RecordEditor.re.tree.ChildTreeToXml;
import net.sf.RecordEditor.re.tree.TreeParserRecord;
import net.sf.RecordEditor.re.tree.TreeParserXml;
import net.sf.RecordEditor.re.tree.TreeToXml;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.StandardRendor;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This class displays file details on the screen.  This class has 2 subclasses
 * <ul compact>
 * <li>RecordFrame - displays on record
 * <li>RecordList - displays all records in a table format
 * </ul>
 *
 * <p>This class holds common code / loads buttons etc.
 *
 * @see LineFrame - displays on record
 * @see LineList - displays all records in a table format
 *
 * <p>
 * @author Bruce Martin
 * @version 0.56
 */
@SuppressWarnings("serial")
public abstract class BaseDisplay
implements AbstractFileDisplay, ILayoutChanged, ReActionHandler {

//	private static ArrayList<Class> classList = new ArrayList<Class>();
//	private static ArrayList<TableCellRenderer> renderList = new ArrayList<TableCellRenderer>();
//	private static ArrayList<TableCellEditor> editorList = new ArrayList<TableCellEditor>();

	private static final String PRINTING_REQUIRES_JAVA_5 = "Printing failed (Printing requires Java 1.5)";
	protected static final int NORMAL_DISPLAY = 3;
	protected static final int TREE_DISPLAY = 5;
	//static ImageIcon searchIcon = new ImageIcon(Common.dir + "searchEye.gif");

	private static final int RECORDS_TO_CHECK = 30;
	public static final int MAXIMUM_NO_COLUMNS = 5000;

	protected static final int NO_OPTION_PANEL = 1;
	protected static final int STD_OPTION_PANEL = 2;
	protected static final int TREE_OPTION_PANEL = 3;
	protected static final int NO_LAYOUT_LINE = 4;

	protected static final IDisplayBuilder DisplayBldr = DisplayBuilderFactory.getInstance();

	public static final int NO_CHILD_FRAME = 1;
	public static final int CHILD_FRAME_RIGHT = 2;
	public static final int CHILD_FRAME_BOTTOM = 2;
	//private int displayOpt = STD_OPTION_PANEL;

	public final String formType;
	public final boolean primary;

	private Search searchScreen;

	protected Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();
	    //Toolkit.getDefaultToolkit().getScreenSize();

	@SuppressWarnings("rawtypes")
	protected AbstractLayoutDetails layout;
	@SuppressWarnings("rawtypes")
	protected FileView fileView;
	@SuppressWarnings("rawtypes")
	protected FileView fileMaster;

	protected JTable tblDetails;
	private JTable alternativeTbl = null;
	private LayoutCombo layoutCombo;

//	private int layoutIndex =-3;
//	protected final int fullLineIndex;

	//protected ListnerPanel pnl = new ListnerPanel();
	protected BaseHelpPanel actualPnl = new BaseHelpPanel(this.getClass().getSimpleName());

	protected TableCellRenderer[] cellRenders;
	//protected TableCellEditor[]   cellEditors;

	private   int maxHeight = -1;
	protected int[] widths;
	private IDisplayChangeListner changeListner = null;

	private RecordFormats[] displayDetails = null;
	protected TableCellEditor[] cellEditors;

	private HeaderToolTips toolTips;
    //private HeaderRender headerRender = new HeaderRender();

	private int displayType = NORMAL_DISPLAY;

	private boolean copyHiddenFields = false;

	private DisplayFrame parentFrame;

	private MouseListener dockingPopup = null;

	private KeyAdapter listner = new KeyAdapter() {
    	private long lastWhen = -121;
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	/*System.out.print(" } " + event.getKeyCode() + " " + event.getID()
        			+ " modifiers " + event.getModifiers() + " " + event.getModifiersEx()
        			+ " when " + event.getWhen() + " " + lastWhen);*/
        	//Common.logMsg("Key Released ", null);
            if (event.getModifiers() == KeyEvent.CTRL_MASK && lastWhen != event.getWhen()) {
                /*if (event.getKeyCode() == KeyEvent.VK_F) {
                    searchScreen.startSearch(fileView);
                } else*/ if (event.getKeyCode() == KeyEvent.VK_A) {
                	tblDetails.selectAll();
//                } else if (event.getKeyCode() == KeyEvent.VK_S) {
//                    saveFile();
                } else if (event.getKeyCode() == KeyEvent.VK_INSERT) {
                	insertLine(0);
                } else if (event.getKeyCode() == KeyEvent.VK_C) {
                    copyRecords();
               } else if (event.getKeyCode() == KeyEvent.VK_V) {
                    fileView.pasteLines(getInsertAfterPosition());
                } else if (event.getKeyCode() == KeyEvent.VK_L) {
                	new GotoLine(BaseDisplay.this, fileView);;
                } else if (event.getKeyCode() == KeyEvent.VK_K) {
                	System.out.println("Table Class:" + tblDetails.getClass().getName());
               }
               lastWhen = event.getWhen();
                //System.out.print("   !!!");
            }
        }
    };

    private TableModelListener layoutChangeListner = new TableModelListener() {

		/**
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent arg0) {
			layoutChangedInternal(fileMaster.getLayout());
		}
    };

    private ActionListener layoutListner = new ActionListener() {
//		private int layoutIndex =-3;
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public final void actionPerformed(final ActionEvent e) {

			if (e.getSource() == layoutCombo) {
		    	setupForChangeOfLayout();
			}
		}
	};
	/**
	 * base class for editing a file
	 *
	 * @param formType panel name
	 * @param group record layout group used to display a record
	 * @param viewOfFile current file table representation
	 * @param masterfile internal representation of a File
	 * @param primary wether the screen is a primary screen
	 */
	protected BaseDisplay(final String formType,
					   final FileView viewOfFile,
	        		   final boolean primary,
	        		   final boolean addFullLine,
	        		   final boolean fullList,
	        		   final boolean prefered,
	        		   final boolean hex,
	        		   final int option ) {
		//super(viewOfFile.getBaseFile().getFileNameNoDirectory(), formType, viewOfFile.getBaseFile());
		//this.setPrimaryView(primary);

		fileView   = viewOfFile;
		this.formType = formType;
		this.primary = primary;
		//displayOpt = option;

		init(addFullLine, fullList, prefered, hex);
		//fullLineIndex = layoutCombo.getFullLineIndex();

		switch (option) {
		case NO_LAYOUT_LINE:
			break;
		case NO_OPTION_PANEL:
			actualPnl.addLine("Layouts", getLayoutCombo());
			break;
		default:
			int opt = fileView.isBrowse() ? OptionPnl.BROWSE_PANEL
					 : OptionPnl.EDIT_PANEL;

			actualPnl.addComponent3Lines("Layouts", getLayoutCombo(), new OptionPnl(opt, this));
		}
	}


	/**
	 * Common Initialise
	 */
	private void init(
			final boolean addFullLine, final boolean fullList,
			final boolean prefered,  final boolean hex) {

		fileMaster = fileView.getBaseFile();
		layout     = fileMaster.getLayout();

		actualPnl.addReKeyListener(listner);

		setupDisplayDetails(layout);

		layoutCombo = new LayoutCombo(layout, addFullLine, fullList, prefered, hex);

		setTableFormatDetails(0);

		searchScreen = new Search(this, this.fileMaster);

		layoutCombo.addActionListener(layoutListner);

		//pnl.setHeight(Math.max(25, iconHeight + 8));


//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//
////		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//	    this.addInternalFrameListener(new InternalFrameAdapter() {
////	        public void internalFrameClosed(final InternalFrameEvent e) {
////	            closeWindow();
////	        }
//            public void internalFrameClosing(InternalFrameEvent e)  {
//                 windowClosingCheck();
//            }
//	    });

	    fileView.addTableModelListener(layoutChangeListner);

	}


	protected final void initToolTips(int colsToSkip) {

        toolTips = new HeaderToolTips(tblDetails.getColumnModel(), colsToSkip);
        tblDetails.setTableHeader(toolTips);
	}


	public void doClose() {
		AbstractFileDisplay child = getChildScreen();
		if (child != null) {
			child.doClose();
		}
		if (fileView != null) {
			fileView.removeTableModelListener(layoutChangeListner);
		}
		closeWindow();
		layoutCombo.removeActionListener(layoutListner);
	}
	/**
	 * Close a window
	 *
	 */
	public void closeWindow() {

		try {
			if (searchScreen != null) {
				ReMainFrame.getMasterFrame().deleteWindow(searchScreen);
				searchScreen.setVisible(false);
				searchScreen.setClosed(true);
				searchScreen = null;
			}
			fileView.removeTableModelListener(layoutChangeListner);
			stopCellEditing();
			fileMaster = null;
		} catch (Exception ex) {
		}
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.common.ILayoutChanged#layoutChanged(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public final void layoutChanged(AbstractLayoutDetails newLayout) {
	}

	private void layoutChangedInternal(AbstractLayoutDetails newLayout) {
//		System.out.println("Change Layout 2 !!! ");

		if (newLayout != layout) {
			layout = newLayout;
//			layoutCombo.removeActionListener(layoutListner);
			layoutCombo.setRecordLayout(newLayout);
//			layoutCombo.addActionListener(layoutListner);

			setupDisplayDetails(newLayout);

			setTableFormatDetails(getLayoutIndex());
			setNewLayout(newLayout);
//			searchScreen.setRecordLayout(newLayout);
		}
	}


	/**
	 * Execute standard RecordEditor action with an option
	 * @param action action to perform
	 * @param o option supplied
	 */
	public void executeAction(int action, Object o) {

		if (o != null) {
			switch (action) {
			case ReActionHandler.EXPORT_VELOCITY:
				executeSaveAs(SaveAs3.FORMAT_VELOCITY, o.toString());
				break;
			case ReActionHandler.EXPORT_XSLT:
				executeSaveAs(SaveAs3.FORMAT_XSLT, o.toString());
				break;
			case ReActionHandler.EXPORT_SCRIPT:
				executeSaveAs(SaveAs3.FORMAT_SCRIPT, o.toString());
				break;
				default:
					executeAction(action);
			}
		} else{
			executeAction(action);
		}
	}


	/**
	 *  Execute standard RecordEditor actions
	 *
	 * @param action action to perform
	 */
	public void executeAction(int action) {

		stopCellEditing();

		try {
			switch (action) {
			case ReActionHandler.FIND:						searchScreen.startSearch(fileView);		break;
			case ReActionHandler.FILTER:					new FilterFrame(parentFrame, fileView);	break;
			case ReActionHandler.COMPARE_WITH_DISK:			compareWithDisk();						break;
			case ReActionHandler.EXECUTE_SAVED_FILTER:		executeSavedFilter();					break;
			case ReActionHandler.EXECUTE_SAVED_SORT_TREE:	executeSavedSortTree();					break;
			case ReActionHandler.EXECUTE_SAVED_RECORD_TREE: executeSavedRecordTree();				break;
			case ReActionHandler.SHOW_INVALID_ACTIONS: 		createErrorView();	  					break;
			case ReActionHandler.TABLE_VIEW_SELECTED: 		createView();							break;
			case ReActionHandler.RECORD_VIEW_SELECTED:		createRecordView();						break;
			case ReActionHandler.COLUMN_VIEW_SELECTED:		createColumnView();	                    break;
			case ReActionHandler.SELECTED_VIEW:
				int[] selRows = getSelectedRows();

				if (selRows != null && selRows.length > 0) {
					BaseDisplay bd = getNewDisplay(fileView.getView(selRows));
					DisplayBuilder.addToScreen(parentFrame, bd);
					copyVisibility(bd);
					bd.layoutCombo.setSelectedIndex(layoutCombo.getSelectedIndex());
				}
				break;
			case ReActionHandler.BUILD_SORTED_TREE:			new CreateSortedTree(this, fileView);	break;
			case ReActionHandler.BUILD_FIELD_TREE:			new CreateFieldTree(this, fileView);	break;
			case ReActionHandler.BUILD_RECORD_TREE:		  	new CreateRecordTree(this, fileView);	break;
			case ReActionHandler.BUILD_LAYOUT_TREE: {
	        	TreeParserRecord parser = new TreeParserRecord(executeAction_100_getParent());

	        	DisplayBuilderFactory.newLineTree(parentFrame, this.fileView, parser, false, 0);
			} break;
			case ReActionHandler.BUILD_LAYOUT_TREE_SELECTED:
			   if (getSelectedRowCount() > 0) {
	        	  TreeParserRecord parser = new TreeParserRecord(executeAction_100_getParent());

	        	  DisplayBuilderFactory.newLineTree(parentFrame, this.fileView.getView(getSelectedRows()), parser, false, 0);
			   }
			break;
			case ReActionHandler.BUILD_XML_TREE_SELECTED:	createXmlTreeView();               break;
			case ReActionHandler.SAVE_AS:				    new SaveAs4(this, this.fileView);  break;
			case ReActionHandler.EXPORT:				    new SaveAs3(this, this.fileView);  break;
			case ReActionHandler.EXPORT_AS_CSV:
				executeSaveAs(SaveAs3.FORMAT_DELIMITED, "");
			break;
			case ReActionHandler.EXPORT_AS_FIXED:
				executeSaveAs(SaveAs3.FORMAT_FIXED, "");
			break;
			case ReActionHandler.EXPORT_AS_HTML:
				executeSaveAs(SaveAs3.FORMAT_1_TABLE, "");
			break;
			case ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW:
			    executeSaveAs(SaveAs3.FORMAT_MULTI_TABLE, "");
			break;
			case ReActionHandler.EXPORT_HTML_TREE:
			    executeSaveAs(SaveAs3.FORMAT_TREE_HTML, "");
			break;
			case ReActionHandler.EXPORT_VELOCITY:
			    executeSaveAs(SaveAs3.FORMAT_VELOCITY, "");
			break;
			case ReActionHandler.EXPORT_XSLT:
			    executeSaveAs(SaveAs3.FORMAT_XSLT, "");
			break;
			case ReActionHandler.EXPORT_SCRIPT:
			    executeSaveAs(SaveAs3.FORMAT_SCRIPT, "");
			break;
			case ReActionHandler.SAVE_AS_XML:
				if (layout.hasTreeStructure() || layout.hasChildren()) {
					JFileChooser chooseFile = new JFileChooser();
					chooseFile.setDialogType(JFileChooser.SAVE_DIALOG);
					chooseFile.setSelectedFile(new File(fileView.getFileName() + ".xml"));

					int ret = chooseFile.showOpenDialog(null);

					if (ret == JFileChooser.APPROVE_OPTION) {
						if (layout.hasChildren()) {
							new ChildTreeToXml(chooseFile.getSelectedFile().getPath(),  fileView.getLines());
						} else {
				        	TreeParserRecord parser = new TreeParserRecord(executeAction_100_getParent());

							new TreeToXml(chooseFile.getSelectedFile().getPath(),
									parser.parse(fileView)
							);
						}
					}
				}
			break;
			case ReActionHandler.SAVE:		    	saveFile();				break;
			case ReActionHandler.DELETE_RECORD:		deleteLines();			break;
			case ReActionHandler.COPY_RECORD:
				copyRecords();			break;
			case ReActionHandler.CUT_RECORD:
				copyRecords();
				deleteLines();
			break;
			case ReActionHandler.PASTE_RECORD:
				fileView.pasteLines(getInsertAfterPosition());		break;
			case ReActionHandler.PASTE_RECORD_PRIOR:	fileView.pasteLines(getInsertBeforePosition());	break;
			case ReActionHandler.CORRECT_RECORD_LENGTH:	setRecordLayout();								break;
			case ReActionHandler.INSERT_RECORDS:		insertLine(0);									break;
			case ReActionHandler.INSERT_RECORD_PRIOR:	insertLine(-1);									break;
			case ReActionHandler.CLOSE:					closeWindow();									break;
			case ReActionHandler.SORT:			    	new SortFrame(this, fileView);					break;
			case ReActionHandler.HELP:		    		actualPnl.showHelp();									break;
			case ReActionHandler.ADD_ATTRIBUTES:	new AddAttributes(fileView, layoutCombo.getLayoutIndex());  break;
			case ReActionHandler.PRINT:
			    try {
			        tblDetails.print(JTable.PrintMode.NORMAL);
			    } catch (Exception e) {
	                Common.logMsg(PRINTING_REQUIRES_JAVA_5, e);
	            }
			break;
			case ReActionHandler.PRINT_SELECTED:
				int[] selRowsP = getSelectedRows();

				if (selRowsP != null && selRowsP.length > 0) {
					try {
						BaseDisplay bdisp = getNewDisplay(fileView.getView(selRowsP));
						DisplayFrame bd = new DisplayFrame(bdisp);
						//JTable t = bd.tblDetails;
						bdisp.layoutCombo.setSelectedIndex(layoutCombo.getSelectedIndex());
						copyVisibility(bdisp);
						bd.setVisible(true);
						bd.setVisible(false);
						bd.executeAction(ReActionHandler.PRINT);
						bd.dispose();
				    } catch (Exception e) {
		                Common.logMsg(PRINTING_REQUIRES_JAVA_5, e);
		            }
				}

				break;
			case ReActionHandler.AUTOFIT_COLUMNS:
				Common.calcColumnWidths(getJTable(), 1);
			}
		} catch (Exception e) {
			Common.logMsg("Error Executing action:", null);
			Common.logMsg(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private final void executeSaveAs(int format, String s) {
		new SaveAs3(this, this.fileView, format, s);
	}
	protected final void executeTreeAction(int action) {

		switch (action) {
		case ReActionHandler.PASTE_RECORD_PRIOR:
		case(ReActionHandler.PASTE_RECORD):
			getFileView().pasteTreeLines(getInsertAfterLine(action == ReActionHandler.PASTE_RECORD_PRIOR));
		break;

//			AbstractLine line = getInsertAfterLine();
//			if (line.getTreeDetails().getParentLine() == null) {
//				getFileView().pasteLines(fileView.indexOf(line) - 1);
//			} else {
//				getFileView().pasteTreeLines(line.getTreeDetails().getParentLine());
//			}
		default:
			executeAction(action);
		}

	}

	@Override
	public void insertLine(int adj) {
		if (fileMaster.getTreeTableNotify() == null) {
			insertLine_100_FlatFile(adj);
		} else {
			insertLine_200_TreeFile(adj);
		}
	}

	private void insertLine_100_FlatFile(int adj) {
		int pos = fileView.newLine(getInsertAfterPosition(), adj);


		if (layout.isXml()) {
			//System.out.println("Setting Layout index " + layoutList.getLayoutIndex());
			fileView.getLine(pos).setWriteLayout(layoutCombo.getLayoutIndex());
		}
		if (layout.getRecordCount() == 1) {
			AbstractRecordDetail rec = layout.getRecord(0);
			AbstractLine l = fileView.getLine(pos);
			for (int i = 0; i < rec.getFieldCount(); i++) {
				if (rec.getField(i).getDefaultValue() != null) {
					try {
						l.setField(0, i, rec.getField(i).getDefaultValue());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

		checkLayout();
		newLineFrame(fileView, pos);
	}


	private void insertLine_200_TreeFile(int adj) {
		AbstractLine l = getInsertAfterLine(adj < 0);
		AbstractLine newLine = null;

		if (l == null) {
			if (fileView.getRowCount() == 0) {
				newLine = newMainLine(0);
				checkLayout();
				if (newLine != null) {
					newLineFrame(fileView, 0);
                   	AbstractLineNode root = (AbstractLineNode) fileView.getTreeTableNotify().getRoot();
                   	fileView.getTreeTableNotify().fireTreeStructureChanged(root, root.getPath(), null, null);
				}
			}
		} else {
			List<AbstractChildDetails> children = l.getTreeDetails().getInsertRecordOptions();
			AbstractChildDetails ldef = l.getTreeDetails().getChildDefinitionInParent();
			AbstractLine parent = l.getTreeDetails().getParentLine();
			AbstractChildDetails<AbstractRecordDetail<? extends FieldDetail>> rootDef = null;
			if (children == null || children.size() == 0) {
				if (ldef == null || ! ldef.isRepeated()) {
					Common.logMsg("Nothing can be inserted at this point", null);
					return;
				}

				if (parent == null ) {
					newLine = insertLine_210_CreateMainRecord(l);
				} else {
					newLine = insertLine_220_CreateRepeated(parent, l, ldef);
				}
			} else {
				if (ldef != null && ldef.isRepeated()) {
					children.add(ldef);
				} else if (parent == null) {
					int idx = l.getPreferredLayoutIdx();
					rootDef = new BasicChildDefinition<AbstractRecordDetail<? extends FieldDetail>>(
												l.getLayout().getRecord(idx), idx ,0);
					children.add(rootDef);
				}

				if (children.size() == 1) {
					newLine = insertLine_230_CreateChild(l, children.get(0), -1);
				} else {
					AbstractChildDetails resp = (AbstractChildDetails) ReOptionDialog.showInputDialog(
							parentFrame, "Select the Record to Insert", "Record Selection", JOptionPane.QUESTION_MESSAGE, null,
						children.toArray(), children.get(0));

					if (resp != null) {
						if (resp == rootDef) {
							newLine = insertLine_210_CreateMainRecord(l);
						} else if (resp == ldef) {
							newLine = insertLine_220_CreateRepeated(parent, l, ldef);
						} else {
							newLine = insertLine_230_CreateChild(l, resp, -1);
						}
					}
				}
			}
			if (newLine != null) {
				//DisplayBuilder.newLineFrameTree(parentFrame, fileView, newLine);
				DisplayBldr.newDisplay(
						IDisplayBuilder.ST_RECORD_TREE, "", parentFrame, fileView.getLayout(), fileView, newLine);
			}
		}
	}

	private AbstractLine insertLine_210_CreateMainRecord (AbstractLine line) {
		return newMainLine(fileView.indexOf(line));
	}

	private AbstractLine insertLine_220_CreateRepeated(
			AbstractLine parent, AbstractLine child, AbstractChildDetails childDef) {

		int location = parent.getTreeDetails().getLines(childDef.getChildIndex()).indexOf(child);

		if (location >= 0) {
			location += 1;
		}

		return insertLine_230_CreateChild(parent, childDef, location);
	}

	private AbstractLine insertLine_230_CreateChild(
		AbstractLine parent, AbstractChildDetails childDef, int location) {
		AbstractLine ret =  parent.getTreeDetails().addChild(childDef, location);

		AbstractLineNode pn = fileView.getTreeNode(parent);
		if (ret != null && pn != null /*&& o instanceof AbstractLineNode */) {
			//System.out.println(" >>> >>> Insert Node: " + ret.getFullLine());
			pn.insert(ret, -1, location);
		}

		return ret;
	}

	private void checkLayout() {
		if ((!fileView.isView()) && fileView.getRowCount() == 1) {
			int idx = fileView.getLine(0).getPreferredLayoutIdx();
			if (idx >= 0) {
				setLayoutIndex(idx);
				setupForChangeOfLayout();
			}
		}
	}

	protected AbstractLine newMainLine(int pos) {
		int location =  fileView.newLine(pos, 0) ;
		AbstractLine ret = fileView.getLine(location);
		AbstractLineNode pn = (AbstractLineNode) fileView.getTreeTableNotify().getRoot();
		if (ret != null && pn != null /*&& o instanceof AbstractLineNode */) {
			pn.insert(ret, -1, location);
			//fileView.getTreeTableNotify().fireTreeNodesInserted(pn);
			//fileView.getTreeTableNotify().fireTreeNodesInserted(pn);
		}

		return ret;

//		int location =  fileView.newLine(0) ;
//		AbstractLine newLine = fileView.getLine(location);
//		if (newLine != null) {
//			newLineFrame(fileView, 0);
//		}
	}

	private int[] executeAction_100_getParent() {

    	int[] parentIdxs = new int[layout.getRecordCount()];

    	for (int i = 0; i < parentIdxs.length; i++) {
    		parentIdxs[i] = layout.getRecord(i).getParentRecordIndex();
    	}

    	return parentIdxs;
	}

	private void compareWithDisk() {
		try {
			(new DoCompare()).compare1Layout(
				new LineBufferedReader(fileView.getFileName(), layout, null, null, true),
				new LineBufferedReader(fileView.getFileName(), layout, fileView.getBaseFile().getLines(), true)
			);
		} catch (Exception e) {
			Common.logMsg("Error Compare", e);
			e.printStackTrace();
		}
	}

	private void executeSavedFilter() {
		AbstractExecute<EditorTask> action = new AbstractExecute<EditorTask>() {
			public void execute(EditorTask details) {
				FilterDetails filter = new FilterDetails(layout, true);

				filter.updateFromExternalLayout(details.filter);
		    	FileView view = fileView.getFilteredView(filter);
		    	if (view == null) {
		    		Common.logMsg("No records matched the filter", null);
		    	} else {
		    		DisplayBuilderFactory.newLineList(parentFrame, layout, view, fileView.getBaseFile());
		    	}
			}

			public void executeDialog(EditorTask details) {
				(new FilterFrame(parentFrame, fileView))
						.updateFromExternalLayout(details.filter);
			}
		};

		new ExecuteSavedFile<EditorTask>(
				fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", fileView,
				Parameters.getFileName(Parameters.FILTER_SAVE_DIRECTORY),
				action, EditorTask.class
		);
	}


	private void executeSavedSortTree() {
		AbstractExecute<EditorTask> action = new AbstractExecute<EditorTask>() {
			public void execute(EditorTask details) {
				CreateSortedTree treePnl = new CreateSortedTree(BaseDisplay.this, fileView);
				treePnl.setFromSavedDetails(details);
				treePnl.doAction();
			}

			public void executeDialog(EditorTask details) {
				(new CreateSortedTree(BaseDisplay.this, fileView)).setFromSavedDetails(details);

			}
		};

		new ExecuteSavedFile<EditorTask>(
				fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Sort Tree", fileView,
				Parameters.getFileName(Parameters.SORT_TREE_SAVE_DIRECTORY),
				action, EditorTask.class
		);
	}


	private void executeSavedRecordTree() {
		AbstractExecute<EditorTask> action = new AbstractExecute<EditorTask>() {
			public void execute(EditorTask details) {
				CreateRecordTree treePnl = new CreateRecordTree(BaseDisplay.this, fileView);
				treePnl.treeDisplay.setFromSavedDetails(details);
				treePnl.doAction();
			}

			public void executeDialog(EditorTask details) {
				(new CreateRecordTree(BaseDisplay.this, fileView)).treeDisplay.setFromSavedDetails(details);

			}
		};

		new ExecuteSavedFile<EditorTask>(
				fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Sort Tree", fileView,
				Parameters.getFileName(Parameters.RECORD_TREE_SAVE_DIRECTORY),
				action, EditorTask.class
		);
	}

	/**
	 * Check if action is available
	 *
	 * @param action action to be checked
	 *
	 * @return wether action is available
	 */
	public boolean isActionAvailable(final int action) {

	    boolean ret =
	       (action == ReActionHandler.FIND)
    	|| (action == ReActionHandler.FILTER)
    	|| (action == ReActionHandler.TABLE_VIEW_SELECTED)
    	|| (action == ReActionHandler.RECORD_VIEW_SELECTED)
    	|| (action == ReActionHandler.COLUMN_VIEW_SELECTED)
    	|| (action == ReActionHandler.SELECTED_VIEW)
    	|| (action == ReActionHandler.SAVE_AS)
    	|| (action == ReActionHandler.EXPORT)
     	|| (action == ReActionHandler.EXPORT_AS_CSV)
     	|| (action == ReActionHandler.EXPORT_AS_FIXED)
     	|| (action == ReActionHandler.EXPORT_AS_HTML)
    	|| (action == ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW)
    	|| (action == ReActionHandler.EXPORT_HTML_TREE && layout.hasChildren())
    	|| (action == ReActionHandler.EXPORT_VELOCITY)
    	|| (action == ReActionHandler.EXPORT_XSLT)
    	|| (action == ReActionHandler.EXPORT_SCRIPT)
		|| (action == ReActionHandler.COPY_RECORD)
		|| (action == ReActionHandler.CLOSE)
        || (action == ReActionHandler.HELP)
        || (action == ReActionHandler.PRINT)
        || (action == ReActionHandler.PRINT_SELECTED)
        || ( (! layout.hasChildren())
	        && (  	    action == ReActionHandler.BUILD_SORTED_TREE
	        		||  action == ReActionHandler.BUILD_FIELD_TREE
	        		||  action == ReActionHandler.BUILD_RECORD_TREE
	        		|| (action == ReActionHandler.EXECUTE_SAVED_SORT_TREE)
			        || (action == ReActionHandler.EXECUTE_SAVED_RECORD_TREE)
			    )
			&& fileView.isTreeViewAvailable()
	        )
        || (action == ReActionHandler.BUILD_LAYOUT_TREE && layout.hasTreeStructure())
        || (action == ReActionHandler.BUILD_LAYOUT_TREE_SELECTED && layout.hasTreeStructure())
        || (action == ReActionHandler.SAVE_AS_XML && (layout.hasTreeStructure() || layout.hasChildren()))
        || (action == ReActionHandler.BUILD_XML_TREE_SELECTED && layout.isXml())
        || (action == ReActionHandler.ADD_ATTRIBUTES && layout.isOkToAddAttributes()
        || (action == ReActionHandler.EXECUTE_SAVED_FILTER)
        || (action == ReActionHandler.COMPARE_WITH_DISK)
        );


	    if (! this.fileView.isBrowse()) {
		    ret =   ret
			    || (action == ReActionHandler.SAVE)
			    || (action == ReActionHandler.DELETE_RECORD)
				|| (action == ReActionHandler.COPY_RECORD)
				|| (action == ReActionHandler.CUT_RECORD)
				|| (action == ReActionHandler.PASTE_RECORD)
				|| (action == ReActionHandler.PASTE_RECORD_PRIOR)
				|| (action == ReActionHandler.CORRECT_RECORD_LENGTH)
				|| (action == ReActionHandler.INSERT_RECORDS)
				|| (action == ReActionHandler.INSERT_RECORD_PRIOR)
				|| (action == ReActionHandler.SORT)
				|| (action == ReActionHandler.SHOW_INVALID_ACTIONS);
	    }

		return ret;
	}

	/**
	 * Get the insert / paste position
	 * @return insert / paste position
	 */
	protected abstract int getInsertAfterPosition();

	protected int getInsertBeforePosition() {
		return getStandardPosition() - 1;
	}

	/**
	 * Get Insertion Point for Tree Display
	 * @return Insertion Point (line)
	 */
	protected AbstractLine getInsertAfterLine(boolean prev) {
		int idx = getInsertAfterPosition();
		if (prev) {
			idx -= 1;
		}
		if (idx < 0) {
			return null;
		}
		return fileView.getLine(idx);
	}

	/**
	 * Standard table position calculation
	 * @return position
	 */
	protected int getStandardPosition() {

		int pos = getCurrRow();
		if (pos < 0) {
			pos = fileView.getRowCount() - 1;
		}

		return pos;
	}


	/**
	 * @return the tblDetails
	 */
	@Override
	public final JTable getJTable() {
		return tblDetails;
	}


	/**
	 * @param tableDetails the tblDetails to set
	 */
	public final void setJTable(JTable tableDetails) {
		this.tblDetails = tableDetails;

		actualPnl.setComponentName(tableDetails, "FileDisplay");
		tblDetails.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		if (Common.OPTIONS.highlightEmptyActive.isSelected()) {
			tblDetails.setDefaultRenderer(Object.class, new StandardRendor());
		}
	}


	/**
	 * Create a view from the selected records
	 */
	private void createView() {
		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			DisplayBuilderFactory.newLineList(this.parentFrame, fileView.getLayout(), fileView.getView(selRows),
		            fileView.getBaseFile());
		}
	}

	/**
	 * Create a view from the selected records
	 */
	private void createColumnView() {

		if (getSelectedRowCount() > MAXIMUM_NO_COLUMNS) {
			Common.logMsg("To Many Rows Selected for ColumnDisplay", null);
		} else {
			int[] selRows = getSelectedRows();

			if (selRows != null && selRows.length > 0) {
				DisplayBldr.newDisplay(
						IDisplayBuilder.ST_LINES_AS_COLUMNS, "", parentFrame, fileView.getLayout(), fileView.getView(selRows), 0);
			    //DisplayBuilder.newLinesAsColumns(parentFrame, fileView.getView(selRows));
			}
		}
	}

	/**
	 * Create a view from the selected records
	 */
	private void createRecordView() {
		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			newLineFrame(fileView.getView(selRows), 0);
		}
	}


	/**
	 * Create a view from the selected records
	 */
	private void createErrorView() {
		FileView errorView = fileView.getViewOfErrorRecords();

		if (errorView == null) {
				Common.logMsg("No Error Records, nothing to display", null);
		} else {
			newLineDisp("Error Records", errorView, 0);
		}
	}

	/**
	 * Create a view from the selected records
	 */
	private void createXmlTreeView() {
		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			DisplayBuilderFactory.newLineTree(parentFrame, fileView.getView(selRows), TreeParserXml.getInstance(), false, 1);
		}
	}


	/**
	 * Save the file back to disk
	 */
	public final boolean saveFile() {
		boolean ret = true;

		//System.out.println("Save File Called");
		if ("".equals(fileMaster.getFileName())) {
			new SaveAs3(this, fileMaster);
		} else {
		    try {
		        fileMaster.writeFile();

				FileView errorView = fileView.getViewOfErrorRecords();

				if (errorView != null) {
					saveFileError(LangConversion.convert("File saved, but there where records in error that may not make it on to the file"), null);
					newLineDisp("Error Records", errorView, 0);
					ret = false;
				}

		    } catch (Exception ex) {
		    	saveFileError(LangConversion.convert("Save Failed:") + " " + ex.getMessage(), ex);
		        ex.printStackTrace();
		        ret = false;
	        }
			}
	    return ret;
	}

	private void saveFileError(String s, Exception ex) {

        JOptionPane.showInternalMessageDialog(parentFrame, s);
        Common.logMsgRaw(s, ex);
	}


	private void check4Delete() {
		int[] selected = getSelectedRows();
		if (selected != null
		&& selected.length > 0
		/*&& super.tblDetails.getCellEditor(). == null*/) {

			int res = JOptionPane.YES_OPTION;
			if (Common.OPTIONS.warnWhenUsingDelKey.isSelected()) {
				res = JOptionPane.showConfirmDialog(
					actualPnl,
					ReMessages.LINE_DELETE_MSG.get(Integer.toString(selected.length)),
//					LangConversion.convert(
//							"Do you want to delete the selected {0} lines ?",
//							Integer.toString(selected.length)),

					ReMessages.LINE_DELETE_CHECK.get(),
					//LangConversion.convert(LangConversion.ST_MESSAGE, "Line Delete confirmation"),
					JOptionPane.YES_NO_OPTION);
			}
			if (res == JOptionPane.YES_OPTION) {
				stopCellEditing();
				deleteLines();
			}
		}
	}


	/**
	 * Deleting one or more records
	 */
	public void deleteLines() {
		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			fileView.deleteLines(selRows);
		}
	}


	/**
	 * Copies one ore more records
	 */
	 public final void copyRecords() {

		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			fileView.copyLines(selRows);
		}
	 }


	/**
	 * Change the record Layout
	 */
	 public abstract void fireLayoutIndexChanged();

	 /**
	  * New Layout allocated to the frame
	  */
	 @Override
	 public void setNewLayout(AbstractLayoutDetails newLayout) {
		 setupDisplayDetails(newLayout);
	 }


	 private void setupDisplayDetails(AbstractLayoutDetails newLayout) {
		displayDetails = new RecordFormats[newLayout.getRecordCount()];
		for (int i = 0; i < displayDetails.length; i++) {
		    displayDetails[i] = null;
		}
	 }
	 /**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getCurrRow()
	 */
	 public abstract int getCurrRow();


	 /**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getTreeLine()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLine getTreeLine() {
		return null;
	}


	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#setCurrRow(net.sf.RecordEditor.re.file.FilePosition)
	 */
	@Override
	public void setCurrRow(FilePosition position) {
		int idx = setLayoutForPosition(position);

		parentFrame.setToActiveTab(this);
		setCurrRow((position.row), idx, position.currentFieldNumber);
	}

	protected final int setLayoutForPosition(FilePosition position) {
		int idx = position.recordId;
		if (position.layoutIdxUsed >= 0 && position.layoutIdxUsed < layout.getRecordCount()) {
			int currIdx;

			idx = position.layoutIdxUsed;
			if (position.layoutIdxUsed != (currIdx = getLayoutIndex())
			&&  currIdx < layout.getRecordCount() ) {
				setLayoutIndex(position.layoutIdxUsed);
				setupForChangeOfLayout();
			}
		}
		return idx;
	}

	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#setCurrRow(int, int, int)
	 */
	 @Override
	public abstract void setCurrRow(int newRow, int layoutId, int fieldNum);



	/**
	 * decide which record to use
	 */
	private void setRecordLayout() {
		int i;
		int[] selRows = getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			int layoutIdx = getLayoutIndex();

			for (i = 0; i < selRows.length; i++) {
				fileView.getLine(i).setWriteLayout(layoutIdx);
			}
		}
	}

    /**
     * set the record layout based on the most common record
     * in the first 30 lines
     */
    protected final void setLayoutIdx() {

        if (layout.getRecordCount() > 0) {
            int i, l;
            int record2Use = 0;
            int currMax = 0;
            int recordsToCheck = Math.min(RECORDS_TO_CHECK,
            							  fileView.getRowCount());
            int[] layoutCounts = new int[layout.getRecordCount()];

 //           System.out.println("setLayoutIdx: " + fileView.getRowCount() + " " + tblDetails.getRowCount());

            for (i = 0; i < layoutCounts.length; i++) {
                layoutCounts[i] = 0;
            }

            for (i = 0; i < recordsToCheck; i++) {
                l = getLayoutIdx_100_getLayout4Row(i);
                if (l > 0) {
                    layoutCounts[l] += 1;
                }
            }

            for (i = 0; i < layoutCounts.length; i++) {
                if (layoutCounts[i] > currMax) {
                    currMax = layoutCounts[i];
                    record2Use = i;
                }
            }

            setLayoutIndex(record2Use);
            setupForChangeOfLayout();
        }
    }


    /**
     * get the prefered layout for a row. note: this is overriden in LineTree
     * @param row table row to get the layout for
     * @return rows prefered layout
     */
    protected int getLayoutIdx_100_getLayout4Row(int row) {
    	return fileView.getTempLine(row).getPreferredLayoutIdx();
    }

    /**
     * get the selected Row count
     * @return selected Row count
     */
    public int getSelectedRowCount() {
    	return tblDetails.getSelectedRowCount();
    }

	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getSelectedRows()
	 */
	public int[] getSelectedRows() {
		return tblDetails.getSelectedRows();
	}


	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getSelectedLines()
	 */
	@Override
	public List<AbstractLine> getSelectedLines() {

		return fileView.getLines(tblDetails.getSelectedRows());
	}


	/**
	 * Stop editing a cell
	 */
	public void stopCellEditing() {

	    Common.stopCellEditing(tblDetails);
	}


	/**
	 * This method gets table format details for a record
	 *
	 * @param recordIndex index of record to retrieve
	 */
	protected final void setTableFormatDetails(int recordIndex) {

	    if (recordIndex >= displayDetails.length) {
	        cellRenders = null;
	        cellEditors = null;
	    } else {
	        RecordFormats recordDesc = getDisplayDetails(recordIndex);
	        cellRenders = recordDesc.getCellRenders();
	        cellEditors = recordDesc.getCellEditors();
	        widths      = recordDesc.getWidths();

	        maxHeight   = recordDesc.getMaxHeight();
	        if (tblDetails != null && (tblDetails.getRowHeight() > maxHeight)) {
	        	maxHeight = Math.max(maxHeight, (int) tblDetails.getRowHeight());
	        }
	    }
	}


	/**
	 * Get display details for a record
	 *
	 * @param idx record index
	 *
	 * @return requested display details
	 */
	protected final RecordFormats getDisplayDetails(int idx) {

        if (displayDetails[idx] == null ) {
            layout = fileView.getLayout();
            displayDetails[idx] = new RecordFormats(this.fileView, layout, idx);
        }
        return displayDetails[idx];
	}


	/**
	 * check if the format has changed
	 * @param event
	 * @return
	 */
	protected final boolean hasTheFormatChanged(TableModelEvent event) {
		boolean changed = false ;

		if (event.getType() == TableModelEvent.UPDATE
		&& event.getFirstRow() < 0 && event.getLastRow() < 0) {
			for (int i = 0; i < displayDetails.length && ! changed; i++) {
				if (displayDetails[i] != null) {
					changed = changed || displayDetails[i].hasTheFormatChanged();
				}
			}
		}

		if (changed) {
			//System.out.println("Set Format ");
			setTableFormatDetails(getLayoutIndex());
		}

		return  changed ;
	}

//	private void layoutChanged() {
//		boolean changed = layout != this.fileMaster.getLayout();
//
//		if (changed) {
//			layoutCombo.setRecordLayout(this.fileMaster.getLayout());
//
//			displayDetails = new RecordFormats[layout.getRecordCount()];
//			for (int i = 0; i < displayDetails.length; i++) {
//			    displayDetails[i] = null;
//			}
//
//			setTableFormatDetails(getLayoutIndex());
//			newLayout();
//		}
//	}

	/**
	 * Set the table cell height
	 *
	 */
	protected void setRowHeight() {
		setRowHeight(maxHeight);
	}

	protected void setRowHeight(int height) {
	    if (height > 0) {
	        tblDetails.setRowHeight(height);
	        if (alternativeTbl != null) {
	        	alternativeTbl.setRowHeight(height);
	        }
	    }
	}


	   /**
     * This method defines the Column Headings
     */
    protected final void defineColumns(int numCols, int blankColumns, int columnsToSkip) {
    	defineColumns(tblDetails, numCols, blankColumns, columnsToSkip);
    }

    protected final void defineColumns(JTable tblDtls, int numCols, int blankColumns, int columnsToSkip) {
        int i, idx, count;
        tblDtls.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel tcm = tblDtls.getColumnModel();

        for (i = blankColumns; i < numCols; i++) {
            if (widths != null && (i - blankColumns) < widths.length  && widths[i - blankColumns] > 0) {
                tcm.getColumn(i).setPreferredWidth(widths[i - blankColumns]);
            }
        }

        //System.out.println("Define Columns " );
        idx = getLayoutIndex();
        if (idx < layoutCombo.getFullLineIndex()) {
            toolTips.setTips(layout.getFieldDescriptions(idx, 0));

            try {
	            //System.out.print(" > cell Rendor " + (cellRenders == null));
	            if (cellRenders != null) {
	            	count =  Math.min(this.fileView.getLayoutColumnCount(idx), cellRenders.length);
	                for (i = columnsToSkip; i < count ; i++) {
	                    if (cellRenders[i] != null) {
//	                    	System.out.println("Rendor ~~> 1 " + i
//	                    			+ ", " + (i + blankColumns - columnsToSkip)
//	                    			+ "  idx  " + idx
//	                    			+ ", " + fileView.getRealColumn(idx, i));
	                        tcm.getColumn(i + blankColumns - columnsToSkip).setCellRenderer(cellRenders[i]);
	                    }
	                }
	            }

	            if (cellEditors != null) {
	            	count =  Math.min(this.fileView.getLayoutColumnCount(idx), cellEditors.length);
	                for (i = columnsToSkip; i < count; i++) {
	                    if (cellEditors[i] != null) {
	                        tcm.getColumn(i + blankColumns - columnsToSkip).setCellEditor(cellEditors[i]);
	                    }
	                }
	            }
            } catch (Exception e) {
				e.printStackTrace();
			}
        }

        Common.calcColumnWidths(tblDtls, blankColumns + 1);
        setRowHeight();
    }


    protected final boolean isCurrLayoutIdx(int layoutId) {
    	int idx = layoutCombo.getLayoutIndex();
    	return idx == layoutId
    		|| idx == layoutCombo.getPreferedIndex();
    }

	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getLayoutIndex()
	 */
	public final int getLayoutIndex() {
		return layoutCombo.getLayoutIndex();
	}


	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#setLayoutIndex(int)
	 */
	public void setLayoutIndex(int recordIndex) {
//		System.out.println("Set Layout Index " + recordIndex);
		//Common.logMsg("Setting LayoutIndex", null);

		layoutCombo.removeActionListener(layoutListner);
		layoutCombo.setLayoutIndex(recordIndex);
		if (recordIndex >= 0) {
			setTableFormatDetails(recordIndex);
			setRowHeight();
		}
		layoutCombo.addActionListener(layoutListner);
	}


	/**
	 * @return the layoutList
	 */
	protected final LayoutCombo getLayoutCombo() {
		return layoutCombo;
	}

	/**
	 * Get the number of fields for each record
	 * @return field counts for for each record
	 */
	protected final int[] getFieldCounts() {
       int[] rows = new int[layout.getRecordCount()];

        for (int i = 0; i < layout.getRecordCount(); i++) {
        	rows[i] = fileView.getLayoutColumnCount(i);
        }

        return rows;
	}

//	/**
//	 * Register a rendor/editor for a class
//	 *
//	 * @param classDtls class to register editor / render for
//	 * @param render rendor to register
//	 * @param editor editor to register
//	 */
//	public static void registerTableEditor(Class classDtls, TableCellRenderer render, TableCellEditor editor) {
//
//		classList.add(classDtls);
//		renderList.add(render);
//		editorList.add(editor);
//	}



	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getFileView()
	 */
	@Override
	public FileView getFileView() {
		return fileView;
	}



	/**
	 * @return the alternativeTbl
	 */
	public JTable getAlternativeTbl() {
		return alternativeTbl;
	}


	/**
	 * @param alternativeTbl the alternativeTbl to set
	 */
	protected void setAlternativeTbl(JTable alternativeTbl) {
		this.alternativeTbl = alternativeTbl;
	}



	protected void newLineFrame(final FileView viewOfFile, final int cRow) {
		AbstractFileDisplayWithFieldHide newScreen;
		if (displayType != NORMAL_DISPLAY && viewOfFile.getLayout().hasChildren()) {
//			newScreen = DisplayBuilder.newLineFrameTree(parentFrame, viewOfFile, cRow);
			newScreen = DisplayBldr.newDisplay(
					IDisplayBuilder.ST_RECORD_TREE, "", parentFrame, viewOfFile.getLayout(), viewOfFile, cRow);


		} else {
			newScreen = newLineDisp(viewOfFile, cRow);
		}

		if (copyHiddenFields && this instanceof AbstractFileDisplayWithFieldHide) {
			int idx = getLayoutIndex();
			newScreen.setFieldVisibility(idx,
					((AbstractFileDisplayWithFieldHide) this).getFieldVisibility(idx));
		}
	}

	protected AbstractFileDisplayWithFieldHide newLineDisp(final FileView viewOfFile, final int cRow) {
		return newLineDisp("Record:", viewOfFile, cRow);
		//return DisplayBuilder.newLineDisplay(this.parentFrame, viewOfFile, cRow);
	}


	protected AbstractFileDisplayWithFieldHide newLineDisp(String name, final FileView viewOfFile, final int cRow) {
		return DisplayBldr.newDisplay(IDisplayBuilder.ST_RECORD_SCREEN, name, parentFrame, viewOfFile.getLayout(), viewOfFile, cRow);
		//return DisplayBuilder.newLineDisplay(this.parentFrame, name, viewOfFile, cRow);
	}

	protected static final void addToScreen(IDisplayFrame df, BaseDisplay d) {
		if (Common.OPTIONS.useSeperateScreens.isSelected()) {
			new DisplayFrame(d);
		} else {
			df.addScreen(d);
		}
	}

	protected final int[] getColumnWidths() {
		TableColumnModel tcm = tblDetails.getColumnModel();
		int[] ret = new int[tcm.getColumnCount()];
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			ret[i] = tcm.getColumn(i).getPreferredWidth();
		}

		return ret;
	}


	protected final void setColumnWidths(int[] colWidths) {
		TableColumnModel tcm = tblDetails.getColumnModel();
		int size = Math.min(colWidths.length,  tcm.getColumnCount());

		for (int i = 0; i < size; i++) {
			tcm.getColumn(i).setPreferredWidth(colWidths[i]);
		}

	}

	   /**
	 * @param displayType the displayType to set
	 */
	public final void setDisplayType(int displayType) {
		this.displayType = displayType;
	}


	/**
	 * @param copyHiddenFields the copyHiddenFields to set
	 */
	public final void setCopyHiddenFields(boolean copyHiddenFields) {
		this.copyHiddenFields = copyHiddenFields;
	}

	/**
	 * @return the actualPnl
	 */
	@Override
	public BaseHelpPanel getActualPnl() {
		return actualPnl;
	}


	/**
	 * @return the dockingPopup
	 */
	public MouseListener getDockingPopup() {
		return dockingPopup;
	}


	/**
	 * @param dockingPopup the dockingPopup to set
	 */
	@Override
	public void setDockingPopup(MouseListener dockingPopup) {
		if (this.dockingPopup == null) {
			this.dockingPopup = dockingPopup;
			this.actualPnl.addMouseListener(dockingPopup);
		}
	}


	/**
	 * @return the parentFrame
	 */
	@Override
	public DisplayFrame getParentFrame() {
		return parentFrame;
	}


	public AbstractFileDisplay getChildScreen() {
		return null;
	}


	public int getAvailableChildScreenPostion() {
		return net.sf.RecordEditor.edit.display.AbstractCreateChildScreen.CS_RIGHT;
	}

	public int getCurrentChildScreenPostion() {
		return net.sf.RecordEditor.edit.display.AbstractCreateChildScreen.CS_RIGHT;
	}

	public void removeChildScreen() {
	}

	public int getChildFramType() {
		if (getChildScreen() != null) {
			return CHILD_FRAME_RIGHT;
		}

		return NO_CHILD_FRAME;
	}

	/**
	 * @param parentFrame the parentFrame to set
	 */
	public final void setParentFrame(DisplayFrame parentFrame, boolean mainframe) {
		this.parentFrame = parentFrame;

		if (mainframe) {
			setScreenSize(mainframe);
		}

	}

	protected void setScreenSize(boolean mainframe) {

	}

	/**
	 * @param changeListner the changeListner to set
	 */
	public void setChangeListner(IDisplayChangeListner changeListner) {
		this.changeListner = changeListner;
	}

	protected void notifyChangeListners() {
		if (changeListner != null) {
			changeListner.displayChanged();
		}
	}

	public String getScreenName() {
		return LangConversion.convert(LangConversion.ST_COLUMN_HEADING, formType);
	}

	private void copyVisibility(AbstractFileDisplay bd) {
		if (this instanceof AbstractFileDisplayWithFieldHide) {
			int count = layoutCombo.getItemCount();
			AbstractFileDisplayWithFieldHide from = (AbstractFileDisplayWithFieldHide) this;
			AbstractFileDisplayWithFieldHide to = (AbstractFileDisplayWithFieldHide) bd;

			for (int i = 0; i < count; i++) {
				to.setFieldVisibility(i, from.getFieldVisibility(i));
			}
		}
	}

	//protected abstract JTable getNewTable(FileView view);
	protected abstract BaseDisplay getNewDisplay(FileView view);

	protected final void setupForChangeOfLayout() {

		int idx = layoutCombo.getSelectedIndex();

		if (idx >= 0) {
			setTableFormatDetails(idx);
			setRowHeight();
			fireLayoutIndexChanged();
		}
	}



    /**
    *
    * @author Bruce Martin
    *
    * This class supplies Column Tips (displayed
    * when the cursor is held over a column).
    */
   private static class HeaderToolTips extends JTableHeader {
       private String[] tips;
       private final int colsToSkip;
       /**
        *
        * @param columnModel column model to use
        */
       public HeaderToolTips(final TableColumnModel columnModel, int colsToSkip) {
           super(columnModel);
           this.colsToSkip = colsToSkip;
       }

       /**
        * Define the Column Heading Tips
        *
        * @param strings - List of strings Tips
        */
       public void setTips(String[] strings) {
           tips = strings;
       }

       /**
        * @see javax.swing.table.JTableHeader#getToolTipText
        */
       public String getToolTipText(MouseEvent m) {
           String tip = super.getToolTipText(m);
           //int col = tblDetails.columnAtPoint(m.getPoint());
           try {
	           int col = super.getColumnModel().getColumnIndexAtX(m.getPoint().x);
	           if (col >= 0) {
		           col = super.getColumnModel().getColumn(col).getModelIndex() - colsToSkip;
		           if ((tips != null) && ((col < tips.length) && (col >= 0))) {
		               tip = tips[col];
		           }
	           }
           } catch (Exception e) {
        	   e.printStackTrace();
           }

           return tip;
       }
   }

   public class DelKeyWatcher extends KeyAdapter {
	   	private long lastWhen = Long.MIN_VALUE;
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	long when = event.getWhen();

        	if (when != lastWhen
        	&& (event.getModifiers() & KeyEvent.CTRL_MASK) == 0
        	&& (event.getModifiers() & KeyEvent.SHIFT_MASK) == 0
        	&& Common.OPTIONS.deleteSelectedWithDelKey.isSelected()) {
	        	switch (event.getKeyCode()) {
	        	case KeyEvent.VK_DELETE: check4Delete();	break;
	        	}
	        }

        	lastWhen = when;
        }
   }


   /**
    * Class to sort a table on a header click to a table
    * @param table to be sorted
    */
   public final class HeaderSort extends MouseAdapter {

       private int lastCol = -1;

       /**
        * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
        */
       public void mousePressed(MouseEvent e) {
           if (e.getClickCount() == 2) {
               JTableHeader header = (JTableHeader) e.getSource();
               int col = header.columnAtPoint(e.getPoint());
               int layoutIndex = getLayoutIndex();

               col = fileView.getRealColumn(layoutIndex,
               		header.getColumnModel().getColumn(col).getModelIndex() - 2);

               if (col >= 0) {
                   int[] cols = {col};
                   boolean[] descending = {lastCol == col};

                   fileView.sort(
                           new net.sf.JRecord.Details.LineCompare(fileView.getLayout(),
                           		layoutIndex,
                                   cols,
                                   descending
                           ));

                   if (lastCol == col) {
                       lastCol = -1;
                   } else {
                       lastCol = col;
                   }
               }
           }
       }
   }

/**
     *
     * @author Bruce Martin
     *
     * This column formats the Column Headings
     */
    public class HeaderRender extends JPanel implements TableCellRenderer {

        /**
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
         * 		(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(
            JTable tbl,
            Object value,
            boolean isFldSelected,
            boolean hasFocus,
            int row,
            int column) {

            removeAll();
            setLayout(new GridLayout(2, 1));

            if (column >= 0 && value != null) {

                String s = (String) value;
                String first = s;
                String second = "";
                int pos = s.indexOf(Common.COLUMN_LINE_SEP);
                if (pos > 0) {
                    first = s.substring(pos + 1);
                    second = s.substring(0, pos);
                }
                JLabel label = new JLabel(first);
                add(label);
                if ((!second.equals(""))) {
                    label = new JLabel(second);

  //                  System.out.println("Header Render");
                    if (getLayoutIndex() >= getLayoutCombo().getFullLineIndex()) {
                    	label.setFont(SwingUtils.getMonoSpacedFont());
                    }

                    add(label);
                }
            }
            this.setBorder(BorderFactory.createEtchedBorder());

            return this;
        }
    }
}

