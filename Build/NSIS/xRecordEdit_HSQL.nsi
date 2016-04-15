; Script generated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
SetCompressor /SOLID lzma
SetCompressionLevel 9

!define PRODUCT_NAME "RecordEdit_HSQL"                                                             
!define PRODUCT_VERSION "0.97m"                                                                                  
!define PRODUCT_PUBLISHER "Bruce Martin"                                                           
!define PRODUCT_WEB_SITE "http://record-editor.sf.net"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_STARTMENU_REGVAL "NSIS:StartMenuDir"

!define SEND_TO_NAME "Record Edit HSQL"                           

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
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "RecordEdit_HSQL"
!define MUI_STARTMENUPAGE_REGISTRY_ROOT "${PRODUCT_UNINST_ROOT_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_KEY "${PRODUCT_UNINST_KEY}"
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "${PRODUCT_STARTMENU_REGVAL}"
!insertmacro MUI_PAGE_STARTMENU Application $ICONS_GROUP
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page                                                        
;!define MUI_FINISHPAGE_RUN "$JAVA_RUN"  
;!define MUI_FINISHPAGE_RUN_PARAMETERS "-cp $\"$INSTDIR\lib\hsqldbmain.jar$\" org.hsqldb.Server"
!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\Docs\hWelcome.htm"
!define RECORDEDIT_JAR "lib\runFullEditor.jar"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
<OutFile default="RecordEdit_Installer_for_HSQL_0.97m.exe"/>
InstallDir "$PROGRAMFILES\RecordEdit\HSQL"
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
 

;  Delete "$INSTDIR\lib\JRecord.jar"

;  Delete "$INSTDIR\lib\RecordEdit.jar"
;  Delete "$INSTDIR\lib\LayoutEdit.jar"
;  Delete "$INSTDIR\lib\cb2xml.Jar"



  <expand overwrite=try outpath="$INSTDIR\License" inpath="..\General" name="*.txt" name1="*.html">

  
  <expand overwrite=try outpath="$INSTDIR\UserData" inpath="..\Instalation\UserData" name="*.zip" >
  <expand                                           inpath="..\Instalation\hsqldb"    name="Database.zip" >

  SetOverwrite off
  <expand outpath="$INSTDIR\lib" inpath="..\Instalation\hsqldb\lib\"  name="Params.Properties"/>
  <expand overwrite=try outpath="$INSTDIR\lib" inpath="..\Instalation\hsqldb\lib\"   name3="StartServer.bat" name4="StopServer4Upgrade.bat"/>
  exec  '"$INSTDIR\lib\StopServer4Upgrade.bat" "$JAVA_RUN1"'
  <expand overwrite=try outpath="$INSTDIR\lib" inpath="..\Instalation\GeneralDB\lib" name="run*.jar" name2="SystemJars.txt">
  ; expand overwrite=try outpath="$INSTDIR\lib" inpath="..\lib" name="run*.jar"
  ;			name2="JRecord.jar" name3="RecordEdit.jar" name4="LayoutEdit.jar" name5="cb2xml.jar"
 
  ;<expand  inpath="..\Instalation\GeneralDB\lib" name="icons*.zip" name1="*.ico" />
  <expand  inpath="..\Instalation\hsqldb\lib\" name="properties.zip" name1="*.txt" name2="InstallHelper.bat"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="JRecord.properties" name1="JRecord.properties.html" name2="LayoutEditor_TipOfTheDay.properties" name3="RecordEditor_TipOfTheDay.properties" />
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="*.ico" name1="*.png"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="*Edit.pack" name1="cb2xml.pack" name2="pict.zip" name4="chardet.pack"  name5="ZCalendar.pack"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="jlibdiff.pack"  name2="RunUnpack.exe" name3="velocity-1.7*.pack" name4="runCobolBatchLoad.bat" name5="StartServerX.exe"/>
  <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="JRecord.pack" name1="jibx-run.pack" name2="rsyntaxtextarea.pack" name3="swingx-subset-1.6.4.pack" name4="zip4j_1.3.2.pack" >

  <psc proc="normal">
    <expand  inpath="..\Instalation\hsqldb_izpack\lib" name="hsqldbmain.pack"/>
  </psc>
  File "NsisUnpack.jar"
  
