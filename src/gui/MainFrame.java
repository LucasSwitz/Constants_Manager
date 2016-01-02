package gui;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.DimensionUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Administrator on 1/1/2016.
 */
public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JScrollPane tablePane;
    private JTable constantsTable;

    public MainFrame(String name, int width, int height)
    {
        super(name);
        this.setPreferredSize(new DimensionUIResource(width,height));

        initUIElements();
    }


    private void initUIElements()
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        mainPanel = new JPanel();
        constantsTable = new JTable(new ContantsTableModel("C:\\Users\\Administrator\\Desktop\\Contants.txt"));
        tablePane = new JScrollPane(constantsTable);

        this.getContentPane().add(mainPanel);
        mainPanel.add(tablePane);


        constantsTable.addMouseListener(new TableListener());
    }
    class TableListener implements MouseInputListener
    {
        JPopupMenu menu;
        JMenuItem addEntry,deleteEntry,editEntry;

        public TableListener()
        {
            menu = new JPopupMenu();

            addEntry = new JMenuItem();
            addEntry.setAction(new AddEntryAction("Add Entry"));
            menu.add(addEntry);

            deleteEntry = new JMenuItem();
            deleteEntry.setAction(new DeleteEntryAction("Delete Entry"));

            editEntry = new JMenuItem();
            editEntry.setAction(new EditEntryAction("Edit Entry"));
        }

        private void doPop(MouseEvent e)
        {
            menu.show(e.getComponent(),e.getX(),e.getY());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON3)
            {
                if(constantsTable.getSelectedColumn() !=  -1)
                {
                    menu.add(editEntry);
                    menu.add(deleteEntry);
                }
                doPop(e);
            }
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

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    class EditEntryAction extends AbstractAction {

        protected EditEntryFrame frame;
        private int row;
        public EditEntryAction(String name) {
            super(name);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            row = constantsTable.getSelectedRow();
            initFrame();
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    while (frame.isVisible()) {
                        try {
                            Thread.sleep(10);
                            if (frame.isValuesSet()) {
                                editTable();
                                break;
                            }
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            })).start();
        }

        protected void editTable() {
            ((ContantsTableModel) constantsTable.getModel()).editSet(row, frame.getOutValues()[0], frame.getOutValues()[1]);
        }

        protected void initFrame() {
            String[] vars = ((ContantsTableModel)constantsTable.getModel()).getSet(row);
            frame = new EditEntryFrame("Edit Entry", vars);
        }


    }
    class DeleteEntryAction extends AbstractAction
    {
        public DeleteEntryAction(String name)
        {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = constantsTable.getSelectedRow();
            if (row != -1)
            {
                ((ContantsTableModel) constantsTable.getModel()).deleteSet(row);
            }
        }
    }

    class AddEntryAction extends EditEntryAction
    {
        public AddEntryAction(String name)
        {
            super(name);
        }
        @Override
        protected void initFrame()
        {
            frame = new EditEntryFrame("Add Entry");
        }

        @Override
        protected void editTable()
        {
            ((ContantsTableModel) constantsTable.getModel()).addSet(frame.getOutValues()[0], frame.getOutValues()[1]);
        }
    }
}



