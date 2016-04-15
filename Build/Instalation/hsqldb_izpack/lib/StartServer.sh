#! /bin/sh
#
#  This script will
#  1) Establish the position of the DB directory (check the old position, if it exists use it)
#     Otherwise use the new location
#  2) extract the DB from the Zip file if it does no exist
#  3) Start the HSQL server  
#
    dbDir="%INSTALL_PATH/Database"
    javaCmd="%JAVA_HOME/bin/java"
    libDir="%INSTALL_PATH/lib"
    if [ -f "$dbDir/recordedit.script" ]; then
	echo "HSQL DB $dbDir used."
    else
#	dbDir="${HOME}/.RecordEditor/HSQLDB/Database"
	dbDir="%USER_HOME/.RecordEditor/HSQLDB/Database"
	if [ -f "$dbDir/recordedit.script" ]; then
	    echo "HSQL DB $dbDir used."
	else
	    echo "Extracting Database to $dbDir"
	    $javaCmd -jar "${libDir}/run.jar" net.sf.RecordEditor.edit.XCreateData N $dbDir
    	fi    
    fi
    
    cd "${dbDir}"

    $javaCmd -cp "${libDir}/hsqldbmain.jar" org.hsqldb.Server

