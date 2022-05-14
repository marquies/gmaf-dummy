package de.patricksteinert.gmaf.dummygui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Application starter creating the basic user interface.
 * <p>
 * <p>
 * Created by Patrick Steinert on 04.04.22.
 */
public class DummyGui {
    public JFrame f;


    private String connectionString = "localhost:4711";

    /**
     * Main function of the application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        DummyGui g = new DummyGui();
        g.runDummy();
    }

    /**
     * Start method to create the user interface.
     */
    public void runDummy() {

        f = new JFrame();


        Border bo = new LineBorder(Color.yellow);
        JMenuBar bar = new JMenuBar();
        bar.setBorder(bo);
        JMenu menu = new JMenu("GMAF Menu");

        NvidiaCUDAConfig item = new NvidiaCUDAConfig(this);
        menu.add(item);
        bar.add(menu);
        f.setJMenuBar(bar);

        NvidiaCUDAConfigDialog dialog = new NvidiaCUDAConfigDialog(this, connectionString);

        DummyGui dummy = this;

        JButton clickToConfig = new JButton("click to config");
        clickToConfig.setBounds(130, 10, 150, 40);
        clickToConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        f.add(clickToConfig);

        JButton clickToGenConfig = new JButton("click to generate config");
        clickToGenConfig.setBounds(130, 60, 150, 40);
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


        JButton clickToQuery = new JButton("click to query");
        clickToQuery.setBounds(130, 110, 150, 40);
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

    /**
     * Getter for the connection string.
     * @return the connection string
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * Setter for the connection string.
     * 
     * @param connectionString connection string.
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

}

