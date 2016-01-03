package gui;

import gui.MainFrame;
import robot.ConstantsManager;

import java.io.File;

/**
 * Created by Administrator on 1/1/2016.
 */
public class entry {

    public static void main(String args[])
    {
        ConstantsManager.setFile(new File("C:\\Users\\Administrator\\Desktop\\C2.txt"));
        System.out.println(ConstantsManager.getDouble("yell"));
    }
}
