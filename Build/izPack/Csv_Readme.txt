 Csv Editor 0.95
*===============*

This program is for editing Csv Files.
It is built on top of the RecordEditor Project 
(http://record-editor.sourceforge.net).

The editor requires at least Java 6 to run, but java 7 is prefered


Changes
Version 0.95


  * Added basic Script-Editor to the Script-Run dialog
  * Extra Functions / Methods for use in Macro Scripts, More example scripts and Script-Documentation.
  * Problem fix's and general cleanup.
  * Updates to How to documentation for Scripts.
  * New Text Editor View.
  * New positive binary types (Mainly for Cobol).
  * Minor updates to Open File to improves looks and make better use of screen space.
  * Added 2 new CsvParsers:
    o Basic - Delimiter all fields - ensure there is a delimiter for every field (even when a field is not present)
    o Basic - Delimiter all fields + 1 - ensure there is a delimiter for every field + an extra delimiter after the last field


Version 0.90
  * Related screens are now displayed as tabs on the one screen (default).
    There is still the option to display each view in its own screen.
  * Different views can be docked / undocked with each other
  * List screens can now have a Record Screen where the current record is displayed.
  * Support for foreign languages via GetText po files / resource bundles produced via GetText.
    There are sample language files generated using Google Translate
  * Filter - added And / Or operators, added Starts With operator
  * Enhanced HTML Export
  * Option to Open Exported file
  * Highlight changed fields on Compare screen (Feature request 3532674).
  * Updated Find function
  * Minor Gui changes


Version 0.80.6
  * Can now view/edit Xml and Fixed Width files
  * Enhanced SaveAs / Export options
    - Seperate SaveAs operate
    - Option to Edit exported file.
    - Tree Export on Csv / Fixed Width options
    - Xsl Transform option (Xml) export
    - Can export using a Script (JavaScript supported out the box,
      other scripting languages (Jython, JRuby etc) can be added).
  * GUI Changes
    - option to set initial Size of the Editor (Full Screen, Last Size or Set Size)
    - Esc will close most utility screens (but not the main screens).

