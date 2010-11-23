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
package net.sf.RecordEditor.utils.filter;


import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.jibx.compare.FieldTest;
import net.sf.RecordEditor.jibx.compare.Layout;
import net.sf.RecordEditor.jibx.compare.Record;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;



/**
 * This class holds Filter details ~ i.e. Description of which
 * Lines should be displayed on a Filtered-View.
 *
 * @author Bruce Martin
 * @version 0.53
 */
public class FilterDetails {

    private static final String[] LAYOUT_COLUMN_HEADINGS = {"Record", "Include"};
    private static final String[] FIELD_COLUMN_HEADINGS  = {"Field", "Include"};

    private ComboOption[] recordOptions;
    private static final int INCLUDE_INDEX  = 1;
    private static final int SEQUENCE_INDEX = 1;

    private final AbstractLayoutDetails layout;
    private AbstractLayoutDetails layout2;

    private int[] recordNo;
    private int[][] fields;

    private int layoutIndex = 0;

    private JTextField messageFld = new JTextField();

    private FilterFieldList filterFields;

    private FilterFieldList filterFieldsL2 = null;
    private FilterFieldList filterFieldsL2a;
    private FieldList fieldList;
    
    private boolean twoLayouts = false;


    /**
     * Create filter details
     *
     * @param group detail group
     */
    public FilterDetails(final AbstractLayoutDetails group) {
        super();
        layout = group;
        
        init();
    }
    
    private void init() {
        int count = layout.getRecordCount();
        
        recordNo = new int[count];
        fields = new int[count][];
        filterFields = new FilterFieldList(layout);
        
        for (int i = 0; i < count; i++) {
        	recordNo[i] = 0;

        	fields[i]   = null;
        }
    }


    public ComboOption[] getRecordOptions() {
		return recordOptions;
	}

	/**
     * get a field mapping
     *
     * @return field mapping
     */
    public final int[][] getFieldMap() {
        int i, j, k, count;
        int[][] ret = null;

//        for (i = 0; i < positions.length; i++) {
//            if (positions[i] != null) {

        for (i = 0; i < fields.length; i++) {
            if (fields[i] != null) {
                count = 0;
//                for (j = 0; j < positions[i].length; j++) {
//                    if (positions[i][j] == null || positions[i][j].booleanValue()) {
                for (j = 0; j < fields[i].length; j++) {
                    if (fields[i][j] >= 0) {
                        count++;
                    }
                }

                if (count > 0) {
                    if (ret == null) {
                       // ret = new int[positions.length][];
                    	ret = new int[fields.length][];
                    }

                    ret[i] = new int[count];

                    k = 0;
//                  for (j = 0; j < positions[i].length; j++) {
//                      if (positions[i][j] == null || positions[i][j].booleanValue()) {
                    for (j = 0; j < fields[i].length; j++) {
                         if (fields[i][j] >= 0) {
                            ret[i][k++] = j;
                        }
                    }
                }
            }
        }

        return ret;
    }


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


    /**
     * Get Table model for displaying the record list
     *
     * @return Record Table Model
     */
    public FilterFieldList getFilterFieldListMdl() {
         return filterFields;
    }

    
    public DefaultComboBoxModel getComboModelLayout2() {
 
    	return  filterFieldsL2.getFieldModel();
    }
    
    public DefaultComboBoxModel getComboModelLayout2a() {

    	return filterFieldsL2a.getFieldModel();
    }

    /**
     * get layout index
     *
     * @return layout index
     */
    public int getLayoutIndex() {
        return layoutIndex;
    }


    /**
     * set layout index
     *
     * @param pLayoutIndex new layout index
     */
    public void setLayoutIndex(int pLayoutIndex) {

        filterFields.setLayoutIndex(pLayoutIndex);
        if (filterFieldsL2 != null) {
        	//System.out.println("Setting 2nd index " + pLayoutIndex + " " + recordNo[pLayoutIndex]);
        	filterFieldsL2.setLayoutIndex(recordNo[pLayoutIndex]);
        	filterFieldsL2a.setLayoutIndex(recordNo[pLayoutIndex]);
        }

        this.layoutIndex = pLayoutIndex;
    }


