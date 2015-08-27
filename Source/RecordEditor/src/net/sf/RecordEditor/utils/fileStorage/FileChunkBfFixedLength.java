package net.sf.RecordEditor.utils.fileStorage;


public class FileChunkBfFixedLength extends FileChunkLine {
	private long initialFirstLine;

	public FileChunkBfFixedLength(FileDetails details, int theFirstLine, long textPos, int recordCount) {
		super(details, theFirstLine, textPos);
		super.count = recordCount;
		initialFirstLine = theFirstLine;
	}


	@Override
	protected void uncompress() {
		
		if (recordStore != null) {
			details.registerUse(this);
		} else if (super.getDiskAddress() < 0 && compressed == null) {
			int rl = super.details.getLayout().getMaximumRecordLength();
			int len =  super.count * rl;
			long pos = ((long) initialFirstLine) * rl;
			recordStore = (RecordStoreBase) super.details.getEmptyRecordStore();
			recordStore.setBytes(
					super.details.readFromMainFile(pos, len), len, count
			);
		} else {
			super.uncompress();
		}
	}
	
	
}
