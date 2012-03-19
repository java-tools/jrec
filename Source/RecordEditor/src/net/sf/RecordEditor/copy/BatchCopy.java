package net.sf.RecordEditor.copy;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;

public class BatchCopy {
	
	private static int isInput = 1;
	private static int isOutput = 2;
	private static int isInputLayout = 3;
	private static int isOutputLayout = 4;
	private static int isErrorFile = 5;
	
	private StringBuilder bld = new StringBuilder();
	private String sep = "";

	public BatchCopy(AbstractLayoutSelection<?> reader1, AbstractLayoutSelection<?> reader2,  String[] args) {
		CopyDefinition def = null;
		JibxCall<CopyDefinition> jibx = new JibxCall<CopyDefinition>(CopyDefinition.class);
		int i, mode;
		String outLayout = "";
	
		try {
			if ("-h".equalsIgnoreCase(args[0]) || "-?".equalsIgnoreCase(args[0])) {
				helpMsg();
				return;
			}
			def = jibx.marshal(args[0]);
			
			mode = 0;
			
			for (i = 1; i < args.length; i++) {
				if ("-i".equalsIgnoreCase(args[i])) {
					assign(mode, def);
					mode = isInput;
				} else if ("-o".equalsIgnoreCase(args[i])) {
					assign(mode, def);
					mode = isOutput;
				} else 	if ("-il".equalsIgnoreCase(args[i])) {
					assign(mode, def);
					mode = isInputLayout;
				} else if ("-ol".equalsIgnoreCase(args[i])) {
					assign(mode, def);
					mode = isOutputLayout;
				} else if ("-ef".equalsIgnoreCase(args[i])) {
					assign(mode, def);
					mode = isErrorFile;
				} else if ("-h".equalsIgnoreCase(args[i]) || "-?".equalsIgnoreCase(args[i])) {
					helpMsg();
				} else{
					bld.append(sep).append(args[i]);
					sep = " ";
				}
			}
			
			assign(mode, def);
			
			System.out.println();
			try {
				if (def.newFile.layoutDetails != null) {
					outLayout = def.newFile.layoutDetails.name;
				}
				System.out.println("Copy Definition: " + args[0] );
				System.out.println("Copy From  File: " + def.oldFile.name + "\t\t Layout: " + def.oldFile.layoutDetails.name);
				System.out.println("Copy To    File: " + def.newFile.name + "\t\t Layout: " + outLayout);
			}catch (Exception e) {
			}
			System.out.println();
							

			DoCopy.copy(reader1, reader2, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void helpMsg() {
		
		System.out.println();
		System.out.println("Batch Copy, First parameter must be the copy-control-file after that:");
		System.out.println("    -i Input File                -il Input Layout");
		System.out.println("    -o Input File                -ol Input Layout");
		System.out.println("    -ef Field error file");
		System.out.println();
	}
	
	private void assign(int mode, CopyDefinition def) {
		String s = bld.toString();
		
		//System.out.println("    Mode: " + mode + " " + s);
		if (mode == isInput) {
			def.oldFile.name = s;
		} else if (mode == isOutput) {
			def.newFile.name = s;
		} else if (mode == isInputLayout) {
			def.getLayoutDetails().name = s;
			def.oldFile.layoutDetails.name = s;
			def.getLayoutDetails().name = def.oldFile.layoutDetails.name;
		} else if (mode == isOutputLayout) {
			def.newFile.layoutDetails.name = s;
		} else if (mode == isErrorFile) {
			def.newFile.layoutDetails.name = s;
		}

		bld = new StringBuilder();
		sep = "";
	}
}
