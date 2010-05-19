/*
	Build HSQLDB installer 
*/
	parse arg vers x
	if vers = "" then vers = '061b'
	
	if vers <> "" then do
		vers = '_'vers
	end
	of='E:\Work\RecordEdit\izPack\RecordEdit_Installer_for_GenericDB'vers'.jar'
	call cd 'E:\Work\RecordEdit\izPack'
	say of

	'"C:\Program Files\IzPack\bin\compile" Generic_RecordEdit.xml -b . -o' of ' -k standard'
	'copy' of 'G:\RecordEdit_Prj\Install\'