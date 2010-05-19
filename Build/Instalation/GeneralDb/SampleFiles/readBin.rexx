	id="wc020"
	id='sampleEbcidicNew'
	id='tstFile1'
	TblIn = id".bin"
	outFile = id'.txt'
	'del' outFile
	recLen = 80
	/*Call R000_ReadTbl*/
	Call R000_ReadTbl
	
	out = ''
	sep = '{'
	i = 0
	
	Do While  inLines <> ''
		interpret 'parse var inLines Line' recLen+1 'inLines'
		say length(inLines) length(line) say c2x(line)
		do while line <> ''
			parse var Line char 2 line
			out = out || sep || F000_FormatByte(char)
			if length(out) > 65 then do
				Call W200_WriteLine out
				out = ''
			end
			sep = ','
		end
		Call W200_WriteLine out '}'
		out = ''
		sep = '{'
	End

Return


F000_FormatByte:
Parse Arg char

	num = c2d(char)
	if (num > 127) then do
		num = num - 256
	end

Return right(num, 4)


R000_ReadTbl:

    inLines = charin(TblIn, , 5 * recLen)
    
Return


W200_WriteLine:
parse arg zzz_L

    zxxx= lineout(outFile, zzz_L)

Return
