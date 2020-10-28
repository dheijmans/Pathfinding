package pathfinding;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
    
    public int mode;
    public static final int EDITING = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int RESULTS = 3;
    
    private KeyCode pressedKey;
    private Node hoveredNode;
        
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
        
        this.mode = EDITING;
        
        this.toolbar = new Toolbar(this);   
        this.canvas = new Canvas(this.width, this.height);
        
        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / (this.gridWidth + 2 * this.padding), 
        this.canvas.getHeight() / (this.gridHeight + 2 * this.padding));
        this.affine.appendTranslation(this.padding, this.padding);
        
        this.pf = new Pathfinder(this.gridWidth, this.gridHeight, this);
        this.pf.startNode = new Node(3, 3);
        this.pf.endNode = new Node(60, 32);
       
        this.getChildren().addAll(this.toolbar, this.canvas);
        
        this.canvas.setOnMousePressed(this::handleMouse);
        this.canvas.setOnMouseDragged(this::handleMouse);
        this.canvas.setOnMouseMoved(this::handleMouse);
        
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);
    }
    
    private void handleMouse(MouseEvent event) {
        this.hoveredNode = null;
        try {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Point2D mouse = this.affine.inverseTransform(mouseX, mouseY);
            //Checks if mouse is on grid or not
            if (this.pf.isOnGrid((int) Math.floor(mouse.getX()), (int) Math.floor(mouse.getY()))) { 
                Node n = new Node((int) mouse.getX(), (int) mouse.getY());
                this.hoveredNode = n;
                if (this.mode == EDITING) {
                    //Prevents from drawing over startnode and endnode
                    if (n.isSameNodeAs(this.pf.startNode) || n.isSameNodeAs(this.pf.endNode)) {
                       return;
                    }
                    if (event.getButton() == MouseButton.PRIMARY) {  
                        if (this.pressedKey == KeyCode.S) {
                            this.pf.maze[n.getY()][n.getX()] = Pathfinder.UNBLOCKED;
                            this.pf.startNode = n;
                        } else if (this.pressedKey == KeyCode.E) {
                            this.pf.maze[n.getY()][n.getX()] = Pathfinder.UNBLOCKED;
                            this.pf.endNode = n;
                        } else {
                            this.pf.maze[n.getY()][n.getX()] = Pathfinder.BLOCKED;
                        }  
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        this.pf.maze[n.getY()][n.getX()] = Pathfinder.UNBLOCKED;
                    }
                }
            }
        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
        }
        draw();
    }
    
    private void handleKeyPressed(KeyEvent event) {
        this.pressedKey = event.getCode();
    }
    
    private void handleKeyReleased(KeyEvent event) {
        if(this.pressedKey == event.getCode()) {
            this.pressedKey = null;                   
        }
    }
    
    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, this.gridWidth, this.gridHeight);

        for (int y = 0; y < this.pf.maze.length; y++) {
            for (int x = 0; x < this.pf.maze[0].length; x++) {
                if (this.pf.maze[y][x] == Pathfinder.UNBLOCKED) {
                    Node n = new Node(x, y);
                    if (n.isSameNodeAs(this.pf.startNode)) {
                        gc.setFill(Color.GOLD);
                    } else if (n.isSameNodeAs(this.pf.endNode)) {
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
        
        if (this.hoveredNode != null) {
            gc.setFill(Color.rgb(0, 0, 0, 0.2d));
            gc.fillRect(this.hoveredNode.getX(), this.hoveredNode.getY(), 1, 1);
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
    
    public Toolbar getToolbar() {
        return this.toolbar;
    }
    
}