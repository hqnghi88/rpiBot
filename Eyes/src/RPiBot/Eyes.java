package RPiBot;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;
public class Eyes extends JFrame implements Runnable {
	
	String emotion = EmotionController.NORMAL;
	public HashMap<String, String> STATEMAP = new HashMap<String, String>() {
		{
			put(EmotionController.NORMAL, EmotionController.OPEN);
			put(EmotionController.HAPPY, EmotionController.OPEN);
			put(EmotionController.GLANCELEFT, EmotionController.OPEN);
			put(EmotionController.GLANCERIGHT, EmotionController.OPEN);
		}
	};
	public HashMap<String, String> NEWSTATEMAP = new HashMap<String, String>() {
		{
			put(EmotionController.NORMAL, EmotionController.CLOSE);
			put(EmotionController.HAPPY, EmotionController.LAUGH);
			put(EmotionController.GLANCELEFT, EmotionController.GLANCELEFT);
			put(EmotionController.GLANCERIGHT, EmotionController.GLANCERIGHT);
		}
	};


	public DrawPane myPane;
	public Eye left;
	public Eye right;
	public EmotionController emotionController;

	public Eyes(final EmotionController ec) {

		super();
		emotionController=ec;
		this.setBackground(Color.BLACK);

		left = new Eye(150);

		right = new Eye(150 + 750);

		myPane = new DrawPane(left,right,true);
		setContentPane(myPane);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				System.exit(0);
			}
		});

	}

	int waitthread = 500;
	int sleep = 20;
	
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (left.ispaused && right.ispaused) {
				try {
					// if (left.state.equals("open")) {
					// Thread.sleep((int) (waitthread+ waitthread *
					// Math.random()));
					// }
					// if (left.state.equals("laugh")) {
					Thread.sleep((int) (waitthread * Math.random() + waitthread * Math.random()));
					// }

					// if (left.state.equals("close")) {
					// Thread.sleep((int) ( waitthread * Math.random()));
					// }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				left.ispaused = false;
				right.ispaused = false;
				// left.notify();
				// right.notify();
				if (left.state.equals(EmotionController.OPEN)) {
//					emotion = EmotionController.NORMAL;
//
//					if ((int) (Math.random() * 10) == 2) {
//						emotion = EmotionController.HAPPY;
//					}
//					if ((int) (Math.random() * 10) == 3) {
//						emotion = EmotionController.GLANCELEFT;
//					}
//					if ((int) (Math.random() * 10) == 4) {
//						emotion = EmotionController.GLANCERIGHT;
//					}
					
					emotion=emotionController.getEmotion();
					left.state = STATEMAP.get(emotion);
					right.state = STATEMAP.get(emotion);
					left.newstate = NEWSTATEMAP.get(emotion);
					right.newstate = NEWSTATEMAP.get(emotion);
				}

			}

			myPane.paintComponent(myPane.getGraphics());
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
