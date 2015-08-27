/*
 * @Author Bruce Martin
 * Created on 22/07/2005
 *
 * Purpose:
 * This class holds Filter details ~ i.e. Description of which
 * Lines should be displayed on a Filtered-View.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Changed to use Boolean.valueOf instead of new Boolean
 *     to reduce class creation
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - JRecord Spun off
 */
package net.sf.RecordEditor.re.script.bld;


import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.utils.lang.LangConversion;



/**
 * This class holds Filter details ~ i.e. Description of which
 * Lines should be displayed on a Filtered-View.
 *
 * @author Bruce Martin
 * @version 0.53
 */
public class ScriptBdDetails {


	public static int FT_GROUP        = 3;


    private static final String[] LAYOUT_GROUP_COLUMN_HEADINGS = LangConversion.convertColHeading(
 			"Filter Record Selection",
 			new String[] {"Record", "In Group", "Include"});
    private static final String[] LAYOUT_COLUMN_HEADINGS =
    					 {LAYOUT_GROUP_COLUMN_HEADINGS[0], LAYOUT_GROUP_COLUMN_HEADINGS[2]};

    private static final String[] FIELD_COLUMN_HEADINGS  = LangConversion.convertColHeading(
			"Script_Field_Selection",
			new String[] {"Field", "Select On", "Update"});


    private static final int INCLUDE_INDEX  = 1;

    private static final int SELECT_INDEX = 1;
    private static final int UPDATE_INDEX = 2;

	private final AbstractLayoutDetails layout;
	
	public String[] columnHeading = FIELD_COLUMN_HEADINGS.clone();

    private Boolean[] recordSelected;
    private Boolean[][][] fields;

    private int layoutIndex = 0;

    private JTextField messageFld = new JTextField();


    private FieldList fieldList;
    
    private final ScriptOption scriptOpt;




    /**
     * Create filter details
     *
     * @param group detail group
     */
    public ScriptBdDetails(final AbstractLayoutDetails group, ScriptOption opt) {
        super();
        layout = group;
        scriptOpt = opt;
 //       this.filterType = filterType;

        init();
    }

    private void init() {
        int count = layout.getRecordCount();
        Boolean recSel = count == 1;

        recordSelected = new Boolean[count];
        fields = new Boolean[count][][];
 

        for (int i = 0; i < count; i++) {
        	recordSelected[i] = recSel;

        	fields[i]   = new Boolean[layout.getRecord(i).getFieldCount()][];
        	for (int j = 0; j <fields[i].length; j++) {
        		fields[i][j] = new Boolean[2];
        		fields[i][j][0] = Boolean.FALSE;
        		fields[i][j][1] = Boolean.FALSE;
        	}
        }
        
        if (hasValue(scriptOpt.selectName)) {
        	columnHeading[SELECT_INDEX] = scriptOpt.selectName;
        }
        
        if (hasValue(scriptOpt.updateName)) {
        	columnHeading[UPDATE_INDEX] = scriptOpt.updateName;
        }
    }

    
    private boolean hasValue(String s) {
    	return s != null && s.length() > 0;
    }

//    public ComboOption[] getRecordOptions() {
//		return recordOptions;
//	}
//
//
//
    /**
     * Get Table model for displaying the record list
     *
     * @return Record Table Model
     */
    public AbstractTableModel getLayoutListMdl() {

        return new LayoutList();
    }


    /**
     * Get Message field
     *
     * @return message field
     */
    public JTextField getMessageFld() {
        return messageFld;
    }


    /**
	 * @param messageFld the messageFld to set
	 */
	protected void setMessageFld(JTextField messageFld) {
		this.messageFld = messageFld;
	}


	/**
     * Get Table model for displaying the record list
     *
     * @return Record Table Model
     */
    public AbstractTableModel getFieldListMdl() {
    	fieldList = new FieldList();
    	return fieldList;
    }

