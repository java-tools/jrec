package net.sf.RecordEditor.copy;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.tree.TreeParserRecord;
import net.sf.RecordEditor.edit.tree.TreeToXml;
import net.sf.RecordEditor.edit.util.WriteLinesAsXml;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.jibx.compare.RecordParent;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;

public class DoCopy2Xml {

	public void copyFile(AbstractLayoutSelection layoutReader1, CopyDefinition copy) throws Exception {

		AbstractLayoutDetails layout = layoutReader1.getRecordLayout(
				copy.oldFile.layoutDetails.name, copy.oldFile.name);
		
		System.out.println(" Copy Xml:  " + layout.getRecordCount()  );
		if (layout.getRecordCount() > 1 && copy.oldFile.getLayoutDetails() != null) {
			layout = layout.getFilteredLayout(copy.oldFile.getLayoutDetails().getFilteredRecords());
		}
		
		net.sf.RecordEditor.edit.file.FileView view = new net.sf.RecordEditor.edit.file.FileView(
					copy.oldFile.name, layout, true);
		
		if (layout.hasChildren()) {
			new net.sf.RecordEditor.edit.tree.ChildTreeToXml(copy.newFile.name,  view.getLines());
		} else if (layout.getRecordCount() == 1) {
			new WriteLinesAsXml(copy.newFile.name, view.getLines());
		} else {
			int idx;
	    	RecordParent[] parentRel = copy.treeDefinition.parentRelationship;
	    	int[] parentIdxs = new int[layout.getRecordCount()];
	    	
	    	for (int i = 0; i < parentIdxs.length; i++) {
	    		parentIdxs[i] =  net.sf.JRecord.Common.Constants.NULL_INTEGER;
	    	}
	    	for (int i = 0; i < parentRel.length; i++) {
				idx = layout.getRecordIndex(parentRel[i].recordName);
				if (idx >= 0) {
					parentIdxs[idx] = layout.getRecordIndex(parentRel[i].parentName);
				}
	    	}

	    	TreeParserRecord parser = new TreeParserRecord(parentIdxs);
	
			new TreeToXml(copy.newFile.name, parser.parse(view));
		}
	}
	
	public static DoCopy2Xml newCopy() {
		return new DoCopy2Xml();
	}
}
