package net.sf.RecordEditor.copy;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;

public class BatchCopy {
	
	private static int isInput = 1;
	private static int isOutput = 2;

	public BatchCopy(AbstractLayoutSelection<?> reader1, AbstractLayoutSelection<?> reader2,  String[] args) {
		CopyDefinition def = null;
		JibxCall<CopyDefinition> jibx = new JibxCall<CopyDefinition>(CopyDefinition.class);
		int i, mode;
	
		try {
			def = jibx.marshal(args[0]);
			
			mode = 0;
			for (i = 1; i < args.length; i++) {
				if ("-i".equalsIgnoreCase(args[i])) {
					mode = isInput;
				} else if ("-o".equalsIgnoreCase(args[i])) {
					mode = isOutput;
				} else {
					if (mode == isInput) {
						def.oldFile.name = args[i];
					} else if (mode == isOutput) {
						def.newFile.name = args[i];
					}
					mode = 0;
				}
			}
			
			DoCopy.copy(reader1, reader2, def);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
