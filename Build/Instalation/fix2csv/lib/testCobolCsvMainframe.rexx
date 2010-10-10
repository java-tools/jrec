/* Rexx program to test bat files */

	/* process standard fixed width mainframe file, the default TAB character will be used as the field seperator */
	'Cobol2CsvMainframe.bat' ,
			  '-c C:\Program Files\RecordEdit\HSQL\Cobol\DTAR107.cbl' ,
			  '-i C:\Program Files\RecordEdit\HSQL\SampleFiles\DTAR107.bin' ,
			  '-o C:\Program Files\RecordEdit\HSQL\SampleFiles\DTAR107_tst.csv'

	/*
		process mainframe (VB dump format [includes Block-Descriptor-Word as well Record-Descriptor-word])
		it also uses a comma as the field seporator
	*/
	'Cobol2CsvMainframe_VBdump.bat -s , ' ,
			  '-c C:\Program Files\RecordEdit\HSQL\Cobol\DTAR1000.cbl' ,
			  '-i C:\Program Files\RecordEdit\HSQL\SampleFiles\DTAR1000_Store_file_large_Recfm_U.bin' ,
			  '-o C:\Program Files\RecordEdit\HSQL\SampleFiles\DTAR1000_Store_file_large_Recfm_U_tst.csv'

