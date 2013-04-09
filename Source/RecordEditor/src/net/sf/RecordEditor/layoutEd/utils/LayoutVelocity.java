package net.sf.RecordEditor.layoutEd.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.db.Table.TypeList;
import net.sf.RecordEditor.utils.TypeNameArray;

public class LayoutVelocity {

	private final TypeList types;
	private final TypeNameArray typeNames = new TypeNameArray();

	public LayoutVelocity(int fromIdx) {
		int key;

		types = new TypeList(fromIdx, false, false);

  		for (int i = 0; i < types.getSize(); i++) {
  			key = ((Integer) types.getKeyAt(i)).intValue();

  			typeNames.set(key, String.valueOf(types.getFieldAt(i)));
  		}
	}


	public final void run(
			final String template, RecordRec record,
			final String outputFile) throws Exception {

		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf8"));
		try {
			net.sf.RecordEditor.re.script.RunVelocity.getInstance()
				  .genSkel(template, record.getValue(), typeNames, outputFile, w);
		} finally {
			w.close();
		}
	}
}
