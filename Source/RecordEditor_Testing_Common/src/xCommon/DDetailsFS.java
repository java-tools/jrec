package xCommon;

import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.edit.display.SaveAs.ZTstSaveAsBuildTestData;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlFileStructure;

public class DDetailsFS extends DDetails {



	public static DDetailsFS getDtar020Details(XSchemas.CharSetType type) throws IOException {
		return new DDetailsFS(XSchemas.DTAR020, Constants.IO_FIXED_LENGTH, null, type);
	}

	public static DDetailsFS getDtar021Details(XSchemas.CharSetType type) throws IOException {
		return new DDetailsFS(XSchemas.DTAR021, Constants.IO_FIXED_LENGTH, null, type);
	}

	public static DDetailsFS getDtar022Details(XSchemas.CharSetType type) throws IOException {
		return new DDetailsFS(XSchemas.DTAR022, Constants.IO_FIXED_LENGTH, null, type);
	}

	public static DDetailsFS getDtar023Details(XSchemas.CharSetType type) throws IOException {
		return new DDetailsFS(XSchemas.DTAR023, Constants.IO_FIXED_LENGTH, null, type);
	}


	public static DDetailsFS getCase1Details() throws IOException {
		return new DDetailsFS(XSchemas.CASE_1, Constants.IO_UNICODE_TEXT, 
				null, XSchemas.CharSetType.SHIFT_JIS, XFileData.case1Data() );
	}

	public static DDetailsFS getCase2Details() throws IOException {
		return new DDetailsFS(XSchemas.CASE_2, Constants.IO_BIN_TEXT, null, 
				XSchemas.CharSetType.SHIFT_JIS, XFileData.case2Data());
	}

	public final SaveAsPnlFileStructure saveAs;
	public final ZTstSaveAsBuildTestData.SaveAsPnlFileStructureTst saveAsTst;
	
	
	private DDetailsFS(XSchemas schemaBuilder, int fileStructure, int[] selected, XSchemas.CharSetType type) throws IOException {
		this(schemaBuilder, fileStructure, selected, type, XFileData.dtar020Data());
	}
	
	private DDetailsFS(XSchemas schemaBuilder, int fileStructure, int[] selected, XSchemas.CharSetType type,
			String[][] data
	) throws IOException {
		super(schemaBuilder, fileStructure, selected,  type, data);
		this.saveAsTst = ZTstSaveAsBuildTestData.getSaveAsPnlFileStructureTst(saveAsFields);
		this.saveAs = saveAsTst.saveAsFS;
	}
}
