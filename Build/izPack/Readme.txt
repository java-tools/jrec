The RecordEditor is a Data File Editor (for both CSV (Comma / tab seperated fields)
and fixed field positions files typically used in Cobol / IBM Mainframe).
It uses Record-Layout to format a file in a human readable format. 
It can edit both Text and binary files in PC / Unix / IBM Mainframe formats.

The Record-Layouts are stored in a DB, but they can be imported from a Cobol Copybook


Version 0.94.1
    * Support for Records (> 32KB)
    * Add Tree Combo for field types
    * New Types where the Decimal point is ','
    * New Cobol Dialects which partially support the Cobol DECIMAL-POINT IS COMMA clause.
    * Several Cobol related fix's
    * Change file structure option
    * Save with different file structure

Version 0.94
    * New Text Editor View
    * New positive types added
    * Cobol Changes
      - VB file detection added
      - Positive fields are restricted to positive numbers 
    * Fix's for Sort Tree, Child Screen position (after find) and Fuji Zoned Type
 
 
Version 0.92.1
 
   * Adding 2 extra Csv parser - Basic - Delimiter all fields and Basic - Delimiter all fields + 1
   * Fix for existing Csv Parser "Parser - Matching Quotes" for when no quote is defined.

 
Version 0.92

    * All screens for a File are now displayed as Tabs rather than as seperate screens. The tabs can be undocked (and redocked) if required.
    * Added Child screen option to List Screens
    * Added Hints dialogue
    * Enhanced HTML Export, option to open exported file with default programs
    * Highlight change fields in compare
    * Minor GUI changes
    * Enhanced Help menu (can open Manual / Forums etc).
    * Fixed Paste Prior issues


Version 0.91.1
    * Minor fix's / changes to installers

Version 0.91.0
    * Improved (rewritten) Group Filters
    * Support for editting GetText-po files
    * Support for editting SwingX tip of the day properties files
    * Load (from saved Xml) option added to a filter, Sort, Sort Tree and Record-Tree screens
    * Daily Tips added to startup
    * various minor enhancements / fix's

Version 0.90.0
    * Related screens are now displayed as tabs on the one screen (default).
      There is still the option to display each view in its own screen.
    * Different views can be docked / undocked with each other
    * List screens can now have a Record Screen where the current record is displayed.
    * Find - option to start from the start (when the end is reached).
    * Filter - Group filters are now supported

Version 0.89.3
    * Problem fix's mainly related to Foreign Languages in Combo box's
    * Improved Html handling in supplied sample translations
    * About 100 extra text fields


Version 0.89.2
    * The RecordEditor can now has a very basic po file parser (po file must use encoding=utf8) 
      and can use po file directly.
    * Action / Action-Descriptions are now text rather than using a lookup key. This is to allow Automatic translation
    * Minor improvements in the language selection screen
    * The Help system will look for html files in <HelpDir>/<2-char-language-code>/, followed by <HelpDir>/.
      This would allow for localised help files. 
    * There are now sample po files in several languages (German, French, Spanish, Italian, Chinese, Japanese)
      created using Google-Translate. They all need a human translator to work on them. 
      If some ones a Google Translate for a different version, let me know.
  
Version 0.89.1
    * Support for foreign languages via gettext po files --> gettext generated resource
      bundles.
    * New language tab on the options screen where the "foreign~language" can be selected.
    * In Utilities editor, directory variables are now decoded/encoded via the directory
      search buttons.
      
Changes Release 0.88
    * Minor Gui changes
    * Enhanced HTML Export
    * Option to Open Exported file
    * Highlight changed fields on Compare screen (Feature request 3532674).
    * Enhanced Help menu (can open Manual / Forums etc)
    * Minor fix's

Changes Release 0.86
    * Filter - added And / Or operators, added Starts With operator
    * Find - fix's + new Start-With operator

