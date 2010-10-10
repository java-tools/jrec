#! /bin/sh

    libdir=%INSTALL_PATH/lib
    cd %INSTALL_PATH/lib

     %JAVA_HOME/bin/java -Xmx1500m -jar "${libdir}/runFullEditor.jar" ${@}

 
