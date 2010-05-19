/*
	Build HSQLDB installer 
*/
	parse arg vers x
	if vers = "" then vers = '069'
	
	if vers <> "" then do
		vers = '_'vers
	end
	of='/home/bm/Work/RecordEditor/RecordEdit_Installer_for_HSQL'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	

	/*'"C:\Program Files\IzPack\bin\compile" -?'*/
	/*'bin/compile RecordEdit_HSQL.xml -b . -o' of " -k standard"*/ /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\'*/
	'/home/bm/Work/IzPack/bin/compile RecordEdit_HSQL.xml -b . -o' of " -k standard" /* version 3.10  -h 'C:\Program Files\IzPack\doc\izpack\' */

