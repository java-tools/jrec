package net.sf.RecordEditor.utils.params;

public class BoolOpt {
	private String param;

	public BoolOpt(String value) {
		param = value;
	}

	public BoolOpt(String value, boolean def) {
		this(value);
		if (def) {
			Parameters.setDefaultTrue(value);
		}
	}

	public boolean isSelected() {
		boolean ret;

		if (Parameters.isDefaultTrue(param)) {
			ret = ! "N".equalsIgnoreCase(Parameters.getString(param));
		} else {
			ret = "Y".equalsIgnoreCase(Parameters.getString(param));
		}

		return ret;
	}
}
