package RPiBot.Faces;

import java.awt.Color;
import java.awt.Graphics;

public class Eye {
	// implements Runnable {
	String state = EmotionController.OPEN;
	String newstate = EmotionController.CLOSE;
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

			g.fillRect((int) ((l + 50) * scalex), (int) (100 * scaley), (int) (50 * scalex), (int) (300 * scaley));
			g.fillRect((int) ((l + 50) * scalex), (int) (100 * scaley), (int) (600 * scalex), (int) (50 * scaley));
			g.fillRect((int) ((l + 650) * scalex), (int) (100 * scaley), (int) (50 * scalex),
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
		if (threshold < 300) {
			g.fillRect((int) ((l + 150 + (dir * threshold)) * scalex), (int) ((200) * scaley), (int) (500 * scalex),
					(int) (300 * scaley));
			threshold += animation;
		} else {
			String t = state;
			state = newstate;
			newstate = t;
			threshold = 300;

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
			if (state.equals(EmotionController.CLOSE) && newstate.equals(EmotionController.OPEN)) {
				opening(g);
			} else if (state.equals(EmotionController.OPEN) && newstate.equals(EmotionController.CLOSE)) {
				closing(g);
			} else if (state.equals(EmotionController.OPEN) && newstate.equals(EmotionController.LAUGH)) {
				laughing(g);
			} else if (state.equals(EmotionController.LAUGH) && newstate.equals(EmotionController.OPEN)) {
				stoplaughing(g);
			} else if (state.equals(EmotionController.OPEN) && newstate.equals(EmotionController.GLANCELEFT)) {
				glance(g, -1);
			} else if (state.equals(EmotionController.GLANCELEFT) && newstate.equals(EmotionController.OPEN)) {
				stopglance(g, -1);
			} else if (state.equals(EmotionController.OPEN) && newstate.equals(EmotionController.GLANCERIGHT)) {
				glance(g, 1);
			} else if (state.equals(EmotionController.GLANCERIGHT) && newstate.equals(EmotionController.OPEN)) {
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

