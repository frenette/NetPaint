package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line extends PaintObject {

    public Line(Color color, Point start, Point end) {
	super(color, start, end);
    }

    @Override
    public void draw(Graphics g) {
	Graphics2D g2D = (Graphics2D) g;

	g2D.setColor(this.getColor());
	g2D.drawLine((int) this.getStart().getX(), (int) this.getStart().getY(), (int) this.getEnd().getX(),
		(int) this.getEnd().getY());
    }
}