
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GameOverFrame extends JFrame {

	public GameOverFrame() {

	}

	public GameOverFrame(int score) {

	}

	public GameOverFrame(String title, int score) {
		super(title);
		
		int highscore = getHighscore();
		if (score > highscore) highscore = setHighscore(score);

		//UI
		setSize(200, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel gameOverLabel = new JLabel("Game Over");
		gameOverLabel.setBounds(15, 20, 200, 40);
		gameOverLabel.setFont(new Font("Serif", Font.BOLD, 32));

		JLabel scoreLabel = new JLabel("Your Score: " + score);
		scoreLabel.setBounds(25, 80, 200, 40);
		scoreLabel.setFont(new Font("Serif", Font.PLAIN, 24));

		JLabel highscoreLabel = new JLabel("High Score: " + highscore);
		highscoreLabel.setBounds(25, 120, 200, 40);
		highscoreLabel.setFont(new Font("Serif", Font.PLAIN, 24));

		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(15, 180, 160, 40);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameDriver.createGameFrame();
				setVisible(false);
			}
		});

		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(15, 240, 160, 40);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		getContentPane().add(gameOverLabel);
		getContentPane().add(highscoreLabel);
		getContentPane().add(scoreLabel);
		getContentPane().add(restartButton);
		getContentPane().add(exitButton);
	}


	public int getHighscore() {

		int highscore = 0;

		try {
			BufferedReader highscoresReader = new BufferedReader(new FileReader("highscore.txt"));

			highscore = Integer.parseInt(highscoresReader.readLine());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return highscore;
	}

	public int setHighscore(int score) {
		
		BufferedWriter highscoreWriter = null;

		try {
			highscoreWriter = new BufferedWriter(new FileWriter("highscore.txt"));
			highscoreWriter.write("" + score);
			highscoreWriter.flush();
		}
		catch (IOException e) {

		}
		finally {
			try {
				highscoreWriter.close();
			}
			catch (IOException e) {}
		}

		return score;
	}
}