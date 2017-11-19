package RPiBot.Updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RPiBot {

	public static void main(String[] args) {

		new Thread(new FaceUpdater()).start();
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