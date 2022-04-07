package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NvidiaCUDAGenerateConfigDialog extends JDialog
        implements ActionListener,
        PropertyChangeListener {
    private final JComboBox cb;
    private final JSpinner jSpinner;
    private final JCheckBox enablePmpr;
    private final JSpinner jSpinner1;
    private String typedText = null;

    private String connectionString;
    private JOptionPane optionPane;

    private String btnString1 = "Enter";
    private String btnString2 = "Cancel";

    /**
     * Creates the reusable dialog.
     */
    public NvidiaCUDAGenerateConfigDialog(Frame aFrame) {

        super(aFrame, true);

        setTitle("CUDA Generate Configuration");

        String msgString = "Select algorithm:";


        DefaultComboBoxModel model = new DefaultComboBoxModel<String>();

        model.addElement("seq");
        model.addElement("pc_cpu_par");
        model.addElement("pc_cuda");
        model.addElement("pmpr_cuda");
        model.addElement("pcpr_cuda");

        cb = new JComboBox(model);

        String msgString1 = "File Limit";
        SpinnerModel spinnerModelodel = new SpinnerNumberModel(100, -1, 999999, 1);
        jSpinner = new JSpinner(spinnerModelodel);

        String msgString2 = "Enable PMPR at Threshold";
        enablePmpr = new JCheckBox();
        enablePmpr.setSelected(true);

        String msgString3 = "PMPR Threshold";
        SpinnerModel spinnerModelodel1 = new SpinnerNumberModel(500, -1, 10000, 1);
        jSpinner1 = new JSpinner(spinnerModelodel1);


        Object[] array = {msgString, cb, msgString1, jSpinner, msgString2, enablePmpr, msgString3, jSpinner1};

        Object[] options = {btnString1, btnString2};

        optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        setContentPane(optionPane);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {

                optionPane.setValue(new Integer(
                        JOptionPane.CLOSED_OPTION));
            }
        });
        optionPane.addPropertyChangeListener(this);
    }

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return typedText;
    }

    /**
     * This method handles events for the text field.
     */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    /**
     * This method reacts to state changes in the option pane.
     */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {

                closeDialog();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    File fileToSave = fileChooser.getSelectedFile();

                    try {
                        StringBuilder s = new StringBuilder();
                        s.append("s=" + cb.getSelectedItem() + "\n");
                        s.append("c=" + ((Integer) jSpinner.getValue()).intValue() + "\n");
                        s.append("e=" + enablePmpr.isSelected() + "\n");
                        s.append("t=" + ((Integer) jSpinner1.getValue()).intValue() + "\n");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave.getAbsolutePath()));
                        writer.write(s.toString());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                }

            } else {
                typedText = null;
                closeDialog();
            }
        }
    }

    /**
     *
     */
    public void closeDialog() {
        setVisible(false);
    }

}