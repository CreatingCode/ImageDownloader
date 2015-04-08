import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String IMG_RE = "<img.*?\">";
    private static final String SRC_RE = "src=\".*?\"";
    private static final String DAT_RE = "data-original=(\"|\').*?(\"|\')";

    public static boolean isValid(String s) {
        if (s == null) {
            return false;
        } else if (!s.contains(".com")) {
            return false;
        } else return true;
    }

    // url needs to have a format of http://<DOMAIN>.com/<STAFF_PAGE>
    public static String validateURL(String url) {
        String validatedURL = url;

        validatedURL = validatedURL.replace("https://", "");
        validatedURL = validatedURL.replace("www.", "");
        validatedURL = validatedURL.replaceAll(" ", "%20");

        if (!validatedURL.startsWith("http://")) {
            validatedURL = "http://" + validatedURL;
        }

        return validatedURL;
    }

    // gets domain to use as the folder name
    // needs the URL that's passed in to in validated format
    public static String getDomain(String validatedURL) {
        String domain = validatedURL.replace("http://", "");
        return domain.substring(0, domain.indexOf("."));
    }

    // get img tags from html
    public static ArrayList<String> getImgTags(String html) {
        ArrayList<String> imgTags = new ArrayList<String>();

        Pattern pattern = Pattern.compile(IMG_RE);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) { imgTags.add(matcher.group()); }

        return imgTags;
    }

    // extract the src from img tags and validate
    public static ArrayList<String> getSrcAndValidate(ArrayList<String> imgTags, String domain) {
        String rootURL = "http://" + domain + ".com";
        ArrayList<String> srcs = new ArrayList<String>();

        Pattern pattern;
        Matcher matcher;

        // loop through tags and extract either the data-type or src
        // if src is a relative url add domain to it
        for(String imgTag : imgTags) {
            String tempSrc;
            if (imgTag.contains("data-original")) {
                System.out.println(imgTag);
                pattern = Pattern.compile(DAT_RE);
                matcher = pattern.matcher(imgTag);

                matcher.find();
                tempSrc = stripRE(matcher.group());
            } else {
                pattern = Pattern.compile(SRC_RE);
                matcher = pattern.matcher(imgTag);

                matcher.find();
                tempSrc = stripRE(matcher.group());
            }

            if (tempSrc.startsWith("/")) {
                tempSrc = rootURL + tempSrc;
            }

            if (tempSrc.contains("?")) {
                tempSrc = tempSrc.substring(0, tempSrc.indexOf("?"));
            }

            srcs.add(validateURL(tempSrc));
        }

        return srcs;
    }

    // strip out the attribute and quotes
    private static String stripRE(String src) {
        String temp = src;
        temp = temp.replaceAll("src=", "");
        temp = temp.replaceAll("data-original=", "");
        temp = temp.replaceAll("\"", "");
        temp = temp.replaceAll("\'", "");

        return temp;
    }

    public static boolean isStaffPhoto(String src) {
        String[] spIdentifiers = {"person"};

        for (int i = 0; i < spIdentifiers.length; i++) {
            if (src.contains(spIdentifiers[i]))
                return true;
        }

        return false;
    }
}
