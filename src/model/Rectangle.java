package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Rectangle extends PaintObject {

    public Rectangle(Color color, Point start, Point end) {
	super(color, start, end);
    }

    @Override
    public void draw(Graphics g) {
	Graphics2D g2D = (Graphics2D) g;

	int width = (int) (this.getEnd().getX() - this.getStart().getX());
	int height = (int) (this.getEnd().getY() - this.getStart().getY());

	g2D.setColor(this.getColor());

	int upperLeftX;
	int upperLeftY;

	if (width < 0) {
	    upperLeftX = (int) this.getEnd().getX();
	} else {
	    upperLeftX = (int) this.getStart().getX();
	}

	if (height < 0) {
	    upperLeftY = (int) this.getEnd().getY();
	} else {
	    upperLeftY = (int) this.getStart().getY();
	}

	g2D.fillRect(upperLeftX, upperLeftY, Math.abs(width), Math.abs(height));
    }
}