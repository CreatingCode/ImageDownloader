import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private JFrame _frame;
    private JPanel _mainPanel;
    private JButton _button;
    private JScrollPane _scrollPane;
    private static JTextArea _log;

    private String _url;

    public GUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        _frame = new JFrame();
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setTitle("Staff downloader");

        // take the menu bar off the jframe
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // set the name of the application menu item
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Staff Downloader");

        // set the look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        _mainPanel = new JPanel(new BorderLayout());
        _button = new JButton("Download staff page...");
        _log = new JTextArea(20, 50);
    }

    public void create() {
        _button.addActionListener(this);
        _mainPanel.add(_button, BorderLayout.PAGE_START);

        _log.setMargin(new Insets(5, 5, 5, 5));
        _log.setEditable(false);
        _scrollPane = new JScrollPane(_log);
        _mainPanel.add(_scrollPane, BorderLayout.PAGE_END);

        _frame.add(_mainPanel);

        _frame.pack();
        _frame.setVisible(true);
    }

    public void run(String url) {
        if (Parser.isValid(url)) {
            String validatedURL = Parser.validateURL(url);

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
            GUI.toLog("Done!");
            GUI.toLog("*********************************\n");
        } else {
            GUI.toLog("You did not enter a valid URL!   ¯\\_(ツ)_/¯");
        }
    }

    public static void toLog(String m) {
        _log.append(m + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _url = (String) JOptionPane.showInputDialog(_button, "Enter staff URL");

        run(_url);
    }
}
