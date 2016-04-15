The RecordEditor is a Data File Editor (for both CSV (Comma / tab seperated fields)
and fixed field positions files typically used in Cobol / IBM Mainframe).
It uses Record-Layout to format a file in a human readable format. 
It can edit both Text and binary files in PC / Unix / IBM Mainframe formats.

The Record-Layouts are stored in a DB, but they can be imported from a Cobol Copybook


### Changes 0.97m

* Tidy-up of Cobol-Import screens
* Added option to generate JRecord IOBuilder code to the Cobol Import screen
* New cb2xml.jar (95.6) - Cobol import


### Changes 0.97k

* Fix problems introduced in 0.97j (xml / PO / Tip file)
* Fix issue with importing Xml Schema's (for formats).
* Added support for using combo's in "prefered" layout in Sort Tree's
* Fix problem on Cobol-Copybook import where Record-Details are not copied to the DB.
* Added Create Combo to Record Layout drop down menu
* Added Generate Record Names right click menu in Layout Wizard

Changes 0.97j

* Update to find (when not found)
* Update to ReplaceAll message
* New Improved Cobol Import 

Changes 0.97h/i

* support usage supported a group level (Cobol)
* Fix for use with multiple screens
* Option to select the screen (if multiple displays).
* Record Selection fix
* Fix for Exporting layouts as Xml

### Changes 0.97g
* prepare "installers"
* Add extra trace and problem reporting
* Add a new Character-set (font) component that will list available character-sets
* fix for Csv files where there is a large number of columns
* Fix for filtering / saving large files
* Added support for combo-box's in "preferred" mode


### Changes 0.97
* Java 8 / OpenJDK fixes.
* Line size option on Cobol-Import screen
* Some cobol fixes
* Using the latest version of cb2xml
* Recent Directory pane of file search screens & more right click menu options 
  on file search screens


### Changes 0.96h
* Updated Csv-Parsers
* More Cell operations (Right click and Edit menus)
* Added Recent Directory option to File Menu
* New JavaScript macros (Delete-Duplicates and Show Duplicates)
* New Jython / Groovy macros to save files as JSon. These require Jython / Groovy interpreter's to be downloaded separately from there Project sites
* New Delete-Duplicate / Sort Macro-Build functions
* Removed Java-7 dependencies that had crept in, Should run in Java 6 again.
* Improved major error reporting
* Fixed issue with spaces in filename (when used as a Startup parameter for RecordEditor)
* Allign Type names with JRecord / Cobol-Editor to make it easier to transfer Xml-Schemas between the programs
* Allign File-Structure names with JRecord / Cobol-Editor


### Changes 0.96f
* For unsigned comp-3 fields, the unsigned nyble will be used instead of the positive sign nyble (User Request)
* New Layout to Csv Compare option on the Csv compare screen. (Note: Saving this compare will no work yet.
* Resolved a few differences between JRecord's processing of Schema-Xml and RecordEditor's processing of Schema-Xml.
* New Fixed-Width-Char option. This is specifically for Multi-Byte Character-set (UTF-8, UTF-16 etc) fixed width files.
* Minor improvements/fix to the Macro-Ediors Save-All button.
* New Example Java-Script Macros (Script directory in the zip file) to delete duplicate Records and to show duplicate records in a seperate view
* New Example Jython and Groovy Macros (Script directory in the zip file) to write a RecordEditor-view as JSON. 


### Changes 0.96e
* Zoned-Decimal in Ebcdic-German + several other European language versions of Ebcdic
* Several fix's for Fixed Width 16 bit charsets (e.g. UTF-16 etc)
* Fix's / enhancements for Unicode Csv files 

### Changes 0.96d
* New Text view where fields are highlighted
* Allow block cell selections and added block cell coping/pasteing
* Major changes to Installers -  Better Windows UAC handling, can install in Windows"User" account, Better handling of 
multiple user's on PC.
* Can specify Charset on Csv and Fixed width export
* New Csv compare function
* Added file charset detection for Ebcdic files
* Added Edit-Layout button to tool bar
* Key assignment changes, Record-Copy Record-Cut, Record-Paste, Record-Insert and Record-Delete are now assigned to keys Alt-C, Alt-X, Alt-V, Alt-Insert and Alt-Delete. Control-C and Control-V are now for cell copying and pasting.
* Automatic backup of File Layouts (Schema's) as Xml files (to directory &lt;RecordEditorUserDirectory&gt;Copybook/SchemaBu).
* Fixed problem in copying to Ebcdic Files (that use a new-line character) on Windows machines. 
* Fix problem new Xml export function
* Fix System and record-seperator problems in Xml export

### Changes 0.96c

* New Script build function. This function will build sample Scripts (Macro's) for the current file. It will make it easier to start writing scripts.
* Improvements in loading lage gzip files
* Problem Fix (see https://sourceforge.net/p/record-editor/bugs/5/)
* several improvements (see https://sourceforge.net/p/record-editor/support-requests/3/) added file checking + cleared MSG field.
* Added option to view HTML to HTML compare function.


### Changes 0.96b

* Improvements in handling big files (Filtering, copying, pasteing).

### Changes 0.96a

* File name Fields are now combo box's (with most recent files with a file search button straight after the Field.
* Updated XML-Layout import / export functions
* New Load XML-Layout option (to load a single XML-Layout
* Several Big-File related changes including filter improvements.

### Changes 0.95d

Changes include

* Extra -layout (specify a layout for a file) option when running the fulleditor
* New Java method for starting the RecordEditor with a file/record-layout
* New experimental file-fields that have  both a last-used combo option + a file search button 


### Changes 0.95c

* Fixing several problems with the Csv code
* Adding HTML type for Csv fields containing HTML.

### Changes 0.95b

* The XML **CobolName** tag for fields is now imported into the RecordEditor
* For **Generic-Csv** and **Basic Csv** dialogue's, Embedded **<new-Line>** character in fields
are now supported. Also field quotes can now be any individual character (or multiple characters).


