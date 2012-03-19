/*
	rexx call izpack 
*/
	parse arg IzPackSource OutPutFile x

	if isUnix() then do
		of='/home/bm/Work/RecordEditor/'OutPutFile
			
		call cd '/home/bm/Work/RecordEditor/izPack'
		
		say of
	
		'/home/bm/Work/IzPack/bin/compile' IzPackSource ' -b . -o' of " -k standard"
	end; else do
		of='C:\Users\mum\Bruce\Work\RecordEditor\Build\izPack\'OutPutFile
			
		call cd 'C:\Users\mum\Bruce\Work\RecordEditor\Build\izPack\izPack'
		
		say IzPackSource
		say of
	
		'"C:\Program Files (x86)\IzPack\bin\compile.bat"' IzPackSource ' -b . -o' of " -k standard"
	end

	return
	
isUnix:

    env = uname('S')
  
     if env = 'UNIX' | env = 'Linux' then do
  	  return 1
     end
     return 0
return
