/* rexx 

	zip up  source code

*/
	parse arg vers x
	
	zipPgm  = '"C:\Program Files\7-Zip\7z.exe"'
	zipFile = 'RecordEdit_Zip_Examples_061.zip'
/*	zipMsA  = 're2jasper_zip_MsAccess.zip' */
	
	'del' zipFile
	'del' zipMsA
	rest =  '-ir!Docs\*.* -ir!..\GeneralDB\J*Doc\*.*' ,
	        '-ir!..\GeneralDB\J*Doc\*\*.* -ir!..\GeneralDB\J*Doc\*\*\*.*' ,
			'-ir!..\GeneralDB\J*Doc\*\*\*\*.* -ir!..\GeneralDB\J*Doc\*\*\*\*\*.*' ,
			'-ir!..\GeneralDB\src*\net\sf\RecordEditor\examples\*.*' ,
			'-ir!..\GeneralDB\src*\net\sf\RecordEditor\examples\*\*.*'
	zipPgm 'a  -tzip' zipFile  '-ir!Readme.html '  rest
/*	zipPgm 'a  -tzip' zipMsA   '-ir!re2jInstallation.html -ir!Jars\*.*' rest*/
