/*

      Run a Record Tree from the script directory, Save the Tree as Xml and then 
      Execute the find Command

*/

   screen = RecordEditorData.executeSavedTask(
   	   	RecordEditorData.executeConstansts.scriptDir /*"ScriptDir"*/, 
   	   	"RecordTree_amsPO.xml");
 
   if (screen != null) { /* Checking there is a valid parent screen   */
   	    /* Checking the SAVE_AS_XML command is accepted by the current screen, then executing it */
       if (screen.isActionAvailable(RecordEditorData.actionConstants.SAVE_AS_XML)) {
    	   RecordEditorData.showMessage("Saving file as Xml;\nPlease Select the file name and directory");
           screen.executeAction(RecordEditorData.actionConstants.SAVE_AS_XML)
       }
   	    /* Checking the FIND command is accepted by the current screen then executing it
   	       This will Start the Find Dialog                                               */
       if (screen.isActionAvailable(RecordEditorData.actionConstants.FIND)) {
           screen.executeAction(RecordEditorData.actionConstants.FIND)
       }
   } else {
      print("Screen is null");
   }
