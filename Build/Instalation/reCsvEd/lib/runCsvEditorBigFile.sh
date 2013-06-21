#! /bin/sh

    libdir=%INSTALL_PATH/lib

     %JAVA_HOME/bin/java -Xmx1200m -jar "${libdir}/runCsvEditor.jar" ${@}

 
