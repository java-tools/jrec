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

import javax.swing.DefaultComboBoxModel;
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
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;



/**
 * This class holds Filter details ~ i.e. Description of which
 * Lines should be displayed on a Filtered-View.
 *
 * @author Bruce Martin
 * @version 0.53
 */
public class FilterDetails {

	public static int FT_NORMAL       = 1;
	//public static int FT_SIMPLE_GROUP = 2;
	public static int FT_GROUP        = 3;


    private static final String[] LAYOUT_GROUP_COLUMN_HEADINGS = LangConversion.convertColHeading(
 			"Filter Record Selection",
 			new String[] {"Record", "In Group", "Include"});
    private static final String[] LAYOUT_COLUMN_HEADINGS =
    					 {LAYOUT_GROUP_COLUMN_HEADINGS[0], LAYOUT_GROUP_COLUMN_HEADINGS[2]};
    private static final String[] TWO_LAYOUT_COLUMN_HEADINGS = {
    	LAYOUT_GROUP_COLUMN_HEADINGS[0],
    	LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Equivalent Record"),
    };
    private static final String[] FIELD_COLUMN_HEADINGS  = LangConversion.convertColHeading(
			"Filter Field Selection",
			new String[] {"Field", "Include"});

    private ComboOption[] recordOptions;
    private static final int INCLUDE_INDEX  = 1;
    private static final int IN_GROUP_INDEX = 1;
    private static final int GROUP_INCLUDE_INDEX  = 2;
    private static final int SEQUENCE_INDEX = 1;

	private final AbstractLayoutDetails layout;
	private AbstractLayoutDetails layout2;

    private boolean[] inGroup;
    private int[] recordNo;
    private int[][] fields;

    private int layoutIndex = 0;

    private JTextField messageFld = new JTextField();

    private FilterFieldBaseList filterFields;

    private FilterFieldList filterFieldsL2 = null;
    private FilterFieldList filterFieldsL2a;
    private FieldList fieldList;

    private boolean twoLayouts = false;
    private int filterType;

    private int groupHeader;


    /**
     * Create filter details
     *
     * @param group detail group
     */
    public FilterDetails(final AbstractLayoutDetails group, int filterType) {
        super();
        layout = group;
        this.filterType = filterType;

        init();
    }

