The RecordEditor is a Data File Editor (for both CSV (Comma / tab seperated fields)
and fixed field positions files typically used in Cobol / IBM Mainframe).
It uses Record-Layout to format a file in a human readable format. 
It can edit both Text and binary files in PC / Unix / IBM Mainframe formats.

The Record-Layouts are stored in a DB, but they can be imported from a Cobol Copybook

Changes Release 0.80.4
    * Change the Initialisation process
    * Add Jars to the Export as Xslt option
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
