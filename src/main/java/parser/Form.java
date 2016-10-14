package parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Form extends JFrame implements ActionListener {
    JTextArea textArea;
    JButton buttonOpenFile;
    JButton buttonParseFile;
    private String pathToFile = "";

    public static void createAndShowGUI() {
        Form myFrame = new Form();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.prepareUI();
        myFrame.pack();
        myFrame.setVisible(true);
    }

    private void prepareUI(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane panel = new JScrollPane(textArea);
        panel.setPreferredSize(new Dimension(200, 50));
        buttonParseFile = new JButton("Parse file");
        buttonOpenFile = new JButton("Open File");
        buttonOpenFile.addActionListener(this);
        buttonParseFile.addActionListener(this);
        buttonParseFile.setEnabled(false);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonOpenFile, BorderLayout.PAGE_START);
        getContentPane().add(buttonParseFile, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonOpenFile){
            final JFileChooser jFileChooser = new JFileChooser();
            int returnVal = jFileChooser.showOpenDialog(Form.this);
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
            List<Scenario> scenarios = new ArrayList<>();
            try {
                scenarios = HTMLReader.readFile(pathToFile);
            }
            catch(IOException ex){
                textArea.setText("Error while parsing the file!");
            }
            CSVWriter.writeCsvFile("4.csv", scenarios);
        }
    }
}
