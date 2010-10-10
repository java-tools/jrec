/* rexx */
parse arg id x
   nsis = "/home/bm/Programs/nsis-2.46/makensis"
   
   
   if id = 'h2' then do
       nsis "EXxRecordEdit_H2.nsi"
   end; else do
       nsis "EXxRecordEdit_MSAccess.nsi"
       nsis "EXxRecordEdit_HSQL.nsi"
       nsis "EXxRecordEdit_H2.nsi"
   end


  
