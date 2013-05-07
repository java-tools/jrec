/* Rexx 

	Rexx Program to build documentation
*/

	logo='sourceforge'
	
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
	    instalation = 'E:\tmp\RecordEditor\Build\Instalation\'  /*'E:\Work\RecordEdit\Instalation\'*/
	    b2h = '"E:\Work\Rexx\B2H\B2H.REXX"'
	    sep ='\'
	    copy='Copy'
	    del='del'
	    html = 'htm'
	    rename = 'rename'
	    q=''
	end
	
	/*'C:\Regina\regina.exe ' b2h '"reLibDir.dcf" (LOG=reLibDir.log QUIET)'*/
	
	param = "LOG=WebSite.log AUTOSPLIT=2 SPLITLINK=NO TOCRET=NO QUIET FRAMEPOS=TOP FRAMETOCOPT='TAB LOGO="""logo""" " 


	regina b2h  '"WebSite.dcf ('param')"'
	

