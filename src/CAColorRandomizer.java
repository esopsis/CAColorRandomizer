import java.awt.*;
import java.awt.image.BufferedImage;
import java.applet.*;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.*;

/*
CAColorRandomizer.java
by Eric J.Parfitt (ejparfitt@gmail.com)

This program interprets the evolution of a rule 110 celluar automaton
as a bunch of different colored triangle-oid shapes.  A ramdomizing
element runs slowly from right to left on the screen, to keep the 
evolution from dying down.

Version: 1.0 alpha
*/

public class CAColorRandomizer extends Applet implements Runnable {
	//Variables
	private boolean[] ruleDigit = {false, true, true, false, true, true, true, false};	//Rule 110
	//private boolean[] ruleDigit = {false, false, false, true, true, true, true, false};		//Rule 30
	private BufferedImage drawing;
	private Graphics2D g;
	private Thread myThread;
	private int triSize = 1;
	private int arrayH = 250 / triSize;
	private int xMax = 500;
	private Random random = new Random();
	
	boolean cellA = false;
	boolean cellB = false;
	boolean cellC = false;
	private boolean[] row = new boolean[xMax];
	private boolean[][] array = new boolean[arrayH][xMax];
	private boolean[] tempRow = new boolean[row.length];
	private int numColors = 7;
	private boolean isUp;
	private boolean isDown;
	public boolean keyDown(Event e, int key) {
		if(key == Event.UP) {
			isUp = true;
		}
		if(key == Event.DOWN) {
			isDown = true;
		}
		return true;
	}

	public boolean keyUp(Event e, int key) {
		if(key == Event.UP) {
			isUp = false;
		}
		if(key == Event.DOWN) {
			isDown = false;
		}
		return true;
	}
	public void keyTyped(KeyEvent e) {

	}
	Scanner scan = new Scanner(System.in);


	// More variables
	
	Color redColor;
	Color weirdColor;
	Color bgColor;
	Color whiteColor;
	Color blackColor;
	Color greenColor;
	Color yellowColor;
	Color orangeColor;
	Color cyanColor;
	Color blueColor;
	Color violetColor;
	Color purpleColor;
	Color redPurple;
	private int xSize = 1200;
	private int ySize = 1000;
	private int offset = 10;
	private int stepNumber = 1;
	int initRowL;
	public void init() 
	{
		
		for(int i = 0; i < row.length; i++) {
			row[i] = random.nextBoolean();
		}
		// set up double buffering
		drawing = new BufferedImage((int) xSize, (int) ySize, BufferedImage.TYPE_4BYTE_ABGR);
		g = drawing.createGraphics();
		resize((int) xSize, (int) ySize);

		// Colors
		initRowL = row.length;
		redColor = Color.red;
		weirdColor = new Color(60,60,122);
		bgColor = Color.black;
		whiteColor = Color.white;
		blackColor = Color.black;
		greenColor = Color.green;
		yellowColor = Color.yellow;
		orangeColor = new Color(255, 128, 0);
		cyanColor = Color.cyan;
		blueColor = Color.blue;
		purpleColor = new Color(128,0,255);
		violetColor = new Color(255, 0, 255);
		redPurple = new Color (255,0,128);

		setBackground(bgColor);
		
		for (int i = 0; i < row.length; i++) {
			array[0][i] = row[i];
		}
	}
	

	public void stop()
	{
		myThread = null;
	}

