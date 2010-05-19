/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '069a'
	
	if vers <> "" then do
		vers = '_'vers
	end
	of='/home/bm/Work/RecordEditor/ru_ProtocolBuffer_Editor_Installer'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	

	/*'"C:\Program Files\IzPack\bin\compile" -?'*/
	/*'bin/compile RecordEdit_HSQL.xml -b . -o' of " -k standard"*/ /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\'*/
	'/home/bm/Work/IzPack/bin/compile ProtoBuf_Editor.xml -b . -o' of " -k standard" /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\' */

