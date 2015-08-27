package net.sf.RecordEditor.re.editProperties;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.color.ColorGroup;
import net.sf.RecordEditor.utils.swing.color.ColorGroupManager;
import net.sf.RecordEditor.utils.swing.color.IColorGroup;

@SuppressWarnings("serial")
public class EditColors extends BaseHelpPanel {

	private static String[] SPECIAL_OPTIONS = {"Field Delimiter"};
	private static final int MAXIMUM_NUMBER_OF_ROWS = 120;
    private JEditorPane tipsPane;

    private JTextField numberOfColorsTxt = new JTextField();
    private colorTblMdl mdl = new colorTblMdl();
    private JTable colorTbl = new JTable(mdl);
    
    private String[] rowNames = new String[MAXIMUM_NUMBER_OF_ROWS];
    private Color[] colors = new Color[MAXIMUM_NUMBER_OF_ROWS];
    private Color[] backgroundColors = new Color[MAXIMUM_NUMBER_OF_ROWS];
    private int rowCount = 8;
    
    private static final String[] COLUMNS  = {"Field", "Color" };
    
    private final String propertiesName;
    private final EditParams params;
    private final IColorGroup currentColors, defaultColors;
    private final boolean canChangeSize;
    
    private final JFrame parent;
    
    public static EditColors getFieldColorEditor(JFrame parent, EditParams params) {
    	return new EditColors(
    						parent,
			    			UtMessages.FIELD_COLOR_TIP.get(), 
			    			params,
			    			Parameters.FIELD_COLORS, 
			    			true, 
			    			ColorGroupManager.getInstance().get(IColorGroup.FIELDS), 
			    			ColorGroup.getDefaultStandardColors(),
			    			null);
    }
    
    
    public static EditColors getSpecialColorEditor(JFrame parent, EditParams params) {
    	return new EditColors(
    						parent,
    						UtMessages.SPECIAL_COLOR_TIP.get(), 
			    			params,
			    			Parameters.SPECIAL_COLORS, 
			    			true, 
			    			ColorGroupManager.getInstance().get(IColorGroup.SPECIAL), 
			    			ColorGroup.getDefaultSpecialColors(),
			    			SPECIAL_OPTIONS);
    }
    

    
    private EditColors(JFrame parent, String tip, EditParams params, String propertiesName, boolean canChangeSize, IColorGroup currentColors, IColorGroup defaultColors, String[] rowNames) {
    	
    	this.propertiesName = propertiesName;
    	this.params			= params;
    	this.currentColors  = currentColors;
    	this.defaultColors  = defaultColors;
    	this.canChangeSize  = canChangeSize;
    	this.parent         = parent;
    	
    	tipsPane = new JEditorPane("text/html", tip);
    	
    	init_100_setup(rowNames);
    	init_200_layoutScreen();
    	init_300_Finalise();
    }
    
    private void init_100_setup(String[] paramRowNames) {
    	rowCount = currentColors.size();
    		
    	for (int i = rowCount; i < MAXIMUM_NUMBER_OF_ROWS; i++) {   
    		rowNames[i] = "";
    		colors[i] = Color.BLACK;
    		backgroundColors[i] = null;
    	}

    	for (int i = 0; i < rowCount; i++) {
    		rowNames[i] = "";
    		colors[i] = currentColors.getForegroundColor(i);
    		backgroundColors[i] = currentColors.getBackgroundColor(i);
    	}
    	
    	if (paramRowNames == null) {
    		for (int i = 0; i < MAXIMUM_NUMBER_OF_ROWS; i++) {    		
    			rowNames[i] = "Field " + i;
        	}
    	} else {
    		System.arraycopy(paramRowNames, 0, rowNames, 0, paramRowNames.length);
    	}    	
   }
    
     
   private void init_200_layoutScreen() {
   	
		this.addComponentRE(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
		        tipsPane);

		if (canChangeSize) {
			this.addLineRE("Number of Field Colors", numberOfColorsTxt);
		}
		
		this.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,      colorTbl);
    }
   
   private void init_300_Finalise() {
	   TableColumnModel columnModel = colorTbl.getColumnModel();
	   
	   columnModel.getColumn(1).setCellRenderer(new ColorRendor());
	   
	   numberOfColorsTxt.setText(Integer.toString(rowCount));
	   numberOfColorsTxt.addFocusListener(new FocusAdapter() {
		@Override public void focusLost(FocusEvent e) {
			try {
				int newRowCount = Math.min(MAXIMUM_NUMBER_OF_ROWS, Integer.parseInt(numberOfColorsTxt.getText()));
				String key = propertiesName + "count";
				rowCount = newRowCount;
				if (newRowCount == defaultColors.size()) {
					params.remove(key);
				} else {
					mdl.fireTableDataChanged();
					params.setProperty(key, Integer.toString(rowCount));
				}

			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			}
		}		   
	   });
	   
	   colorTbl.addMouseListener(new MouseAdapter() {
		   @Override public void mouseReleased(MouseEvent e) {
			   showStyle( colorTbl.rowAtPoint(e.getPoint()));
		   }
	   });
   }
   
   private void showStyle(int row) {
	   if (row < 0 || row >= rowCount) return;
	   
	   StyleEdit se = new StyleEdit(parent, colorTbl, rowNames[row], colors[row], backgroundColors[row]); 

	   Color newColor = se.getColor();
	   Color newBackground = se.getBackgroundColor();
	   boolean changed = false;
	 
	   if (newColor != colors[row]) {
		   saveColor( propertiesName + row, defaultColors.getForegroundColor(row), newColor);
		   colors[row] = newColor;
		   changed = true;
	   }
		 
	   if (newBackground != backgroundColors[row]) {
		   saveColor( propertiesName + "back." + row, defaultColors.getBackgroundColor(row), newBackground);
		   backgroundColors[row] = newBackground;
		   changed = true;
	   }

	   if (changed) {
		   params.writeProperties();
		   mdl.fireTableDataChanged();
	   }
   }
   
   private void saveColor(String key, Color oldColor, Color newColor) {
	   
	   if (newColor == oldColor) {
		   params.remove(key);
	   } else {
		   String s = "null";
		   if (newColor != null) {
			   int c = newColor.getRGB();
			   System.out.println("Color " + newColor.getRed() + " "+ newColor.getGreen() + " "+ newColor.getBlue() + " ");
			   s = Integer.toString(c);
		   }
		   params.setProperty(key, s);
	   }
   }
 
   private class colorTblMdl extends AbstractTableModel implements TableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return rowCount;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return COLUMNS.length;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return rowNames[rowIndex];
			}
			return "Hello World";
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMNS[column];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false; //columnIndex > 0;
		}
    }
    
    
    private class ColorRendor extends JTextField implements TableCellRenderer {

    	Color origBackGround = this.getBackground();
    	
		/* (non-Javadoc)
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			String val ="";
			if (value != null) {
				val = value.toString();
			}
			
			this.setForeground(colors[row]);
			
			if (backgroundColors[row] == null) {
				this.setBackground(origBackGround);
			} else {
				this.setBackground(backgroundColors[row]);
			}
			this.setText(val);
			return this;
		}
    	
    }
}