	public void start() {
		if(myThread == null) {
			myThread = new Thread(this, "curvechange");
			myThread.start();
		}
	}
	//Gets keys and updates graphics
	public void run() {
		while(true) {
			//speed
			try {Thread.sleep(0);}
			catch(Exception e) {}
			update(getGraphics());
			
		}
	}
	public void update(Graphics gr) {
		paint(gr);
	}
	// Draws stuff on screen
	public void paint(Graphics gr) {
		
		if (isUp == true) {
			while(isDown == false) {
				Wait.oneSec();
			}
		}
		
		g.setColor(bgColor);
		g.fillRect(0, 0, (int) xSize, (int) ySize);
		//Randomizes center line
		row[row.length - 1 - (int) (stepNumber / 8) % row.length] = random.nextBoolean();
		for(int i = 0; i < row.length && i < xMax; i++) {
			array[arrayH - 1][i] = row[i];
		}
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				
				if (array[i][j] == true) {
					g.setColor(blackColor);
				}
				else {
					g.setColor(whiteColor);
				}
				//finds length of triangle top
				if(j==0){
					}
				if (array[i][j] == false) {
					if(j==0){
						}
					int numTrue = 0;
					while (j + numTrue + 1 < array[i].length && array[i][j + numTrue + 1]) {
						if(j==0){
							}
						numTrue++;
						if (j + numTrue + 1 == array[i].length && (i-1) >= 0 && array[i-1][0] == true) {
							int k = 0;
							while (array[i-1][k]) {
								numTrue++;
								k++;
							}
							
						}
					}
					if (numTrue % numColors  - 2 == 1) {
						g.setColor(redColor);
					}
					if (numTrue % numColors - 2 == 2) {
						g.setColor(orangeColor);
					}
					if (numTrue % numColors - 2 == 3) {
						g.setColor(yellowColor);
					}
					if (numTrue % numColors - 2 == 4) {
						g.setColor(greenColor);
					}
					if (numTrue % numColors - 2 == -2) {
						g.setColor(cyanColor);
					}
					if (numTrue % numColors - 2 == -1) {
						g.setColor(blueColor);
					}
					if (numTrue % numColors -2 == 0) {
						g.setColor(violetColor);
					}
					if (numTrue >= 3) {
						int l = 0;
						for (int k = triSize * (2 * numTrue - 2) - (triSize * 2 - 1); k > 0; k--) {
							g.drawLine(triSize * 2 - 1 + offset + triSize * 2 * j, 50 + triSize * 2 * i + triSize * 2 * (numTrue - 1) - k, triSize * 2 - 1 + offset + triSize * 2 * j + k - 1, 50 + triSize * 2 * i + triSize * 2 * (numTrue - 1) - k);
							l++;
						}
					}
					if (numTrue >= 3) {
						g.fillRect(offset + triSize * 2 * j, 50 + triSize * 2 * i, triSize * 2 * ((numTrue - 2) + 1), triSize * 2 - 1);
						g.fillRect(offset + triSize * 2 * j, 50 + triSize * 2 * i, triSize * 2 - 1, triSize * 2 * ((numTrue - 2) + 1));
					}

				}
				if (i + 1 < array.length) {
					array[i][j] = array[i + 1][j];
				}
			}
		}
		g.setColor(bgColor);
		
		for(int i = 0; i < row.length; i++){
			
			if (i - 1 >=0 && i + 1 < row.length) {
				cellA = row[i - 1];
				cellB = row[i];
				cellC = row[i + 1];
			}
			if (i == row.length - 1) {
				cellA = row[row.length - 2];
				cellB = row[row.length - 1];
				cellC = row[0];
			}
			if (i == 0) {
				cellA = row[row.length - 1];
				cellB = row[0];
				cellC = row[1];
			}
			
			if (cellA == false && cellB == false &&  cellC == false) {
				tempRow[i] = ruleDigit[7];
			}
			if (cellA == false && cellB == false && cellC == true) {
				tempRow[i] = ruleDigit[6];
			}
			if (cellA == false && cellB == true && cellC == false) {
				tempRow[i] = ruleDigit[5];
			}
			if (cellA == false && cellB == true && cellC == true) {
				tempRow[i] = ruleDigit[4];
			}
			if (cellA == true && cellB == false && cellC == false) {
				tempRow[i] = ruleDigit[3];
			}
			if (cellA == true && cellB == false && cellC == true) {
				tempRow[i] = ruleDigit[2];
			}
			if (cellA == true && cellB == true && cellC == false) {
				tempRow[i] = ruleDigit[1];
			}
			if (cellA == true && cellB == true && cellC == true) {
				tempRow[i] = ruleDigit[0];
			}
		}
		for (int i = 0; i < tempRow.length; i++) {
			row[i] = tempRow[i];
		}
		tempRow = new boolean[row.length];
		//Increments step number
		stepNumber ++;
		gr.drawImage(drawing, 0,0, this);
	}
}
	
