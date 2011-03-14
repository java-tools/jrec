package net.sf.RecordEditor.layoutWizard;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.HexTwoLineRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.tblModels.LineArrayHexModel;
import net.sf.RecordEditor.utils.tblModels.LineArrayModel;

public class ColumnSelector {

    private static final int STANDARD_TABLE_CELL_WIDTH = SwingUtils.CHECK_BOX_HEIGHT;
    private static final int COLUMNS_TO_NUMER = 10;

    public int stdLineHeight;
    
    private AbstractTableModel stdModel, hexModel, fileModel;
    private TableCellRenderer stdRender, hexRender, tblRender;

    private RecordDefinition recordDef;
    //private ArrayList<ColumnDetails> columnList;
	private Details currentDetails;

    public final JTable fileTbl = new JTable();
    public final JCheckBox hexChk = new JCheckBox();
  
    private final JCheckBox lookMainframeZoned = new JCheckBox("Mainframe Zoned Numeric");
    private final JCheckBox lookPcZoned = new JCheckBox("PC/Unix Zoned Numeric");
    private final JCheckBox lookComp3 = new JCheckBox("Comp 3");
    private final JCheckBox lookCompBigEndian = new JCheckBox("Binary Integer (Big Endian)");
    private final JCheckBox lookCompLittleEndian = new JCheckBox("Binary Integer (Little Endian)");
   
    private final JButton clearFieldsBtn = new JButton("Clear Fields");
    private final JButton addFieldsBtn = new JButton("Add Fields");
    
    private  JTextComponent msg;
    
