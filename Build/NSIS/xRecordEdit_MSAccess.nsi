; Script generated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
SetCompressor /SOLID lzma
SetCompressionLevel 9

!define PRODUCT_NAME "RecordEdit"
!define PRODUCT_VERSION "0.69.2c"                                                                                
!define PRODUCT_PUBLISHER "Bruce Martin"                                                                          
!define PRODUCT_WEB_SITE "http://record-editor.sf.net"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_STARTMENU_REGVAL "NSIS:StartMenuDir"

; MUI 1.67 compatible ------
!include "MUI.nsh"
                                         
; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; Welcome page
!insertmacro MUI_PAGE_WELCOME
; License page
!insertmacro MUI_PAGE_LICENSE "..\General\LICENSE.txt"
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Start menu page
var ICONS_GROUP
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "RecordEdit"
!define MUI_STARTMENUPAGE_REGISTRY_ROOT "${PRODUCT_UNINST_ROOT_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_KEY "${PRODUCT_UNINST_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "${PRODUCT_STARTMENU_REGVAL}"
!insertmacro MUI_PAGE_STARTMENU Application $ICONS_GROUP
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
;!define MUI_FINISHPAGE_RUN "$INSTDIR\bat\init.bat"
!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\Docs\AWelcome.htm"
!define RECORDEDIT_JAR "lib\runFullEditor.jar"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
<OutFile default="RecordEdit_Installer_for_MSAccess_069.2c.exe">
InstallDir "$PROGRAMFILES\RecordEdit\MSaccess"
ShowInstDetails show
ShowUnInstDetails show

var JAVA_DIR
var JAVA_RUN
var JAVA_RUN1


Section "MainSection" SEC01
  Call GetJRE
  Pop $JAVA_DIR
  StrCpy $JAVA_RUN "$JAVA_DIR\javaw.exe"
  StrCpy $JAVA_RUN1 "$JAVA_DIR\java.exe"
  
  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  
  Delete "$INSTDIR\Jars\AddProperties2Jar.rexx"
  Delete "$INSTDIR\Jars\AddProperties2Jar.bat"
  Delete "$INSTDIR\Jars\RecordEdit.jar"
  Delete "$INSTDIR\Jars\LayoutEdit.jar"
  Delete "$INSTDIR\Jars\net\sf\RecordEditor\utils\RecEdit.properties"
  Delete "$INSTDIR\Jars\LayoutEdit.ico"
  Delete "$INSTDIR\Jars\RecordEdit.ico"
  
  Delete "$INSTDIR\lib\JRecord.jar"
  Delete "$INSTDIR\lib\ZCalendar.jar"
  Delete "$INSTDIR\lib\RecordEdit.jar"
  Delete "$INSTDIR\lib\LayoutEdit.jar"
  Delete "$INSTDIR\lib\cb2xml.Jar"

;  Delete "$INSTDIR\lib\StAX.jar"

 
  <expand overwrite=try outpath="$INSTDIR\License" inpath="..\General" name="LICENSE*.txt">

  <expand overwrite=try outpath="$INSTDIR\lib" inpath="..\Instalation\GeneralDB\lib" name="run*.jar" name2="ZCalendar.jar"/>
  ;<expand overwrite=try outpath="$INSTDIR\lib" inpath="..\lib" name="run*.jar" name1="JRecord.jar" 
  ;			name2="ZCalendar.jar" name3="RecordEdit.jar" name4="LayoutEdit.jar" name5="cb2xml.jar" name6="StAX.jar"/>
  <expand  inpath="..\Instalation\GeneralDB\lib" name="icons*.zip" name1="*.ico"/>
  <expand  inpath="..\Instalation\MSaccess\lib\" name="properties.zip" name1="*Files.txt"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="*Edit.pack" name1="cb2xm*.pack" name3="StAX.pack" name4="chardet.pack"  name5="ZCalendar.pack"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="j*.pack" name1="JRecord.propertie*"/>
  <psc proc="unix">
    <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="JRecord.pack">
  </psc>
  File "NsisUnpack.jar"
  
  exec '"$JAVA_RUN1" -jar "$INSTDIR\lib\NsisUnpack.jar" $INSTDIR\lib'
  
  <expand overwrite="try" outpath="$INSTDIR\Docs" inpath="..\Instalation\MSaccess\Docs" name="a*.htm" name1="RecordEdit.htm" name2="reaMan*.htm"/>
  <expand inpath="..\Instalation\GeneralDB\Docs\" name="Hlp*.htm" name1="CobolEditor.htm" name2="Ex*.htm" name3="syntax.css" name4="HowTo.htm"/>
  <expand inpath="..\Instalation\GeneralDB\Docs\" name="diff1.html" name1="Copy.htm"/>
  <expand outpath="$INSTDIR\Docs\jsTree" inpath="..\Instalation\GeneralDB\Docs\JSTREE\" name="*.gif" name1="*.css" name2="*.js" />
  <expand overwrite="try" outpath="$INSTDIR\Docs\Diagram" inpath="..\Instalation\GeneralDB\Docs\Diagram\"  name="*.png" DateCheck=yes/>                                                               

  <expand overwrite=try outpath="$INSTDIR\lib\net\sf\RecordEditor\utils" 
                        inpath="..\Instalation\MSaccess\lib\net\sf\RecordEditor\utils\" name="*.properties"/>  
  
  <expand outpath="$INSTDIR\CopyBook\Cobol" inpath="..\Instalation\GeneralDB\CopyBook\Cobol" name="*.cbl" DateCheck=yes/>
  <expand outpath="$INSTDIR\CopyBook\cb2xml" inpath="..\Instalation\GeneralDB\CopyBook\cb2xml" name="*.xml" DateCheck=yes/>
  <expand outpath="$INSTDIR\CopyBook\Csv" inpath="..\Instalation\GeneralDB\CopyBook\Csv" name1="*.Txt" DateCheck=yes/>
  <expand outpath="$INSTDIR\CopyBook\Xml" inpath="..\Instalation\GeneralDB\CopyBook\Xml" name1="*.Xml" DateCheck=yes/>
  <expand overwrite=try outpath="$INSTDIR\SampleVelocityTemplates\Copybook" inpath="..\Instalation\GeneralDB\SampleVelocityTemplates\Copybook" name="*.vm"/>
  <expand overwrite=try outpath="$INSTDIR\SampleVelocityTemplates\File" inpath="..\Instalation\GeneralDB\SampleVelocityTemplates\File" name="*.vm"/>
  <expand overwrite=try outpath="$INSTDIR\SampleFiles" 
                        inpath="..\Instalation\GeneralDB\SampleFiles\" name="Ams_LocDownload_20041228_Extract*.txt"/>  
  <expand overwrite=try inpath="..\SampleFiles" name="*.txt" name1="*.bin"  DateCheck=yes/>
  <expand overwrite=try inpath="..\SampleFiles" name="*.csv"   DateCheck=yes/>    
  <expand overwrite=try outpath="$INSTDIR\SampleFiles\Xml" inpath="..\SampleFiles\Xml" name="*.xml" DateCheck=yes/>  
                      
  <expand overwrite=try outpath="$PROFILE\RecordEditor_MSaccess" inpath="..\Instalation\MSaccess\Properties" 
  		name="Params.Properties" name1="Files.txt" name2="Properties.zip" name3="CobolFiles.txt"/>  
	
  <expand overwrite=off outpath="$PROFILE\RecordEditor_MSaccess\User\Compare"    inpath="..\Instalation\GeneralDB\User\Compare"    name="*.xml" />  
  <expand overwrite=off outpath="$PROFILE\RecordEditor_MSaccess\User\Filter"     inpath="..\Instalation\GeneralDB\User\Filter"     name="*.xml" />  
  <expand overwrite=off outpath="$PROFILE\RecordEditor_MSaccess\User\RecordTree" inpath="..\Instalation\GeneralDB\User\RecordTree" name="*.xml" />  
  <expand overwrite=off outpath="$PROFILE\RecordEditor_MSaccess\User\SortTree"   inpath="..\Instalation\GeneralDB\User\SortTree"   name="*.xml" />  


  SetOverwrite off
  SetOutPath "$PROFILE\RecordEditor_MSaccess"
  
  <psc proc="normal">
  File "..\Instalation\MSaccess\RecordEditor.mdb"
  </psc>

   

                                                       
; Shortcuts
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
  !insertmacro MUI_STARTMENU_WRITE_END
  
 ;; <psc proc="normal">
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit\command"
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit" 
  DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" 
                                                                                                               
  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\Record Edit" "" ""
  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\Record Edit\command" "" "$\"$JAVA_RUN$\" -jar $\"$INSTDIR\${RECORDEDIT_JAR}$\"   $\"%1$\""

  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\ODBC Data Sources" "RecordLayout" "Microsoft Access Driver (*.mdb)"
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "Driver" "$SYSDIR\ODBCJT32.DLL"
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "DBQ" "$PROFILE\RecordEditor_MSaccess\RecordEditor.mdb"
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "Description" "Record Description"
  WriteRegDWORD HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "DriverId" 0x19
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "FIL" "MS Access;"
  WriteRegDWORD HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "SafeTransactions" 0x0
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout" "UID" ""
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines" "" ""
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines\Jet" "ImplicitCommitSync" ""
  WriteRegDWORD HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines\Jet" "MaxBufferSize" 0x800
  WriteRegDWORD HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines\Jet" "PageTimeout" 0x5
  WriteRegDWORD HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines\Jet" "Threads" 0x3
  WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\ODBC\ODBC.INI\RecordLayout\Engines\Jet" "UserCommitSync" "Yes"
 ;;  </psc>
  
 
  ClearErrors
  FileOpen $0 $INSTDIR\lib\EditBigFile.Bat w
  IfErrors done
  FileWrite $0 "javaw  -Xmx1400m -jar $\"$INSTDIR\${RECORDEDIT_JAR}$\""
  FileClose $0
  done:

  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\Edit RecordEditor Startup Properties.lnk" 
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\Run with Velocity.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Basic Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Examples.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Howto.lnk"
SectionEnd

Section -AdditionalIcons
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application

;  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP\Utils"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP\Documentation"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\${RECORDEDIT_JAR}$\"" "$INSTDIR\lib\RecordEdit.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\lib\runLayouteditor.jar$\"" "$INSTDIR\lib\LayoutEdit.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\Record Editor Manual.lnk" "$INSTDIR\Docs\aRecordEdit.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\Examples.lnk" "$INSTDIR\Docs\ExampleFR.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\HowTo.lnk" "$INSTDIR\Docs\HowTo.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCopy.lnk" "$INSTDIR\Docs\Copy.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCompare.lnk" "$INSTDIR\Docs\diff1.html"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Editor Big Files.lnk" "$INSTDIR\lib\EditBigFile.Bat"  "" "$INSTDIR\lib\RecordEdit.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Edit RecordEditor Startup Properties.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.editProperties.EditOptions"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Run with Velocity.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.edit.util.RunVelocityGui"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.edit.EditFileLayout"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor Documentation.lnk" "$INSTDIR\Docs\CobolEditor.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCompare.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.diff.CompareDBLayout"  "$INSTDIR\lib\Utility.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCopy.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.copy.CopyDBLayout"  "$INSTDIR\lib\Utility.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Uninstall.lnk" "$INSTDIR\uninst.exe"
  !insertmacro MUI_STARTMENU_WRITE_END
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
  

SectionEnd


Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" IDYES +2
  Abort
FunctionEnd

Function GetJRE
;
;  Find JRE (Java.exe etc)
;  1 - in .\jre directory (JRE Installed with application)
;  2 - in JAVA_HOME environment variable
;  3 - in the registry
;  4 - assume javaw.exe in current dir or PATH

  Push $R0
  Push $R1

  ClearErrors
  StrCpy $R0 "$EXEDIR\jre\bin\"
  IfFileExists $R0 JreFound
  StrCpy $R0 ""

  ClearErrors
  ReadEnvStr $R0 "JAVA_HOME"
  StrCpy $R0 "$R0\bin\"
  IfErrors 0 JreFound

  ClearErrors
  ReadRegStr $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"
  StrCpy $R0 "$R0\bin\"

  IfErrors 0 JreFound
  StrCpy $R0 ""

  ClearErrors
  StrCpy $R0 "$PROGRAMFILES\Merant\vm\common\jre\win32\bin\"
  IfFileExists $R0 JreFound

 JreFound:
  Pop $R1
  Exch $R0
FunctionEnd


Section Uninstall
  !insertmacro MUI_STARTMENU_GETFOLDER "Application" $ICONS_GROUP
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
 
  <WriteDelete/>
  
  Delete "$PROFILE\RecordEditor_MSaccess\RecordEditor.mdb"
  Delete "$INSTDIR\lib\JRecord.jar"
  Delete "$INSTDIR\lib\ZCalendar.jar"
  Delete "$INSTDIR\lib\RecordEdit.jar"
  Delete "$INSTDIR\lib\LayoutEdit.jar"
  Delete "$INSTDIR\lib\cb2xml.Jar"
  Delete "$INSTDIR\lib\StAX.jar"
  Delete "$INSTDIR\lib\EditBigFile.Bat"
  Delete "$INSTDIR\lib\NsisUnpack.jar"
  Delete "$INSTDIR\lib\chardet.jar"
  Delete "$INSTDIR\lib\jibx-run.jar"
  Delete "$INSTDIR\lib\jlibdiff.jar"


  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\Edit RecordEditor Startup Properties.lnk" 
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\Run with Velocity.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Basic Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Examples.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Howto.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\CobolEditor Documentation.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\FileCompare.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\FileCopy.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Editor Big Files.lnk" 
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Examples.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCompare.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCopy.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\HowTo.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Record Editor Manual.lnk"


  RMDir "$SMPROGRAMS\$ICONS_GROUP\Documentation"
  RMDir "$SMPROGRAMS\$ICONS_GROUP\Utils"
  RMDir "$SMPROGRAMS\$ICONS_GROUP"
  RMDir "$INSTDIR\lib\utils"
  RMDir "$INSTDIR\lib\edit"
  RMDir "$INSTDIR\Copybook\cb2xml"
  RMDir "$INSTDIR\Copybook\Cobol"
  RMDir "$INSTDIR\Copybook\Csv"
  RMDir "$INSTDIR\Copybook"
  RMDir "$INSTDIR\src"
  RMDir "$INSTDIR\lib\net"
  RMDir "$INSTDIR\lib"
  RMDir "$INSTDIR\Docs\jsTree"
  RMDir "$INSTDIR\Docs\Diagram"
  RMDir "$INSTDIR\Docs"
  RMDir "$INSTDIR\SampleVelocityTemplates\Copybook"
  RMDir "$INSTDIR\SampleVelocityTemplates\File"
  RMDir "$INSTDIR\SampleVelocityTemplates"
  RMDir "$INSTDIR\SampleFiles\xml"
  RMDir "$INSTDIR\SampleFiles"
  RMDir "$INSTDIR\Database"
  RMDir "$INSTDIR"         
  RMDir "$PROFILE\RecordEditor_MsAccess"

  RMDir "$INSTDIR"

  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit\command"
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit" 
  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  SetAutoClose true
SectionEnd