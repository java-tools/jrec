/*
 * Created on 8/08/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Created local versions of TableLayout Constants
 *   - extra JInternalFrame support
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Automatic screen layout via overiding getPreferredSize and
 *     validate methods
 *   - add addComponent3Lines method which adds a fields over
 *     three lines
 *   - set line size based on third component in the line
 */
package net.sf.RecordEditor.utils.swing;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import java.awt.Component;
import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.BasicTrans;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.extraColumn.IExtraComponent;
import net.sf.RecordEditor.utils.swing.extraColumn.IHasExtraComponent;


/**
 * Standard Panel, it allows for the easy definition of
 * fields going down the page. It uses the TableLayout Manager to
 * format the panel.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class BasePanel extends JPanel {

	private static final int WIDTH_PREFERNCE_IDX = 0;
	private static final int HEIGHT_PREFERNCE_IDX = 1;
	
	public static final boolean NAME_COMPONENTS = "Y".equalsIgnoreCase(
			Parameters.getString(Parameters.NAME_FIELDS));

    public static final int LEFT = TableLayout.LEFT;

    /** Indicates that the component is top justified in its cell */
    public static final int TOP = TableLayout.TOP;

    /** Indicates that the component is centered in its cell */
    public static final int CENTER = TableLayout.CENTER;

    /** Indicates that the component is full justified in its cell */
    public static final int FULL = TableLayout.FULL;

    /** Indicates that the component is bottom justified in its cell */
    public static final int BOTTOM = TableLayout.BOTTOM;

    /** Indicates that the component is right justified in its cell */
    public static final int RIGHT = TableLayout.RIGHT;
    
  

    /**
     * Indicates that the component is leading justified in its cell. Leading
     * justification means components are left justified if their container is
     * left-oriented and right justified if their container is right-oriented.
     * Trailing justification is opposite. see
     * java.awt.Component#getComponentOrientation
     */
    public static final int LEADING = TableLayout.LEADING;

    /**
     * Indicates that the component is trailing justified in its cell. Trailing
     * justification means components are right justified if their container is
     * left-oriented and left justified if their container is right-oriented.
     * Leading justification is opposite. see
     * java.awt.Component#getComponentOrientation
     */
    public static final int TRAILING = TableLayout.TRAILING;

    /** Indicates that the row/column should fill the available space */
    public static final double FILL = TableLayout.FILL;

    /**
     * Indicates that the row/column should be allocated just enough space to
     * accomidate the preferred size of all components contained completely
     * within this row/column.
     */
    public static final double PREFERRED = TableLayout.PREFERRED;

    /**
     * Indicates that the row/column should be allocated just enough space to
     * accomidate the minimum size of all components contained completely within
     * this row/column.
     */
    public static final double MINIMUM = TableLayout.MINIMUM;

	public static final int PROMPT = 0;
	public static final int FIELD  = 1;
	public static final int LAST   = 2;

	public static final double NORMAL_HEIGHT = SwingUtils.NORMAL_FIELD_HEIGHT;
	private static final int FIELD_COMPARE2
				= (int) Math.max(
							SwingUtils.BUTTON_HEIGHT + 1,
							Math.max(NORMAL_HEIGHT + 1, SwingUtils.COMBO_TABLE_ROW_HEIGHT)
							) + 3;
	public static final double HEIGHT_1P1 = NORMAL_HEIGHT * 1.1;
	public static final double HEIGHT_1P2 = NORMAL_HEIGHT * 1.2;
	public static final double HEIGHT_1P4 = NORMAL_HEIGHT * 1.4;
	public static final double HEIGHT_1P6 = NORMAL_HEIGHT * 1.6;
	public static final double HEIGHT_1P7 = NORMAL_HEIGHT * 1.7;


	public static final double GAP    = SwingUtils.STANDARD_FONT_HEIGHT * 5 / 12;
	public static final double GAP0   = SwingUtils.STANDARD_FONT_HEIGHT;
	public static final double GAP1   = SwingUtils.STANDARD_FONT_HEIGHT * 5 / 3;
	public static final double GAP2   = SwingUtils.STANDARD_FONT_HEIGHT * 5 / 2;
	public static final double GAP3   = GAP1 * 2;
	public static final double GAP4   = GAP1 * 3;
	public static final double GAP5   = GAP1 * 4;

	public static final int FIELD_HEIGHT_INDEX = 1;
	public static final int GAP_HEIGHT_INDEX = 2;

	public static final int VG_BORDER = 0;
	public static final int VG_PROMPT = 1;
	public static final int VG_GAP1   = 2;
	public static final int VG_FIELD1 = 3;
	public static final int VG_GAP2   = 6;
	public static final int VG_FIELD2 = 7;

	public static final int LAST_USED_COLUMN = -99;
	public static final int MAXIMUM_COLUMN = -97;


	public static final int BORDER = 10;
	public static final double HEADING_HEIGHT = ((int) (NORMAL_HEIGHT * 5) / 4);
	public static final double HEADING_GAP = ((int) (NORMAL_HEIGHT * 3) / 4);


	private static final int MAX_PNL_LINES = 200;

	private static final int FIELD_SEPERATION_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 3;

	private int numCols = 1;


	private int[][] fieldLayout =  {{TableLayout.RIGHT, TableLayout.CENTER},
							{TableLayout.FULL, TableLayout.CENTER},
							{TableLayout.FULL, TableLayout.CENTER}};

	private double[] vGaps = {BORDER, TableLayout.PREFERRED,
					   5, TableLayout.FILL,
					   1, 1, FIELD_SEPERATION_WIDTH - 2, TableLayout.PREFERRED,
					   BORDER};

	private double[] hGaps = {BORDER, NORMAL_HEIGHT, 5};

	private double[][] gridSize = {{1},
						   {1}};

	private int currRow = -1;
	private double[] lSize = new double[MAX_PNL_LINES];

	private TableLayout tLayout = new TableLayout(gridSize);

	private String heading = null;
	private JComponent headingComponent = null;

	private boolean toBeDone = true;
