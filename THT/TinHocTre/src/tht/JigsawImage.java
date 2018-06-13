package tht;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JigsawImage {

	public static void main(String[] args) {
	try {
			
		//Provide number of rows and column
		int row = 5;
		int col = 5;
		
		BufferedImage originalImgage = ImageIO.read(new File("D:/ThiSinh/input26/ori.jpg"));
			
		//total width and total height of an image
		int tWidth = originalImgage.getWidth();
		int tHeight = originalImgage.getHeight();

		System.out.println("Image Dimension: " + tWidth + "x" + tHeight);
		
		//width and height of each piece
		int eWidth = tWidth / col;
		int eHeight = tHeight / row;

		int x = 0;
		int y = 0;

		for (int i = 0; i < row; i++) {
			y = 0;
			for (int j = 0; j < col; j++) {
				try {
					System.out.println("creating piece: "+i+" "+j);
					
					BufferedImage SubImgage = originalImgage.getSubimage(y, x, eWidth, eHeight);
					File outputfile = new File("D:/ThiSinh/input26/"+(i*row+j+1)+".bmp");
					ImageIO.write(SubImgage, "jpg", outputfile);
					
					y += eWidth;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			x += eHeight;
		}

	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
}