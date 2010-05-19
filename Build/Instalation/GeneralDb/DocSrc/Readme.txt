The *.dcf files are the documentation source. It is in IBM Bookmaster format (GML tag set).
There is a free Bookmaster to HTML converter (B2H written in rexx) on IBM VM download page
I have included these files for completeness.


DCF files

AWelcome.DCF - Access version Welcome document
HWelcome.DCF - HSQLDB versions Welcome document
GWelcome.DCF - Generic DB versions Welcome document

reAMan.dcf - Microsoft Access versions Manual
reHMan.dcf - HSQLDB versions Manual
reGMan.dcf - Generic DB versions Manual


iRe_*.DCF  - various include files (.im macro file imbeds files).


these primary documents basically imbed other files (iRe_*.DCF)

I have made some made some changes to the standard B2H (version) because:
* Bugs in the Regina implementation
* Enhancements (ie to allow for Frames / Javascript menus)

I have included a document B2H_differences.html which list my changes to the standard
B2H.rexx (Version 4.7). There is also text version of the difference file B2H_differences.txt

Bld_Documentation.rexx will build the documentation (note you will have to change the 
directories

The Rexx program Bld_Documentation.Rexx builds all the documents, it requires my
version of B2H which I can not distribute

Finally

RecordEdit.Druid is the Druid DB definition

