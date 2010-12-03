/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '069h'
	
	if vers <> "" then do
		vers = '_'vers
	end
	
	call CALLIZPACK 'ProtoBuf_Editor.xml ru_ProtocolBuffer_Editor_Installer'vers'.jar'	
	
/*	
	of='/home/bm/Work/RecordEditor/ru_ProtocolBuffer_Editor_Installer'vers'.jar'
	
	say of
	
	call cd '/home/bm/Work/RecordEditor/izPack'
	

	'/home/bm/Work/IzPack/bin/compile ProtoBuf_Editor.xml -b . -o' of " -k standard"
*/

