package View;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import model.PaintObjectCollection;

public class Netpaint extends JComponent {



    public Netpaint() {


	this.addMouseMotionListener(new MouseMotionListener() {
	});

	this.addMouseListener(new MouseListener() {



	    @Override
	    public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed(MouseEvent e): " + e);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased(MouseEvent e): " + e);
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered(MouseEvent e): " + e);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited(MouseEvent e): " + e);
	    }
	});
    }

    public void paintComponent(Graphics g) {
	Graphics2D graphics2D = (Graphics2D) g;

    }
}
