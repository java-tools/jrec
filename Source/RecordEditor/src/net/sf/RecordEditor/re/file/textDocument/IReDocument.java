package net.sf.RecordEditor.re.file.textDocument;

import javax.swing.text.Document;

import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.DocumentUpdateListner;

public interface IReDocument extends Document, DocumentUpdateListner {

	public abstract DataStoreContent getDataStoreContent();
}
