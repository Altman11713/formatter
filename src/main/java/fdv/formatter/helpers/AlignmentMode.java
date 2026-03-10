package fdv.formatter.helpers;

/**
 * AlignmentMode represents the formatting modes supported by the program.

 * The purpose of this class:
 * - command-line input arrives as text
 * - the rest of the code should work with known, valid values
 * - using an enum is safer and cleaner than passing regular strings everywhere

 * Supported modes:
 * - LEFT
 * - RIGHT
 * - CENTER
 * - JUSTIFY
 * - HARD
 */
public enum AlignmentMode {
    LEFT,
    RIGHT,
    CENTER,
    JUSTIFY,
    HARD;

    /**
     * Converts a raw string from the command line into a valid AlignmentMode.

     * What it does:
     * - takes user input like "left"
     * - converts it to the matching enum value

     * Why it does it:
     * - the rest of the program should not depend on regular strings
     * - this gives us validation in one place

     * How it works:
     * - input gets lowercased
     * - matches known values
     * - throws an exception if the input is unsupported
     *
     * @param value the raw mode string from the user
     * @return the matching AlignmentMode
     */
    public static AlignmentMode fromString(String value) {
        return switch (value.toLowerCase()) {
            case "left" -> LEFT;
            case "right" -> RIGHT;
            case "center" -> CENTER;
            case "justify" -> JUSTIFY;
            case "hard" -> HARD;
            default -> throw new IllegalArgumentException(
                    "Unknown mode: " + value + ". Use left, right, center, justify, or hard."
            );
        };
    }
}