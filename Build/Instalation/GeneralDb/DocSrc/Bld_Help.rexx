/* Rexx 

	Rexx Program to build documentation
*/
	if  ISUNIX() then do
	    say 'Unix ...'
	    regina = 'rexx '
	    instalation = '/home/bm/Work/RecordEditor/Instalation/'
	    b2h='/home/bm/Work/Rexx/B2H/B2H.REXX'
	    sep= '/'
	    Copy = 'cp'
	    del='rm'
	    html = 'htm'
	    rename='mv'
	    q='"'
	end; else do
	    say 'Windows ...'
	    regina = 'C:\Regina\regina.exe '
	    instalation = 'C:\Users\mum\Bruce\Work\RecordEditor\Build\Instalation\'  /*'E:\Work\RecordEdit\Instalation\'*/
	    b2h = 'C:\Users\mum\Bruce\Work\Rexx\B2H\B2H.REXX'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
	    q=''
	end

    logo='sourceforge'
    home=1
  /*  if home then do*/

		regina b2h q'HlpRe.dcf (HTMPEXT='html 'HTMLEXT='html 'AUTOSPLIT=YES SPLITLINK=no)'q
		regina b2h q'HlpLe.dcf (HTMPEXT='html 'HTMLEXT='html 'AUTOSPLIT=2 SPLITLINK=no)'q
		regina b2h q'HlpCe.dcf (HTMPEXT='html 'HTMLEXT='html 'AUTOSPLIT=2 SPLITLINK=no)'q
		regina b2h q'diff.dcf  (HTMPEXT='html 'HTMLEXT='html ')'q
	
		copy 'HlpRe*.htm 'instalation'GeneralDB'sep'Docs'sep
		copy 'HlpRe*.htm 'instalation'hsqldb'sep'Docs'sep
		copy 'HlpRe*.htm 'instalation'MSaccess'sep'Docs'sep
		copy 'HlpLe*.htm 'instalation'GeneralDB'sep'Docs'sep
		copy 'HlpLe*.htm 'instalation'hsqldb'sep'Docs'sep
		copy 'HlpLe*.htm 'instalation'MSaccess'sep'Docs'sep
		copy 'HlpCe02.htm 'instalation'GeneralDB'sep'Docs'sep
		copy 'HlpCe02.htm 'instalation'hsqldb'sep'Docs'sep
		copy 'HlpCe02.htm 'instalation'MSaccess'sep'Docs'sep
		copy 'diff.htm 'instalation'GeneralDB'sep'Docs'sep
		copy 'diff.htm 'instalation'hsqldb'sep'Docs'sep
		copy 'diff.htm 'instalation'MSaccess'sep'Docs'sep
  /*   end; else do
		b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"  
	
		'C:\Regina\regina.exe ' b2h 'HlpLe.dcf (AUTOSPLIT=2 SPLITLINK=no)'
	
		'copy HlpRe*.htm "G:\Its\Itas\SMS Support\Bruces\RecordEdit\docs\"'
		'copy HlpLe*.htm "G:\Its\Itas\SMS Support\Bruces\RecordEdit\docs\"'
    end*/
	
	
	
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
