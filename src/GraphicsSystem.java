import uk.ac.leedsbeckett.oop.LBUGraphics;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//GraphicsSystem Class extends contents of LBUGraphics class file
public class GraphicsSystem extends LBUGraphics {
    //stores value of radius passed as parameter in circle method
    private int radius;
    //mutator to store the radius value
    public void setRadius(int radius){
        this.radius = radius;
    }
    //accessor to get the radius
    public int getRadius(){
        return this.radius;
    }
    //overriding about method of base class
    @Override
    public void about() {
        //calling the base class about function's properties
        super.about();
        displayMessage("Promise Rijal");
    }
    //overriding circle method of base class
    @Override
    public void circle(int r) {
        //creating an object of Graphics2D class and linking it to the graphics2DContext method of base class
        Graphics2D g = this.getGraphics2DContext();
        clearInterface();
        g.setColor(PenColour);
        //draws a circle of required radius r and getX() anf getY() function takes the coordinates of the turtle
        g.drawOval(getxPos(),getyPos(),r,r);
    }

    //fill command to fill circle of particular radius
    public void fill(){
        Graphics2D g = this.getGraphics2DContext();
        g.setColor(PenColour);
        //fills circle of radius r and getX() anf getY() function takes the coordinates of the turtle
        g.fillOval(getxPos(),getyPos(),getRadius(),getRadius());
    }
    //colorPicker function works when we input rgb value of a color
    public void colorPicker(int a, int b, int c) {
        Color col = new Color(a, b, c);
        setPenColour(col);
    }
    //function to create a square
    public void square(int side) {
        forward(side);
        turnRight(90);
        forward(side);
        turnRight(90);
        forward(side);
        turnRight(90);
        forward(side);
        turnRight(90);
    }

    //function to create a pentagon
    public void pentagon(int side) {
        forward(side);
        turnRight(288);
        forward(side);
        turnRight(288);
        forward(side);
        turnRight(288);
        forward(side);
        turnRight(288);
        forward(side);
        turnRight(288);
    }

    //function to create a hexagon
    public void hexagon(int side) {
        forward(side);
        turnRight(300);
        forward(side);
        turnRight(300);
        forward(side);
        turnRight(300);
        forward(side);
        turnRight(300);
        forward(side);
        turnRight(300);
        forward(side);
        turnLeft(60);
    }
    //function to create an equilateral triangle
    public void triangle(int side) {
        forward(side);
        turnRight(240);
        forward(side);
        turnRight(240);
        forward(side);
        turnRight(240);
    }

    //function to check if only two parameter are entered to create a triangle
    public void triangle(int sideA, int sideB) {
        displayMessage("Valid Command With Missing Parameter");
    }

