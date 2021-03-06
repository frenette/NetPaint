package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JComponent;

import model.Image;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.PaintObjectCollection;
import model.Rectangle;

/*
 * This is the component we will be painting onto
 */

public class Canvas extends JComponent implements Observer {

    /*
     * Testing
     */
    private Color currentColor;

    public Color getCurrentColor() {
	return this.currentColor;
    }

    public void setCurrentColor(Color color) {
	this.currentColor = color;
    }

    private String paintObjectType;

    public String getPaintObjectType() {
	return this.paintObjectType;
    }

    public void setPaintObjectType(String s) {
	this.paintObjectType = s;
    }

    // private PaintObject PaintObjectType;
    /*
     * End testing
     */

    private PaintObjectCollection paintObjectCollection;
    private boolean clicked;

    public Canvas(Dimension d) {
	super();
	this.setSize(d);
	this.addMouseListener(new CanvasMouseListener());
	this.addMouseMotionListener(new CanvasMouseMotionListener());

	this.paintObjectCollection = new PaintObjectCollection();
	this.paintObjectCollection.addObserver(this);
	this.clicked = false;

	this.paintObjectType = "oval";
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);

	Vector<PaintObject> paintObjects = paintObjectCollection.getPaintObjects();

	if (paintObjectCollection.getAllPaintObjects() != null && paintObjectCollection.getPaintObjects().size() != 0) {
	    for (PaintObject obj : paintObjects) {
		obj.draw(g);
	    }
	}
    }

    @Override
    public void update(Observable o, Object arg) {
	/*
	 * The paintObjectCollection has been updated
	 */
	this.repaint();
    }

    private class CanvasMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
	    // System.out.println("mouseClicked(MouseEvent e): " + e);
	    /*
	     * Fires when the mouse has been clicked, and then released
	     */

	    if (!clicked) {
		/*
		 * If the mouse has not already been clicked we are going to
		 * create a tempPaintObject
		 */

		clicked = true;

		if (paintObjectType.equalsIgnoreCase("oval")) {
		    paintObjectCollection.setTempPaintObject(new Oval(currentColor, e.getPoint(), e.getPoint()));
		} else if (paintObjectType.equalsIgnoreCase("rectangle")) {
		    paintObjectCollection.setTempPaintObject(new Rectangle(currentColor, e.getPoint(), e.getPoint()));
		} else if (paintObjectType.equalsIgnoreCase("line")) {
		    paintObjectCollection.setTempPaintObject(new Line(currentColor, e.getPoint(), e.getPoint()));
		} else {
		    paintObjectCollection.setTempPaintObject(new Image(currentColor, e.getPoint(), e.getPoint()));
		}
	    } else {
		/*
		 * If the mouse has already been clicked we are saying it is a
		 * final PaintComponent
		 */

		clicked = false;
		paintObjectCollection.setTempPaintObjectEnd(e.getPoint());
		paintObjectCollection.setTempPaintObjectAsPermenant();
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    // System.out.println("mousePressed(MouseEvent e): " + e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    // System.out.println("mouseReleased(MouseEvent e): " + e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	    // System.out.println("mouseEntered(MouseEvent e): " + e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    // System.out.println("mouseExited(MouseEvent e): " + e);
	}
    }

    private class CanvasMouseMotionListener implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
	    // System.out.println("mouseDragged(MouseEvent e): " + e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	    // System.out.println("mouseMoved(MouseEvent e): " + e);

	    /*
	     * We only care about it if the mouse has already been clicked
	     */

	    if (clicked) {
		/*
		 * We are in the process of drawing a tempPaintObject
		 */

		paintObjectCollection.setTempPaintObjectEnd(e.getPoint());
	    }
	}
    }
}
