package RPiBot;

import java.awt.Graphics;

import javax.swing.JPanel;

//create a component that you can actually draw on.
class DrawPane extends JPanel {

	Eye left;
	Eye right;
	public DrawPane(final Eye l, final Eye r,boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		left=l;
		right=r;
	}

	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 1920 , 1080);
		left.run(g);
		right.run(g);

	}
}