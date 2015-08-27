/**
 * 
 */
package net.sf.RecordEditor.utils.fileStorage;

import java.util.AbstractList;

import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;


/**
 * 
 * This class implements a view as a range of lines in the primary view.
 * it is used to update a view after lines have been pasted to the primary file
 * 
 * @author Bruce Martin
 *
 */
public abstract class DataStoreBase
extends AbstractList<AbstractLine>
implements IDataStore<AbstractLine> {

	protected IDataStore<? extends AbstractLine> parent;

	private TextInterface textInterface = null;
	
	public DataStoreBase(IDataStore<? extends AbstractLine> parent) {
		this.parent = parent;
	}


	@Override
	public final void addChildViewRE(ISortNotify notify) {
		throw new RuntimeException("AddChildView is not supported DataStoreRange");
	}


	@Override
	public final String getSizeDisplayRE() {
		return "";
	}

	@Override
	public final long getSpaceRE() {
		return 0;
	}




	@Override
	public final AbstractLayoutDetails getLayoutRE() {
		return parent.getLayoutRE();
	}

	@Override
	public final void setLayoutRE(AbstractLayoutDetails layout) {
		parent.setLayoutRE(layout);
	}


	protected final int getParentIndex(Object o) {
		if (o instanceof IChunkLine) {
			@SuppressWarnings("rawtypes")
			IChunkLine lc = (IChunkLine) o;
			return (lc.getActualLine());
		}
		
		return parent.indexOf(o);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#hasEmbeddedCr()
	 */
	@Override
	public boolean hasEmbeddedCr() {
		return parent.hasEmbeddedCr();
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#isTextViewPossible()
	 */
	@Override
	public boolean isTextViewPossibleRE() {
		return parent.isTextViewPossibleRE();
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#getTextInterface()
	 */
	@Override
	public ITextInterface getTextInterfaceRE() {
		if (textInterface == null) {
			textInterface = new TextInterface(this);
		}
		return textInterface ;
	}


	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
		if (textInterface != null) {
			textInterface.resetPosition(e);
		}
	}

//
//	@Override
//	public int getParentIndex(int index) {
//		return index + start;
//	}
}
