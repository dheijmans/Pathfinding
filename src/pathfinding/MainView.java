package pathfinding;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import static pathfinding.Pathfinder.BLOCKED;
import static pathfinding.Pathfinder.UNBLOCKED;

public class MainView extends VBox {
    
    private final int gridWidth;
    private final int gridHeight;
    private final int padding = 1;
    
    private final Affine affine;
    private final Button runBtn;
    private final Canvas canvas;
    
    private Pathfinder pf;
    
    
    
    public MainView(int width, int height) {
        this.gridWidth = 64;
        this.gridHeight = 36;
        
        this.runBtn = new Button("RUN");
        this.canvas = new Canvas(width, height);
        
        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / (this.gridWidth + 2 * this.padding), this.canvas.getHeight() / (this.gridHeight + 2 * this.padding));
        this.affine.appendTranslation(this.padding, this.padding);
        
        this.getChildren().addAll(this.canvas);
        
        this.pf = new Pathfinder(this.gridWidth, this.gridHeight);
        pf.maze = new int[][] {
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
        this.pf.startNode = new Node(1, 1);
        this.pf.endNode = new Node(8, 8);    
        this.pf.printMaze();
        this.pf.aStar();
    }
    
    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,this.gridWidth,this.gridHeight);
        
        for (int y = 0; y < this.pf.maze.length; y++) {
            for (int x = 0; x < this.pf.maze[0].length; x++) {
                if (this.pf.maze[y][x] == UNBLOCKED) {
                    if (this.pf.startNode.isSameNodeAs(x, y)) {
                        g.setFill(Color.WHEAT);
                        g.fillRect(x, y, 1, 1);
                    } else if (this.pf.endNode.isSameNodeAs(x, y)) {
                        g.setFill(Color.ORANGE);
                        g.fillRect(x, y, 1, 1);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getPath())) {
                        g.setFill(Color.AQUA);
                        g.fillRect(x, y, 1, 1);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getClosed())) {
                        g.setFill(Color.RED);
                        g.fillRect(x, y, 1, 1);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getOpen())) {
                        g.setFill(Color.GREEN);
                        g.fillRect(x, y, 1, 1);
                    }
                } else if (this.pf.maze[y][x] == BLOCKED) {
                    g.setFill(Color.DIMGRAY);
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        
        g.setStroke(Color.BLACK);
        g.setLineWidth(0.05f);
        for (int i = 0; i < this.gridWidth + 1; i++) {
            g.strokeLine(i, 0d, i, this.gridHeight);
        }
        for (int i = 0; i < this.gridHeight + 1; i++) {
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