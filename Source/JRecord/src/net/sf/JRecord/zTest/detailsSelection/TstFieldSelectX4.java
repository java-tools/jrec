package net.sf.JRecord.zTest.detailsSelection;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.zTest.Common.TstConstants;

public class TstFieldSelectX4 extends TestCase {

	public static final String[] TEXT_COMPARISON_OPERATORS = {
		"=", "eq", "!=", "<>", "ne", ">", "gt", ">=", "ge", "<", "lt", "<=", "le",
		Constants.STARTS_WITH,  Constants.DOES_NOT_CONTAIN,  Constants.CONTAINS,
		Constants.TEXT_NE
	};
	public static final String[][][] GET_RESULTS_FIELD1 = {


		{ // 0 >> d		"d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 1 >> dc	"d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true",	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain","false","false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains","true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)","true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 2 >> dc123	"d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Contains",	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 3 >> D		"d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"gt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{">="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"ge"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"lt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"le"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Starts With",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 4 >> DC	"d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"gt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{">="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"ge"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"lt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"le"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Starts With",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 5 >> DC123 "d", 		"c", 		"dc", 		"dc1", 		"e", 		"c1"
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"gt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{">="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"ge"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"lt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"le"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Starts With",	"false","false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true","true",	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains","false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)","true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

	};

	// Ignore Case Tests
	public static final String[][][] GET_RESULTS_FIELD2 = {
		{ // 0 >> d		d			c			dc			dc1			e			c1
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain","false","true",	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains","true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)","false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 1 >> dc			d			c			dc			dc1			e			c1
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 2 >> dc123	d			c			dc			dc1			e			c1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Contains",	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 3 >> D			d			c			dc			dc1			e			c1
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 4 >> DC			d			c			dc			dc1			e			c1
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 5 >> DC123	d			c			dc			dc1			e			c1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Contains",	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

	};


	public static final String[][][] GET_RESULTS_FIELD3 = {
		{ // 0 >> d		D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Starts With",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 1 >> dc	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Starts With",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 2 >> dc123	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Starts With",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"false",	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 3 >> D		D			C			DC			DC1			E			C1
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 4 >> DC	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 5 >> DC123	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Contains","true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)","true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

	};

	// case insensitive
	public static final String[][][] GET_RESULTS_FIELD4 = {
		{ // 0 >> d		D			C			DC			DC1			E			C1
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 1 >> dc	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 2 >> dc123	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true",	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false","false","false",	"false",	"true"	,	"false"},
			{"Contains","true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)","true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 3 >> D		D			C			DC			DC1			E			C1
			{"="	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"false",	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"<="	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"false",	"false",	"false",	"false",	"false"},
			{"<> (Text)",	"false",	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

		{ // 4 >> DC	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"true"	,	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"false",	"false",	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"false",	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"true"	,	"true"	,	"false"},
			{"<="	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"le"	,	"false",	"false",	"true"	,	"true"	,	"true"	,	"false"},
			{"Starts With",	"true"	,	"false",	"true"	,	"false",	"false",	"false"},
			{"Doesn't Contain",	"false",	"false",	"false",	"true"	,	"true"	,	"true"	},
			{"Contains",	"true"	,	"true"	,	"true"	,	"false",	"false",	"false"},
			{"<> (Text)",	"true"	,	"true"	,	"false",	"true"	,	"true"	,	"true"	},
		},

		{ // 5 >> DC123	D			C			DC			DC1			E			C1
			{"="	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"eq"	,	"false",	"false",	"false",	"false",	"false",	"false"},
			{"!="	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"<>"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{"ne"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
			{">"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"gt"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{">="	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"ge"	,	"true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"lt"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"<="	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"le"	,	"false",	"false",	"false",	"false",	"true"	,	"false"},
			{"Starts With",	"true",	"false",	"true"	,	"true"	,	"false",	"false"},
			{"Doesn't Contain",	"false","false","false",	"false",	"true"	,	"false"},
			{"Contains","true"	,	"true"	,	"true"	,	"true"	,	"false",	"true"	},
			{"<> (Text)","true"	,	"true"	,	"true"	,	"true"	,	"true"	,	"true"	},
		},

	};

	private final String[] lines = {
			"f1\tf2\tf3",
			"d\t40\t50",
			"dc\t40\t50",
			"dc123\t40\t50",
			"D\t40\t50",
			"DC\t40\t50",
			"DC123\t40\t50",
	};
	private final String[] compare1 = { "d", "c", "dc", "dc1", "e", "c1"};
	private final String[] compare2 = { "D", "C", "DC", "DC1", "E", "C1"};

	public void testCharField1() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT, compare1, true);
		doTest(TstConstants.TAB_CSV_LAYOUT, compare1, true, GET_RESULTS_FIELD1);
	}

	public void testCharField2() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT, compare1, false);
		doTest(TstConstants.TAB_CSV_LAYOUT, compare1, false, GET_RESULTS_FIELD2);
	}

	public void testCharField3() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT, compare2, true);
		doTest(TstConstants.TAB_CSV_LAYOUT, compare2, true, GET_RESULTS_FIELD3);
	}

	public void testCharField4() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT, compare2, false);
		doTest(TstConstants.TAB_CSV_LAYOUT, compare2, false, GET_RESULTS_FIELD4);
	}


