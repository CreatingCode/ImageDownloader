import javax.swing.*;
import java.util.ArrayList;

// TEST SITE : http://roseaucountyford.com/Staff.aspx
// TEST SITE : http://drivecrossroadsford.com/Staff.aspx

public class Main {
    public static void main(String [] args) {
        // get staff URL from user
        String staffURL = (String) JOptionPane.showInputDialog("Enter staff URL");

        //String staffURL = "http://roseaucountyford.com/Staff.aspx";
        String validatedURL = Parser.validateURL(staffURL);

        Downloader downloader = new Downloader(Parser.getDomain(validatedURL));

        String html = Downloader.getHTML(validatedURL);

        ArrayList<String> imgTags = Parser.getImgTags(html);
        ArrayList<String> srcs = Parser.getSrcAndValidate(imgTags, Parser.getDomain(validatedURL));

        for (String src : srcs) {
            if (!Parser.isStaffPhoto(src))
                continue;

            downloader.download(src);
        }

        System.out.println("Done!");
    }
}