package tht;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FrameMainBFS extends JFrame {
	public int numberTest = 5;
	public int N, K, R, C, rotate, X, rX, cX;

	CannyEdgeDetector detector = new CannyEdgeDetector();
	public int[][] matrix;
	ArrayList foundEdge = new ArrayList<>();
	ArrayList foundEdgeImg = new ArrayList<>();
	// public int[][] e, e2;
	int[] rotarray = {0, 180, 90, 90, 270,
			// 0, 180, 270, 90,
			270, 270, 90, 180, 270, 270, 180, 0, 270, 90, 0, 180 };
	public int[] used;
	public int[][] res_rot;
	public int[][] res_marked;
	public int[][] rot;
	public int[][] marked;
	public int[][] visited;
	public int maxi = -9999;

	int foundpiece = 0;
	int totalPoint = 0;
	int tmpPoint = 0;
	int[] dx = { 1, -1, 0, 0 };
	int[] dy = { 0, 0, 1, -1 };


	public int[] differof2(int[][] e, int[][] e2, int direction) {
		int[] sum = new int[16];
		for (int i = 0; i < 16; i++)
			sum[i] = 0;
		int bound = 7;
		int rthres = 1;
		for (int u = 0; u < K; u++) {
			if (direction == 0) {
				if (Math.abs(e[bound][u] - e2[bound][K - u - 1]) <= rthres)
					sum[0]++;
				if (Math.abs(e[bound][u] - e2[K - bound - 1][u]) <= rthres)
					sum[1]++;
				if (Math.abs(e[bound][u] - e2[u][bound]) <= rthres)
					sum[2]++;
				if (Math.abs(e[bound][u] - e2[K - u - 1][K - bound - 1]) <= rthres)
					sum[3]++;
			}

			if (direction == 1) {
				if (Math.abs(e[K - bound - 1][u] - e2[bound][u]) <= rthres)
					sum[4]++;
				if (Math.abs(e[K - bound - 1][u] - e2[K - bound - 1][K - u - 1]) <= rthres)
					sum[5]++;
				if (Math.abs(e[K - bound - 1][u] - e2[K - u - 1][bound]) <= rthres)
					sum[6]++;
				if (Math.abs(e[K - bound - 1][u] - e2[u][K - bound - 1]) <= rthres)
					sum[7]++;
			}

			if (direction == 2) {
				if (Math.abs(e[u][bound] - e2[bound][u]) <= rthres)
					sum[8]++;
				if (Math.abs(e[u][bound] - e2[K - bound - 1][K - u - 1]) <= rthres)
					sum[9]++;
				if (Math.abs(e[u][bound] - e2[K - u - 1][bound]) <= rthres)
					sum[10]++;
				if (Math.abs(e[u][bound] - e2[u][K - bound - 1]) <= rthres)
					sum[11]++;
			}

			if (direction == 3) {
				if (Math.abs(e[u][K - bound - 1] - e2[bound][u]) <= rthres)
					sum[12]++;
				if (Math.abs(e[u][K - bound - 1] - e2[K - bound - 1][K - u - 1]) <= rthres)
					sum[13]++;
				if (Math.abs(e[u][K - bound - 1] - e2[K - u - 1][bound]) <= rthres)
					sum[14]++;
				if (Math.abs(e[u][K - bound - 1] - e2[u][K - bound - 1]) <= rthres)
					sum[15]++;
			}
		}
		int ff = -999;
		int r = 0;
		for (int i = 0; i < 16; i++) {
			if (sum[i] > ff) {
				ff = sum[i];
//				r = rr[i];
				 r=i;
			}
			// System.out.print(sum[i]+" ");
		}
		// System.out.println();
		return new int[] { ff, r };
	}
//
//	public int[] differof(int[][] e, int[][] e2, int direction) {
//		int[] sum = new int[16];
//		for (int i = 0; i < 16; i++)
//			sum[i] = 0;
//		int bound = 7;
//		int rthres = 0;
//		for (int u = 0; u < K; u++) {
//			if (direction == 0) {
//				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 2, bound, K - u - 1) > rthres)
//					sum[0]++;
//				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 2, K - bound - 1, u) > rthres)
//					sum[1]++;
//				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 3, bound, u) > rthres)
//					sum[2]++;
//				if (around(e, e2, 0, bound, u) > rthres && around(e, e2, 3, K - bound - 1, K - u - 1) > rthres)
//					sum[3]++;
//			}
//
//			if (direction == 1) {
//				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 2, bound, u) > rthres)
//					sum[4]++;
//				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 2, K - bound - 1, K - u - 1) > rthres)
//					sum[5]++;
//				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 3, bound, K - u - 1) > rthres)
//					sum[6]++;
//				if (around(e, e2, 0, K - bound - 1, u) > rthres && around(e, e2, 3, K - bound - 1, u) > rthres)
//					sum[7]++;
//			}
//
//			if (direction == 2) {
//				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 2, bound, u) > rthres)
//					sum[8]++;
//				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 2, K - bound - 1, K - u - 1) > rthres)
//					sum[9]++;
//				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 3, bound, K - u - 1) > rthres)
//					sum[10]++;
//				if (around(e, e2, 1, bound, u) > rthres && around(e, e2, 3, K - bound - 1, u) > rthres)
//					sum[11]++;
//			}
//
//			if (direction == 3) {
//				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 2, bound, K - u - 1) > rthres)
//					sum[12]++;
//				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 2, K - bound - 1, u) > rthres)
//					sum[13]++;
//				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 3, bound, u) > rthres)
//					sum[14]++;
//				if (around(e, e2, 1, K - bound - 1, u) > rthres && around(e, e2, 3, K - bound - 1, K - u - 1) > rthres)
//					sum[15]++;
//			}
//		}
//
//		// rthres = 20;
//		// for (int u = 0; u < K; u++) {
//		// if (direction == 0) {
//		// if (around2(e, e2, 0, bound, u) > rthres && around2(e, e2, 2, bound, K - u -
//		// 1) > rthres)
//		// sum[0]++;
//		// if (around2(e, e2, 0, bound, u) > rthres && around2(e, e2, 2, K - bound - 1,
//		// u) > rthres)
//		// sum[1]++;
//		// if (around2(e, e2, 0, bound, u) > rthres && around2(e, e2, 3, bound, u) >
//		// rthres)
//		// sum[2]++;
//		// if (around2(e, e2, 0, bound, u) > rthres && around2(e, e2, 3, K - bound - 1,
//		// K - u - 1) > rthres)
//		// sum[3]++;
//		// }
//		//
//		// if (direction == 1) {
//		// if (around2(e, e2, 0, K - bound - 1, u) > rthres && around2(e, e2, 2, bound,
//		// u) > rthres)
//		// sum[4]++;
//		// if (around2(e, e2, 0, K - bound - 1, u) > rthres && around2(e, e2, 2, K -
//		// bound - 1, K - u - 1) > rthres)
//		// sum[5]++;
//		// if (around2(e, e2, 0, K - bound - 1, u) > rthres && around2(e, e2, 3, bound,
//		// K - u - 1) > rthres)
//		// sum[6]++;
//		// if (around2(e, e2, 0, K - bound - 1, u) > rthres && around2(e, e2, 3, K -
//		// bound - 1, u) > rthres)
//		// sum[7]++;
//		// }
//		//
//		// if (direction == 2) {
//		// if (around2(e, e2, 1, bound, u) > rthres && around2(e, e2, 2, bound, u) >
//		// rthres)
//		// sum[8]++;
//		// if (around2(e, e2, 1, bound, u) > rthres && around2(e, e2, 2, K - bound - 1,
//		// K - u - 1) > rthres)
//		// sum[9]++;
//		// if (around2(e, e2, 1, bound, u) > rthres && around2(e, e2, 3, bound, K - u -
//		// 1) > rthres)
//		// sum[10]++;
//		// if (around2(e, e2, 1, bound, u) > rthres && around2(e, e2, 3, K - bound - 1,
//		// u) > rthres)
//		// sum[11]++;
//		// }
//		//
//		// if (direction == 3) {
//		// if (around2(e, e2, 1, K - bound - 1, u) > rthres && around2(e, e2, 2, bound,
//		// K - u - 1) > rthres)
//		// sum[12]++;
//		// if (around2(e, e2, 1, K - bound - 1, u) > rthres && around2(e, e2, 2, K -
//		// bound - 1, u) > rthres)
//		// sum[13]++;
//		// if (around2(e, e2, 1, K - bound - 1, u) > rthres && around2(e, e2, 3, bound,
//		// u) > rthres)
//		// sum[14]++;
//		// if (around2(e, e2, 1, K - bound - 1, u) > rthres && around2(e, e2, 3, K -
//		// bound - 1, K - u - 1) > rthres)
//		// sum[15]++;
//		// }
//		// }
//		int ff = -999;
//		int r = 0;
//		for (int i = 0; i < 16; i++) {
//			if (sum[i] > ff) {
//				ff = sum[i];
//				// r = rr[i];
//				r = i;
//			}
//			// System.out.print(sum[i]+" ");
//		}
//		// System.out.println();
//		return new int[] { ff, r };
//	}
//
//	
	public JPanel contentP;

	public FrameMainBFS() {
		contentP = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setContentPane(contentP);
		Container container = getContentPane();
		container.setLayout(new GridLayout(1, 1));
		JScrollPane scroll = new JScrollPane(contentP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.setLayout(new ScrollPaneLayout());

		container.add(scroll);
		// for(numberTest=0; numberTest<25; numberTest++) {
		init();
		createlabel();
		// }
		setPreferredSize(new Dimension(800, 600));
		setSize(800, 600);
		setVisible(true);
	}

	public void createlabel() {

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				// System.out.print(res_marked[i][j] + "*" + res_rot[i][j] + " ");
				// System.out.println();
				BufferedImage im = (BufferedImage) foundEdgeImg.get(res_marked[i][j] - 1);// ImageIO.read(imageFile);
				int w = im.getWidth();
				int h = im.getHeight();
				double angle = (Math.PI / 2) * (rotarray[res_rot[i][j]] / 90);
				AffineTransform tx = new AffineTransform();
				tx.rotate(angle, w / 2, h / 2);// (radian,arbit_X,arbit_Y)

				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				im = op.filter(im, null);// (sourse,destination)
				ImageIcon image = new ImageIcon(im);
				JLabel imageLabel = new JLabel();
				contentP.add(imageLabel);

				// this.getContentPane().setLayout(new GridLayout(3, 3));
				imageLabel.setLocation(0, 0);
				imageLabel.setSize(150, 150);
				ImageIcon imageIcon = new ImageIcon(image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
				imageLabel.setIcon(imageIcon);
				imageLabel.setVisible(true);
			}
		}
	}

	public void init() {

		readInfo();
		contentP.setLayout(new GridLayout(R, C));
		foundpiece = 0;
		totalPoint = 0;
		tmpPoint = 0;
		used = new int[N];
		for (int i = 0; i < N; i++)
			used[i] = 0;
		rot = new int[R][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				rot[i][j] = 0;
		visited = new int[R][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				visited[i][j] = 0;
		marked = new int[R][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				marked[i][j] = 0;
		res_marked = new int[R][C];
		res_rot = new int[R][C];
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
				System.out.println("Processed img " + i);
			}
			foundpiece = 1;
			visited[rX - 1][cX - 1] = X;
			marked[rX - 1][cX - 1] = X;
			used[X - 1] = 1;
			// bfs(X - 1, rX - 1, cX - 1);
			dfs(X - 1, rX - 1, cX - 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bfs(int theX, int xp, int yp) {
		LinkedList<Integer> queue = new LinkedList<Integer>();

		used[theX] = foundpiece;
		queue.add(theX);

		while (queue.size() != 0) {
			theX = queue.poll();
			System.out.print(theX + " ");

			for (int i = 0; i < N; i++) {
				if (used[i] == 0) {
					used[i] = foundpiece;
					queue.add(i);
				}
			}
		}
	}

	public void dfs(int theX, int xp, int yp) {

//		if (totalPoint > 0)
//			return;
		if (foundpiece >= R * C) {
			if (tmpPoint > totalPoint) {
				totalPoint = tmpPoint;
				for (int i = 0; i < R; i++) {
					for (int j = 0; j < C; j++) {
						res_marked[i][j] = marked[i][j];
						res_rot[i][j] = rot[i][j];
					}
				}
				for (int i = 0; i < R; i++) {
					for (int j = 0; j < C; j++)
						System.out.print(res_marked[i][j] + "*" + res_rot[i][j] + " ");
					System.out.println();
				}
				for(int i=0; i<N; i++) System.out.print(used[i]+" ");
				System.out.println(totalPoint);
				System.out.println();
			}
			return;
		}
		// int oldtmp = tmpPoint;
		// int oldfound = foundpiece;
		// System.out.println(theX);
		int[][] e = (int[][]) foundEdge.get(theX);
		 int oldtmpPoint=tmpPoint;
		int[] queue= {-1,-1,-1,-1};
		int[] maxtmp = {-99,-99,-99,-99};
		for (int i = 0; i < 4; i++) {
			if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
				if (marked[xp + dx[i]][yp + dy[i]] == 0) {
					// int mx = -1, xxx = -1, rr = 0;
					int jjj = 0, rott = 0;
					maxtmp[i]=-99;
					for (int j = 0; j < N; j++) {
						if (j == theX || used[j] > 0)
							continue;
						int[][] e2 = (int[][]) foundEdge.get(j);
						int[] tmp = differof2(e, e2, i);
						int tmppoint2 = 0;
						for (int ii = 0; ii < 4; ii++) {
							int xxp = xp + dx[i] + dx[ii];
							int yyp = yp + dy[i] + dy[ii];
							if (xxp >= 0 && xxp < R && yyp >= 0 && yyp < C) {
								if (marked[xxp][yyp] > 0 && marked[xxp][yyp] != theX + 1) {
									int[] tmp2 = differof2(e2, (int[][]) foundEdge.get(marked[xxp][yyp] - 1), ii);
									tmppoint2 += tmp2[0];
								}
							}
						}

						if (tmp[0] + tmppoint2 > maxtmp[i]) {
							maxtmp[i] = tmp[0] + tmppoint2;
							rott = tmp[1];
							jjj = j;
						}

						// if (tmp[0] >= mx) {
						// mx = tmp[0];
						// rr = tmp[1];
						// xxx = j;
						// }
					}

					foundpiece++;
					used[jjj] = foundpiece;
					rot[xp + dx[i]][yp + dy[i]] = rott;// tmp[1];
					marked[xp + dx[i]][yp + dy[i]] = jjj + 1;
					queue[i]=jjj; 
					tmpPoint += maxtmp[i];
				}
				// for (int ii = 0; ii < R; ii++) {
				// for (int jj = 0; jj < C; jj++)
				// System.out.print(marked[ii][jj] + " ");
				// System.out.println();
				// }
				// System.out.println();
			}
		}
		
		for(int i=0; i<4;i++) {
			if(queue[i]>0) {				
				// tmpPoint += tmppoint2;
				dfs(queue[i], xp + dx[i], yp + dy[i]);
				marked[xp + dx[i]][yp + dy[i]] = 0;
				used[queue[i]] = 0;
				foundpiece--;
				tmpPoint -= maxtmp[i];
				// tmpPoint -= tmppoint2;
			}
		}

		// for (int i = 0; i < 4; i++) {
		// if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
		// if (visited[xp + dx[i]][yp + dy[i]] == 0 && marked[xp + dx[i]][yp + dy[i]]
		// >0) {
		//// for (int j = 0; j < N; j++) {
		//// if (j == theX)
		//// continue;
		// visited[xp + dx[i]][yp + dy[i]] = 1;
		// dfs(marked[xp + dx[i]][yp + dy[i]]-1, xp + dx[i], yp + dy[i]);
		//// }
		// }
		// }
		// }
		// for (int i = 0; i < 4; i++) {
		// if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
		// if (visited[xp + dx[i]][yp + dy[i]] == 1) {
		// visited[xp + dx[i]][yp + dy[i]] = 0;
		//
		// marked[xp + dx[i]][yp + dy[i]] = 0;
		// used[marked[xp + dx[i]][yp + dy[i]] ] = 0;
		// foundpiece--;
		//
		// }
		// }
		// }

		// for (int i = 0; i < 4; i++) {
		// if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
		// if (visited[xp + dx[i]][yp + dy[i]] == 0) {
		// for (int j = 0; j < N; j++) {
		// if (used[j] > 0)
		// used[j] = 0;
		// }
		// }
		// }
		// }
		// tmpPoint = oldtmp;
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

//		 detector.setLowThreshold(0.5f);
//		 detector.setHighThreshold(1f);
//		
//		 detector.setSourceImage(bufferImage2);
//		 detector.process();
//		 BufferedImage edges = detector.getEdgesImage();
//		 BufferedImage b = new BufferedImage(edges.getWidth(), edges.getHeight(),
//		 edges.getType());
//		 Graphics g = b.getGraphics();
//		 g.drawImage(edges, 0, 0, null);
//		 g.dispose();
//		foundEdgeImg.add(b);
//		foundEdge.add(BItoA(b));
		foundEdgeImg.add(bufferImage2);
		foundEdge.add(BItoA(bufferImage2));
		// File imageFile = new File("D:\\ThiSinh\\input\"+numberTest+\"\\" + X
		// +
		// ".bmp");
		// Image i = edges;// ImageIO.read(imageFile);
		// ImageIcon image = new ImageIcon(i);
		// JLabel imageLabel = new JLabel();
		// contentP.add(imageLabel);
		//
		// // this.getContentPane().setLayout(new GridLayout(3, 3));
		// imageLabel.setLocation(0, 0);
		// imageLabel.setSize(150, 150);
		// ImageIcon imageIcon = new ImageIcon(image.getImage().getScaledInstance(150,
		// 150, Image.SCALE_SMOOTH));
		// imageLabel.setIcon(imageIcon);
		// imageLabel.setVisible(true);
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
		FrameMainBFS f = new FrameMainBFS();
	}
}
