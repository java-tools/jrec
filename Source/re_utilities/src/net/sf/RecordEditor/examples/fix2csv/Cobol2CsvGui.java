/*
 * @Author Bruce Martin
 * Created on 31/1/2006
 *
 * Purpose:
 *   This class will copy a RecordEditor defined file to a CSV file
 * using the RecordEditor Definition
 */
package net.sf.RecordEditor.examples.fix2csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Log.TextLog;

import net.sf.RecordEditor.examples.CopybookToLayout;
import net.sf.RecordEditor.examples.utils.ParseArguments;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * This class will copy a RecordEditor defined file to a CSV file
 * using the RecordEditor Definition
 *
 * @author Bruce Martin
 *
 * Note: to compile this program you need to have the RecordEditor
 * Source code installed

 */
public class Cobol2CsvGui
implements   ActionListener	{

    public static final int FMT_INTEL      = 0;
    public static final int FMT_MAINFRAME  = 1;
    public static final int FMT_BIG_ENDIAN = 1;

    private static final String ARG_COPYBOOK   = "-C";
    private static final String ARG_BINARY     = "-B";
    private static final String ARG_FONT       = "-F";
    private static final String ARG_STRUCTURE  = "-FS";
    private static final String ARG_IN_FILE    = "-I";
    private static final String ARG_OUT_FILE   = "-O";
    private static final String ARG_SEPERATOR  = "-S";

    private static final String[] VALID_PARAMS = {
            ARG_COPYBOOK, ARG_BINARY, ARG_STRUCTURE, ARG_SEPERATOR,
            ARG_IN_FILE, ARG_OUT_FILE, ARG_FONT
    };


    public static final String DIR_COBOL = Parameters.getString("CobolDir");

    private static final String[] COMPUTER_OPTIONS = {"Intel ", "Mainframe ", "Big-Endian"};

    private static final String[] FILE_STRUCTURE = {
            "Default Reader",
            "Fixed Length Binary",
            "Line based Binary",
            "Mainframe VB (rdw based) Binary",
            "Mainframe VB Dump: includes Block length",
            "Fujitsu Variable Binary"
    };
    private static final int[] FILE_STRUCTURE_ID = {0, 2, 3, 4, 5, 7};

    private static final String[] SEPERATOR = {
            "Comma", "Tab", "Space", ";", ":", "|"
    };
    private static final String[] SEPERATOR_CHAR = {
            ",", "\t", " ", ";", ":", "|"
    };

    private JFrame frame = new JFrame("Cobol to CSV");
    private BaseHelpPanel pnl = new BaseHelpPanel();

	private JTextField cobolFile      = new JTextField();
	private JTextField inputFile      = new JTextField();
	private JTextField outputFile     = new JTextField();
	private JTextField fontName       = new JTextField();
	private JComboBox  sepOptions     = new JComboBox(SEPERATOR);
	private JComboBox  binaryOptions  = new JComboBox(COMPUTER_OPTIONS);
	private JComboBox  fileStructure  = new JComboBox(FILE_STRUCTURE);

	private JTextArea  message        = new JTextArea();


	private JButton    cobolFind      = new JButton("Find Cobol Copybook");
	private JButton    findInput      = new JButton("Find Input File");
	private JButton    findOutput     = new JButton("Find Output File");
	private JButton    goBtn          = new JButton("go");

	private JFileChooser chooseFile   = null; //new JFileChooser();
    private static HashMap layoutMap  = new HashMap();



    /**
     * Convert RecordEditor file to a CSV file
     *
     * @param arguments program arguments
     *
     */
	public Cobol2CsvGui(final String[] arguments) {
	    super();

	    setParams(arguments);

	    pnl.addHeading("Cobol File to CSV screen");
	    pnl.setGap(BasePanel.GAP2);

	    pnl.addLine("Cobol Copybook", cobolFile, cobolFind);
	    pnl.setGap(BasePanel.GAP1);
	    pnl.addLine("Input File",  inputFile, findInput);
	    pnl.addLine("Output File", outputFile, findOutput);

	    pnl.setGap(BasePanel.GAP1);
	    pnl.addLine("Font Name", fontName);
	    pnl.addLine("Field Seperator", sepOptions);
	    pnl.addLine("Binary Format",  binaryOptions);
	    pnl.addLine("File Structure", fileStructure, goBtn);

	    pnl.setGap(BasePanel.GAP3);
	    pnl.addMessage(new JScrollPane(message));

	    pnl.done();
	    frame.getContentPane().add(pnl);
	    frame.pack();

	    cobolFind.addActionListener(this);
	    findInput.addActionListener(this);
	    findOutput.addActionListener(this);
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
	 * set values from the program arguments
	 *
	 * @param arguments program arguments
	 */
	private void setParams(String[] arguments) {
	    if (arguments != null) {
	        ParseArguments args = new ParseArguments(VALID_PARAMS, arguments);
	        int structureIdx = args.getIntArg(ARG_STRUCTURE, 0);

	        if (structureIdx > 1) {
	            structureIdx -= 1;
	        }

	        cobolFile.setText(args.getArg(ARG_COPYBOOK, ""));
	        inputFile.setText(args.getArg(ARG_IN_FILE, ""));
	        outputFile.setText(args.getArg(ARG_OUT_FILE, ""));
	        fontName.setText(args.getArg(ARG_FONT, ""));
		    try {
		        sepOptions.setSelectedItem(args.getArg(ARG_SEPERATOR, ""));
		    } catch (Exception e) {
	            e.printStackTrace();
	        }
		    setComboIdx(binaryOptions, args.getIntArg(ARG_BINARY, 0));
		    setComboIdx(fileStructure, structureIdx);
	    }
	}


	/**
	 * set the value of a combo item
	 * @param combo combo being updated
	 * @param idx new index value
	 */
	private void setComboIdx(JComboBox combo, int idx) {
	    try {
	        combo.setSelectedIndex(idx);
	    } catch (Exception e) {
	        combo.setSelectedIndex(0);
            e.printStackTrace();
        }
	}


    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

		if (e.getSource() == findInput) {
		    getFileName(inputFile);
		} else if (e.getSource() == cobolFind) {
		    getFileName(cobolFile);
		} else if (e.getSource() == findOutput) {
		    getFileName(outputFile);
		} else if (e.getSource() == goBtn) {
		    boolean ok = checkFileName(cobolFile.getText(), "Cobol Copybook");

		    if (ok && checkFileName(inputFile.getText(), "Input File")) {
		        CopybookToLayout conv = new CopybookToLayout();
		        LayoutDetail layout;
		        String ifile = inputFile.getText();
		        String ofile = outputFile.getText();
			    String sep   = SEPERATOR_CHAR[sepOptions.getSelectedIndex()];

		        if ("".equals(ofile)) {
		            ofile = ifile + ".csv";
		        }
		        conv.setBinaryFormat(binaryOptions.getSelectedIndex());

		        try {
		            layout = conv.readCobolCopyBook(
		                    cobolFile.getText(),
		                    CopybookToLayout.SPLIT_NONE,
		                    Common.LINE_SEPERATOR,
		                    null,
		                    FILE_STRUCTURE_ID[fileStructure.getSelectedIndex()],
		                    new TextLog()
		            );

		            new Fix2Csv(layout, ifile, ofile, sep);
		        } catch (Exception ex) {
		            message.setText(ex.getMessage());
		            ex.printStackTrace();
                }
		    }
		}
    }


    /**
     * Get the filename via a JFileChooser
     *
     * @param fileNameField filename field to be retrieved
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
     *
     * @throws Exception any error that occurs
     */
    public LayoutDetail getLayout(String name) throws Exception {
        LayoutDetail ret = null;
        String ucName = name.toUpperCase();

        if (layoutMap.containsKey(ucName)) {
            ret = (LayoutDetail) layoutMap.get(ucName);
        } else {
	        CopybookToLayout copybookReader = new CopybookToLayout();
	        ret = copybookReader.readCobolCopyBook(
	                cobolFile.getText(), CopybookToLayout.SPLIT_NONE,
	                "", null, FILE_STRUCTURE_ID[fileStructure.getSelectedIndex()],
	                new TextLog()
	        );

            layoutMap.put(ucName, ret);
        }

        return ret;
    }


    /**
     * start program
     * @param arguments program arguments
     */
    public static void main(String[] arguments) {
        new Cobol2CsvGui(arguments);
    }

}
