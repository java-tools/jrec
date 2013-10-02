package net.sf.RecordEditor.utils.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class FieldWithBtns<Component extends JComponent> extends JPanel {

	private static final int FIELD_WIDTH = 20;

	protected final Component component;



	public FieldWithBtns(Component component) {
		super();
		this.component = component;

		component.setOpaque(true);
		component.setMinimumSize(new Dimension(FIELD_WIDTH, component.getHeight()));
	}





	protected final void initScreen(JButton... btns) {

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, component);

		if (btns.length > 1) {
			JPanel p = new JPanel();
			for (JButton btn : btns) {
				p.add(btn);
			}
			this.add(BorderLayout.EAST, p);
		} else {
			this.add(BorderLayout.EAST, btns[0]);
		}

		this.setBorder(component.getBorder());
		component.setBorder(BorderFactory.createEmptyBorder());
	}





	public final void setupBackground() {
		super.setBackground(Color.WHITE);
	}


	public final void setBackgroundOfField(Color bg) {
		component.setBackground(bg);
	}





}