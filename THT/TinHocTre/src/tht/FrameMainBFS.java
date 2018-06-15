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
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class FrameMainBFS extends JFrame {
	public int numberTest = 0;
	public int N, K, R, C, rotate, X, rX, cX;

	CannyEdgeDetector detector = new CannyEdgeDetector();
	public int[][] matrix;
	ArrayList foundEdgeO = new ArrayList<>();
	ArrayList<BufferedImage> foundEdgeImgO = new ArrayList<>();
	ArrayList foundEdge = new ArrayList<>();
	ArrayList<BufferedImage> foundEdgeImg = new ArrayList<>();
	// public int[][] e, e2;
	int[] rotarray = { 0, 270, 90, 180, 0, 0, 90, 180, 0, 270, 90, 180, 0, 180, 180, 0
			// 0, 270, 90, 180, 0, 270, 90, 180, 0, 270, 90, 180, 180, 180, 180, 0
	};
	public int[] used;
	public int[][] res_rot;
	public int[][] res_marked;
	public int[][] rot;
	public int[][] marked;
	public int[][] visited;
	public int maxi = -9999;

	int foundpiece = 0;
	int totalPoint = 99999999;
	int tmpPoint = 0;
	int[] dy = { 1, -1, 0, 0 };
	int[] dx = { 0, 0, 1, -1 };

	public JPanel contentP;

	public FrameMainBFS() {
		contentP = new JPanel();
		// setContentPane(contentP);
		Container container = getContentPane();
		container.setLayout(new GridLayout(1, 1));
		JScrollPane scroll = new JScrollPane(contentP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.setLayout(new ScrollPaneLayout());

		container.add(scroll);
	}

	public void process() {

		// for(numberTest=0; numberTest<25; numberTest++) {
		init();
		createlabel();
		// }
		setPreferredSize(new Dimension(800, 600));
		setSize(1000, 900);
		setVisible(true);
	}

	public void createlabel() {
		int scale = 100;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				// System.out.print(res_marked[i][j] + "*" + res_rot[i][j] +
				// " ");
				// System.out.println();
				BufferedImage im = (BufferedImage) foundEdgeImgO.get(res_marked[i][j] - 1);// ImageIO.read(imageFile);
				int w = im.getWidth();
				int h = im.getHeight();
				double angle = (Math.PI / 2) * (rotarray[res_rot[i][j]] / 90);
				if (res_marked[i][j] == X) {
					angle = 0;
				}
				AffineTransform tx = new AffineTransform();
				tx.rotate(angle, w / 2, h / 2);// (radian,arbit_X,arbit_Y)

				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				im = op.filter(im, null);// (sourse,destination)
				ImageIcon image = new ImageIcon(im);
				JLabel imageLabel = new JLabel();
				contentP.add(imageLabel);

				// this.getContentPane().setLayout(new GridLayout(3, 3));
				imageLabel.setLocation(0, 0);
				imageLabel.setSize(scale, scale);
				ImageIcon imageIcon = new ImageIcon(
						image.getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
				imageLabel.setIcon(imageIcon);
				imageLabel.setVisible(true);
				imageLabel.setToolTipText("" + used[res_marked[i][j] - 1]);

			}
		}
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				// System.out.print(res_marked[i][j] + "*" + res_rot[i][j] +
				// " ");
				// System.out.println();
				BufferedImage im = (BufferedImage) foundEdgeImg.get(res_marked[i][j] - 1);// ImageIO.read(imageFile);
				int w = im.getWidth();
				int h = im.getHeight();
				double angle = (Math.PI / 2) * (rotarray[res_rot[i][j]] / 90);
				if (res_marked[i][j] == X) {
					angle = 0;
				}
				AffineTransform tx = new AffineTransform();
				tx.rotate(angle, w / 2, h / 2);// (radian,arbit_X,arbit_Y)

				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				im = op.filter(im, null);// (sourse,destination)
				ImageIcon image = new ImageIcon(im);
				JLabel imageLabel = new JLabel();
				contentP.add(imageLabel);

				// this.getContentPane().setLayout(new GridLayout(3, 3));
				imageLabel.setLocation(0, 0);
				imageLabel.setSize(scale, scale);
				ImageIcon imageIcon = new ImageIcon(
						image.getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
				imageLabel.setIcon(imageIcon);
				imageLabel.setVisible(true);
			}
		}
	}

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 =  rgb1        & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 =  rgb2        & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
	public int similarity(BufferedImage im1, BufferedImage im2) {
		int res = 0;
		for (int i = 0; i < im1.getWidth(); i++) {
			for (int j = 0; j < im1.getHeight(); j++) {
//                res += pixelDiff(im1.getRGB(i, j), im2.getRGB(i, j));
				if (printPixelARGB(im1.getRGB(i, j)) - printPixelARGB(im2.getRGB(i, j))<1) {
					res++;
				}
			}
		}
		res = (int) (((float) res / (im1.getWidth() * im1.getHeight())) * 100);
		return res;
//		 long maxDiff = 3L * 255 * im1.getWidth() * im1.getHeight();
//		 
//	        return (int) (100.0 * res / maxDiff);
	}

	public void fillDiff() {
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				if (i != j) {
					int maxsim = 0;
					for (int ri = 0; ri < 4; ri++) {
						BufferedImage im = (BufferedImage) foundEdgeImgO.get(j);
						int w = im.getWidth();
						int h = im.getHeight();
						double angle = (Math.PI / 2) * ri;
						AffineTransform tx = new AffineTransform();
						tx.rotate(angle, w / 2, h / 2);
						AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
						im = op.filter(im, null);
						int tmp = similarity(foundEdgeImgO.get(i), im);
						if(tmp>maxsim) {
							maxsim=tmp;
						}
					}
					matrix[i][j] = maxsim;
				}
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

	}

	public void init() {
		System.out.println("test: " + numberTest);
		readInfo();
		contentP.setLayout(new GridLayout(R * 2, C));
		foundpiece = 0;
		totalPoint = 99999999;
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
		matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				matrix[i][j] = 0;
			}
		}
		try {
			// checkAnImage(new File("D:\\ThiSinh\\input"+numberTest+"\\" + X +
			// ".txt"));
			for (int i = 1; i <= N; i++) {
				checkAnImage(new File("D:\\ThiSinh\\input" + numberTest + "\\" + i + ".bmp"));
				System.out.println("Processed img " + i);
			}

			fillDiff();
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

	public int[] differof1(int[][] e, int[][] e2, int direction, int bound, int rthres, int xttj) {
		int[] sum = new int[16];
		for (int i = 0; i < 16; i++)
			sum[i] = -1;
		// int bound = 7;
		// int rthres = 15;
		int tt = 0;
		// for (int tt = 0; tt < 1; tt++)
		for (int u = bound; u < K - bound - 1; u++) {
			if (bound + tt >= K || K - bound - 1 - tt < 0)
				continue;
			if (direction == 1) {
				if ((e[bound + tt][u] == e2[bound + tt][K - u - 1])
						&& (e[bound + tt][u + 1] == e2[bound + tt][K - (u + 1) - 1]))
					sum[0]++;
				if ((e[bound + tt][u] == e2[K - bound - 1 - tt][u])
						&& (e[bound + tt][u + 1] == e2[K - bound - 1 - tt][u + 1]))
					sum[1]++;
				if ((e[bound + tt][u] == e2[u][bound + tt]) && (e[bound + tt][u + 1] == e2[u + 1][bound + tt]))
					sum[2]++;
				if ((e[bound + tt][u] == e2[K - u - 1][K - bound - 1 - tt])
						&& (e[bound + tt][u + 1] == e2[K - (u + 1) - 1][K - bound - 1 - tt]))
					sum[3]++;
			}

			if (direction == 0) {
				if ((e[K - bound - 1 - tt][u] == e2[bound + tt][u])
						&& (e[K - bound - 1 - tt][u + 1] == e2[bound + tt][u + 1]))
					sum[4]++;
				if ((e[K - bound - 1 - tt][u] == e2[K - bound - 1 - tt][K - u - 1])
						&& (e[K - bound - 1 - tt][u + 1] == e2[K - bound - 1 - tt][K - (u + 1) - 1]))
					sum[5]++;
				if ((e[K - bound - 1 - tt][u] == e2[K - u - 1][bound + tt])
						&& (e[K - bound - 1 - tt][u + 1] == e2[K - (u + 1) - 1][bound + tt]))
					sum[6]++;
				if ((e[K - bound - 1 - tt][u] == e2[u][K - bound - 1 - tt])
						&& (e[K - bound - 1 - tt][u + 1] == e2[u + 1][K - bound - 1 - tt]))
					sum[7]++;
			}

			if (direction == 3) {
				if ((e[u][bound + tt] == e2[bound + tt][u]) && (e[u + 1][bound + tt] == e2[bound + tt][u + 1]))
					sum[8]++;
				if ((e[u][bound + tt] == e2[K - bound - 1 - tt][K - u - 1])
						&& (e[u + 1][bound + tt] == e2[K - bound - 1 - tt][K - (u + 1) - 1]))
					sum[9]++;
				if ((e[u][bound + tt] == e2[K - u - 1][bound + tt])
						&& (e[u + 1][bound + tt] == e2[K - (u + 1) - 1][bound + tt]))
					sum[10]++;
				if ((e[u][bound + tt] == e2[u][K - bound - 1 - tt])
						&& (e[u + 1][bound + tt] == e2[u + 1][K - bound - 1 - tt]))
					sum[11]++;
			}

			if (direction == 2) {
				if ((e[u][K - bound - 1 - tt] == e2[bound + tt][u])
						&& (e[u + 1][K - bound - 1 - tt] == e2[bound + tt][u + 1]))
					sum[12]++;
				if ((e[u][K - bound - 1 - tt] == e2[K - bound - 1 - tt][K - u - 1])
						&& (e[u + 1][K - bound - 1 - tt] == e2[K - bound - 1 - tt][K - (u + 1) - 1]))
					sum[13]++;
				if ((e[u][K - bound - 1 - tt] == e2[K - u - 1][bound + tt])
						&& (e[u + 1][K - bound - 1 - tt] == e2[K - (u + 1) - 1][bound + tt]))
					sum[14]++;
				if ((e[u][K - bound - 1 - tt] == e2[u][K - bound - 1 - tt])
						&& (e[u + 1][K - bound - 1 - tt] == e2[u + 1][K - bound - 1 - tt]))
					sum[15]++;
			}
		}
		int ff = 9999999;
		int r = 0;
		for (int i = 0; i < 16; i++) {
			if (sum[i] >= 0 && -sum[i] < ff) {
				ff = -sum[i];
				// r = rr[i];
				r = i;
			}
			// System.out.print(sum[i]+" ");
		}
		// System.out.println();
		return new int[] { ff, r };
	}

	public int[] differof2(int[][] e, int[][] e2, int direction, int bound, int rthres, int xtt) {
		int[] sum = new int[16];
		for (int i = 0; i < 16; i++)
			sum[i] = -1;
		// int bound = 7;
		// int rthres = 15;
		for (int tt = 0; tt <= xtt; tt++)
			for (int u = bound; u < K - bound; u++) {
				if (bound + tt >= K || K - bound - 1 - tt < 0)
					continue;
				if (direction == 1) {
					if (Math.abs(e[bound + tt][u] - e2[bound + tt][K - u - 1]) >= rthres)
						sum[0]++;
					if (Math.abs(e[bound + tt][u] - e2[K - bound - 1 - tt][u]) >= rthres)
						sum[1]++;
					if (Math.abs(e[bound + tt][u] - e2[u][bound + tt]) >= rthres)
						sum[2]++;
					if (Math.abs(e[bound + tt][u] - e2[K - u - 1][K - bound - 1 - tt]) >= rthres)
						sum[3]++;
				}

				if (direction == 0) {
					if (Math.abs(e[K - bound - 1 - tt][u] - e2[bound + tt][u]) >= rthres)
						sum[4]++;
					if (Math.abs(e[K - bound - 1 - tt][u] - e2[K - bound - 1 - tt][K - u - 1]) >= rthres)
						sum[5]++;
					if (Math.abs(e[K - bound - 1 - tt][u] - e2[K - u - 1][bound + tt]) >= rthres)
						sum[6]++;
					if (Math.abs(e[K - bound - 1 - tt][u] - e2[u][K - bound - 1 - tt]) >= rthres)
						sum[7]++;
				}

				if (direction == 3) {
					if (Math.abs(e[u][bound + tt] - e2[bound + tt][u]) >= rthres)
						sum[8]++;
					if (Math.abs(e[u][bound + tt] - e2[K - bound - 1 - tt][K - u - 1]) >= rthres)
						sum[9]++;
					if (Math.abs(e[u][bound + tt] - e2[K - u - 1][bound + tt]) >= rthres)
						sum[10]++;
					if (Math.abs(e[u][bound + tt] - e2[u][K - bound - 1 - tt]) >= rthres)
						sum[11]++;
				}

				if (direction == 2) {
					if (Math.abs(e[u][K - bound - 1 - tt] - e2[bound + tt][u]) >= rthres)
						sum[12]++;
					if (Math.abs(e[u][K - bound - 1 - tt] - e2[K - bound - 1 - tt][K - u - 1]) >= rthres)
						sum[13]++;
					if (Math.abs(e[u][K - bound - 1 - tt] - e2[K - u - 1][bound + tt]) >= rthres)
						sum[14]++;
					if (Math.abs(e[u][K - bound - 1 - tt] - e2[u][K - bound - 1 - tt]) >= rthres)
						sum[15]++;
				}
			}
		int ff = 9999999;
		int r = 0;
		for (int i = 0; i < 16; i++) {
			if (sum[i] >= 0 && sum[i] < ff) {
				ff = sum[i];
				// r = rr[i];
				r = i;
			}
			// System.out.print(sum[i]+" ");
		}
		// System.out.println();
		return new int[] { ff, r };
	}

	public void dfs(int theX, int xp, int yp) {

		// if (totalPoint > 0)
		// return;
		if (foundpiece >= R * C) {
			if (tmpPoint < totalPoint) {
				totalPoint = tmpPoint;
				for (int i = 0; i < R; i++) {
					for (int j = 0; j < C; j++) {
						res_marked[i][j] = marked[i][j];
						res_rot[i][j] = rot[i][j];
					}
				}
				for (int i = 0; i < R; i++) {
					for (int j = 0; j < C; j++)
						System.out.print(
								res_marked[i][j] + "*" + res_rot[i][j] + "\t" + used[res_marked[i][j] - 1] + "\t");
					System.out.println();
				}
				// for (int i = 0; i < N; i++)
				// System.out.print(used[i] + " ");
				System.out.println();
				System.out.println(totalPoint);
				System.out.println();
				System.out.println();

				// int[][] ee=(int[][]) foundEdge.get(0);
				//
				// for (int i = 0; i < K; i++) {
				// for (int j = 0; j < K; j++) {
				// System.out.print(ee[i][j] + " ");
				// }
				// System.out.println();
				// }
				// System.out.println();
				// System.out.println();
				// System.out.println();
				// System.out.println();
				// System.out.println();
				// System.out.println();
				//
				// ee=(int[][]) foundEdgeO.get(0);
				//
				// for (int i = 0; i < K; i++) {
				// for (int j = 0; j < K; j++) {
				// System.out.print(ee[i][j] + " ");
				// }
				// System.out.println();
				// }
			}
			return;
		}
		// int oldtmp = tmpPoint;
		// int oldfound = foundpiece;
		// System.out.println(theX);
		int[][] e = (int[][]) foundEdge.get(theX);
		int[][] eO = (int[][]) foundEdgeO.get(theX);
		int oldtmpPoint = tmpPoint;
		int[] queue = { -1, -1, -1, -1 };
		int[] mintmp = { 9999999, 9999999, 9999999, 9999999 };
		int[] maxtmp = { -9999999, -9999999, -9999999, -9999999 };
		for (int i = 0; i < 4; i++) {
			if (xp + dx[i] >= 0 && xp + dx[i] < R && yp + dy[i] >= 0 && yp + dy[i] < C) {
				if (marked[xp + dx[i]][yp + dy[i]] == 0) {
					// int mx = -1, xxx = -1, rr = 0;
					int jjj = 0, rott = 0;
					mintmp[i] = 9999999;
					for (int j = 0; j < N; j++) {
						if (j == theX || used[j] > 0 ||  matrix[theX][j]>40||  matrix[j][theX]>40)
							continue;
						int[][] e2 = (int[][]) foundEdge.get(j);
						int[][] e2O = (int[][]) foundEdgeO.get(j);
						int[] tmp = differof2(e, e2, i, 7, 0, 1);
						// int[] tmp1 = differof1(e, e2, i, 7, 2);
						int[] tmpO = differof2(eO, e2O, i, 0, 2, 1);
						// int[] tmpO1 = differof1(eO, e2O, i, 0, 5);
						int tmppoint2 = 0;
						// int tmppoint3 = 0;
						for (int ii = 0; ii < 4; ii++) {
							int xxp = xp + dx[i] + dx[ii];
							int yyp = yp + dy[i] + dy[ii];
							if (xxp >= 0 && xxp < R && yyp >= 0 && yyp < C) {
								if (marked[xxp][yyp] > 0 && marked[xxp][yyp] != theX + 1) {
									int[] tmp2 = differof2(e2, (int[][]) foundEdge.get(marked[xxp][yyp] - 1), ii, 7, 0,
											1);// 7, 1, 15
									// int[] tmp3 = differof1(e2, (int[][])
									// foundEdge.get(marked[xxp][yyp] - 1), ii,
									// 7, 2);
									int[] tmp2O = differof2(e2O, (int[][]) foundEdgeO.get(marked[xxp][yyp] - 1), ii, 0,
											2, 1);// 0,50, 15
									// int[] tmp2O1= differof1(e2O, (int[][])
									// foundEdgeO.get(marked[xxp][yyp] - 1),
									// ii, 0,5);
									tmppoint2 += tmp2[0] + tmp2O[0];
									// tmppoint3 += tmp3[0] + tmp2O1[0];
								}
							}
						}

						if (tmp[0] + tmpO[0] + tmppoint2 < mintmp[i]) {
							mintmp[i] = tmp[0] + tmpO[0] + tmppoint2;
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
					queue[i] = jjj;
					tmpPoint += mintmp[i];
				}
				// for (int ii = 0; ii < R; ii++) {
				// for (int jj = 0; jj < C; jj++)
				// System.out.print(marked[ii][jj] + " ");
				// System.out.println();
				// }
				// System.out.println();
			}
		}

		for (int i = 0; i < 4; i++) {
			if (queue[i] > 0) {
				// tmpPoint += tmppoint2;
				dfs(queue[i], xp + dx[i], yp + dy[i]);
				// marked[xp + dx[i]][yp + dy[i]] = 0;
				// used[queue[i]] = 0;
				// foundpiece--;
				// tmpPoint -= mintmp[i];
			}
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
		// detector.setLowThreshold(0.005f);
		// detector.setHighThreshold(0.05f);

		detector.setSourceImage(bufferImage2);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		BufferedImage b = new BufferedImage(edges.getWidth(), edges.getHeight(), edges.getType());
		Graphics g = b.getGraphics();
		g.drawImage(edges, 0, 0, null);
		g.dispose();
		foundEdgeImgO.add(bufferImage2);
		foundEdgeO.add(BItoA(bufferImage2));
		foundEdgeImg.add(b);
		foundEdge.add(BItoA(b));
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
		// ImageIcon imageIcon = new
		// ImageIcon(image.getImage().getScaledInstance(150,
		// 150, Image.SCALE_SMOOTH));
		// imageLabel.setIcon(imageIcon);
		// imageLabel.setVisible(true);
	}

	public int printPixelARGB(int pixel) {
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		// System.out.println("argb: " + alpha + ", " + red + ", " + green +
		// ", " +
		// blue);
		return (red + green + blue) / 3;
	}

	public int[][] BItoA(BufferedImage edges) {
		int[][] arr = new int[edges.getWidth()][edges.getHeight()];

		for (int i = 0; i < edges.getWidth(); i++)
			for (int j = 0; j < edges.getHeight(); j++)
				arr[i][j] = printPixelARGB(edges.getRGB(i, j));

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
			System.out.print(N + " ");
			System.out.print(K + " ");
			System.out.print(R + " ");
			System.out.println(C + " ");
			System.out.println(rotate + " ");
			System.out.print(X + " ");
			System.out.print(rX + " ");
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
		int i = 0;
		// for (i = 15; i < 26; i++) {
		FrameMainBFS f = new FrameMainBFS();
		// if(i==15)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.numberTest = i;
		f.process();
		// }
	}
}
