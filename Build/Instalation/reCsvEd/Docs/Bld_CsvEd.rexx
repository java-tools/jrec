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
	    regina = '"C:\Regina\regina.exe"'
	    regina = "regina"
	    instalation = 'C:\Users\mum\Bruce\Work\RecordEditor\Build\Instalation\' /*'E:\Work\RecordEdit\Instalation\'*/
	    jeRecord='C:\Users\mum\Bruce\Work\JRecord\Docs\'
	    b2h = 'C:\Users\mum\Bruce\Work\Rexx\B2H\B2H.REXX' /* home */

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


    regina b2h q'HlpCsvEd.dcf (HTMPEXT='html 'HTMLEXT='html 'AUTOSPLIT=2 SPLITLINK=no)'q
    regina b2h '"reCsvEd.dcf  (HTMPEXT='html 'HTMLEXT='html 'LOG=Copy.log QUIET' ,
				'NOMAINTOC  TOCRET=NO FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'' ' ,
				')"'
    regina b2h "howTo.dcf"
				
ISUNIX:
return 0
