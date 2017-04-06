package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	color.getSelectionModel().addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Color: " + color.getColor());
		canvas.setCurrentColor(color.getColor());
	    }

	});

	ButtonGroup buttonGroup = new ButtonGroup();
	JPanel buttonDisplay = new JPanel();
	JRadioButton oval = new JRadioButton("Oval");
	oval.addActionListener((ActionEvent e) -> {
	    System.out.println("oval");
	    canvas.setPaintObjectType("oval");
	});
	buttonDisplay.add(oval);
	JRadioButton rectangle = new JRadioButton("Rectangle");
	rectangle.addActionListener((ActionEvent e) -> {
	    System.out.println("rectangle");
	    canvas.setPaintObjectType("rectangle");
	});
	buttonDisplay.add(rectangle);
	JRadioButton line = new JRadioButton("Line");
	line.addActionListener((ActionEvent e) -> {
	    System.out.println("line");
	    canvas.setPaintObjectType("line");
	});
	buttonDisplay.add(line);
	JRadioButton image = new JRadioButton("Image");
	image.addActionListener((ActionEvent e) -> {
	    System.out.println("image");
	    canvas.setPaintObjectType("image");
	});
	buttonDisplay.add(image);
	jFrame.add(buttonDisplay, BorderLayout.CENTER);

	color.setMinimumSize(new Dimension(400, 200));
	jFrame.add(color, BorderLayout.SOUTH);

	jFrame.setSize(600, 900);
	jFrame.setVisible(true);
	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * Inner ButtonListener class
     */

    private class ButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    String val = ((JRadioButton) e.getSource()).getText();
	    System.out.println(val);
	}

    }
}
