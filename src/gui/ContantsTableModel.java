package gui;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 1/1/2016.
 */
public class ContantsTableModel extends AbstractTableModel {

    private final String[] columnNames = {"names", "values"};
    private File file;
    private ArrayList<ConstantsTableSet> vars;

    public ContantsTableModel(String filePath) {
        this(new File(filePath));
    }

    public ContantsTableModel(File file) {

        this();

        setCurrentFile(file);
        loadFile(file);
    }

    public ContantsTableModel() {
        vars = new ArrayList<>();
    }

    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    public void setCurrentFile(File file) {
        this.file = file;
    }

    public int getRowCount() {
        return vars.size();
    }

    @Override
    public int getColumnCount() {
        return vars.get(0).getAll().size();
    }

    private void clearTable() {
        vars.clear();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return vars.get(rowIndex).getColumnVal(columnIndex);
    }

    public boolean loadFile(File file) {
        if (vars.size() != 0) {
            clearTable();
        }
        if (file != this.file) {
            setCurrentFile(file);
        }

        char inVarsBuf[] = new char[2048];
        try {
            new BufferedReader(new FileReader(file)).read(inVarsBuf);
            processInData(inVarsBuf);
        } catch (Exception e) {
            System.out.println("Error Loading File: " + file.getName());
            return false;
        }
        this.fireTableDataChanged();
        return true;

    }

    public void addSet(ConstantsTableSet set)
    {
        vars.add(set);
        fireTableRowsInserted(vars.size() - 1, vars.size() - 1);

    }

    public void addSet(String name, String value, ConstantsTableSet.Status status) {
        addSet(new ConstantsTableSet(name,value,status));
    }

    public ConstantsTableSet getSet(int row) {
        return vars.get(row);
    }

    public void editSet(int row, String name, String value) {

        if (name != vars.get(row).getName())
            vars.get(row).setName(name);
        if (value != vars.get(row).getValue())
            vars.get(row).setValue(value);

        System.out.println(row);

        this.fireTableRowsUpdated(row, row);

    }

    public void deleteSet(int row) {
        vars.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    private void processInData(char[] varsIn) {
        System.out.println(varsIn);
        String currentVar = "";
        String currentValue = "";
        String currentData = "";
        for (int i = 0; i < varsIn.length; i++) {
            char c = varsIn[i];
            switch (c) {
                case '\r':
                    break;
                case '\n':
                    currentValue = currentData;
                    addSet(new ConstantsTableSet(currentVar, currentValue, ConstantsTableSet.Status.SUCCESS));
                    currentData = "";
                    break;
                case '=':
                    currentVar = currentData;
                    currentData = "";
                    break;
                case '~':
                    addBrokenRuntimeVar(currentVar);
                    break;
                case (char) 0:
                    //removes null characters from input
                    break;
                default:
                    currentData += c;

                    if (i + 1 == varsIn.length) {
                        currentValue = currentData;
                        addSet(new ConstantsTableSet(currentVar,currentValue, ConstantsTableSet.Status.SUCCESS));
                    }
            }
        }
    }

    private void addBrokenRuntimeVar(String name)
    {

    }
    public boolean outputModelToFile() {
        return outputModelToFile(file);
    }

    public boolean outputModelToFile(String pathToFile) {
        return outputModelToFile(new File(pathToFile));
    }

    public boolean outputModelToFile(File file) {
        char[] out = modelToCharArray();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(out);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public char[] modelToCharArray() {
        String s = "";
        for (int i = 0; i < getRowCount(); i++) {
            s += vars.get(i).getName() + "=" + vars.get(i).getValue() + "\r\n";
        }
        return s.toCharArray();
    }
}
