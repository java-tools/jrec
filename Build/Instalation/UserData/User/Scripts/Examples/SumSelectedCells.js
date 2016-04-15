/**
 *  Illistrate acessing underlying table - you need to be careful !!!
 */
 
 if (RecordEditorData.initialTab == null) {
 	  RecordEditorData.showMessage("No Table data available");
 } else {
     var tbl = RecordEditorData.initialTab.getJTable();
     var firstRow = tbl.getSelectedRow();
     var rowCount = tbl.getSelectedRowCount();
     var firstColumn = tbl.getSelectedColumn();
     var columnCount = tbl.getSelectedColumnCount();
     var sep = "";
     var disp = "Totals: ";
     
     for (c = 0; c < columnCount; c++) {
     	   var total = 0;
         for (r = 0; r < rowCount; r++) {
       	    var val = parseFloat(tbl.getValueAt(firstRow + r, firstColumn + c).toString());
         	 println(r + ", " + c +" >> >" + tbl.getValueAt(firstRow + r, firstColumn + c) +"< " + val);
         	 if (val != NaN) {
     	           total = total + val;
	          }
         }
         disp = disp + sep +  total	
         sep = ", "
     }
     
     RecordEditorData.showMessage(disp);
 }
 
