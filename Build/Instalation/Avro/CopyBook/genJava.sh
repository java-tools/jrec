#!/bin/bash

  dir=/home/bm/Programs/avro-src-1.3.1/dist/java/

  java -cp ${dir}avro-1.3.1.jar:${dir}avro-tools-1.3.1.jar org.apache.avro.specific.SpecificCompiler $*
