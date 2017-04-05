package model;

import java.awt.Point;
import java.util.Observable;
import java.util.Vector;

public class PaintObjectCollection extends Observable {

    private Vector<PaintObject> paintObjects;
    private PaintObject tempPaintObject;

    public PaintObjectCollection() {
	this.paintObjects = new Vector<>();
    }

    public Vector<PaintObject> getAllPaintObjects() {
	Vector<PaintObject> allPaintObjects = new Vector<>();
	allPaintObjects.addAll(this.paintObjects);
	allPaintObjects.add(this.tempPaintObject);
	return allPaintObjects;
    }

    public Vector<PaintObject> getPaintObjects() {
	return paintObjects;
    }

    /*
     * When the server sends over a Vector<PaintObjects> we will then call this
     * method
     */
    public void setPaintObjects(Vector<PaintObject> paintObjects) {
	this.paintObjects = paintObjects;
	this.setChanged();
	this.notifyObservers();
    }

    public PaintObject getTempPaintObject() {
	return this.tempPaintObject;
    } /*
       * The server only needs to know when we add the final object.
       */

    public void setTempPaintObject(PaintObject tempPaintObject) {
	this.tempPaintObject = tempPaintObject;

	/*
	 * TODO : remove "this.paintObjects.addElement(tempPaintObject);" only
	 * here for testing
	 */
	this.paintObjects.addElement(tempPaintObject);
	/*
	 * END TODO
	 */

	this.setChanged();
	this.notifyObservers();
    }

    public void setTempPaintObjectEnd(Point end) {
	this.tempPaintObject.setEnd(end);
	this.setChanged();
	this.notifyObservers();
    }

    /*
     * When this method is called we need to contact the server and let it know
     * we added a PaintObject
     */
    public void setTempPaintObjectAsPermenant() {
	this.paintObjects.addElement(this.tempPaintObject);
	this.setChanged();
	this.notifyObservers();
    }

}
