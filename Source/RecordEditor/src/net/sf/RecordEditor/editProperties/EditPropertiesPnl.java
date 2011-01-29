/*
 * @Author Bruce Martin
 * Created on 24/01/2007 for version 0.60
 *
 * Purpose:
 * edit a group of related properties
 */
package net.sf.RecordEditor.editProperties;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;

/**
 * edit a group of related properties
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditPropertiesPnl extends BasePanel {

	private static final int STRING_VAL  = 1;
	private static final int INT_VAL     = 2;
	private static final int BOOLEAN_VAL = 3;
		private static final int LIST_VAL    = 5;
	private static final int DIRECTORY   = 6;

	public static final Integer FLD_TEXT    = Integer.valueOf(STRING_VAL);
	public static final Integer FLD_INT     = Integer.valueOf(INT_VAL);
	public static final Integer FLD_BOOLEAN = Integer.valueOf(BOOLEAN_VAL);
	public static final Integer FLD_LIST    = Integer.valueOf(LIST_VAL);
	public static final Integer FLD_DIR     = Integer.valueOf(DIRECTORY);

    private static final int NAME_COLUMN = 0;
	private static final int DESCRPTION_COLUMN = 1;
    private static final int VALUE_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int PROMPT_COLUMN = 4;
    private static final int PARAM_COLUMN = 5;

    private JEditorPane tips;


    private Object[][] tableData;

    private EditParams pgmParams;


    /**
     * Edit Properties panel. this panel lets the user edit a group of related
     * properties
     * @param params general program params
     * @param description description of theproperties to be displayed
     * @param tblData properties details
     */
    public EditPropertiesPnl(final EditParams params, final String description, final Object[][] tblData) {
        super();

        pgmParams = params;
        tableData = tblData;
        //System.out.println(description);
        tips = new JEditorPane("text/html", description);

        init_100_Fields();

    }

    /**
     * Initialise Fields
     *
     */
    private void init_100_Fields() {

        this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
        
        for (int i = 0; i < tableData.length; i++) {
            tableData[i][VALUE_COLUMN]
                         = Parameters.getString(tableData[i][NAME_COLUMN].toString());
            if (tableData[i][TYPE_COLUMN] instanceof Integer) {
            	int type = ((Integer) tableData[i][TYPE_COLUMN]).intValue();
            	switch (type) {
            	case STRING_VAL:
            		addField(i, new TxtFld(i), null);
            		break;
            	case INT_VAL:
            		addField(i, new IntFld(i), null);
            		break;
            	case BOOLEAN_VAL:
            		addField(i, new BoolFld(i), null);
             		break;
            	case LIST_VAL:
            		addField(i, new Combo(i), null);
             		break;
            	case DIRECTORY:
            		ChooseFileName fn = new ChooseFileName(i);
            		fn.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            		addField(i, fn, fn.getChooseFileButton());
             		break;
            	}
            }
        }
    }
    
    private void addField(int row, JComponent item, JComponent item2) {
    	Object prompt = tableData[row][PROMPT_COLUMN];
    	if (prompt == null) {
    		prompt = tableData[row][DESCRPTION_COLUMN];
    	}
    	item.setToolTipText(tableData[row][DESCRPTION_COLUMN].toString());
    	super.addLine(prompt.toString(), item, item2);
    }
    
    private void setValue(int row, String value) {
		tableData[row][VALUE_COLUMN] = value;
		
		String propertyName = tableData[row][NAME_COLUMN].toString();
        String oldValue = pgmParams.getProperty(propertyName);

        if (value == null || "".equals(value.trim())) {
            pgmParams.remove(propertyName);
            if (oldValue != null && ! oldValue.trim().equals("")) {
                pgmParams.propertiesChanged = true;
            }
        } else if (oldValue == null || ! oldValue.equals(value)) {
            pgmParams.setProperty(propertyName, value);
            pgmParams.propertiesChanged = true;
        }

    }
    

	private class TxtFld extends JTextField implements FocusListener {
    	
    	int fieldNo;
    	private TxtFld(int fldNo) {
    		fieldNo = fldNo;
    		if (tableData[fieldNo][VALUE_COLUMN] == null) {
    			super.setText("");
    		} else {
    	   		super.setText(tableData[fieldNo][VALUE_COLUMN].toString());
    		}
    		
    		super.addFocusListener(this);
    	}

    	
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			setValue(fieldNo, super.getText());
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
    
	private class BoolFld extends JCheckBox implements FocusListener {
    	int fieldNo;
    	boolean defaultVal;
    	private BoolFld(int fldNo) {
    		Object val = tableData[fldNo][VALUE_COLUMN];
    		fieldNo = fldNo;
    		defaultVal = Parameters.isDefaultTrue(tableData[fieldNo][NAME_COLUMN].toString());
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
    	}

    	
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override 
		public void focusLost(FocusEvent arg0) {
			if (super.isSelected() == defaultVal) {
				setValue(fieldNo, null);
			} else if (super.isSelected()) {
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
	
	private class ChooseFileName extends FileChooser implements FocusListener {

		private int fieldNo;
		public ChooseFileName(int fldNo) {
			super(true, "Choose Directory");
    		fieldNo = fldNo;
    		if (tableData[fieldNo][VALUE_COLUMN] == null) {
    			super.setText("");
    		} else {
    	   		super.setText(tableData[fieldNo][VALUE_COLUMN].toString());
    		}
    		
    		super.addFocusListener(this);
    	}

    	
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			setValue(fieldNo, super.getText());
		}
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {
			
		}
	}
	
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
    		
    		options[0] = new ComboStrOption("", "");
    		for (int i = 0; i < items.length; i++) {
    			options[i+1] = new ComboStrOption(items[i][0], items[i][1]);
    			
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
			setValue(fieldNo, ((ComboStrOption)super.getSelectedItem()).key);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent arg0) {		
		}
	}
}
