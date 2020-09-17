package pl.mamicam.game.controllers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class GameController extends JFrame {
    private final int maxLvl = 6;
    private int lvl_Eat = maxLvl;
    private int lvl_Fun = maxLvl;
    private int lvl_Sleep = maxLvl;

    JTextField eatLvlField = new JTextField();
    JTextField funLvlField = new JTextField();
    JTextField sleepLvlField = new JTextField();

    public void addLifeInformation() {
        eatLvlField.setEditable(false);
        eatLvlField.setBounds(500, 10, 100, 50);
        eatLvlField.setText("Jedzenie: " + lvl_Eat);
        eatLvlField.setFont(new Font("Cambria", Font.BOLD, 13));

        funLvlField.setEditable(false);
        funLvlField.setBounds(500, 70, 100, 50);
        funLvlField.setText("Zabawa: " + lvl_Fun);
        funLvlField.setFont(new Font("Cambria", Font.BOLD, 13));

        sleepLvlField.setEditable(false);
        sleepLvlField.setBounds(500, 130, 100, 50);
        sleepLvlField.setText("Sen: " + lvl_Sleep);
        sleepLvlField.setFont(new Font("Cambria", Font.BOLD, 13));

        add(eatLvlField);
        add(funLvlField);
        add(sleepLvlField);
    }

    public void showInformation() {
        System.out.println("==============================");
        System.out.println("Jedzenie: " + lvl_Eat);
        System.out.println("Zabawa: " + lvl_Fun);
        System.out.println("Spanie: " + lvl_Sleep);
        System.out.println("==============================");
    }

    protected int getLvl() {
        return Math.min(lvl_Eat, Math.min(lvl_Sleep, lvl_Fun));
    }

    public class GameWindowController extends JPanel {

        public GameWindowController() {
            setLayout(null);
            addButtons();
            addLifeInformation();
        }

        private void addButtons() {
            try {
                JButton eatButton = new JButton("JEDZENIE");
                eatButton.setBounds(110, 295, 90, 45);
                eatButton.setBackground(Color.YELLOW);
                eatButton.setFont(new Font("Cambria", Font.BOLD, 13));
                eatButton.setForeground(Color.BLUE);
                eatButton.setBorderPainted(false);
                eatButton.addActionListener(e -> {
                    if (lvl_Eat < maxLvl) {
                        lvl_Eat++;
                        eatLvlField.setText("Jedzenie: " + lvl_Eat);
                        eatLvlField.setVisible(true);
                    }
                    repaint();
                });

                JButton funButton = new JButton("ZABAWA");
                funButton.setBounds(180, 345, 90, 45);
                funButton.setBackground(Color.YELLOW);
                funButton.setFont(new Font("Cambria", Font.BOLD, 13));
                funButton.setForeground(Color.BLUE);
                funButton.setBorderPainted(false);
                funButton.addActionListener( e -> {
                    if (lvl_Fun < maxLvl) {
                        lvl_Fun++;
                        funLvlField.setText("Zabawa: " + lvl_Fun);
                        funLvlField.setVisible(true);
                    }
                    repaint();
                });

                JButton sleepButton = new JButton("SEN");
                sleepButton.setBounds(208, 295, 90, 45);
                sleepButton.setBackground(Color.YELLOW);
                sleepButton.setFont(new Font("Cambria", Font.BOLD, 13));
                sleepButton.setForeground(Color.BLUE);
                sleepButton.setBorderPainted(false);
                sleepButton.addActionListener(e -> {
                    if (lvl_Sleep < maxLvl) {
                        lvl_Sleep++;
                        sleepLvlField.setText("Sen: " + lvl_Sleep);
                        sleepLvlField.setVisible(true);
                    }
                    repaint();
                });

                add(eatButton);
                add(funButton);
                add(sleepButton);

            } catch (Exception e) {
                System.out.println("Ścieżka jest niepoprawna! " + e);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(650, 450);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            BufferedImage picture;

            try {
                picture = ImageIO.read(new File("pictures/" + getLvl() + ".jpg"));
                g.drawImage(picture, 0, 0, this);
            } catch (Exception e) {
                System.out.println("Ścieżka obrazka nie jest poprawna! " + e);
            }
        }
    }

    public GameController(String title) {
        super(title);

        GameWindowController gameWindow = new GameWindowController();
        add(gameWindow);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();

        Thread gameLogic = new Thread(() -> {
            boolean ifLoose = false;

            while (!ifLoose) {
                try {
                    Thread.sleep(1000);
                    int lifeLvl = new Random().nextInt(3) + 1;
                    if (lifeLvl == 1) {
                        lvl_Eat--;
                        eatLvlField.setText("Jedzenie: " + lvl_Eat);
                        eatLvlField.setVisible(true);
                        repaint();
                    }
                    if (lifeLvl == 2) {
                        lvl_Fun--;
                        funLvlField.setText("Zabawa: " + lvl_Fun);
                        funLvlField.setVisible(true);
                        repaint();
                    }
                    if (lifeLvl == 3) {
                        lvl_Sleep--;
                        sleepLvlField.setText("Sen: " + lvl_Sleep);
                        sleepLvlField.setVisible(true);
                        repaint();
                    }

                    showInformation();
                    addLifeInformation();

                    if (getLvl() > 0) {
                        repaint();
                    } else {
                        ifLoose = true;

                        BorderLayout borderLayout = new BorderLayout();
                        borderLayout.setHgap(10);
                        borderLayout.setVgap(10);

                        JFrame exitFrame = new JFrame();
                        exitFrame.setLayout(borderLayout);
                        exitFrame.setTitle("Wyjście z gry");
                        exitFrame.setSize(350, 200);
                        exitFrame.setBackground(Color.YELLOW);
                        JLabel exitLabel = new JLabel("  KONIEC GRY!!!");
                        JButton exitButton = new JButton("Wyjście");
                        JButton noButton = new JButton("Nowa Gra");

                        exitLabel.setFont(new Font("Cambria", Font.BOLD, 17));
                        exitLabel.setForeground(Color.BLUE);

                        exitButton.addActionListener(e -> {
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                            System.exit(0);
                        });
                        exitButton.setBackground(Color.ORANGE);
                        exitButton.setFont(new Font("Cambria", Font.BOLD, 15));
                        exitButton.setForeground(Color.BLUE);

                        noButton.addActionListener(e ->{
                            exitFrame.setVisible(false);
                            gameWindow.repaint();
                            GameController gameController = new GameController("TamagotchiNewGame");
                            gameController.add(gameWindow);
                        } );
                        noButton.setBackground(Color.ORANGE);
                        noButton.setFont(new Font("Cambria", Font.BOLD, 15));
                        noButton.setForeground(Color.BLUE);

                        exitFrame.add(exitLabel, BorderLayout.CENTER);
                        exitFrame.add(exitButton, BorderLayout.EAST);
                        exitFrame.add(noButton, BorderLayout.WEST);
                        exitFrame.setVisible(true);
                    }
                } catch (Exception e) {
                    System.out.println("Czas dostępu do wątku jest niewłaściwy! " +  e);
                }
            }
        });
        gameLogic.start();
    }
}