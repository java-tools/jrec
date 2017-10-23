package utils;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;

/**
 * Copybook Interface for use in testing
 * 
 * @author Bruce Martin
 *
 */
public class XTstCopybookInterface implements CopyBookInterface {
	
	public static final int STANDARD_SYSTEM_ID = 0;
	
	private final List<SystemItem> systems = Arrays.asList(new SystemItem());
	private final List<ExternalRecord> records;
	private final Map<String, ExternalRecord> recordMap;
	
	public XTstCopybookInterface(List<ExternalRecord> records) {
		this.records = records;
		this.recordMap = new HashMap<>(records.size() * 2);
		
		for (ExternalRecord record : records) {
			recordMap.put(record.getRecordName().toLowerCase(), record);
		}
		
		systems.get(0).systemId = STANDARD_SYSTEM_ID;
		systems.get(0).description = "System 1";
	}

	@Override
	public void registerDecider(String layoutName, RecordDecider decider) {
		
	}

	@Override
	public ArrayList<SystemItem> getSystems() throws Exception {
		return new ArrayList<SystemItem>(systems);
	}

	@Override
	public void loadLayouts(ArrayList<LayoutItem> layouts) {
		for (ExternalRecord record : records) {
			layouts.add(new LayoutItem(record.getRecordName(), record.getRecordName() + "Description", STANDARD_SYSTEM_ID));
		}
	}

	@Override
	public LayoutDetail getLayout(String lName) {
		ExternalRecord rec = recordMap.get(lName.toLowerCase());
		if (rec != null) {
			return rec.asLayoutDetail();
		}
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

}
