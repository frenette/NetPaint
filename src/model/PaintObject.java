package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class PaintObject implements Serializable {

    private static final long serialVersionUID = -8291637189012926339L;
    private Color color;
    private Point start;
    private Point end;

    protected PaintObject(Color color, Point start, Point end) {
	this.color = color;
	this.start = start;
	this.end = end;
    }

    public Color getColor() {
	return this.color;
    }

    public Point getStart() {
	return this.start;
    }

    public Point getEnd() {
	return this.end;
    }
    
    public void setEnd(Point end) {
	this.end = end;
    }

    public abstract void draw(Graphics g);

}