//	private boolean	singleColumn = false;

	private String fieldNamePrefix = "";
	private String panelId = "";

	private boolean nameComponents = NAME_COMPONENTS;

	private JTextComponent messageTxt;



	private static final boolean  logMissingMsgFields =
				"y".equalsIgnoreCase(
							Parameters.getString(Parameters.LOG_TEXT_FIELDS));
	private static final String txtLogFile = Parameters.getPropertiesDirectoryWithFinalSlash() + "MissingMessageTxt.txt";
	
	private int colOf3rdComponent = 7;
//	private int colInc = 0;
//	private ArrayList<IExtraComponent> extraComponents = null;
//	private boolean onlySingleItems = true;
//	private ArrayList<TableLayoutConstraints> rightItems = new ArrayList<TableLayoutConstraints>();
	private ArrayList<ComponentToAdd> toAdd = new ArrayList<BasePanel.ComponentToAdd>();

	private boolean addFillToEnd = false;
//	private static PrintWriter logPrinter = null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public BasePanel() {
		super();
		int i;

		this.setLayout (tLayout);

		lSize[0] = hGaps[0];
		for (i = 1; i < lSize.length; i++) {
			lSize[i] = 0;
		}

		String className = this.getClass().getSimpleName();
		if (! ("BasePanel".equals(className) || "BaseHelpPanel".equals(className))) {
			panelId = className;
		}
	}
//
//	public final void oneColumn() {
//		fieldLayout[0][0] = TableLayout.FULL;
//		fieldLayout[0][1] = TableLayout.FULL;
//
//		vGaps[1] = TableLayout.FILL;
//		vGaps[3] = TableLayout.PREFERRED;
//
//		singleColumn = true;
//	}


	/**
	 * Adds a Heading Field to the Form
	 *
	 * @param headingText Heading Text
	 */
	public final void addHeadingRE(String headingText) {

		headingIncRE(HEADING_HEIGHT, HEADING_GAP);

		heading = headingText;
	}


	/**
	 * Add a panel Heading
	 *
	 * @param pnlHeading Heading component
	 */
	public final void addHeadingComponentRE(JComponent pnlHeading) {

		headingIncRE(BasePanel.PREFERRED, GAP);

		this.headingComponent = pnlHeading;
	}


	/**
	 * Increment fields for a heading
	 *
	 * @param height Heading Height
	 * @param gap Horizontal gap to the first field
	 */
	private final void headingIncRE(double height, double gap) {

		currRow += 1;

		lSize[currRow]     = height;
		lSize[currRow + 1] = gap;
	}

	/**
	 * Adds a field to the Panel
	 *
	 * @param prompt Fields Prompt
	 * @param component Component to be added to the Panel
	 */
	public final BasePanel addLineRE(String prompt, JComponent component) {
		addLineRE(prompt, component, 3, 3);
		
		checkForExtraComponenetRE(component);
		return this;
	}

