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
package net.sf.RecordEditor.re.file.filter;


import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.jibx.compare.FieldTest;
import net.sf.RecordEditor.jibx.compare.Layout;
import net.sf.RecordEditor.jibx.compare.Record;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;




/**
 * This class holds Filter details ~ i.e. Description of which
 * Lines should be displayed on a Filtered-View.
 *
 * @author Bruce Martin
 * @version 0.91
 */
public class FilterGroupDetails {

    private static final String[] LAYOUT_COLUMN_HEADINGS = LangConversion.convertColHeading(
			"Filter Record Selection",
			new String[] {"Record","In Group", "Include"});
    private static final String[] FIELD_COLUMN_HEADINGS  = LangConversion.convertColHeading(
			"Filter Field Selection",
			new String[] {"Field", "Include"});

    private static final int GROUP_INCLUDE_INDEX  = 1;
    private static final int INCLUDE_INDEX  = 2;
    private static final int SEQUENCE_INDEX = 1;

    @SuppressWarnings("rawtypes")
	private final AbstractLayoutDetails layout;

    private int[][] recordNo = new int[2][];
    private int[][] fields;

    private int layoutIndex = 0;

    private JTextField messageFld = new JTextField();

    private FilterFieldList filterFields;

    private FieldList fieldList;

    private int groupHeader, op;


    /**
     * Create filter details
     *
     * @param group detail group
     */
    public FilterGroupDetails(@SuppressWarnings("rawtypes") final AbstractLayoutDetails group) {
        super();
        layout = group;

        init();
    }

    private void init() {
        int count = layout.getRecordCount();

        recordNo[0] = new int[count];
        recordNo[1] = new int[count];
        fields = new int[count][];
        filterFields = new FilterFieldList(layout);

        for (int i = 0; i < count; i++) {
        	recordNo[0][i] = 0;
        	recordNo[1][i] = 0;

        	fields[i]   = null;
        }
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

        this.layoutIndex = pLayoutIndex;
    }


	/**
	 * @return the groupHeader
	 */
	public int getGroupHeader() {
		return groupHeader;
	}

	/**
	 * @param groupHeader the groupHeader to set
	 */
	public void setGroupHeader(int groupHeader) {
		this.groupHeader = groupHeader;
	}

	/**
	 * @return the op
	 */
	public int getOp() {
		return op;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(int op) {
		this.op = op;
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

        return recordNo[1][index] >= 0;
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
		@SuppressWarnings("rawtypes")
		AbstractRecordDetail recordDetail;

//		System.out.println("field Get 1 -Layout Name: " + values.getLayoutDetails().name);

		int[][] fieldInc = getFieldMap();

		tmpLayoutSelection.name = layout.getLayoutName();

		if (groupHeader >= 0 && groupHeader < layout.getRecordCount()) {
			tmpLayoutSelection.groupHeader = layout.getRecord(groupHeader).getRecordName();
		}
		tmpLayoutSelection.booleanOperator = Common.BOOLEAN_AND_STRING;
		if (op == Common.BOOLEAN_OPERATOR_OR) {
			tmpLayoutSelection.booleanOperator = Common.BOOLEAN_OR_STRING;
		}

		for (int i =0; i < layout.getRecordCount(); i++) {
			if (isInclude(i)) {
				rec = new net.sf.RecordEditor.jibx.compare.Record();

				recordDetail = layout.getRecord(i);
				rec.name = recordDetail.getRecordName();

				allFields = true;
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
					if (fieldInc != null && i < fieldInc.length && fieldInc[i] != null) {
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


				FilterField filterFld;
				FieldTest test;
				rec.fieldTest = new ArrayList<FieldTest>(FilterFieldList.NUMBER_FIELD_FILTER_ROWS);
				for (j = 0; j < FilterFieldList.NUMBER_FIELD_FILTER_ROWS; j++) {
					filterFld = filterFields.getFilterField(i, j);
					if (filterFld.getFieldNumber() >= 0) {
						test = new net.sf.RecordEditor.jibx.compare.FieldTest();
						if (filterFld.getBooleanOperator() == Common.BOOLEAN_OPERATOR_OR) {
							test.booleanOperator = "Or";
						}
						test.fieldName = recordDetail.getField(filterFld.getFieldNumber()).getName();
						test.operator  = Compare.getOperatorAsString(filterFld.getOperator());
						test.value     = filterFld.getValue();
						rec.fieldTest.add(test);
						allSelected = false;
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

    /**
     * Set Filter based on the external XML Layout interface
     * @param values external XML Layout interface to be used to update the filter
     */
    public final void updateFromExternalLayout(Layout values) {
    	int i, j, k, idx;
    	Record rec;


	    op = Common.BOOLEAN_OPERATOR_AND;

    	if (values.groupHeader != null && ! "".equals(values.groupHeader)) {
	    	groupHeader = layout.getRecordIndex(values.groupHeader);

	    	if (values.booleanOperator.equalsIgnoreCase(Common.BOOLEAN_OR_STRING)) {
	    		op = Common.BOOLEAN_OPERATOR_OR;
	    	}
    	}

		if (values.records == null
		||  values.records.size() == 0) {
			init();
		} else {
			for (i = 0; i < recordNo.length; i++) {
				String s = layout.getRecord(i).getRecordName();
				recordNo[1][i] = -1;
				for (j = 0; j < values.records.size(); j++) {
					rec = values.records.get(j);
					if (rec.name.equalsIgnoreCase(s)) {
						recordNo[1][i] = 0;
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
							@SuppressWarnings("rawtypes")
							AbstractRecordDetail recDtl = layout.getRecord(i);
							FieldTest tst;
							for (k = 0; k < rec.fieldTest.size(); k++) {
								tst = rec.fieldTest.get(k);
								filterFld = new FilterField();
								filterFld.setFieldNumber(recDtl.getFieldIndex(tst.fieldName));
								filterFld.setOperator(Compare.getOperator(tst.operator));
								filterFld.setValue(tst.value);

								if ("Or".equalsIgnoreCase(tst.booleanOperator)) {
									filterFld.setBooleanOperator(Common.BOOLEAN_OPERATOR_OR);
								}

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
    @SuppressWarnings("serial")
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

            if (columnIndex >= GROUP_INCLUDE_INDEX) {
                return Boolean.valueOf(recordNo[columnIndex - GROUP_INCLUDE_INDEX][rowIndex] >= 0);
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

        	int idx = columnIndex - GROUP_INCLUDE_INDEX;
           	recordNo[idx][rowIndex] = Constants.NULL_INTEGER;

        	if (aValue == null) {
        		// Do Nothing
        	} else if (aValue instanceof Integer) {
        		recordNo[idx][rowIndex] = ((Integer) aValue).intValue();
        		//System.out.print("   --- " +  (filterFieldsL2 != null));
         		//System.out.println();

        	} else if (((Boolean) aValue).booleanValue()) {
            	recordNo[idx][rowIndex] = 0;
            }
        }
     }



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
            return FIELD_COLUMN_HEADINGS.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
            return FIELD_COLUMN_HEADINGS[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public int getRowCount() {
        	@SuppressWarnings("rawtypes")
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


   			isDefaultValue =  aValue == null
        				 || (aValue.getClass() != Boolean.class)
        				 || ((Boolean) aValue).booleanValue();

   			if (isDefaultValue) {
   				fieldNo = 0;
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
