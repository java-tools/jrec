package net.sf.RecordEditor.utils.fileStorage;

public class FileChunkBfFixedLength extends FileChunkLine {
	private int initialFirstLine;

	public FileChunkBfFixedLength(FileDetails details, int theFirstLine, int recordCount) {
		super(details, theFirstLine);
		super.count = recordCount;
		initialFirstLine = theFirstLine;
	}


	@SuppressWarnings("rawtypes")
	@Override
	protected void uncompress() {
		
		if (recordStore != null) {
			details.registerUse(this);
		} else if (super.getDiskAddress() < 0 && compressed == null) {
			int rl = super.details.getLayout().getMaximumRecordLength();
			int len =  super.count * rl;
			recordStore = (RecordStoreBase) super.details.getEmptyRecordStore();
			recordStore.setBytes(
					super.details.readFromMainFile(initialFirstLine * rl, len), len, count
			);
		} else {
			super.uncompress();
		}
	}
	
	
}
