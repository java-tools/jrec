/*
 * @Author Bruce Martin
 * Created on 8/02/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class implements a right Justfield Table cell render
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RightJustifyRendor extends DefaultTableCellRenderer {

    /**
     * This class implements a right Justfield Table cell render
     */
    public RightJustifyRendor() {
        super();

        setHorizontalAlignment(SwingConstants.RIGHT);
    }

}
