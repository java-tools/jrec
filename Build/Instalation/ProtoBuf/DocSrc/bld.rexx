/* Rexx 

	Rexx Program to build documentation
*/

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


      say  regina b2h '"ProtoBufIntro.dcf HTMPEXT='html 'HTMLEXT='html 'LOG=intro.log QUIET"'
      regina b2h '"ProtoBufIntro.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=intro.log QUIET)"'
