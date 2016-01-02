package gui;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

/**
 * Created by Administrator on 1/2/2016.
 */
public class EditEntryFrame extends JFrame {

    private JTextField varNameBox;
    private JTextField varValueBox;
    private String varName = "";
    private String varValue = "";

    private String[] outputValues = {null,null};

    private final Dimension INPUT_BOX_DIMENSION = new Dimension(70,20);

    public EditEntryFrame(String name)
    {
        super(name);

        initUIElements();
    }
    public EditEntryFrame(String name, String varName, String varValue)
    {
        super(name);

        this.varName = varName;
        this.varValue = varValue;

        initUIElements();
    }
    public EditEntryFrame(String name,String[] vars)
    {
        super(name);
        this.varName = vars[0];
        this.varValue = vars[1];

        initUIElements();
    }

    private void initUIElements() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new DimensionUIResource(200, 100));
        this.pack();

        JPanel panel = new JPanel();

        varNameBox = new JTextField();
        varValueBox = new JTextField();

        varNameBox.setPreferredSize(INPUT_BOX_DIMENSION);
        varValueBox.setPreferredSize(INPUT_BOX_DIMENSION);

        varNameBox.setText(varName);
        varValueBox.setText(varValue);

        panel.add(varNameBox);
        panel.add(varValueBox);

        this.getContentPane().add(panel);

        this.setVisible(true);

        this.addWindowListener(new CustomWindowListener());
    }

    private void setOutValues(String varName,String varValue)
    {
        outputValues[0] = varName;
        outputValues[1] = varValue;
    }
    public boolean isValuesSet()
    {
        return outputValues[0] != null && outputValues[1] != null;
    }
    public String[] getOutValues()
    {
        return outputValues;
    }



    class CustomWindowListener implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            if(!varNameBox.getText().isEmpty() && !varValueBox.getText().isEmpty())
            setOutValues(varNameBox.getText(),varValueBox.getText());
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

}
