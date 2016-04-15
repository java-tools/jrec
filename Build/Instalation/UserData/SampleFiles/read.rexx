/*
	TblIn = "tstFile.bin"
	outFile = 'binRec.txt'*/
	id = 'tstFile1'
	TblIn = id".bin"
	outFile = id'.txt'
	'del' outFile
	eol = ',  13,  10'
	
	Call R000_ReadTbl
	
	out = ''
	sep = '{'
	i = 0
	Line = Line 
	
	Do While  Line <> ''
		parse var Line char 2 line
		out = out || sep || F000_FormatByte(char)
		i = i + 1;
		If i = 11 then do
			Call W200_WriteLine out
			out = ''
			i = 0
		end
		sep = ','
	
	End
	Call W200_WriteLine out eol '},'
	
	
	Call W200_WriteLine out '}'

Return


F000_FormatByte:
Parse Arg char

	num = c2d(char)
	if (num > 127) then do
		num = num - 256
	end

Return right(num, 4)


R000_ReadTbl:


   Line= CharIn(TblIn,,chars(tblin))

Return


W200_WriteLine:
parse arg zzz_L

    zxxx= lineout(outFile, zzz_L)

Return