    /**
     * get include status
     *
     * @param index Layout to check
     *
     * @return wether to include this record type
     */
    public final boolean isInclude(int index) {
       
    	if (index < 0 || index >= recordNo.length) {
            return false;
        }

        return recordNo[index] >= 0;
    }


    /**
     * get the external XML Layout interface
     * @return external XML Layout interface
     */
    public final Layout getExternalLayout() {
    	int j;
		Layout tmpLayoutSelection = new net.sf.RecordEditor.jibx.compare.Layout();
		Record rec;
		boolean allFields;
		boolean allSelected = true;
		AbstractRecordDetail recordDetail;
		
//		System.out.println("field Get 1 -Layout Name: " + values.getLayoutDetails().name);
		
		int[][] fieldInc = getFieldMap();
		
		tmpLayoutSelection.name = layout.getLayoutName();
		for (int i =0; i < layout.getRecordCount(); i++) {
			if (isInclude(i)) {
				rec = new net.sf.RecordEditor.jibx.compare.Record();
				
				recordDetail = layout.getRecord(i);
				rec.name = recordDetail.getRecordName();
				
				allFields = ! twoLayouts;
				if (allFields && fieldInc != null && fieldInc[i] != null) {
					allFields = fieldInc[i].length == recordDetail.getFieldCount();
					for (j = 0; j < recordDetail.getFieldCount() && allFields; j++) {
						if ((fieldInc[i][j] != j)) {
							allFields = false;
						}
					}
				}
				
				if (! allFields) {
					allSelected = false;
					if (fieldInc != null && i < fieldInc.length) {
						rec.fields = new String[fieldInc[i].length];
						for (j = 0; j < fieldInc[i].length; j++) {
							//rec.fields[j]= recordDetail.getField(fieldInc[i][j]).getName();
							rec.fields[j]= recordDetail.getField(layout.getAdjFieldNumber(i, fieldInc[i][j])).getName();
						}
					} else {
						rec.fields = new String[recordDetail.getFieldCount()];
						for (j = 0; j < rec.fields.length; j++) {
							rec.fields[j]= recordDetail.getField(j).getName();
						}
					}
				}
				
				if (! twoLayouts) {
					FilterField filterFld;
					FieldTest test;
					rec.fieldTest = new ArrayList<FieldTest>(FilterFieldList.NUMBER_FIELD_FILTER_ROWS); 
					for (j = 0; j < FilterFieldList.NUMBER_FIELD_FILTER_ROWS; j++) {
						filterFld = filterFields.getFilterField(i, j);
						if (filterFld.getFieldNumber() >= 0) {
							test = new net.sf.RecordEditor.jibx.compare.FieldTest();
							test.fieldName = recordDetail.getField(filterFld.getFieldNumber()).getName();
							test.operator  = Compare.getOperatorAsString(filterFld.getOperator());
							test.value     = filterFld.getValue();
							rec.fieldTest.add(test);
							allSelected = false;
						}
					}
				}
				
				tmpLayoutSelection.getRecords().add(rec);
			} else {
				allSelected = false;
			}
		}
		

		if (allSelected) {
			tmpLayoutSelection.records = null;
		}
		
		return tmpLayoutSelection;
    }


