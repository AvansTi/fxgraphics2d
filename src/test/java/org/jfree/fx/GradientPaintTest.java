package org.jfree.fx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GradientPaintTest extends Application {
    Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        javafx.scene.canvas.Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));

        canvas.setOnMouseDragged(e ->
        {
            position = new Point2D.Double(e.getX(), e.getY());
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });

        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Hello Radial");
        primaryStage.show();
    }
    public Point2D position = new Point2D.Double(100,100);

    public void draw(FXGraphics2D g2d)
    {
        g2d.setBackground(Color.white);
        g2d.clearRect(0,0,1920,1080);

        float[] fractions = { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f};
        Color[] colors = new Color[fractions.length];
        for(int i = 0; i < colors.length; i++)
            colors[i] = Color.getHSBColor(fractions[i], 1.0f, 1.0f);

        g2d.setPaint(new RadialGradientPaint(
                1920/2, 1080/2,	//center
                Math.min(1920, 1080), //radius
                (float)position.getX(), (float)position.getY(), //focal point
                fractions, colors, MultipleGradientPaint.CycleMethod.REFLECT));

        g2d.fill(new Rectangle2D.Double(0,0,1920, 1080));

    }
}
