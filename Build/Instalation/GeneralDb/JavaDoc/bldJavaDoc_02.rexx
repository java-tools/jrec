
   wrkspace = 'C:\Users\mum\Bruce\workspace\'
   src = wrkspace'RecordEditor_097\src'
   srcJrec = wrkspace'JRecord_097\src'
   srcJCommon = wrkspace'JRecord_Common_097\src'
   '"C:\Program Files (x86)\Java\jdk1.7.0_02\bin\JavaDoc.exe" ',
           '-d RecordEditor_Script_JavaDoc',
           '-overview MacroOverview.htm' ,
           '-nodeprecated' ,
           '-nodeprecatedlist ' ,
           '-sourcepath 'src';'srcJrec';'srcJCommon,
           '-subpackages  net.sf.JRecord.Details net.sf.RecordEditor.re.display',
           src'/net/sf/RecordEditor/re/file/FileView.java',
           src'/net/sf/RecordEditor/re/script/ScriptData.java' ,
           src'/net/sf/RecordEditor/utils/common/ReActionHandler.java ',
           srcJCommon'/net/sf/JRecord/Common/AbstractFieldValue.java',
           srcJCommon'/net/sf/JRecord/Common/IFieldDetail.java',
           src'/net/sf/RecordEditor/re/script/IExecDirectoryConstants.java'

 /*          'net.sf.RecordEditor.re.file.FileView net.sf.RecordEditor.re.script.ScriptData'*/
           /*'net.sf.RecordEditor.edit'   IFieldDetail */