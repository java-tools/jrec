/*
 * Created on 27/09/2004
 *
 */
package net.sf.RecordEditor.layoutEd.Record;

/**
 * This interface is used to all child Panels to trigger Search Arguments changed
 * actions in the parent panel
 *
 * @author Bruce Martin
 * @version 0.51
 */
public interface SearchArgAction {

    /**
     * A Search argument has changed
     *
     */
    void searchArgChanged();
}
