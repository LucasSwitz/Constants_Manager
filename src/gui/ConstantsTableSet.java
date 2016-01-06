package gui;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/6/2016.
 */
public class ConstantsTableSet {


    /*
    NAME INDEX = 0
    VALUE INDEX = 1

     */
    private Status status;
    ArrayList<String> row;
    public enum Status
    {
        SUCCESS,
        FAILED
    }

    public ConstantsTableSet(String name, String value)
    {
        this(name,value,Status.SUCCESS);
    }
    public ConstantsTableSet(String name,String value, Status status)
    {

        this.status = status;

        row = new ArrayList<>();

        row.add(name);
        row.add(value);
    }
    public ArrayList<String> getAll()
    {
        return row;
    }
    public void setName(String name){row.set(0,name);}
    public void setValue(String value){row.set(1,value);}
    public void setStatus(Status status){this.status = status;}
    public String getName(){return row.get(0);}
    public String getValue(){return row.get(1);}
    public String getColumnVal(int i){return row.get(i);}
    public boolean isSuccessful()
    {
        return status == Status.SUCCESS;
    }
}
