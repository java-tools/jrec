/*
	Build Protocol Buffer installer 
*/
	parse arg vers x
	if vers = "" then vers = '0.69.2c'
	
	if vers <> "" then do
		vers = '_'vers
	end
	
	
	call CALLIZPACK 'Csv_Editor.xml reCsvEditor_Installer'vers'.jar'

