package net.sf.RecordEditor.re.editProperties;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * Purpose: Change the foreign language used in the RecordEditor
 * License: GPL
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditLanguage extends BasePanel {

	private static final String FLAG_DIR = "/pict/flags/";
	private static final ImageIcon BLANK_ICON =  Common.readIcon(FLAG_DIR + "blank.png");

	private static final String DESCRIPTION
		= LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProtoperties_Language",
				  "<h2>Foreign Language</h2>"
				+ "This screen lets you change the language used in the <b>RecordEditor</b><br/>"
				+ "You will need to <b>exit</b> and <b>restart</b> the Editor for the change to come into effect<br/><br/>"
				+ "If your language is not supported ???, Why not do the Translation your self ???.<br/>"
				+ "Have a look at the ReadMe.html in the lanuage directory:")
				+ " " + Parameters.expandVars(
							Parameters.formatLangDir(
									Parameters.getString(Parameters.LANG_DIRECTORY)));

	private JEditorPane tips = new JEditorPane("text/html", DESCRIPTION);

	private FileChooser langDirFchooser = new FileChooser();

	private JComboBox languagesCombo = new JComboBox();

	private final EditParams params;

	private String lastLangDir = null,
		           lastLang ;

	private HashMap<String, FlagComboItem> langItmHash = new HashMap<String, EditLanguage.FlagComboItem>(40);

   public EditLanguage(final EditParams params) {
	   this.params = params;

	   init_100_InitVars();
	   init_200_LayoutScreen();
	   init_300_FinaliseScreen();
   }

   private void init_100_InitVars() {

	   lastLang = params.getProperty(Parameters.CURRENT_LANGUAGE);
//	   languagesCombo.setSelectedItem(params.getProperty(Parameters.CURRENT_LANGUAGE));
//	   System.out.println("@@ " + languagesCombo.getSelectedItem()
//			   + " " + params.getProperty(Parameters.CURRENT_LANGUAGE));
	   getLanguages();

	   langDirFchooser.setText(getLangDir());
	   langDirFchooser.setExpandVars(true);
	   langDirFchooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	   if (langItmHash.containsKey(lastLang)) {
		   languagesCombo.setSelectedItem(langItmHash.get(lastLang));
	   } else {
		   languagesCombo.setSelectedIndex(0);
	   }

   }

   private void init_200_LayoutScreen() {

	   this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
	   this.addLine("Language Directory", langDirFchooser, langDirFchooser.getChooseFileButton());
	   this.addLine("Language", languagesCombo);
   }

   private void init_300_FinaliseScreen() {

	   languagesCombo.setRenderer(new FlagComboRenderer());
	   langDirFchooser.addFcFocusListener(new FocusAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				params.setProperty(
						Parameters.LANG_DIRECTORY,
						Parameters.encodeVars(langDirFchooser.getText()));
				params.propertiesChanged = true;

				lastLangDir = langDirFchooser.getText();
				getLanguages();
			}
	   });

	   languagesCombo.addFocusListener(new FocusAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				Object o = languagesCombo.getSelectedItem();
				if (o == null) {
					o = "";
				}

				lastLang = o.toString().trim();
				params.setProperty(
						Parameters.CURRENT_LANGUAGE,
						lastLang);
				params.propertiesChanged = true;
			}
	   });
   }


   private void getLanguages() {

	   String dir = Parameters.expandVars(getLangDir());

	   if (lastLangDir == null || ! lastLangDir.equals(dir)) {
		   String s;
		   String langFilePref = "ReMsgs.";
		   Object lang = languagesCombo.getSelectedItem();
		   TreeSet<String> items = new TreeSet<String>();

		   lastLangDir = dir;
		   languagesCombo.removeAllItems();
		   langItmHash.clear();

		   File directory = new File(dir);
		   ImageIcon flag;

		   if (directory.exists() && directory.isDirectory()) {
			   File[] fList = directory.listFiles();

			   for (File file : fList) {
				   if (file.isFile()) {
					   s = file.getName();
					   if (s.startsWith(Parameters.LANG_FILE_PREFIX)) {
						   if (s.toLowerCase().endsWith(".class")) {
							   items.add(s.substring(Parameters.LANG_FILE_PREFIX.length(), s.length() - 6));
						   } else if (s.endsWith(".po")) {
							   items.add(s.substring(Parameters.LANG_FILE_PREFIX.length(), s.length() - 3));
						   }
					   } else if (s.startsWith(langFilePref) && s.endsWith(".po")) {
						   items.add(s.substring(langFilePref.length(), s.length() - 3));
					   }
				   }
			   }
		   }

		   languagesCombo.addItem(new FlagComboItem(" "));
		   for (String itm : items) {
			  languagesCombo.addItem(new FlagComboItem(itm));
		   }

		   if (lang != null && ! "".equals(lang.toString())) {
			   languagesCombo.setSelectedItem(lang);
		   }
	   }
   }

   private String getLangDir() {

	   return Parameters.formatLangDir(
			   params.getProperty(Parameters.LANG_DIRECTORY));

   }


   private class FlagComboItem {
	   public final ImageIcon icon;
	   public final String langCode;

	   public FlagComboItem(String langCode) {
			super();
			this.langCode = langCode;
			if ("".equals(langCode.trim())) {
				icon = null;
			} else if ("tst".equals(langCode) || "txt".equals(langCode)) {
				icon = BLANK_ICON;
			} else {
				icon = Common.readIcon(FLAG_DIR + langCode + ".png");
			}
			langItmHash.put(langCode, this);
	   }

	   public String toString() {
		   return langCode;
	   }
   }


   private class FlagComboRenderer extends JLabel
   implements ListCellRenderer {


	   private FlagComboRenderer() {
		   setOpaque(true);
		   //setHorizontalAlignment(CENTER);
		   setVerticalAlignment(CENTER);
	   }

	   /*
	    * This method finds the image and text corresponding
	    * to the selected value and returns the label, set up
	    * to display the text and image.
	    */
	   public Component getListCellRendererComponent(
			   JList list,
			   Object value,
			   int index,
			   boolean isSelected,
			   boolean cellHasFocus) {
		   //Get the selected index. (The index param isn't
		   //always valid, so just use the value.)

		   super.setText(value.toString());
		   if (value instanceof FlagComboItem) {
			   super.setIcon(((FlagComboItem) value).icon);
		   } else {
			   super.setIcon(null);
		   }
		   if (isSelected) {
			   setBackground(list.getSelectionBackground());
			   setForeground(list.getSelectionForeground());
		   } else {
			   setBackground(list.getBackground());
			   setForeground(list.getForeground());
		   }

		   return this;
	   }
   }


}
