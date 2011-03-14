/*
 * @Author Bruce Martin
 * Created on 13/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.record.types;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.display.array.ArrayRender;
import net.sf.RecordEditor.edit.display.array.ArrayTableEditor;
import net.sf.RecordEditor.record.format.CellFormat;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.filter.ColumnMappingInterface;
import net.sf.RecordEditor.utils.swing.ComboBoxRenderAdapter;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.TableCellEditorWithDefault;
import net.sf.RecordEditor.utils.swing.Combo.ComboItemEditor;
import net.sf.RecordEditor.utils.swing.Combo.ComboItemRender;
import net.sf.RecordEditor.utils.swing.Combo.ComboModelSupplier;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class RecordFormats {

    private static final int STATUS_DOES_NOT_EXIST = -1;
    private static final int STATUS_UNKOWN         =  0;

	private int[] widths;
	private CellFormat[] fieldFormats;
	private int rendorStatus = STATUS_UNKOWN,
				editorStatus = STATUS_UNKOWN;

	private int maxHeight = Type.NULL_INT;


	private final AbstractRecordDetail recordDescription;
	private final AbstractLayoutDetails layout;
	private final int recordIdx;
	private final ColumnMappingInterface mapping;

    /**
     *
     */
    public RecordFormats(final ColumnMappingInterface columnMapping, final AbstractLayoutDetails recordLayout, final int recordIndex) {
        super();

		mapping = columnMapping;
		layout  = recordLayout;
		recordIdx = recordIndex;
		recordDescription = layout.getRecord(recordIdx);

		allocateArrays();
    }

    /**
     * Get alls the rendors
     *
     * @return Returns the cellRender.
     */
    public TableCellRenderer[] getCellRenders() {

        TableCellRenderer[] cellRenders = null;
        if (rendorStatus != STATUS_DOES_NOT_EXIST) {
            int j, idx;
            FieldDetail fieldDef;
            boolean foundRendor = false;

            cellRenders = new TableCellRenderer[recordDescription.getFieldCount()];

            for (j = 0; j < cellRenders.length; j++) {
                cellRenders[j] = null;
                idx = mapping.getRealColumn(recordIdx, j);
                
                fieldDef = recordDescription.getField(idx);
                if (fieldDef.getType() == Type.ftXmlNameTag) {
                	cellRenders[j] = new ComboBoxRenderAdapter(
                			new LayoutCombo(layout, false, true));
                } else if (fieldDef.getType() == Type.ftArrayField) {
                	cellRenders[j] = new ArrayRender();
                } else if (fieldDef.getType() == Type.ftComboItemField 
                		&&  fieldDef instanceof ComboModelSupplier) {
                	cellRenders[j] = new ComboItemRender(((ComboModelSupplier) fieldDef).getComboModel());
                } else if (idx < fieldFormats.length && fieldFormats[idx] != null) {
                	try {
                		cellRenders[j] = fieldFormats[idx]
                		               .getTableCellRenderer(recordDescription.getField(idx));
                	} catch (Exception e) {
                		Common.logMsg("Can not create Rendor for field: " + recordDescription.getField(idx).getName(), e);
                	}
                }
                
//                if (cellRenders[j]  != null) {
//                	System.out.println("## Got Render " + j 
//                			+ " " + recordDescription.getField(idx).getName()
//                			+ " " + recordDescription.getField(idx).getType()
//                			+ " " + cellRenders[j].getClass().getName());
//                } else {
//
//                	System.out.println("## No  Render " + j 
//                			+ " " + recordDescription.getField(idx).getName()
//                			+ " " + recordDescription.getField(idx).getType()
//                	);
//                    
//                }
                foundRendor |= (cellRenders[j] != null);
            }

            if (! foundRendor) {
                cellRenders = null;
                rendorStatus = STATUS_DOES_NOT_EXIST;
            }
        }
        return cellRenders; // (TableCellRenderer[]) cellRenders.clone();
    }


    /**
     * return all editors
     * @return Returns the cellEditors.
     */
    public TableCellEditor[] getCellEditors() {
        TableCellEditor[] cellEditor = null;
        if (editorStatus != STATUS_DOES_NOT_EXIST) {
            int j, idx;
            FieldDetail fieldDef;
            boolean foundRendor = false;

            cellEditor = new TableCellEditor[recordDescription.getFieldCount()];

            for (j = 0; j < cellEditor.length; j++) {
                cellEditor[j] = null;
                idx = mapping.getRealColumn(recordIdx, j);
                //System.out.print("Mapping ~~> " + j + " " + idx 
                //		+ " " + (recordDescription.getField(idx).getType() == Type.ftXmlNameTag));

                fieldDef = recordDescription.getField(idx);
                if (fieldDef.getType() == Type.ftXmlNameTag) {
                	cellEditor[j] = new DefaultCellEditor(
                			new LayoutCombo(layout, false, true));
                } else if (fieldDef.getType() == Type.ftArrayField) {
                	cellEditor[j] = new ArrayTableEditor();
                } else if (fieldDef.getType() == Type.ftComboItemField
                		&&  fieldDef instanceof ComboModelSupplier) {
                	cellEditor[j] = new ComboItemEditor(((ComboModelSupplier) fieldDef).getComboModel());
                	maxHeight = Math.max(maxHeight, SwingUtils.COMBO_TABLE_ROW_HEIGHT);
                } else if (idx < fieldFormats.length && fieldFormats[idx] != null) {
                	try {
                		cellEditor[j] = fieldFormats[idx]
                		               .getTableCellEditor(recordDescription.getField(idx));
                		//System.out.print(" found ");
                	} catch (Exception e) {
                	}
                } 
                if (cellEditor[j] == null && fieldDef.getDefaultValue() != null) {
                	cellEditor[j] = new TableCellEditorWithDefault(fieldDef.getDefaultValue());
                }
                //System.out.println();
                foundRendor |= (cellEditor[j] != null);
            }

            if (! foundRendor) {
                cellEditor = null;
                editorStatus = STATUS_DOES_NOT_EXIST;
            }
        }
        return cellEditor; // (TableCellEditor[]) cellEditors.clone();
    }
    
    
    /**
     * Check if the format has changed
     * @return wether the format has changed
     */
    public boolean hasTheFormatChanged() {
    	boolean ret = fieldFormats.length != recordDescription.getFieldCount();
    	
    	if (ret) {
    		allocateArrays();
    	}
    	
    	return ret;
    }

    
    private void allocateArrays() {
    	boolean foundWidth = false;
    	ReTypeManger manager = ReTypeManger.getInstance();
		fieldFormats = new CellFormat[recordDescription.getFieldCount()];

		widths = new int[recordDescription.getFieldCount()];
        for (int j = 0; j < widths.length; j++) {
        	widths[j] = Constants.NULL_INTEGER;

            try {
             	fieldFormats[j] = manager.getFormat(recordDescription.getField(j).getFormat());

            	if (fieldFormats[j] == null) {
            		fieldFormats[j] = manager.getTypeFormat(recordDescription.getField(j).getType());
            	}

            	if (fieldFormats[j] != null) {
            		maxHeight = Math.max(maxHeight, fieldFormats[j].getFieldHeight());
            		widths[j] = fieldFormats[j].getFieldWidth();
            		foundWidth |= (widths[j] > 0);
            	}
            } catch (Exception e) {
            }
        }

        if (! foundWidth) {
            widths = null;
        }

    }


    /**
     * Get the maximum width of the fields (a value < 0 means
     * use the default width).
     *
     * @return Returns the widths.
     */
    public int[] getWidths() {
        return widths;
    }

    /**
     * Get the maximum height of the fields (a value < 0 means
     * use the default height).
     *
     * @return Returns the maxHeight.
     */
    public int getMaxHeight() {
        return maxHeight;
    }

}
