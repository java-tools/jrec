package net.sf.RecordEditor.test.recordSelection;

import java.util.List;

import junit.framework.TestCase;

import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;

public class TestValues {
	public static final String OR  = "or";
	public static final String AND = "and";

	public final RecordSelectionRec[] singleTest = {
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=", "val1"),
	};
	public final OpTree singleTestResult =
			new OpTree("f1", "=", "val1").setId("");
	
	public final RecordSelectionRec[] andTest = {
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=",  "val1"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f2", ">",  "val2"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f3", "<",  "val3"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f4", "!=", "val4"),
	};
	public final OpTree andTestResult =
			new OpTree(
					AND,
					new OpTree[] {
							new OpTree("f1", "=", "val1"),
							new OpTree("f2", ">", "val2"),
							new OpTree("f3", "<", "val3"),
							new OpTree("f4", "!=", "val4"),
					}
			).setId("");
			
	public final RecordSelectionRec[] orTest1 = {
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f1", "=",  "val1"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f2", ">",  "val2"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR , "f3", "<",  "val3"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f4", "!=", "val4"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f5", "=",  "val5"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f6", ">",  "val6"),
		new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR , "f7", "<",  "val7"),
	};
	
	public final OpTree orTest1Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree(
									AND,
									new OpTree[] {
											new OpTree("f1", "=", "val1"),
											new OpTree("f2", ">", "val2"),
									}
							),
							new OpTree(
									AND,
									new OpTree[] {
											new OpTree("f3", "<", "val3"),
											new OpTree("f4", "!=", "val4"),
											new OpTree("f5", "=", "val5"),
											new OpTree("f6", ">", "val6"),
									}
							),
							new OpTree("f7", "<", "val7"),
					}
	).setId("");
	
	
	
	public final RecordSelectionRec[] orTest2 = {
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f1", "=",  "val1"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f2", ">",  "val2"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR , "f3", "<",  "val3"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f4", "!=", "val4"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f5", "=",  "val5"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f6", ">",  "val6"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR , "f7", "<",  "val7"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND,   "f8", "!=", "val8"),
	};

	public final OpTree orTest2Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree(
									AND,
									new OpTree[] {
													new OpTree("f1", "=", "val1"),
													new OpTree("f2", ">", "val2"),
										}
									),
							new OpTree(
											AND,
									new OpTree[] {
													new OpTree("f3", "<", "val3"),
													new OpTree("f4", "!=", "val4"),
													new OpTree("f5", "=", "val5"),
													new OpTree("f6", ">", "val6"),
											}
									),
							new OpTree(
											AND,
									new OpTree[] {
													new OpTree("f7", "<", "val7"),
													new OpTree("f8", "!=", "val8"),
											}
									),
					}
	).setId("");

	
	public final RecordSelectionRec[] orTest3 = {
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=",  "val1"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f2", ">",  "val2"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f3", "<",  "val3"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f4", "!=", "val4"),
	};
	
	public final OpTree orTest3Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree("f1", "=", "val1"),
							new OpTree("f2", ">", "val2"),
							new OpTree("f3", "<", "val3"),
							new OpTree("f4", "!=", "val4"),
					}
			).setId("");
	
	public final RecordSelectionRec[] orTest4 = {
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=",  "val1"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1a", "=",  "val1a"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f2", ">",  "val2"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f3", "<",  "val3"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f4", "!=", "val4"),
	};
	
	public final OpTree orTest4Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree(
									AND,
									new OpTree[] {
													new OpTree("f1", "=", "val1"),
													new OpTree("f1a", "=", "val1a"),
										}
									),
							new OpTree("f2", ">", "val2"),
							new OpTree("f3", "<", "val3"),
							new OpTree("f4", "!=", "val4"),
					}
			).setId("");
	
	
	
	public final RecordSelectionRec[] orTest5 = {
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=",  "val1"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR,  "f2",  ">",  "val2"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f2a", ">",  "val2a"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f3", "<",  "val3"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f4", ">=", "val4"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f5", "<=", "val5"),
	};
	
	public final OpTree orTest5Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree("f1", "=", "val1"),
							new OpTree(
									AND,
									new OpTree[] {
													new OpTree("f2" , ">", "val2"),
													new OpTree("f2a", ">", "val2a"),
										}
									),
							new OpTree("f3", "<", "val3"),
							new OpTree("f4", ">=", "val4"),
							new OpTree("f5", "<=", "val5"),
					}
			).setId("");

	
	public final RecordSelectionRec[] orTest6 = {
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f1", "=",  "val1"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f2", ">",  "val2"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f3", "<",  "val3"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_OR, "f4", "!=", "val4"),
			new RecordSelectionRec(-1, -1, 1, Common.BOOLEAN_OPERATOR_AND, "f4a", "!=", "val4a"),
	};
	
	public final OpTree orTest6Result =
			new OpTree(
					OR,
					new OpTree[] {
							new OpTree("f1", "=", "val1"),
							new OpTree("f2", ">", "val2"),
							new OpTree("f3", "<", "val3"),
							new OpTree(
									AND,
									new OpTree[] {
													new OpTree("f4" , "!=", "val4"),
													new OpTree("f4a", "!=", "val4a"),
										}
									),
					}
			).setId("");
	
	public final RecordSelectionRec[][] tests = {
			singleTest,
			andTest,
			orTest1,
			orTest2,
			orTest3,
			orTest4,
			orTest5,
			orTest6,
	};
	public final void insertTest(int key, RecordSelectionRec[] test) {
		RecordSelectionDB db = new RecordSelectionDB();
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		
		db.deleteAll();
		db.setParams(key, key+1);
		db.deleteAll();
		
		for (RecordSelectionRec tst : test) {
			db.insert(tst);
		}
		db.close();
	}
	
	public void compare(String id, RecordSelectionRec[] expected, List<RecordSelectionRec> actual) {
		TestCase.assertEquals(id + " List Size ", expected.length, actual.size());
		
		for (int i = 0; i <  expected.length; i++) {
			String id1 = id + "." + i;
			TestCase.assertEquals(id1 + " Bool ", expected[i].getBooleanOperator(), actual.get(i).getBooleanOperator());
			TestCase.assertEquals(id1 + " Field ", expected[i].getFieldName(), actual.get(i).getFieldName());
			TestCase.assertEquals(id1 + " Operator ", expected[i].getOperator(), actual.get(i).getOperator());
			TestCase.assertEquals(id1 + " Value ", expected[i].getFieldValue(), actual.get(i).getFieldValue());
		}
	}
	
	public static class OpTree {
		public final String boolOp, op, fieldName, fieldValue;
		public final OpTree[] tree;
		public String id;
		
		
		
		public OpTree(String op, OpTree[] tree) {
			super();
			this.boolOp = op;
			this.tree = tree;
			this.op = "";
			this.fieldName = "";
			this.fieldValue = "";
		}



		public OpTree(String fieldName, String op, String fieldValue) {
			super();
			this.op = op;
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
			
			boolOp = "";
			tree = null;
		}
		
		public OpTree setId(String parentId) {
			id = parentId + ": " + boolOp;
			if (tree != null) {
				for (int i =0;i < tree.length; i++) {
					tree[i].setId(id + "." + i);
				}
			}
			return this;
		}
		
	}
}
