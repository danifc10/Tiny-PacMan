package pt.isec.pa.tinypac.model.data.maze;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
/**
 * class for maze manage
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
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
    /**
     * Default constructor
     * @param level actual level
     */
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
    }

    /**
     * Function to connect the name of file to a level
     *
     */
    public void putLevelsFile(){
        levels.put(1, "Level01.txt");
        levels.put(2, "Level02.txt");
        levels.put(3, "Level03.txt");
        levels.put(4, "Level04.txt");
        levels.put(5, "Level05.txt");
        levels.put(6, "Level06.txt");
        levels.put(7, "Level07.txt");
        levels.put(8, "Level08.txt");
        levels.put(9, "Level09.txt");
        levels.put(10, "Level10.txt");
        levels.put(11, "Level11.txt");
        levels.put(12, "Level12.txt");
        levels.put(13, "Level13.txt");
        levels.put(14, "Level14.txt");
        levels.put(15, "Level15.txt");
        levels.put(16, "Level16.txt");
        levels.put(17, "Level17.txt");
        levels.put(18, "Level18.txt");
        levels.put(19, "Level19.txt");
        levels.put(20, "Level20.txt");
    }

    /**
     * Get the name of the file from this level
     * @param i level
     */
    public String getLevelFile(int i){
        return levels.get(i);
    }

    /**
     * Get PacMan start position
     * @return pacManStart PacMan start position 'M'
     */
    public Position getPacManStart(){
        return pacManStart;
    }
    /**
     * Get Ghost gate position
     * @return ghostGate Ghost gate position 'Y'
     */
    public Position getGhostGate(){
        return ghostGate;
    }
    /**
     * Get PacMan current position
     * @return p PacMan current position
     */
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

    /**
     * Remove trace of ghost walk
     * @param (x,y) Ghost position
     */
    public void remove(int x, int y) {
        IMazeElement e = maze.get(x, y);
        if (e instanceof Point)
            maze.set(x, y, new Point());
        else if (e instanceof PowerPoint)
            maze.set(x, y, new PowerPoint());
        else if (e instanceof Fruit)
            maze.set(x, y, new Fruit());
        else if (e instanceof WarpZone)
            maze.set(x, y, new WarpZone());
        else
            maze.set(x, y, new EmptyZone());
    }

    /**
     * Function to check if is wall for PacMan
     * @param (x,y) position to check
     * @return boolean - true if is wall false if not
     */
    public boolean checkIfWall(int x, int y) {
        return maze.get(x,y) instanceof Wall;
    }
    /**
     * Function to get the maze board
     * @return  maze.getMaze() maze
     */
    public char [][] getMazeControl(){
        return maze.getMaze();
    }
    /**
     * Get board height
     * @return height
     */
    public int getHeight(){
        return this.height;
    }
    /**
     * Get board width
     * @return width
     */
    public int getWidth(){
        return this.width;
    }
    /**
     * Set a new element in a position
     * @param (x,y, element) position to set the element and the element to set
     */
    public void setXY(int x, int y, IMazeElement element) {
        maze.set(x, y, element);
    }
    /**
     * Get the element in that position
     * @param (x,y) position to get
     * @return maze.get(x,y) element
     */
    public IMazeElement getXY(int x, int y) {
        return maze.get(x,y);
    }
    /**
     * Get the array list of all ghost start position
     * @return ghostStartPositions array list of all ghost start position
     */
    public List<Position> getGhostStartPositions(){
        return ghostStartPositions;
    }
    /**
     * Function to check if is wall for ghosts
     * @param (x,y) position to check
     * @return boolean - true if is wall false if not
     */
    public boolean checkIfWallGhost(int x, int y) {
        return maze.get(x, y) instanceof WarpZone || maze.get(x, y) instanceof Wall;
    }

    /**
     * Function to check if ghost can walk for that position
     * @param (x,y) position to check
     * @return boolean - true if can false if not
     */
    public boolean checkIfWalk(int x, int y){
        return(
                !((maze.get(x,y) instanceof Wall)) &&
                !((maze.get(x,y) instanceof WarpZone)) &&
                !((maze.get(x, y) instanceof GhostGate)) &&
                !((maze.get(x, y) instanceof GhostSpot))
        );
    }

    /**
     * Function to remove a ghost
     * @param (x,y) ghost position
     *
     */
    public void removeGhost(int x, int y) {
        maze.set(x, y, new EmptyZone());
    }

    /**
     * Function to set a new fruit in fruit position
     *
     */
    public void setNewFruit() {
        maze.set(fruitPosition.getX(), fruitPosition.getY(), new Fruit());
    }

    /**
     * Get the max points player can reach in this board
     * @return totalPoints max points player can reach in this board
     */
    public int getTotalPoints(){
        return totalPoints;
    }

    public int checkWin(){
        int count = 0;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ;  j < width ; j++){
                if(maze.get(i, j) instanceof EmptyZone)
                    count++;
            }
        }
        return count;
    }

}