;  exec 'InstallHelper.bat "$JAVA_RUN1" > InstallBat.txt'
;  exec '"$JAVA_RUN1" -jar "$INSTDIR\lib\NsisUnpack.jar" $INSTDIR\lib'
;  exec  '"$JAVA_RUN1" -jar "$INSTDIR\lib\run.jar" net.sf.RecordEditor.edit.XCreateData'
  execwait '"$JAVA_RUN1" -jar "$INSTDIR\lib\NsisUnpack.jar" $INSTDIR\lib'
;  exec  '"$JAVA_RUN" -jar "$INSTDIR\lib\run.jar" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB'
  exec  '"$JAVA_RUN1" -jar "$INSTDIR\lib\run.jar" net.sf.RecordEditor.edit.XCreateData n $PROFILE\RecordEditor_HSQL\Database'
  
  <expand overwrite="try" outpath="$INSTDIR\lib\Extensions" 
                          inpath="..\Instalation\hsqldb_izpack\lib\Extensions" name="*.txt"/>  
;  <expand overwrite="try" outpath="$PROFILE\RecordEditor_HSQL\Extensions" 
;                          inpath="..\Instalation\hsqldb_izpack\lib\Extensions" name="*.txt"/>  


;  <expand overwrite="try" outpath="$INSTDIR\lib\net\sf\RecordEditor\utils" 
;                          inpath="..\Instalation\hsqldb\lib\net\sf\RecordEditor\utils\" name="*.properties"/>  
  
  <expand overwrite="try" outpath="$INSTDIR\lang" name="*.html" name1="*.po"  name2="msgfmt*.*" inpath="..\Instalation\GeneralDB\lang">
  <expand overwrite="try" outpath="$INSTDIR\lang\Sample"  name="*.po"               inpath="..\Instalation\GeneralDB\lang\Sample">
  <expand overwrite="try" outpath="$INSTDIR\lang\Diagram" name="*.png"              inpath="..\Instalation\GeneralDB\lang\Diagram">
;  exec '"$JAVA_RUN1" -jar "$INSTDIR\lib\run.jar" net.sf.RecordEditor.edit.XCreateData'
; Just in case do another uninstall

   
  <expand overwrite="try" outpath="$INSTDIR\Docs" inpath="..\Instalation\hsqldb\Docs" name="h*.htm" name1="RecordEdit.htm" name2="rehMan*.htm" name3="Documents.htm"/>
  <expand inpath="..\Instalation\GeneralDB\Docs" name="Hlp*.htm" name1="CobolEditor.htm" name2="Ex*.htm" name3="syntax.css" name4="HowTo.htm"/>
  <expand inpath="..\Instalation\GeneralDB\Docs\"  name2="diff1.html" name3="Copy.htm"/>
  <expand outpath="$INSTDIR\Docs\jsTree" inpath="..\Instalation\GeneralDB\Docs\JSTREE\" name="*.gif" name1="*.css" name2="*.js"/>
  <expand overwrite="try" outpath="$INSTDIR\Docs\Diagram" inpath="..\Instalation\GeneralDB\Docs\Diagram\"  name="*.png" DateCheck=yes/>
 
