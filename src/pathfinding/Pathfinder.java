package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

// importing required functions

public class Pathfinder {
    
    public static final int UNBLOCKED = 0;
    public static final int BLOCKED = 1;

    // sets a value for wether the Node is blocked or not
    
    public int[][] maze;
    public Node startNode, endNode;
    
    // creates the "Maze", startNode and endNode 
    
    private final int height;
    private final int width;
    
    // sets height and width for maze
    
    private boolean diagonal = true;
    
    // enables diagonal movement for the pathfinder function

    private ArrayList<Node> open = new ArrayList<Node>();
    private ArrayList<Node> closed = new ArrayList<Node>();
    private ArrayList<Node> path = new ArrayList<Node>();
    
    // creates lists for "open" Nodes (not yet checked or still checking nodes), 
    // "closed" Nodes (already checked)
    // "path" Nodes (the Nodes that give the shortest path)
    
    private final MainView mainView;
    private final Timeline timeline;
    
    public Pathfinder(int width, int height, MainView mainView) {
        this.height = height;
        this.width = width;
        this.maze = new int[this.height][this.width];
        this.mainView = mainView;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(10), this::animateStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        
        // sets a width, height for Maze
        // creates the whole thing
        // sets the "ping" to 10 milliseconds, thus every 10 milliseconds  a Node will get checked
        // timeline repeats the function
    }
    
    private void animateStep(ActionEvent event) {
        step();
        this.mainView.draw();
    }
    
    // execution of timeline function (time after time)
    
    public void aStar() {
        this.open.clear();
        this.closed.clear();
        this.path.clear();
        this.startNode.setF(0, distance(this.startNode, this.endNode));
        this.open.add(this.startNode);
        this.diagonal = this.mainView.getToolbar().getDiagonalState();
        this.timeline.play();
        this.mainView.getToolbar().getPauseButton().setText("Pause");
        
        // sets basic state for the Pathfinder, whereas the lists are empty, checks for diagonal option and sets the pause button correctly
    }
    
    private void step() {
        if (this.open.isEmpty()) {
            this.mainView.getToolbar().getPauseButton().setText("Pause");
            this.mainView.getToolbar().getDiagonal().setDisable(false);
            this.timeline.stop();
            this.mainView.setMode(MainView.RESULTS);            
            Popup.noPossiblePathFound();
            return;
            
            // sets results when results are found
        }
        Node current = this.setCurrent();
        this.open.remove(current);
        this.closed.add(current);
        if (current.isSameNodeAs(this.endNode)) {
            this.mainView.getToolbar().getDiagonal().setDisable(false);
            retracePath(current);
            this.timeline.stop();
            this.mainView.setMode(MainView.RESULTS);
            this.mainView.getSidebar().setAmountOfSteps(this.path.size() - 1);
            
            // sets currentNode in closed list, unless the currentNode = endNode, then shows path and sets the path length in the sidebar
        } 
        ArrayList<Node> neighbours = getNeighbours(current);
        for (Node neighbour : neighbours) 
        { 
            if (isBlocked(neighbour) || isInList(neighbour, this.closed)) {
                continue;
            }    
            if (isNewPathShorter(current, neighbour) || !isInList(neighbour, this.open)) {
                int g = current.getG() + distance(current, neighbour);
                int h = distance(neighbour, this.endNode);
                neighbour.setF(g, h);
                neighbour.setParent(current);
                if (!isInList(neighbour, this.open)) {
                    this.open.add(neighbour);
                } else {
                    updateOpen(neighbour);                       
                }                        
            }
            
            // checks G & H values (and thus creates the F) and decides wether ot not a certain path is faster as another
        }
    }
    
