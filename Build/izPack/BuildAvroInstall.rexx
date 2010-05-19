/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '069d'
	
	if vers <> "" then do
		vers = '_'vers
	end
	of='/home/bm/Work/RecordEditor/ru_Avro_Editor_Installer'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	

	/*'"C:\Program Files\IzPack\bin\compile" -?'*/
	/*'bin/compile RecordEdit_HSQL.xml -b . -o' of " -k standard"*/ /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\'*/
	'/home/bm/Work/IzPack/bin/compile Avro_Editor.xml -b . -o' of " -k standard" /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\' */