;  <expand outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Cobol" inpath="..\Instalation\GeneralDB\CopyBook\Cobol" name="*.*" DateCheck=yes/>
;  <expand outpath="$PROFILE\RecordEditor_HSQL\CopyBook\cb2xml" inpath="..\Instalation\GeneralDB\CopyBook\cb2xml" name="*.xml" DateCheck=yes/>
;  <expand outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Csv" inpath="..\Instalation\GeneralDB\CopyBook\Csv" name1="*.Txt" DateCheck=yes/>
;  <expand outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Xml" inpath="..\Instalation\GeneralDB\CopyBook\Xml" name1="*.Xml" DateCheck=yes/>
;;;  <expand outpath="$INSTDIR\Copybook" inpath="..\Instalation\GeneralDB\Copybook" name="*.txt" DateCheck=yes/>
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates\Copybook" inpath="..\Instalation\GeneralDB\SampleVelocityTemplates\Copybook" name="*.vm"/>
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates\File" inpath="..\Instalation\GeneralDB\SampleVelocityTemplates\File" name="*.vm"/>
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates\File\GetText" inpath="..\Instalation\GeneralDB\SampleVelocityTemplates\File\GetText" name="*.vm"/>
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles" 
;                        inpath="..\Instalation\GeneralDB\SampleFiles\" name="Ams_LocDownload_20041228_Extract*.txt"/>  
;  <expand overwrite=try inpath="..\SampleFiles" name="*.txt" name1="*.bin" name2="xmlModDTAR020.bin.xml" DateCheck=yes/>  
;  <expand overwrite=try inpath="..\SampleFiles" name="*.csv"  DateCheck=yes/>
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\Xml" inpath="..\Instalation\GeneralDB\SampleFiles\Xml" name="*.xml"/>  
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\po"  inpath="..\Instalation\GeneralDB\SampleFiles\po"  name="*.*"/>  
;
;  <expand overwrite=try outpath="$PROFILE\RecordEditor_HSQL" inpath="..\Instalation\hsqldb_izpack\HSQLDB\" 
;  			  name1="Files.txt" name="CobolFiles.txt"  name3="UserJars.txt"/>  
;  <expand overwrite=off inpath="..\Instalation\hsqldb\RecordEditor_HSQL_nsis\"  name1="Params.Properties" name2="Properties.zip">  
;	
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Copy"          inpath="..\Instalation\GeneralDB\User\Copy"          name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Compare"       inpath="..\Instalation\GeneralDB\User\Compare"       name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Filter"        inpath="..\Instalation\GeneralDB\User\Filter"        name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Fields"        inpath="..\Instalation\GeneralDB\User\Fields"        name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\LayoutExport"  inpath="..\Instalation\GeneralDB\User\LayoutExport"  name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\RecordTree"    inpath="..\Instalation\GeneralDB\User\RecordTree"    name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\SortTree"      inpath="..\Instalation\GeneralDB\User\SortTree"      name="*.xml" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Xslt"          inpath="..\Instalation\GeneralDB\User\Xslt"          name="*.xsl" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\ExportScripts" inpath="..\Instalation\GeneralDB\User\ExportScripts" name="*.*" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Scripts"       inpath="..\Instalation\GeneralDB\User\Scripts"       name="*.py" name1="*.rb" name2="*.js" name3="*.txt"/>  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Scripts\Examples" inpath="..\Instalation\GeneralDB\User\Scripts\Examples"  name="*.*" />  
;  <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Icons"         inpath="..\Instalation\GeneralDB\User\Icons"         name="*.*" />  
;
;  <psc proc="normal">
;    <expand overwrite=off outpath="$PROFILE\RecordEditor_HSQL\Database" inpath="..\Instalation\hsqldb\Database" name="*"/>
;  </psc>

  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\" inpath="..\Instalation\UserData" name="*"/>
  <expand4del outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Cobol" inpath="..\Instalation\UserData\CopyBook\Cobol" name="*.*" DateCheck=yes/>
  <expand4del outpath="$PROFILE\RecordEditor_HSQL\CopyBook\cb2xml" inpath="..\Instalation\UserData\CopyBook\cb2xml" name="*.xml" DateCheck=yes/>
  <expand4del outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Csv" inpath="..\Instalation\UserData\CopyBook\Csv" name1="*.Txt" DateCheck=yes/>
  <expand4del outpath="$PROFILE\RecordEditor_HSQL\CopyBook\Xml" inpath="..\Instalation\UserData\CopyBook\Xml" name1="*.Xml" DateCheck=yes/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Copybook" inpath="..\Instalation\UserData\User\VelocityTemplates\Copybook" name="*.vm"/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\File" inpath="..\Instalation\UserData\User\VelocityTemplates\File" name="*.vm"/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\File\GetText" inpath="..\Instalation\UserData\User\VelocityTemplates\File\GetText" name="*.vm"/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Script" inpath="..\Instalation\UserData\User\VelocityTemplates\Script\JavaScript" name="*.vm"/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Script" inpath="..\Instalation\UserData\User\VelocityTemplates\Script" name="*.*"/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles" 
                        inpath="..\Instalation\UserData\SampleFiles\" name="Ams_LocDownload_20041228_Extract*.txt"/>  
  <expand4del overwrite=try inpath="..\SampleFiles" name="*.txt" name1="*.bin" name2="*.xml" DateCheck=yes/>  
  <expand4del overwrite=try inpath="..\SampleFiles" name="*.csv" name2"*.rexx" name3="*.htm" DateCheck=yes/>
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\Xml" inpath="..\Instalation\UserData\SampleFiles\Xml" name="*.xml"/>  
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\po"  inpath="..\Instalation\UserData\SampleFiles\po"  name="*.*"/>  
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\Csv"  inpath="..\Instalation\UserData\SampleFiles\Csv"  name="*.*"/>  
  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL\SampleFiles\Fujitsu"  inpath="..\Instalation\UserData\SampleFiles\Fujitsu"  name="*.*"/>  

  <expand4del overwrite=try outpath="$PROFILE\RecordEditor_HSQL" inpath="..\Instalation\hsqldb_izpack\HSQLDB\" 
  			  name1="Files.txt" name="CobolFiles.txt"  name3="UserJars.txt"/>  
  <expand4del overwrite=off inpath="..\Instalation\hsqldb\RecordEditor_HSQL_nsis\"  name1="Params.Properties" name2="Properties.zip">  
	
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Copy"          inpath="..\Instalation\UserData\User\Copy"          name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Compare"       inpath="..\Instalation\UserData\User\Compare"       name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Filter"        inpath="..\Instalation\UserData\User\Filter"        name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Fields"        inpath="..\Instalation\UserData\User\Fields"        name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\LayoutExport"  inpath="..\Instalation\UserData\User\LayoutExport"  name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\RecordTree"    inpath="..\Instalation\UserData\User\RecordTree"    name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\SortTree"      inpath="..\Instalation\UserData\User\SortTree"      name="*.xml" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Xslt"          inpath="..\Instalation\UserData\User\Xslt"          name="*.xsl" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\ExportScripts" inpath="..\Instalation\UserData\User\ExportScripts" name="*.*" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Scripts"       inpath="..\Instalation\UserData\User\Scripts"       name="*.py" name1="*.rb" name2="*.js" name3="*.txt"/>  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Scripts\Examples" inpath="..\Instalation\UserData\User\Scripts\Examples"  name="*.*" />  
  <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\User\Icons"         inpath="..\Instalation\UserData\User\Icons"         name="*.*" />  
  <psc proc="normal">
    <expand4del overwrite=off outpath="$PROFILE\RecordEditor_HSQL\Database" inpath="..\Instalation\hsqldb\Database" name="*"/>
  </psc>

    
  SetOverwrite on
