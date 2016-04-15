 Protocol Buffers Editor 0.97l
*=============================*

This program is for editing Binary Files in 
Googles Protocol Buffers format (2.6.1 and hopefully latter).
It is built on top of the RecordEditor (0.97k) Project 
(http://record-editor.sourceforge.net).

The editor requires Protocol buffers 3.0beta (or later ???)
to be installed. The Protocol Buffer program protoc needs to be 
on the Program Path, The packages uses the protoc command to "compile" .proto files.


Changes 0.97l
    * Add check for duplicate map keys
    
    
Changes 0.97k
    * Use RecordEditor 0.97k
    * Added Combo's to 'Prefered' Layout
    * Make 'Prefered' the default layout 


Changes 0.96j
    * Fix for problem with entering "" in optional numeric fields
    * Enhanced mainscreen of Csv editor.
    
Changes 0.96i
    * New Recent-directories pane on file-chooser screens
    * New Character-set option on Csv & Fixed-Width exports
    * Substantial updates to Csv editor.
    * Fixed Java 8 && Open-Jdk issues 
    * More Example-Macro's and Macro Build functions.
      Note: Macro functions are only applicable to Single Message delimited files
    
Changes 0.95c
    * Support for Protocol Buffers extensions 

Changes Release 0.92
    * Related screens are now displayed as tabs on the one screen (default).
      There is still the option to display each view in its own screen.
    * Different views can be docked / undocked with each other
    * List screens can now have a Record Screen where the current record is displayed.
    * Find - option to start from the start (when the end is reached).
    * Filter - Group filters are now supported
    * Load (from saved Xml) option added to a filter, Sort, Sort Tree and Record-Tree screens
    * Daily Tips added to startup
    * various minor enhancements / fix's
    
Changes Release 0.88
   * New option to search for Proto definitions that match a Protocol Buffer file
   * Improved option setup when opening a protocol buffer file
   * Enhanced HTML export
   * Option to Open exported File with default application
   * Highlight changed fields in the Compare screen
   * Minor Gui changes
   * Minor changes to Help Menu (open manual / open forums etc)
   * Filter - And / or operators, extra selection lines + Start With operator
   * Find - Start With operator, minor changes
   
Changes Release 0.85
  * Many minor GUI changes mostly related to Windows and Nimbus Laf
  * Change Look and feel back to Native on Windows
  * Changes to find function to fix some issues
  * Added Csv / Xml File Editor


Changes 0.80.6
  * Use Protocol Buffers 2.4.1
  * Enhanced SaveAs/Export : Tree Export option for Xml, Csv and Fixed.
  * Option to Edit saved/exported File (if it is in a supported format: Xml, Csv, Fixed)
  * Seperated SaveAs and export functions
  * Export using Velocity Templates included as Standard
  * New Export via Script (Jython, JRuby etc). You will need to allocate the appropriate Jars
    (Edit Options >>>> Jars >>>> Optional Jars) to the Protocol Buffers Editor.
  * Option to set the size of the screen when the program starts 
    (Edit Options >>>> Looks >>>> Screen properties)
  * Esc closes most utility screens (but not main edit screens)
  * Can delete selected records with delete key
  * File Compare options moved From the File menu to Utilities Menu



