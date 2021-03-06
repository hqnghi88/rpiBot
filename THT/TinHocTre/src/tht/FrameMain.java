package tht;

import java.awt.Container;
import java.awt.Dimension;
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
	public int numberTest = 1;
	public int N, K, R, C, rotate, X, rX, cX;

	CannyEdgeDetector detector = new CannyEdgeDetector();
	public int[][] matrix;
	ArrayList foundEdge = new ArrayList<>();
	// public int[][] e, e2;

	public int[] used;
	public int[][] visited;
	public int maxi = -9999;

	public int around(int[][] e, int[][] e2, int f, int fixed, int u) {
		int count = 0;
		int thres = 2;
		if (f == 0) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e[fixed][u - i] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e[fixed][u + i] == -1) {
					count++;
				}
			}
		}
		if (f == 1) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e[u - i][fixed] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e[u + i][fixed] == -1) {
					count++;
				}
			}
		}
		if (f == 2) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e2[fixed][u - i] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e2[fixed][u + i] == -1) {
					count++;
				}
			}
		}
		if (f == 3) {
			for (int i = 0; i < thres; i++) {
				if (u - i >= 0 && e2[u - i][fixed] == -1) {
					count++;
				}
			}
			for (int i = 1; i < thres; i++) {
				if (u + i < K && e2[u + i][fixed] == -1) {
					count++;
				}
			}
		}
		return count;
	}

	public int differof(int[][] e, int[][] e2, int direction) {
		int[] sum = new int[16];
		for (int i = 0; i < 16; i++)
			sum[i] = 0;
		int bound = 7;
		int rthres = 0;
		for (int u = 0; u < K; u++) {
			// if(e[7][x]==-1 && e2[7][x]==-1) sum[0]++;
			if (direction == 0) {
				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 2, bound, K - u) > rthres)
					sum[0]++;
				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 2, K - bound - 1, u) > rthres)
					sum[1]++;
				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 3, bound, u) > rthres)
					sum[2]++;
				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 3, K - bound - 1, K - u) > rthres)
					sum[3]++;
			}

			if (direction == 1) {
				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 2, bound, u) > rthres)
					sum[4]++;
				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 2, K - bound - 1, K - u) > rthres)
					sum[5]++;
				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 3, bound, K - u) > rthres)
					sum[6]++;
				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 3, K - bound - 1, u) > rthres)
					sum[7]++;
			}

			if (direction == 2) {
				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 2, bound, u) > rthres)
					sum[8]++;
				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 2, K - bound - 1, K - u) > rthres)
					sum[9]++;
				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 3, bound, K - u) > rthres)
					sum[10]++;
				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 3, K - bound - 1, u) > rthres)
					sum[11]++;
			}

			if (direction == 3) {
				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 2, bound, K - u) > rthres)
					sum[12]++;
				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 2, K - bound - 1, u) > rthres)
					sum[13]++;
				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 3, bound, u) > rthres)
					sum[14]++;
				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 3, K - bound - 1, K - u) > rthres)
					sum[15]++;
			}

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
		int ff = -999;
		for (int i = 0; i < 16; i++) {
			if (sum[i] > ff)
				ff = sum[i];
			// System.out.print(sum[i]+" ");
		}
		// System.out.println();
		return ff;
	}

	public JPanel contentP;

	public FrameMain() {
		contentP = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setContentPane(contentP);
		Container container = getContentPane();
		container.setLayout(new GridLayout(1, 1));
		JScrollPane scroll = new JScrollPane(contentP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.setLayout(new ScrollPaneLayout());

		// 6. Add the scroll pane to the contentpane of JApplet.

		container.add(scroll);
		init();
		setPreferredSize(new Dimension(800, 600)); // !! added
		setSize(800, 600);
		// setVisible(true);
	}

	public void init() {

		readInfo();
		contentP.setLayout(new GridLayout(R, C));
		used = new int[N];
		for (int i = 0; i < N; i++)
			used[i] = 0;
		visited = new int[R][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				visited[i][j] = 0;
		maxi = -9999;
		matrix = new int[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				matrix[i][j] = -1;
			}
		}
		matrix[rX - 1][cX - 1] = 1;
		try {
			// checkAnImage(new File("D:\\ThiSinh\\input"+numberTest+"\\" + X +
			// ".txt"));
			for (int i = 1; i <= N; i++) {
				checkAnImage(new File("D:\\ThiSinh\\input" + numberTest + "\\" + i + ".bmp"));
				System.out.println("Done " + i);
			}
			foundpiece = 1;
			visited[rX - 1][cX - 1] = X;
			used[X - 1] = 1;
			dfs(X - 1, rX - 1, cX - 1);
			// for(int i=0; i<N; i++)System.out.print (visited[i]+" ");

			// e = (int[][]) foundEdge.get(X - 1);
			// int chose=-1;
			// // for(int i=0; i<N;i++) {
			// int i=7;
			// // if(i==X-1) continue;
			// e2 = (int[][]) foundEdge.get(i);
			// System.out.println(i+1);
			// if(differof()) chose=i;
			// // }

			// if(chose>-1) visited[chose]=1;
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

	int foundpiece = 0;
	int totalPoint = 0;
	int tmpPoint = 0;
	int[] dx = { 1, -1, 0, 0 };
	int[] dy = { 0, 0, 1, -1 };

	public void dfs(int theX, int xp, int yp) {
		if (foundpiece >= R * C) {
			if (tmpPoint > totalPoint) {
				totalPoint = tmpPoint;
				for (int i = 0; i < R; i++) {
					for (int j = 0; j < C; j++)
						System.out.print(visited[i][j] + " ");
					System.out.println();
				}
				System.out.println(totalPoint);
			}
			return;
		}
		// int maxtmp=0;
		int[][] e = (int[][]) foundEdge.get(theX);
		// int chose=-1;

		for (int i = 0; i < 4; i++) {
			if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
				if (visited[xp + dx[i]][yp + dy[i]] == 0) {
					for (int j = 0; j < N; j++) {
						if (j == theX || used[j] > 0)
							continue;
						int[][] e2 = (int[][]) foundEdge.get(j);
						int tmp = differof(e, e2, i);
						foundpiece++;
						used[j] = foundpiece;
						visited[xp + dx[i]][yp + dy[i]] = j + 1;
						tmpPoint += tmp;
						dfs(j, xp + dx[i], yp + dy[i]);
						tmpPoint -= tmp;
						foundpiece--;
						used[j] = 0;
						visited[xp + dx[i]][yp + dy[i]] = 0;

					}
				}
			}
		}

		// for (int i = 0; i < N; i++) {
		// if (i == theX || visit[i] > 0)
		// continue;
		// e2 = (int[][]) foundEdge.get(i);
		// // System.out.println(i+1);
		// int tmp = differof();
		// foundpiece++;
		// visited[i] = foundpiece;
		//// if (totalPoint < tmpPoint + tmp) {
		// tmpPoint += tmp;
		//// dfs(i);
		// tmpPoint -= tmp;
		// foundpiece--;
		// visited[i] = 0;
		//// }
		// // if(tmp>0) {chose=i;maxtmp=tmp;}
		// }
		// if(chose>-1) {
		// foundpiece++;
		// visited[chose]=foundpiece;
		// if(foundpiece<R*C && totalPoint<tmpPoint+maxtmp){
		// tmpPoint+=maxtmp;
		// dfs(chose);
		// }
		// }
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
		// File imageFile = new File("D:\\ThiSinh\\input\"+numberTest+\"\\" + X
		// +
		// ".bmp");
		Image i = edges;// ImageIO.read(imageFile);
		ImageIcon image = new ImageIcon(i);
		JLabel imageLabel = new JLabel();
		contentP.add(imageLabel);

		// this.getContentPane().setLayout(new GridLayout(3, 3));
		imageLabel.setLocation(0, 0);
		imageLabel.setSize(150, 150);
		ImageIcon imageIcon = new ImageIcon(image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
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
	}
}
