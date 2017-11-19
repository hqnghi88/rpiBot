package RPiBot;

public class EmotionController {

	public static String NORMAL = "normal";
	public static String OPEN = "open";
	public static String CLOSE = "close";
	public static String LAUGH = "laugh";
	public static String HAPPY = "happy";
	public static String GLANCELEFT = "GLANCELEFT";
	public static String GLANCERIGHT = "GLANCERIGHT";
	
	public String getEmotion() {
		String emotion = EmotionController.NORMAL;

		if ((int) (Math.random() * 10) == 2) {
			emotion = EmotionController.HAPPY;
		}
		if ((int) (Math.random() * 10) == 3) {
			emotion = EmotionController.GLANCELEFT;
		}
		if ((int) (Math.random() * 10) == 4) {
			emotion = EmotionController.GLANCERIGHT;
		}
		return emotion;
	}
}
