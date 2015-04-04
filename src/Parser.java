public class Parser {
    // url needs to have a format of http://<DOMAIN>.com/<STAFF_PAGE>
    public static String validateURL(String url) {
        String validatedURL = url;

        validatedURL = validatedURL.replace("https://", "");
        validatedURL = validatedURL.replace("www.", "");

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
}