Changes Release 0.85
    * Enhanced Record Selection Edit screen + problem fix's
    * Minor layout editor changes + problem fix's
    * Many minor GUI changes mostly related to Windows and Nimbus Laf
    * Updated Find function
    * New FileWizard for use when the file is not recognized.

Changes Release 0.80.6
    * Change the Initialisation process
    * Can enter Jars in the Export as Xslt option
    * Created seperate SaveAs function.
    * Reorganised menu's
    * Utility (but not main screens) close with esc key.
    * Seperate SaveAs and Export Screens
    * Added Option to export via Script (Jython, JRuby etc)
    * Extended Record Selection option in the Layout Editor
    * Fixed several bugs in the Layout Editor.
    * Can include a child-layout multiple times in a Group-Layout
    * Extended Record Selection (layout editor). You can now use extended expresions
    
Changes Release 0.80.2
    * On the SaveAs/Export screen there is
      - Option to edit the output file (only if the recordEditor can work out the structure)
      - Option to keep the screen open
      - New Xsl transform option. You can use an external tool (e.g. saxon) but it must 
        be allocated at RecordEditor Startup. 
    * Velocity / Xslt opotions now remember theextension last used with a particular
      Template  
    
Changes Release 0.80.1  
  * Tree (including Xml)
    - New Tree Csv-Export
    - 2 Tree Variables (root & nodeList) provided to Velocity templates
    - 2 new Velocity Templates (zXmlStyleSheet1.vm & zXmlStyleSheet2.vm)
  
  * Cobol Changes
    - New option to import all Cobol Copybooks in a directory
    - Reintroduced Batch load of cobol copybooks (see runCobolBatchLoad.Bat). This tool
      allows single files to be loaded or whole directories
    
  * LayoutEditor
    - A child record can be used multiple times in a Group record

    
Changes Release 0.69.1
    * New Field - Hide / Show function.
    * New Unknown layouts for use with unknown files
    * New Prefered Layout option in the Main Display
    * New Hex displays  (1 / 2 / 3 line) in the Main display.
    * Fix for Cobol Sign Seperate fields
    * Enhanced Layout Wizard:
      - Support for multi-record files
      - Field Search added
      - Improved Binary Files support 
      - File Format detection added
    * New Layout Import From Xml Files in a Directory / Export Layouts To Xml Files in a directory
    * New CSV Styles to support "Column Names" in Quotes (Record Styles 3->5) + some CSV fixes.
    * New Option to change layout after the files has been loaded
    * Internal changes to support "Object Data Stores" like Protocol Buffers and Avro
      See Protocol Buffer Editor and Avro Editor's
    * Support for H2 Database's Mixed Mode
    * Added optional Error-File and error-limits to the online / batch copy utilities
    * Added new -il/-ol (input/output layout) and -ef (error file) options to the batch utility

Changes Release 0.67/0.68
    * improved PC Cobol support (particularly Open Cobol)

Changes Release 0.65
    * File Compare Utility - This is a field based compare; fields can be ignored or data in files of different formats can be compared.
    * Option to Save / Import Record-Layouts (Descriptions of a file) as a XML file. These XML-Layouts can be used in the Sister Product JRecord to Read / Write files from a Java Program.
    * Options to save files as XML.
    * Minor enhancements to File and View Menu's.
    * Can Save Filters, Sort Tree's and Record Tree's definitions and execute later.
    * Can Summmarise fields in a Sort Tree. 


Changes Release 0.62
    * 3 new Tree views added to editor.
    * New Column view (where rows are displayed as columns going across the page).
    * New Parameter field editors in the Layout Editor
    * Can Import / Export Record-Layouts (copybooks) in a Csv Format
    * New Option to copy copybooks between DB's
    * Very limited Support for editing XML files (viewing / limited changes to 
      existing XML files).

Changes Release 0.61
    * Improved Options Editor.
    * Can now choose the Look and Feel / Icons.
    * Improved date support.
    * Full line display mode
    * Can update via Text & Hex 
    * Bug fixes (Date and CSV files). 