;  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL" 
;  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL\command"

  
;  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL" "" ""
;  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL\command" "" "$\"$JAVA_RUN$\" -cp $\"$INSTDIR\lib\LayoutEdit.jar;$INSTDIR\lib\hsqldbmain.jar$\" net.sf.RecordEditor.edit.FullEditor  $\"%1$\""
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}\command"
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}" 

  
  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}" "" ""
  WriteRegStr HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}\command" "" "$\"$JAVA_RUN$\" -jar $\"$INSTDIR\${RECORDEDIT_JAR}$\" $\"%1$\""


  ClearErrors
  FileOpen $0 $INSTDIR\lib\EditBigFile.Bat w
  IfErrors done
  FileWrite $0 "start /b  javaw  -Xmx1400m -jar $\"$INSTDIR\${RECORDEDIT_JAR}$\""
  FileClose $0
  
  FileOpen $0 $INSTDIR\lib\BatchCopy.Bat w
  IfErrors done
  FileWrite $0 "start /b  javaw -jar $\"$INSTDIR\lib\run.jar net.sf.RecordEditor.copy.BatchCopyDbLayout %*$\""
  FileClose $0
  done:

SectionEnd

Section -AdditionalIcons
  SetShellVarContext all
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\CobolEditor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Edit RecordEditor Startup Properties.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Run with Velocity.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\CobolEditor Documentation.lnk"

  Delete "$SMPROGRAMS\$ICONS_GROUP\Basic Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Stop Server.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Start Server.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\HowTo.lnk"
 
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application

