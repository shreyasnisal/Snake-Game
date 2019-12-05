
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GameDriver {

	private static MenuFrame menuFrame = new MenuFrame("Snake");
	private static JFrame gameFrame = new JFrame("Snake");

	public static void main(String[] args) {
		menuFrame.setVisible(true);
	}


	public static JFrame createGameFrame() {
		GamePanel gamePanel = new GamePanel();

		gameFrame.setContentPane(gamePanel);

		gameFrame.setSize(500, 500);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		gamePanel.requestFocusInWindow();

		return gameFrame;
	}

	public static JFrame getGameFrame() {
		return gameFrame;
	}

	public static JFrame getMenuFrame() {
		return menuFrame;
	}

	public static JFrame createGameOverFrame(int score) {
		gameFrame.setVisible(false);
		GameOverFrame gameOverFrame = new GameOverFrame("Game Over", score);
		gameOverFrame.setVisible(true);
		return gameOverFrame;
	}
}