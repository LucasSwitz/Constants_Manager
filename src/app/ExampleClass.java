package app;

import lib.ConstantsUser;

/**
 * Created by Administrator on 1/6/2016.
 */
public class ExampleClass extends ConstantsUser {

    int i;
    String s;
    boolean b;
    double d;

    @Override
    public void loadConstants(){
            i = getInteger("i");
            s = getString("s");
            b = getBoolean("b");
            d = getDouble("d");
    }

    @Override
    protected void loadDefaults() {
        i = 0;
        s = "";
        b = false;
        d =  0.0;
    }
}
