/* Rexx 

	Rexx Program to build documentation
*/

	b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"' /* home */
	dir = "../Docs/"

	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"'  /* home */
	rexx = 'C:\Regina\regina.exe'
	copy = 'copy'
	
	b2h = '/home/bm/Work/Rexx/B2H/B2H.REXX' /* home - linux */
	rexx = 'rexx'
	copy = 'cp'
	html = 'htm'
	rename='mv'
	
	rexx b2h '"Copy.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=Copy.log QUIET)"'

/*	rexx b2h '"HowTo.dcf (HTMPEXT='html 'HTMLEXT='html 'LOG=HowTo.log QUIET)"'*/
	copy 'HowTo.htm "'Dir'"'	

