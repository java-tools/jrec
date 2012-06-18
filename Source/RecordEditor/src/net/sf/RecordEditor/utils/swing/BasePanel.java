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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Parameters;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;


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
	public static final int VG_GAP2   = 4;
	public static final int VG_FIELD2 = 5;



	public static final int BORDER = 10;
	public static final double HEADING_HEIGHT = ((int) (NORMAL_HEIGHT * 5) / 4);
	public static final double HEADING_GAP = ((int) (NORMAL_HEIGHT * 3) / 4);


	private static final int MAX_PNL_LINES = 200;

	private static final int FIELD_SEPERATION_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 3;

	private int numCols = 1;


	private int[][] fieldLayout =  {{TableLayout.RIGHT, TableLayout.TOP},
							{TableLayout.FULL, TableLayout.FULL},
							{TableLayout.FULL, TableLayout.TOP}};

	private double[] vGaps = {BORDER, TableLayout.PREFERRED,
					   5, TableLayout.FILL,
					   FIELD_SEPERATION_WIDTH, TableLayout.PREFERRED,
					   BORDER};

	private double[] hGaps = {BORDER, NORMAL_HEIGHT, 5};

	private double[][] gridSize = {{1},
						   {1}};

	private int currRow = -1;
	private double[] lSize = new double[MAX_PNL_LINES];

	private TableLayout tLayout = new TableLayout(gridSize);

	private String heading = null;
	private JComponent headingComponent = null;

	private boolean toBeDone = true, singleColumn = false;

	private String fieldNamePrefix = "";

	private boolean nameComponents = NAME_COMPONENTS;
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
	}

	public final void oneColumn() {
		fieldLayout[0][0] = TableLayout.FULL;
		fieldLayout[0][1] = TableLayout.FULL;

		vGaps[1] = TableLayout.FILL;
		vGaps[3] = TableLayout.PREFERRED;

		singleColumn = true;
	}


	/**
	 * Adds a Heading Field to the Form
	 *
	 * @param headingText Heading Text
	 */
	public final void addHeading(String headingText) {

		headingInc(HEADING_HEIGHT, HEADING_GAP);

		heading = headingText;
	}


	/**
	 * Add a panel Heading
	 *
	 * @param pnlHeading Heading component
	 */
	public final void addHeadingComponent(JComponent pnlHeading) {

		headingInc(BasePanel.PREFERRED, GAP);

		this.headingComponent = pnlHeading;
	}


	/**
	 * Increment fields for a heading
	 *
	 * @param height Heading Height
	 * @param gap Horizontal gap to the first field
	 */
	private final void headingInc(double height, double gap) {

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
	public final BasePanel addLine(String prompt, JComponent component) {
		JLabel promptLbl = null;

		if (prompt != null) {
			 promptLbl = new JLabel(prompt);
			 promptLbl.setHorizontalAlignment(JLabel.RIGHT);
		}

		if (component == null) {
			if (promptLbl != null) {
				addLine(promptLbl, component);
			}
		} else {
			addLine(promptLbl, component);
			setComponentName(component, prompt);
		}

		return this;
	}

	public final BasePanel addLine(JComponent promptLbl) {
		return addLine(promptLbl, (JComponent) null);
	}

	public final BasePanel addLine(JComponent promptLbl, JComponent component) {
		incRow();

		if (promptLbl != null) {
			this.add(promptLbl,
				new TableLayoutConstraints(1, currRow, 1, currRow,
						fieldLayout[PROMPT][0], fieldLayout[PROMPT][1]));
			if (component != null
			&& ! singleColumn
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

		setNumCols(3);
		if (component == null) {
			if (promptLbl != null) {
				lSize[currRow] = Math.max(lSize[currRow], promptLbl.getPreferredSize().getHeight());
			}
		} else {
		    this.add(component,
		            new TableLayoutConstraints(3, currRow, 3, currRow,
		                    fieldLayout[FIELD][0], fieldLayout[FIELD][1]));

		    //setComponentName(component, promptLbl);

			lSize[currRow] = Math.max(lSize[currRow], component.getPreferredSize().getHeight());
		    registerComponent(component);
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
	public final BasePanel addLine(String prompt, JComponent component, JComponent component2) {

		if (component2 == null) {
			addLine(prompt, component);
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
				addLine(prompt, component);
			}


			setNumCols(5);
			this.add(component2,
				 new TableLayoutConstraints(5, currRow, 5, currRow,
						fieldLayout[LAST][0], fieldLayout[LAST][1]));
			registerComponent(component2);


			setComponent2Name(component2, prompt +"_fld2");


			lSize[currRow] = Math.max(
					lSize[currRow],
					component2.getPreferredSize().getHeight());
		}
		return this;
	}

	/**
	 * Adds 2 components to Panel on the Same Line
	 *
	 * @param prompt Field Prompt
	 * @param component Primary Component
	 * @param component2 Seconday component
	 * @param component3 third component
	 */
	public final BasePanel addLine(String prompt, JComponent component, JComponent component2, JComponent component3) {
		JLabel promptLbl = null;

//		if (promptLbl != null) {
			 promptLbl = new JLabel(prompt);
			 promptLbl.setHorizontalAlignment(JLabel.RIGHT);
//		}
		return addLine(promptLbl, component, component2, component3);
	}

	public final BasePanel addLine(JComponent prompt, JComponent component, JComponent component2, JComponent component3) {

		addLine(prompt, component);

		if (component2 != null) {
			this.add(component2,
				 new TableLayoutConstraints(5, currRow, 5, currRow,
						 fieldLayout[FIELD][0], fieldLayout[FIELD][1]));
			registerComponent(component2);
			lSize[currRow] = Math.max(lSize[currRow], component2.getPreferredSize().getHeight());
			setNumCols(5);
		}

		if (component3 != null) {
			this.add(component3,
				 new TableLayoutConstraints(7, currRow, 7, currRow,
						fieldLayout[LAST][0], fieldLayout[LAST][1]));
			registerComponent(component3);

			lSize[currRow] = Math.max(lSize[currRow], component3.getPreferredSize().getHeight());
			setNumCols(7);
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
	        addLine(prompt,  component,  component2);
	    } else {
	        double pad = (height - hGaps[FIELD_HEIGHT_INDEX]) / 2;

	        currRow += 1;
            lSize[currRow + 1]     = pad;

	        addLine(prompt, component);

	        setNumCols(5);
	        this.add(component2,
	                new TableLayoutConstraints(5, currRow - 1, 5, currRow + 1,
	                        fieldLayout[LAST][0], fieldLayout[LAST][1]));
	        registerComponent(component2);

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
	public void registerComponent(JComponent component) {

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
	public final BasePanel addComponent(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 JTable table) {

	    addComponent(startCol, endCol,
				 height, gap,
				 hPosition, vPosition,
				 new JScrollPane(table));
		registerComponent(table);

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
	public final BasePanel addComponent(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 JComponent component) {
		return addComponent(startCol, endCol, height, gap, hPosition, vPosition, true, component);
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
	public final BasePanel addComponent(int startCol, int endCol,
							 double height, double gap,
							 int hPosition, int vPosition,
							 boolean incRow,
							 JComponent component) {
		setNumCols(endCol);

		if (incRow) {
			incRow(height, gap);
		}
		add(component, new TableLayoutConstraints(startCol, currRow,
												  endCol, currRow,
												  hPosition, vPosition));
		registerComponent(component);

		return this;
	}


	/**
	 * Adds a Menu item to the panel
	 *
	 * @param prompt Menu Prompt
	 * @param btn    Menu Button to be added to the panel
	 */
	public final BasePanel addMenuItem(String prompt, JButton btn) {

		incRow();

		add(btn, new TableLayoutConstraints(1, currRow, 1, currRow,
		        BasePanel.RIGHT, BasePanel.TOP));
		registerComponent(btn);

		JLabel promptLbl = new JLabel(prompt);
		//promptLbl.setHorizontalAlignment(JLabel.RIGHT);

		setNumCols(3);
		this.add(promptLbl,
			new TableLayoutConstraints(3, currRow, 3, currRow,
					fieldLayout[FIELD][0], fieldLayout[FIELD][1]));

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
	public final BasePanel addButton(boolean incRow, int col, int height, JButton btn) {


		if (col < 4) {
			int c = col * 2 - 1;

			if (incRow) {
				incRow(height, hGaps[GAP_HEIGHT_INDEX]);
			} else if (height > 0) {
				lSize[currRow] = height;
			}

			setNumCols(col * 2 - 1);

//			System.out.println("Add Btn >> " + col + " " + oneField + "  "
//					+  c + " " + currRow);

			add(btn, new TableLayoutConstraints(c, currRow, c, currRow,
			        BasePanel.RIGHT, BasePanel.TOP));
			registerComponent(btn);
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
	public final JButton addIconButton(boolean incRow, int col, ImageIcon icon) {
		JButton btn = new JButton("", icon);
		addButton(incRow, col, icon.getIconHeight() + 5, btn);

		return btn;
	}



	/**
	 * Set the Horizontal Gap between the current Field and the Next Field
	 *
	 * @param gap Line gap
	 */
	public final BasePanel setGap(double gap) {
		lSize[currRow + 1] = gap;
		return this;
	}


	/**
	 * Set Height of the current Field
	 *
	 * @param height Required Height
	 */
	public final BasePanel setHeight(double height) {
		lSize[currRow] = height;
		return this;
	}


	/**
	 * Add a message Field to the panel
	 *
	 * @param msg message Field
	 */
	public final BasePanel addMessage(JComponent msg) {

		incRow(hGaps[FIELD_HEIGHT_INDEX], 2);

		add(msg, new TableLayoutConstraints(1, currRow, numCols, currRow,
		        BasePanel.FULL, BasePanel.FULL));
		registerComponent(msg);

		if (msg.getMinimumSize().height < HEIGHT_1P6) {
			setHeight(HEIGHT_1P6);
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

		//System.out.print("done " + headingComponent==null);
		if (headingComponent != null) {
			//System.out.println(" adding");
			add(headingComponent,
					new TableLayoutConstraints(1, 0, numCols, 0,
							TableLayout.CENTER, TableLayout.FULL));
		} else if (heading != null) {
			JLabel jHeading = new JLabel(heading);
			add(jHeading,
					 new TableLayoutConstraints(1, 0, numCols, 0,
							TableLayout.CENTER, TableLayout.FULL));
		}

		if (numCols == 1) {
			double[] vG = {vGaps[0], vGaps[1], vGaps[vGaps.length - 1]};

			tLayout.setColumn(vG);
		} else if (numCols == 3) {
			double[] vG = {vGaps[0], vGaps[1], vGaps[2], vGaps[3], vGaps[vGaps.length - 1]};

			tLayout.setColumn(vG);
		} else if (numCols == 7) {
			double[] vG = {vGaps[0], vGaps[1], vGaps[2], vGaps[3],
					5, TableLayout.PREFERRED,
					vGaps[4], vGaps[5], vGaps[vGaps.length - 1]};

			tLayout.setColumn(vG);
		} else {
			tLayout.setColumn(vGaps);
		}
		tLayout.setRow(hGap);

		toBeDone = false;
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
		}
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
		}
	}

	/**
	 * @param nameComponents the nameComponents to set
	 */
	public void setNameComponents(boolean nameComponents) {
		this.nameComponents = nameComponents;
	}

}
