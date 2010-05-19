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
	    say 'Windaows ...'
	    regina = 'C:\Regina\regina.exe '
	    instalation = 'E:\Work\RecordEdit\Instalation\'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
	    q=''
	end

	
regina b2h q'Example.dcf ' ,
		'(HTMPEXT='html 'HTMLEXT='html 'NOMAINTOC index  TOCRET=NO LOG=Aman.log QUIET SHOWHTML FRAMEPOS FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')'q

/*	regina q'xmple.dcf ' ,
		'(HTMPEXT='html 'HTMLEXT='html 'NOMAINTOC index AUTOSPLIT=1 SPLITLINK=no  TOCRET=NO LOG=Aman.log QUIET SHOWHTML FRAMEPOS FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')'q
*/
		
	regina  b2h  q'xmple.dcf ' ,
		'(HTMPEXT='html 'HTMLEXT='html 'NOMAINTOC index AUTOSPLIT=1 TOCRET=NO LOG=xmpl.log QUIET SHOWHTML FRAMEPOS FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')'q
		

	copy 'Example*.htm 'instalation'GeneralDB'sep'Docs'sep
	copy 'Example*.htm 'instalation'hsqldb'sep'Docs'sep
	copy 'Example*.htm 'instalation'MSaccess'sep'Docs'sep		

