package net.sf.RecordEditor.tip.display;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FileView;

import org.jdesktop.swingx.tips.TipOfTheDayModel;

public class TipModel implements TipOfTheDayModel {

	@SuppressWarnings("rawtypes")
	private final FileView view;


	public TipModel(@SuppressWarnings("rawtypes") FileView view) {
		super();
		this.view = view;

	}

	@Override
	public int getTipCount() {
		return view.getRowCount();
	}

	@Override
	public Tip getTipAt(int index) {
		@SuppressWarnings("rawtypes")
		AbstractLine l = view.getLine(index);
		return new TipImp(l.getField(0, 0), l.getField(0, 1));
	}


	public static class TipImp implements Tip {
		private final String name, tip;


		public TipImp(Object name, Object tip) {
			super();
			this.name = fix(name);
			this.tip = fix(tip);
		}

		private String fix(Object o) {
			if (o == null) {
				return "";
			}
			return o.toString();
		}

		@Override
		public String getTipName() {
			return name;
		}

		@Override
		public Object getTip() {
			return tip;
		}
	}
}
