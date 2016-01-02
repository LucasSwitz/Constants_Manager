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
    private ArrayList<String> vars[];

    public ContantsTableModel(String filePath) {
        this(new File(filePath));
    }

    public ContantsTableModel(File file) {
        setFile(file);
        vars = new ArrayList[2];

        vars[0] = new ArrayList<String>();
        vars[1] = new ArrayList<String>();

        readIn(file);
    }

    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getRowCount() {
        return vars[0].size();
    }

    @Override
    public int getColumnCount() {
        return vars.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return vars[columnIndex].get(rowIndex);
    }

    private boolean readIn(File file) {
        char inVarsBuf[] = new char[2048];
        try {
            new BufferedReader(new FileReader(file)).read(inVarsBuf);
            processInData(inVarsBuf);
        } catch (Exception e) {
            System.out.println("Error Loading File: " + file.getName());
            return false;
        }
        return true;

    }

    public void addSet(String var, String value) {
        vars[0].add(var);
        vars[1].add(value);

        fireTableRowsInserted(vars[0].size() - 1, vars[0].size() - 1);
    }

    public String[] getSet(int row) {
        return new String[]{vars[0].get(row), vars[1].get(row)};
    }

    public void editSet(int row, String var, String value) {
        if (var != vars[0].get(row))
            vars[0].set(row, var);
        if (value != vars[1].get(row))
            vars[1].set(row, value);
        this.fireTableRowsUpdated(row, row);

    }

    public void deleteSet(int row) {
        vars[0].remove(row);
        vars[1].remove(row);
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
                case '\n':
                    currentValue = currentData;
                    addSet(currentVar, currentValue);
                    currentData = "";
                    break;
                case '=':
                    currentVar = currentData;
                    currentData = "";
                    break;
                case (char)0:
                    //removes null characters from input
                    break;
                default:
                    currentData += c;

                    if (i + 1 == varsIn.length) {
                        currentValue = currentData;
                        addSet(currentVar, currentValue);
                    }
            }
        }
    }
    public boolean outputModelToFile()
    {
        return outputModelToFile(file);
    }
    public boolean outputModelToFile(String pathToFile)
    {
        return outputModelToFile(new File(pathToFile));
    }
    public boolean outputModelToFile(File file)
    {
        char[] out = modelToCharArray();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(out);
            writer.close();
        }catch (Exception e)
        {
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    public char[] modelToCharArray()
    {
        String s = "";
        for(int i =0; i < getRowCount();i++)
        {
            s += vars[0].get(i) + "=" + vars[1].get(i)+"\n";
        }
        return s.toCharArray();
    }
}
