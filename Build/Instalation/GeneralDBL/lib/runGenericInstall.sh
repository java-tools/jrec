#! /bin/sh

    libdir=%INSTALL_PATH/lib
    
    cd $INSTALL_PATH/lib
    
    %JAVA_HOME/bin/java -jar "${libdir}/run.jar" net.sf.RecordEditor.re.editProperties.GenericInstaller

 
