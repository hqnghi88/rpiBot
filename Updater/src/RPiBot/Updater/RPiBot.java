package RPiBot.Updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RPiBot {

	public static void main(String[] args) {

		try {
			for(int i=0; i<1; i++) {
				String msg="";
				String line;
				Process p = Runtime.getRuntime()
						.exec("cmd /c \"cd D:\\GitHub\\rpiBot\\ && git status\"");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					msg+=line;
				}
				System.out.println(msg);
				input.close();
				msg="";
				p = Runtime.getRuntime()
						.exec("cmd /c \"cd D:\\GitHub\\rpiBot\\Face\\ && mvn install\"");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					msg=line;
				}
				System.out.println(msg);
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
/*
Your branch is up-to-date with 'origin/master'.
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   pom.xml
	modified:   ../Updater/src/RPiBot/Faces/RPiBot.java

no changes added to commit (use "git add" and/or "git commit -a")
*/