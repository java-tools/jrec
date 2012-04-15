/* rexx */


   dirC = '\'
   if isUnix() then do
      dirC = '/'
   end
   
   if isUnix() then do
       work =  '/home/bm/Work/'
       baseDir = work'RecordEditor/'
       log = baseDir'build'
       JRecord_Dir = '/home/bm/Work/JRecord/IzPack/'
   end; else do
       work =  'C:\Users\mum\Bruce\Work\'
       baseDir = work'RecordEditor\'
       log = baseDir'build'
       JRecord_Dir = work'JRecord\IzPack\'
   end
   
   
   version = '0.80.4'
   jrVersion = '0.68.3'
   
   /*Call pack*/
   Call prompt
   Do while translate(opt) <> 'X'
	if opt < 6 then do
		'rm' log''opt'.log'
	end
	select
		when opt = 1 then call pack
		when opt = 'A' then call pack 'a'
		when opt = 'P' then call pack 'pb'
		when opt = 3 then do
			call cd baseDir'izPack'
   
			'rexx BuildGenericInstall.rexx >' log'2.log'
		end
		when opt = 2 then do
			call cd baseDir'izPack'
   
			'rexx BuildInstall.rexx >' log'3.log'
		end
		when opt = 4 then do
			call cd baseDir'NSIS'
   
			'rexx bld.rexx >' log'4.log'
		end
		when opt = 5 then do
			call cd work'JRecord'dirC'IzPack'
   
			'rexx BuildInstall.rexx >' log'5.log'
		end
		when opt = 6 then do
			'java -jar RecordEdit_Installer_for_HSQL_'version'.jar'
		end
		when opt = 7 then do
			'java -jar RecordEdit_Installer_for_GenericDB_'version'.jar'
		end
		
		when opt = 9 then do
			'java -jar' JRecord_Dir'JRecord_Installer_'jrVersion'.jar'
			
		end
		otherwise
	end
	
	call prompt
   end
   
   
Return

pack:
parse arg prm xx
   'rm' log'1'prm'.log'
   call cd baseDir'Instalation'dirC'hsqldb_izpack'
   'dir'
   say 'rexx runpack.rexx 'prm' >' log'1'prm'.log'
   'rexx runpack.rexx 'prm' >' log'1'prm'.log'
return

prompt:

   say ' '
   say
   say '-------------------------------------'
   say
   say ' '
   say '    1 - Pack Jars       '
   say '    2 - Build HSQLDB     6 - install HSQLDB'
   say '    3 - Build Generic    7 - install Generic'
   say '    4 - NSIS'
   say '    5 - JRecord          9 - install JRecord'
   say
   say '    a - pack avro        p - pack protocol buffers'
   say
   say 'enter option (x=exit) -->>'
   
   pull opt
Return

isUnix:

    env = uname('S')
  
    if env = 'UNIX' | env = 'Linux' then do
  	return 1
    end
     
return 0

