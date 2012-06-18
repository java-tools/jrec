/* Rexx 

	Rexx Program to build documentation
*/
    if linux() then do
	    say 'Unix ...'
	    regina = 'rexx '
	    instalation = '/home/bm/Work/RecordEditor/Instalation/'
	    b2h='/home/bm/Work/Rexx/B2H/B2H.REXX'
	    sep= '/'
	    Copy = 'cp'
	    del='rm'
	    html = 'htm'
	    rename='mv'
    end; else do
 	    say 'Windaows ...'
	    regina = '"C:\Regina\regina.exe"'
	    regina = "regina"
	    instalation = 'C:\Users\mum\Bruce\Work\RecordEditor\Build\Instalation\' 
	    b2h = 'C:\Users\mum\Bruce\Work\Rexx\B2H\B2H.REXX'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
   end
    q='"'

      say  regina b2h '"ProtoBufIntro.dcf HTMPEXT='html 'HTMLEXT='html 'LOG=intro.log QUIET"'
      regina b2h '"ProtoBufIntro.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=intro.log QUIET)"'
return


linux:
return 0
      
