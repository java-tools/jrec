#! /bin/sh

    libdir=%INSTALL_PATH/lib

    ##cd /home/bruce/RecordEdit/HSQLDB/Database
    if [ "$1" = "hide" ]; then
    	if [ -f "${libdir}/hsqldbmain.jar" -a  -f "${libdir}/RecordEdit.jar" -a  -f "${libdir}/LayoutEdit.jar" -a -f "${libdir}/JRecord.jar" -a -f "${libdir}/properties.zip" ] ; then
           nohup /opt/java/jre1.8.0_25/bin/java -cp "${libdir}/LayoutEdit.jar:${libdir}/hsqldbmain.jar:${libdir}/RecordEdit.jar:${libdir}/JRecord.jar:${libdir}/properties.zip" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB > /dev/null 2>&1
        fi
        exit 0
    else
       nohup /opt/java/jre1.8.0_25/bin/java -cp "${libdir}/LayoutEdit.jar:${libdir}/hsqldbmain.jar:${libdir}/RecordEdit.jar:${libdir}/JRecord.jar:${libdir}/properties.zip" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB
    fi

 
