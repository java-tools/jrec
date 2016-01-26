package net.sf.RecordEditor.re.file.textDocument;

import javax.swing.text.Element;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;



@SuppressWarnings("serial")
public class CobolCopybookEditorKit extends StyledEditorKit {
	
	public static final String NAME = "cobol";

	private ViewFactory viewFactory = new ViewFactory() {
		@Override public View create(Element elem) {
			return new CobolCopybookDocView(elem);
		}
	};
	
    @Override
	public ViewFactory getViewFactory() {
	    return viewFactory;
	}

	@Override
	public String getContentType() {
	    return NAME;
	}
}
