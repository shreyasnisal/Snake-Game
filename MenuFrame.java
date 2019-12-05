
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MenuFrame extends JFrame {

	public MenuFrame(String title) {
		super(title);

		setSize(200, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel titleLabel = new JLabel("Snake");
		titleLabel.setBounds(50, 20, 100, 40);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
		

		JButton startButton = new JButton("Play");
		startButton.setBounds(10, 100, 170, 40);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GameDriver.createGameFrame();

			}
		});

		// JButton highscoresButton = new JButton("Scores");
		// highscoresButton.setBounds(10, 150, 170, 40);
		// highscoresButton.addActionListener(new ActionListener() {
		// 	@Override
		// 	public void actionPerformed(ActionEvent e) {

		// 	}
		// }); 

		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(10, 150, 170, 40);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		getContentPane().add(titleLabel);
		getContentPane().add(startButton);
		// getContentPane().add(highscoresButton);
		getContentPane().add(exitButton);

		setVisible(true);

	}
}