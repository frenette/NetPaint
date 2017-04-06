package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image extends PaintObject {

    // private I

    public Image(Color color, Point start, Point end) {
	super(color, start, end);
    }

    @Override
    public void draw(Graphics g) {
	Graphics2D graphics = (Graphics2D) g;
	
	BufferedImage img = null;
	try {
	    img = ImageIO.read(new File("images/image_01.png"));
	} catch (IOException e) {
	    System.err.println(e);
	}
	
	int width = (int) (this.getEnd().getX() - this.getStart().getX());
	int height = (int) (this.getEnd().getY() - this.getStart().getY());

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
	
	
	graphics.drawImage(img, upperLeftX, upperLeftY, Math.abs(width), Math.abs(height), null);
    }
}
