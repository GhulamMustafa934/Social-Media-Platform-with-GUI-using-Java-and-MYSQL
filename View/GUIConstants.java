package View;

import java.awt.Color;

public class GUIConstants {

    // Light Theme Backgrounds
    // (names kept as "dark*" for compatibility with existing code that
    // references them — values now point at a light theme palette)
    public static final Color darkBg = new Color(245, 246, 250);      // app background
    public static final Color darkCard = new Color(255, 255, 255);    // card / panel background
    public static final Color darkCardHover = new Color(240, 242, 247);
    public static final Color darkHeader = new Color(255, 255, 255);
    public static final Color darkBorder = new Color(226, 229, 235);

    // Text Colors
    public static final Color textWhite = new Color(26, 26, 26);      // primary text (near-black)
    public static final Color textGray = new Color(120, 122, 135);    // secondary text
    public static final Color textLight = new Color(70, 70, 82);      // muted text

    // Accent Colors
    public static final Color accentBlue = new Color(0, 149, 246);
    public static final Color accentPurple = new Color(110, 90, 255);
    public static final Color accentGreen = new Color(0, 180, 130);
    public static final Color accentRed = new Color(237, 73, 86);

    // Buttons
    public static final Color btnPrimary = new Color(0, 149, 246);
    public static final Color btnPrimaryHover = new Color(30, 170, 255);
    public static final Color btnSecondary = new Color(232, 234, 239);

    // Legacy colors (keep for compatibility)
    public static final Color white = Color.WHITE;
    public static final Color lightGray = new Color(240, 240, 245);
    public static final Color black = Color.BLACK;
    public static final Color blue = new Color(2, 161, 252);
    public static final Color red = Color.RED;
    public static final Color green = new Color(46, 204, 113);
    public static final Color textFieldHint = Color.decode("#959595");
    public static final Color background = new Color(240, 240, 245);
}
