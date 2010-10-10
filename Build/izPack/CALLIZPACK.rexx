/*
	rexx call izpack 
*/
	parse arg IzPackSource OutPutFile x

	
	of='/home/bm/Work/RecordEditor/'OutPutFile
		
	call cd '/home/bm/Work/RecordEditor/izPack'
	
	say o

	'/home/bm/Work/IzPack/bin/compile' IzPackSource ' -b . -o' of " -k standard" 

