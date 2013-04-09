package net.sf.RecordEditor.test.recordSelection;

import junit.framework.TestCase;
import net.sf.JRecord.detailsSelection.AbsGroup;
import net.sf.JRecord.detailsSelection.AndSelection;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.OrSelection;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.test.recordSelection.TestValues.OpTree;
import net.sf.RecordEditor.utils.ReadRecordSelection;
import net.sf.RecordEditor.utils.common.Common;

public class TestReadRecordSelection extends TestCase {

	TestValues tstVals = new TestValues();

	public void test1() {
		runTest("Test 1 - Single ", tstVals.singleTest, tstVals.singleTestResult);
	}

	public void test2() {
		runTest("Test 2 - And ", tstVals.andTest, tstVals.andTestResult);
	}

	public void test3() {
		runTest("Test 3 - Or 1 ", tstVals.orTest1, tstVals.orTest1Result);
	}

	public void test4() {
		runTest("Test 4 - Or 2 ", tstVals.orTest2, tstVals.orTest2Result);
	}


	public void test5() {
		runTest("Test 5 - Or 3 ", tstVals.orTest3, tstVals.orTest3Result);
	}

	public void test6() {
		runTest("Test 6 - Or 4 ", tstVals.orTest4, tstVals.orTest4Result);
	}

	public void test7() {
		runTest("Test 7 - Or 5 ", tstVals.orTest5, tstVals.orTest5Result);
	}

	public void test8() {
		runTest("Test 8 - Or 6 ", tstVals.orTest6, tstVals.orTest6Result);
	}

	public void runTest(String id, RecordSelectionRec[] test, OpTree result) {
		int key = -1331;
		tstVals.insertTest(key, test);

		RecordSel sel = ReadRecordSelection.getInstance().getRecordSelection(
				Common.getConnectionIndex(), key, key+1, null, "", "");

		checkTest(id, sel, result);


		RecordSel sel1 = ReadRecordSelection.getInstance().getRecordSelection(
				Common.getConnectionIndex(), key, key+1, null, "fff", "vvv");

		checkTestDefaultField(id, sel1, result);

	}



	private static void checkTestDefaultField(String id, RecordSel sel, OpTree result) {

		assertTrue(id + " aa", sel instanceof AndSelection);
		if (sel instanceof AndSelection) {
			AndSelection a = (AndSelection) sel;
			assertEquals(id, 2, a.getSize());

			checkField(
					id + "Field: ",
					a.get(0),
					new OpTree("fff", "=", "vvv"));
			checkTest(id + " db: ", a.get(1), result);
		}
	}

	private static void checkTest(String id, RecordSel sel, OpTree result) {
		String testId = id + result.id;

		System.out.print(testId);
		if ("".equals(result.boolOp)) {
			checkField(testId, sel, result);
		} else {
			System.out.print("\t\t" + sel.getClass().getName());
			if (sel instanceof AbsGroup) {
				AbsGroup selGroup = (AbsGroup) sel;
				if (sel instanceof OrSelection) {
					assertEquals(testId, TestValues.OR, result.boolOp);
				} else if (sel instanceof AndSelection) {
					assertEquals(testId, TestValues.AND, result.boolOp);
				}
				System.out.println("---> " + result.tree.length + " " + selGroup.getSize());
				assertEquals(testId, result.tree.length, selGroup.getSize());

				System.out.println();
				for (int i = 0; i < result.tree.length; i++) {
					checkTest(id, selGroup.get(i), result.tree[i]);
				}
			} else {
				System.out.println("Error Expecting Group: " + id + " " + result.id);
				assertTrue("Error Expecting Group: " + id + " " + result.id, false);
			}
		}
		System.out.println();
	}

	private static void checkField(String testId, RecordSel sel, OpTree result) {
		if (sel instanceof FieldSelect) {
			FieldSelect fs = (FieldSelect) sel;
			System.out.print("\t" + result.fieldName
					+ "\t" +result.op + "\t" + result.fieldValue);
			System.out.print("\t" + fs.getFieldName()
					+ "\t" + fs.getOperator() + "\t" + fs.getFieldValue());

			assertEquals(testId, result.fieldName, fs.getFieldName());
			assertEquals(testId, result.op, fs.getOperator());
			assertEquals(testId, result.fieldValue, fs.getFieldValue());

			String op = result.op;
			if ("!=".equals(op) ) {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.NotEqualsSelect);
			} else if (">".equals(op)) {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.GreaterThan);
			} else if (">=".equals(op)) {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.GreaterThan);
			} else if ("<".equals(op)) {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.LessThan);
			} else if ("<=".equals(op)) {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.LessThan);
			} else {
				assertTrue(testId+ " wrong class", fs instanceof FieldSelectX.EqualsSelect);
			}

		} else {
			System.out.println("Error Not expecting Group: " + testId + " " + result.id);
			assertTrue("Error Not expecting Group: " + testId + " " + result.id, false);
		}

	}
}
