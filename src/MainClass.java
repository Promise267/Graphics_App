import uk.ac.leedsbeckett.oop.LBUGraphics;
import javax.swing.*;

class MainClass extends LBUGraphics {

    MainClass() {
        JFrame frame = new JFrame();
        about();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainClass();
    }
    @Override
    public void processCommand(String s) {

    }
}