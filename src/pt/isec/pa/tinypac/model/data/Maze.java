package pt.isec.pa.tinypac.model.data;

import java.io.*;
import java.util.Scanner;
public final class Maze {
    private final int width;
    private final int height;
    private final IMazeElement[][] board;
    public Maze(int height, int width, String File) {
        this.width = width;
        this.height = height;
        this.board = new IMazeElement[height][width];
        File arquivo = new File("src/pt/isec/pa/tinypac/model/data/Level101.txt");
        try {
            // Criar um objeto Scanner para ler o conteúdo do arquivo
            Scanner leitor = new Scanner(arquivo);
            // Ler o conteúdo do arquivo linha por linha
            int row = 0;
            while (leitor.hasNextLine()) {
                String line = leitor.nextLine();
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    switch (c) {
                        case 'x' -> this.board[row][col] = new Wall();
                        case 'o' -> this.board[row][col] = new Point();
                        case 'F' -> this.board[row][col] = new Fruit();
                        case 'O' -> this.board[row][col] = new PowerPoint();
                        case 'Y' -> this.board[row][col] = new GhostGate();
                        case 'y' -> this.board[row][col] = new GhostSpot();
                        case 'W' -> this.board[row][col] = new WrapZone();
                        case 'M' -> this.board[row][col] = new PacManStart();
                    }
                }
                row++;
            }
            // Fechar o objeto Scanner
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado.");
            e.printStackTrace();
        }
    }

    public void set(int y, int x, IMazeElement element) {
        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length)
            return;
        board[y][x] = element; // can be null
    }

    public IMazeElement get(int y, int x) {
        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length)
            return null;
        return board[y][x]; // can be null
    }

    public char[][] getMaze() {
        char[][] char_board = new char[board.length][board[0].length];
        for(int y=0;y<board.length;y++)
            for(int x=0;x<board[y].length;x++)
                if (board[y][x]==null)
                    char_board[y][x] = ' ';
                else
                    char_board[y][x] = board[y][x].getSymbol();
        return char_board;
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}