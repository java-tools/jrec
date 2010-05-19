/* Rexx 

	Rexx Program to build documentation
*/

	b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"' /* home */
	
	if  ISUNIX() then do
	    say 'Unix ...'
	    regina = 'rexx '
	    instalation = '/home/bm/Work/RecordEditor/Instalation/'
	    jeRecord= '/home/bm/Work/JRecord/Docs/'
	    b2h='/home/bm/Work/Rexx/B2H/B2H.REXX'
	    sep= '/'
	    Copy = 'cp'
	    del='rm'
	    html = 'htm'
	    rename='mv'
	end; else do
	    say 'Windaows ...'
	    regina = 'C:\Regina\regina.exe '
	    instalation = 'E:\Work\RecordEdit\Instalation\'
	    jeRecord='E:\Work\JRecord\Docs\'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
	end

	say ' '
	say ' 'copy del sep html
	say ' '
	
	/*regina b2h '"reLibDir.dcf" (LOG=reLibDir.log QUIET)'*/
	
	regina b2h '"reSampleFiles.dcf (HTMPEXT='html'  LOG=reSampleFiles.log )"'
	regina b2h '"RecordEditIntro.dcf (HTMPEXT='html' LOG=RecordEditIntro.log QUIET)"'
	regina b2h '"ceCobolEditor.dcf (HTMPEXT='html' LOG=CE.log QUIET)"'
	del 'CobolEditor.'html
	rename 'ceCobolEditor.'html' CobolEditor.'html

	Call BuildJRecordDoc

	Call BuildDoc 'g 'instalation'GeneralDB'sep'Docs'sep

	Call BuildDoc 'h 'instalation'hsqldb'sep'Docs'sep
	Call BuildDoc 'a 'instalation'MSaccess'sep'Docs'sep
/*	Call BuildDoc 
	Call BuildDoc 'W'*/
Return


/**
 *  This procedure builds Welcome and User-Manuals for one version of the Record Editor
 *  It accepts 1 parameter of VersionId (Version Identifier) whish can be
 *     a - Microsoft Version
 *     h - HSQLDB server Version
 *     g - Generic Version
 *     w - Version for work
 */
BuildDoc:
parse arg VersionId dir x
	regina b2h '"re'VersionId'Welcome.dcf (HTMPEXT='html' LOG=Welcome'VersionId'.log QUIET)"'
	
	del VersionId'Welcome.'html''
	rename 're'VersionId'Welcome.'html' 'VersionId'Welcome.'html
	
	/* INDEX=YES INDEXREFLINK=YES */
	regina b2h ' "re'VersionId'Man.dcf ' ,
		'(HTMPEXT='html 'HTMLEXT='html 'NOMAINTOC  TOCRET=NO LOG=man'VersionId'.log QUIET SHOWHTML FRAMEPOS'  ,
		'FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')"'
			
	del VersionId'RecordEdit.'html''
	rename 're'VersionId'ManFR.'html' 'VersionId'RecordEdit.'html
	say '----' rename 're'VersionId'ManFR.'html' 'VersionId'RecordEdit.'html


	if dir <> "" then do
	    if ISUNIX() = 0 then do
		Dir = '"'Dir'"'
	    end
	    say '"'Dir ||  VersionId'RecordEdit.'html'"'
	    say '"'Dir're'VersionId'ManTC.'html'"'
	    say '"'Dir're'VersionId'Man.'html'"'

	    del '"'Dir || VersionId'Welcome.'html'"'	
	    del '"'Dir || VersionId'RecordEdit.'html'"'
	    del '"'Dir're'VersionId'Man.'html'"'
	    del '"'Dir're'VersionId'ManTC.'html'"'
	    
	    Copy VersionId'Welcome.'html' 'Dir	
	    Copy VersionId'RecordEdit.'html' 'Dir
	    Copy 'CobolEditor.'html' 'Dir
	    Copy 're'VersionId'Man.'html' 'Dir
	    Copy 're'VersionId'ManTC.'html' 'Dir
	end
Return


BuildJRecordDoc:

	regina b2h ' "jrEdit.dcf ' ,
		'(HTMPEXT='html 'HTMLEXT='html 'NOMAINTOC  TOCRET=NO LOG=jrEdit.log QUIET SHOWHTML FRAMEPOS'  ,
		'FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' ' ,
		'FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody='' bgcolor="#001F66"'')"'

	Copy 'jrEdit*.htm*' jeRecord
Return




