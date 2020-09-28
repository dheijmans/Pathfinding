package pathfinding;

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
        if (n != null) {
            return this.x == n.getX() && this.y == n.getY();
        } else {
            return false;
        }           
    }
    
    public boolean isSameNodeAs(int x, int y) {
        return this.x == x && this.y == y;
    }

}