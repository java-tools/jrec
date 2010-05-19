/* Rexx 

	Rexx Program to build documentation
*/

    logo='sourceforge'
    home=1
    if home then do
		b2h = '"E:\Work\Rexx\B2H\B2H.REXX"'  /* home */

		'C:\Regina\regina.exe ' b2h 'zWebWizard.dcf (AUTOSPLIT=no SPLITLINK=no)'
    end; else do
		b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	
		/*'C:\Regina\regina.exe ' b2h 'HlpRe.dcf (AUTOSPLIT=YES SPLITLINK=no)'*/
		'C:\Regina\regina.exe ' b2h 'zWebWizard.dcf (AUTOSPLIT=2 SPLITLINK=no)'
	
		/*'copy HlpRe*.htm "G:\Its\Itas\SMS Support\Bruces\RecordEdit\docs\"'
		'copy HlpLe*.htm "G:\Its\Itas\SMS Support\Bruces\RecordEdit\docs\"'*/
    end
	
	
	
/*	call GenHTML 'RecordMain iRE_re005_RecordEditMain'
	call GenHTML 'RecordTable iRe_re010_RecordTableOption'
	call GenHTML 'SingleRecord iRE_re015_SingleRecord'
	call GenHTML 'Search iRe_re020_Search'
	call GenHTML 'Filter iRe_re025_Filter'
	call GenHTML 'SaveAs iRE_re030_SaveAs'*/
	
	
	
return
	/*'C:\Regina\regina.exe ' b2h '"reLibDir.dcf" (LOG=reLibDir.log QUIET)'*/
	
	/*param = "LOG=RecordEditIntro.log AUTOSPLIT=2 SPLITLINK=NO TOCRET=NO QUIET FRAMEPOS=TOP FRAMETOCOPT='TAB LOGO="""logo""" "
	'C:\Regina\regina.exe ' b2h '"RecordEditIntro.dcf" ('param')'*/

GenHTML:
parse arg name dcf x

	htmlName = 'hlpRE_'name'.htm'
	'C:\Regina\regina.exe ' b2h '"'dcf'.dcf" '
	'del' htmlName
	'rename' dcf'.htm' htmlName
return
