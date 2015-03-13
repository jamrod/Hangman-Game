
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static java.lang.System.out;

public class HangmanGame {
	
	//method to get word
	
	public static String getWord() {
		boolean tooLong = false;
		boolean invalidChar = false;
		String word = null;
		do {
			
			if (tooLong == false && invalidChar == false){
		 word = JOptionPane.showInputDialog("Enter a word for the player to guess; less than 8 letters, please");
			}	
		    if (tooLong == true){
				word = JOptionPane.showInputDialog("Sorry, too long; less than 8 letters, please");
		    }
			if (invalidChar == true){
				word = JOptionPane.showInputDialog("Invalid character detected, try again; less than 8 letters, please");
			}
			
		//check word length
		int length = word.length(); 
		if (length > 8) { tooLong = true; }
		else {tooLong = false;}
					
		
		//check word for invalid characters
		boolean tempBoolean = false;
		StringBuilder checkedString = new StringBuilder();
		CharacterIterator checkChars = new StringCharacterIterator(word);	
		for (char ch = checkChars.first(); ch != CharacterIterator.DONE; ch = checkChars.next())
			{
			if (Character.isLetter(ch) == (false))
				tempBoolean = true;
			    char lowerCh = Character.toLowerCase(ch);
			    checkedString.append(lowerCh);
			    
			}
		if (tempBoolean == true)
		   {invalidChar = true;}
		else {invalidChar = false;}
		word = checkedString.toString();
		}
		while (tooLong == true || invalidChar == true);
		
		//out.println(word);
		return word;
		
	} // close GetWord	
	
	//method to set right letters string to _
	public static char[] getLetterString( int length) {
		char[] letters = new char[length];
		
		for( int i = 0; i < length; i++)
		{
			letters[i] = '_';
		}
		
		return letters;
	}
		
	
	//Game Frame
	public static class GameFrame extends JFrame implements EventListener, ActionListener 
	{
		
		private static final long serialVersionUID = 1L;
		String word = getWord();
		
		//initializations
		int wordLength = word.length();
		static int numGuesses = 8;			
		boolean correctGuess;
		boolean allCorrect;
		
		//convert word string to Char array	
		char rightLetters[] = getLetterString(wordLength);
		String rightLettersString = new String(rightLetters);
		
		
		//Frame components
		JLabel wordLengthLabel = new JLabel ("Word Length = " + wordLength);
		JLabel guessesUsed = new JLabel ("Guesses " + numGuesses);
		JTextField letterField = new JTextField(); 	
		JButton guess = new JButton ("Guess Letter");
		JTextField wordField = new JTextField(word.length());
		JButton guessWord = new JButton ("Guess Word");
		JLabel rightLettersGuessed = new JLabel ("Right Letters");
		JLabel showRightLetters = new JLabel (rightLettersString);
		String GuessedLetters = "-";
		JLabel youGuessed = new JLabel ("You've Guessed-");
		JLabel showGuesses = new JLabel (GuessedLetters);
		JButton playAgain = new JButton ("Play Again?");
		
		static JPanel buttonPanel = new JPanel();
		
		
		
		
		//class to display image
				public static class ImagePanel  extends JPanel   {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					static BufferedImage man = setImage(8);
					
					JLabel picLabel = new JLabel(new ImageIcon(man));
					
