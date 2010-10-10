/* rexx */

		zipPgm = '"C:\Program Files\7-Zip\7z.exe"'
		version = "v062"

        Zip    = 'E:\Work\RecordEdit\Instalation\Cobol2Csv_'version'.zip'
        ZipRe  = 'E:\Work\RecordEdit\Instalation\Re2Csv_'version'.zip'
		buDir  = 'E:\Work\RecordEdit\Instalation\fix2csv\'
		srcDir = 'E:\Eclipse\workspace\RecordEdit_Utilities_061\src*\net\sf\RecordEditor\examples\fix2csv\'
		srcDir = 'I:\Eclipse\workspace\RecordEdit_Utilities_062\src*\net\sf\RecordEditor\examples\fix2csv\'
		
		"del '"Zip"'"
		"del '"ZipRe"'" 
		
		zipPgm 'a  -tzip' zip   '-xr!'buDir're2*' '-ir!'buDir'*' '-xr!'buDir'\lib\RecordEdit.jar'
		zipPgm 'a  -tzip' zip   '-xr!'srcDir're2*' '-ir!'srcDir'*'
/*
        zipPgm 'a  -tzip' zip   '-ir!E:\Eclipse\workspace\recordEdit\*' ,
		                        '-xr!E:\Eclipse\workspace\recordEdit\edit\*' ,
								'-xr!E:\Eclipse\workspace\recordEdit\info\*' ,
								'-xr!E:\Eclipse\workspace\recordEdit\layoutEdit\*' ,
								'-xr!E:\Eclipse\workspace\recordEdit\net\sf\RecordEditor\*'
*/
		zipPgm 'a  -tzip' ZipRe '-xr!'buDir'Cobol*' '-ir!'buDir'*' '-xr!'buDir'\lib\RecordEdit.jar'
		zipPgm 'a  -tzip' zipRe   '-xr!'srcDir'Cobol*' '-ir!'srcDir'*'

		
