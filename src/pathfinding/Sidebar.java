package pathfinding;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    
    private Label steps;
    private Label startNode;
    private Label endNode;
    private Label blockedNodes;
    
    private final MainView mainView;
    private final Pathfinder pathfinder;
    
    public Sidebar(MainView mv) {
        this.mainView = mv;
        this.pathfinder = this.mainView.getPathfinder();
        
        this.steps = new Label("Amount of steps: -");
        this.startNode = new Label("Startnode: (0,0)");
        this.endNode = new Label("Endnode: (0,0)");
        this.blockedNodes = new Label("Amount of blocked nodes: 0");
        
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(this.steps, this.startNode, this.endNode, this.blockedNodes);          
    }
    
    public void setAmountOfSteps(int s) {
        if(s == 0) {
            this.steps.setText("Amount of steps: -");
        } else {
            this.steps.setText("Amount of steps: " + s);
        }
    }
    
    public void setStartNode(Node n) {
        int x = n.getX();
        int y = n.getY();
        this.startNode.setText("Startnode: (" + (x + 1) + "," + (y + 1) + ")");
    }
    
    public void setEndNode(Node n) {
        int x = n.getX();
        int y = n.getY();
        this.endNode.setText("Endnode: (" + (x + 1) + "," + (y + 1) + ")");
    }
    
    public void setBlockedNodes(int b) {
        this.blockedNodes.setText("Amount of blocked nodes: " + b);
    }
       
}
