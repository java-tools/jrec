package net.sf.RecordEditor.re.display;

import net.sf.RecordEditor.utils.screenManager.ReFrame;


/**
 * Frame that stores the parent display and returns that if necessary
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ReChildFrame extends ReFrame implements IChildDisplay {

	private final AbstractFileDisplay source;


	public ReChildFrame(AbstractFileDisplay src, String enName) {
		this(src, "", enName);
	}


	public ReChildFrame(AbstractFileDisplay src, String name, String enName) {
		super(src.getFileView().getFileNameNoDirectory(), name, enName, src.getFileView().getBaseFile());
		source = src;
	}



	@Override
	public AbstractFileDisplay getSourceDisplay() {
		return source;
	}

}
