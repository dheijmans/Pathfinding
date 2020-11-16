package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Pathfinder {
    
    public static final int UNBLOCKED = 0;
    public static final int BLOCKED = 1;

    public int[][] maze;
    public Node startNode, endNode;
    
    private final int height;
    private final int width;
    
    private boolean diagonal = true;

    private ArrayList<Node> open = new ArrayList<Node>();
    private ArrayList<Node> closed = new ArrayList<Node>();
    private ArrayList<Node> path = new ArrayList<Node>();
    
    private final MainView mainView;
    private final Timeline timeline;
    
    public Pathfinder(int width, int height, MainView mainView) {
        this.height = height;
        this.width = width;
        this.maze = new int[this.height][this.width];
        this.mainView = mainView;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(10), this::animateStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    private void animateStep(ActionEvent event) {
        step();
        this.mainView.draw();
    }
    
    public void aStar() {
        this.open.clear();
        this.closed.clear();
        this.path.clear();
        this.startNode.setF(0, distance(this.startNode, this.endNode));
        this.open.add(this.startNode);
        this.diagonal = this.mainView.getToolbar().getDiagonalState();
        this.timeline.play();
        this.mainView.getToolbar().getPauseButton().setText("Pause");
    }
    
    private void step() {
        if (this.open.isEmpty()) {
            this.mainView.getToolbar().getPauseButton().setText("Pause");
            this.mainView.getToolbar().getDiagonal().setDisable(false);
            this.timeline.stop();
            this.mainView.setMode(MainView.RESULTS);
            this.mainView.getSidebar().SetAmountOfSteps(this.path.size() - 1);
            Popup.noPossiblePathFound();
            return;
        }
        Node current = this.setCurrent();
        this.open.remove(current);
        this.closed.add(current);
        if (current.isSameNodeAs(this.endNode)) {
            this.mainView.getToolbar().getDiagonal().setDisable(false);
            retracePath(current);
            this.timeline.stop();
            this.mainView.setMode(MainView.RESULTS);
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
    }
    
    private int distance(Node a, Node b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());  
        
            return 10 * Math.max(dx, dy) + 4 * Math.min(dx, dy);
       
//        return (int) Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
    }
    
    public static boolean isInList(Node n, ArrayList<Node> list) {
        for (Node a : list) { 
            if (n.isSameNodeAs(a)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isBlocked(Node n) {
        return this.maze[n.getY()][n.getX()] == BLOCKED;
    }
    
    private boolean isNewPathShorter(Node current, Node neighbour) {
        return current.getG() + distance(current, neighbour) < neighbour.getG();
    }
    
    private boolean isCrossing(Node n, int x, int y) {
        if (n.getX() != x && n.getY() != y) {
            if (this.maze[n.getY()][x] == BLOCKED && this.maze[y][n.getX()] == BLOCKED) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isOnGrid(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
    
    private Node setCurrent() {
        Collections.sort(this.open, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return Integer.compare(a.getG(), b.getG());
            }
        });
        return this.open.get(0);
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
    }
    
    private Node getNeighbourNode(int x, int y) {
        for (Node n : this.open) { 
            if (n.isSameNodeAs(x, y)) {
                return n;
            }
        }
        return new Node(x, y);
    }
    
    private void updateOpen(Node n) {
        for (Node a : this.open) { 
            if (n.isSameNodeAs(a)) {
                this.open.remove(a);
                this.open.add(n);
                return;
            }
        }
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
    }
    
    public void clear() {
        this.open.clear();
        this.closed.clear();
        this.path.clear();
        this.mainView.draw();        
    }
    
    public ArrayList<Node> getOpen() {
        return this.open;
    }
    
    public ArrayList<Node> getClosed() {
        return this.closed;
    }
    
    public ArrayList<Node> getPath() {
        return this.path;
    }
    
    public Timeline getTimeline() {
        return this.timeline;
    }
    
}