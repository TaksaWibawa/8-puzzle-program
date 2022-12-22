package Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SliderPuzzle extends JFrame implements ActionListener{
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,shuffle,solution;
    static int counter = 0;
    static JLabel counterLabel;
    List<Integer> solutionList = new ArrayList<>(); //list for initial

    public SliderPuzzle(){
        //Set up the window (frame)
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create elements
        b1 = new JButton("1");
        b2 = new JButton(" ");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("2");

        solutionList.add(1);
        solutionList.add(0);
        solutionList.add(3);
        solutionList.add(4);
        solutionList.add(5);
        solutionList.add(6);
        solutionList.add(7);
        solutionList.add(8);
        solutionList.add(2);

        shuffle = new JButton("Shuffle!");
        solution = new JButton("Solution!");
        counterLabel = new JLabel("Moves: 0");

        //Attach elements to window
        add(b1);add(b2);add(b3);add(b4);add(b5);add(b6);add(b7);add(b8);add(b9); add(shuffle); add(solution);
        Container contentPane = this.getContentPane();
        contentPane.add(counterLabel);

        //Put elements on the right places of window (Frame)
        b1.setBounds(90,60,50,40);
        b2.setBounds(160,60,50,40);
        b3.setBounds(230,60,50,40);
        b4.setBounds(90,115,50,40);
        b5.setBounds(160,115,50,40);
        b6.setBounds(230,115,50,40);
        b7.setBounds(90,170,50,40);
        b8.setBounds(160,170,50,40);
        b9.setBounds(230,170,50,40);
        shuffle.setBounds(110,230,150,40);
        solution.setBounds(110,290,150,40);
        counterLabel.setBounds(145,15,180,40);

        //Customize elements
        shuffle.setBackground(Color.LIGHT_GRAY);
        solution.setBackground(Color.LIGHT_GRAY);

        b1.setBackground(Color.decode("#5adbb5"));
        b2.setBackground(Color.decode("#5adbb5"));
        b3.setBackground(Color.decode("#5adbb5"));
        b4.setBackground(Color.decode("#5adbb5"));
        b5.setBackground(Color.decode("#5adbb5"));
        b6.setBackground(Color.decode("#5adbb5"));
        b7.setBackground(Color.decode("#5adbb5"));
        b8.setBackground(Color.decode("#5adbb5"));
        b9.setBackground(Color.decode("#5adbb5"));

        b1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b3.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b4.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b5.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b6.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b7.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b8.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        b9.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        shuffle.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        solution.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        counterLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));

        //Add an event listener
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        shuffle.addActionListener(this);
        solution.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == shuffle){
            int temp = solutionList.get(3);
            String s = b4.getText();

            solutionList.set(3, solutionList.get(8));
            b4.setText(b9.getText());

            solutionList.set(8, temp);
            b9.setText(s);

            temp = solutionList.get(0);
            s = b1.getText();

            solutionList.set(0, solutionList.get(4));
            b1.setText(b5.getText());

            solutionList.set(4, temp);
            b5.setText(s);

            temp = solutionList.get(1);
            s = b2.getText();

            solutionList.set(1, solutionList.get(6));
            b2.setText(b7.getText());

            solutionList.set(6, temp);
            b7.setText(s);

            resetClick();
        }
        if(e.getSource() == solution){
            Logic puzzle = new Logic(solutionList);
            System.out.println(solutionList);
            if(puzzle.isSolvable(puzzle.initial)){
                int x = -1, y = -1;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        if(puzzle.initial[i][j] == 0){
                            x = i;
                            y = j;
                        }
                    }
                }
                try {
                    puzzle.solve(puzzle.initial, puzzle.goal, x, y);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                b1.setText("1"); solutionList.set(0, 1);
                b2.setText("2"); solutionList.set(1, 2);
                b3.setText("3"); solutionList.set(2, 3);
                b4.setText("4"); solutionList.set(3, 4);
                b5.setText("5"); solutionList.set(4, 5);
                b6.setText("6"); solutionList.set(5, 6);
                b7.setText("7"); solutionList.set(6, 7);
                b8.setText("8"); solutionList.set(7, 8);
                b9.setText(" "); solutionList.set(8, 0);
                JOptionPane.showMessageDialog(SliderPuzzle.this,"YOU WON!\n" + "Total Moves: " + counter);
            }
            else{
                JOptionPane.showMessageDialog(SliderPuzzle.this, "This puzzle is not solvable");
            }
            resetClick();
        }
        if(e.getSource() == b1){
            String s = b1.getText();
            if(b2.getText().equals(" ")){
                b2.setText(s); solutionList.set(1, solutionList.get(0));
                b1.setText(" "); solutionList.set(0, 0);

            }
            else if(b4.getText().equals(" ")){
                b4.setText(s); solutionList.set(3, solutionList.get(0));
                b1.setText(" "); solutionList.set(0, 0);
            }
        }
        if(e.getSource() == b2){
            String s=b2.getText();
            if(b1.getText().equals(" ")){
                b1.setText(s); solutionList.set(0, solutionList.get(1));
                b2.setText(" "); solutionList.set(1, 0);
            }
            else if(b3.getText().equals(" ")){
                b3.setText(s); solutionList.set(2, solutionList.get(1));
                b2.setText(" "); solutionList.set(1, 0);
            }
            else if(b5.getText().equals(" ")){
                b5.setText(s); solutionList.set(4, solutionList.get(1));
                b2.setText(" "); solutionList.set(1, 0);
            }
        }
        if(e.getSource() == b3){
            String s=b3.getText();
            if(b2.getText().equals(" ")){
                b2.setText(s); solutionList.set(1, solutionList.get(2));
                b3.setText(" "); solutionList.set(2, 0);
            }
            else if(b6.getText().equals(" ")){
                b6.setText(s); solutionList.set(5, solutionList.get(2));
                b3.setText(" "); solutionList.set(2, 0);
            }
        }
        if(e.getSource() == b4){
            String s=b4.getText();
            if(b1.getText().equals(" ")){
                b1.setText(s); solutionList.set(0, solutionList.get(3));
                b4.setText(" "); solutionList.set(3, 0);
            }
            else if(b7.getText().equals(" ")){
                b7.setText(s); solutionList.set(6, solutionList.get(3));
                b4.setText(" "); solutionList.set(3, 0);
            }
            else if(b5.getText().equals(" ")){
                b5.setText(s); solutionList.set(4, solutionList.get(3));
                b4.setText(" "); solutionList.set(3, 0);
            }
        }
        if(e.getSource() == b5){
            String s=b5.getText();
            if(b2.getText().equals(" ")){
                b2.setText(s);  solutionList.set(1, solutionList.get(4));
                b5.setText(" "); solutionList.set(4, 0);
            }
            else if(b4.getText().equals(" ")){
                b4.setText(s); solutionList.set(3, solutionList.get(4));
                b5.setText(" "); solutionList.set(4, 0);
            }
            else if(b6.getText().equals(" ")){
                b6.setText(s); solutionList.set(5, solutionList.get(4));
                b5.setText(" ");solutionList.set(4, 0);
            }
            else if(b8.getText().equals(" ")){
                b8.setText(s); solutionList.set(7, solutionList.get(4));
                b5.setText(" ");solutionList.set(4, 0);
            }
        }
        if(e.getSource() == b6){
            String s=b6.getText();
            if(b9.getText().equals(" ")){
                b9.setText(s); solutionList.set(8, solutionList.get(5));
                b6.setText(" "); solutionList.set(5, 0);
            }
            else if(b3.getText().equals(" ")){
                b3.setText(s); solutionList.set(2, solutionList.get(5));
                b6.setText(" "); solutionList.set(5, 0);
            }
            else if(b5.getText().equals(" ")){
                b5.setText(s); solutionList.set(4, solutionList.get(5));
                b6.setText(" "); solutionList.set(5, 0);
            }
        }
        if(e.getSource() == b7){
            String s=b7.getText();
            if(b4.getText().equals(" ")){
                b4.setText(s); solutionList.set(3, solutionList.get(6));
                b7.setText(" "); solutionList.set(6, 0);
            }
            else if(b8.getText().equals(" ")){
                b8.setText(s); solutionList.set(7, solutionList.get(6));
                b7.setText(" "); solutionList.set(6, 0);
            }
        }
        if(e.getSource() == b8){
            String s=b8.getText();
            if(b7.getText().equals(" ")){
                b7.setText(s); solutionList.set(6, solutionList.get(7));
                b8.setText(" "); solutionList.set(7, 0);
            }
            else if(b9.getText().equals(" ")){
                b9.setText(s); solutionList.set(8, solutionList.get(7));
                b8.setText(" "); solutionList.set(7, 0);
            }
            else if(b5.getText().equals(" ")){
                b5.setText(s); solutionList.set(4, solutionList.get(7));
                b8.setText(" "); solutionList.set(7, 0);
            }
        }
        if(e.getSource() == b9){
            String s=b9.getText();
            if(b6.getText().equals(" ")){
                b6.setText(s); solutionList.set(5, solutionList.get(8));
                b9.setText(" "); solutionList.set(8, 0);
            }
            else if(b8.getText().equals(" ")){
                b8.setText(s); solutionList.set(7, solutionList.get(8));
                b9.setText(" "); solutionList.set(8, 0);
            }

            if(b1.getText().equals("1")&&b2.getText().equals("2")&&b3.getText().equals("3")&&b4.getText().equals("4")&&b5.getText().equals("5")&&b6.getText().equals("6")&&b7.getText().equals("7")&&b8.getText().equals("8")&&b9.getText().equals(" ")){
                JOptionPane.showMessageDialog(SliderPuzzle.this,"YOU WON!\n" + "Total Moves: " + counter);
                solutionList.set(0, 1);
                solutionList.set(1, 2);
                solutionList.set(2, 3);
                solutionList.set(3, 4);
                solutionList.set(4, 5);
                solutionList.set(5, 6);
                solutionList.set(6, 7);
                solutionList.set(7, 8);
                solutionList.set(8, 0);
                resetClick();
            }
        }
        addClick();
    }
    public void resetClick(){
        counter = -1;
        counterLabel.setText("Moves: 0");
    }
    public static void addClick(){
        counter++;
        counterLabel.setText("Moves: " + counter);
    }
}
