package parser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class MainForm extends JFrame implements ActionListener {
    private JButton buttonOpenFile;
    private JButton buttonParseFile;
    private JTextArea textArea;
    private JPanel mainPanel;
    private JComboBox comboBoxOutputFormat;
    private JRadioButton radioButtonAllScenarios;
    private JRadioButton radioButtonSuccessScenarios;
    private JRadioButton radioButtonFailedScenarios;

    private static JFrame frame;
    private String pathToFile = "";
    private String outputFormat = ".csv";

    public static void createAndShowGUI(){
        MainForm mainForm = new MainForm();
        mainForm.prepareUI(mainForm);
    }

    private void prepareUI(MainForm mainForm){
        frame = new JFrame("MainForm");
        frame.setContentPane(mainForm.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(650, 250);
        frame.setVisible(true);

        textArea.setEditable(false);

        buttonOpenFile.addActionListener(this);
        buttonParseFile.addActionListener(this);
        buttonParseFile.setEnabled(false);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonAllScenarios);
        group.add(radioButtonSuccessScenarios);
        group.add(radioButtonFailedScenarios);

        radioButtonAllScenarios.setSelected(true);
        radioButtonAllScenarios.addActionListener(this);
        radioButtonSuccessScenarios.addActionListener(this);
        radioButtonFailedScenarios.addActionListener(this);

        comboBoxOutputFormat.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //click buttonOpenFile event
        if(e.getSource() == buttonOpenFile){
            final JFileChooser jFileChooser = new JFileChooser();
            int returnVal = jFileChooser.showOpenDialog(MainForm.this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                pathToFile = jFileChooser.getSelectedFile().getAbsolutePath();
                consoleMessage("Selected file: " + pathToFile);
                buttonParseFile.setEnabled(true);
            }else if(returnVal == JFileChooser.CANCEL_OPTION){
                consoleMessage("Cancelled");
            }else if(returnVal == JFileChooser.ERROR_OPTION){
                consoleMessage("Error!");
            }else{
                consoleMessage("unknown...");
            }
        }

        //click buttonParseFile event
        if(e.getSource() == buttonParseFile){
            List<Scenario> scenarios = new ArrayList<>();
            try {
                scenarios = HTMLReader.readFile(pathToFile);

                final JFileChooser jFileChooser = new JFileChooser();

                String formatComment = outputFormat.equals(".csv") ? "CSV File" : "Excel File";

                FileFilter filter = new FileNameExtensionFilter(formatComment, outputFormat);
                jFileChooser.setFileFilter(filter);

                int returnVal = jFileChooser.showSaveDialog(MainForm.this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    pathToFile = jFileChooser.getSelectedFile().getAbsolutePath();
                }

                CSVWriter.writeCsvFile(pathToFile + outputFormat, scenarios);
            }
            catch(IOException ex){
                consoleMessage("Error while parsing the file!");
            }
        }

        //set radio button event
        if(e.getSource() == radioButtonAllScenarios || e.getSource() == radioButtonSuccessScenarios || e.getSource() == radioButtonFailedScenarios){
            JRadioButton radioButton = (JRadioButton) e.getSource();

            if(radioButton.getText().equals("All scenarios")){
                consoleMessage("All scenarios radio button was set");
            }
            else if(radioButton.getText().equals("Success Scenarios")){
                consoleMessage("Success Scenarios radio button was set");
            }
            else if(radioButton.getText().equals("Failed Scenarios")){
                consoleMessage("Failed Scenarios radio button was set");
            }
        }

        //set dropdown box event
        if(e.getSource() == comboBoxOutputFormat){
            outputFormat = comboBoxOutputFormat.getSelectedItem().toString();
            consoleMessage(outputFormat + " item was added");
        }
    }

    private void consoleMessage(String message){
        final String newline = "\n";

        if(textArea.getText().isEmpty()){
            textArea.setText(newline + message);
        }
        else {
            textArea.append(newline + message);
        }
    }
}
