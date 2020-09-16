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
    
    public Node(int x, int y, int g, int h, Node parent) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
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
    
    public void setF(int g, int h) {
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

}
