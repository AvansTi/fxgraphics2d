package org.jfree.fx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.*;

public class WindingRule extends Application {
    Stage stage;
    ResizableCanvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene((new Scene(mainPane)));
        primaryStage.setTitle("Hello Winding Rule");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        //scg(graphics);
        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        GeneralPath myShape = new GeneralPath();
        myShape.moveTo(-2f, 0f);
        myShape.quadTo(0f, 2f, 2f, 0f);
        myShape.quadTo(0f, -2f, -2f, 0f);
        myShape.moveTo(-1f, 0.5f);
        myShape.lineTo(-1f, -0.5f);
        myShape.lineTo(1f, 0.5f);
        myShape.lineTo(1f, -0.5f);
        myShape.closePath();
        myShape.setWindingRule(Path2D.WIND_EVEN_ODD);
        graphics.setColor(Color.green);
        graphics.fill(AffineTransform.getScaleInstance(100, 100).createTransformedShape(myShape));
        graphics.setColor(Color.black);
        graphics.draw(AffineTransform.getScaleInstance(100, 100).createTransformedShape(myShape));


    }
}
