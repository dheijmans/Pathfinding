/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import java.util.ArrayList;

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
        this.endNode.setF(distance(this.startNode, this.endNode), 0);
        open.add(this.startNode);
        
        while (true) {
            Node current = this.setCurrent();
        }
    }
    
    private int distance(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
    
    private Node setCurrent() {
        
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
    }
    
}
