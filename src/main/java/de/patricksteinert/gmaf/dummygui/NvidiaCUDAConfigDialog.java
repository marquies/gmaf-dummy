package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI component for the configuration dialog.
 */
public class NvidiaCUDAConfigDialog extends JDialog
        implements ActionListener,
        PropertyChangeListener {
    /**
     * Regex patten field for input validation.
     */
    private final Pattern pattern;

    /**
     * Base UI.
     */
    private final DummyGui gui;

    /**
     * Connection string regex pattern for input validation.
     */
    private String regex = "^[A-Za-z0-9.-]*:[0-9]{1,4}$";

    /**
     * Input text element for validation.
     */
    private String typedText = null;

    /**
     * Connection string input filed.
     */
    private JTextField textField;

    /**
     * Input/Output connection string.
     */
    private String connectionString;

    /**
     * UI Component.
     */
    private JOptionPane optionPane;

    /**
     * OK button text.
     */
    private String btnString1 = "Enter";

    /**
     * Cancel button text.
     */
    private String btnString2 = "Cancel";

    /**
     * Creates the reusable dialog.
     *
     * @param gui                     the parent frame.
     * @param currentConnectionString the currently used connection string.
     */
    public NvidiaCUDAConfigDialog(DummyGui gui, String currentConnectionString) {

        super(gui.f, true);
        this.gui = gui;

        pattern = Pattern.compile(regex);

        connectionString = currentConnectionString.toLowerCase();
        setTitle("CUDA Configuration");

        textField = new JTextField(10);
        textField.setText(connectionString);


        String msgString1 = "Set the CUDA processor instance host:port";
        Object[] array = {msgString1, textField};

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

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });

        textField.addActionListener(this);
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
                typedText = textField.getText();
                String ucText = typedText.toLowerCase();

                Matcher m = pattern.matcher(ucText);

                if (m.matches()) {
                    gui.setConnectionString(ucText);
                    closeDialog();
                } else {
                    //text was invalid
                    textField.selectAll();
                    JOptionPane.showMessageDialog(
                            NvidiaCUDAConfigDialog.this,
                            "Sorry, \"" + typedText + "\" "
                                    + "isn't a valid host:port.\n",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    typedText = null;
                    textField.requestFocusInWindow();
                }
            } else {
                typedText = null;
                closeDialog();
            }
        }
    }

    /**
     * Call to close the dialog.
     */
    public void closeDialog() {
        setVisible(false);
    }

}