    /**
     * get the external XML Layout interface
     * @return external XML Layout interface
     */
    public final Layout getExternalLayout2() {
    	int j, idx;
		Layout tmpLayoutSelection = new net.sf.RecordEditor.jibx.compare.Layout();
		Record rec;
		AbstractRecordDetail recordDetail;
		
//		System.out.println("field Get 1 -Layout Name: " + values.getLayoutDetails().name);
		
		int[][] fieldInc = getFieldMap();
		
		tmpLayoutSelection.name = layout2.getLayoutName();
		for (int i =0; i < layout.getRecordCount(); i++) {
			if (isInclude(i) && fieldInc != null && fieldInc[i] != null) {
				rec = new net.sf.RecordEditor.jibx.compare.Record();
				
				idx = recordNo[i];
				
				recordDetail = layout2.getRecord(idx);
				rec.name = recordDetail.getRecordName();
				
				rec.fields = new String[fieldInc[i].length];
				for (j = 0; j < fieldInc[i].length; j++) {
					rec.fields[j]= recordDetail.getField(fields[i][fieldInc[i][j]]).getName();
//					System.out.println("===))) " + idx + " " + j + " " + fieldInc[i][j]
//					        + " " + fields[i][fieldInc[i][j]]
//							+ " " + recordDetail.getRecordName() + ": " + rec.fields[j]);
				}
				
				tmpLayoutSelection.getRecords().add(rec);
			}
		}
		
		return tmpLayoutSelection;
    }
   
    /**
     * Set Filter based on the external XML Layout interface
     * @param values external XML Layout interface to be used to update the filter
     */
    public final void updateFromExternalLayout(Layout values) {
    	int i, j, k, idx;
    	Record rec;
    	
		if (values.records == null 
		||  values.records.size() == 0) {
			init();
		} else {
			for (i = 0; i < recordNo.length; i++) {
				String s = layout.getRecord(i).getRecordName();
				recordNo[i] = -1;
				for (j = 0; j < values.records.size(); j++) {
					rec = values.records.get(j);
					if (rec.name.equalsIgnoreCase(s)) {
						recordNo[i] = 0;
						if (rec.fields == null || rec.fields.length == 0) {
							fields[i] = null;
						} else {							
							initFieldRow(i);
							
                            for (k = 0; k < rec.fields.length; k++) {
                            	idx = layout.getRecord(i).getFieldIndex(rec.fields[k]);
                            	fields[i][idx] = 0;
                            }
						}
						
						if (rec.fieldTest != null && rec.fieldTest.size() > 0) {
							FilterField filterFld;
							AbstractRecordDetail recDtl = layout.getRecord(i);
							FieldTest tst;
							for (k = 0; k < rec.fieldTest.size(); k++) {
								tst = rec.fieldTest.get(k);
								filterFld = new FilterField();
								filterFld.setFieldNumber(recDtl.getFieldIndex(tst.fieldName));
								filterFld.setOperator(Compare.getOperator(tst.operator));
								filterFld.setValue(tst.value);

								filterFields.setFilterField(i, k, filterFld);
							}
						}
						
						break;
					}
				}
			}
			
		}
    }

    private void initFieldRow(int row) {

        int len = layout.getRecord(row).getFieldCount();

        fields[row] = new int[len];
		
        for (int k = 0; k < len; k++) {
        	fields[row][k] = -1;
        }

    }

	/**
	 * @param twoLayouts the isTwoLayouts to set
	 */
	public final void set2Layouts(boolean is2layouts) {
		twoLayouts = is2layouts;
	}

	/**
	 * Set details for the second layout
	 * @param dtl second layout details
	 * @param values2 external layout selection details
	 */
	public final void set2ndLayout(AbstractLayoutDetails dtl) {
		this.twoLayouts = true;
		
		if (layout2 != dtl && dtl != null) {
			layout2 = dtl;
		
			filterFieldsL2 = new FilterFieldList(layout2);
			filterFieldsL2a = new FilterFieldList(layout2);
						
		    recordOptions = new ComboOption[layout2.getRecordCount()+1];
	        recordOptions[0] = new ComboOption(Constants.NULL_INTEGER, " ");
	        for (int i = 1; i < recordOptions.length; i++) {
	        	recordOptions[i] = new ComboOption(i-1, layout2.getRecord(i-1).getRecordName());
	        }

		}
	}
	
