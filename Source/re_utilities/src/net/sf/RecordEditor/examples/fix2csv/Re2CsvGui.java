package net.sf.RecordEditor.examples.fix2csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.examples.utils.ParseArguments;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;


/**
 * This program converts a RecordEdit file to a CSV typ file
 * (Comma, Tab delimitered fields).
 *
 * @author Bruce Martin
 *
 * Note: to compile this program you need to have the RecordEditor
 * Source code installed
 *
 */
public class Re2CsvGui implements ActionListener	{

    private static final String ARG_LAYOUT     = "-L";
    private static final String ARG_IN_FILE    = "-I";
    private static final String ARG_OUT_FILE   = "-O";
    private static final String ARG_SEPERATOR  = "-S";

    private static final String[] VALID_PARAMS = {
            ARG_LAYOUT,  ARG_SEPERATOR,
            ARG_IN_FILE, ARG_OUT_FILE
    };

    private static final int FILE_NAME_WIDTH = 35;
    private static final String[] SEPERATOR = {
            "Comma", "Tab", "Space", ";", ":", "|"
    };
    private static final String[] SEPERATOR_CHAR = {
            ",", "\t", " ", ";", ":", "|"
    };

    private JFrame frame = new JFrame();
    private BaseHelpPanel pnl = new BaseHelpPanel();

	private JTextField inFileName     = new JTextField(FILE_NAME_WIDTH);
	private JTextField outFileName    = new JTextField();
	private JComboBox  systemCombo    = new JComboBox();
	private JComboBox  layoutCombo    = new JComboBox();
	private JComboBox  sepOptions     = new JComboBox(SEPERATOR);
	private JTextArea  message        = new JTextArea();
	private JTextArea  description    = new JTextArea();

	private JButton    findInFile     = new JButton("Find Input File");
	private JButton    findOutFile    = new JButton("Find Output File");
	private JButton    goBtn          = new JButton("go");

	private ArrayList<SystemItem> systems = new ArrayList<SystemItem>();
	private ArrayList<LayoutItem> layouts = new ArrayList<LayoutItem>();
	private int[]      layoutId       = null;

	private JFileChooser chooseFile   = null; //new JFileChooser();


    private static HashMap<String, LayoutDetail> layoutMap  = new HashMap<String, LayoutDetail>();

    private CopyBookInterface copybook = new CopyBookDbReader();



    /**
     * This program converts a RecordEdit file to a CSV typ file
     * (Comma, Tab delimitered fields).
     *
     * @param args program arguments
     */
    public Re2CsvGui(final String[] args) {
        super();

        setParams(args);
        setupScreen();
    }


	/**
	 * set values from the program arguments
	 *
	 * @param arguments program arguments
	 */
	private void setParams(String[] arguments) {
        ParseArguments args = new ParseArguments(VALID_PARAMS, arguments);

        if (arguments != null) {
	        inFileName.setText(args.getArg(ARG_IN_FILE, ""));
	        outFileName.setText(args.getArg(ARG_OUT_FILE, ""));
	        try {
	            sepOptions.setSelectedItem(args.getArg(ARG_SEPERATOR, ""));
	        } catch (Exception e) {
            }
	    }

        if ("".equals(inFileName.getText())) {
            inFileName.setText(Common.DEFAULT_FILE_DIRECTORY);
        }
	}


