package model;

import java.awt.Point;
import java.util.Observable;
import java.util.Vector;

public class PaintObjectCollection extends Observable {

    /*
     * Testing
     */
    public void add() {
	this.paintObjects.addElement(this.tempPaintObject);
	this.setChanged();
	this.notifyObservers();
    }
    /*
     * End testing
     */

    private Vector<PaintObject> paintObjects;
    private PaintObject tempPaintObject;

    public PaintObjectCollection() {
	this.paintObjects = new Vector<>();
    }

    public Vector<PaintObject> getPaintObjects() {
	return paintObjects;
    }

    public void setPaintObjects(Vector<PaintObject> paintObjects) {
	this.paintObjects = paintObjects;

	// TODO
	// this.setChanged();
	// this.notifyObservers();
    }

    public PaintObject getTempPaintObject() {
	return this.tempPaintObject;
    }

    public void setTempPaintObject(PaintObject tempPaintObject) {
	this.tempPaintObject = tempPaintObject;

	/*
	 * Let the server know we changed the tempPaintObject
	 */

	// TODO
	// this.setChanged();
	// this.notifyObservers();
    }

    public void setTempPaintObjectEnd(Point end) {
	this.tempPaintObject.setEnd(end);

	/*
	 * Let the server know we changed the tempPaintObject
	 */

	// TODO
	// this.setChanged();
	// this.notifyObservers();
    }
}
