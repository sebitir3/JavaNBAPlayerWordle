
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiris0808
 */
public class nbaGuesser implements Runnable, ActionListener {

    // Class Variables  
    Random rand = new Random();

    int randPlayer = rand.nextInt(34);
    int guessCounter;

    JPanel mainPanel;
    JPanel statGridPanel;

    JTextField guessBox;

    JLabel poeltlTitle;
    JLabel subtitleText;
    JLabel[] statGrid;

    JLabel name;
    JLabel team;
    JLabel conf;
    JLabel div;
    JLabel pos;
    JLabel height;
    JLabel age;

    JButton submitButton;

    JTextField inputPlayer;

    Font titleFont = new Font("Monospaced", Font.BOLD, 50);
    Font subtitleFont = new Font("Monospaced", Font.PLAIN, 35);

    Color cyan = new Color(16, 92, 89);
    Color closeYellow = new Color(219, 200, 22);
    Color correctGreen = new Color(27, 133, 20);

    //create array 
    String[][] stats = new String[33][9];

    // Method to assemble our GUI
    public void run() {
        // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
        JFrame frame = new JFrame("Bootleg Poeltl");
        // Makes the X button close the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // makes the windows 800 pixel wide by 600 pixels tall
        frame.setSize(790, 500);
        // shows the window
        frame.setVisible(true);

        //scanner // i = ROW || number = COLUMN
        try {
            Scanner input = new Scanner(new File("nbaplayers.csv"));
            for (int i = 0; i < 32; i++) {
                String line = input.nextLine();
                String[] perLine = line.split(",");
                stats[i][0] = perLine[0];
                stats[i][1] = perLine[1];
                stats[i][2] = perLine[2];
                stats[i][3] = perLine[3];
                stats[i][4] = perLine[4];
                stats[i][5] = perLine[5];
                stats[i][6] = perLine[6];
                stats[i][7] = perLine[7];
                stats[i][8] = perLine[8];

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(nbaGuesser.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create the panel to put things on
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //create a seperate panel for stats
        statGridPanel = new JPanel();
        statGridPanel.setLayout(new GridLayout(1, 9));

        //statheaders
        name = new JLabel("Name");
        name.setBounds(75, 315, 360, 50);
        name.setForeground(cyan);

        team = new JLabel("Team");
        team.setBounds(219, 315, 360, 50);
        team.setForeground(cyan);

        conf = new JLabel("Confer.");
        conf.setBounds(291, 315, 360, 50);
        conf.setForeground(cyan);

        div = new JLabel("Division");
        div.setBounds(363, 315, 360, 50);
        div.setForeground(cyan);

        pos = new JLabel("Position");
        pos.setBounds(435, 315, 360, 50);
        pos.setForeground(cyan);

        height = new JLabel("Height");
        height.setBounds(507, 315, 360, 50);
        height.setForeground(cyan);

        age = new JLabel("Age");
        age.setBounds(651, 315, 360, 50);
        age.setForeground(cyan);

        //guessbox
        guessBox = new JTextField();
        guessBox.setBounds(130, 230, 360, 50);

        //submitButton
        submitButton = new JButton("Submit");
        submitButton.setBounds(500, 230, 126, 49);
        //make button listen
        submitButton.addActionListener(this);
        submitButton.setActionCommand("submitGuess");

        //jlabels for titles
        poeltlTitle = new JLabel("POELTL");
        poeltlTitle.setBounds(300, 85, 600, 50);
        poeltlTitle.setFont(titleFont);
        poeltlTitle.setForeground(cyan);

        //subtitle
        subtitleText = new JLabel("NBA Player Guessing Game");
        subtitleText.setBounds(133, 150, 600, 50);
        subtitleText.setFont(subtitleFont);
        subtitleText.setForeground(cyan);

        //stat headers
        statGrid = new JLabel[9];
        for (int i = 0; i < statGrid.length; i++) {
            //make the label
            statGrid[i] = new JLabel("");

            //add button to the panel
            statGridPanel.add(statGrid[i]);

        }

        //add gui components main panel
        mainPanel.add(statGridPanel);
        mainPanel.add(poeltlTitle);
        mainPanel.add(subtitleText);
        mainPanel.add(guessBox);
        mainPanel.add(submitButton);

        //statheaders
        mainPanel.add(name);
        mainPanel.add(team);
        mainPanel.add(conf);
        mainPanel.add(div);
        mainPanel.add(pos);
        mainPanel.add(height);
        mainPanel.add(age);

        //set dimensions for stat grid
        statGridPanel.setBounds(75, 355, 650, 50);

        //add mainpanel to frame
        frame.add(mainPanel);

        //target player test
        System.out.println(stats[randPlayer][0]);

    }

    // method called when a button is pressed
    public void actionPerformed(ActionEvent e) {
        // get the command from the action
        String command = e.getActionCommand();
        //submitButton
        if (command.equals("submitGuess")) {
            String inputText = guessBox.getText();
            int spot = checkPlayer();

            //close heights
            int closeGuess = Integer.parseInt(stats[spot][7]);
            int closeTarget = Integer.parseInt(stats[randPlayer][7]);

            //close age
            int closeAge = Integer.parseInt(stats[spot][8]);
            int closeAgeTarget = Integer.parseInt(stats[randPlayer][8]);

            guessBox.setText("");
            //checkGuesses
            if (inputText.equals(stats[randPlayer][0] + " " + stats[randPlayer][1])) {
                System.out.println("correct");
                for (int i = 0; i < 9; i++) {
                    statGrid[i].setText(stats[spot][i]);
                    statGrid[i].setForeground(correctGreen);
                }
                //add 1 to guess counter
                guessCounter++;
                
                //display win and reset
                JOptionPane.showMessageDialog(mainPanel, "Congrats! You guessed " + stats[randPlayer][0] + " " + stats[randPlayer][1] + " in " + guessCounter + " guesses!");
                reset();

            } else {
                //test
                //System.out.println("incorrect");
                for (int i = 0; i < 9; i++) {
                    statGrid[i].setText(stats[spot][i]);

                    //check if stats match
                    if (i != 6 && stats[spot][i].equals(stats[randPlayer][i])) {
                        //set stat green if correct
                        statGrid[i].setForeground(correctGreen);
                    } //check if age and height stats are close
                    else if (closeAge == closeAgeTarget - 2 || closeAge == closeAgeTarget + 2 || closeAge == closeAgeTarget - 1 || closeAge == closeAgeTarget + 1) {
                        //set age yellow indicating close (w/in 2)
                        statGrid[8].setForeground(closeYellow);
                    } else if (closeGuess == closeTarget - 2 || closeGuess == closeTarget + 2 || closeGuess == closeTarget - 1 || closeGuess == closeTarget + 1) {
                        //set height yellow indicating close (w/in 2)
                        statGrid[6].setForeground(closeYellow);
                        statGrid[7].setForeground(closeYellow);
                        //check rest of stats like before
                        
                    } //stats do not match
                    else {
                        //set stats gray
                        statGrid[i].setForeground(Color.DARK_GRAY);
                    }
                }
                //add 1 to guesscounter even when wrong guess
                guessCounter++;
            }

        }
    }

    public int checkPlayer() {
        //check if inputed player is in player csv
        for (int i = 0; i < 31; i++) {
            String inputText = guessBox.getText();
            if (inputText.equals(stats[i][0] + " " + stats[i][1])) {
                return i;
            }
        }
        return -1;

    }

    public void reset() {
        //new random player
        randPlayer = rand.nextInt(32);

        //test if new random player generated
        //System.out.println(stats[randPlayer][0]);

        //make stat grid blank
        for (int i = 0; i < statGrid.length; i++) {
            statGrid[i].setText("");
        }
    }

    // Main method to start our program
    public static void main(String[] args) {
        // Creates an instance of our program
        nbaGuesser gui = new nbaGuesser();
        // Lets the computer know to start it in the event thread
        SwingUtilities.invokeLater(gui);
    }
}
