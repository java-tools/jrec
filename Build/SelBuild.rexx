/* rexx */


   dirC = '\'
   if isUnix() then do
      dirC = '/'
   end
   
   
   work =  '/home/bm/Work/'
   baseDir = work'RecordEditor/'
   log = baseDir'build'
   JRecord_Dir = '/home/bm/Work/JRecord/IzPack/'
   
   
   version = '0.69.1'
   jrVersion = '0.68.1'
   
   /*Call pack*/
   Call prompt
   Do while translate(opt) <> 'X'
	if opt < 6 then do
		'rm' log''opt'.log'
	end
	select
		when opt = 1 then call pack
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
	end
	
	call prompt
   end
   
   
Return

pack:

   'rm' log'1.log'
   call cd baseDir'Instalation'dirC'hsqldb_izpack'
   
   'rexx runpack.rexx >' log'1.log'
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
   say 'enter option (x=exit) -->>'
   
   pull opt
Return

isUnix:

    env = uname('S')
  
    if env = 'UNIX' | env = 'Linux' then do
  	return 1
    end
     
return 0

