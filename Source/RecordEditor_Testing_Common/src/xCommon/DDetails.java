package xCommon;

import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.edit.display.SaveAs.CommonSaveAsFields;
import net.sf.RecordEditor.re.file.FileView;


/**
 * Basic Test details:<ul>
 *  <li>SchemaBuilder
 *  <li>Character-set
 *  <li><i>Display</i> class
 *  <li>Normal schema
 *  <li><i>File</i> definition
 *  <li>save-fields
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public class DDetails {

	public static DDetails getDtar020Details(XSchemas.CharSetType type) throws IOException {
		return new DDetails(XSchemas.DTAR020, Constants.IO_FIXED_LENGTH, null, type);
	}

	public static DDetails getDtar020Details(XSchemas.CharSetType type, int[] rows) throws IOException {
		return new DDetails(XSchemas.DTAR020, Constants.IO_FIXED_LENGTH, rows, type);
	}

	public final XSchemas schemaBuilder;
	public final XSchemas.CharSetType type;
	public final XDisplay display;
	public final LayoutDetail schema;
	public final FileView file;
	public final CommonSaveAsFields saveAsFields;
	
	
	private DDetails(XSchemas schemaBuilder, int fileStructure, int[] selected, XSchemas.CharSetType type) throws IOException {
		this(schemaBuilder, fileStructure, selected, type, XFileData.dtar020Data());
	}
	
	protected DDetails(XSchemas schemaBuilder, int fileStructure, int[] selected, XSchemas.CharSetType type,
			String[][] data
	) throws IOException {
		this.schemaBuilder = schemaBuilder;
		this.schema = schemaBuilder.toLayout(type.charset, fileStructure);
		this.file = XFileData.toFileView(schema, data);
		this.display = new XDisplay(file, selected);
		this.saveAsFields = new CommonSaveAsFields(display, file, null);
		this.type = type;
	}
	
	public final LayoutDetail getLayout(int fileStructure) throws IOException {
		return schemaBuilder.toLayout(type.charset, fileStructure);
	}
	
	public final LayoutDetail getLayoutChgFont(int fileStructure) throws IOException {
		return schemaBuilder.toLayout(XSchemas.otherCharset(type).charset, fileStructure);
	}

}
