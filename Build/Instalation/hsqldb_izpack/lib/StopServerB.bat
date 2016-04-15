 
IF not Exist "%INSTALL_PATH\lib\hsqldbmain.jar" GOTO CD_01
IF NOT Exist "%INSTALL_PATH\lib\LayoutEdit.jar" GOTO CD_01
IF NOT Exist "%INSTALL_PATH\lib\RecordEdit.jar" GOTO CD_01
IF NOT Exist "%INSTALL_PATH\lib\JRecord.jar" GOTO CD_01
IF NOT Exist "%INSTALL_PATH\lib\run.jar" GOTO CD_01
IF NOT Exist "%INSTALL_PATH\lib\properties.zip" GOTO CD_01
start java.exe -jar "%INSTALL_PATH\lib\run.jar" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB
:CD_01
exit 0
