package pathfinding;

public class Node {
    
    private final int x, y;
    private int f, g, h;
    private Node parent; 
    
    // f = g + h
    // g = distance startNode to curent node
    // h = distance endNode to current node
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // sets x & y values for the Node
    
    public int getX() {
        return this.x;
    }
    
    // checks x
    
    public int getY() {
        return this.y;
    }
    
    // checks y
    
    public int getF() {
        return this.f;
    }
    
    // returns f
    
    public int getG() {
        return this.g;
    }
    
    // returns g
    
    public Node getParent() {
        return this.parent;
    }
    
    // set node as parentNode
    
    public void setF(int g, int h) {
        this.g = g;
        this.h = h;
        this.f = g + h;
    }
    
    // checks distances between startNode and currentNode & currentNode and endNode. 
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    // sets current node as parent
    
    public boolean isSameNodeAs(Node n) {
        if (n != null) {
            return this.x == n.getX() && this.y == n.getY();
        } else {
            return false;
        }           
    }
    // checks wether or not the currentNode = the parentNode to prvent the system from checking the sam eNode over and over
    
    public boolean isSameNodeAs(int x, int y) {
        return this.x == x && this.y == y;
    }
    // incase the currentNode != parentNode sets currentNode as new parentNode
}