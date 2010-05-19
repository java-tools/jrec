/*
	Build HSQLDB installer 
*/
	parse arg vers x
	if vers = "" then vers = '062a'
	
	if vers <> "" then do
		vers = '_'vers
	end
/**	of='E:\Work\RecordEdit\izPack\RecordEdit_Installer_for_GenericDB'vers'.jar'
	call cd 'E:\Work\RecordEdit\izPack'*/
	of='/home/bm/Work/RecordEditor/izPack/RecordEdit_Installer_for_GenericDB'vers'.jar'
	call cd '/home/bm/Work/RecordEditor/izPack'
	say of

/*	'"C:\Program Files\IzPack\bin\compile" Generic_RecordEdit.xml -b . -o' of ' -k standard'*/
	'/home/bm/Work/IzPack/bin/compile Generic_RecordEdit.xml -b . -o' of ' -k standard -l 9'
	/*'copy' of 'G:\RecordEdit_Prj\Install\'*/