//	/**
//	 * Adds a field to the Panel
//	 *
//	 * @param prompt Fields Prompt
//	 * @param component Component to be added to the Panel
//	 */
//	public final BasePanel addLineWithWidth(String prompt, JComponent component, int width) {
//		//TODO
//		
//		int save = fieldLayout[FIELD][WIDTH_PREFERNCE_IDX];
//		try {
//			fieldLayout[FIELD][WIDTH_PREFERNCE_IDX] = width;
//			addLine(prompt, component, 3, 3);
//			
//			checkForExtraComponenet(component);
//		} finally {
//			fieldLayout[FIELD][WIDTH_PREFERNCE_IDX] = save;
//		}
//		return this;
//	}

	//TODO check
	private void checkForExtraComponenetRE(JComponent component) {
		if (component instanceof IHasExtraComponent) {
			IExtraComponent c = ((IHasExtraComponent) component).getExtraComponentDetails();
			
			this.add(c.getComponent(), 
					 new TableLayoutConstraints(5, currRow, 5, currRow, TableLayout.LEFT, TableLayout.CENTER));
			
			vGaps[4] = 5;
			vGaps[5] = TableLayout.MINIMUM;
			vGaps[6] = 6;
			setNumCols(5);
		}
	}

	private final BasePanel addLineInternal(String prompt, JComponent component) {
		addLineRE(prompt, component, 3, 3);
		
		checkForExtraComponenetRE(component);
		return this;

	}

	public final BasePanel addLineFullWidthRE(String prompt, JComponent component) {
		return addLineRE(prompt, component, 3, colOf3rdComponent);
	}
	/**
	 * Adds a field to the Panel
	 *
	 * @param prompt Fields Prompt
	 * @param component Component to be added to the Panel
	 */
	private final BasePanel addLineRE(String prompt, JComponent component, int firstCol, int lastCol) {
		JLabel promptLbl = getLabelRE(prompt);

		if (component == null) {
			if (promptLbl != null) {
				addLineRE(promptLbl, component);
			}
		} else {
			addLineRE(promptLbl, component, firstCol, lastCol);
			setComponentName(component, prompt);
		}

		return this;
	}

