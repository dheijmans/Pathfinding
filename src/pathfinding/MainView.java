package pathfinding;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

// once again just importing the functions

public class MainView extends VBox {
    
    public int mode;
    public static final int EDITING = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int RESULTS = 3;
    // sets values for the mode the program is in
    
    private KeyCode pressedKey;
    private Node hoveredNode;
    // values for if a key is pressed and if something hovers on a certain position
        
    private final int width, height;    
    private final int gridWidth, gridHeight;
    private final double padding = 0.5;
    
    // sets width and height and padding
    
    private final Affine affine;
    private final Toolbar toolbar;
    private final Canvas canvas;
    private final Pathfinder pf;
    private final Infobar infobar;
    private final Sidebar sidebar;
    // constructor mainview class
    
    public MainView() {
        this.width = 1600;
        this.height = 900;
        this.gridWidth = 64;
        this.gridHeight = 36;
        // stes widht and height for the window and for the grid
        
        BorderPane root = new BorderPane();
                
        this.toolbar = new Toolbar(this);   
        root.setTop(this.toolbar);
 
        this.canvas = new Canvas(this.width, this.height);
        root.setCenter(this.canvas);
        BorderPane.setMargin(this.canvas, new Insets(0, 0, 0, 0));
        
        this.sidebar = new Sidebar(this);
        this.sidebar.setMinWidth(250d);
        this.sidebar.setPadding(new Insets(12, 0, 0, 0));
        root.setRight(this.sidebar);
        // sets a minimal size for the sidebar
        
        this.infobar = new Infobar();
        this.infobar.setMaxWidth(1600d);
        this.infobar.setPadding(new Insets(0, 12, 0, 12));
        root.setBottom(this.infobar);
        // sets max width for infobar 
        
        
        setMode(MainView.EDITING);

        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / (this.gridWidth + 2 * this.padding), 
        this.canvas.getHeight() / (this.gridHeight + 2 * this.padding));
        this.affine.appendTranslation(this.padding, this.padding);
        
        this.pf = new Pathfinder(this.gridWidth, this.gridHeight, this);
        this.pf.startNode = new Node(3, 3);
        this.pf.endNode = new Node(60, 32);
        
        getSidebar().setStartNode(this.pf.startNode);
        getSidebar().setEndNode(this.pf.endNode);
       
        this.getChildren().addAll(root);
        
        this.canvas.setOnMousePressed(this::handleMouse);
        this.canvas.setOnMouseDragged(this::handleMouse);
        this.canvas.setOnMouseMoved(this::handleMouse);
        
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);
        // enables all functions for the editing mode such as placing start / end node
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
                this.infobar.SetCursor(n.getX(), n.getY());
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
                            getSidebar().setStartNode(n);
                        } else if (this.pressedKey == KeyCode.E) {
                            this.pf.maze[n.getY()][n.getX()] = Pathfinder.UNBLOCKED;
                            this.pf.endNode = n;
                            getSidebar().setEndNode(n);
                        } else {
                            if (this.pf.maze[n.getY()][n.getX()] == Pathfinder.UNBLOCKED) {
                                this.pf.maze[n.getY()][n.getX()] = Pathfinder.BLOCKED;
                                getSidebar().addBlockedNode();
                            }
                            
                        }  
                        // allows you to set nodes to certain states (bocked, unblocked, start / node, with primary mouse during editing mode
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        if (this.pf.maze[n.getY()][n.getX()] == Pathfinder.BLOCKED) {
                            this.pf.maze[n.getY()][n.getX()] = Pathfinder.UNBLOCKED;
                        getSidebar().removeBlockedNode();
                        }
                        // lets you remove blocked nodes by using the secondary mouse buttong
                    }
                }
            }
        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
            // when error between mousx&y to gridx&y gives error message
        }
        draw();
    }
    
    private void handleKeyPressed(KeyEvent event) {
        this.pressedKey = event.getCode();
        // checks if a certain key is pressed
    }
    
    private void handleKeyReleased(KeyEvent event) {
        if(this.pressedKey == event.getCode()) {
            this.pressedKey = null;                   
        }
        // checks if the node is released
    }
    
    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, this.gridWidth, this.gridHeight);
        // colouring and sizing of the grid

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
            // sets colours for the nodes (start, end, block / not blocked, open, path, closed
        }
        
        if (this.hoveredNode != null) {
            gc.setFill(Color.rgb(0, 0, 0, 0.2d));
            gc.fillRect(this.hoveredNode.getX(), this.hoveredNode.getY(), 1, 1);
            // sets a light colour for the node themouse is hovering at
        }
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.03d);
        for (int i = 0; i < this.gridWidth + 1; i++) {
            gc.strokeLine(i, 0, i, this.gridHeight);
        }
        for (int i = 0; i < this.gridHeight + 1; i++) {
            gc.strokeLine(0, i, this.gridWidth, i);
        }
        // sets values and colours for the stroke
    }
    
    public Pathfinder getPathfinder() {
        return this.pf;
        // gets pathfinder function
    }
    
    public Toolbar getToolbar() {
        return this.toolbar;
        // gets toolbar function
    }
    
    public Sidebar getSidebar() {
        return this.sidebar;
        // gets sidebar function
    }
    
    public void setMode(int m) {
        this.mode = m;
        this.infobar.setMode(m);
        // sets mode the pathfinder is in
    }
    
}