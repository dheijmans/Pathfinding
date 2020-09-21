/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

/**
 *
 * @author 120006994
 */
public class MainView extends VBox {
    private final int width;
    private final int height;
    
    private Affine affine;
    
    private Button runBtn;
    private Canvas canvas;
    
    
    public MainView(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.runBtn = new Button("RUN");
        this.canvas = new Canvas(this.width, this.height);
        
        this.affine = new Affine();
        this.affine.appendScale(this.width / 10f, this.height / 10f);
        
        
        this.getChildren().addAll(this.runBtn, this.canvas);
        
    }
    
    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,this.width,this.height);
        
        g.setStroke(Color.BLACK);
        g.setLineWidth(0.05f);
        for(int i = 0; i < this.width; i++) {
            g.strokeLine(i, 0, i, this.width);
        }
        
        for(int i = 0; i < this.height; i++) {
            g.strokeLine(0, i, this.height, i);
        }
    }
    
    public int getCanvasWidth() {
        return this.width;
    }
    
    public int getCanvasHeight() {
        return this.height;
    }
    
    
}