    //function to create a scalene triangle
    public void triangle(int sideA, int sideB, int sideC) {
        //formula to determine the angle between angles when length is given
        int angle_A = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(sideB, 2) + Math.pow(sideC, 2) - Math.pow(sideA, 2)) / (2 * sideB * sideC))));
        int angle_B = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(sideA, 2) + Math.pow(sideC, 2) - Math.pow(sideB, 2)) / (2 * sideA * sideC))));
        int angle_C = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(sideA, 2) + Math.pow(sideB, 2) - Math.pow(sideC, 2)) / (2 * sideA * sideB))));
        int sum = angle_A + angle_B + angle_C;
        //check whether the sum of angles is equal to 180 and length of a side is less the sum of other two sides
        if ((sum == 180) && (sideA < sideB + sideC) && (sideB < sideA + sideC) && (sideC < sideA + sideB)) {
            //reducing the angle by 180 so the sides converge with one another at the equivalent points
            angle_A = 180 - angle_A;
            angle_B = 180 - angle_B;
            angle_C = 180 - angle_C;
            forward(sideC);
            turnRight(angle_B);
            forward(sideA);
            turnRight(angle_C);
            forward(sideB);
            turnRight(angle_A);
            clearInterface();
        } else {
            displayMessage("Triangle Cannot Be Drawn");
        }
    }

    //function to save the command entered in the text box
    public void save(String s) {
        //try catch function to test the error while the code os being executed
        try {
            //creates a new bufferwriter object to write the commands into a file and append 'true' means to append it to input.txt file
            BufferedWriter bw_inp = new BufferedWriter(new FileWriter("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\input.txt", true));
            //checks whether the command is 'save' or 'load'
            if (s.equals("save") || s.equals("load")) {
                //creates a new bufferwriter object to write the only 'save' or 'load' commands into a file and append 'true' means to append it to delete.txt file
                BufferedWriter bw_del = new BufferedWriter(new FileWriter("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\delete.txt", true));
                bw_del.write(s + "\n");
                bw_del.close();
                //if the command was 'save' then it takes a screenshot of frame
                if (s.equals("save")) {
                    BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    //create graphics of the img object
                    Graphics2D graphics2D = img.createGraphics();
                    //prints the graphics
                    printAll(graphics2D);
                    //clears the memory
                    graphics2D.dispose();
                    ImageIO.write(img, "jpg", new File("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\image.jpg"));
                }
            }
            bw_inp.write(s + "\n");
            bw_inp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //load function to load the commands
    public void load() {
        try {
            String sCurrentLine;
            String lastLine = "";
            //reads the input.txt file where all the valid commands are stored
            BufferedReader br = new BufferedReader(new FileReader("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\input.txt"));
            //checks if the txt file contains any null avlue till the end of file
            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;
                //checks if the last command was 'save'
                if (lastLine.equals("save")) {
                    //creates a new file called output.txt file and writes into that file
                    PrintWriter pw = new PrintWriter("output.txt");
                    //reads the delete file which contains the 'save' or 'load' command
                    BufferedReader br2 = new BufferedReader(new FileReader("delete.txt"));

                    String line2 = br2.readLine();
                    //creating an object of hashset
                    HashSet<String> hs = new HashSet<String>();
                    while (line2 != null) {
                        hs.add(line2);
                        line2 = br2.readLine();
                    }
                    //reads the input.txt file
                    BufferedReader br1 = new BufferedReader(new FileReader("input.txt"));
                    String line1 = br1.readLine();
                    while (line1 != null) {
                        if (!hs.contains(line1))
                            pw.println(line1);
                        line1 = br1.readLine();
                    }
                    //flushes the stream of printwriter object
                    pw.flush();
                    br1.close();
                    br2.close();
                    pw.close();
                    String currentline;
                    String command = "";
                    //reads the commands appended in the output.txt file
                    BufferedReader br_loop = new BufferedReader(new FileReader("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\output.txt"));
                    while ((currentline = br_loop.readLine()) != null) {
                        clearInterface();
                        command = currentline;
                        //loops the commands of the file into the processCommand function
                        processCommand(command);
                    }
                    br_loop.close();
                } else {
                    displayMessage("Commands Not Saved");
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //constructor that creates a new frame when ever class's instance is made
    JFrame frame;
    //objects to create button
    JButton saveButton, loadButton, browseButton, colorButton, bgcolorButton;

    GraphicsSystem() {
        frame = new JFrame();
        about();
        frame.add(this);
        frame.pack();
        frame.setTitle("Turtle");
        frame.setResizable(true);
        //if exit button of the frame is clicked
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                //the two options of the display box
                String[] responses = {"Adios", "No"};
                //image of the icon is set to the required image
                ImageIcon icon = new ImageIcon(new ImageIcon("D:\\College\\SEMESTER 4\\OOPS\\Assessment\\warning.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                //the frame of the display box contains following contents
                int resp = JOptionPane.showOptionDialog(frame,
                        "Confirm Exit",
                        "Exit?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        icon,
                        responses,
                        0);
                //if 'Adios' button is clicked
                if (resp == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //if 'No' button is clicked
                } else {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        //new saveButton, loadButton, browseButton, pencolorButton and bgcolorButton is added the user interface

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setBounds(10, 10, 200, 200);
        saveButton.addActionListener(this);
        this.add(saveButton);

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.setBounds(10, 10, 200, 200);
        loadButton.addActionListener(this);
        this.add(loadButton);

        browseButton = new JButton();
        browseButton.setText("Browse");
        browseButton.setBounds(10, 10, 200, 200);
        browseButton.addActionListener(this);
        this.add(browseButton);

        colorButton = new JButton();
        colorButton.setText("Pen Color");
        colorButton.setBounds(10, 10, 200, 200);
        colorButton.addActionListener(this);
        this.add(colorButton);

        bgcolorButton = new JButton();
        bgcolorButton.setText("Background Color");
        bgcolorButton.setBounds(10, 10, 200, 200);
        bgcolorButton.addActionListener(this);
        this.add(bgcolorButton);


        frame.setVisible(true);
        penDown();
    }

    //when a button is pressed following are the actions performed
    @Override
    public void actionPerformed(ActionEvent e) {
        //calling the actionPerformed function of base class
        super.actionPerformed(e);

        //if saveButton is clicked
        if (e.getSource() == saveButton) {
            save("save");
            displayMessage("Commands Saved Successfully");
            saveButton.setEnabled(false);
        }
        //if loadButton is clicked
        if (e.getSource() == loadButton) {
            save("load");
            load();
            clearInterface();
            loadButton.setEnabled(false);
        }
        //if colorButton is clicked
        if (e.getSource() == colorButton) {
            clearInterface();
            //color chooser interface
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.black);
            setPenColour(color);
            save(String.valueOf(color));
        }
        //if bgcolorButton is clicked
        if (e.getSource() == bgcolorButton) {
            clearInterface();
            //color chooser interface
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.black);
            setBackground_Col(color);
            clear();
            save(String.valueOf(color));
        }

        //if browseButton is clicked
        if (e.getSource() == browseButton) {
            clearInterface();
            //filechooser interface
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(System.getProperty("user.home")));
            //filtering files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
            file.addChoosableFileFilter(filter);
            int res = file.showOpenDialog(null);
            //if the user clicks open in Jfilechooser
            if (res == JFileChooser.APPROVE_OPTION) {
                File selFile = file.getSelectedFile();
                String path = selFile.getAbsolutePath();
                try {
                    Desktop.getDesktop().open(new File(path));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    //main function
    public static void main(String[] args) {
        //create instance of class that extends LBUGraphics
        new GraphicsSystem();
    }

    @Override
    public void processCommand(String s) {
        //Matcher function sets a pattern in which it stores only integer values
        Matcher matcher_integer = Pattern.compile("\\d+").matcher(s);
        //creating an arraylist
        List<Integer> numbers = new ArrayList<>();
        //the Integer values are then stores in the array list
        while (matcher_integer.find()) {
            numbers.add(Integer.valueOf(matcher_integer.group()));
        }

        //converting the command into lowercase and removing the space in between
        s = s.toLowerCase().replaceAll("\\s", "");
        //an array containing all the valid commands that can be used in this program
        String[] commands = {"about", "clear", "reset", "pendown", "penup", "white", "red", "black", "green", "forward", "backward", "turnleft", "turnright", "exit", "fill", "circle", "triangle", "square", "pentagon", "hexagon", "load", "save"};
        //boolean variable result to check if the command String s is present in the array
        boolean result = Arrays.stream(commands).anyMatch(s::contains);
        //boolean variable to check if only integer are present in the parameter of processCommand
        boolean color_entered = s.matches("[0-9]+");

        //if only integers are entered
        if (color_entered) {
            colorPicker(numbers.get(0), numbers.get(1), numbers.get(2));
            clearInterface();
        }
        //if valid command is entered
        else if (result) {
            if (s.equals("about")) {
                save(s);
                about();
            }
            if (s.equals("clear")) {
                save(s);
                clear();
                clearInterface();
            }
            if (s.equals("reset")) {
                save(s);
                reset();
                turnLeft(90);
                clearInterface();
            }
            if (s.equals("pendown")) {
                save(s);
                penDown();
                clearInterface();
            }
            if (s.equals("penup")) {
                save(s);
                penUp();
                clearInterface();
            }
            if (s.equals("white")) {
                save(s);
                setPenColour(Color.white);
                clearInterface();
            }
            if (s.equals("black")) {
                save(s);
                setPenColour(Color.black);
                clearInterface();
            }
            if (s.equals("red")) {
                save(s);
                setPenColour(Color.red);
                clearInterface();
            }
            if (s.equals("green")) {
                save(s);
                setPenColour(Color.green);
                clearInterface();
            }
            if (s.equals("save")) {
                save(s);
                displayMessage("Commands Saved Successfully");
            }
            if (s.equals("load")) {
                save(s);
                load();
            }
            if (s.equals("exit")) {
                System.exit(0);
            }
            if (s.equals("fill")) {
                save(s);
                fill();
            }
            //if the valid command contains these following commands
            if (s.contains("forward") || s.contains("backward") || s.contains("turnleft") || s.contains("turnright") || s.contains("circle") || s.contains("triangle") || s.contains("square") || s.contains("pentagon") || s.contains("hexagon")) {
                if (s.equals("forward") || s.equals("backward") || s.equals("turnleft") || s.equals("turnright") || s.equals("circle") || s.equals("triangle") || s.equals("square") || s.equals("pentagon") || s.equals("hexagon")) {
                    displayMessage("Valid Command With Missing Parameter");
                } else {
                    displayMessage("Non Numeric Value Entered");
                }
                if (s.equals("forward" + "-" + numbers.get(0)) || s.equals("backward" + "-" + numbers.get(0)) || s.equals("turnleft" + "-" + numbers.get(0)) || s.equals("turnright" + "-" + numbers.get(0)) || s.equals("circle" + "-" + numbers.get(0)) || s.equals("triangle" + "-" + numbers.get(0)) || s.equals("square" + "-" + numbers.get(0)) || s.equals("pentagon" + "-" + numbers.get(0)) || s.equals("hexagon" + "-" + numbers.get(0))) {
                    displayMessage("Negative Parameter Entered");
                } else if (s.equals("forward" + numbers.get(0))) {
                    save(s);
                    forward(numbers.get(0));
                    clearInterface();
                } else if (s.equals("backward" + numbers.get(0))) {
                    save(s);
                    forward(-numbers.get(0));
                    clearInterface();
                } else if (s.equals("turnleft" + numbers.get(0))) {
                    save(s);
                    penDown();
                    turnLeft(numbers.get(0));
                    clearInterface();
                } else if (s.equals("turnright" + numbers.get(0))) {
                    save(s);
                    penDown();
                    turnRight(numbers.get(0));
                    clearInterface();
                } else if (s.equals("circle" + numbers.get(0))) {
                    save(s);
                    penDown();
                    //the mutator is called to set the value of passed parameter to radius variable
                    setRadius(numbers.get(0));
                    circle(numbers.get(0));
                } else if (s.equals("square" + numbers.get(0))) {
                    save(s);
                    penDown();
                    square(numbers.get(0));
                    clearInterface();
                } else if (s.equals("pentagon" + numbers.get(0))) {
                    save(s);
                    penDown();
                    pentagon(numbers.get(0));
                    clearInterface();
                } else if (s.equals("hexagon" + numbers.get(0))) {
                    save(s);
                    penDown();
                    hexagon(numbers.get(0));
                    clearInterface();
                } else if (s.equals("triangle" + numbers.get(0))) {
                    save(s);
                    penDown();
                    triangle(numbers.get(0));
                    clearInterface();
                } else if (s.equals("triangle" + numbers.get(0) + numbers.get(1)) || s.equals("triangle" + "-" + numbers.get(0) + "-" + numbers.get(1)) || s.equals("triangle" + numbers.get(0) + "-" + numbers.get(1)) || s.equals("triangle" + "-" + numbers.get(0) + numbers.get(1))) {
                    triangle(numbers.get(0), numbers.get(1));
                } else if (s.equals("triangle" + numbers.get(0) + numbers.get(1) + numbers.get(2))) {
                    save(s);
                    penDown();
                    triangle(numbers.get(0), numbers.get(1), numbers.get(2));
                } else if (s.equals("triangle" + "-" + numbers.get(0) + "-" + numbers.get(1) + "-" + numbers.get(2)) || s.equals("triangle" + numbers.get(0) + "-" + numbers.get(1) + "-" + numbers.get(2)) || s.equals("triangle" + "-" + numbers.get(0) + numbers.get(1) + "-" + numbers.get(2)) || s.equals("triangle" + "-" + numbers.get(0) + "-" + numbers.get(1) + numbers.get(2)) || s.equals("triangle" + "-" + numbers.get(0) + numbers.get(1) + numbers.get(2)) || s.equals("triangle" + numbers.get(0) + "-" + numbers.get(1) + numbers.get(2)) || s.equals("triangle" + numbers.get(0) + numbers.get(1) + "-" + numbers.get(2))) {
                    displayMessage("Negative Parameter Entered");
                } else {
                    displayMessage("Non Numeric Value Entered");
                }
            }
        } else {
            displayMessage("Invalid Command Entered");
        }
    }
}