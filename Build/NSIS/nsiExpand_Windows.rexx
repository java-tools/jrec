/*
	Call main "-i xRecordEdit_HSQL.nsi"
	Call main "-i xRecordEdit_MSAccess.nsi "  
*/
	
	Call main "-i xRecordEdit_MSAccess.nsi -o RecordEdit_Upgrade_MSAccess.nsi -p RecordEdit_Upgrade_for_MSAccess_062.exe -d 01/03/07 -psc upgrade"
	Call main "-i xRecordEdit_HSQL.nsi -o RecordEdit_Upgrade_HSQL.nsi -p RecordEdit_Upgrade_for_HSQL_062.exe -d 01/03/07 -psc upgrade"


return

main:
parse arg pgmParmeters

    Call A000_Init

    Call B000_Process

    Call C000_Fin
Return

A000_Init:

	call A100_InitVars 
	call A200_ParsePgmParams
return


A100_InitVars:

	numFiles = 0

	Yes      = 1
	No       = 0

	MoreData = yes
   
	!Overwrite   = "OVERWRITE"
	!Outpath     = "OUTPATH"
	!Inpath      = "INPATH" 
	!tab         = '09'x
	!InputFile   = "I"
	!OutputFile  = "O"
	!ProgramFile = "P"
	!Default     = "DEFAULT"
	!DatePref    = "D" 
	!DateCheck   = "DATECHECK"
	!Psc         = "PSC"
	!Proc        = "PROC"
	!xProc       = "XPROC"
Return

A200_ParsePgmParams:

	pgmParms. = ""
	Curr = !InputFile
	pgmParmeters.!Psc = 'normal'
	
	Do while pgmParmeters <> ""
		parse var pgmParmeters p pgmParmeters
		
		if substr(p, 1, 1) = "-" then do
			say Curr":" pgmParms.Curr
			Curr = Translate(Substr(p, 2))
		end; else do
			pgmParms.Curr = pgmParms.Curr p
		end
	end
	say ":"Curr":" pgmParms.Curr
	
	MinDate = strip(pgmParms.!DatePref)
	if MinDate <> "" then do
		say ">"MinDate"<"
		MinDate = Date("S",MinDate,"E")
	end
	
	filename = strip(pgmParms.!InputFile)
	Outfile  = strip(pgmParms.!OutputFile)
	PgmFile  = strip(pgmParms.!ProgramFile)
	PscValue = pgmParmeters.!Psc
	say 'pscvalue' PscValue
	
	if Outfile = "" then do
		Outfile = "EX"filename	
		Say "Using Default File name" Outfile
	end
Return

B000_Process:

	Call R000_ReadFile
	do while MoreData
		/*say Line*/
		if substr(strip(Line), 1, 1) = "<" then do
			Call B100_GetFullLine
			
			Select
				when (Command = "EXPAND")      then Call B200_ProcessExpand
				when (Command = "WRITEDELETE") then Call B300_ProcessDeletes
				when (Command = "OUTFILE")     then Call B400_ProcessOutfile
				when (Command = "PSC")         then Call B500_ProcessPsc
				when (Command = "/PSC")        then nop
				Otherwise
					say "Invalid Command " Command
			end
		end; else do
			Queue Line
		end

		Call R000_ReadFile
	end
Return

B100_GetFullLine:
	FullLine = ""
	/*parse var line "<" line
	say line*/

	/*say "~~" B910_EndsWith(">,"Strip(line)) line*/
	FullLine = FullLine || Line" " 
	
	do while moreData & (B910_EndsWith(">,"Strip(line)) = no)
		Call R000_ReadFile

		FullLine = FullLine || Line" " 
	end
	
	parse var FullLine "<" text ">"
	
	Text = Strip(Strip(translate(Text," ",!tab),"T"),"T","/")
	
	parse var text Command rest
	Command = Translate(Command)
	ll = command
	
	Attributes. = ""
	
	DateCheck = no;
	do while rest <> ""	
		parse var rest attr "=" temp
		attr = translate(strip(attr))

		if substr(temp,1,1) = '"' then do
			parse var temp '"' val '"' rest
		end; else if substr(temp,1,1) = "'" then do
			parse var temp "'" val "'" rest
		end; else do
			parse var temp val rest
		end
		
		Attributes.attr = val
		ll = ll ' >'attr"="val'<'
	end
	   
	if translate(Attributes.!DateCheck) = "YES" then do
		DateCheck = yes
	end

	
	/*say ll*/
