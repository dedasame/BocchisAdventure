package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//lets the window properly close when user click x 
		window.setResizable(false);
		window.setTitle("BOCCHI'S ADVENTURE");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		
		window.setLocationRelativeTo(null);
		//window display of center of screen
		window.setVisible(true);
		
		
		gamePanel.startGameThread();
		
		
	}

}
