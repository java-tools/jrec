/**
 * 
 */
package net.sf.RecordEditor.utils.swing.extraColumn;

import javax.swing.JComponent;

/**
 * @author Bruce
 *
 */
public class ExtraSwingComponent implements IExtraComponent {

	private final JComponent component;

	public ExtraSwingComponent(JComponent component) {
		super();
		this.component = component;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.extraColumn.IExtraComponent#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		return component;
	}

}
