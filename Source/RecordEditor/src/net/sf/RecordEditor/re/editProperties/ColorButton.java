package net.sf.RecordEditor.re.editProperties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;



@SuppressWarnings("serial")
public class ColorButton extends JButton implements ActionListener{

	private Color color;
	private final String title;
	
	public ColorButton(Color color, String title) {
		this.color = color;
		this.title = title;
		this.setIcon(new ColorBox());
		setMargin(new Insets(2,2,2,2));
		addActionListener(this);
	}
	
	
	/**
	 * @return the color
	 */
	public final Color getColor() {
		return color;
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Color newColor = JColorChooser.showDialog(
                this,
                title,
                color);
		
		if (newColor != null) {
			color = newColor;
			this.revalidate();
		}
	}


	private class ColorBox implements Icon {

		
		/* (non-Javadoc)
		 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			if(color == null) return;

			g.setColor(color);
			g.fillRect(x,y,getIconWidth(),getIconHeight());
//			g.setColor(color.darker());
//			g.drawRect(x,y,getIconWidth()-1,getIconHeight()-1);
		}

		/* (non-Javadoc)
		 * @see javax.swing.Icon#getIconWidth()
		 */
		@Override
		public int getIconWidth() {
			return 37;
		}

		/* (non-Javadoc)
		 * @see javax.swing.Icon#getIconHeight()
		 */
		@Override
		public int getIconHeight() {
			return 9;
		}
		
	}
}
