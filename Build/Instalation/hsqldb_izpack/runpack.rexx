parse arg arg


	if arg = 'pb' then do
		call pack_ProtoBuf 'ProtoBuffers'
		call pack_ProtoBuf 'ProtoBufEditor'
	end; else if arg = 'a' then do
		call pack_Avro 'AvroEditor'
	end; else if arg = 'aa' then do
		call pack_Avro 'AvroEditor'
		/*call pack_Avro 'avro-1.4.0'*/
		call pack_Avro 'avro-tools-1.4.0'
	end; else if arg = 'h2' then do
		call pack_H2 'h2-1.2.141'
	end; else if arg = 'v' then do
		call pack 'velocity-1.7'
		call pack 'velocity-1.7-dep'
	end; else if arg = 'u' then do
		call pack_Utility 'jibx-run'
	    call pack_Utility 'jlibdiff'
	end; else do
		/*call pack 'StAX'*/
		/*call pack 'jibx-run'
		call pack 'jlibdiff'
		call pack 'velocity-1.7'
		call pack 'velocity-1.7-dep'
		call pack 'hsqldbmain'
		call pack 'chardet'*/
		
		/*call pack 'ZCalendar'
		call pack 'cb2xml'*/
		
		call pack 'JRecord'
		call pack 'LayoutEdit'
		call pack 'RecordEdit'
		

		
		if isUnix() then do
			'cp JRecord.pack ../ProtoBuf/lib'
			'cp RecordEdit.pack ../ProtoBuf/lib'
			'cp chardet.pack ../ProtoBuf/lib'
			'cp ZCalendar.pack ../ProtoBuf/lib'
			'cp JRecord.pack ../Avro/lib'
			'cp RecordEdit.pack ../Avro/lib'
			'cp chardet.pack ../Avro/lib'
			'cp ZCalendar.pack ../Avro/lib'
		end; else do
			'copy JRecord.pack ..\ProtoBuf\lib'
			'copy RecordEdit.pack ..\ProtoBuf\lib'
			'copy chardet.pack ..\ProtoBuf\lib'
			'copy ZCalendar.pack ..\ProtoBuf\lib'
			'copy JRecord.pack ..\Avro\lib'
			'copy RecordEdit.pack ..\Avro\lib'
			'copy chardet.pack ..\Avro\lib'
			'copy ZCalendar.pack ..\Avro\lib'
		end
	end
	
/*	'cp JRecord.pack lib/'
	'cp JRecord.pack ../GeneralDBL/lib'
	'cp JRecord.pack ../hsqldb/lib'
	
	'cp LayoutEdit.pack lib/'
	'cp LayoutEdit.pack ../GeneralDBL/lib'
	'cp LayoutEdit.pack ../hsqldb/lib'
	
	'cp RecordEdit.pack lib/'
	'cp RecordEdit.pack ../GeneralDBL/lib'
	'cp RecordEdit.pack ../hsqldb/lib'*/
return


pack:
parse arg name x
	call pack_only
	
	if isUnix() then do
	   'cp 'name'.pack lib/'
	  /* 'cp 'name'.pack ../GeneralDBL/lib'*/
	end; else do
	   'copy 'name'.pack lib\'
	   /*'copy 'name'.pack ..\GeneralDBL\lib'*/
	end

return

pack_ProtoBuf:
parse arg name x
	call pack_only
	
	if isUnix() then do
	   'cp 'name'.pack ../ProtoBuf/lib'
	end; else do
	   'copy 'name'.pack ..\ProtoBuf\lib'
	end
return


pack_Avro:
parse arg name x
	call pack_only
	
	if isUnix() then do
		'cp 'name'.pack ../Avro/lib'
	end; else do
	   'copy 'name'.pack ..\Avro\lib'
	end
return

pack_H2:
parse arg name x
	call pack_only
	
	'cp 'name'.pack ../H2/lib/H2.pack'
return

pack_Utility:
parse arg name x
	call pack_only
return


pack_only:
	/*say '"/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/bin/pack200" --no-gzip 'name'.pack 'name'.jar'*/
	
	if isUnix() then do
	   '"/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/bin/pack200" --no-gzip 'name'.pack 'name'.jar'
	end; else do
	   '"C:\Program Files\java\jre6\bin\pack200" --no-gzip 'name'.pack 'name'.jar'
	end
return
	
isUnix:

    env = uname('S')
  
     if env = 'UNIX' | env = 'Linux' then do
  	  return 1
     end
     return 0
return
	