    private void init() {
        int count = layout.getRecordCount();

        recordNo = new int[count];
        fields = new int[count][];
        if (filterType == FT_NORMAL) {
        	filterFields = new FilterFieldList(layout);
        } else {
        	filterFields = new FilterFieldGroupList(layout);

        	inGroup = new boolean[count];
        	for (int i = 0; i < count; i++) {
        		inGroup[i] = true;
        	}
        }

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
    	if (twoLayouts) {
    		return new TwoLayoutList();
    	} else if (filterType == FT_GROUP) {
    		return new GroupLayoutList();
    	}
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
    public FilterFieldBaseList getFilterFieldListMdl() {
         return filterFields;
    }


    @SuppressWarnings("rawtypes")
	public DefaultComboBoxModel getComboModelLayout2() {

    	return  filterFieldsL2.getFieldModel();
    }

    @SuppressWarnings("rawtypes")
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
	 * @return the filterType
	 */
	public int getFilterType() {
		return filterType;
	}

	/**
	 * @return the groupFilter
	 */
	public int isNormalFilter() {
		return filterType;
	}

	/**
	 * @param groupFilter the groupFilter to set
	 */
	public void setNormalFilter(int isNormalFilter) {
		this.filterType = isNormalFilter;
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

    public final boolean isInGroup(int index) {

    	if (inGroup == null || index < 0 || index >= inGroup.length) {
            return false;
        }

        return inGroup[index];
    }

    /**
     * get the external XML Layout interface
     * @return external XML Layout interface
     */
    public final Layout getExternalLayout() {
    	int j;
    	net.sf.RecordEditor.jibx.compare.Layout tmpLayoutSelection = new net.sf.RecordEditor.jibx.compare.Layout();
		net.sf.RecordEditor.jibx.compare.Record rec;
		boolean allSelected = true;

		AbstractRecordDetail recordDetail;

		FilterField filterFld;
		FieldTest test;


//		System.out.println("field Get 1 -Layout Name: " + values.getLayoutDetails().name);

		int[][] fieldInc = getFieldMap();

		tmpLayoutSelection.name = layout.getLayoutName();

		if (filterType == FT_GROUP) {
			if (groupHeader >= 0 && groupHeader < layout.getRecordCount()) {
				tmpLayoutSelection.groupHeader = layout.getRecord(groupHeader).getRecordName();
			}

			rec = new net.sf.RecordEditor.jibx.compare.Record();
			rec.fieldTest = new ArrayList<FieldTest>(FilterFieldList.NUMBER_FIELD_FILTER_ROWS);
			for (j = 0; j < FilterFieldList.NUMBER_FIELD_FILTER_ROWS; j++) {
				filterFld = filterFields.getFilterField(0, j);
				if (filterFld.getFieldNumber() >= 0) {
					test = new net.sf.RecordEditor.jibx.compare.FieldTest();
					if (filterFld.getBooleanOperator() == Common.BOOLEAN_OPERATOR_OR) {
						test.booleanOperator = "Or";
					}
					recordDetail = layout.getRecord(filterFld.getRecordNumber());
					test.recordName = recordDetail.getRecordName();
					test.fieldName  = recordDetail.getField(filterFld.getRecFieldNumber()).getName();
					test.operator   = Compare.getOperatorAsString(filterFld.getOperator());
					test.groupOperator = filterFld.getGrouping();
					test.value      = filterFld.getValue();
					rec.fieldTest.add(test);
					allSelected = false;
				}
			}
			tmpLayoutSelection.getRecords().add(rec);
			for (int i = 0; i < layout.getRecordCount(); i++) {
				rec = getExternalRecord(i, fieldInc);
				allSelected = allSelected && (rec.fields != null) && isInclude(i);
				tmpLayoutSelection.getRecords().add(rec);
			}
		} else {
			for (int i = 0; i < layout.getRecordCount(); i++) {
				if (isInclude(i)) {
					rec = getExternalRecord(i, fieldInc);
					allSelected = allSelected && (rec.fields == null);

					recordDetail = layout.getRecord(i);
					rec.name = recordDetail.getRecordName();

//					allFields = ! twoLayouts;
//					if (allFields && fieldInc != null && fieldInc[i] != null) {
//						allFields = fieldInc[i].length == recordDetail.getFieldCount();
//						for (j = 0; j < recordDetail.getFieldCount() && allFields; j++) {
//							if ((fieldInc[i][j] != j)) {
//								allFields = false;
//							}
//						}
//					}
//
//					if (! allFields) {
//						allSelected = false;
//						if (fieldInc != null && i < fieldInc.length && fieldInc[i] != null) {
//							rec.fields = new String[fieldInc[i].length];
//							for (j = 0; j < fieldInc[i].length; j++) {
//								//rec.fields[j]= recordDetail.getField(fieldInc[i][j]).getName();
//								rec.fields[j]= recordDetail.getField(layout.getAdjFieldNumber(i, fieldInc[i][j])).getName();
//							}
//						} else {
//							rec.fields = new String[recordDetail.getFieldCount()];
//							for (j = 0; j < rec.fields.length; j++) {
//								rec.fields[j]= recordDetail.getField(j).getName();
//							}
//						}
//					}

					if (! twoLayouts) {
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
					}

					tmpLayoutSelection.getRecords().add(rec);
				} else {
					allSelected = false;
				}
			}


		}

		if (allSelected) {
			tmpLayoutSelection.records = null;
		}
		return tmpLayoutSelection;
    }

    private net.sf.RecordEditor.jibx.compare.Record getExternalRecord(int idx, int[][] fieldInc) {
    	net.sf.RecordEditor.jibx.compare.Record rec = new net.sf.RecordEditor.jibx.compare.Record();
    	int j;

    	AbstractRecordDetail recordDetail = layout.getRecord(idx);
		rec.name = recordDetail.getRecordName();

		boolean allFields = ! twoLayouts;
		if (allFields && fieldInc != null && fieldInc[idx] != null) {
			allFields = fieldInc[idx].length == recordDetail.getFieldCount();
			for (j = 0; j < recordDetail.getFieldCount() && allFields; j++) {
				if ((fieldInc[idx][j] != j)) {
					allFields = false;
				}
			}
		}

		if (! allFields) {
//			allSelected = false;
			if (fieldInc != null && idx < fieldInc.length && fieldInc[idx] != null) {
				rec.fields = new String[fieldInc[idx].length];
				for (j = 0; j < fieldInc[idx].length; j++) {
					//rec.fields[j]= recordDetail.getField(fieldInc[i][j]).getName();
					rec.fields[j]= recordDetail.getField(layout.getAdjFieldNumber(idx, fieldInc[idx][j])).getName();
				}
			} else {
				rec.fields = new String[recordDetail.getFieldCount()];
				for (j = 0; j < rec.fields.length; j++) {
					rec.fields[j]= recordDetail.getField(j).getName();
				}
			}
		}

		if (filterType == FT_GROUP) {
			rec.include = isInclude(idx);
			rec.inGroup = isInGroup(idx);
		}
		return rec;
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
    	int i, j;
    	Record rec;
		FilterField filterFld;
		FieldTest tst;
		int start = 0 ;


//	    op = Common.BOOLEAN_OPERATOR_AND;
	    filterType = FT_NORMAL;
    	if (values.groupHeader != null && ! "".equals(values.groupHeader)) {
	    	groupHeader = layout.getRecordIndex(values.groupHeader);

	    	if (groupHeader >= 0) {
	    		filterType = FT_GROUP;
	    		start = 1;
	    	}
    	}

		if (values.records == null
		||  values.records.size() == 0) {
			init();
		} else if (recordNo.length == 1 && values.records.size() - start == 1) {
			matchRecord(0, start, values.records.get(start));
		} else {
			for (i = 0; i < recordNo.length; i++) {
				String s = layout.getRecord(i).getRecordName();
				recordNo[i] = -1;
				for (j = start; j < values.records.size(); j++) {
					rec = values.records.get(j);
					if (rec.name.equalsIgnoreCase(s)) {
						matchRecord(i, j, rec);

						break;
					}
				}
			}
		}

		if (filterType == FT_GROUP && values.records != null && values.records.size() >= 0
		&& values.records.get(0).fieldTest != null) {
			int recNo;
			for (int k = 0; k < values.records.get(0).fieldTest.size(); k++) {
				tst = values.records.get(0).fieldTest.get(k);
				recNo = layout.getRecordIndex(tst.recordName);
				filterFld = FilterField.newGroupFilterFields();
				if (recNo >= 0) {
					filterFld.setFieldNumber(recNo, layout.getRecord(recNo).getFieldIndex(tst.fieldName));
				}
				filterFld.setOperator(Compare.getOperator(tst.operator));
				filterFld.setValue(tst.value);
				filterFld.setGrouping(tst.groupOperator);

				if ("Or".equalsIgnoreCase(tst.booleanOperator)) {
					filterFld.setBooleanOperator(Common.BOOLEAN_OPERATOR_OR);
				}

				filterFields.setFilterField(0, k, filterFld);
			}
		}
    }

	private void matchRecord(int recNumber1, int recNumber2, Record rec) {
		int idx;
		FilterField filterFld;
		FieldTest tst;
		if (rec.include == null || rec.include) {
			recordNo[recNumber1] = recNumber2;
		}
		if (inGroup != null) {
			inGroup[recNumber1] = rec.inGroup == null || rec.inGroup;
		}
		if (rec.fields == null || rec.fields.length == 0) {
			fields[recNumber1] = null;
		} else {
			initFieldRow(recNumber1);

		    for (int k = 0; k < rec.fields.length; k++) {
		    	idx = layout.getRecord(recNumber1).getFieldIndex(rec.fields[k]);
		    	fields[recNumber1][idx] = 0;
		    }
		}

		if (rec.fieldTest != null && rec.fieldTest.size() > 0) {
			AbstractRecordDetail recDtl = layout.getRecord(recNumber1);

			for (int k = 0; k < rec.fieldTest.size(); k++) {
				tst = rec.fieldTest.get(k);
				filterFld = new FilterField(filterType == FT_GROUP);
				filterFld.setFieldNumber(recDtl.getFieldIndex(tst.fieldName));
				filterFld.setOperator(Compare.getOperator(tst.operator));
				filterFld.setValue(tst.value);

				if ("Or".equalsIgnoreCase(tst.booleanOperator)) {
					filterFld.setBooleanOperator(Common.BOOLEAN_OPERATOR_OR);
				}

				filterFields.setFilterField(recNumber1, k, filterFld);
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
				if (recordNo.length == 1 && layout2.getRecordCount() == 1) {
					recordNo[0] = 0;
					updateRecordsFields(0);
				} else {
					for (i = 0; i < recordNo.length; i++) {
						setUpRecord(i);
					}
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
                return Boolean.valueOf(recordNo[rowIndex] >= 0);
            }
            return layout.getRecord(rowIndex).getRecordName();
        }
     }

    /**
     * Table model to display records whith 2 layouts
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private class TwoLayoutList extends BaseLayoutList {

        public TwoLayoutList() {
			super(TWO_LAYOUT_COLUMN_HEADINGS);
		}



        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

            if (columnIndex == INCLUDE_INDEX) {
        		if (recordNo[rowIndex] < 0) {
        			return recordOptions[0];
        		} else if (recordNo[rowIndex] + 1 >= recordOptions.length ) {
        			return null;
        		}
        		return recordOptions[recordNo[rowIndex] + 1];
            }
            return layout.getRecord(rowIndex).getRecordName();
        }
      }

    /**
     * Table model to display records whith 2 layouts
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private class GroupLayoutList extends BaseLayoutList {

        public GroupLayoutList() {
			super(LAYOUT_GROUP_COLUMN_HEADINGS);
		}



        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

            switch (columnIndex) {
            case IN_GROUP_INDEX:
            	return Boolean.valueOf(inGroup[rowIndex]);
            case GROUP_INCLUDE_INDEX:
            	return Boolean.valueOf(recordNo[rowIndex] >= 0);
            }
            return layout.getRecord(rowIndex).getRecordName();
        }



		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.filter.FilterDetails.BaseLayoutList#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == IN_GROUP_INDEX) {
				if (aValue instanceof Boolean) {
					inGroup[rowIndex] = ((Boolean) aValue).booleanValue();
				}
			} else {
				super.setValueAt(aValue, rowIndex, columnIndex);
			}
		}


      }

// ---------------------------------------------------------------------------------


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
        	if (twoLayouts && columnIndex == 1) {
        		return LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Equivalent Field");
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
