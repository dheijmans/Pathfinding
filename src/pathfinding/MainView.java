package pathfinding;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
    
    private final int width, height;    
    private final int gridWidth, gridHeight;
    private final int padding = 1;
    
    private final Affine affine;
    private final Toolbar toolbar;
    private final Canvas canvas;
    private final Pathfinder pf;
    
    public MainView() {
        this.width = 1600;
        this.height = 900;
        this.gridWidth = 64;
        this.gridHeight = 36;
        
        this.toolbar = new Toolbar(this);   
        this.canvas = new Canvas(this.width, this.height);
        
        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / (this.gridWidth + 2 * this.padding), this.canvas.getHeight() / (this.gridHeight + 2 * this.padding));
        this.affine.appendTranslation(this.padding, this.padding);
        
        this.pf = new Pathfinder(this.gridWidth, this.gridHeight, this);
        this.pf.startNode = new Node(3, 6);
        this.pf.endNode = new Node(57, 28);
       
        this.getChildren().addAll(this.toolbar, this.canvas);
        
        this.canvas.setOnMouseClicked(event -> {
            int mouseX = (int) Math.floor(event.getX());
            int mouseY = (int) Math.floor(event.getY());
            this.pf.maze[mouseY][mouseX] = 1;
            draw();
        });
    }
    
    
    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, this.gridWidth, this.gridHeight);

        for (int y = 0; y < this.pf.maze.length; y++) {
            for (int x = 0; x < this.pf.maze[0].length; x++) {
                if (this.pf.maze[y][x] == Pathfinder.UNBLOCKED) {
                    
                    if(this.pf.startNode == null) {
                        continue;
                    } else if (this.pf.startNode.isSameNodeAs(x, y)) {
                        gc.setFill(Color.GOLD);
                    } else if(this.pf.endNode == null ) {
                        continue;
                    } else if (this.pf.endNode.isSameNodeAs(x, y)) {
                        gc.setFill(Color.DARKORCHID);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getPath())) {
                        gc.setFill(Color.DEEPSKYBLUE);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getOpen())) {
                        gc.setFill(Color.LIME);
                    } else if (Pathfinder.isInList(new Node(x, y), this.pf.getClosed())) {
                        gc.setFill(Color.RED);
                    } else {
                        continue;
                    }
                    gc.fillRect(x, y, 1, 1);
                } else if (this.pf.maze[y][x] == Pathfinder.BLOCKED) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x, y, 1, 1);
                }
            }
        }
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.03d);
        for (int i = 0; i < this.gridWidth + 1; i++) {
            gc.strokeLine(i, 0, i, this.gridHeight);
        }
        for (int i = 0; i < this.gridHeight + 1; i++) {
            gc.strokeLine(0, i, this.gridWidth, i);
        }
    }
    
    public Pathfinder getPathfinder() {
        return this.pf;
    }
    
}