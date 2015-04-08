// TEST SITE : http://roseaucountyford.com/Staff.aspx
// TEST SITE : http://drivecrossroadsford.com/Staff.aspx

import javax.swing.*;

public class Main {
    public static void main(String [] args) {
        GUI gui = null;
        try {
            gui = new GUI();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        gui.create();
    }
}