    /**
     * Setup the screen fields
     */
    public void setupScreen() {

		loadSystems();
		copybook.loadLayouts(layouts);
		loadLayoutCombo();

        pnl.addHeading("RecordLayout Selection screen");

		pnl.setGap(BasePanel.GAP1);
		pnl.addComponent("Input File", inFileName, findInFile);
		pnl.addComponent("Output File", outFileName, findOutFile);
		pnl.setGap(BasePanel.GAP2);
	    pnl.addComponent("Field Seperator", sepOptions);
		pnl.setGap(BasePanel.GAP2);

		pnl.addComponent("System", systemCombo);
		pnl.addComponent("Record Layout", layoutCombo, goBtn);

		pnl.addComponent("Description", description);
		pnl.setHeight(BasePanel.NORMAL_HEIGHT * 3);

		pnl.setGap(BasePanel.GAP2);

		pnl.addMessage(new JScrollPane(message));

		pnl.done();
		frame.getContentPane().add(pnl);
		frame.pack();


		findInFile.addActionListener(this);
		findOutFile.addActionListener(this);
		systemCombo.addActionListener(this);
		layoutCombo.addActionListener(this);
		goBtn.addActionListener(this);

	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {

				System.exit(0);
			}
		});
	    frame.setVisible(true);

    }



	/**
	 * Load the record Layout combo
	 */
	private void loadLayoutCombo() {
		int i, j;
		int size = layouts.size();
		int sysIdx = systemCombo.getSelectedIndex();

		if ((layoutId == null) || layoutId.length < size) {
			layoutId = new int[size];
		}

		layoutCombo.removeAllItems();
		if (sysIdx <= 0) {
			for (i = 0; i < size; i++) {
				LayoutItem layout  = layouts.get(i);
				layoutCombo.addItem(layout.getRecordName());
				layoutId[i] = i;
			}
		} else {
			int sys = (systems.get(sysIdx - 1)).systemId;

			j = 0;
			for (i = 0; i < size; i++) {
				LayoutItem layout  = layouts.get(i);
				if (layout.getSystem() == sys) {
					layoutCombo.addItem(layout.getRecordName());
					layoutId[j++] = i;
				}
			}
		}
		setDescription();
	}


	/**
	 * setup layoutdetails
	 *
	 */
	private void setLayoutDetails() {

	    if (layoutCombo.getSelectedItem() != null) {
	        String layoutName = layoutCombo.getSelectedItem().toString();
			this.getLayout(layoutName);

			setDescription();
	    }
	}

	/**
	 * Set the layout description
	 *
	 */
	private void setDescription() {
		try {
			int idx = layoutId[layoutCombo.getSelectedIndex()];
			description.setText((layouts.get(idx)).getDescription());
		} catch (Exception ex) {
		    ex.printStackTrace();
			description.setText("");
		}
	}

	/**
	 * Load the various systems from the DB
	 *
	 */
	private void loadSystems() {
		int i, num;
		SystemItem dtls;

		systemCombo.removeAllItems();

		systemCombo.addItem("<All>");

		try {
			systems = copybook.getSystems();
		} catch (Exception ex) {
			message.setText(ex.getMessage());
			message.setCaretPosition(1);
			ex.printStackTrace();
		}

		num = systems.size();
		for (i = 0; i < num; i++) {
			dtls = systems.get(i);
			systemCombo.addItem(dtls.description);
		}

	}



	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == findInFile) {
		    getFileName(inFileName);
		} else if (e.getSource() == findOutFile) {
		    getFileName(outFileName);
		} else if (e.getSource() == systemCombo) {
			loadLayoutCombo();
		} else if (e.getSource() == layoutCombo) {
			setLayoutDetails();
		} else if (e.getSource() == goBtn) {
		    boolean ok = checkFileName(inFileName.getText(), "Input File");

		    if (ok) {
		        LayoutDetail layout;
		        String ifile = inFileName.getText();
		        String ofile = outFileName.getText();
			    String sep   = SEPERATOR_CHAR[sepOptions.getSelectedIndex()];

		        if ("".equals(ofile)) {
		            ofile = ifile + ".csv";
		        }

		        try {
		            CopyBookDbReader copybookReader = new CopyBookDbReader();
		            layout = copybookReader.getLayout(layoutCombo.getSelectedItem().toString());
		            new Fix2Csv(layout, ifile, ofile, sep);
		        } catch (Exception ex) {
		            System.out.println("}} " + ex.getMessage());
		            System.out.println("}} " + ex.getMessage());
		            message.setText(ex.getMessage());
		            ex.printStackTrace();
                }
		    }
		}
	}


	/**
	 * search for the file under the user's direction
	 *
	 * @param fileNameField file name field to be updated
	 */
	private void getFileName(JTextField fileNameField) {

		if (chooseFile == null) {
			chooseFile = new JFileChooser();
		}

		chooseFile.setSelectedFile(new File(fileNameField.getText()));
		int ret = chooseFile.showOpenDialog(null);

		if (ret == JFileChooser.APPROVE_OPTION) {
			fileNameField.setText(chooseFile.getSelectedFile().getPath());
		}
	}


	/**
     * Check the filename
     *
     * @param fileName2Check filename field to be retrieved
     * @param id file Identifier (to be included in error Messages)
     *
     * @return wether a valid file has been entered
     */
    private boolean checkFileName(String fileName2Check,
            					  String id) {
        boolean ok = true;

        if ("".equals(fileName2Check)) {
            ok = false;
            message.setText("You must enter a " + id);
        } else if (! (new File(fileName2Check)).exists()) {
            ok = false;
            message.setText("The " + id + " does not exist");
        }

        return ok;
    }


    /**
     * get layout details
     *
     * @param name name of the layout to be retrieved
     *
     * @return layout
     */
    public LayoutDetail getLayout(String name) {
        LayoutDetail ret = null;
        String ucName = name.toUpperCase();

        if (layoutMap.containsKey(ucName)) {
            ret = layoutMap.get(ucName);
        } else {
            ret = copybook.getLayout(name);
            layoutMap.put(ucName, ret);
        }

        return ret;
    }


    /**
     * run record editor to CSV program
     *
     * @param args program arguments
     */
    public static void main(final String[] args) {

        try {
            new Re2CsvGui(args);
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