    public ScriptSchemaDtls getSchemaDetails(int source) {
    	return new ScriptSchemaDtls(layout, source, recordSelected, fields); 
    }
//
//    @SuppressWarnings("rawtypes")
//	public DefaultComboBoxModel getComboModelLayout2() {
//
//    	return  filterFieldsL2.getFieldModel();
//    }
//
//
//    /**
//     * get layout index
//     *
//     * @return layout index
//     */
//    public int getLayoutIndex() {
//        return layoutIndex;
//    }
//

    /**
     * set layout index
     *
     * @param pLayoutIndex new layout index
     */
    public void setLayoutIndex(int pLayoutIndex) {
  
        this.layoutIndex = pLayoutIndex;
    }

//
//    /**
//	 * @return the filterType
//	 */
//	public int getFilterType() {
//		return filterType;
//	}
//
//	/**
//	 * @return the groupFilter
//	 */
//	public int isNormalFilter() {
//		return filterType;
//	}
//
//	/**
//	 * @param groupFilter the groupFilter to set
//	 */
//	public void setNormalFilter(int isNormalFilter) {
//		this.filterType = isNormalFilter;
//	}
//
//	/**
//	 * @return the groupHeader
//	 */
//	public int getGroupHeader() {
//		return groupHeader;
//	}
//
//	/**
//	 * @param groupHeader the groupHeader to set
//	 */
//	public void setGroupHeader(int groupHeader) {
//		this.groupHeader = groupHeader;
//	}
//
//	/**
//	 * @return the op
//	 */
//	public int getOp() {
//		return op;
//	}

//	/**
//	 * @param op the op to set
//	 */
//	public void setOp(int op) {
//		this.op = op;
//	}
//
//	/**
//     * get include status
//     *
//     * @param index Layout to check
//     *
//     * @return wether to include this record type
//     */
//    public final boolean isInclude(int index) {
//
//    	if (index < 0 || index >= recordNo.length) {
//            return false;
//        }
//
//        return recordNo[index];
//    }
//
//    public final boolean isInGroup(int index) {
//
//    	if (inGroup == null || index < 0 || index >= inGroup.length) {
//            return false;
//        }
//
//        return inGroup[index];
//    }
//
//    /**
//     * get the external XML Layout interface
//     * @return external XML Layout interface
//     */
//
//    private void initFieldRow(int row) {
//
//        int len = layout.getRecord(row).getFieldCount();
//
//        fields[row] = new int[len];
//
//        for (int k = 0; k < len; k++) {
//        	fields[row][k] = -1;
//        }
//
//    }
//
//	
//
//	private void setUpRecord(int i) {
//		String l1, l2;//, f1, f2;
//		int j;
////		RecordDetail rec1, rec2;
//
////		rec1 = layout.getRecord(i);
//		l1 = layout.getRecord(i).getRecordName().toLowerCase();
//
//		for (j = 0; j < layout2.getRecordCount(); j++) {
//			l2 = layout2.getRecord(j).getRecordName().toLowerCase();
//			//System.out.println("-->> " + i + " " + j + " >" + l1 + "<   >" + l2 + "<");
//			if (l1.indexOf(l2) >= 0 || l2.indexOf(l1) >= 0) {
//				recordNo[i] = j;
//				updateRecordsFields(i);
////				rec2 = layout2.getRecord(j);
////				for ( k = 0; k < rec1.getFieldCount(); k++) {
////					f1 = rec1.getField(k).getName().toLowerCase();
////					if (! "filler".equals(f1)) {
////						for (l = 0; l < rec2.getFieldCount(); l++) {
////							f2 = rec2.getField(l).getName().toLowerCase();
////							if (f1.indexOf(f2) >= 0 || f2.indexOf(f1) >= 0) {
////								if (fields[i] == null) {
////									fields[i] = getInitialisedArray(rec1.getFieldCount());
////								}
////								fields[i][k] = l;
////								break;
////							}
////						}
////					}
////				}
//				break;
//			}
//		}
//	}
//
//	private boolean updateRecordsFields(int rowIndex) {
//
//		int i, j, k;
//		String s;
//		boolean tableUpdated = false;
//		AbstractRecordDetail rec1 = layout.getRecord(rowIndex);
//   		AbstractRecordDetail rec2 = layout2.getRecord(recordNo[rowIndex]);
//   		//System.out.println();
//
//   		if (rec2 != null) {
//        	//System.out.println("== " + rec1.getRecordName() + "   "+ rec2.getRecordName());
//
//        	String[] names = new String[rec2.getFieldCount()];
//    		for (i = 0; i < rec2.getFieldCount(); i++) {
//    			names[i] = standardiseName(rec2.getField(i).getName());
//    		}
//
//        	for (i = 0; i < rec1.getFieldCount(); i++) {
//    			//idx = rec2.getFieldIndex(rec1.getField(i).getName());
//    			k = layout.getAdjFieldNumber(rowIndex, i);
//    			s = standardiseName(rec1.getField(k).getName());
//        		for (j = 0; j < names.length; j++) {
//        			if (names[j].equals(s)) {
//        				//System.out.println("    ~~~ "  + i + " ~ " + k + " " + s + " " + j);
//        				if (fields[rowIndex] == null) {
//        					initFieldRow(rowIndex);
//        				}
//        				fields[rowIndex][i] = j;
//        				tableUpdated = true;
//        				break;
//        			}
//        		}
//    		}
//
//       	}
//       	return tableUpdated;
//	}
//
//
//	private int[] getInitialisedArray(int size) {
//		int[] ret = new int[size];
//
//		for (int i = 0; i < size; i++) {
//			ret[i] = -1;
//		}
//
//		return ret;
//	}
//
    /**
     * Table model to display records
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private abstract class BaseLayoutList extends AbstractTableModel {

    	private final String[] layoutColumnHeadings ;


        public BaseLayoutList(String[] layoutColumnHeadings) {
			super();
			this.layoutColumnHeadings = layoutColumnHeadings;
		}


		/**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public final int getColumnCount() {
            return layoutColumnHeadings.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public final String getColumnName(int columnIndex) {
//        	if (twoLayouts && columnIndex == 1) {
//        		return LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Equivalent Record");
//        	}
            return layoutColumnHeadings[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#getRowCount
         */
        public final int getRowCount() {
             return layout.getRecordCount();
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public final boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex >= INCLUDE_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

           	recordSelected[rowIndex] = Boolean.FALSE;

        	if (aValue == null) {
        		// Do Nothing
//        	} else if (aValue instanceof Integer) {
//        		recordNo[rowIndex] = ((Integer) aValue).intValue();
//        		//System.out.print("   --- " +  (filterFieldsL2 != null));
//         		//System.out.println();
//
//           	} else if (aValue instanceof ComboOption) {
//        		recordNo[rowIndex] = ((ComboOption) aValue).index;
//        		//System.out.print("   --- " +  (filterFieldsL2 != null));
//          		//System.out.println();

        	} else if (aValue instanceof Boolean) {
            	recordSelected[rowIndex] = (Boolean) aValue;
            	if (scriptOpt.selectOneRecord && recordSelected[rowIndex]) {
            		for (int i = 0; i < recordSelected.length; i++) {
            			if (i != rowIndex) {
            				recordSelected[rowIndex] = Boolean.FALSE;
            			}
            		}
            		
            	}
            }
        }

    }

    /**
     * Table model to display records
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private class LayoutList extends BaseLayoutList {

        public LayoutList() {
			super(LAYOUT_COLUMN_HEADINGS);
		}


        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

            if (columnIndex == INCLUDE_INDEX) {
                return recordSelected[rowIndex];
            }
            return layout.getRecord(rowIndex).getRecordName();
        }
     }
//
//    /**
//     * Table model to display records whith 2 layouts
//     *
//     * @author Bruce Martin
//     *
//     */
//    @SuppressWarnings("serial")
//	private class TwoLayoutList extends AbstractTableModel {
//
//        public TwoLayoutList() {
//			super(TWO_LAYOUT_COLUMN_HEADINGS);
//		}
//
//
//
//        /**
//         * @see javax.swing.table.TableModel#getValueAt
//         */
//        public Object getValueAt(int rowIndex, int columnIndex) {
//
//            if (columnIndex == INCLUDE_INDEX) {
//        		if (recordNo[rowIndex] < 0) {
//        			return recordOptions[0];
//        		} else if (recordNo[rowIndex] + 1 >= recordOptions.length ) {
//        			return null;
//        		}
//        		return recordOptions[recordNo[rowIndex] + 1];
//            }
//            return layout.getRecord(rowIndex).getRecordName();
//        }
//      }
//
//    /**
//     * Table model to display records whith 2 layouts
//     *
//     * @author Bruce Martin
//     *
//     */
//    @SuppressWarnings("serial")
//	private class GroupLayoutList extends BaseLayoutList {
//
//        public GroupLayoutList() {
//			super(LAYOUT_GROUP_COLUMN_HEADINGS);
//		}
//
//
//
//        /**
//         * @see javax.swing.table.TableModel#getValueAt
//         */
//        public Object getValueAt(int rowIndex, int columnIndex) {
//
//            switch (columnIndex) {
//            case IN_GROUP_INDEX:
//            	return Boolean.valueOf(inGroup[rowIndex]);
//            case GROUP_INCLUDE_INDEX:
//            	return Boolean.valueOf(recordNo[rowIndex] >= 0);
//            }
//            return layout.getRecord(rowIndex).getRecordName();
//        }
//
//
//
//		/* (non-Javadoc)
//		 * @see net.sf.RecordEditor.re.file.filter.FilterDetails.BaseLayoutList#setValueAt(java.lang.Object, int, int)
//		 */
//		@Override
//		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//			if (columnIndex == IN_GROUP_INDEX) {
//				if (aValue instanceof Boolean) {
//					inGroup[rowIndex] = ((Boolean) aValue).booleanValue();
//				}
//			} else {
//				super.setValueAt(aValue, rowIndex, columnIndex);
//			}
//		}
//
//
//      }

