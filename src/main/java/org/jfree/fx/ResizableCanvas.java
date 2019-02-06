package org.jfree.fx;


import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class ResizableCanvas extends Canvas {

    private Resizable observer;
	private FXGraphics2D g2d;
    public ResizableCanvas(Resizable observer, Parent parent) throws IllegalArgumentException {
		super(640, 480);
        this.observer = observer;
		this.g2d = new FXGraphics2D(getGraphicsContext2D());

		// Check if we have a valid container
		if (parent instanceof BorderPane) {
			BorderPane oorderPane = (BorderPane)parent;
			oorderPane.widthProperty().addListener(observable -> resize(oorderPane.getWidth(), oorderPane.getHeight()));
			oorderPane.heightProperty().addListener(observable -> resize(oorderPane.getWidth(), oorderPane.getHeight()));
		} else if (parent instanceof StackPane) {
			StackPane stackPane = (StackPane)parent;
			stackPane.widthProperty().addListener(observable -> resize(stackPane.getWidth(), stackPane.getHeight()));
			stackPane.heightProperty().addListener(observable -> resize(stackPane.getWidth(), stackPane.getHeight()));
		} else if (parent instanceof FlowPane) {
			FlowPane flowPane = (FlowPane) parent;
			flowPane.widthProperty().addListener(observable -> resize(flowPane.getWidth(), flowPane.getHeight()));
			flowPane.heightProperty().addListener(observable -> resize(flowPane.getWidth(), flowPane.getHeight()));
		} else {
			throw new IllegalArgumentException("Parent type is not supported");
		}

        heightProperty().addListener(observable -> redraw());
    }

    @Override
    public boolean isResizable() {
        return true;
    }
	
	@Override
	public double minHeight(double width) {
		return 0;
	}

	@Override
	public double maxHeight(double width) {
		return 10000;
	}
	
	@Override
    public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
    }
	
	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 10000;
	}

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
		if (width > 0 && height > 0)
			redraw();
    }

    private void redraw() {
		int width = (int)getWidth();
		int height = (int)getHeight();

		// Clear current screen
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

        this.observer.draw(g2d);
    }


}