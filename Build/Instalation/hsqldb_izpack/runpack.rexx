parse arg arg

/*	call pack 'StAX'
	call pack 'cb2xml'
	call pack 'jibx-run'
	call pack 'jlibdiff'
	call pack 'hsqldbmain'*/
	if arg = 'pb' then do
		/*call pack_ProtoBuf 'ProtoBuffers'*/
		call pack_ProtoBuf 'ProtoBufEditor'
	end; else if arg = 'a' then do
		call pack_Avro 'AvroEditor'
		/*call pack_Avro 'avro-1.3.2'
		call pack_Avro 'avro-tools-1.3.2'*/
	end; else do
		call pack 'cb2xml'
		call pack 'JRecord'
		call pack 'LayoutEdit'
		call pack 'RecordEdit'
		
		'cp JRecord.pack ../ProtoBuf/lib'
		'cp RecordEdit.pack ../ProtoBuf/lib'
		'cp JRecord.pack ../Avro/lib'
		'cp RecordEdit.pack ../Avro/lib'
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
	
	'cp 'name'.pack lib/'
	'cp 'name'.pack ../GeneralDBL/lib'
	'cp 'name'.pack ../hsqldb/lib'

return

pack_ProtoBuf:
parse arg name x
	call pack_only
	
	'cp 'name'.pack ../ProtoBuf/lib'
return


pack_Avro:
parse arg name x
	call pack_only
	
	'cp 'name'.pack ../Avro/lib'
return

pack_only:
	say '"/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/bin/pack200" --no-gzip 'name'.pack 'name'.jar'
	'"/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/bin/pack200" --no-gzip 'name'.pack 'name'.jar'
return
	
	