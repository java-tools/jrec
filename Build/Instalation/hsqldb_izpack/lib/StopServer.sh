#! /bin/sh

    libdir=%INSTALL_PATH/lib

    ##cd %INSTALL_PATH/Database
    nohup %JAVA_HOME/bin/java -cp "${libdir}/LayoutEdit.jar:${libdir}/hsqldbmain.jar:${libdir}/RecordEdit.jar:${libdir}/JRecord.jar:${libdir}/properties.zip" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB

 
