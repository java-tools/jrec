#! /bin/sh

    libdir="%INSTALL_PATH/lib"
    
    %JAVA_HOME/bin/java -jar "${libdir}/run.jar" net.sf.RecordEditor.copy.CopyDBLayout
    
    
##
##  You could also run the Copy using file layout definitions (i.e. XML or Cobol) with this command
##
##  %JAVA_HOME/bin/java -jar "${libdir}/run.jar" net.sf.RecordEditor.copy.CopyFileLayout
##
