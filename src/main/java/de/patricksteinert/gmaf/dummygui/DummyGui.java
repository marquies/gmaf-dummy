package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Patrick Steinert on 04.04.22.
 */
public class DummyGui {

    public static void main(String[] args) {
        DummyGui g = new DummyGui();
        g.runDummy();
    }

    public void runDummy() {
        JFrame f = new JFrame();//creating instance of JFrame


        Border bo = new LineBorder(Color.yellow);
        JMenuBar bar = new JMenuBar();
        bar.setBorder(bo);
        JMenu menu = new JMenu("GMAF Menu");

        //JMenuItem item = new JMenuItem("Ich bin das JMenuItem");
        NvidiaCUDAConfig item = new NvidiaCUDAConfig(f);
        menu.add(item);
        bar.add(menu);
        f.setJMenuBar(bar);

        NvidiaCUDAConfigDialog dialog = new NvidiaCUDAConfigDialog(f, "localhost:4711");


        DummyGui dummy = this;

        JButton clickToConfig = new JButton("click to config");//creating instance of JButton
        clickToConfig.setBounds(130, 10, 150, 40);//x axis, y axis, width, height
        clickToConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        f.add(clickToConfig);//adding button in JFrame

        JButton clickToGenConfig = new JButton("click to generate config");//creating instance of JButton
        clickToGenConfig.setBounds(130, 60, 150, 40);//x axis, y axis, width, height
        clickToGenConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NvidiaCUDAGenerateConfigDialog genConfigdialog = new NvidiaCUDAGenerateConfigDialog(f);

                genConfigdialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                genConfigdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                genConfigdialog.pack();
                genConfigdialog.setVisible(true);
            }
        });
        f.add(clickToGenConfig);//adding button in JFrame

        GraphCodeMeta gc = new GraphCodeMeta("post_100.wapo.json", null);


        JButton clickToQuery = new JButton("click to query");//creating instance of JButton
        clickToQuery.setBounds(130, 110, 150, 40);//x axis, y axis, width, height
        clickToQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NvidiaCUDAProcessor ncp = new NvidiaCUDAProcessor();
                ncp.setQueryObject(gc);
                String configString = dialog.getValidatedText();
                if (configString == null) {
                    System.err.println("Please config first");

                    JOptionPane.showMessageDialog(f, "Please config first.");

                }
                String[] connectionString = configString.split(":");
                ncp.setHost(connectionString[0]);
                ncp.setPort(Integer.parseInt(connectionString[1]));
                try {
                    ncp.execute();
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(f, "Execution failed: " + exception.getMessage());
                }
            }
        });

        f.add(clickToQuery);

        f.setSize(400, 300);
        f.setLayout(null);
        f.setVisible(true);

    }
}