// ---------------------------------------------------------------------------------
//
//
//	private String standardiseName(String name) {
//		if (name == null) {
//			return null;
//		}
//		StringBuffer b = new StringBuffer(name.toLowerCase());
//		char c;
//
//		for (int i = name.length() -1; i >= 0; i--) {
//			c = b.charAt(i);
//			if (c == ' ' || c == '-' || c == '_') {
//				b.deleteCharAt(i);
//			}
//		}
//
//		return b.toString();
//	}

    /**
     * This table model displays field list
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private class FieldList extends AbstractTableModel {

        /**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public int getColumnCount() {
            return columnHeading.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
            return columnHeading[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public int getRowCount() {
			AbstractRecordDetail rec = layout.getRecord(layoutIndex);
        	if (rec == null) {
        		return 0;
        	}
            return rec.getFieldCount();
        }


        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

        	if (columnIndex == 0) {
        		return layout.getAdjField(layoutIndex, rowIndex).getName();
        	}

            return fields[layoutIndex][rowIndex][columnIndex - 1];
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex >= SELECT_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        	boolean isDefaultValue;
//        	int fieldNo = -1;
//        	int defaultVal = 0;

        	Boolean val = Boolean.FALSE;
        	if (aValue == null) {
        	} else if (aValue instanceof Boolean) {
        		val = (Boolean) aValue;
        	} else {
        		val = "true".equalsIgnoreCase(aValue.toString());
        	}

        	fields[layoutIndex][rowIndex][columnIndex - 1] = val;
   
        }
    }
}