	public final void set2ndLayout(AbstractLayoutDetails dtl, Layout values1, Layout values2) {
		//TODO 2nd layout
		set2ndLayout(dtl);
		
		if (dtl.getRecordCount() > 0) {
			int i, j, idx1, idx2, end, fIdx1, fIdx2, ii;
			Record recDiff1, recDiff2;
			AbstractRecordDetail rec1, rec2;
			for ( i = 0; i < recordNo.length; i++) {
				recordNo[i] = -1;
			}
			if (values2.records == null || values2.records.size() == 0) {
				for (i = 0; i < recordNo.length; i++) {
					setUpRecord(i);
				}
			} else {
				for (i = 0; i < values1.records.size(); i++) {
					try {
						recDiff1 = values1.records.get(i);
						recDiff2 = values2.records.get(i);
						idx1 = layout.getRecordIndex(recDiff1.name);
						idx2 = layout2.getRecordIndex(recDiff2.name);
						if (idx1 >= 0 && idx2 >= 0) {
							recordNo[idx1] = idx2;
							if (recDiff1.fields == null || recDiff1.fields.length == 0
							||  recDiff2.fields == null || recDiff2.fields.length == 0) {
								setUpRecord(idx1);
							} else {
								end = Math.min(recDiff1.fields.length, recDiff2.fields.length);
								rec1 = layout.getRecord(idx1);
								rec2 = layout2.getRecord(idx2);
								
								fields[idx1] = getInitialisedArray(rec1.getFieldCount());
								for (j = 0; j < end; j++) {
									fIdx1 = rec1.getFieldIndex(recDiff1.fields[j]);
									fIdx2 = rec2.getFieldIndex(recDiff2.fields[j]);
									ii = layout.getUnAdjFieldNumber(idx1, fIdx1);
									if (fIdx1 >= 0 && ii >= 0) {
										fields[idx1][ii] = fIdx2;
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void setUpRecord(int i) {
		String l1, l2;//, f1, f2;
		int j;
//		RecordDetail rec1, rec2;
		
//		rec1 = layout.getRecord(i);
		l1 = layout.getRecord(i).getRecordName().toLowerCase();
		
		for (j = 0; j < layout2.getRecordCount(); j++) {
			l2 = layout2.getRecord(j).getRecordName().toLowerCase();
			//System.out.println("-->> " + i + " " + j + " >" + l1 + "<   >" + l2 + "<");
			if (l1.indexOf(l2) >= 0 || l2.indexOf(l1) >= 0) {
				recordNo[i] = j;
				updateRecordsFields(i);
//				rec2 = layout2.getRecord(j);
//				for ( k = 0; k < rec1.getFieldCount(); k++) {
//					f1 = rec1.getField(k).getName().toLowerCase();
//					if (! "filler".equals(f1)) {
//						for (l = 0; l < rec2.getFieldCount(); l++) {
//							f2 = rec2.getField(l).getName().toLowerCase();
//							if (f1.indexOf(f2) >= 0 || f2.indexOf(f1) >= 0) {
//								if (fields[i] == null) {
//									fields[i] = getInitialisedArray(rec1.getFieldCount());
//								}
//								fields[i][k] = l;
//								break;
//							}
//						}
//					}
//				}
				break;
			}
		}	
	}
	
	//TODO setUpRecord
	private boolean updateRecordsFields(int rowIndex) {

		int i, j, k;
		String s;
		boolean tableUpdated = false;
		AbstractRecordDetail rec1 = layout.getRecord(rowIndex);
   		AbstractRecordDetail rec2 = layout2.getRecord(recordNo[rowIndex]);
   		//System.out.println();
   		
   		if (rec2 != null) {
        	//System.out.println("== " + rec1.getRecordName() + "   "+ rec2.getRecordName());
   		
        	String[] names = new String[rec2.getFieldCount()];
    		for (i = 0; i < rec2.getFieldCount(); i++) {
    			names[i] = standardiseName(rec2.getField(i).getName());
    		}
        		
        	for (i = 0; i < rec1.getFieldCount(); i++) {
    			//idx = rec2.getFieldIndex(rec1.getField(i).getName());
    			k = layout.getAdjFieldNumber(rowIndex, i);
    			s = standardiseName(rec1.getField(k).getName());
        		for (j = 0; j < names.length; j++) {
        			if (names[j].equals(s)) {
        				//System.out.println("    ~~~ "  + i + " ~ " + k + " " + s + " " + j);
        				if (fields[rowIndex] == null) {
        					initFieldRow(rowIndex);
        				}
        				fields[rowIndex][i] = j;
        				tableUpdated = true;
        				break;
        			}
        		}
    		}

       	}
       	return tableUpdated;
	}

	
	private int[] getInitialisedArray(int size) {
		int[] ret = new int[size];
		
		for (int i = 0; i < size; i++) {
			ret[i] = -1;
		}
	
		return ret;
	}

	
    /**
     * Table model to display records
     *
     * @author Bruce Martin
     *
     */
    private class LayoutList extends AbstractTableModel {


        /**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public int getColumnCount() {
            return LAYOUT_COLUMN_HEADINGS.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
        	if (twoLayouts && columnIndex == 1) {
        		return "Equivalent Record";
        	}
            return LAYOUT_COLUMN_HEADINGS[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#getRowCount
         */
        public int getRowCount() {
             return layout.getRecordCount();
        }


        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
        	
            if (columnIndex == INCLUDE_INDEX) {
	        	if (twoLayouts) {
	        		//return Integer.valueOf(recordNo[rowIndex]);
	        		if (recordNo[rowIndex] < 0) {
	        			return recordOptions[0];
	        		}
	        		return recordOptions[recordNo[rowIndex] + 1];
	        	}
        	
                return Boolean.valueOf(recordNo[rowIndex] >= 0);
            }
            return layout.getRecord(rowIndex).getRecordName();
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == INCLUDE_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	
//        	System.out.println("~~>>> " + rowIndex + " " + aValue + " " + aValue.getClass()
//        			+ " " + (aValue instanceof Integer));

        	//TODO Set fields 1
           	recordNo[rowIndex] = Constants.NULL_INTEGER;

        	if (aValue == null) {
        		// Do Nothing
        	} else if (aValue instanceof Integer) {
        		recordNo[rowIndex] = ((Integer) aValue).intValue();
        		//System.out.print("   --- " +  (filterFieldsL2 != null));
        		if (filterFieldsL2 != null) {
    	        	filterFieldsL2.setLayoutIndex(recordNo[rowIndex]);
    	        	filterFieldsL2a.setLayoutIndex(recordNo[rowIndex]);
    	        	
    	        	//System.out.print("  -  " +  (fields[rowIndex] == null) + " ");
    	        	if (fields[rowIndex] == null) {
 	    	        	if (updateRecordsFields(rowIndex)) {
	    	        		fieldList.fireTableDataChanged();
	    	        	}
    	        	} else if (recordNo[rowIndex] < 0) {
    	        		fields[rowIndex] = null;
    	        		fieldList.fireTableDataChanged();
    	        	}
    	        }
        		//System.out.println();

           	} else if (aValue instanceof ComboOption) {
        		recordNo[rowIndex] = ((ComboOption) aValue).index;
        		//System.out.print("   --- " +  (filterFieldsL2 != null));
        		if (filterFieldsL2 != null) {
    	        	filterFieldsL2.setLayoutIndex(recordNo[rowIndex]);
    	        	filterFieldsL2a.setLayoutIndex(recordNo[rowIndex]);
    	        	
    	        	//System.out.print("  -  " +  (fields[rowIndex] == null) + " ");
    	        	if (fields[rowIndex] == null) {
 	    	        	if (updateRecordsFields(rowIndex)) {
	    	        		fieldList.fireTableDataChanged();
	    	        	}
    	        	} else if (recordNo[rowIndex] < 0) {
    	        		fields[rowIndex] = null;
    	        		fieldList.fireTableDataChanged();
    	        	}
    	        }
        		//System.out.println();

        	} else if (((Boolean) aValue).booleanValue()) {
            	recordNo[rowIndex] = 0;
            } 
        }
     }

	
	private String standardiseName(String name) {
		if (name == null) {
			return null;
		}
		StringBuffer b = new StringBuffer(name.toLowerCase());
		char c;
		
		for (int i = name.length() -1; i >= 0; i--) {
			c = b.charAt(i);
			if (c == ' ' || c == '-' || c == '_') {
				b.deleteCharAt(i);
			}
		}

		return b.toString();
	}

    /**
     * This table model displays field list
     *
     * @author Bruce Martin
     *
     */
    private class FieldList extends AbstractTableModel {

        /**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public int getColumnCount() {
            return FIELD_COLUMN_HEADINGS.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
        	if (twoLayouts && columnIndex == 1) {
        		return "Equivalent Field";
        	}
            return FIELD_COLUMN_HEADINGS[columnIndex];
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

        	if (columnIndex == SEQUENCE_INDEX) {
        		if (twoLayouts) {
//        			System.out.println(" ===>> " + layoutIndex + " " + (fields[layoutIndex] == null)
//        					+ " " + recordNo[layoutIndex]);
        			if (fields[layoutIndex] == null || fields[layoutIndex][rowIndex] < 0
        			||  recordNo[layoutIndex] < 0) {
        				return "";
        			}
        			return layout2.getRecord(recordNo[layoutIndex])
        						.getField(fields[layoutIndex][rowIndex]).getName();
        		}
//                if (positions[layoutIndex] == null
//                || positions[layoutIndex][rowIndex] == null) {
//                    return Boolean.TRUE;
//                }
//                return positions[layoutIndex][rowIndex];
        		if (fields[layoutIndex] == null
        		||  fields[layoutIndex][rowIndex] >= 0) {
        			return Boolean.TRUE;
        		}
                return Boolean.FALSE;
            }
            return layout.getAdjField(layoutIndex, rowIndex).getName();
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == SEQUENCE_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	boolean isDefaultValue;
        	int fieldNo = -1;
        	int defaultVal = 0;
        	
        	
       		if (twoLayouts) {
       			if (aValue != null && ! "".equals(aValue) 
       			&& layoutIndex >= 0 && layoutIndex < recordNo.length && recordNo[layoutIndex] >= 0) {
       				int idx = recordNo[layoutIndex];       				
//					System.out.print("~~>> " + rowIndex + " " + idx + " " + (layout2 == null));
//      				System.out.print(" " + (layout2.getRecord(idx) == null));
      				fieldNo = layout2.getRecord(idx).getFieldIndex(aValue.toString());
//      				System.out.println(" " + aValue + " " + fieldNo
//      						+ " " + idx
//      						+ " " + layout2.getLayoutName()
//      						+ " " + layout2.getRecord(idx).getRecordName());
       			}
       			isDefaultValue = fieldNo < 0;
       			defaultVal = -1;
       		} else {
       			isDefaultValue =  aValue == null
            				 || (aValue.getClass() != Boolean.class)
            				 || ((Boolean) aValue).booleanValue();

       			if (isDefaultValue) {
       				fieldNo = 0;
       			}
       		}
       		
            if (!(fields[layoutIndex] == null && isDefaultValue)) {
                if (isDefaultValue) {
                    fields[layoutIndex][rowIndex] = fieldNo;
                } else  {
                    try {
                        if (fields[layoutIndex] == null) {
                            int j, len;

                            len = layout.getRecord(layoutIndex).getFieldCount();
                            fields[layoutIndex] = new int[len];
                            for (j = 0; j < len; j++) {
                                fields[layoutIndex][j] = defaultVal;
                            }
                        }

                        fields[layoutIndex][rowIndex] = fieldNo;
                    } catch (Exception e) {
                        messageFld.setText("Invalid Field Include: " + e.getMessage());
                    }
                }
            }
        }
    }
}
