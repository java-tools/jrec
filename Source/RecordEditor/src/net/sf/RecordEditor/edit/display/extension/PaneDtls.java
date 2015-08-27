package net.sf.RecordEditor.edit.display.extension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

public class PaneDtls {
	public static int NOT_HTML = 1;
	public static int HTML_IF_HTML_TAG = 2;
	public static int IS_HTML = 3;
	
	public final String name;
	public final JComponent fld;
	public final JTextComponent txtFld;
	public final FieldDef fieldDef;
	public final int col;
//	@Deprecated
//	public final boolean isHtml;
	public final int htmlType;
	public final double weight;

	private boolean visible = true;

	public PaneDtls(String name, FieldDef fieldDef, JComponent fld) {
		this(name, fieldDef, fld, 0, NOT_HTML, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JComponent fld, int col, int html, double weight) {
		super();
		this.name = name;
		this.fld = fld;
		this.txtFld = null;
		this.fieldDef = fieldDef;
		this.col = col;
		this.weight = weight;
		this.htmlType = html;
//		this.isHtml = htmlType != NOT_HTML;
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld) {
		this(name, fieldDef, fld, 0, NOT_HTML, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, double weight) {
		this(name, fieldDef, fld, 0, NOT_HTML, weight);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col) {
		this(name, fieldDef, fld, col, NOT_HTML, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col, boolean html) {
		this(name, fieldDef, fld, col, html? IS_HTML : NOT_HTML, -1);
	}

	public PaneDtls(String name, FieldDef fieldDef, JTextComponent fld, int col, int html, double weight) {
		super();
		this.name = name;
		this.fld = fld;
		this.txtFld = fld;
		this.fieldDef = fieldDef;
		this.col = col;
//		this.isHtml = html;
		this.weight = weight;
		this.htmlType = html;
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
	
	public boolean isHtml() {
		return htmlType != NOT_HTML;
	}

	public final JComponent getDisplayPane() {
		JScrollPane ret = new JScrollPane(fld);

		ret.setBorder(BorderFactory.createTitledBorder(name));

		return ret;
	}
}
