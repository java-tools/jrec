/* rexx 

	zip up  source code

*/
	parse arg vers x
	
	zipPgm  = '"C:\Program Files\7-Zip\7z.exe"'
	
	tmpZip   = 're_dcf.zip'
	
	
	'del' tmpZip
	zipPgm 'a  -tzip' tmpZip '-ir!*.dcf -ir!*.rexx'  
	
	