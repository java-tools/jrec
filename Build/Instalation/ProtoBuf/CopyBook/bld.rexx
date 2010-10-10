parse arg protoFile
/*
  Rexx Script to "compile" a proto definition
*/
	'protoc' protofile '--java_out=./ --descriptor_set_out='protofile'comp'
