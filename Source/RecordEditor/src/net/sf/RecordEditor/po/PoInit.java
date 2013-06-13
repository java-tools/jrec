package net.sf.RecordEditor.po;

import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.po.display.PoDisplayBuilder;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.tip.display.TipDisplayBuilder;
import net.sf.RecordEditor.utils.common.Common;

/**
 * This class add's the The GetText-PO Classes to the RecordEditor.
 * it is called by the RecordEditor if present.
 *
 * @author Bruce Martin
 *
 */
public class PoInit {
	public PoInit() {
		DisplayBuilderFactory.register(new PoDisplayBuilder());
		DisplayBuilderFactory.register(new TipDisplayBuilder());
		LineIOProvider.getInstance().register(new PoLineIOProvider());

		Common.OPTIONS.getTextPoPresent.set(true);
	}
}
