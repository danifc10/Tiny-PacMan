package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MazeControl {
    private final Maze maze;
    //private final IMazeElement[][] board;
    private int width;
    private int height;
    private List<Position>ghostStartPositions = new ArrayList<>();
    private Map<Integer, String> levels = new HashMap<>();

    public MazeControl(String File) throws FileNotFoundException {
        putLevelsFile();
        File arquivo = new File("src\\levels\\" + File );
        // Criar um objeto Scanner para ler o conteúdo do arquivo
        Scanner leitor = new Scanner(arquivo);
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
        //this.board = new IMazeElement[rows][cols];
        try {
            // Criar um objeto Scanner para ler o conteúdo do arquivo
            Scanner leitor2 = new Scanner(arquivo);
            // Ler o conteúdo do arquivo linha por linha
            int row = 0;
            while (leitor2.hasNextLine()) {
                String line = leitor2.nextLine();
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    switch (c) {
                        case 'x' -> {
                            maze.set(row, col, new Wall());
                            //this.board[row][col] = new Wall();
                        }
                        case 'o' -> {
                            maze.set(row, col, new Point());
                            //this.board[row][col] = new Point();
                        }
                        case 'F' -> {
                            maze.set(row, col, new Fruit());
                            //this.board[row][col] = new Fruit();
                        }
                        case 'O' -> {
                            maze.set(row, col, new PowerPoint());
                            //this.board[row][col] = new PowerPoint();
                        }
                        case 'Y' -> {
                            maze.set(row, col, new GhostGate());
                            //this.board[row][col] = new GhostGate();
                        }
                        case 'y' -> {
                            ghostStartPositions.add(new Position(row, col));
                            maze.set(row, col, new GhostSpot());
                            //this.board[row][col] = new GhostSpot();
                        }
                        case 'W' ->{
                            maze.set(row, col, new WrapZone());
                            //this.board[row][col] = new WrapZone();
                        }
                        case 'M' -> {
                            maze.set(row, col, new PacManStart());
                            //this.board[row][col] = new PacManStart();
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

    public void putLevelsFile(){
        levels.put(1, "Level101.txt");
        levels.put(2, "Level102.txt");
    }

    public String getLevelFile(int i){
        return levels.get(i);
    }

    public int getXstart(){
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if((maze.get(i,j).getSymbol())== 'M')
                    return i;
            }
        }
        return 0;
    }

    public int getYstart(){
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if(maze.get(i,j).getSymbol() == 'M')
                    return j;
            }
        }
        return 0;
    }

    public int getYghostStart(){
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if(maze.get(i,j).getSymbol()== 'Y')
                    return j;
            }
        }
        return 0;
    }

    public int getXghostStart(){
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if(maze.get(i,j).getSymbol()== 'Y')
                    return i;
            }
        }
        return 0;
    }

    public void remove(int x, int y) {
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if((maze.get(i, j).getSymbol() == 'W')){
                    maze.set(i, j, new WrapZone());
                }else if(maze.get(i, j).getSymbol() == 'Y'){
                    maze.set(i, j, new GhostGate());
                }else if((i == x &&  j == y ) && (maze.get(i, j).getSymbol() != 'W') && (maze.get(i, j).getSymbol() != 'Y')) {
                    maze.set(i, j, new EmptyZone());
                }
            }
        }
    }

    public void removeGhostRoad(int x, int y) {
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if((i == x &&  j == y ) && (maze.get(i, j).getSymbol() == 'W')){
                    maze.set(i, j, new WrapZone());
                }else if((i == x &&  j == y ) && (maze.get(i, j).getSymbol() == 'Y')){
                    maze.set(i, j, new GhostGate());
                }else if((i == x &&  j == y ) && maze.get(i, j).getSymbol()== 'o') {
                    maze.set(i, j, new Point());
                }else if((i == x &&  j == y ) && maze.get(i, j).getSymbol()== 'O') {
                    maze.set(i, j, new PowerPoint());
                }else if((i == x &&  j == y ) && maze.get(i, j).getSymbol()== 'F') {
                    maze.set(i, j, new Fruit());
                }else if((i == x &&  j == y ) ){
                    maze.set(i, j, new EmptyZone());
                }
            }
        }
    }

    public boolean checkWin() {
        int count = 0;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                if(maze.get(i, j).getSymbol() == ' '){
                    count++;
                }
            }
        }
        if(count == (this.width * this.height))
            return true;
        else
            return false;
    }

    public boolean checkIfWall(int x, int y) {
        return maze.get(x,y).getSymbol() == 'x';
    }

    public boolean checkIfPoint(int x, int y){
        return maze.get(x,y).getSymbol() == 'o';
    }

    public boolean checkIfFruit(int x, int y){
        return maze.get(x,y).getSymbol() == 'F';
    }

    public boolean checkIfPower(int x, int y){
        return maze.get(x,y).getSymbol() == 'O';
    }

    public boolean checkIfWrap(int x, int y){
        return maze.get(x,y).getSymbol() == 'W';
    }

    public boolean chekIfGhost(int x, int y){
        return maze.get(x,y).getSymbol() == 'G';
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

}
