package pt.isec.pa.tinypac.model.data.maze;

import pt.isec.pa.tinypac.model.data.*;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MazeControl implements Serializable {
    private Maze maze;
    private int width;
    private int height;
    private List<Position>ghostStartPositions ;
    private Position fruitPosition;
    private Position ghostGate;
    private Position pacManStart;
    private final Map<Integer, String> levels = new HashMap<>();
    private int totalPoints = 0;

    public MazeControl(int level) {
        ghostStartPositions = new ArrayList<>();
        putLevelsFile();
        File arquivo = new File("levels\\"+ getLevelFile(level));
        while(!arquivo.exists()){
            arquivo = new File("levels\\" + getLevelFile(level - 1));
        }
        // Criar um para ler o conteúdo do arquivo
        Scanner leitor = null;
        try {
            leitor = new Scanner(arquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Ler o conteúdo do arquivo linha por linha
        this.width = 0;
        this.height = 0;
        while (leitor.hasNextLine()) {
            String line = leitor.nextLine();
            this.height++;
            this.width = line.length();
        }
        leitor.close();

        this.maze = new Maze(this.height, this.width);

        try {
            leitor = new Scanner(arquivo);
            // Ler o conteúdo do arquivo linha por linha
            int row = 0;
            while (leitor.hasNextLine()) {
                String line = leitor.nextLine();
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    switch (c) {
                        case 'x' -> {
                            maze.set(row, col, new Wall());
                        }
                        case 'o' -> {
                            maze.set(row, col, new Point());
                            totalPoints++;
                        }
                        case 'F' -> {
                            maze.set(row, col, new Fruit());
                            fruitPosition = new Position(row, col);
                            totalPoints++;
                        }
                        case 'O' -> {
                            maze.set(row, col, new PowerPoint());
                            totalPoints++;
                        }
                        case 'Y' -> {
                            maze.set(row, col, new GhostGate());
                            ghostGate = new Position(row, col);
                        }
                        case 'y' -> {
                            ghostStartPositions.add(new Position(row, col));
                            maze.set(row, col, new GhostSpot());
                        }
                        case 'W' ->{
                            maze.set(row, col, new WarpZone());
                        }
                        case 'M' -> {
                            maze.set(row, col, new PacManStart());
                            pacManStart = new Position(row, col);
                        }
                    }
                }
                row++;
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado.");
            e.printStackTrace();
        }
        System.out.println("total: " + totalPoints);
    }

    public void putLevelsFile(){
        levels.put(1, "Level101.txt");
        levels.put(2, "Level102.txt");
        levels.put(3, "Level103.txt");
        levels.put(4, "Level104.txt");
        levels.put(5, "Level105.txt");
        levels.put(6, "Level106.txt");
        levels.put(7, "Level107.txt");
        levels.put(8, "Level108.txt");
        levels.put(9, "Level109.txt");
        levels.put(10, "Level110.txt");
        levels.put(11, "Level111.txt");
        levels.put(12, "Level112.txt");
        levels.put(13, "Level113.txt");
        levels.put(14, "Level114.txt");
        levels.put(15, "Level115.txt");
        levels.put(16, "Level116.txt");
        levels.put(17, "Level117.txt");
        levels.put(18, "Level118.txt");
        levels.put(19, "Level119.txt");
        levels.put(20, "Level120.txt");
    }

    public String getLevelFile(int i){
        return levels.get(i);
    }

    public Position getPacManStart(){
        return pacManStart;
    }

    public Position getGhostGate(){
        return ghostGate;
    }

    public Position getPacManPosition() {
        Position p = null;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ;  j < width ; j++){
                if(maze.get(i, j) instanceof PacMan)
                    p = new Position(i, j);
            }
        }
        return p;
    }

    public void remove(int x, int y) {
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if((maze.get(i, j) instanceof WarpZone)){
                    maze.set(i, j, new WarpZone());
                }else if(maze.get(i, j) instanceof GhostGate){
                    maze.set(i, j, new GhostGate());
                }else if((i == x &&  j == y ) && !(maze.get(i, j) instanceof WarpZone) && !(maze.get(i, j) instanceof GhostGate)) {
                    maze.set(i, j, new EmptyZone());
                }
            }
        }
    }

    public boolean checkWin() {
        int count = 0;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if(maze.get(i, j) instanceof EmptyZone){
                    count++;
                }
            }
        }
        return count >= totalPoints;
    }

    public boolean checkIfWall(int x, int y) {
        return maze.get(x,y) instanceof Wall;
    }

    public boolean checkIfPoint(int x, int y){
        return maze.get(x,y) instanceof Point;
    }

    public boolean checkIfFruit(int x, int y){
        return maze.get(x,y) instanceof Fruit;
    }

    public boolean checkIfPower(int x, int y){
        return maze.get(x,y) instanceof PowerPoint;
    }

    public boolean checkIfWarp(int x, int y){
        return maze.get(x,y) instanceof WarpZone;
    }

    public boolean chekIfGhost(int x, int y){
        return ((maze.get(x, y) instanceof Blinky)
                || (maze.get(x, y) instanceof Pinky)
                || (maze.get(x, y) instanceof Clyde)
                || (maze.get(x, y) instanceof Inky));
    }

    public char [][] getMazeControl(){
        return maze.getMaze();
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void setXY(int x, int y, IMazeElement element) {
        maze.set(x, y, element);
    }

    public IMazeElement getXY(int x, int y) {
        return maze.get(x,y);
    }

    public List<Position> getGhostStartPositions(){
        return ghostStartPositions;
    }

    public boolean checkIfWallGhost(int x, int y) {
        if(maze.get(x, y) instanceof WarpZone || maze.get(x, y) instanceof Wall)
            return true;
        else
            return false;
    }

    public void removeGhost(int x, int y) {
        maze.set(x, y, new EmptyZone());
    }

    public void setNewFruit() {
        maze.set(fruitPosition.getX(), fruitPosition.getY(), new Fruit());
    }

    public int getTotalPoints(){
        return totalPoints;
    }

}
