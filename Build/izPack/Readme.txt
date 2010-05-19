The RecordEditor is a Data File Editor (for both CSV (Comma / tab seperated fields)
and fixed field positions files typically used in Cobol / IBM Mainframe).
It uses Record-Layout to format a file in a human readable format. 
It can edit both Text and binary files in PC / Unix / IBM Mainframe formats.

The Record-Layouts are stored in a DB, but they can be imported from a Cobol Copybook

Changes Release 0.69
    * New Field - Hide / Show function (more work still needed).
    * New Unknown ??? layouts for use with unknown files
    * New Prefered Layout option in the Main Display
    * New Hex displays  (1 / 2 / 3 line) in the Main display.
      Currently it is display only (no updates) when in hex mode. 
    * Fix for Cobol Sign Seperate fields
    * New Layout Import From Xml Files in a Directory / Export Layouts To Xml Files in a directory

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
