/* Rexx 

	Rexx Program to build documentation
*/

	b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"' /* home */
	dir = "../Docs/"

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
	    regina = '"C:\Regina\regina.exe"'
	    regina = "regina"
	    instalation = 'C:\Users\mum\Bruce\Work\RecordEditor\Build\Instalation\' /*'E:\Work\RecordEdit\Instalation\'*/
	    b2h = 'C:\Users\mum\Bruce\Work\Rexx\B2H\B2H.REXX'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
	    q=''
	end

	
	regina b2h '"Copy.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=Copy.log QUIET)"'

	regina b2h '"HowTo.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=HowTo.log QUIET)"'
	copy 'HowTo.htm "'Dir'"'	

