/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '0.80.4'
	
	if vers <> "" then do
		vers = '_'vers
	end
	
	
	call CALLIZPACK 'Avro_Editor.xml ru_Avro_Editor_Installer'vers'.jar'

