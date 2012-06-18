package net.sf.JRecord.zTest.Types;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import junit.framework.TestCase;

public class TstTypesGeneral extends TestCase {

	public static final int[] NUMERIC_TYPES = {

	 	Type.ftNumLeftJustified,
	 	Type.ftNumRightJustified,
	 	Type.ftNumZeroPadded,
	 	Type.ftAssumedDecimal,
	 	Type.ftSignSeparateLead,
	 	Type.ftSignSeparateTrail,
	 	Type.ftDecimal,
	 	Type.ftBinaryInt,
	 	Type.ftPostiveBinaryInt,
	 	Type.ftFloat,
	 	Type.ftDouble,
	 	Type.ftNumAnyDecimal,
	 	Type.ftPositiveNumAnyDecimal,
	 	Type.ftBit,

	 	Type.ftPackedDecimal,
	 	Type.ftZonedNumeric,
	 	Type.ftBinaryBigEndian,
	 	Type.ftPositiveBinaryBigEndian,
	 	Type.ftRmComp,
	 	Type.ftRmCompPositive,

	 	Type.ftFjZonedNumeric,
	};

	public static final int[] BINARY_TYPES = {
		Type.ftHex,
		Type.ftDecimal,
		Type.ftBinaryInt,
		Type.ftPostiveBinaryInt,
		Type.ftFloat,
		Type.ftDouble,

		Type.ftBit,

	 	Type.ftPackedDecimal,
	 	Type.ftBinaryBigEndian,
	 	Type.ftPositiveBinaryBigEndian,
	 	Type.ftRmComp,
	 	Type.ftRmCompPositive,

		Type.ftCharRestOfRecord
	};

	private TypeManager typeManager = new TypeManager();
	private byte[] bytes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};


	public void testNumeric() {
		TypeManager m = TypeManager.getInstance();
		HashSet<Integer> numTypes = new HashSet<>(NUMERIC_TYPES.length + 25);
		HashSet<Integer> binTypes = new HashSet<>(BINARY_TYPES.length + 25);

		for (int i = 0; i < 200; i++) {
			if (m.getType(i).isBinary()) {
				System.out.print("  " + i);
			}
		}

		for (int typeId : NUMERIC_TYPES) {
			assertTrue("Numeric Type: " + typeId, m.getType(typeId).isNumeric());
			numTypes.add(typeId);
		}

		for (int i = 0; i < 200; i++) {
			if (! numTypes.contains(i)) {
				assertFalse("Char Type: " + i, m.getType(i).isNumeric());
			}
		}

		for (int typeId : BINARY_TYPES) {
			assertTrue("Bin Type: " + typeId, m.getType(typeId).isBinary());
			binTypes.add(typeId);
		}

		for (int i = 0; i < 200; i++) {
			if (! binTypes.contains(i)) {
				assertFalse("Type: " + i, m.getType(i).isBinary());
			}
		}
	}


	public  void testNumbers() throws RecordException {
		Random r = new Random();
		FieldDetail fd;
		int val;
		String res = "";
		boolean ok = true;

		for (int type : NUMERIC_TYPES) {
			switch (type) {
			case Type.ftBit: break;
			default:
				fd = getType(1, 8, type);
				for (int i = 0; i < 5000; i++) {
					val = r.nextInt(10000);

					try {
						setFldValue(fd, val);
						res = getFldValue(fd);
						assertEquals("Type: " + type, val, (new BigDecimal(res.trim())).intValue());
					} catch (Exception e) {
						System.out.println();
						System.out.println();
						System.out.println("Type: " + type + " Count: " + i + " Val: " + val + " result: >" + res + "<");
						System.out.println();
						System.out.println();
						e.printStackTrace();
						ok = false;
						break;
					}
				}
			}
		}

		assertTrue("Check abends ", ok);
	}



	  /**
     * Get field value
     * @param fld field definition
     * @return value of the field
     */
    private String getFldValue(FieldDetail fld) {
        return typeManager.getType(fld.getType()).getField(bytes, fld.getPos(), fld).toString();
    }

    /**
     * Set Fields value
     * @param fld field definition
     * @param val value to assign
     * @throws RecordException any error that occurs
     */
    private void setFldValue(FieldDetail fld, Integer val) throws RecordException {
        typeManager.getType(fld.getType()).setField(bytes, fld.getPos(), fld, val);
    }

	private FieldDetail getType(int pos, int len, int type) {

		FieldDetail field = new FieldDetail("", "", type, 0, "", -1, "");

		field.setPosLen(pos, len);

		return field;
	}

}
