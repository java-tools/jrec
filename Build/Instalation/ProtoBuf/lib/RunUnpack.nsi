; Java Launcher
;--------------

; You want to change the below lines   
Name "RunUpack"   
Caption "my program Launcher"       
OutFile "RunUnpack.exe"

; param below can be user, admin    
RequestExecutionLevel admin

!include UAC.nsh

SilentInstall silent
AutoCloseWindow true
ShowInstDetails show

Section ""    
  ; command to execute    
  StrCpy $0 'java -cp RunProtoBufEditor.jar net.sf.RecordEditor.utils.Run'      
  SetOutPath $EXEDIR    
  Exec $0    
SectionEnd

