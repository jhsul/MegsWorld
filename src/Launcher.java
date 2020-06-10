import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 160;

    public static final Integer[] levels = {1, 2, 3, 4, 5};

    public static final Font titleFont = new Font("Verdana", Font.PLAIN, 20);
    public static final Font bodyFont = new Font("Verdana", Font.PLAIN, 14);

    private int selectedLevel;

    public Launcher() {
        super("Meg's World Launcher");

        selectedLevel = 1;

        JLabel title = new JLabel("MEG'S WORLD");
        title.setBounds(0, 20, WIDTH, 20);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(titleFont);

        ////

        JLabel levelLabel = new JLabel("Select level: ");
        levelLabel.setBounds(20, 60, (WIDTH / 2) - 40, 30);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setFont(bodyFont);

        JComboBox<Integer> levelSelect = new JComboBox(levels);
        levelSelect.setBounds((WIDTH / 2) + 20, 60, (WIDTH / 2) - 40, 30);
        levelSelect.setFont(bodyFont);

        levelSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selection = (Integer) levelSelect.getSelectedItem();
                if (!selection.equals(selectedLevel)) {
                    selectedLevel = selection;
                    //System.out.println("New selected level: " + selectedLevel);
                }
            }
        });

        ////

        JButton startButton = new JButton("Start");
        startButton.setBounds(20, 100, WIDTH - 40, 20);
        startButton.setFont(bodyFont);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Starting level: " + selectedLevel);
                Game game = new Game(selectedLevel);
                JFrame gameFrame = new JFrame("Level " + selectedLevel);
                gameFrame.add(game);
                gameFrame.pack();
                gameFrame.setSize(Game.WIDTH, Game.HEIGHT);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setResizable(false);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);

                game.start();
            }
        });

        add(title);
        add(startButton);
        add(levelLabel);
        add(levelSelect);

        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
