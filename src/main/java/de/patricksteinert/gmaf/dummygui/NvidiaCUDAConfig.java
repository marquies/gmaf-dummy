package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NvidiaCUDAConfig extends JMenuItem implements ActionListener {

	private Frame parent;

	public NvidiaCUDAConfig(Frame frame) {
		setText("CUDA Configuration");
		parent = frame;
		addActionListener(this);
	}

	
	
	public void actionPerformed(ActionEvent e) {
		// open Config Dialog
		NvidiaCUDAConfigDialog dialog = new NvidiaCUDAConfigDialog(parent, "localhost:4711");
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		dialog.setVisible(true);
	}
}