//	public final BasePanel addLine(JComponent promptLbl) {
//		return addLine(promptLbl, (JComponent) null);
//	}
//
	public final BasePanel addLineRE(JComponent promptLbl, JComponent component) {
		
		addLineRE(promptLbl, component, 3, 3);
		checkForExtraComponenetRE(component);
		return this;
	}


	public final BasePanel addLine1to3(JComponent component) {
		
		addLineRE((JLabel) null, component, 1, 3);
		checkForExtraComponenetRE(component);
		return this;
	}
	
	public final BasePanel addLine3to5(String prompt, JComponent component) {
		return addLine3to5(getLabelRE(prompt), component);
	}

	private JLabel getLabelRE(String prompt) {
		JLabel promptLbl = null;

		if (prompt != null) {
			 promptLbl = new JLabel(LangConversion.convertFld(panelId, prompt));
			 promptLbl.setHorizontalAlignment(JLabel.RIGHT);
		}
		return promptLbl;
	}

	public final BasePanel addLine3to5(JComponent promptLbl, JComponent component) {
		return addLineRE(promptLbl, component, 3, colOf3rdComponent);
	}

	private final BasePanel addLineRE(JComponent promptLbl, JComponent component, int firstCol, int lastCol) {
		incRow();

		lastCol = fixColRE(lastCol);
		if (promptLbl != null) {
			this.add(promptLbl,
				new TableLayoutConstraints(1, currRow, 1, currRow,
						fieldLayout[PROMPT][WIDTH_PREFERNCE_IDX],
						fieldLayout[PROMPT][HEIGHT_PREFERNCE_IDX]));
			if (component != null
//			&& ! singleColumn
			&& promptLbl.getPreferredSize().height <= component.getPreferredSize().height
			&& component.getPreferredSize().height <= FIELD_COMPARE2) {
				Dimension d = promptLbl.getPreferredSize();
//				System.out.print(" --> " + d.height
//						+ " " + component.getPreferredSize().height
//						+ " " + d.width);
				d.setSize(d.getWidth(), component.getPreferredSize().height);
				promptLbl.setPreferredSize(d);
//				System.out.println(" --> " + promptLbl.getPreferredSize().height);
				promptLbl.setAlignmentY(BOTTOM);
//			} else if (component != null) {
//				System.out.println(" ==> " + component.getPreferredSize().height
//						+ " " + FIELD_COMPARE2);
			}
		}

		setNumCols(Math.max(3, lastCol));
		if (component == null) {
			if (promptLbl != null) {
				lSize[currRow] = Math.max(lSize[currRow], promptLbl.getPreferredSize().getHeight());
			}
		} else {
		    addRightItem(component,
		            		firstCol, currRow, lastCol, currRow,
		                    fieldLayout[FIELD][WIDTH_PREFERNCE_IDX], 
		                    fieldLayout[FIELD][HEIGHT_PREFERNCE_IDX]);

		    //setComponentName(component, promptLbl);

			lSize[currRow] = Math.max(lSize[currRow], component.getPreferredSize().getHeight());
		    registerComponentRE(component);
		}
		return this;
	}


	/**
	 * Adds 2 components to Panel on the Same Line
	 *
	 * @param prompt Field Prompt
	 * @param component Primary Component
	 * @param component2 Seconday component
	 */
	public final BasePanel addLineRE(String prompt, JComponent component, JComponent component2) {

		if (component2 == null) {
			addLineInternal(prompt, component);
		} else {
			int h = component2.getPreferredSize().height;
			if (component != null
			&& h > component.getPreferredSize().getHeight()
			&& h <= FIELD_COMPARE2) {
				Dimension d = component.getPreferredSize();
//				System.out.print(" ### " + d.height
//						+ " " + h
//						+ " " + d.width);
				d.setSize(d.getWidth(), h);
				component.setPreferredSize(d);
//			} else if (component != null) {
//				System.out.print(" #### " + component.getPreferredSize().getHeight()
//						+ " " + h
//						+ " " + FIELD_COMPARE2) ;
			}

			if (component == null) {
				incRow();
			} else {
				addLineInternal(prompt, component);
			}


			setNumCols(colOf3rdComponent);
			//TODO add
			
			addRightItem(component2,
						colOf3rdComponent, currRow, colOf3rdComponent, currRow,
						fieldLayout[LAST][WIDTH_PREFERNCE_IDX], 
						fieldLayout[LAST][HEIGHT_PREFERNCE_IDX]);
//			this.add(component2,
//				 new TableLayoutConstraints(colOf3rdComponent, currRow, colOf3rdComponent, currRow,
//						fieldLayout[LAST][0], fieldLayout[LAST][1]));
			registerComponentRE(component2);


			setComponent2Name(component2, prompt +"_fld2");


			lSize[currRow] = Math.max(
					lSize[currRow],
					component2.getPreferredSize().getHeight());
		}
		return this;
	}
	
	private void addHeldComponents() {
		for (ComponentToAdd c : toAdd) {
			this.add(c.component, c.getConstraint());
		}
		toAdd.clear();
	}

	private void addRightItem(JComponent component2,
			int startCol, int startRow,
			int endCol, int endRow, 
			int widthConstraint,
			int heightConstraint) {
			//TableLayoutConstraints constraint) {
//		this.add(component2, constraint);
		
		toAdd.add(new ComponentToAdd(
				component2, 
				startCol, startRow, endCol, endRow, widthConstraint, 
				heightConstraint));
//		rightItems.add(constraint);

	}

	/**
	 * Adds 2 components to Panel on the Same Line
	 *
	 * @param prompt Field Prompt
	 * @param component Primary Component
	 * @param component2 Seconday component
	 * @param component3 third component
	 */
	public final BasePanel addLineRE(String prompt, JComponent component, JComponent component2, JComponent component3) {
		JLabel promptLbl = null;

//		if (promptLbl != null) {
			 promptLbl = new JLabel(LangConversion.convertFld(fieldNamePrefix, prompt));
			 promptLbl.setHorizontalAlignment(JLabel.RIGHT);
//		}
		return addLineRE(promptLbl, component, component2, component3);
	}

	public final BasePanel addLineRE(JComponent prompt, JComponent component, JComponent component2, JComponent component3) {

		addLineRE(prompt, component);

		if (component2 != null) {
			addRightItem(component2,
				 		 colOf3rdComponent, currRow, colOf3rdComponent, currRow,
						 fieldLayout[FIELD][WIDTH_PREFERNCE_IDX],
						 fieldLayout[FIELD][HEIGHT_PREFERNCE_IDX]);
			registerComponentRE(component2);
			lSize[currRow] = Math.max(lSize[currRow], component2.getPreferredSize().getHeight());
			setNumCols(colOf3rdComponent);
		}

		if (component3 != null) {
			addRightItem(component3,
						colOf3rdComponent + 2, currRow, colOf3rdComponent + 2, currRow,
						fieldLayout[LAST][WIDTH_PREFERNCE_IDX], 
						fieldLayout[LAST][HEIGHT_PREFERNCE_IDX]);
			registerComponentRE(component3);

			lSize[currRow] = Math.max(lSize[currRow], component3.getPreferredSize().getHeight());
			setNumCols(colOf3rdComponent + 2);
		}

		return this;
	}

	/**
	 * Add 2 components over 3 lines
	 *
	 * @param prompt Field Prompt
	 * @param component Primary Component
	 * @param component2 Seconday component
	 */
	public final void addComponent3Lines(String prompt, JComponent component, JComponent component2) {
	    double height = 0;
	    if (component2 != null) {
	        height = component2.getPreferredSize().height;
	    }

	    if (height < hGaps[FIELD_HEIGHT_INDEX]) {
	        addLineRE(prompt,  component,  component2);
	    } else {
	        double pad = (height - hGaps[FIELD_HEIGHT_INDEX]) / 2;

	        currRow += 1;
            lSize[currRow + 1]     = pad;

	        addLineRE(prompt, component);

	        setNumCols(colOf3rdComponent);
	        addRightItem(component2,
	                		colOf3rdComponent, currRow - 1, colOf3rdComponent, currRow + 1,
	                        fieldLayout[LAST][WIDTH_PREFERNCE_IDX],
	                        fieldLayout[LAST][HEIGHT_PREFERNCE_IDX]);
	        registerComponentRE(component2);

	        currRow += 1;

	        lSize[currRow]     = pad;
	        lSize[currRow + 1] = hGaps[GAP_HEIGHT_INDEX];
	    }
	}

	/**
	 * Register a component. Can be overriden to for special purposes
	 * (like registering keylister (ie listen for PF1 for help)
	 *
	 * @param component compoent being registered
	 */
	public void registerComponentRE(JComponent component) {

       // System.out.println("==}} " + component.getName() + " " + component.getClass().getName());
	}


	/**
	 * Add a Component in a supplied Column with supplied column height
	 *
	 * @param startCol   Starting Column
	 * @param endCol     End Column
	 * @param height     Height of the row
	 * @param gap        Gap to follow the row
	 * @param hPosition  Horizontal Position
	 * @param vPosition  Vertial Position
	 * @param table      Table to be added to the Panel
	 */
	public final BasePanel addComponentRE(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 JTable table) {

	    addComponentRE(startCol, endCol,
				 height, gap,
				 hPosition, vPosition,
				 true,
				 new JScrollPane(table));
		registerComponentRE(table);

		return this;
	}


	/**
	 * Add a Component in a supplied Column with supplied column height
	 *
	 * @param startCol   Starting Column
	 * @param endCol     End Column
	 * @param height     Height of the row
	 * @param gap        Gap to follow the row
	 * @param hPosition  Horizontal Position
	 * @param vPosition  Vertial Position
	 * @param component  Component to be added to the Panel
	 */
	public final BasePanel addComponentRE(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 JComponent component) {
		return addComponentRE(startCol, endCol, height, gap, hPosition, vPosition, true, component);
	}


	/**
	 * Add a Component in a supplied Column with supplied column height
	 *
	 * @param startCol   Starting Column
	 * @param endCol     End Column
	 * @param height     Height of the row
	 * @param gap        Gap to follow the row
	 * @param hPosition  Horizontal Position
	 * @param vPosition  Vertial Position
	 * @param component  Component to be added to the Panel
	 */
	public final BasePanel addComponentRE(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 JEditorPane component) {
		return addComponentRE(startCol, endCol, height, gap, hPosition, vPosition, true, new JScrollPane(component));
	}

	/**
	 * Add a Component in a supplied Column with supplied column height
	 *
	 * @param startCol   Starting Column
	 * @param endCol     End Column
	 * @param height     Height of the row
	 * @param gap        Gap to follow the row
	 * @param hPosition  Horizontal Position
	 * @param vPosition  Vertial Position
	 * @param component  Component to be added to the Panel
	 */
	public final BasePanel addComponentRE(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 boolean incRow,
							 JComponent component) {
		
		if (endCol >= 0) {
			startCol = fixColRE(startCol);
		}
		endCol = fixColRE(endCol);
		
		setNumCols(endCol);

		if (incRow) {
			incRow(height, gap);
		}
		add(component, new TableLayoutConstraints(startCol, currRow,
												  endCol, currRow,
												  hPosition, vPosition));
		registerComponentRE(component);

		return this;
	}

	private int fixColRE(int endCol) {
		if (endCol == LAST_USED_COLUMN) {
			endCol = numCols;
		} else if (endCol == MAXIMUM_COLUMN) {
			endCol = numCols + 1;
//		} else if (endCol == 3) {
//			endCol = 4;
		} else if (endCol >= 5) {
			endCol += 2;
		}
		return endCol;
	}

	/**
	 * Adds a Menu item to the panel; the prompt is in English
	 *
	 * @param prompt Menu Prompt
	 * @param btn    Menu Button to be added to the panel
	 */
	public final BasePanel addMenuItemRE(String prompt, JButton btn) {
		return addMenuItemNativeRE(LangConversion.convertFld(fieldNamePrefix, prompt), btn);
	}

	/**
	 * Adds a Menu item to the panel; the prompt is in the local language
	 *
	 * @param prompt Menu Prompt
	 * @param btn    Menu Button to be added to the panel
	 */
	public final BasePanel addMenuItemNativeRE(String prompt, JButton btn) {
		
		incRow();

		add(btn, new TableLayoutConstraints(1, currRow, 1, currRow,
		        BasePanel.RIGHT, BasePanel.TOP));
		registerComponentRE(btn);

		JLabel promptLbl = new JLabel(prompt);
		//promptLbl.setHorizontalAlignment(JLabel.RIGHT);

		setNumCols(5);
		this.add(promptLbl,
			new TableLayoutConstraints(3, currRow, 5, currRow,
					fieldLayout[FIELD][WIDTH_PREFERNCE_IDX], 
					fieldLayout[FIELD][1]));

		setComponentName(btn, prompt);
		return this;
	}

	/**
	 * Adds a button to the Panel in the supplied column
	 *
	 * @param incRow wether to increment the row counter
	 * @param col    column to add the button in
	 * @param height height of the Button
	 * @param btn    Button to Add
	 */
	public final BasePanel addButtonRE(boolean incRow, int col, int height, JButton btn) {


		if (col < 4) {
			int c = col * 2 - 1;
			int endC = c;
			if (col == 3) {
				endC += 2;
				
			} else if (col > 3) {
				c += 2;
				endC = c;
			}

			if (incRow) {
				incRow(height, hGaps[GAP_HEIGHT_INDEX]);
			} else if (height > 0) {
				lSize[currRow] = height;
			}

			setNumCols(endC);

//			System.out.println("Add Btn >> " + col + " " + oneField + "  "
//					+  c + " " + currRow);

			add(btn, new TableLayoutConstraints(c, currRow, endC, currRow,
			        BasePanel.RIGHT, BasePanel.TOP));
			registerComponentRE(btn);
		}

		return this;
	}


	/**
	 * Add an Icon to the panel as a Button
	 *
	 * @param incRow wether to increment the row counter
	 * @param col    column to add the button in
	 * @param icon   Icon to be added to the panel
	 *
	 * @return  Button that was added to the panel
	 */
	public final JButton addIconButtonRE(boolean incRow, int col, ImageIcon icon) {
		JButton btn = new JButton("", icon);
		addButtonRE(incRow, col, icon.getIconHeight() + 5, btn);

		return btn;
	}



	/**
	 * Set the Horizontal Gap between the current Field and the Next Field
	 *
	 * @param gap Line gap
	 */
	public final BasePanel setGapRE(double gap) {
		lSize[currRow + 1] = gap;
		return this;
	}


	/**
	 * Set Height of the current Field
	 *
	 * @param height Required Height
	 */
	public final BasePanel setHeightRE(double height) {
		if (toAdd.size() > 0) {
			toAdd.get(0).heightConstraint = FULL;
		}
		lSize[currRow] = height;
		return this;
	}

	public final void setWidthConstraintRE(int val) {
		if (toAdd.size() > 0) {
			toAdd.get(0).widthConstraint = val;
		}
	}

	public void setMessageTxtRE(String msg) {
		setMessageRawTxtRE(LangConversion.convert(msg));
	}

	public void setMessageTxtRE(String msg, String error) {
		setMessageRawTxtRE(LangConversion.convert(msg) + " " + error);
	}

	public void setMessageRplTxtRE(String msg, String msg2) {
		setMessageRawTxtRE(LangConversion.convert(msg, msg2));
	}

	public void setMessageRawTxtRE(String msg) {
		if (messageTxt == null) {
			Common.logMsgRaw(msg, null);
			if (logMissingMsgFields) {
				try {

					PrintWriter	logPrinter = new PrintWriter(new FileWriter(txtLogFile, true));

					logPrinter.println("setMsg: " + this.getClass().getName() + " ! " + this.panelId + " ! " + msg);
					logPrinter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} else {
			messageTxt.setText(msg);
		}
	}

	/**
	 * Add a message Field to the panel
	 */
	public final BasePanel addMessageRE() {
		return addMessage(new JTextField());
	}


	/**
	 * Add a message Field to the panel
	 *
	 * @param msg message Field
	 */
	public final BasePanel addMessage(JTextComponent msg) {
		messageTxt = msg;
		return addMessageI(msg);
	}

	public final void setMessageRE(JTextComponent msg) {
		messageTxt = msg;
	}

	/**
	 * Add a message Field to the panel
	 *
	 * @param msg message Field
	 */
	public final BasePanel addMessage(JComponent msg) {

		return addMessageI(msg);
	}

	/**
	 * Add a message Field to the panel
	 *
	 * @param msg message Field
	 */
	private final BasePanel addMessageI(JComponent msg) {

		incRow(hGaps[FIELD_HEIGHT_INDEX], 2);

		add(msg, new TableLayoutConstraints(1, currRow, numCols, currRow,
		        BasePanel.FULL, BasePanel.FULL));
		registerComponentRE(msg);

		if (msg.getMinimumSize().height < HEIGHT_1P6) {
			setHeightRE(HEIGHT_1P6);
		}

		return this;
	}


	/**
	 * Increment the row counter and set standard Hieght and Gap
	 *
	 */
	public final void incRow() {

		incRow(hGaps[FIELD_HEIGHT_INDEX], hGaps[GAP_HEIGHT_INDEX]);
	}


	/**
	 * Increment the row counter and set the Hieght and Gap amounts to the
	 * supplied values
	 *
	 * @param height Height of the Field
	 * @param gap    Gap that will follow the field
	 */
	public final void incRow(double height, double gap) {
		addHeldComponents();
		
		currRow += 2;

		lSize[currRow]     = height;
		lSize[currRow + 1] = gap;
	}


    /**
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize() {

        layoutComponents();
        return super.getPreferredSize();
    }


    /**
     * @see java.awt.Component#validate()
     */
    public void validate() {

        layoutComponents();
        super.validate();
    }


    /**
     * Layout components if it still needs to be done
     */
    private void layoutComponents() {

        if (toBeDone) {
            done();
        }
    }

	/**
	 * Updates the Panels layout.
	 *
	 */
	public void done() {
		double[] hGap = new double[currRow + 2];

		System.arraycopy(lSize, 0, hGap, 0, currRow + 2);
		
		addHeldComponents();

		//System.out.print("done " + headingComponent==null);
		if (headingComponent != null) {
			//System.out.println(" adding");
			add(headingComponent,
					new TableLayoutConstraints(1, 0, numCols, 0,
							TableLayout.CENTER, TableLayout.FULL));
		} else if (heading != null) {
			JLabel jHeading = new JLabel(LangConversion.convertFld(fieldNamePrefix, heading));
			add(jHeading,
					 new TableLayoutConstraints(1, 0, numCols, 0,
							TableLayout.CENTER, TableLayout.FULL));
		}
		BasicTrans.flush(BasicTrans.FLUSH_PNL);

		//TODO
		//TODO position of extra-column code
		double[] vG = vGaps;
		switch (numCols) {
		case 1:
		case 3:
		case 5:
			vG = new double[numCols + 2];
			System.arraycopy(vGaps, 0, vG, 0, vG.length - 1);
			vG[vG.length - 1] = vGaps[vGaps.length - 1];
			break;
		case 8:
			if (addFillToEnd) {
				vG = new double[numCols + 2];
				System.arraycopy(vGaps, 0, vG, 0, vGaps.length - 1);
	//			System.arraycopy(vGaps, vGaps.length - 3, vG, vG.length - 3, 3);
				vG[vG.length - 2] = TableLayout.FILL;
				vG[vG.length - 1] = vGaps[vGaps.length - 1];
			}
			break;
		case 9:
			vG = new double[numCols * 2 - 1];
			System.arraycopy(vGaps, 0, vG, 0, vGaps.length - 3);
			System.arraycopy(vGaps, vGaps.length - 3, vG, vG.length - 3, 3);
			vG[vG.length - 5] = 5;
			vG[vG.length - 4] = TableLayout.PREFERRED;
			break;
		}
//		if (numCols == 1) {
//			vG = new double[] {vGaps[0], vGaps[1], vGaps[vGaps.length - 1]};
//		} else if (numCols == 3) {
//			vG = new double[] {vGaps[0], vGaps[1], vGaps[2], vGaps[3], vGaps[vGaps.length - 1]};
//		} else if (numCols == 9) {
//			vG = new double[] {vGaps[0], vGaps[1], vGaps[2], vGaps[3],
//					5, TableLayout.PREFERRED,
//					vGaps[6], vGaps[7], vGaps[vGaps.length - 1]};
//		}
//		
//		if (! onlySingleItems) {
//			if (vG.length > 4) {
//				double[] vGtemp = vG;
//				vG = new double[vG.length + 2];
//				System.arraycopy(vGtemp, 0, vG, 0, 4);
//				System.arraycopy(vGtemp, 4, vG, 6, vGtemp.length - 4);
//				vG[4] = 3;
//				vG[5] = TableLayout.MINIMUM;
//			
//				if (vG.length > 7) {
//					vG[6] = 6;
//				}
//			}
//		}
		
		tLayout.setColumn(vG);
		tLayout.setRow(hGap);

		toBeDone = false;

		if (messageTxt == null && logMissingMsgFields) {
			try {
				PrintWriter logPrinter = new PrintWriter(new FileWriter(txtLogFile, true));

				logPrinter.println("done: " + this.getClass().getName() + " ! " + this.panelId + " ! ");
				logPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

//		private static final boolean  logMissingMsgFields =
//				"y".equalsIgnoreCase(
//							Parameters.getString(Parameters.LOG_TEXT_FIELDS));
//	private static final String txtItmFile = Parameters.getPropertiesDirectoryWithFinalSlash() + "MissingMessageTxt.txt";
//
//	private static PrintWriter log = null;

	}


	public final int getNumberOfCols() {
		return numCols;
	}


	/**
	 * Update the maximum number of columns if need be
	 *
	 * @param cols column to check if greater than current max
	 */
	private final void setNumCols(int cols) {
		if (cols > numCols) {
			numCols = cols;
		}
	}



	/**
	 * @return the current row
	 */
	public final int getCurrRow() {
		return currRow;
	}

	/**
	 * @param newCurrentRow Set the Current row
	 */
	public final void setCurrRow(int newCurrentRow) {
		currRow = newCurrentRow;
	}


	/**
	 * Set the Gap width
	 * @param gapId Gap Type (ie PROMPT = 0;  FIELD  = 1; LAST   = 2;)
	 * @param gapWidth new gap width
	 */
	public final void setVerticalGap(int gapId, double gapWidth) {
		vGaps[gapId] = gapWidth;
		if (gapId == VG_BORDER) {
			vGaps[vGaps.length - 1] = gapWidth;
		} else if (gapId == VG_GAP2) {
			vGaps[gapId] = gapWidth - 2;
		}
	}

	public final BasePanel setFieldsToActualSize() {
		fieldLayout[FIELD][WIDTH_PREFERNCE_IDX] = LEFT;
		return this;
	}

	public final BasePanel setFieldsToFullSize() {
		fieldLayout[FIELD][WIDTH_PREFERNCE_IDX] = FULL;
		return this;
	}

	public final void setFieldSize(int fieldId, int widthHeight, int newValue) {
		fieldLayout[fieldId][widthHeight] = newValue;
	}

//	public final BasePanel setComponentName(Component component, Component name) {
//
//		if (name != null) {
//			setComponentName(component, name.toString());
//		}
//		return this;
//	}


	public final BasePanel setComponent2Name(Component component, String name) {
		if (name != null && ! "".equals(name)) {
			setComponentName(component, name + "_Fld2");
		}
		return this;
	}


	public final BasePanel setComponentName(Component component, String name) {

	    if (nameComponents && component != null && name != null && ! "".equals(name)) {
	    	String suffix = "";
	    	if (component instanceof JTextField
	    	|| component instanceof JTextArea
	    	|| component instanceof JComboBox) {
	    		suffix = "_Txt";
	    	} else if (component instanceof JCheckBox) {
	    		suffix = "_Chk";
	    	}  else if (component instanceof JButton) {
	    		suffix = "_Btn";
	    	} else if (component instanceof JTable) {
	    		suffix = "_JTbl";
	    	}
	    	component.setName(fieldNamePrefix + name + suffix);
	    }

		return this;
	}

	/**
	 * @param fieldNamePrefix the fieldNamePrefix to set
	 */
	public void setFieldNamePrefix(String fieldNamePrefix) {
		if ( fieldNamePrefix == null || "".equals(fieldNamePrefix) ) {
			this.fieldNamePrefix = "";
		} else {
			this.fieldNamePrefix = fieldNamePrefix + ".";
			this.panelId = fieldNamePrefix;
		}
	}

	/**
	 * @param nameComponents the nameComponents to set
	 */
	public void setNameComponents(boolean nameComponents) {
		this.nameComponents = nameComponents;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}
	
	public static void setToCommonWidth(int adj, int minWidth, JComponent... components) {
		int width = 0;
		for (JComponent c : components) {
			width = Math.max(width, c.getPreferredSize().width);
		}
		
		if (width > 0) {
			width = Math.max(width + SwingUtils.CHAR_FIELD_WIDTH * adj, SwingUtils.CHAR_FIELD_WIDTH * minWidth);
			for (JComponent c : components) {
				c.setPreferredSize(new Dimension(width, c.getPreferredSize().height));
			}
		}
	}
	
	public void setAddFillToEnd(boolean addFillToEnd) {
		this.addFillToEnd = addFillToEnd;
	}
	


	private static class ComponentToAdd {
		
		final JComponent component;
		
		final int startCol, startRow, endCol, endRow;
		int widthConstraint, heightConstraint;

		public ComponentToAdd(JComponent component, int startCol, int startRow,
				int endCol, int endRow, int widthConstraint,
				int heightConstraint) {
			super();
			this.component = component;
			this.startCol = startCol;
			this.startRow = startRow;
			this.endCol = endCol;
			this.endRow = endRow;
			this.widthConstraint = widthConstraint;
			this.heightConstraint = heightConstraint;
		}
		
		TableLayoutConstraints getConstraint() {
			return new TableLayoutConstraints(
							startCol, startRow, endCol, endRow, 
							widthConstraint, heightConstraint);
		}
	}

	
}
