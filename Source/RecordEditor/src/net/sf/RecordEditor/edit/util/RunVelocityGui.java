/*
 * @Author Bruce Martin
 * Created on 1/02/2007 for version 0.60
 *
 * Purpose:
 * Gui program to run velocity on a Record Based File
 */
package net.sf.RecordEditor.edit.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.RecordEditor.re.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.re.script.RunVelocity;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;
/**
 * Gui program to run velocity on a Record Based File
 *
 * @author Bruce Martin
 *
 */
public class RunVelocityGui implements ActionListener {

    private static final int NOT_INSTALLED_MSG_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 25;
    private JFrame  frame = new JFrame();
    private BasePanel pnl = new BasePanel();

    private JPanel runPanel = new JPanel();
    private FileChooser inputFile = new FileChooser();
    private FileChooser templateFile = new FileChooser(true, "Choose Velocity Template");
    private FileChooser outputFile = new FileChooser(false, "Choose Output File");
    private JButton runBtn = SwingUtils.newButton("Run");
    private JTextArea message = new JTextArea();


    private CopyBookDbReader copybookReader = CopyBookDbReader.getInstance();

    private LayoutSelectionDB layoutSelection;


    /**
     *
     */
    public RunVelocityGui() {
        super();

        if (Common.isVelocityAvailable()) {
             init_100_SetupScreenFields();
            init_200_BuildScreen();
        } else {
            init_300_BuildNoVelocityScreen();
        }
        //pnl.done();
        frame.getContentPane().add(pnl);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * setup screen fields
     *
     */
    private void init_100_SetupScreenFields() {

       	layoutSelection = new LayoutSelectionDB(copybookReader, message, false);
        inputFile.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
        templateFile.setText(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());

        runBtn.addActionListener(this);

        frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent e) {
			    Common.closeConnection();
			}
		});
    }

    /**
     * build the screen
     *
     */
    private void init_200_BuildScreen() {

        pnl.addLine("Input File", inputFile, inputFile.getChooseFileButton());
        pnl.addLine("Template File", templateFile, templateFile.getChooseFileButton());
        pnl.setGap(BasePanel.GAP1);

        layoutSelection.addLayoutSelection(pnl, inputFile, runPanel, null, null);
        pnl.setGap(BasePanel.GAP1);

        pnl.addLine("Output File", outputFile, outputFile.getChooseFileButton());
        pnl.setGap(BasePanel.GAP2);

        pnl.addLine("", null, runBtn);
        pnl.setGap(BasePanel.GAP2);

        pnl.addMessage(message);
        pnl.setHeight(BasePanel.GAP2);
    }


    /**
     * build the screen
     *
     */
    private void init_300_BuildNoVelocityScreen() {
        JEditorPane description = new JEditorPane(
        		"text/html",
   				ReMessages.VELOCITY_NOT_INSTALLED.get()
//       		LangConversion.convertId(
//        				LangConversion.ST_MESSAGE, "RunVelocityHint",
//	                "<h2>Velocity is not Installed</h2>"
//	              + "Velocity has not been installed into the <b>record editor</b>.<br>"
//	              + "The easiest way to install Velocity is to go to the RecordEditor Download page<br>"
//	              + "<b>http://sourceforge.net/project/showfiles.php?group_id=139274</b> "
//	              + "and download file <b>ru_velocity_1.4*.zip</b>.<br>"
//	              + "From this zip file and copy the 2 jars files to the <b>&lt;RecordEditor install directory&gt;/lib</b> directory. "
//	              + "<br><br>Alternately in the <b>HowTo</b> document, there is a discussion on installing velocity.")
        );

		pnl.addComponent(1, 5, NOT_INSTALLED_MSG_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				description);
    }


    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public final void actionPerformed(ActionEvent e) {

        message.setText("");
        if (e.getSource() == runBtn) {
            if ("".equals(inputFile.getText().trim())) {
                pnl.setMessageTxt("You must enter an input file");
                inputFile.requestFocus();
            } else  if ("".equals(templateFile.getText().trim())) {
            	pnl.setMessageTxt("You must enter a Velocity Template file");
                templateFile.requestFocus();
            } else  if ("".equals(outputFile.getText().trim())) {
            	pnl.setMessageTxt("You must enter an Output file");
                outputFile.requestFocus();
            } else {
                RunVelocity velocity = RunVelocity.getInstance();

                try {
                    velocity.processFile(layoutSelection.getRecordLayout(""),
                            inputFile.getText(), templateFile.getText(), outputFile.getText());
                } catch (Exception ex) {
                    message.setText(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }


    /**
     * @see net.sf.RecordEditor.utils.MainProgram#setPgmArgs(java.lang.String[])
     */
    public void setPgmArgs(String[] pgmArgs) {
        frame.setVisible(true);
    }


    /**
     * run the program
     * @param pgmArgs program arguments
     */
    public static void main(final String[] pgmArgs) {

        (new RunVelocityGui()).setPgmArgs(null);
    }

}