					//method to display image
					public ImagePanel()  {
						setImage(numGuesses);
						//JLabel picLabel = new JLabel(new ImageIcon(man));
						add(picLabel);
						setVisible(true);
						}
					//method to set image
					public static BufferedImage setImage(int numGuesses)  {	
						try {
						switch (numGuesses)
						 {
						case 0:
							man = ImageIO.read(new File("src/resources/dead.bmp"));
							break;
						case 1:
							man = ImageIO.read(new File("src/resources/1guesses.bmp"));
							break;
						case 2:
							man = ImageIO.read(new File("src/resources/2guesses.bmp"));
							break;
						case 3:
							man = ImageIO.read(new File("src/resources/3guesses.bmp"));
							break;
						case 4:
							man = ImageIO.read(new File("src/resources/4guesses.bmp"));
							break;
						case 5:
							man = ImageIO.read(new File("src/resources/5guesses.bmp"));
							break;
						case 6:
							man = ImageIO.read(new File("src/resources/6guesses.bmp"));
							break;	
						case 7:
							man = ImageIO.read(new File("src/resources/7guesses.bmp"));
							break;
						case 8:
							man = ImageIO.read(new File("src/resources/guess.bmp"));
							break;	
						case 9:
							man = ImageIO.read(new File("src/resources/win.bmp"));
							break;
						
						}
						
						}
						catch(IOException ex){
					        System.out.println (ex.toString());
					        System.out.println("Could not find file " + numGuesses);	
						}
						return man;
					
					}
				
				}
				static ImagePanel panel = new ImagePanel();
		
				
		//Frame Method
		public GameFrame (){
			setTitle("HangMan Game");
			numGuesses = 8;
			guessesUsed.setText("Guesses " + numGuesses);
			add (buttonPanel);
			buttonPanel.setLayout(new GridLayout(0,1,0,10));
			buttonPanel.add(wordLengthLabel);
			buttonPanel.add(guessesUsed);
			buttonPanel.add(letterField);
			buttonPanel.add(guess);
			buttonPanel.add(wordField);
			buttonPanel.add(guessWord);
			buttonPanel.add(rightLettersGuessed);
			buttonPanel.add(showRightLetters);
			buttonPanel.add(youGuessed);
			buttonPanel.add(showGuesses);
			
			ImagePanel.setImage(8);
		    panel.picLabel.setIcon(new ImageIcon(ImagePanel.man));
			add(panel);
			

			guess.addActionListener(this);
			guessWord.addActionListener(this);
			playAgain.addActionListener(this);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new FlowLayout());
			pack();
			setVisible(true);
			//setLocationRelativeTo(null);
		}
				

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//test if guessed word is correct
			if (arg0.getSource() == guessWord) {
				String GuessWord = wordField.getText();
				
				if (GuessWord.equals(word))
				{
					out.println("You Win");
					numGuesses = 10;
					WinLoseFrame();
				}
			
				 
				}
				   
				
			
			if (arg0.getSource() == guess) {
			//test input char to see if it is correct and set value in array
				char inputChar = letterField.getText().charAt(0);
				int counter = 0;
				correctGuess = false;
				allCorrect = true;
				GuessedLetters = GuessedLetters + inputChar;
				
					//loop to iterate through char array
				CharacterIterator wordChars = new StringCharacterIterator(word);	
				for (char ch = wordChars.first(); ch != CharacterIterator.DONE; ch = wordChars.next()
				)
					{
					if (ch == inputChar) {
						rightLetters[counter] = inputChar;			
						correctGuess = true;						
					}
					else {
						//test if a letter has already been assigned
						if (Character.isLetter(rightLetters[counter]) == (false)) 
						//assign blank instead of letter
							{
							rightLetters[counter] = '_';
							allCorrect = false;
							}
					}
					counter ++;
				}
					
					out.println ("once through guess counter= " + counter);
			
				}
			if (arg0.getSource() == playAgain){
				
				buttonPanel.removeAll();
				//panel.removeAll();
				getContentPane().repaint();
				main (null);
			}
			{
			if (allCorrect) {
				numGuesses = 9;
				out.println("You Win!");
				WinLoseFrame();
				allCorrect = false;
			}
			if (correctGuess) {
				numGuesses ++; 
				correctGuess = false;
				}
			if (arg0.getSource() != playAgain){
			numGuesses --;
			}
			if (numGuesses <= 0) {
				out.println("So Sorry, You Lose!");
				WinLoseFrame();
			}
			
			guessesUsed.setText("Guesses " + numGuesses);
			guessesUsed.repaint();
			String rightLettersString = new String(rightLetters);
			//rightLettersString.replaceAll(".(?=.)", "$0 ");
			showRightLetters.setText(rightLettersString);
			showRightLetters.repaint();
			showGuesses.setText(GuessedLetters);
			showGuesses.repaint();
			letterField.setText("");
			letterField.requestFocus();
			wordField.setText("");
			GameFrame.buttonPanel.revalidate();
			GameFrame.buttonPanel.repaint();
			
		    
			ImagePanel.setImage(numGuesses);
		    panel.picLabel.setIcon(new ImageIcon(ImagePanel.man));
			
			GameFrame.panel.picLabel.repaint();
		    panel.revalidate();
		    panel.repaint();
		    panel.setVisible(true);
			out.println("numGuesses-- " + numGuesses);
			}
			
			
			
		}


		private void WinLoseFrame() {
			// TODO Auto-generated method stub
			letterField.setEnabled(false);
			guess.setEnabled(false);
			wordField.setEnabled(false);
			guessWord.setEnabled(false);
			rightLettersGuessed.setText(word);
			if (numGuesses >= 9) {
			youGuessed.setText("You Win!");
			}
			else {
				youGuessed.setText("Sorry, You Lose.");
			}
			//JLabel rightWord = new JLabel(word);
			//buttonPanel.add(rightWord);
			buttonPanel.add(playAgain);
			
		}

		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	GameFrame frame = new GameFrame();
	//GameFrame.ImagePanel panel = new GameFrame.ImagePanel();
	//frame.add(panel);
	frame.pack();
	
	}

}
