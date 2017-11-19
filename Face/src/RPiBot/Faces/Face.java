package RPiBot.Faces;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Face {
	
//	public Face(){
	public static void main(String[] args) {
		EmotionController ec=new EmotionController();
		Eyes eyes = new Eyes(ec);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		eyes.setSize(screenSize);

		eyes.setSize(420, 240);
		eyes.left.scalex = eyes.getWidth() / 1920.0;
		eyes.left.scaley = eyes.getHeight() / 1080.0;

		eyes.right.scalex = eyes.getWidth() / 1920.0;
		eyes.right.scaley = eyes.getHeight() / 1080.0;
//		 eyes.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		 eyes.setUndecorated(true);
		eyes.setVisible(true);
		// eyes.left.g=eyes.myPane.getGraphics().create();
		// eyes.right.g=eyes.myPane.getGraphics().create();

		new Thread(eyes).start();
		// new Thread(eyes.left).start();
		// new Thread(eyes.right).start();
	}

}
