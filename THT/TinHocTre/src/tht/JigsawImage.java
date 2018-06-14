package tht;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JigsawImageGenerator {

	public static int printPixelARGB(int pixel) {
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		// System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " +
		// blue);
		return (red + green + blue) / 3;
	}

	public static int[][] BItoA(BufferedImage edges) {
		int[][] arr = new int[edges.getWidth()][edges.getHeight()];

		for (int i = 0; i < edges.getWidth(); i++)
			for (int j = 0; j < edges.getHeight(); j++)
				arr[i][j] = printPixelARGB(edges.getRGB(i, j));

		return arr;
	}
	
	public static void main(String[] args) {
		try {

			// Provide number of rows and column
			int row = 5;
			int col = 5;

			BufferedImage originalImgage = ImageIO.read(new File(
					"D:/ThiSinh/input27/ori.jpg"));

			// total width and total height of an image
			int tWidth = originalImgage.getWidth();
			int tHeight = originalImgage.getHeight();

			System.out.println("Image Dimension: " + tWidth + "x" + tHeight);

			// width and height of each piece
			int eWidth = tWidth / col;
			int eHeight = tHeight / row;

			int x = 0;
			int y = 0;
			try {
				File file = new File("D:/ThiSinh/input27/info.txt");
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(row * col + " ");
				fileWriter.write(eWidth + " ");
				fileWriter.write(row + " ");
				fileWriter.write(col + "\n");
				fileWriter.write("0\n");
				int xidx=(int) (Math.random()*10);
				System.out.println(xidx);
				for (int i = 0; i < row; i++) {
					y = 0;
					for (int j = 0; j < col; j++) {
						if(i * row + j ==xidx){
							fileWriter.write(xidx+1+" "+(i+1) + " "+(j+1));

						}
						System.out.println("creating piece: " + i + " " + j);

						BufferedImage SubImgage = originalImgage.getSubimage(y,
								x, eWidth, eHeight);
						File outputfile = new File("D:/ThiSinh/input27/"
								+ (i * row + j + 1) + ".bmp");
						ImageIO.write(SubImgage, "bmp", outputfile);
						
						int[][] gray=BItoA(SubImgage);

						File file2 = new File("D:/ThiSinh/input27/"+(i * row + j + 1) +".txt");
						FileWriter fileWriter2 = new FileWriter(file2);
						for(int u=0;u<eWidth;u++){
							for(int v=0; v<eWidth; v++){
//								System.out.print(gray[u][v]+" ");
								fileWriter2.write(gray[u][v]+" ");
							}
//							System.out.println();
							fileWriter2.write("\n");
						}

						fileWriter2.flush();
						fileWriter2.close();
						
						y += eWidth;

					}

					x += eHeight;
				}

				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
