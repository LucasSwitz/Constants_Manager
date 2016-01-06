package lib;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/6/2016.
 */
public abstract class ConstantsUser {

    public static ArrayList<ConstantsUser> users = new ArrayList<>();

    public ConstantsUser() {
        users.add(this);
    }

    protected String getString(String name)
    {
        return ConstantsManager.getString(name);
    }
    protected int getInteger(String name)
    {
        return ConstantsManager.getInt(name);
    }
    protected boolean getBoolean(String name)
    {
        return ConstantsManager.getBool(name);
    }
    protected double getDouble(String name)
    {
        return ConstantsManager.getDouble(name);
    }

    public abstract void loadConstants();
    protected abstract void loadDefaults();
}