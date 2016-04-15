rem Unzip DB (if needed) and start HSQL Server
rem 
IF Exist "C:%HOMEPATH%\.RecordEditor\HSQLDB\Database\DBcreatedSuccessFully" GOTO CD_01
echo "%JAVA_HOME\bin\java.exe" -jar "%~dp0run.jar" net.sf.RecordEditor.edit.XCreateData n C:%HOMEPATH%\.RecordEditor\HSQLDB\Database
"%JAVA_HOME\bin\java.exe" -jar "%~dp0run.jar" net.sf.RecordEditor.edit.XCreateData n C:%HOMEPATH%\.RecordEditor\HSQLDB\Database
:CD_01
C:
CD C:%HOMEPATH%\.RecordEditor\HSQLDB\Database
"%~dp0%StartServerX.exe"


