 Protocol Buffers Editor 0.80.5
*==============================*

This program is for editing Binary Files in 
Googles Protocol Buffers format (2.4.1 and hopefully latter).
It is built on top of the RecordEditor Project 
(http://record-editor.sourceforge.net).

The editor requires Protocol buffers 2.4.1 (or later ???, tested with 2.41)
to be installed. The Protocol Buffer programs need to be 
on the Program Path, The packages uses the protoc command to compile .proto files.



Changes 0.80.5
  * Use Protocol Buffers 2.4.1
  * Enhanced SaveAs/Export : Tree Export option for Xml, Csv and Fixed.
  * Option to Edit saved/exported File (if it is in a supported format: Xml, Csv, Fixed)
  * Seperated SaveAs and export functions
  * Export using Velocity Templates 
  * Option to set the size of the screen when the program starts 
    (Edit Options >>>> Looks >>>> Screen properties)
  * Esc closes most utility screens (but not main edit screens)
  * Can delete records with delete key


Changes 0.69h
  * Problem fix's
  * Can use Velocity Templates when Exporting data from the file being editted
  
Changes 0.69g
  * Support for import in the proto definition
  * Can specify protoc command + supply extra options to protoc command
  * New option to view files proto definition

Changes 0.69f
  * Bug fixes (Insert code + memory leak)

Changes 0.69e
  * Bug fixes (particularly Compare)
  * Internal changes for variety reasons including improved automatically Testing

Changes 0.69d
  * Basic implementation for bytes
  * Boolean fields shown in check box
  * Enum arrays now displayed in Combobox's
  * "Empty" required fields are now displayed with a light Red background
  * Option to highlight Empty Fields (light Blue background) 
    via Edit >>> Highlight Missing fields option
  * New Clear Field option on right click button

Changes 0.69c
  * Fixed problem with spaces in proto file name
  * Fix editing empty file issue (Tree View)
  * Fix for Repeated Items (elementry data e.g. Repeated integers)
  * Fixed some problems with optional fields
  * Enum's are now selected from a combo box (exept when a repeating field)
  * New Array Popup / fixed some problems with Array handling
  * several other fixes

Changes 0.69b
  * Added Show/Hide fields to Tree display
  * Fixed some problems in the  Show/Hide fields functions
  * Fixed issue with "Prefered Layout" no displaying all fields
  * Updated to later version of the RecordEditor 


