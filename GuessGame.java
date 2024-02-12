import java.awt.BorderLayout;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GuessGame extends JFrame{
    private int MIN_NUMBER = 0;
    private int MAX_NUMBER = 100;
    private int MAX_GUESSES = 10;
    private int secretNumber;
    private int remainingGuesses;

    private JTextArea  messageArea;
    private JTextField guessField;
    private JButton guessButton;

    public GuessGame(){
        setTitle("MyGuessGame");
        setDefaultCloseOperation(3);
        setSize(400, 500);
        setLayout(new BorderLayout());

        initializeGame();
        setupUI();
    }

    private void initializeGame(){
        Random random = new Random();
        secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        remainingGuesses = MAX_GUESSES;
    }
     
    private void setupUI(){
        setupMessageArea();
        setupInputPanel();
    }

    private void setupMessageArea(){
        messageArea = new JTextArea();      
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupInputPanel(){
        JPanel inputPanel = new JPanel(new BorderLayout());
        guessField = new JTextField();
        guessField.addActionListener(e -> processGuess());
        guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> processGuess());

        inputPanel.add(guessField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        showMessage("Welcome to Guess the Number Game!\nI'm thinking of a number between "
                + MIN_NUMBER + " and " + MAX_NUMBER + ".\nCan you guess what it is?");
        guessField.requestFocusInWindow();
    }

    private void showMessage(String message){
        messageArea.append(message + "\n");
    }

    private void processGuess(){
        try{    
            int guess = Integer.parseInt(guessField.getText());
            showMessage("Your guess: " + guess);
            if (guess == secretNumber) {
                showMessage("Congratulations! You guessed the number " + secretNumber + " correctly!");
                gameOver();
            }
            else{
                remainingGuesses--;
                if(remainingGuesses == 0){
                    showMessage("Sorry, you've run out of guesses. The correct number was " + secretNumber + ".");
                    gameOver();
                } 
                else{
                    String feedback = guess < secretNumber ? "Too low. Try again." : "Too high. Try again.";
                    showMessage("Incorrect guess. " + feedback + " (" + remainingGuesses + " guesses remaining)");
                }
            }
            guessField.setText(""); 
        }
        catch(NumberFormatException e){
            showMessage("Please enter a valid number");;
        }
    }

    private void gameOver(){
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessGame game = new GuessGame();
            game.setVisible(true);
            game.guessField.requestFocusInWindow();
        });
    }
}
