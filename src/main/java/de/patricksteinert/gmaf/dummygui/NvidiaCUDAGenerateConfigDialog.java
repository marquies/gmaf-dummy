package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NvidiaCUDAGenerateConfigDialog extends JDialog
        implements ActionListener,
        PropertyChangeListener {
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
        model.addElement("pmper_cuda");
        model.addElement("pcpr_cuda");

        JComboBox cb = new JComboBox(model);

        Object[] array = {msgString, cb};

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

                if (true) {
                    closeDialog();
                } else {
                    //text was invalid
                    JOptionPane.showMessageDialog(
                            NvidiaCUDAGenerateConfigDialog.this,
                            "Sorry, \"" + typedText + "\" "
                                    + "isn't a valid host:port.\n",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    typedText = null;
                }
            } else {
                typedText = null;
                closeDialog();
            }
        }
    }

    /**
     */
    public void closeDialog() {
        setVisible(false);
    }

}