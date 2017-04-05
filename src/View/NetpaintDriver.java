package View;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class NetpaintDriver {

    public static void main(String[] args) {
	JFrame jFrame = new JFrame();
	jFrame.setLayout(new BorderLayout());
	
	Canvas canvas = new Canvas(new Dimension(600, 900));
	canvas.setPreferredSize(new Dimension(1500, 1000));

	JScrollPane scroll = new JScrollPane(canvas);
	scroll.setPreferredSize(new Dimension(400, 400));
	jFrame.add(scroll, BorderLayout.NORTH);
	
	JColorChooser color = new JColorChooser();
	color.setMinimumSize(new Dimension(400, 200));
	jFrame.add(color, BorderLayout.SOUTH);

	jFrame.setSize(600, 900);
	jFrame.setVisible(true);
	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
