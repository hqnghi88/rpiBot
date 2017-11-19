package RPiBot.Updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FaceUpdater implements Runnable {
	Process myP;
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			while(true) {
				String line;
				boolean mustRebuild=true;
				Process p = Runtime.getRuntime().exec("cmd /c \"cd D:\\GitHub\\rpiBot\\ && git pull\"");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					if(line.startsWith("Already up-to-date.")) {
						mustRebuild=false;
						break;
					}
					System.out.println(line);
				}
				input.close();
				if(mustRebuild) {					
					p = Runtime.getRuntime().exec("cmd /c \"cd D:\\GitHub\\rpiBot\\Face\\ && mvn install -q\"");
					input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}
					input.close();
					if(myP!=null) {
						myP.destroy();
					}
					myP = Runtime.getRuntime().exec(
							"java -jar D:\\GitHub\\rpiBot\\Face\\target\\Face-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
				}
				Thread.sleep(10000);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
