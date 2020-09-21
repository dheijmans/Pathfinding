/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

/**
 *
 * @author Dennis
 */
public class Node {
    
    private final int x, y;
    private int f, g, h;
    private Node parent; 
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getF() {
        return this.f;
    }
    
    public int getG() {
        return this.g;
    }
    
    public Node getParent() {
        return this.parent;
    }
    
    public void setF(int g, int h) {
        this.g = g;
        this.h = h;
        this.f = g + h;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public boolean isSameNodeAs(Node n) {
        return this.x == n.getX() && this.y == n.getY();
    }
    
    public boolean isSameNodeAs(int x, int y) {
        return this.x == x && this.y == y;
    }

}