 	private  int[] colorInd;   
    private boolean firstTime = true;
    
    
    private ActionListener listner = new ActionListener() {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == clearFieldsBtn) {
				recordDef.columnDtls.clear();
				
				if (currentDetails.recordType == Details.RT_MULTIPLE_RECORDS) {
					recordDef.addKeyField(currentDetails, true);
				}
			} else {
				findFields();
			}
			setColorIndicator();
		}
    };
 
    
    /**
	 * @param columnsList
	 * @param details
	 * @param colorIndicator
	 */
	public ColumnSelector(JTextComponent message) {
		msg = message;
		
        fileTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        hexChk.setSelected(false);
        
        lookPcZoned.setSelected(Common.OPTIONS.searchForPcZoned.isSelected());
        clearFieldsBtn.addActionListener(listner);
        addFieldsBtn.addActionListener(listner);
	}
	
	
	
	public void addMouseListner() {
		addMouseListner(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
			    int col = fileTbl.columnAtPoint(m.getPoint());
			    columnSelected(col);
			}
		});
	}
	
	public void addMouseListner(MouseListener mouseListner) {
		fileTbl.addMouseListener(mouseListner);
		fileTbl.getTableHeader().addMouseListener(mouseListner);
		
        hexChk.addActionListener(new ActionListener() {

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				flipHex();
			}
		});
	}

	public void addFields(BasePanel pnl, int tblHeight) {
		JPanel optionPnl = new JPanel(new GridLayout(3,2));
		JPanel btnPnl = new JPanel(new GridLayout(1,1));

		optionPnl.add(lookMainframeZoned);
		optionPnl.add(lookPcZoned);
		
		optionPnl.add(lookComp3);		
		optionPnl.add(new JPanel());
		optionPnl.add(lookCompBigEndian);		
		optionPnl.add(lookCompLittleEndian);		
		
		//optionPnl.add(new JPanel());
		//optionPnl.add(new JPanel());
		btnPnl.add(clearFieldsBtn);
		btnPnl.add(addFieldsBtn);
		
		pnl.addLine("Show Hex", hexChk)
		   .setGap(BasePanel.GAP);
		pnl.addLine("Search For", optionPnl)
		   .setHeight(BasePanel.PREFERRED)
		   .setGap(BasePanel.GAP1);
		pnl.addLine("", btnPnl)
		   .setHeight(BasePanel.PREFERRED);
		pnl.addComponent(1, 5, tblHeight, 2,
		        BasePanel.FULL, BasePanel.FULL,
				fileTbl);
	}
	/**
     * User has selected a column, need to highlight it
     *
     * @param col column selected by the user
     */
    public final void columnSelected(final int col) {
        int searchCol = col + 1;
        int pos;
        int column;

        pos = -1;

        if (recordDef.columnDtls.size() == 0) {
        	recordDef.columnDtls.add(0, new ColumnDetails(searchCol, currentDetails.defaultType.intValue()));
        } else {
            pos = columnSelected_100_getColumnPosition(searchCol);

            column = recordDef.columnDtls.get(pos).start;

            if (searchCol < column) {
                recordDef.columnDtls.add(pos,
                        new ColumnDetails(searchCol, currentDetails.defaultType.intValue()));
            } else if (searchCol == column) {
                recordDef.columnDtls.remove(pos);
            } else {
                recordDef.columnDtls.add(pos + 1,
                        new ColumnDetails(searchCol, currentDetails.defaultType.intValue()));
           }
        }

        setColorIndicator();
   }

    /**
     * Get the position of the field starting at <b>col</b>
     * in the column table <b>columnsList</b>
     *
     * @param col column that we are searching for.
     *
     * @return column table index
     */
    private int columnSelected_100_getColumnPosition(int col) {
        int ret = 0;
        int size = recordDef.columnDtls.size() - 1;

        int column = -1;

        ret = 0;

        if (size > 0) {
            column = recordDef.columnDtls.get(ret).start;
            while (ret < size && (col > column)) {
                ret += 1;
                column = recordDef.columnDtls.get(ret).start;
            }
        }

        return ret;
    }


    /**
     * Set the background color for every column in the file
     * based on the fields defined in the Column table
     * <b>columnsList</b>
     */
    public final void setColorIndicator() {
        int i, j, col;
        int ind;
        int size = recordDef.columnDtls.size();

        j = 0;
        ind = 0;
        //if (size > 1) System.out.println("-- ");
        for (i = 0; i < size; i++) {
            col = Math.min(recordDef.columnDtls.get(i).start - 1, colorInd.length);
            //System.out.println("-- " + col + " " + ind);
            while (j < col) {
                colorInd[j++] = ind;
            }
            //if (size > 1) System.out.print(i + " - " + j + "~" + ind + "  ");
            ind = 1 - ind;
        }
        while (j < colorInd.length) {
            colorInd[j++] = ind;
        }
        //if (size > 1) System.out.print(colorInd.length + " " + j + "~" + ind);
        fileModel.fireTableDataChanged();
    }

    
    public final void setValues(Details detail, RecordDefinition recordDefinition, boolean findFields) 
    throws Exception {
    	boolean cp037 = "cp037".equalsIgnoreCase(detail.fontName);
        currentDetails = detail;
        recordDef = recordDefinition;
        
        hexChk.setSelected(hexChk.isSelected() || currentDetails.textPct < 40);
        lookComp3.setSelected(cp037 || Common.OPTIONS.searchForComp3.isSelected()); 
        lookCompBigEndian.setSelected(cp037 || currentDetails.textPct < 70 || Common.OPTIONS.searchForCompBigEndian.isSelected());
        lookMainframeZoned.setSelected(cp037 || Common.OPTIONS.searchForMainframeZoned.isSelected());
        lookCompLittleEndian.setSelected((! lookCompBigEndian.isSelected())
        			&& Common.OPTIONS.searchForCompLittleEndian.isSelected());

        setValues_100_SetupTable(findFields);
    }

 
    /**
     * Setup the file table for display
     *
     */
    private void setValues_100_SetupTable(boolean findFields) {

        stdRender = new TblRender();
        stdModel = new LineArrayModel(recordDef.records, 
        		currentDetails.fontName, recordDef.numRecords);
        fileModel = stdModel;
        tblRender = stdRender;
        
        hexModel = new LineArrayHexModel(recordDef.records, 
        		recordDef.numRecords);
        hexRender = new HexRender(currentDetails.fontName);
        
        colorInd = new int[fileModel.getColumnCount()];
        
        if (firstTime) {
        	firstTime = false;
	        fileTbl.setModel(fileModel);
	        stdLineHeight = fileTbl.getRowHeight();
	       
	        setupTableColumns();
        } 
       	flipHex();
       

        if (findFields 
        && Common.OPTIONS.runFieldSearchAutomatically.isSelected() 
        && recordDef.searchForFields) {
        	findFields();
        }

         setColorIndicator();
        //fileMdl.fireTableDataChanged();
    }

    
    private void findFields() {
  
    	if (lookCompBigEndian.isSelected() && lookCompLittleEndian.isSelected()) {
    		msg.setText("Can only look for one type of binary field !!!");
    		lookCompLittleEndian.setSelected(false);
    	}
    	
    	(new FieldSearch()).findFields(
    			currentDetails, recordDef,
    			lookMainframeZoned.isSelected(), lookPcZoned.isSelected(),
    			lookComp3.isSelected(),
    			lookCompBigEndian.isSelected(), lookCompLittleEndian.isSelected()
    	);

    	recordDef.searchForFields = false;
    }
    
    
    /**
     * Read the record from the file <b>detail.filename</b>
     * into the array <b>detail.records</b>
     *
     * @param detail Wizard detail record
     *
     * @throws Exception any errors generated
     */
    @SuppressWarnings("unchecked")
	public final void readFile(Details detail, RecordDefinition recordDefinition) throws Exception {
        AbstractLineReader reader = detail.getReader();
        int i = 0;
        AbstractLine l;
        byte[] s;
        
        currentDetails = detail;
        recordDef = recordDefinition;

        l = reader.read();
        while (l != null && i < recordDef.records.length) {
            s = l.getData();
 
            recordDef.records[i++] = s;
            l = reader.read();
        }
        recordDef.numRecords = i;
    }

    private void flipHex() {
    	
    	int height = stdLineHeight;
    	fileModel = stdModel;
    	tblRender = stdRender;
    	if (hexChk.isSelected()) {
    	   	fileModel = hexModel;
        	tblRender = hexRender;
        	height = (stdLineHeight + 1) * 2;
    	}
    	
    	fileTbl.setModel(fileModel);
    	fileTbl.setRowHeight(height);
    	
    	setupTableColumns();
        fileModel.fireTableDataChanged();
    }

    
    private void setupTableColumns() {
    	
       	TableColumn tc;
    	String s;
    	TableColumnModel tcm  = fileTbl.getColumnModel();
    	int cellWidth = STANDARD_TABLE_CELL_WIDTH;
    	
        for (int i = 0; i < fileModel.getColumnCount(); i++) {
            tc = tcm.getColumn(i);
            cellWidth = STANDARD_TABLE_CELL_WIDTH;
           	if (Common.NIMBUS_LAF && i >= 99 && i % 10 == 9) {
         		cellWidth += 8;
          		//System.out.print(" " + i + " " + cellWidth);
        	}
            tc.setPreferredWidth(cellWidth);
            tc.setCellRenderer(tblRender);

            //if (! Common.TEST_MODE) {
            switch ((i + 1) % COLUMNS_TO_NUMER) {
            	case (0): s = "" + ((i + 1) / COLUMNS_TO_NUMER); break;
            	case (5): s = "+";                         break;
            	default : s = " ";
            }
            tc.setHeaderValue(s);
        }
    }

    /**
	 * @return the currentDetails
	 */
	public final Details getCurrentDetails() {
		return currentDetails;
	}


	/**
     * Table cell render to give each field a different
     * alternating background colors
     *
     * @author Bruce Martin
     *
     */
    public class TblRender  implements TableCellRenderer {


    	private JTextComponent txtFld = null;

        /**
         * Table cell render to give each field a different
         * alternating background colors
         * */
        public TblRender() {
            super();
            
        		txtFld = new JTextField();
        		txtFld.setBorder(BorderFactory.createEmptyBorder());
 
           // this.setBorder(BorderFactory.createEmptyBorder());
//            this.setAlignmentX(JTextField.CENTER_ALIGNMENT);
//            this.setAlignmentY(JTextField.CENTER_ALIGNMENT);
        }


        /**
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
    	public Component getTableCellRendererComponent(
    			JTable tbl,
    			Object value,
    			boolean isSelected,
    			boolean hasFocus,
    			int row,
    			int column) {

    	    //this.setText(value.toString());
    		

//    	    System.out.print(" ~> " + row + " " + column + " " + colorInd[column] + " > " + (disp.getBackground() == Color.LIGHT_GRAY)
//    	    		 + " > " + (disp.getBackground() == Color.WHITE));
    		
    	    if (colorInd[column] == 0) {
    	    	txtFld.setBackground(Color.LIGHT_GRAY);
    	    } else {
    	    	txtFld.setBackground(Color.WHITE);
    	    }
//    	    System.out.println(" : " + (disp.getBackground() == Color.cyan)
//   	    		 + " > " + (disp.getBackground() == Color.WHITE));
    	    
 
    	    txtFld.setText(value.toString());
    	   
    	    

    	    return txtFld;
    	}
    }
  
    
    public class HexRender extends HexTwoLineRender {

		/**
		 * @param font
		 */
		public HexRender(String font) {
			super(font);
		}
    	
    	public Component getTableCellRendererComponent(
    			JTable tbl,
    			Object value,
    			boolean isSelected,
    			boolean hasFocus,
    			int row,
    			int column) {
    		
    		Component ret = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
    	    if (colorInd[column] == 0) {
    	    	ret.setBackground(Color.LIGHT_GRAY);
    	    } else {
    	    	ret.setBackground(Color.WHITE);
    	    }

    		return ret;
    	}

    }
}
