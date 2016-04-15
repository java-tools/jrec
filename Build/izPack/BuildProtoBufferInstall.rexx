/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '0.97l'
	
	if vers <> "" then do
		vers = '_'vers
	end
	
	installer = 'ProtocolBuffer_Editor'vers'_Installer_pb_3.0beta.jar'
	call CALLIZPACK 'ProtoBuf_Editor.xml'  installer
	
	'java -jar' installer
/*	
	of='/home/bm/Work/RecordEditor/ru_ProtocolBuffer_Editor_Installer'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	

	'/home/bm/Work/IzPack/bin/compile ProtoBuf_Editor.xml -b . -o' of " -k standard"
*/

