package gui;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 1/2/2016.
 */
public class EditEntryFrame extends JFrame {

    private final Dimension INPUT_BOX_DIMENSION = new Dimension(70, 20);
    private JTextField varNameBox;
    private JTextField varValueBox;
    private JButton okayButton;
    private String varName = "";
    private String varValue = "";
    private String[] outputValues = {null, null};

    public EditEntryFrame(String name) {
        super(name);

        initUIElements();
    }

    public EditEntryFrame(String varName, String varValue) {
        super(varName);

        this.varName = varName;
        this.varValue = varValue;

        initUIElements();
    }
    public EditEntryFrame(ConstantsTableSet set)
    {
        this(set.getName(),set.getValue());
    }
    public EditEntryFrame(String[] vars) {
        this(vars[0],vars[1]);
    }

    private void initUIElements() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new DimensionUIResource(200, 100));
        this.pack();

        JPanel panel = new JPanel();

        varNameBox = new JTextField();
        varValueBox = new JTextField();

        okayButton = new JButton("OK");

        varNameBox.setPreferredSize(INPUT_BOX_DIMENSION);
        varValueBox.setPreferredSize(INPUT_BOX_DIMENSION);

        varNameBox.setText(varName);
        varValueBox.setText(varValue);

        panel.add(varNameBox);
        panel.add(varValueBox);
        panel.add(okayButton);

        this.getContentPane().add(panel);
        this.setVisible(true);

        okayButton.addMouseListener(new ConfirmationButtonListener());
    }

    private void setOutValues(String varName, String varValue) {
        outputValues[0] = varName;
        outputValues[1] = varValue;
    }

    public boolean isValuesSet() {
        return outputValues[0] != null && outputValues[1] != null;
    }

    public String[] getOutValues() {
        return outputValues;
    }

    private void killWindow() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public class ConfirmationButtonListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!varNameBox.getText().isEmpty() && !varValueBox.getText().isEmpty())
                setOutValues(varNameBox.getText(), varValueBox.getText());
            killWindow();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
