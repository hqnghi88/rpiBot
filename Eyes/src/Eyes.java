
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Eyes extends JFrame implements Runnable {
	public static String NORMAL = "normal";
	public static String OPEN = "open";
	public static String CLOSE = "close";
	public static String LAUGH = "laugh";
	public static String HAPPY = "happy";
	public static String GLANCELEFT = "GLANCELEFT";
	public static String GLANCERIGHT = "GLANCERIGHT";
	String emotion = NORMAL;
	public HashMap<String, String> STATEMAP = new HashMap<String, String>() {
		{
			put(NORMAL, OPEN);
			put(HAPPY, OPEN);
			put(GLANCELEFT, OPEN);
			put(GLANCERIGHT, OPEN);
		}
	};
	public HashMap<String, String> NEWSTATEMAP = new HashMap<String, String>() {
		{
			put(NORMAL, CLOSE);
			put(HAPPY, LAUGH);
			put(GLANCELEFT, GLANCELEFT);
			put(GLANCERIGHT, GLANCERIGHT);
		}
	};

	public class Eye {
		// implements Runnable {
		String state = OPEN;
		String newstate = CLOSE;
		double scalex = 1.0;
		double scaley = 1.0;
		int center = 0;

		public Eye(int c) {
			center = c;
		}

		int threshold = 0;
		int animation = 150;
		public boolean ispaused = false;

		public void opening(Graphics g) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold < 300) {
				g.fillRect((int) ((l + 150) * scalex), (int) ((500 - threshold) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley));
				threshold += animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 300;
				// System.out.println("closed");
				g.fillRect((int) ((l + 150) * scalex), (int) ((500 - threshold) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley));
				ispaused = true;

				// try {
				// Thread.sleep((int)(1000+10*Math.random()));
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		public void closing(Graphics g) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold > 100) {
				g.fillRect((int) ((l + 150) * scalex), (int) ((500 - threshold) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley - 20));
				threshold -= animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 100;

				g.fillRect((int) ((l + 150) * scalex), (int) ((500 - threshold) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley - 20));
				ispaused = true;
				// System.out.println("opened");

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		public void laughing(Graphics g) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold > 100) {
				g.fillRect((int) ((l + 150) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley));
				threshold -= animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 100;

				g.fillRect((int) ((l + 20) * scalex), (int) (100 * scaley), (int) (100 * scalex), (int) (300 * scaley));
				g.fillRect((int) ((l + 50) * scalex), (int) (100 * scaley), (int) (600 * scalex), (int) (100 * scaley));
				g.fillRect((int) ((l + 650) * scalex), (int) (100 * scaley), (int) (100 * scalex),
						(int) (300 * scaley));

				ispaused = true;
				// System.out.println("opened");

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		public void stoplaughing(Graphics g) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold < 300) {
				g.fillRect((int) ((l + 150) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley));
				threshold += animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 300;

				g.fillRect((int) ((l + 150) * scalex), (int) ((500 - threshold) * scaley), (int) (500 * scalex),
						(int) ((0 + (threshold)) * scaley));

				ispaused = true;
				// System.out.println("opened");

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		public void glance(Graphics g, int dir) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold < 200) {
				g.fillRect((int) ((l + 150 + (dir * threshold)) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) (300 * scaley));
				threshold += animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 200;

				g.fillRect((int) ((l + 150 + (dir * threshold)) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) (300 * scaley));

				ispaused = true;
				// System.out.println("opened");

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		public void stopglance(Graphics g, int dir) {

			// g.clearRect((int) (center * scalex), (int) (100 * scaley), (int)
			// (800 * scalex), (int) (800 * scaley));
			g.setColor(Color.WHITE);
			int l = center;
			if (threshold > 0) {
				g.fillRect((int) ((l + 150 + (dir * threshold)) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) (300 * scaley));
				threshold -= animation;
			} else {
				String t = state;
				state = newstate;
				newstate = t;
				threshold = 0;

				g.fillRect((int) ((l + 150 + (dir * threshold)) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
						(int) (300 * scaley));

				ispaused = true;
				// System.out.println("opened");

			}
			//
			// int x = l + 800;
			// g.fillRect((int) ((x + 150) * scalex), (int) (300 * scaley),
			// (int)
			// (500 * scalex), (int) (400 * scaley));
		}

		// public Graphics g;
		// @Override
		// public void run() {
		public void run(Graphics g) {
			// while(true) {

			// TODO Auto-generated method stub
			if (!state.equals(newstate)) {
				if (state.equals(CLOSE) && newstate.equals(OPEN)) {
					opening(g);
				} else if (state.equals(OPEN) && newstate.equals(CLOSE)) {
					closing(g);
				} else if (state.equals(OPEN) && newstate.equals(LAUGH)) {
					laughing(g);
				} else if (state.equals(LAUGH) && newstate.equals(OPEN)) {
					stoplaughing(g);
				} else if (state.equals(OPEN) && newstate.equals(GLANCELEFT)) {
					glance(g, -1);
				} else if (state.equals(GLANCELEFT) && newstate.equals(OPEN)) {
					stopglance(g, -1);
				} else if (state.equals(OPEN) && newstate.equals(GLANCERIGHT)) {
					glance(g, 1);
				} else if (state.equals(GLANCERIGHT) && newstate.equals(OPEN)) {
					stopglance(g, 1);
				}
			}

			// try {
			// Thread.sleep(10);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
		}

	}

	// create a component that you can actually draw on.
	class DrawPane extends JPanel {
		public DrawPane(boolean isDoubleBuffered) {
			super(isDoubleBuffered);
		}

		public void paintComponent(Graphics g) {
			g.clearRect(0, 0, 1920 , 1080);
			left.run(g);
			right.run(g);

		}
	}

	public DrawPane myPane;
	Eye left;
	Eye right;


	public Eyes() {

		super();
		this.setBackground(Color.BLACK);

		left = new Eye(150);

		right = new Eye(150 + 750);

		myPane = new DrawPane(true);
		setContentPane(myPane);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				System.exit(0);
			}
		});

	}

	public static void main(String[] args) {
		Eyes eyes = new Eyes();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		eyes.setSize(screenSize);

//		eyes.setSize(320, 240);
		eyes.left.scalex = eyes.getWidth() / 1920.0;
		eyes.left.scaley = eyes.getHeight() / 1080.0;

		eyes.right.scalex = eyes.getWidth() / 1920.0;
		eyes.right.scaley = eyes.getHeight() / 1080.0;
		 eyes.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 eyes.setUndecorated(true);
		eyes.setVisible(true);
		// eyes.left.g=eyes.myPane.getGraphics().create();
		// eyes.right.g=eyes.myPane.getGraphics().create();

		new Thread(eyes).start();
		// new Thread(eyes.left).start();
		// new Thread(eyes.right).start();
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
				if (left.state.equals(OPEN)) {
					emotion = NORMAL;

					if ((int) (Math.random() * 10) == 2) {
						emotion = HAPPY;
					}
					if ((int) (Math.random() * 10) == 3) {
						emotion = GLANCELEFT;
					}
					if ((int) (Math.random() * 10) == 4) {
						emotion = GLANCERIGHT;
					}
					
					left.state = STATEMAP.get(emotion);
					right.state = STATEMAP.get(emotion);
					left.newstate = NEWSTATEMAP.get(emotion);
					right.newstate = NEWSTATEMAP.get(emotion);
				}

			}
			// if (!state.equals("happy") && ((int)(Math.random()*10) == 1 )) {
			// state="happy";
			// sleep=3000;
			// } else {
			// sleep=500;
			// if(state.equals("open")) {
			// state="close";
			// }else {
			// state="open";
			// }
			// }
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
