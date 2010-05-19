/* Rexx 

	Rexx Program to build documentation
*/

	b2h = "C:\BMstuff\Rexx\B2H\B2h4_8\B2H.Rexx"   /* work */
	b2h = '"E:\Work\Rexx\B2H\B2H.REXX"' /* home */
	
/*	'C:\Regina\regina.exe ' b2h ' "Example.dcf" ' ,
		'(NOMAINTOC index  TOCRET=NO LOG=Aman.log QUIET SHOWHTML FRAMEPOS FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')'

	'C:\Regina\regina.exe ' b2h ' "xmple.dcf" ' ,
		'(NOMAINTOC index AUTOSPLIT=1 SPLITLINK=no  TOCRET=NO LOG=Aman.log QUIET SHOWHTML FRAMEPOS FRAMETOCOPT=''jsTree style=Win level_ident=16 width=240 img_size= expand=YES '' FrameTocSize=''21%''  FrameTocHdColor="#FF0080" FrameTocCss=jsBlueBg FrameTocBody=''bgcolor="#001F66"'')'
*/
		

	'C:\Regina\regina.exe ' b2h ' "Cobol2CSV.dcf" ' ,
		'(TOCRET=NO LOG=c2c.log QUIET SHOWHTML )'
		
		
	'C:\Regina\regina.exe ' b2h ' "re2CSV.dcf" ' ,
		'(TOCRET=NO LOG=c2c.log QUIET SHOWHTML )'
		
