package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UI configuration menu item.
 */
public class NvidiaCUDAConfig extends JMenuItem implements ActionListener {

    /**
     * Swing parent frame.
     */
    private DummyGui parentGui;

    /**
     * Constructor for the menu item.
     *
     * @param gui parent UI.
     */
    public NvidiaCUDAConfig(DummyGui gui) {
        setText("CUDA Configuration");
        parentGui = gui;
        addActionListener(this);
    }

    /**
     * Called on click of the menu item and opens a {@link NvidiaCUDAConfigDialog}.
     *
     * @param e the received event.
     */
    public void actionPerformed(ActionEvent e) {
        // open Config Dialog
        NvidiaCUDAConfigDialog dialog = new NvidiaCUDAConfigDialog(parentGui, "localhost:4711");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }
}