Return


/* Process Expand Tag - ie write all files that match a directory tag*/ 
B200_ProcessExpand:

    Queue ""
	Call G100_AddFile ""
	
	opath = Attributes.!Outpath
	
	if opath <> "" then do
		outputPath = opath
		if Attributes.!overwrite <> "" then do
			Queue "  SetOverwrite" Attributes.!overwrite
		end
		Queue '  SetOutPath "'outputPath'"'
	end
	
	Call B210_Expand "NAME"
	
	do i = 1 to 50
		Call B210_Expand "NAME"i
	end
Return


B210_Expand:
	parse arg attrName x
	
	Name = Attributes.attrName
	path = Strip(Attributes.!inpath,"T","\")"\"
	
	/*if B910_EndsWith("E",attrName) then  say "@@ " B910_EndsWith("E,"attrName) ">" attrName ">"Name"<"*/

	if Strip(Name) <> "" then do
		/*say attrName ">>" name "~~" path || Name*/ 
		ADDRESS SYSTEM "Dir" path || Name WITH OUTPUT STEM dirs.
		found = no
		do j= 5 to dirs.0
			parse var dirs.j 1 FileDate 11 40 file 
			/*say fileDate '~' file*/
			if substr(file,1,1) <> " " & Strip(file,,".") <> "" ,
			& B910_EndsWith("~,"file) = no & B910_EndsWith(".bak,"file) = no then do
				Call G100_AddFile outputPath"\"file
				
				if B211_FileOk() then do
					Queue '  File "'path || file'"'
					found = yes
				end
			end
		end
		
		if found = no then do
			say 'No file found for' path || Name
		end
	end
Return

B211_FileOk:


    if DateCheck = no then do       
		Return yes
	end; else if MinDate <> "" then do
		fdate = substr(FileDate, 7)substr(FileDate, 4, 2)substr(FileDate, 1, 2)
		/*say FileDate ":" FDate ">=" MinDate ":" (FDate >= MinDate)*/
		
		return FDate >= MinDate
	end

Return yes


B300_ProcessDeletes:

	do i = 1 to numFiles
		if dfiles.i = "" then do
			Queue ""
		end; else do
			Queue '  Delete "'dfiles.i'"'
		end
	end
Return


B400_ProcessOutfile:

	if PgmFile <> "" then do
		Queue 'OutFile "'PgmFile'"'
	end; else do
		Queue 'OutFile "'Attributes.!Default'"'
	end
Return

B500_ProcessPsc:

	if PscValue <>"" then do
		if Attributes.!proc <> PscValue | Attributes.!xProc = PscValue then do
			Call R000_ReadFile
			do while MoreData & (translate(substr(strip(line), 1, 5)) <> "</PSC")
				Call R000_ReadFile
			end
		end; else say '... ' PscValue ' Attributes.!proc' Attributes.!proc ' ' Attributes.!xProc
	end

Return


/* Checking if line ends with a character */
B910_EndsWith:
parse arg ch "," zLine

	/*say ch ">>"zLine"<<"*/
	if zLine <> "" & length(zline) >= length(ch) then do
		if substr(zLine, length(zLine) - length(ch) + 1, 1) = ch then do
			return yes
		end
	end
return no


C000_Fin:

    "Del" Outfile
	Do Queued()
		parse pull l
		rc = lineout(Outfile, l)
	end
Return


G100_AddFile:
parse arg zFile x

	numFiles = numFiles + 1
	dfiles.numFiles = zFile
Return


R000_ReadFile:

    if lines(filename,'N') then do
       Line= LineIn(filename)
    end; else do
       line = ''
       MoreData = no
    end
Return