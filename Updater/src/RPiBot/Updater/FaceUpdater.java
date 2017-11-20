package RPiBot.Updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FaceUpdater implements Runnable {
	Process myP;

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();

		if (myP != null) {
			myP.destroy();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			if (myP == null) {
				myP = Runtime.getRuntime().exec(
						"java -jar D:\\GitHub\\rpiBot\\Face\\target\\Face-1.0.0-SNAPSHOT-jar-with-dependencies.jar");
			}
			while (true) {
				String line;
				boolean mustRebuild = true;
				Process p = Runtime.getRuntime().exec("cmd /c \"cd D:\\GitHub\\rpiBot\\ && git pull\"");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					System.out.println(line);
					if (line.startsWith("Already up-to-date.")) {
						mustRebuild = false;
						break;
					}
				}
				input.close();
				if (mustRebuild) {
					if (myP != null) {
						myP.destroy();
					}
					p = Runtime.getRuntime().exec("cmd /c \"cd D:\\GitHub\\rpiBot\\Face\\ && mvn install -q\"");
					input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}
					input.close();
					myP = Runtime.getRuntime().exec(
							"java -jar D:\\GitHub\\rpiBot\\Face\\target\\Face-1.0.0-SNAPSHOT-jar-with-dependencies.jar");
				}
				Thread.sleep(1000);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