	private void doTest(String layoutStr, String[] compare, boolean caseSensitive, String[][][] results) throws Exception {
		ArrayList<AbstractLine> lines = getLines(layoutStr);

		for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
			AbstractLine l = getLines(layoutStr).get(lineNo);
			AbstractLayoutDetails layout = l.getLayout();

			IFieldDetail fd = layout.getRecord(0).getField(0);
			FieldSelect fs ;
			String s;


			for (int i = 0; i < results.length; i++) {

				s = results[lineNo][i][0];

				for (int j = 0; j < compare.length; j++) {
					fs = FieldSelectX.get("", compare[j], s, fd);
					fs.setCaseSensitive(caseSensitive);

					assertEquals("Compare: " + i + ", " + j + " " + compare[j] + " " + s,
							results[lineNo][i][j+1], "" + fs.isSelected(l));
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void printTest(String layoutStr, String[] compare, boolean caseSensitive) throws Exception {
		ArrayList<AbstractLine> lines = getLines(layoutStr);

		for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
			AbstractLine l = getLines(layoutStr).get(lineNo);
			AbstractLayoutDetails layout = l.getLayout();

			IFieldDetail fd = layout.getRecord(0).getField(0);
			FieldSelect fs ;
			String s;


			System.out.print("\t{ // " + lineNo + " >> " + l .getField(0, 0));
			for (int j = 0; j < compare.length; j++) {
				System.out.print("\t\t\t" + compare[j]);
			}
			System.out.println();
			for (int i = 0; i < TEXT_COMPARISON_OPERATORS.length; i++) {
	//		for (int i = 0; i < GET_RESULTS_TEXT_FIELD.length; i++) {

				s = TEXT_COMPARISON_OPERATORS[i];
				//s = GET_RESULTS_TEXT_FIELD[i][0];

				System.out.print("\t\t{\"" + s + "\"");
				if (s.length() < 6) {
					System.out.print("\t");
				}
				for (int j = 0; j < compare.length; j++) {
					fs = FieldSelectX.get("", compare[j], s, fd);
					fs.setCaseSensitive(caseSensitive);
					System.out.print(",\t\"" + fs.isSelected(l) + "\"");
					if (fs.isSelected(l)) {
						System.out.print("\t");
					}

					//assertEquals("Compare: " + i + ", " + j + " " + compare[j],
					//		GET_RESULTS_TEXT_FIELD[i][j+1], "" + fs.isSelected(l));
				}
				System.out.println("},");
			}
			System.out.println("\t},");
		}
	}

	private ArrayList<AbstractLine> getLines(String layoutTxt) throws Exception {
		return TstConstants.getLines(getLayout(layoutTxt), lines);
	}


	private LayoutDetail getLayout(String layout) throws RecordException, Exception {
		return getExternalLayout(layout).asLayoutDetail();
	}

	private ExternalRecord getExternalLayout(String layout) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(layout, "Csv Layout");
	}
}
