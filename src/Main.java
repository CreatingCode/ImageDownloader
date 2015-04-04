import javax.swing.*;

// TEST SITE : http://roseaucountyford.com/Staff.aspx

public class Main {
    public static void main(String [] args) {
        // get staff URL from user
        //String staffURL = (String) JOptionPane.showInputDialog("Enter staff URL");

        String staffURL = "http://roseaucountyford.com/Staff.aspx";
        String validatedURL = Parser.validateURL(staffURL);

        Downloader downloader = new Downloader(Parser.getDomain(validatedURL));
    }
}