     public void printMaze() {
        System.out.print("  ");
        for (int x = 1; x < this.maze[0].length - 1; x++) {
            System.out.print(x % 10 + " ");
            
        }
        System.out.println();
        for (int y = 0; y < this.maze.length; y++) {
            for (int x = 0; x < this.maze[0].length; x++) {
                if (this.maze[y][x] == UNBLOCKED) {
                    if (this.startNode.isSameNodeAs(x, y)) {
                        System.out.print("A ");
                    } else if (this.endNode.isSameNodeAs(x, y)) {
                        System.out.print("B ");
                    } else if (isInList(new Node(x, y), this.path)) {
                        System.out.print("* ");
                    } else {
                        System.out.print("_ ");
                    }
                } else if (this.maze[y][x] == BLOCKED) {
                    System.out.print("X ");
                }
            }
            if (y > 0 && y < this.maze.length - 1) {
                System.out.println(y % 10);
            } else {
                System.out.println();
            }
        }
        // displays values of the maze in console (used during testing when canvas was unavailable)
    }
    
    private int distance(Node a, Node b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());  
        if (this.diagonal) {
            return 10 * Math.max(dx, dy) + 4 * Math.min(dx, dy);
        } else {
            return dx + dy;
        }
        
        // returns x and y distance traveled between two nodes
    }
    
    public static boolean isInList(Node n, ArrayList<Node> list) {
        for (Node a : list) { 
            if (n.isSameNodeAs(a)) {
                return true;
            }
        }
        return false;
        
        // checks wether or not the currentNode is already in a list
    }
    
    private boolean isBlocked(Node n) {
        return this.maze[n.getY()][n.getX()] == BLOCKED;
        
        // checks if the Node is blocked
    }
    
    private boolean isNewPathShorter(Node current, Node neighbour) {
        return current.getG() + distance(current, neighbour) < neighbour.getG();
        
        // is new path shorther check
    }
    
    private boolean isCrossing(Node n, int x, int y) {
        if (n.getX() != x && n.getY() != y) {
            if (this.maze[n.getY()][x] == BLOCKED && this.maze[y][n.getX()] == BLOCKED) {
                return true;
            }
            
        }
        return false;
        
        // prevents diagonal movement through blockades
    }
    
    public boolean isOnGrid(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
    
    // checks if the to be checked nodes are in the grid
    private Node setCurrent() {
        Collections.sort(this.open, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return Integer.compare(a.getF(), b.getF());
            }
        });
        return this.open.get(0);
        
        // sets a new current node depending on the shortest distance
    }
    
    private ArrayList<Node> getNeighbours(Node n) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                if (x == 0 && y == 0) {
                    continue;
                } else if (Math.abs(x) == Math.abs(y) && !this.diagonal) {
                    continue;
                } else if (isOnGrid(n.getX() + x, n.getY() + y)) {
                    if (isCrossing(n, n.getX() + x, n.getY() + y)) {
                        continue;
                    }
                    neighbours.add(getNeighbourNode(n.getX() + x, n.getY() + y));
                }              
            }
        }
        return neighbours;
        
        // checks which Nodes are the neighbours from the currentNode
    }
    
    private Node getNeighbourNode(int x, int y) {
        for (Node n : this.open) { 
            if (n.isSameNodeAs(x, y)) {
                return n;
            }
        }
        return new Node(x, y);
        
        // selects a new node
    }
    
    private void updateOpen(Node n) {
        for (Node a : this.open) { 
            if (n.isSameNodeAs(a)) {
                this.open.remove(a);
                this.open.add(n);
                return;
            }
        }
        // puts the currentNode in the correct list
    }
    
    private void retracePath(Node n) {
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(n);
        Node parent = n.getParent();
        while (parent != null) {
            path.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(path);
        this.path = path;
        // checks which nodes are in the path list and check the smallest route, then displays the shortest route
    }
    
    public void clear() {
        this.open.clear();
        this.closed.clear();
        this.path.clear();
        this.mainView.draw();        
        // clears all list when the clear button is pushed
    }
    
    public ArrayList<Node> getOpen() {
        return this.open;
        // checks which nodes are in the open list
    }
    
    public ArrayList<Node> getClosed() {
        return this.closed;
        // checks which nodes are in the closed list
    }
    
    public ArrayList<Node> getPath() {
        return this.path;
        // checks which nodes are in the Path list
    }
    
    public Timeline getTimeline() {
        return this.timeline;
        // returns timeline
    }
    
}
