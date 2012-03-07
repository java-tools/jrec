package net.sf.JRecord.Details.Selection;

import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.ExternalRecordSelection.FieldSelection;
import net.sf.JRecord.ExternalRecordSelection.GroupSelection;

public class Convert {
	private int lvl = 0;
	public RecordSel convert(ExternalSelection sel, RecordDetail r) {
		lvl = 0;
		System.out.println(" ");
		System.out.println(" Starting  " + sel.getType());
		return convertI(sel, r);
	}
	private RecordSel convertI(ExternalSelection sel, RecordDetail r) {
		RecordSel ret=null;
		GroupSelection<ExternalSelection> g;
		
		for (int i = 0; i < lvl; i++) System.out.print(" ");
		lvl += 1;
		
		switch (sel.getType()) {
		case ExternalSelection.TYPE_ATOM: 
			FieldSelection f = (FieldSelection) sel;
			System.out.println(" Field " + f.getFieldName() 
					+" " + f.getOperator() + " " + f.getFieldValue());
			
			ret = FieldSelectX.get(f, r.getField(f.getFieldName()));
			break;
		case ExternalSelection.TYPE_AND: 
			g = (GroupSelection<ExternalSelection>) sel;
			System.out.println(" And");
			AndSelection and = new AndSelection(g);
			copy(g, and, r);
			ret = and;
			break;
		case ExternalSelection.TYPE_OR: 
			g = (GroupSelection<ExternalSelection>) sel;
			System.out.println(" Or");
			OrSelection or = new OrSelection(g);
			ret = copy(g, or, r);
			break;
		}
		lvl -= 1;
		
		return ret;
	}
	
	private RecordSel copy(GroupSelection<ExternalSelection> g, GroupSelection<RecordSel> to, RecordDetail r) {
		for (int i = 0; i < g.size(); i++) {
			to.add(convertI(g.get(i), r));
		}
		
		if (to.size() == 1) {
			return to.get(0);
		}
		return (RecordSel) to;
	}
}
