/*
 * @Author Bruce Martin
 * Created on 24/01/2007 for version 0.60
 *
 * Purpose:
 * Edit RecordEditor Startup options
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Major rewrite with tags being organised into
 *     logical groups + code removed to EditParams
 *   - JRecord Spun off, code reorg
 */
package net.sf.RecordEditor.editProperties;



public class EditOptions {


    /**
     *  Run the options editor
     * @param args program arguments
     */
    public static void main(String[] args) { 

     	boolean jdbc = ! (args != null && args.length > 0 && "nojdbc".equalsIgnoreCase(args[args.length-1]));
 
        new net.sf.RecordEditor.re.editProperties.EditOptions(true, jdbc, true);
    }
}
