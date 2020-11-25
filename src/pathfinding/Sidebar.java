package pathfinding;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Sidebar extends VBox {
    
    private Label steps;
    private Label startNode;
    private Label endNode;
    private Label blockedNodes;     
    
    private final MainView mainView;
    private final Pathfinder pathfinder;
    
    private int totalBlocked;
    
    public Sidebar(MainView mv) {
        this.mainView = mv;
        this.pathfinder = this.mainView.getPathfinder();
        
        Label stats = new Label("Statistics");
        stats.setFont(new Font(20));
        this.steps = new Label("Amount of steps: -");
        this.startNode = new Label("Startnode: (0,0)");
        this.endNode = new Label("Endnode: (0,0)");
        this.blockedNodes = new Label("Amount of blocked nodes: 0");
        
        Label legend = new Label("Legend");
        legend.setFont(new Font(20));
                               
        TextFlow start = new TextFlow();
        Text blockStart = new Text("■");
        blockStart.setFill(Color.GOLD);
        Text txtStart = new Text(" - Start");
        start.getChildren().addAll(blockStart, txtStart);
        
        TextFlow end = new TextFlow();
        Text blockEnd = new Text("■");
        blockEnd.setFill(Color.DARKORCHID);
        Text txtEnd = new Text(" - End");
        end.getChildren().addAll(blockEnd, txtEnd);
        
        TextFlow blocked = new TextFlow();
        Text blockBlocked = new Text("■");
        blockBlocked.setFill(Color.BLACK);
        Text txtBlocked = new Text(" - Blocked");
        blocked.getChildren().addAll(blockBlocked, txtBlocked);
        
        TextFlow open = new TextFlow();
        Text blockOpen = new Text("■");
        blockOpen.setFill(Color.LIME);
        Text txtOpen = new Text(" - Open");
        open.getChildren().addAll(blockOpen, txtOpen);
        
        TextFlow closed = new TextFlow();
        Text blockClosed = new Text("■");
        blockClosed.setFill(Color.RED);
        Text txtClosed = new Text(" - Closed");
        closed.getChildren().addAll(blockClosed, txtClosed);
        
        TextFlow path = new TextFlow();
        Text blockPath = new Text("■");
        blockPath.setFill(Color.DEEPSKYBLUE);
        Text txtPath = new Text(" - Path");
        path.getChildren().addAll(blockPath, txtPath);

        
        
        
        this.totalBlocked = 0;
        
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(stats, this.steps, this.startNode, this.endNode, this.blockedNodes, legend, start, end, blocked, open, closed, path);          
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
    
    public void addBlockedNode() {
        this.totalBlocked++;
        this.blockedNodes.setText("Amount of blocked nodes: " + this.totalBlocked);
    }
    
    public void removeBlockedNode() {
        this.totalBlocked--;
        this.blockedNodes.setText("Amount of blocked nodes: " + this.totalBlocked);
    }
    
    public void clearBlockedNode() {
        this.totalBlocked = 0;
        this.blockedNodes.setText("Amount of blocked nodes: 0");
    }
       
}
