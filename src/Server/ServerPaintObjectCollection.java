package Server;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Vector;

import model.PaintObject;
import model.Rectangle;

public class ServerPaintObjectCollection extends Observable {

    private Vector<PaintObject> permenantPaintObjects;
    private Vector<PaintObject> tempPaintObjects;

    public ServerPaintObjectCollection() {
	this.permenantPaintObjects = new Vector<>();
	this.tempPaintObjects = new Vector<>();

	permenantPaintObjects.add(new Rectangle(Color.RED, new Point(0, 0), new Point(10, 100)));
	permenantPaintObjects.add(new Rectangle(Color.BLUE, new Point(100, 100), new Point(980, 0)));
	permenantPaintObjects.add(new Rectangle(Color.GREEN, new Point(20, 82), new Point(34, 0)));
	permenantPaintObjects.add(new Rectangle(Color.BLACK, new Point(40, 73), new Point(40, 0)));
	permenantPaintObjects.add(new Rectangle(Color.CYAN, new Point(320, 93), new Point(70, 0)));
	permenantPaintObjects.add(new Rectangle(Color.ORANGE, new Point(430, 42), new Point(0, 90)));
    }

    public Vector<PaintObject> getPermenantPaintObjects() {
	return this.permenantPaintObjects;
    }

    public void addPermenantPaintObject(PaintObject o) {
	this.permenantPaintObjects.addElement(o);
	this.setChanged();
	this.notifyObservers();
    }

    public Vector<PaintObject> getTempPaintObjects() {
	return this.tempPaintObjects;
    }

    public void addTempPaintObjects(PaintObject o) {
	this.tempPaintObjects.addElement(o);
	this.setChanged();
	this.notifyObservers();
    }

    public Vector<PaintObject> getAllPaintObjects() {
	Vector<PaintObject> returnVector = new Vector<PaintObject>();
	returnVector.addAll(this.permenantPaintObjects);
	returnVector.addAll(this.tempPaintObjects);
	return returnVector;
    }
}
