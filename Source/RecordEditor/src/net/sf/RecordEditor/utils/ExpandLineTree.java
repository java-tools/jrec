package net.sf.RecordEditor.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;

public class ExpandLineTree {

	private final static int CONTINUE = 1; 
	private final static int START_SAVING = 2; 
	private final static int EXIT = 4; 
	
	private final static Option NORMAL = new Option(CONTINUE, null) ;
	
	private final Option opt;
	@SuppressWarnings("rawtypes")
	private List<AbstractLine> list;
	private boolean addLine;
	private boolean exit = false;
	
	/**
	 * Expand line + its children into and arrayList
	 * @param line line to expand
	 * @return list of line + children
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<AbstractLine> expandTree(AbstractLine line) {
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>();
		(new ExpandLineTree(NORMAL, lines)).expand(line);

		return lines;
	}
	
	@SuppressWarnings("rawtypes")
	public static ExpandLineTree newExpandLineTree(List<AbstractLine> lines) {
		return new ExpandLineTree(NORMAL, lines);
	}

	/**
	 * Expand a lines children starting from a specific line
	 * @param fromLine line to start expanding from
	 * @return list of lines
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<AbstractLine> expandFrom(AbstractLine fromLine) {
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>();
		AbstractLine root = getRootLine(fromLine);
		
		(new ExpandLineTree(new Option(START_SAVING, fromLine), lines)).expand(root);

		return lines;
	}

	/**
	 * Expand a line tree to a specified line
	 * @param toLine line to expand tree to
	 * @return line expansion
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<AbstractLine> expandTo(AbstractLine toLine) {
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>();

		(new ExpandLineTree(new Option(EXIT, toLine), lines))
				.expand(ExpandLineTree.getRootLine(toLine));

		return lines;
	}

	private ExpandLineTree(Option option, @SuppressWarnings("rawtypes") List<AbstractLine> lines) {
		opt = option;
		list = lines;
		
		addLine = option.val != START_SAVING;
	}
	
	@SuppressWarnings("rawtypes")
	public final   void expand(AbstractLine line) {
		boolean doAdd = addLine;
		switch (opt.check(line)) {
		case (START_SAVING) : addLine = true;				break;
		case (EXIT):
			exit = true;
			return;
		}
		if (doAdd) {
			list.add(line);
		}
		
		AbstractTreeDetails treeDetails = line.getTreeDetails();
		List<AbstractLine> lineList;
		
		for (int i = 0; i < treeDetails.getChildCount(); i++) {
			lineList = treeDetails.getLines(i);
			if (lineList != null) {
				for (AbstractLine child : lineList) {
					expand(child);
					if (exit) {
						return;
					}
				}
			}
		}
		
		return;
	}
	
	@SuppressWarnings("rawtypes")
	public static AbstractLine getRootLine(AbstractLine line) {
		AbstractLine root = line.getTreeDetails().getRootLine();
		if (root == null) {
			root = line;
		}
		return root;
	}
	
//	public final   void filteredExpand(
//			AbstractLine line,
//			boolean[] include, 
//			AbstractLayoutDetails newLayout) {
//		boolean doAdd = addLine;
//		switch (opt.check(line)) {
//		case (START_SAVING) : addLine = true;				break;
//		case (EXIT):
//			exit = true;
//			return;
//		}
//		if (doAdd) {
//			list.add(line);
//		}
//		
//		AbstractTreeDetails childLines = line.getTreeDetails();
//		List<AbstractLine> lineList;
//		
//		if (childLines != null) {
//			for (int i = 0; i < childLines.getChildCount(); i++) {
//				lineList = childLines.getLines(i);
//				for (AbstractLine child : lineList) {
//					if (include[child.getPreferredLayoutIdx()]) {
//						child.setLayout(newLayout);
//						expand(child);
//						if (exit) {
//							return;
//						}
//					}
//				}
//			}
//		}
//		
//		return;
//	}

	
	private static class Option {
		public final int val;
		private final AbstractLine cmp;
		
		/**
		 * @param val
		 * @param compare
		 */
		public Option(int value, AbstractLine compare) {
			this.val = value;
			this.cmp = compare;
		}
		
		public int check(AbstractLine cmpVal) {
			if (cmpVal == cmp) {
				return val;
			}
			return CONTINUE;
		}
	}
}
