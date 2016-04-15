/*
 * Obscures a Text files contents;
 * It will obscure the files contents but leave hints to 
 * its format; Basically
 *    columns 1 to 10  ->  Unchanged
 *    a:z              ->  a
 *    A:Z              ->  A
 *    1:9              ->  9
 * other characters are left alone
 */

    if (RecordEditorData.confirmYN(
    	      "Confirm" 
    	    , "This Script will corrupt the file being editted\n" 
    	    + "it is useful if you want to send a sample file\n"
    	    + "(without corperate data) to an outside person\n\n"
    	    + "Do you really want to run it ???")
    ) {
        for (i = 0; i < RecordEditorData.view.getRowCount(); i++) {
            line = RecordEditorData.view.getLine(i);
            str = line.getFullLine()
            str2 = str.substring(0, 7)
            
            for (j = 7; j < str.length(); j++) {
            	ch = str.substring(j, j+1);
            	/*if (i < 2) print("." + ch)*/
            	if (ch > 'a' && ch <= 'z') {
            		ch = "a";
            	} else if (ch > 'A' && ch <= 'Z') {
            		ch = "A";
            	} else if (ch > '0' && ch <= '9') {
            		ch = "9";
            	} 
            	/*if (i < 2) print(ch)*/
            	str2 = str2 + ch
            }
            /*if (i < 2) println(" " + str.length());*/
            line.setData(str2);
        }
    }