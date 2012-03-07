package net.sf.JRecord.Numeric;

import net.sf.JRecord.Types.Type;

/**
 * Standard Cobol Type to JRecord Type conversion class.
 *
 * @author Bruce Martin
 *
 */public class BasicConvert implements Convert {

    private int identifier;
    
    private int binId;
    private boolean usePositiveInteger;
    
    // Using Object instead of BasicNumericDefinition to avoid dependency on cb2xml. 
    // It allows the class to be used in RecordEditor Edit Properties without cb2xml or with an old
    // cb2xml. Most user's of the RecordEditor probably do not use Cobol so why make the dependancy.
    private Object numericDefinition;
    
    private String name;

    public BasicConvert(int id, String name, int binaryId, int[] binarySizes, boolean usePositive) {
    	this(id, name, binaryId, binarySizes, null, usePositive, 4, 8);
    }
    
    public BasicConvert(int id, String binName, int binaryId, int[] binarySizes, int[] SynchronizeAt, 
    		boolean usePositive, int floatSynchronize, int doubleSynchronize) {
    	
    	name = binName;
    	try {
    		numericDefinition = new net.sf.cb2xml.def.BasicNumericDefinition(
    				binName, binarySizes, SynchronizeAt, usePositive, floatSynchronize, doubleSynchronize
    		);
    	} catch (NoClassDefFoundError e) {
			System.out.println("Class Not Found: " + e.getMessage());
 		}
    	identifier = id;
 
        
        binId = binaryId;

    }
    


    /**
     * This method will convert a Cobol Definition into a JRecord type Id 
     * @param usage Cobol usage (i.e. Comp etc)
     * @param picture Cobol picture
     * @param signed wether it is a signed field
     * @return JRecord type code
     */
    public int getTypeIdentifier(String usage, String picture, boolean signed) {
    	int lType = -121;

        if ("computational".equals(usage)
        || "computational-4".equals(usage)
        || "computational-5".equals(usage)
        || "computational-6".equals(usage)
        || "binary".equals(usage)) {
        	if (binId == Convert.FMT_MAINFRAME
           	||  binId == Convert.FMT_FUJITSU
           	||  binId == Convert.FMT_BIG_ENDIAN) {
                 lType = Type.ftBinaryBigEndian;
                 if (usePositiveInteger && ! signed) {
                	 lType = Type.ftPositiveBinaryBigEndian;
            	}
            } else {
                lType = Type.ftBinaryInt;
                if (usePositiveInteger && ! signed) {
                	lType = Type.ftPostiveBinaryInt;
                }
            }
        } else if ("computational-3".equals(usage)) {
            lType = Type.ftPackedDecimal;
        } else if ("computational-1".equals(usage)) {
            lType = Type.ftFloat;
        } else if ("computational-2".equals(usage)) {
            lType = Type.ftDouble;
        } else if (picture.indexOf('Z') >= 0
               ||  picture.indexOf('-') >= 0
               ||  picture.indexOf('+') >= 0
               ||  picture.indexOf('.') >= 0) {
        	lType = Type.ftNumRightJustified;
        } 
        return lType;
    }

    /**
     * Get the conversion Identifier
     * @return conversion Identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    
    /**
     * Get the binary id to use
     * @return actual binary Id
     */
    public int getBinaryIdentifier() {
    	return binId;
    }
    
     /**
      * wether positive numbers should be represented by positive integers
      * @return if positive integers are use
      */
    public boolean isPositiveIntAvailable() {
    	 return usePositiveInteger;
     }

	/**
	 * @see net.sf.JRecord.Numeric.Convert#getNumericDefinition()
	 */
	@Override
	public Object getNumericDefinition() {
		return numericDefinition;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
    
    
}
