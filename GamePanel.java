
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class GamePanel extends JPanel {

	private static final int UPDATE_RATE = 8;
	private static final int SNAKE_CELLSIZE = 30;
	private static final int SNAKE_SPEED = 30;
	private static final int FOOD_SIZE = 25;
	private static final int SNAKE_MAX_LENGTH = 10;

	private boolean gamePaused = false;

	private int snakeHead_x = 40;
	private int snakeHead_y = 20;

	private int snakeSpeed_x = SNAKE_SPEED;
	private int snakeSpeed_y = 0;

	private int foodX = 0;
	private int foodY = 0;

	private int score = 0;

	private java.util.List<Point> snakeTailPositions;

	public GamePanel() {

		snakeTailPositions = new ArrayList<Point>();

		snakeTailPositions.add(new Point(snakeHead_x - SNAKE_CELLSIZE, snakeHead_y));
		snakeTailPositions.add(new Point(snakeHead_x - SNAKE_CELLSIZE, snakeHead_y));

		Thread snakeMove = new Thread() {
			public void run() {
				while (true) {

					for (int i = snakeTailPositions.size() - 1; i >= 0; i--) {
						if (i == 0) {
							snakeTailPositions.set(i, new Point(snakeHead_x, snakeHead_y));
						}
						else {
							snakeTailPositions.set(i, snakeTailPositions.get(i - 1));
						}
					}

					snakeHead_x += snakeSpeed_x;
					snakeHead_y += snakeSpeed_y;

					try {
						sleep(1000 / UPDATE_RATE);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}

					repaint();
				}
			}
		};

		Thread collisionThread = new Thread() {
			@Override
			public void run() {
				while (true) {
					//collisions
					if (snakeHead_x + SNAKE_CELLSIZE >= foodX && snakeHead_x <= foodX + FOOD_SIZE && snakeHead_y + SNAKE_CELLSIZE >= foodY && snakeHead_y <= foodY + FOOD_SIZE) {
						//snake-food collision
						score++;
						growSnake();
						spawnFood();
						repaint();
					}

					//snake head-tail collision
					for (int i = 0; i < snakeTailPositions.size(); i++) {
						if (snakeHead_x + SNAKE_CELLSIZE > snakeTailPositions.get(i).x && snakeHead_x < snakeTailPositions.get(i).x + SNAKE_CELLSIZE && snakeHead_y + SNAKE_CELLSIZE > snakeTailPositions.get(i).y && snakeHead_y < snakeTailPositions.get(i).y + SNAKE_CELLSIZE) {
							gameOver();
							snakeMove.stop();
							stop();
						}
					}

					//snake-wall collision
					if (snakeHead_x <= 0 || snakeHead_x + SNAKE_CELLSIZE >= 500 || snakeHead_y <= 0 || snakeHead_y + SNAKE_CELLSIZE >= 500) {
						gameOver();
						snakeMove.stop();
						stop();
					}

					try {
						sleep(1000/UPDATE_RATE);
					}
					catch (InterruptedException e) {

					}
				}
			}
		};

		snakeMove.start();
		collisionThread.start();
		spawnFood();

		addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_DOWN && snakeSpeed_y != -SNAKE_SPEED) {
					snakeSpeed_x = 0;
					snakeSpeed_y = SNAKE_SPEED;
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP && snakeSpeed_y != SNAKE_SPEED) {
					snakeSpeed_x = 0;
					snakeSpeed_y = -SNAKE_SPEED;
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT && snakeSpeed_x != SNAKE_SPEED) {
					snakeSpeed_x = -SNAKE_SPEED;
					snakeSpeed_y = 0;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && snakeSpeed_x != -SNAKE_SPEED) {
					snakeSpeed_x = SNAKE_SPEED;
					snakeSpeed_y = 0;
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (gamePaused) {
						snakeMove.resume();
						gamePaused = false;
					}
					else {
						snakeMove.suspend();
						gamePaused = true;
					}
				}
			}
		});
	}

	public void spawnFood() {
		Random random = new Random();
		boolean validLocation = true;

		do {
			validLocation = true;

			foodX = random.nextInt(400) + 30;
			foodY = random.nextInt(400) + 30;

			for (int i = 0; i < snakeTailPositions.size(); i++) {
				if (snakeTailPositions.get(i).x + SNAKE_CELLSIZE >= foodX && snakeTailPositions.get(i).x <= foodX + FOOD_SIZE && snakeTailPositions.get(i).y + SNAKE_CELLSIZE >= foodY && snakeTailPositions.get(i).y <= foodY + FOOD_SIZE) {
					validLocation = false;
					// System.out.println("Invalid Location");
					break;
				}
			}

			if (snakeHead_x + SNAKE_CELLSIZE >= foodX && snakeHead_x <= foodX + FOOD_SIZE && snakeHead_y + SNAKE_CELLSIZE >= foodY && snakeHead_y <= foodY + FOOD_SIZE) {
				validLocation = false;
				// System.out.println("Inavalid Location");
				continue;
			}

		} while(!validLocation);

	}

	public void growSnake() {
		Point lastTailPosition = snakeTailPositions.get(snakeTailPositions.size() - 1);
		snakeTailPositions.add(new Point(lastTailPosition.x - SNAKE_CELLSIZE*snakeSpeed_x, lastTailPosition.y - SNAKE_CELLSIZE*snakeSpeed_y));
		// System.out.println("Snake Size: " + (snakeTailPositions.size() + 1));
	}


	public void gameOver() {
		GameDriver.createGameOverFrame(score);
	}
	
	public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics);

		graphics.drawString("Score: " + score, 20, 20);

		graphics.setColor(Color.BLUE);
		graphics.fillOval(snakeHead_x, snakeHead_y, SNAKE_CELLSIZE, SNAKE_CELLSIZE);

		graphics.setColor(Color.GREEN);
		for (Point tailPosition : snakeTailPositions) {
			graphics.fillOval(tailPosition.x, tailPosition.y, SNAKE_CELLSIZE, SNAKE_CELLSIZE);
		}

		graphics.setColor(Color.RED);
		graphics.fillRect(foodX, foodY, FOOD_SIZE, FOOD_SIZE);

	}
}