/*
	Build HSQLDB installer 
*/
	parse arg vers x
	if vers = "" then vers = '0.80.4'
	
	if vers <> "" then do
		vers = '_'vers
	end

	call CALLIZPACK 'Hsql_RecordEdit.xml RecordEdit_Installer_for_HSQL'vers'.jar'


/*  ----------------------------------------------------------------------------------

	of='/home/bm/Work/RecordEditor/RecordEdit_Installer_for_HSQL'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	


	'/home/bm/Work/IzPack/bin/compile RecordEdit_HSQL.xml -b . -o' of " -k standard"
	
    ----------------------------------------------------------------------------------- */

