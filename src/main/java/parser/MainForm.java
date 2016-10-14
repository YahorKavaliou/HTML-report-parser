package parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class MainForm extends JFrame implements ActionListener {
    private JButton buttonOpenFile;
    private JButton buttonParseFile;
    private JTextArea textArea;
    private JPanel myFrame;

    private String pathToFile = "";

    public static void createAndShowGUI(){
        MainForm mainForm = new MainForm();
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(mainForm.myFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.prepareUI();
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonOpenFile){
            final JFileChooser jFileChooser = new JFileChooser();
            int returnVal = jFileChooser.showOpenDialog(MainForm.this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                pathToFile = jFileChooser.getSelectedFile().getAbsolutePath();
                textArea.setText("Selected file: " + pathToFile);
                buttonParseFile.setEnabled(true);
            }else if(returnVal == JFileChooser.CANCEL_OPTION){
                textArea.setText("Cancelled");
            }else if(returnVal == JFileChooser.ERROR_OPTION){
                textArea.setText("Error!");
            }else{
                textArea.setText("unknown...");
            }
        }

        if(e.getSource() == buttonParseFile){
            java.util.List<Scenario> scenarios = new ArrayList<>();
            try {
                scenarios = HTMLReader.readFile(pathToFile);
            }
            catch(IOException ex){
                textArea.setText("Error while parsing the file!");
            }
            CSVWriter.writeCsvFile("4.csv", scenarios);
        }
    }

    private void prepareUI(){
        textArea.setEditable(false);
        buttonOpenFile.addActionListener(this);
        buttonParseFile.addActionListener(this);
        buttonParseFile.setEnabled(false);
    }
}
