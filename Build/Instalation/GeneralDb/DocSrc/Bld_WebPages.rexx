/* Rexx 

	Rexx Program to build documentation
*/

    logo='sourceforge'
	b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"'  /* home */
	
	/*'C:\Regina\regina.exe ' b2h '"reLibDir.dcf" (LOG=reLibDir.log QUIET)'*/
	
	param = "LOG=RecordEditIntro.log AUTOSPLIT=2 SPLITLINK=NO TOCRET=NO QUIET FRAMEPOS=TOP FRAMETOCOPT='TAB LOGO="""logo""" " 


	'C:\Regina\regina.exe ' b2h '"RecordEditIntro.dcf" ('param')'
	
	'C:\Regina\regina.exe ' b2h '"Versions.dcf" '
	/*'C:\Regina\regina.exe ' b2h '"iRE_Upgrade.dcf" '*/
	
	'del RecordEditIntro.htm'
	'copy record*.htm Website\'
	'del  record*.htm'
