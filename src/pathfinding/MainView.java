package pathfinding;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
    
    private final int gridWidth;
    private final int gridHeight;
    
    private final Affine affine;
    private final Button runBtn;
    private final Canvas canvas;
    
    public MainView(int width, int height) {
        this.gridWidth = 64;
        this.gridHeight = 36;
        
        this.runBtn = new Button("RUN");
        this.canvas = new Canvas(width, height);
        
        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / this.gridWidth, this.canvas.getHeight() / this.gridHeight);
        
        this.getChildren().addAll(this.runBtn, this.canvas);
        
        Pathfinder pathfinder = new Pathfinder(this.gridWidth, this.gridHeight);
        pathfinder.maze = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        pathfinder.startNode = new Node(1, 1);
        pathfinder.endNode = new Node(8, 8);    
        pathfinder.printMaze();
    }
    
    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,this.gridWidth,this.gridHeight);
        
        g.setStroke(Color.BLACK);
        g.setLineWidth(0.05f);
        for (int i = 0; i < this.gridWidth; i++) {
            g.strokeLine(i, 0d, i, this.gridHeight);
        }
        for (int i = 0; i < this.gridHeight; i++) {
            g.strokeLine(0, i, this.gridWidth, i);
        }
    }
    
    public int getCanvasWidth() {
        return (int) this.canvas.getWidth();
    }
    
    public int getCanvasHeight() {
        return (int) this.canvas.getHeight();
    }
    
}