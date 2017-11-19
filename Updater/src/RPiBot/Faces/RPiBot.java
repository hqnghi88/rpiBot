package RPiBot.Faces;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RPiBot {

	public static void main(String[] args) {

		try {
			for(int i=0; i<1; i++) {				
				String line;
				Process p = Runtime.getRuntime()
						.exec("cmd /c \"cd D:\\GitHub\\rpiBot\\ && git status\"");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					System.out.println(line);
				}
				input.close();
				p = Runtime.getRuntime()
						.exec("cmd /c \"cd D:\\GitHub\\rpiBot\\Face\\ && mvn install\"");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					System.out.println(line);
				}
				input.close();
				 p = Runtime.getRuntime()
							.exec("java -jar D:\\GitHub\\rpiBot\\Face\\target\\Face-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
				Thread.sleep(1000);
				p.destroy();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
