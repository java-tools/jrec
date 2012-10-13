package net.sf.RecordEditor.edit.display.extension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

public class PaneDtls {
	public final String name;
	public final JComponent fld;
	public final JTextComponent txtFld;
	public final FieldDef fieldDef;
	public final int col;
	public final boolean isHtml;
	public final double weight;

	private boolean visible = true;

	public PaneDtls(String name, FieldDef fieldDef, JComponent fld) {
		this(name, fieldDef, fld, 0, false, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JComponent fld, int col, boolean html, double weight) {
		super();
		this.name = name;
		this.fld = fld;
		this.txtFld = null;
		this.fieldDef = fieldDef;
		this.col = col;
		this.weight = weight;
		this.isHtml = html;
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld) {
		this(name, fieldDef, fld, 0, false, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, double weight) {
		this(name, fieldDef, fld, 0, false, weight);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col) {
		this(name, fieldDef, fld, col, false, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col, boolean html) {
		this(name, fieldDef, fld, col, html, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col, boolean html, double weight) {
		super();
		this.name = name;
		this.fld = fld;
		this.txtFld = fld;
		this.fieldDef = fieldDef;
		this.col = col;
		this.isHtml = html;
		this.weight = weight;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public PaneDtls setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public final JComponent getDisplayPane() {
		JScrollPane ret = new JScrollPane(fld);

		ret.setBorder(BorderFactory.createTitledBorder(name));

		return ret;
	}
}
