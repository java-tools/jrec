/*
 * @Author Bruce Martin
 * Created on 24/01/2007 for version 0.60
 *
 * Purpose:
 * edit a group of related properties
 */
package net.sf.RecordEditor.re.editProperties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BasicGenericPopup;
import net.sf.RecordEditor.utils.swing.DatePopup;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;
import net.sf.RecordEditor.utils.swing.filechooser.JRFileChooserWrapper;
import net.sf.RecordEditor.utils.swing.treeCombo.FileTreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

/**
 * edit a group of related properties
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditPropertiesPnl extends BasePanel {

	private static final int EMPTY_VAL  = 0;
	private static final int STRING_VAL  = 1;
	private static final int INT_VAL     = 2;
	private static final int BOOLEAN_VAL = 3;
	private static final int LIST_VAL    = 5;
	private static final int DIRECTORY   = 6;
	private static final int DATE_VAL    = 7;
	private static final int RETRIEVE_APPL_SIZE    = 8;
	private static final int CHARSET_VAL  = 9;
	private static final int SINGLE_BYTE_CHARSET_VAL  = 10;

	public static final Integer FLD_EMPTY   = Integer.valueOf(EMPTY_VAL);
	public static final Integer FLD_TEXT    = Integer.valueOf(STRING_VAL);
	public static final Integer FLD_CHARSET = Integer.valueOf(CHARSET_VAL);
	public static final Integer FLD_SINGLE_BYTE_CHARSET = Integer.valueOf(SINGLE_BYTE_CHARSET_VAL);
	public static final Integer FLD_INT     = Integer.valueOf(INT_VAL);
	public static final Integer FLD_BOOLEAN = Integer.valueOf(BOOLEAN_VAL);
	public static final Integer FLD_LIST    = Integer.valueOf(LIST_VAL);
	public static final Integer FLD_DIR     = Integer.valueOf(DIRECTORY);
	public static final Integer FLD_DATE    = Integer.valueOf(DATE_VAL);
	public static final Integer FLD_RETRIEVE_SIZE = Integer.valueOf(RETRIEVE_APPL_SIZE);

    private static final int NAME_COLUMN = 0;
	private static final int DESCRPTION_COLUMN = 1;
    private static final int VALUE_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int PROMPT_COLUMN = 4;
    private static final int PARAM_COLUMN = 5;
    private static final int NAME_COLUMN2 = 6;

    private JEditorPane tips;


    private Object[][] tableData;
    private final JComponent[] components;
    private EditParams pgmParams;

    
    /**
     * Edit Properties panel. this panel lets the user edit a group of related
     * properties
     * @param params general program params
     * @param description description of theproperties to be displayed
     * @param tblData properties details
     */
    public EditPropertiesPnl(final EditParams params, final String description, final Object[][] tblData) {
    	this(params, description, -1, -1, tblData);
    }

    /**
     * Edit Properties panel. this panel lets the user edit a group of related
     * properties
     * @param params general program params
     * @param description description of theproperties to be displayed
     * @param tblData properties details
     */
    public EditPropertiesPnl(final EditParams params, final String description, int splitAt1, int splitAt2, final Object[][] tblData) {
        super();

        pgmParams = params;
        tableData = tblData;
        components = new JComponent[tableData.length];
        //System.out.println(description);
        tips = new JEditorPane("text/html", description);

        init_100_Fields(splitAt1, splitAt2);

    }

    /**
     * Initialise Fields
     *
     */
    private void init_100_Fields(int splitAt1, int splitAt2) {

    	this.setNameComponents(true);

        this.addComponentRE(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
        
        if (splitAt1 < 0) {
        	if (tableData.length > 9) {
        		splitAt1 = 10;
        	} else if (splitAt2 > 0) {
        		splitAt1 = splitAt2;
        		splitAt2 = -1;
        	}
        }

        if (splitAt1 < 0) {
        	addOptions(this, 0, tableData.length);
        } else {
         	JPanel p = new JPanel(new BorderLayout());

        	p.add("West", addOptions(new BasePanel(), 0, splitAt1));
        	
        	if (splitAt2 < 0) {
        		p.add("Center", addOptions(new BasePanel(), splitAt1, tableData.length));
        	} else {
        		p.add("Center", addOptions(new BasePanel(), splitAt1, splitAt2));
        		p.add("East", addOptions(new BasePanel(), splitAt2, tableData.length));
        	}
        	
            this.addComponentRE(0, 6, BasePanel.FILL, BasePanel.GAP1,
    		        BasePanel.FULL, BasePanel.FULL,
    				p);
        }
    }

    private BasePanel addOptions(BasePanel pnl, int st, int en) {

    	pnl.setNameComponents(true);
        for (int i = st; i < en; i++) {
            String s = Parameters.getString(tableData[i][NAME_COLUMN].toString());
            if (s != null) {
            	tableData[i][VALUE_COLUMN] = s;
            }
            if (tableData[i][TYPE_COLUMN] instanceof Integer) {
            	int type = ((Integer) tableData[i][TYPE_COLUMN]).intValue();
            	switch (type) {
            	case EMPTY_VAL:
            		pnl.addLineRE(" ", null);
            		break;
            	case STRING_VAL:
            		addField(pnl, i, new TxtFld(i), null);
            		break;
            	case CHARSET_VAL:
               	case SINGLE_BYTE_CHARSET_VAL:
           		addField(pnl, i, new CharsetFld(i, type == SINGLE_BYTE_CHARSET_VAL), null);
            		break;
            	case DATE_VAL:
            		addField(pnl, i, new DateFld(i), null);
            		break;
            	case INT_VAL:
            		addField(pnl, i, new IntFld(i), null);
            		break;
            	case BOOLEAN_VAL:
            		addField(pnl, i, new BoolFld(i), null);
             		break;
            	case LIST_VAL:
            		addField(pnl, i, new Combo(i), null);
             		break;
            	case DIRECTORY:
            		ChooseFileName fn = new ChooseFileName(i);
            		addField(pnl, i, fn, null);
             		break;
            	case RETRIEVE_APPL_SIZE:
            		if (i > 1 && components[i - 1] instanceof TxtFld && components[i - 2] instanceof TxtFld) {
            			addField(pnl, i, new FetchSize((TxtFld) components[i - 2], (TxtFld) components[i - 1]), null);
            		}
            		break;
            	}
            }
        }
        return pnl;
    }

//    public final void save() {
//    	for (JComponent c : components) {
//    		if (c instanceof ChooseFileName && c.hasFocus()) {
//    			((ChooseFileName) c).focusLost(null);
//    		}
//    	}
//    }

    private void addField(BasePanel pnl, int row, JComponent item, JComponent item2) {
    	Object prompt = tableData[row][PROMPT_COLUMN];
    	if (prompt == null) {
    		prompt = tableData[row][DESCRPTION_COLUMN];
    	}
//    	if (prompt != null) {
//    		item.setName(prompt.toString());
//    		//System.out.println("Set name: " + prompt.toString() + " " + item.getName());
//    	}
    	item.setToolTipText(LangConversion.convert(LangConversion.ST_FIELD_HINT, tableData[row][DESCRPTION_COLUMN].toString()));
    	
    	if (item2 == null) {
    		pnl.addLineRE(prompt.toString(), item);
    	} else {
    		pnl.addLineRE(prompt.toString(), item, item2);
    	}
    	components[row] = item;
    }

    private void setValue(int row, String value) {
    	setValue(row, value, null);
    }

    private void setValue(int row, String value, String value2) {
//		if (row == 8 && "SepWindows".equals(tableData[row][NAME_COLUMN])) {
//			System.out.println(tableData[row][NAME_COLUMN] + " " + tableData[row][VALUE_COLUMN]);
//		}
		tableData[row][VALUE_COLUMN] = value;

		String propertyName = tableData[row][NAME_COLUMN].toString();
		String propertyName2 = tableData[row].length <= NAME_COLUMN2 || tableData[row][NAME_COLUMN2]==null 
				? null 
				: tableData[row][NAME_COLUMN2].toString();
        String oldValue = pgmParams.getProperty(propertyName);


        if (value == null || "".equals(value.trim())) {
        	String dflt = Parameters.getSecondayString(propertyName);
        	if (dflt == null || "".equals(dflt.trim())) {
	            pgmParams.remove(propertyName);
	            if (oldValue != null && ! oldValue.trim().equals("")) {
	            	pgmParams.propertiesChanged = true;
	            }
        	} else {
        		setActualValue("", propertyName, null, null);
        	}
        } else if (! value.equals(oldValue)) {
            setActualValue(value, propertyName, value2, propertyName2);
        }

    }

	/**
	 * @param value
	 * @param propertyName
	 */
	public void setActualValue(String value, String propertyName, String value2, String propertyName2) {
		pgmParams.setProperty(propertyName, value);
		pgmParams.propertiesChanged = true;
		
		if (! isEmpty(propertyName2)) { 
			if (isEmpty(value2)) {
				pgmParams.remove(propertyName2);
			} else {
				pgmParams.setProperty(propertyName2, value2);
			}
		}
	}

	/**
	 * @param val
	 * @return
	 */
	public boolean isEmpty(String val) {
		return val == null || val.length() == 0;
	}

	private static interface FldUpdate {
		public void setTextSilently(String val);
		public UpdateControl getUpdateControl();
	}


	private static class UpdateControl {
		final String key;
		final FldUpdate fld;
		final UpdateControl first;
		UpdateControl next = null;
		
		UpdateControl(String key, FldUpdate fld, UpdateControl first) {
			this.key = key;
			this.fld = fld;
			if (first==null) {
				this.first =  this;
			} else {
				this.first = first;
				this.next = first.next;
				first.next = this;
			}
		}
		
		void notifyOfUpdate(String v) {
			UpdateControl itm =  first;

			while (itm != null) {
				if (itm != this) {
					itm.fld.setTextSilently(v);
				}
				itm = itm.next;
			}
		}
	}

	private static HashMap<String, UpdateControl> fldsMap = new HashMap<String, EditPropertiesPnl.UpdateControl>(300);
	
	private ArrayList<UpdateControl> items = new ArrayList<UpdateControl>();
	private UpdateControl register(String name, FldUpdate fld) {
		String key = name.toLowerCase();
		UpdateControl first = fldsMap.get(key);
		UpdateControl curr = new UpdateControl(key, fld, first);
		if (first == null) {
			fldsMap.put(key, curr);
		}
		items.add(curr);
		return curr;
	}
	
	public final void clear() {
		for (UpdateControl item : items) {
			if (item != item.first) {
				UpdateControl it = item.first;
				while (it != null) {
					if (it.next == item) {
						it.next = item.next;
						break;
					}
					it = it.next;
				}
			} else if (item.next == null) {
				fldsMap.remove(item.key);
			} else {
				fldsMap.put(item.key, item.next);
			}
		}
	}

	private class TxtFld extends JTextField implements FocusListener, FldUpdate {

    	int fieldNo;
    	UpdateControl listners;
    	private TxtFld(int fldNo) {
    		fieldNo = fldNo;
    		if (tableData[fieldNo][VALUE_COLUMN] == null) {
    			super.setText("");
    		} else {
    	   		super.setText(tableData[fieldNo][VALUE_COLUMN].toString());
    		}

    		super.addFocusListener(this);
    		listners = register(tableData[fieldNo][NAME_COLUMN].toString(), this);
    	}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			String text = super.getText();
			setValue(fieldNo, text);
			listners.notifyOfUpdate(text);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.editProperties.EditPropertiesPnl.FldUpdate#setNoTrigger(java.lang.String)
		 */
		@Override
		public void setTextSilently(String val) {
			super.setText(val);
//			try {
//				super.removeFocusListener(this);
//				super.setText(val);
//			} finally {
//				super.addFocusListener(this);
//			}
			
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.editProperties.EditPropertiesPnl.FldUpdate#getUpdateControl()
		 */
		@Override
		public UpdateControl getUpdateControl() {
			return listners;
		}
		
		
    }
	
	private class CharsetFld extends TxtFld  {
		final boolean singleByte;
		private CharsetFld(int fldNo, boolean singleByte) {
			super(fldNo);
			this.singleByte = singleByte;
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			String charset = super.getText();
			if (Charset.isSupported(charset)) {
				if (singleByte && Conversion.isMultiByte(charset)) {
					ReOptionDialog.showMessageDialog(this, UtMessages.SINGLE_BYTE_FONT.get(charset));
				} else {
					super.focusLost(arg0);
				}
			} else {
				ReOptionDialog.showMessageDialog(this, UtMessages.INVALID_FONT.get(charset));
			}
		}
		
		
	}


	private class DateFld extends BasicGenericPopup implements FocusListener {

    	int fieldNo;
    	DatePopup popup = new DatePopup();

    	private DateFld(int fldNo) {
    		fieldNo = fldNo;
    		if (tableData[fieldNo][VALUE_COLUMN] == null) {
    			super.setText("");
    		} else {
    	   		super.setText(tableData[fieldNo][VALUE_COLUMN].toString());
    		}

    		this.setupBackground();
    		//this.setBorder(BorderFactory.createEmptyBorder());
    		//this.setBorder((new JTextField()).getBorder());

    		this.getMinimumSize().setSize(
    								this.getMinimumSize().getWidth(),
    								Math.max(
    										this.getMinimumSize().getWidth(),
    										SwingUtils.NORMAL_FIELD_HEIGHT));

    		super.setPopup(popup);
    		super.addFocusListener(this);
    	}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			EditPropertiesPnl.this.setValue(fieldNo, super.getText());
		}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {
		}
    }

    private class IntFld extends TxtFld {
    	private IntFld(int row) {
    		super(row);
    	}

    	/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			try {
				String s = super.getText().trim();
				Long.getLong(s);
				setValue(fieldNo, s);
			} catch (Exception e) {

			}
		}
    }

	private class BoolFld extends JCheckBox implements FocusListener, ChangeListener {
    	int fieldNo;
    	boolean defaultVal;
    	private BoolFld(int fldNo) {
    		Object val = tableData[fldNo][VALUE_COLUMN];
    		fieldNo = fldNo;
    		defaultVal = Parameters.isDefaultTrue(tableData[fieldNo][NAME_COLUMN].toString());
//    		if (fieldNo == 8 && "SepWindows".equals(tableData[fieldNo][NAME_COLUMN])) {
//    			System.out.println(tableData[fieldNo][NAME_COLUMN]);
//    		}
    		if (val == null || "".equals(val.toString())) {
    			super.setSelected(defaultVal);
    		} else {
    			String s = val.toString().toUpperCase();
    			if (s.startsWith("Y")) {
    				super.setSelected(true);
    			} else if (s.startsWith("N")) {
    				super.setSelected(false);
    			} else {
    				super.setSelected(defaultVal);
    			}
    		}
//    		System.out.println("Field " + fldNo
//    				+ "\t" + tableData[fieldNo][NAME_COLUMN]
//    				+ "\t" + tableData[fieldNo][VALUE_COLUMN]
//    				+ "\t" + super.isSelected()
//    		);
    		super.addFocusListener(this);
    		super.addChangeListener(this);
    	}


		/* (non-Javadoc)
		 * @see javax.swing.AbstractButton#setSelected(boolean)
		 */
		@Override
		public void setSelected(boolean b) {
			if (super.isSelected() != b) {
				super.setSelected(b);
			}
		}


		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			focusLost(null);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			boolean selected = super.isSelected();
			//System.out.println(fieldNo + "\t" + defaultVal + " " + selected +"\t" + tableData[fieldNo][NAME_COLUMN] );
			if (selected == defaultVal) {
				setValue(fieldNo, null);
			} else if (selected) {
				setValue(fieldNo, "Y");
			} else {
				setValue(fieldNo, "N");
			}
		}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {
		}
    }

	private static File[] standardFiles = {
			new File(Parameters.expandVars("<reproperties>")),
			new File(Parameters.expandVars("<reproperties>.User"))
	};
	private static final IFileChooserWrapper cw = JRFileChooserWrapper.newChooser(null, standardFiles) ;
	private class ChooseFileName extends TreeComboFileSelect implements FocusListener, FldUpdate {

		private int fieldNo;
		private UpdateControl listners;
		public ChooseFileName(int fldNo) {
			super(true, true, ((List<FileTreeComboItem>) null), cw, Arrays.asList(standardFiles));
			//super(true, ((List<FileTreeComboItem>) null), cw, ((List<File>) null));
			
			//(true, true, true, null, null);
			//super(true, "Choose Directory");

			//super.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			super.setExpandVars(true);
    		fieldNo = fldNo;
    		if (tableData[fieldNo][VALUE_COLUMN] == null) {
    			super.setText("");
    		} else {
    	   		super.setText(tableData[fieldNo][VALUE_COLUMN].toString());
    		}

    		//super.addFocusListener(this);
    		super.addFcFocusListener(this);
    		listners = register(tableData[fieldNo][NAME_COLUMN].toString(), this);
    	}


	


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.editProperties.EditPropertiesPnl.FldUpdate#getUpdateControl()
		 */
		@Override
		public UpdateControl getUpdateControl() {
			return listners;
		}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			String text = super.getText();
			setValue(fieldNo, text);
			listners.notifyOfUpdate(text);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {

		}
	}

	@SuppressWarnings("rawtypes")
	private class Combo extends JComboBox implements FocusListener {

		private int fieldNo;
		public Combo(int fldNo) {
			Object o = tableData[fldNo][VALUE_COLUMN];
			String s = "";
			int idx = 0;
			String[][] items = (String[][])tableData[fldNo][PARAM_COLUMN];
			ComboStrOption[] options = new ComboStrOption[items.length + 1];
    		fieldNo = fldNo;

    		if (o != null) {
    			s = o.toString();
    		}

    		options[0] = new ComboStrOption("", "", null);
    		for (int i = 0; i < items.length; i++) {
    			options[i+1] = new ComboStrOption(items[i][0], items[i][1], null);

    			if (s.equalsIgnoreCase(items[i][0])) {
    				idx = i+1;
    			}
    		}

    		super.setModel(new DefaultComboBoxModel(options));

    		super.setSelectedIndex(idx);

    		super.addFocusListener(this);
    	}


		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {

			ComboStrOption selectedItem = (ComboStrOption)super.getSelectedItem();
			setValue(fieldNo, selectedItem.key, selectedItem.toString());
		}
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {
		}
	}

	public static class FetchSize extends JButton implements ActionListener {
		TxtFld heightTxt, widthTxt;

		public FetchSize(TxtFld height, TxtFld width) {
			super(LangConversion.convert(LangConversion.ST_BUTTON, "Retrieve Screen size"));
			this.heightTxt = height;
			this.widthTxt = width;

			this.addActionListener(this);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Dimension d = ReMainFrame.getMasterFrame().getSize();
			heightTxt.setText(String.valueOf(d.height));
			widthTxt.setText(String.valueOf(d.width));
			heightTxt.focusLost(null);
			widthTxt.focusLost(null);
		}


	}
	
}
