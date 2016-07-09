package com.isaaccode;

import com.isaaccode.gui.GUI;

import java.io.*;
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
            GUI.toLog("Directory already exits, program might override files in directory");
        } else {
            if (dir.mkdir()) {
                System.out.println("Directory is created!");
                GUI.toLog("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
                GUI.toLog("Failed to create directory!");
            }
        }
    }

    public String getSavePath() {
        return this._savePath;
    }

    public void download(String validatedURL) {
        String name = validatedURL.substring(validatedURL.lastIndexOf("/"), validatedURL.length());

        try {
            URL url = new URL(validatedURL);
            InputStream in = url.openStream();
            OutputStream out =  new BufferedOutputStream(new FileOutputStream(getSavePath() + "/" + name));

            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }

            out.close();
            in.close();

            System.out.println("Downloaded image named: " + name);
            GUI.toLog("Downloaded image named: " + name);
        } catch (MalformedURLException e) {
            System.out.println("Could not download image! URL: " + validatedURL);
            GUI.toLog("* ¯\\_(ツ)_/¯ *-> Could not download image! URL: " + validatedURL + " <-*");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not download image! URL: " + validatedURL);
            GUI.toLog("* ¯\\_(ツ)_/¯ *-> Could not download image! URL: " + validatedURL + " <-*");
            e.printStackTrace();
        }
    }

    public static String getHTML(String validatedURL) {
        String html = null;
        try {
            // make new URL based on staff url
            // then connect in the try block
            URL url = new URL(validatedURL);
            URLConnection connection = url.openConnection();

            // set user agent
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");

            // get stream from connection and make buffered reader from it
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // readlines from bufferedreader and append to html for
            html = new String();
            String line;
            while ((line = in.readLine()) != null) {
                html += line;
            }

            System.out.println("Downloaded HTML source from: " + validatedURL);
            GUI.toLog("Downloaded HTML source from: " + validatedURL);
        } catch (MalformedURLException e) {
            System.out.println("Could not ownloaded HTML source! Site: " + validatedURL);
            GUI.toLog("* ¯\\_(ツ)_/¯ *-> Could not ownloaded HTML source! Site: " + validatedURL);
            GUI.toLog("*********************************\n");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not ownloaded HTML source! Site: " + validatedURL);
            GUI.toLog("* ¯\\_(ツ)_/¯ *-> Could not ownloaded HTML source! Site: " + validatedURL);
            GUI.toLog("*********************************\n");
            e.printStackTrace();
        }

        return html;
    }
}
