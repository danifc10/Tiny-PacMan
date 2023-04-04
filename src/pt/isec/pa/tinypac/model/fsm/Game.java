package pt.isec.pa.tinypac.model.fsm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener {

    public static final int WIDTH = 800; // largura da janela
    public static final int HEIGHT = 600; // altura da janela
    public static final int TILE_SIZE = 20; // tamanho do tile
    public static final int ROWS = HEIGHT / TILE_SIZE; // número de linhas
    public static final int COLS = WIDTH / TILE_SIZE; // número de colunas

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private PacMan pacMan; // instância do Pac-Man
    private Ghost[] ghosts; // array de instâncias dos fantasmas

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        pacMan = new PacMan(WIDTH / 2, HEIGHT / 2, LEFT, 5);
        ghosts = new Ghost[]{
                new Blinky(0, 0, RIGHT),
                new Pinky(WIDTH - TILE_SIZE, 0, UP),
                new Inky(0, HEIGHT - TILE_SIZE, DOWN),
                new Clyde(WIDTH - TILE_SIZE, HEIGHT - TILE_SIZE, LEFT)
        };
    }

    public void start() {
        // inicia o loop do jogo
        while (true) {
            update();
            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // atualiza a posição do Pac-Man
        pacMan.move();
/*
        // atualiza a posição dos fantasmas
        for (Ghost ghost : ghosts) {
            ghost.move();
        }*/
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // desenha o Pac-Man
        g.setColor(Color.YELLOW);
        g.fillOval(pacMan.getX(), pacMan.getY(), TILE_SIZE, TILE_SIZE);

        // desenha os fantasmas
        g.setColor(Color.RED);
        for (Ghost ghost : ghosts) {
            g.fillRect(ghost.getX(), ghost.getY(), TILE_SIZE, TILE_SIZE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // muda a direção do Pac-Man de acordo com a tecla pressionada
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                pacMan.setDirection(LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                pacMan.setDirection(RIGHT);
                break;
            case KeyEvent.VK_UP:
                pacMan.setDirection(UP);
                break;
            case KeyEvent.VK_DOWN:
                pacMan.setDirection(DOWN);
                break;
        }
    }

    // métodos não utilizados
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Game());
        frame.pack();
    }
}