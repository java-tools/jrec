/*
	Build HSQLDB installer 
*/
	parse arg vers x
	if vers = "" then vers = '0.80.4'
	
	if vers <> "" then do
		vers = '_'vers
	end

	call CALLIZPACK 'Generic_RecordEdit.xml RecordEdit_Installer_for_GenericDB'vers'.jar'
	
/*	of='/home/bm/Work/RecordEditor/RecordEdit_Installer_for_GenericDB'vers'.jar'
	call cd '/home/bm/Work/RecordEditor/izPack'
	say of


	'/home/bm/Work/IzPack/bin/compile Generic_RecordEdit.xml -b . -o' of ' -k standard -l 9'
*/

