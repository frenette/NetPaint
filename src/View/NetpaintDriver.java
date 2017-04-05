package View;

import java.awt.Dimension;

import javax.swing.JFrame;

public class NetpaintDriver {

    public static void main(String[] args) {
	Canvas canvas = new Canvas(new Dimension(600, 900));

	JFrame jFrame = new JFrame();
	jFrame.add(canvas);

	jFrame.setSize(600, 900);
	jFrame.setVisible(true);
	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
