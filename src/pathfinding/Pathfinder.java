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
    ArrayList<Node> open = new ArrayList<Node>();
    ArrayList<Node> closed = new ArrayList<Node>();
    ArrayList<Node> path = new ArrayList<Node>();
    
    public Pathfinder() {
        this.maze = new int[10][10];
    }
    
    public void printMaze() {
        System.out.print("  ");
        for (int x = 1; x < this.maze[0].length - 1; x++) {
            System.out.print(x + " ");
        }
        System.out.println();
        for (int y = 0; y < this.maze.length; y++) {
            for (int x = 0; x < this.maze[0].length; x++) {
                if (this.maze[y][x] == UNBLOCKED) {
                    System.out.print("_ ");
                } else if(this.maze[y][x] == BLOCKED) {
                    System.out.print("X ");
                }
            }
            if (y > 0 && y < this.maze.length - 1) {
                System.out.println(y);
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
            
            if (current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
                retracePath(current);
                return;
            }
            
            ArrayList<Node> neighbours = getNeighbours(current);
            for (Node neighbour : neighbours) 
            { 
                if (isBlocked(neighbour) || isInList(neighbour, this.closed)) {
                    continue;
                }
                
                if (current.getG() + distance(current, neighbour) < neighbour.getG() || !isInList(neighbour, this.open)) {
                    neighbour.setF(current.getG() + distance(current, neighbour), distance(neighbour, this.endNode));
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
        
        neighbours.add(makeNeighbourNode(x - 1, y));
        neighbours.add(makeNeighbourNode(x + 1, y));
        neighbours.add(makeNeighbourNode(x, y - 1));
        neighbours.add(makeNeighbourNode(x, y + 1));
        
        return neighbours;
    }
    
    private Node makeNeighbourNode(int x, int y) {
        for (Node n : this.open) { 
            if (n.getX() == x && n.getY() == y) {
                return n;
            }
        }
        return new Node(x, y);
    }
    
    private static boolean isInList(Node a, ArrayList<Node> list) {
        for (Node b : list) { 
            if (b.getX() == a.getX() && b.getY() == a.getY()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isBlocked(Node n) {
        if (this.maze[n.getY()][n.getX()] == BLOCKED) {
            return true;
        } else {
            return false;
        }
    }
    
    private void updateOpen(Node n) {
        for (Node a : this.open) { 
            if (a.getX() == n.getX() && a.getY() == n.getY()) {
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
    
    public static void main(String[] args) {
        Pathfinder pathfinder = new Pathfinder();
        pathfinder.maze = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        pathfinder.printMaze();
        pathfinder.startNode = new Node(1, 1);
        pathfinder.endNode = new Node(8, 8);
        pathfinder.aStar();
        System.out.println("Path:");
        for (Node n : pathfinder.path) { 
            System.out.println(n.getX() + ", " + n.getY());
        }
    }
    
}
