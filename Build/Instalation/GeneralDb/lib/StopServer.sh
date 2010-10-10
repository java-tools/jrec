#! /bin/sh

    libdir=%INSTALL_PATH/lib

    ##cd %INSTALL_PATH/Database
    %JAVA_HOME/bin/java -cp "${libdir}/LayoutEdit.jar:${libdir}/hsqldbmain.jar" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB

 
