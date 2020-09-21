 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Dennis
 */
public class Pathfinder {
    
    public static final int UNBLOCKED = 0;
    public static final int BLOCKED = 1;

    private int[][] maze;
    private Node startNode, endNode;
    private ArrayList<Node> open = new ArrayList<Node>();
    private ArrayList<Node> closed = new ArrayList<Node>();
    private ArrayList<Node> path = new ArrayList<Node>();
    
    public Pathfinder() {
        this.maze = new int[10][10];
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
                        System.out.print("S ");
                    } else if (this.endNode.isSameNodeAs(x, y)) {
                        System.out.print("O ");
                    } else if (isInList(new Node(x, y), this.path)) {
                        System.out.print("* ");
                    } else {
                        System.out.print("_ ");
                    }
                } else if(this.maze[y][x] == BLOCKED) {
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
    
    private void aStar() {
        this.startNode.setF(0, distance(this.startNode, this.endNode));
        this.open.add(this.startNode);
        
        while (true) {
            Node current = this.setCurrent();
            this.open.remove(current);
            this.closed.add(current);
            
            if (current.isSameNodeAs(this.endNode)) {
                retracePath(current);
                return;
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
    }
    
    private static int distance(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
    
    private Node setCurrent() {
        Collections.sort(this.open, new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return Integer.compare(a.getF(), b.getF());
            }
        });
        return this.open.get(0);
    }
    
    private ArrayList<Node> getNeighbours(Node n) {
        int x = n.getX();
        int y = n.getY();
        
        ArrayList<Node> neighbours = new ArrayList<Node>();
        
        neighbours.add(getNeighbourNode(x - 1, y));
        neighbours.add(getNeighbourNode(x + 1, y));
        neighbours.add(getNeighbourNode(x, y - 1));
        neighbours.add(getNeighbourNode(x, y + 1));
        
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
    
    private static boolean isInList(Node n, ArrayList<Node> list) {
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
    
    public ArrayList<Node> getOpen() {
        return this.open;
    }
    
    public ArrayList<Node> getClosed() {
        return this.closed;
    }
    
    public ArrayList<Node> getPath() {
        return this.path;
    }
    
    public static void main(String[] args) {
        Pathfinder pathfinder = new Pathfinder();
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
        pathfinder.aStar();
        System.out.println();
        pathfinder.printMaze();
    }
    
}