;  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP\Utils"
  CreateDirectory "$SMPROGRAMS\$ICONS_GROUP\Documentation"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\${RECORDEDIT_JAR}$\"" "$INSTDIR\lib\RecordEdit.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\lib\runLayouteditor.jar$\"" "$INSTDIR\lib\LayoutEdit.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\${RECORDEDIT_JAR}$\"" "$INSTDIR\lib\RecordEdit.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk" "$JAVA_RUN"  "-jar $\"$INSTDIR\lib\runLayouteditor.jar$\"" "$INSTDIR\lib\LayoutEdit.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\Record Editor Manual.lnk" "$INSTDIR\Docs\hRecordEdit.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\Examples.lnk" "$INSTDIR\Docs\ExampleFR.htm"  
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCopy.lnk" "$INSTDIR\Docs\Copy.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\FileCompare.lnk" "$INSTDIR\Docs\diff1.html"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Documentation\HowTo.lnk" "$INSTDIR\Docs\HowTo.htm"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Editor Big Files.lnk" "$INSTDIR\lib\EditBigFile.Bat"  "" "$INSTDIR\lib\RecordEdit.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Edit RecordEditor Startup Properties.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.editProperties.EditOptions" "$INSTDIR\lib\Utility.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Run with Velocity.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.edit.util.RunVelocityGui"  "$INSTDIR\lib\Utility.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.editFileLayout.Edit"  "$INSTDIR\lib\Cobol.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor Documentation.lnk" "$INSTDIR\Docs\CobolEditor.htm"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCompare.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.diff.CompareDBLayout"  "$INSTDIR\lib\Utility.ico"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCopy.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.copy.CopyDBLayout"  "$INSTDIR\lib\Utility.ico"

  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Editor Big Files.lnk" "$INSTDIR\lib\EditBigFile.Bat"  "" "$PROFILE\RecordEditor_HSQL\User\RecordEdit.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Edit RecordEditor Startup Properties.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.editProperties.EditOptions" "$INSTDIR\lib\Utility.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Run with Velocity.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.edit.util.RunVelocityGui"  "$INSTDIR\lib\Utility.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.editFileLayout.Edit"  "$INSTDIR\lib\Cobol.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\CobolEditor Documentation.lnk" "$INSTDIR\Docs\CobolEditor.htm"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCompare.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.diff.CompareDBLayout"  "$INSTDIR\lib\Utility.ico"
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\FileCopy.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.copy.CopyDBLayout"  "$INSTDIR\lib\Utility.ico"

  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Utils\Uninstall.lnk" "$INSTDIR\uninst.exe"

 
  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Stop Server.lnk" "$JAVA_RUN" "-jar $\"$INSTDIR\lib\run.jar$\" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB"  
 ;CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Stop Server.lnk" "$JAVA_RUN"  "-cp $\"$INSTDIR\lib\LayoutEdit.jar;$INSTDIR\lib\hsqldbmain.jar;$INSTDIR\lib\RecordEdit.jar$\" net.sf.RecordEditor.layoutEd.ShutdownHSQLDB" 

;  SetOutPath "$PROFILE\RecordEditor_HSQL\Database"
;  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Start Server.lnk" "$JAVA_RUN"  "-cp $\"$INSTDIR\lib\hsqldbmain.jar$\" org.hsqldb.Server" 
 ; CreateShortCut "$SMSTARTUP\Start HSQL Server.lnk" "$JAVA_RUN"  "-cp $\"$INSTDIR\lib\hsqldbmain.jar$\" org.hsqldb.Server" 

  CreateShortCut "$SMPROGRAMS\$ICONS_GROUP\Start Server.lnk" "$\"$INSTDIR\lib\StartServer.bat$\"" "$\"$JAVA_RUN$\""   
  CreateShortCut "$SMSTARTUP\Start HSQL Server.lnk" "$\"$INSTDIR\lib\StartServer.bat$\"" "$\"$JAVA_RUN$\""


  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
