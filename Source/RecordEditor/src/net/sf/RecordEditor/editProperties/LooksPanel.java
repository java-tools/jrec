/*
 * @Author Bruce Martin
 * Created on 15/03/2007 for version 0.61
 *
 * Purpose:
 */
package net.sf.RecordEditor.editProperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LooksPanel extends BasePanel implements ActionListener {


    private static final String PANEL_DESCRIPTION =
          "<h1>Look and Feel</h1>"
        + "In java you can plug in a look-and-feel module to format Screen. "
        + "This screen lets you choose which Look-and-Feel to use."
        + "<br><b>Warning:</b> Independent Look-and-Feel modules can slow down response times "
        + "and cause the <b>RecordEditor</b> to crash."
        + "<br><br>The following 2 sites list a number of <b>Look and Feel</b><ul compact>"
        + "<li><b>http://www.java2s.com/Product/Java/Swing/LookAndFeel.htm</b>"
        + "<li><b>http://www.java-tips.org/java-libraries/java-look-and-feel/</b>"
        + "</ul>The JGoodies Look-and-Feel (<b>http://www.java2s.com/Product/Java/Swing/LookAndFeel.htm</b>)"
        + "is popular";
    private static final String JGOODIES
    	= "JGoodies provide a popular Look and Feel, <br>"
    	+ "You may find the Jar <b>looks-<i>&lt;<i>version<i>&gt;.jar</b> is already on your system.<br>"
    	+ "If not download it from <b>http://www.jgoodies.com/freeware/looksdemo/index.html</b> ";
    private static final String COMPIERE
        = "Compiere provide there look and feel at <b>http://www.compiere.org/looks/</b>";
    private static final String TONIC
        = "Tonic look and feel is available at<br><b>http://www.digitprop.com/p.php?page=toniclf&lang=eng</b>";

    private static final String OFFICE_LN_FS
    	= "OfficeLnFs available at <b>http://sourceforge.net/projects/officelnfs/</b>"
    	+ "<br>only works on <i>Windows</i> and <i>Java 1.5</i> or latter. Looks to work well";

    private static final String[] LOOKS_OPTIONS = {
            "Default",
            "Java - System look and Feel",
            "Java - Metal",
           // "Java - motif",
            "Java - Windows",
            "Java - Mac",
            "JGoodies Plastic XP Look And Feel",
            "JGoodies Plastic 3D Look And Feel",
            "JGoodies Plastic Look And Feel",
            "JGoodies Windows Look And Feel",
            "Compiere",
            "OfficeLnFs - Office 2003",
            "OfficeLnFs - Office XP",
            "OfficeLnFs - Visual Studio 2005",
            "Tonic",
            "JTattoo",
            "Liquid",
            "PGS",
            "Infonode",
            "Nimbus",
            "Other",
    };

    private static final int NUMBER_OF_JAVA_LOOKS = 5;


    private int otherOptions = 0;

    private String[] looksDescriptions = {
            "Default Java Look and Feel",
            "Builtin System Look and Feel: " + UIManager.getSystemLookAndFeelClassName(),
            "Builtin Java Metal Look and Feel",
           // "Builtin Java motif",
            "Builtin Java Windows",
            "Builtin Java Mac",
            JGOODIES, JGOODIES, JGOODIES, JGOODIES,
            COMPIERE,
            OFFICE_LN_FS, OFFICE_LN_FS, OFFICE_LN_FS,
            TONIC,
            "JTattoo not my taste but seems fast<br>see <b>http://www.jtattoo.net/</b>",
            "Liquid Look and Feel<br>See <b>https://liquidlnf.dev.java.net/</b>",
            "pgs<br>See <b>https://pgslookandfeel.dev.java.net/</b>",
            "Infonode many commercial java packages use it<br/>see <b>http://www.infonode.net</b>",
            "Nimbus",
            "Other Look and Feel, remember to fill in the <b>class name</b>",
     };

    private String[] looksClasses = {
            "",
            "",
            "javax.swing.plaf.metal.MetalLookAndFeel",
          //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
            "com.sun.java.swing.plaf.mac.MacLookAndFeel",
            "com.jgoodies.plaf.plastic.PlasticXPLookAndFeel",
            "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel",
            "com.jgoodies.plaf.plastic.PlasticLookAndFeel",
            "com.jgoodies.plaf.windows.ExtWindowsLookAndFeel",
            "org.compiere.plaf.CompiereLookAndFeel",
            "org.fife.plaf.Office2003.Office2003LookAndFeel",
            "org.fife.plaf.OfficeXP.OfficeXPLookAndFeel",
            "org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel",
            "com.digitprop.tonic.TonicLookAndFeel",
            "com.jtattoo.plaf.smart.SmartLookAndFeel",
            "com.birosoft.liquid.LiquidLookAndFeel",
            "com.pagosoft.plaf.PgsLookAndFeel",
            "net.infonode.gui.laf.InfoNodeLookAndFeel",
            "Nimbus", //com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            "",
    };

    private JEditorPane tips = new JEditorPane("text/html", PANEL_DESCRIPTION);;

    private JComboBox looks = new JComboBox();
    private JTextField className = new JTextField();
    private FileChooser jarName  = new FileChooser();
    private JEditorPane description = new JEditorPane("text/html", "");

    private EditParams pgmParams;


    /**
     * Panel to select Look and Feel
     * @param params parameters
     */
    public LooksPanel(final EditParams params) {
        super();

        pgmParams = params;

        init_100_ScreenFields();
        init_200_ScreenBuild();
    }


    /**
     * Initialise / setup screen fields
     *
     */
    private void init_100_ScreenFields() {

        FocusAdapter focusMgr = new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                setParameters();
            }
        };

        init_110_BuildLooksComb();
        looks.setSelectedIndex(0);
        try {
            int idx = Integer.parseInt(
                    pgmParams.getProperty(Parameters.PROPERTY_LOOKS_CLASS_INDEX));
            looks.setSelectedIndex(idx);
        } catch (Exception e) {
        }
        className.setText(pgmParams.getProperty(Parameters.PROPERTY_LOOKS_CLASS_NAME));
        jarName.setText(pgmParams.looksJar);

        setOptions();


        looks.addActionListener(this);
        looks.addFocusListener(focusMgr);
        className.addFocusListener(focusMgr);
        jarName.addFcFocusListener(focusMgr);
    }

    /**
     * Build the looks combo
     */
    private void init_110_BuildLooksComb() {
        int i, j;

        looks.addItem(LOOKS_OPTIONS[0]);
        looks.addItem(LOOKS_OPTIONS[1]);
        j = 2;
        for (i = 2; i < LOOKS_OPTIONS.length; i++) {
            if (i >= NUMBER_OF_JAVA_LOOKS
            || isAvailableLookAndFeel(looksClasses[i])) {
                looks.addItem(LOOKS_OPTIONS[i]);
                looksDescriptions[j] = looksDescriptions[i];
                looksClasses[j] = looksClasses[i];
                j += 1;
            }
        }
        otherOptions = j - 1;
    }

    /**
     * Setup the screen
     *
     */
    private void init_200_ScreenBuild() {

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));

        this.addLine("Look and Feel", looks);
        this.setGap(BasePanel.GAP1);

        this.addLine("Look and Feel Class Name", className);
        this.addLine("Jar File", jarName, jarName.getChooseFileButton());
        this.setGap(BasePanel.GAP3);

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(description));

    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public final void actionPerformed(ActionEvent e) {

        if (e.getSource() == looks) {
            setOptions();
            setParameters();
        }

    }

    /**
     * Set Field enabled options / descriptions etc
     *
     */
    private void setOptions() {
        int idx = looks.getSelectedIndex();

        className.setEnabled(idx == otherOptions);
        jarName.setEnabled(idx > 0);

        if (idx < otherOptions) {
            className.setText(looksClasses[idx]);
        }

        description.setText(looksDescriptions[idx]);
    }

    /**
     * Save the updated parameters
     *
     */
    private void setParameters() {

        pgmParams.setProperty(Parameters.PROPERTY_LOOKS_CLASS_INDEX,
                			  Integer.toString(looks.getSelectedIndex()));

        pgmParams.setProperty(Parameters.PROPERTY_LOOKS_CLASS_NAME,
                className.getText());
        pgmParams.looksJar = jarName.getText();
    }


    /**
     * Check to see if Look and Feel is available
     * @param laf look and feel name
     * @return wether it is available
     */
    private boolean isAvailableLookAndFeel(String laf) {

        try {
            //Class lnfClass = Class.forName(laf);
            LookAndFeel newLAF = (LookAndFeel) ((Class.forName(laf)).newInstance());
            return newLAF.isSupportedLookAndFeel();
        } catch (Exception e) { // If ANYTHING weird happens, return false
            return false;
        }
    }

}
