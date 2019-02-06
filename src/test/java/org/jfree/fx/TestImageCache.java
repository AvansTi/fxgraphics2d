package org.jfree.fx;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertTrue;

public class TestImageCache extends ApplicationTest {

	Canvas canvas;
	@Override
	public void start(Stage stage) throws Exception {

		this.canvas = new Canvas(800, 600);
		Scene scene = new Scene(new Group(canvas));
		stage.setScene(scene);
		stage.show();
	}

	int x = 0;
	@Test
	public void testImage() throws InterruptedException {

		final FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

		g2d.drawLine(100,100,500,500);

		BufferedImage image = new BufferedImage(4096,4096, BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().setColor(Color.green);
		image.getGraphics().clearRect(0,0, 4096, 4096);
		for(int i = 0; i < 1000; i++)
			image.getGraphics().drawLine((int)(Math.random()*1000),(int)(Math.random()*1000), (int)(Math.random()*1000),(int)(Math.random()*1000));
		x = 0;
		new AnimationTimer()
		{
			@Override
			public void handle(long now) {
				g2d.drawImage(image, AffineTransform.getTranslateInstance(x,10), null);
				x++;
			}

		}.start();



		Thread.sleep(5000);
	}


}
