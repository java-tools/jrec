#! /bin/sh

    libdir=%INSTALL_PATH/lib

     %JAVA_HOME/bin/java -Xmx700m -jar "${libdir}/runCsvEditor.jar" ${@}

 