;  CreateDirectory "$SMPROGRAMS\RecordEdit_HSQL"
;  CreateShortCut "$SMPROGRAMS\RecordEdit_HSQL\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
;  CreateShortCut "$SMPROGRAMS\RecordEdit_HSQL\Uninstall.lnk" "$INSTDIR\uninst.exe"
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
  IfSilent +2
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" /SD IDYES IDYES +2
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

  SetShellVarContext all
  
  Delete "$INSTDIR\${PRODUCT_NAME}.url"

  <WriteDelete/>
  
  RMDir "$INSTDIR\lib\Extensions"

  Delete "$INSTDIR\lib\BatchCopy.Bat"
  Delete "$INSTDIR\lib\InstallBat.txt"

  Delete "$INSTDIR\lib\swingx-subset-1.6.4.jar"
  Delete "$INSTDIR\lib\rsyntaxtextarea.jar"
  

  
  Delete "$INSTDIR\lib\JRecord.jar"
  Delete "$INSTDIR\lib\velocity-1.7.jar"
  Delete "$INSTDIR\lib\velocity-1.7-dep.jar"
  Delete "$INSTDIR\lib\PoEditor_re.jar"
  Delete "$INSTDIR\lib\swingx-subset-1.6.4.jar"
  Delete "$INSTDIR\lib\zip4j_1.3.2.jar"
  Delete "$INSTDIR\lib\hsqldbmain.jar"
  Delete "$INSTDIR\lib\zip4j_1.3.2.jar"
  Delete "$INSTDIR\lib\properties.zip"
  Delete "$INSTDIR\lib\BatchCopy.bat"
  Delete "$INSTDIR\lib\BatchCopy.bat"

  Delete "$INSTDIR\lib\ZCalendar.jar"
  Delete "$INSTDIR\lib\RecordEdit.jar"
  Delete "$INSTDIR\lib\LayoutEdit.jar"
  Delete "$INSTDIR\lib\cb2xml.Jar"
  Delete "$INSTDIR\lib\hsqldbmain.jar"
  Delete "$INSTDIR\lib\StAX.jar"
  Delete "$INSTDIR\lib\EditBigFile.Bat"
  Delete "$INSTDIR\lib\NsisUnpack.jar"
  Delete "$INSTDIR\lib\chardet.jar"
  Delete "$INSTDIR\lib\jibx-run.jar"
  Delete "$INSTDIR\lib\jlibdiff.jar"
  Delete "$INSTDIR\lib\EditBigFile.Bat"

  Delete "$PROFILE\RecordEditor_HSQL\Database\DBcreatedSuccessFully"
  Delete "$PROFILE\RecordEditor_HSQL\Extensions\Notes.txt"
  Delete "$PROFILE\RecordEditor_HSQL\User\LayoutExport\Notes.txt"
  Delete "$PROFILE\RecordEditor_HSQL\User\LayoutExport\Notes.txt"
  Delete "$PROFILE\RecordEditor_HSQL\User\LayoutExport\Notes.txt"
  Delete "$PROFILE\RecordEditor_HSQL\User\RecordTree\RecordTree_amsPO"
 
  
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Uninstall.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Website.lnk"

  Delete "$SMPROGRAMS\RecordEdit_HSQL\utils\CobolEditor.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\utils\Edit RecordEditor Startup Properties.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\utils\Run with Velocity.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\utils\CobolEditor Documentation.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Documentation\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Record Edit.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Stop Server.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Start Server.lnk"      
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Documentation\Examples.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Full Editor.lnk"
  Delete "$SMPROGRAMS\RecordEdit_HSQL\Documentation\HowTo.lnk"


  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Stop Server.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Start Server.lnk"      
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Examples.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Website.lnk" 
  Delete "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\HowTo.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Editor Big Files.lnk"                                                               
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\CobolEditor.lnk"                                         
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Edit RecordEditor Startup Properties.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\Run with Velocity.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\utils\CobolEditor Documentation.lnk"

  Delete "$SMPROGRAMS\$ICONS_GROUP\Basic Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Examples.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Full Editor.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\Record Editor Manual.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Record Layout Edit.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Stop Server.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Start Server.lnk"
  Delete "$SMPROGRAMS\$ICONS_GROUP\Documentation\HowTo.lnk"
  
  Delete "$SMSTARTUP\Start HSQL Server.lnk" 

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


  RMDir "$SMPROGRAMS\$ICONS_GROUP\Documentation\"
  RMDir "$SMPROGRAMS\$ICONS_GROUP\Utils\"
  RMDir "$SMPROGRAMS\$ICONS_GROUP\Documentation"
  RMDir "$SMPROGRAMS\$ICONS_GROUP\Utils"
  RMDir "$SMPROGRAMS\$ICONS_GROUP"
  RMDir "$SMPROGRAMS\RecordEdit_HSQL\Documentation"
  RMDir "$SMPROGRAMS\RecordEdit_HSQL\Utils"
  RMDir "$SMPROGRAMS\RecordEdit_HSQL"

  RMDir "$INSTDIR\Copybook\cb2xml"
  RMDir "$INSTDIR\Copybook\Cobol"
  RMDir "$INSTDIR\Copybook\Csv"
  RMDir "$INSTDIR\Copybook\Xml"
  RMDir "$INSTDIR\Copybook"

  
  RMDir "$INSTDIR\lib\utils"
  RMDir "$INSTDIR\lib\edit"
  RMDir "$INSTDIR\lib"
  RMDir "$INSTDIR\lib"
  RMDir "$INSTDIR\UserData"
  RMDir "$PROFILE\RecordEditor_HSQL\Copybook\cb2xml"
  RMDir "$PROFILE\RecordEditor_HSQL\Copybook\Cobol"
  RMDir "$PROFILE\RecordEditor_HSQL\Copybook\Csv"
  RMDir "$PROFILE\RecordEditor_HSQL\Copybook\Xml"
  RMDir "$PROFILE\RecordEditor_HSQL\Copybook"
  RMDir "$PROFILE\RecordEditor_HSQL\Extensions"

  RMDir "$INSTDIR\src"
  RMDir "$INSTDIR\lib\net"
  RMDir "$INSTDIR\lib"
  RMDir "$INSTDIR\lang\Sample"
  RMDir "$INSTDIR\lang\Diagram"
  RMDir "$INSTDIR\lang"
  
  RMDir "$INSTDIR\Docs\jsTree"
  RMDir "$INSTDIR\Docs\Diagram"
  RMDir "$INSTDIR\Docs"
  RMDir "$INSTDIR\License"
  
  RMDir "$PROFILE\RecordEditor_HSQL\Extensions"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates\Copybook"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates\File"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleVelocityTemplates"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleFiles\Csv"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleFiles\Fujitsu"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleFiles\po"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleFiles\Xml"
  RMDir "$PROFILE\RecordEditor_HSQL\SampleFiles"
  RMDir "$PROFILE\RecordEditor_HSQL\Database"
  RMDir "$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Copybook"
  RMDir "$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\File"
  RMDir "$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Script\JavaScript"
  RMDir "$PROFILE\RecordEditor_HSQL\User\VelocityTemplates\Script"
  RMDir "$PROFILE\RecordEditor_HSQL\User\VelocityTemplates"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Fields"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Icons"
  RMDir "$PROFILE\RecordEditor_HSQL\User\LayoutExport"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Copy"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Compare"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Filter"
  RMDir "$PROFILE\RecordEditor_HSQL\User\RecordTree"
  RMDir "$PROFILE\RecordEditor_HSQL\User\SortTree"
  RMDir "$PROFILE\RecordEditor_HSQL\User\Xslt"
  RMDir "$PROFILE\RecordEditor_HSQL\User\ExportScripts" 
  RMDir "$PROFILE\RecordEditor_HSQL\User\Scripts\Examples"       
  RMDir "$PROFILE\RecordEditor_HSQL\User\Scripts"       
  RMDir "$PROFILE\RecordEditor_HSQL\User"
  RMDir "$INSTDIR"         
  RMDir "$PROFILE\RecordEditor_HSQL"
  

      
  RMDir "$PROFILE\RecordEditor_HSQL\User\ExportScripts" 
  RMDir "$PROFILE\RecordEditor_HSQL\User\Scripts"       
  

;  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL" 
;  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\Record Edit HSQL\command"

  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}\command"
  DeleteRegKey HKEY_CLASSES_ROOT "*\Shell\${SEND_TO_NAME}" 
                                                                                       


  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  SetAutoClose true
SectionEnd