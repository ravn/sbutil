package dk.statsbiblioteket.util.console;

import java.io.IOException;

/**
 * A string type that can render colored and formatted output to a console.
 */
public class Formatter {

    private Color foreground;
    private Color background;
    private Hint hint;

    private static final String controlStart = "\033[";
    private static final String controlEnd = "m";

    /**
     * Create a formatter with the default settings.
     */
    public Formatter() {

    }

    /**
     * Create a formatter with a preset foreground colour.
     *
     * @param fg foreground color to use
     */
    public Formatter(Color fg) {
        foreground = fg;
    }

    /**
     * Create a formatter with preset foreground and background colours.
     *
     * @param fg foreground color to use
     * @param bg background color to use
     */
    public Formatter(Color fg, Color bg) {
        foreground = fg;
        background = bg;
    }

    /**
     * Create a formatter with preset foreground and background colours as well
     * as display hints.
     *
     * @param fg   foreground color to use
     * @param bg   background color to use
     * @param hint display hint to use
     */
    public Formatter(Color fg, Color bg, Hint hint) {
        foreground = fg;
        background = bg;
        this.hint = hint;
    }

    /**
     * Set the text color of the string.
     *
     * @param color The ANSI color to print the text in
     */
    public void setForeground(Color color) {
        foreground = color;
    }

    /**
     * Set the text's background color
     *
     * @param color ANSI color to use for background
     */
    public void setBackground(Color color) {
        background = color;
    }

    /**
     * Set a hint that modifies how the string is printed.
     *
     * @param hint A {@link Hint}
     */
    public void setHint(Hint hint) {
        this.hint = hint;
    }

    /**
     * Clear all hints, for-, or background colors.
     */
    public void reset() {
        hint = null;
        background = null;
        foreground = null;
    }

    /**
     * Return a formatted representation of the string with colours and
     * hints applied.
     *
     * @param s to format
     * @return Formatted string ready for displaying in a terminal
     */
    public String format(String s) {
        StringBuilder buf = new StringBuilder();
        try {
            format(s, buf);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }

        return buf.toString();
    }

    public Appendable format(String s, Appendable buf) throws IOException {
        if (foreground == null && background == null && hint == null) {
            buf.append(s);
            return buf;
        }

        buf.append(controlStart);

        if (foreground != null) {
            buf.append(Integer.toString(30 + foreground.ordinal()));
        }

        if (background != null) {
            buf.append(foreground != null ? ";" : "");
            buf.append(Integer.toString(40 + background.ordinal()));
        }

        if (hint != null) {
            buf.append((background != null) || (foreground != null) ? ";" : "");
            buf.append(Integer.toString(hint.ordinal()));
        }

        buf.append(controlEnd);
        buf.append(s);
        buf.append(controlStart).append("0").append(controlEnd); // Reset style

        return buf;
    }

}
