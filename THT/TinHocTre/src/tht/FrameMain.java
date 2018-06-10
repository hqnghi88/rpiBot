package tht;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FrameMain extends JFrame {
	public int numberTest = 0;
	public int N, K, R, C, rotate, X, rX, cX;

	CannyEdgeDetector detector = new CannyEdgeDetector();
	public int[][] matrix;
	ArrayList foundEdge = new ArrayList<>();
	public int[][] e, e2;
	public int[] visited;
	public int maxi=-9999;

	public int around(int u, int f, int fn) {
		int count = 0;
		int thres=2;
		if (f == 0) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e[fn][u - i] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e[fn][u + i] == -1) {
					count++;
				}
			}
		}
		if (f == 1) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e[u - i][fn] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e[u + i][fn] == -1) {
					count++;
				}
			}
		}
		if (f == 2) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e2[fn][u - i] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e2[fn][u + i] == -1) {
					count++;
				}
			}
		}
		if (f == 3) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e2[u - i][fn] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e2[u + i][fn] == -1) {
					count++;
				}
			}
		}
		return count;
	}
	public void differof() {
		int[] sum = new int[16];
		for (int i = 0; i < 16; i++)
			sum[i] = 0;
		int bound = 7;
		int rthres=0;
		for (int u = 0; u < K; u++) {
			// if(e[7][x]==-1 && e2[7][x]==-1) sum[0]++;

			if (around(u, 0, bound) > rthres && around(u, 2, bound) > rthres)
				sum[0]++;
			if (around(u, 0, bound) > rthres && around(u, 2, K - bound - 1) > rthres)
				sum[1]++;
			if (around(u, 0, bound) > rthres && around(u, 3, bound) > rthres)
				sum[2]++;
			if (around(u, 0, bound) > rthres && around(u, 3, K - bound - 1) > rthres)
				sum[3]++;

			if (around(u, 0, K - bound - 1) > rthres && around(u, 2, bound) > rthres)
				sum[4]++;
			if (around(u, 0, K - bound - 1) > rthres && around(u, 2, K - bound - 1) > rthres)
				sum[5]++;
			if (around(u, 0, K - bound - 1) > rthres && around(u, 3, bound) > rthres)
				sum[6]++;
			if (around(u, 0, K - bound - 1) > rthres && around(u, 3, K - bound - 1) > rthres)
				sum[7]++;

			if (around(u, 1, bound) > rthres && around(u, 2, bound) > rthres)
				sum[8]++;
			if (around(u, 1, bound) > rthres && around(u, 2, K - bound - 1) > rthres)
				sum[9]++;
			if (around(u, 1, bound) > rthres && around(u, 3, bound) > rthres)
				sum[10]++;
			if (around(u, 1, bound) > rthres && around(u, 3, K - bound - 1) > rthres)
				sum[11]++;

			if (around(u, 1, K - bound - 1) > rthres && around(u, 2, bound) > rthres)
				sum[12]++;
			if (around(u, 1, K - bound - 1) > rthres && around(u, 2, K - bound - 1) > rthres)
				sum[13]++;
			if (around(u, 1, K - bound - 1) > rthres && around(u, 3, bound) > rthres)
				sum[14]++;
			if (around(u, 1, K - bound - 1) > rthres && around(u, 3, K - bound - 1) > rthres)
				sum[15]++;

			// System.out.print(e[7][x]+"\t"+e2[7][x]+"\t\t\t");
			// System.out.print(e[7][x]+"\t"+e2[K-8][x]+"\t\t\t");
			// System.out.print(e[7][x]+"\t"+e2[x][7]+"\t\t\t");
			// System.out.print(e[7][x]+"\t"+e2[x][K-8]+"\t\t\t");
			//
			// System.out.print(e[K-8][x]+"\t"+e2[7][x]+"\t\t\t");
			// System.out.print(e[K-8][x]+"\t"+e2[K-8][x]+"\t\t\t");
			// System.out.print(e[K-8][x]+"\t"+e2[x][7]+"\t\t\t");
			// System.out.print(e[K-8][x]+"\t"+e2[x][K-8]+"\t\t\t");
			//
			// System.out.print(e[x][7]+"\t"+e2[7][x]+"\t\t\t");
			// System.out.print(e[x][7]+"\t"+e2[K-8][x]+"\t\t\t");
			// System.out.print(e[x][7]+"\t"+e2[x][7]+"\t\t\t");
			// System.out.print(e[x][7]+"\t"+e2[x][K-8]+"\t\t\t");
			//
			// System.out.print(e[x][K-8]+"\t"+e2[7][x]+"\t\t\t");
			// System.out.print(e[x][K-8]+"\t"+e2[K-8][x]+"\t\t\t");
			// System.out.print(e[x][K-8]+"\t"+e2[x][7]+"\t\t\t");
			// System.out.print(e[x][K-8]+"\t"+e2[x][K-8]+"\t\t\t");

			// System.out.println();
			// +e[K-8][x]+"\t"+e2[K-8][x]+"\t\t\t"+e[x][7]+"\t"+e2[x][7]+"\t\t\t"+e[x][K-8]+"\t"+e2[x][K-8]);
		}
		for (int i = 0; i < 16; i++) {
			if(sum[i]>maxi)
			System.out.print(sum[i]+" ");
		}
		System.out.println();
	}
	public FrameMain() {
		readInfo();
		visited=new int[N];
		for(int i=0; i<N; i++)visited[i]=0;
		maxi=-9999;
		matrix = new int[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				matrix[i][j] = -1;
			}
		}
		matrix[rX][cX] = 1;
		try {
			// checkAnImage(new File("D:\\ThiSinh\\input"+numberTest+"\\" + X + ".txt"));
			for (int i = 1; i <= N; i++) {
				checkAnImage(new File("D:\\ThiSinh\\input" + numberTest + "\\" + i + ".bmp"));
				System.out.println("Done " + i);
			}
			e = (int[][]) foundEdge.get(X - 1);
			for(int i=0; i<N;i++) {
				if(i==X-1) continue;
				e2 = (int[][]) foundEdge.get(i);
				System.out.println(i+1);
				differof();
			}
			// System.out.println();
			//
			// System.out.println();
			//
			// System.out.println();
			//
			// for (int x = 0; x < K; x++) {
			// System.out.println(e2[7][x]+"\t"+e2[K-8][x]+"\t"+e2[x][7]+"\t"+e2[x][K-8]);
			// }

			// for (int i = 0; i < e.length; i++) {
			// for (int j = 0; j < e[i].length; j++) {
			// System.out.print(e[i][j] + " ");
			// }
			// System.out.println();
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkAnImage(File f) throws Exception {

		// matrix = create2DIntMatrixFromFile(f.toPath());
		//
		// /////// create Image from this PixelArray
		// BufferedImage bufferImage2 = new BufferedImage(matrix.length,
		// matrix[0].length, BufferedImage.TYPE_INT_RGB);
		//
		// for (int y = 0; y < matrix.length; y++) {
		// for (int x = 0; x < matrix[0].length; x++) {
		// int Pixel = matrix[x][y] << 16 | matrix[x][y] << 8 | matrix[x][y];
		// bufferImage2.setRGB(y, x, Pixel);
		// }
		//
		// }
		BufferedImage bufferImage2 = ImageIO.read(f);

		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		detector.setSourceImage(bufferImage2);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		foundEdge.add(BItoA(edges));
		// File imageFile = new File("D:\\ThiSinh\\input\"+numberTest+\"\\" + X +
		// ".bmp");
		Image i = edges;// ImageIO.read(imageFile);
		ImageIcon image = new ImageIcon(i);
		JLabel imageLabel = new JLabel();
		this.add(imageLabel);
		this.setLayout(new GridLayout(3, 3));
		imageLabel.setLocation(0, 0);
		imageLabel.setSize(250, 250);
		ImageIcon imageIcon = new ImageIcon(image.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
		imageLabel.setIcon(imageIcon);
		imageLabel.setVisible(true);
	}

	public int[][] BItoA(BufferedImage edges) {
		int[][] arr = new int[edges.getWidth()][edges.getHeight()];

		for (int i = 0; i < edges.getWidth(); i++)
			for (int j = 0; j < edges.getHeight(); j++)
				arr[i][j] = edges.getRGB(i, j);

		return arr;
	}

	// public void printfromBI(BufferedImage edges) {
	// int[][] arr = new int[edges.getWidth()][edges.getHeight()];
	//
	// for(int i = 0; i < edges.getWidth(); i++)
	// for(int j = 0; j < edges.getHeight(); j++)
	// arr[i][j] = edges.getRGB(i, j);
	//
	// for (int i = 0; i < arr.length; i++) {
	// for (int j = 0; j < arr[i].length; j++) {
	// System.out.print(arr[i][j] + " ");
	// }
	// System.out.println();
	// }
	// }

	public void readInfo() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("D:\\ThiSinh\\input" + numberTest + "\\info.txt"));
			int[] t = new int[100];
			int i = 0;
			while (scanner.hasNextInt()) {
				t[i++] = scanner.nextInt();
			}
			// for (int ii = 0; ii < i; ii++)
			// System.out.println(t[ii]);
			N = t[0];
			K = t[1];
			R = t[2];
			C = t[3];
			rotate = t[4];
			X = t[5];
			rX = t[6];
			cX = t[7];
			System.out.println(N);
			System.out.println(K);
			System.out.println(R);
			System.out.println(C);
			System.out.println(rotate);
			System.out.println(X);
			System.out.println(rX);
			System.out.println(cX);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public int[][] create2DIntMatrixFromFile(Path path) throws IOException {
		return Files.lines(path).map((l) -> l.trim().split("\\s+"))
				.map((sa) -> Stream.of(sa).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
	}

	public static void main(String args[]) {
		FrameMain f = new FrameMain();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1480, 820);
		f.setVisible(true);
	}
}
