import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {
    private String _savePath;
    private static final String ROOT = System.getProperty("user.home") + "/Desktop/";

    public Downloader(String folderName) {
        this._savePath = ROOT + folderName;

        File dir = new File(this._savePath);

        if (dir.exists()) {
            System.out.println("Directory already exits, program might override files in directory");
        } else {
            if (dir.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public String getSavePath() {
        return this._savePath;
    }

    public static String getHTML(String staffURL) {
        String html = null;
        try {
            // make new URL based on staff url
            // then connect in the try block
            URL url = new URL(staffURL);
            URLConnection connection = url.openConnection();

            // set user agent
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)");

            // get stream from connection and make buffered reader from it
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // readlines from bufferedreader and append to html for
            html = new String();
            String line;
            while ((line = in.readLine()) != null) {
                html += line;
            }
        } catch (MalformedURLException e) {
            System.out.println("URL doesn't have the correct form!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not connect!");
            e.printStackTrace();
        }

        return html;
    }
}
