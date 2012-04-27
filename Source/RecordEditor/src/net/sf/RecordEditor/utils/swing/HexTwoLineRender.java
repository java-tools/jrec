/*
 * @Author Bruce Martin
 * Created on 22/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.JLabel;


/**
 * table rendor for 3 line hex display
 *
 * @author Bruce Martin
 *
 */
public class HexTwoLineRender extends HexGenericRender  {
    /**
     * @param font fontname
     */
    public HexTwoLineRender(final String font) {
        super(font, new HexTwoLineField(font));
    }
}
