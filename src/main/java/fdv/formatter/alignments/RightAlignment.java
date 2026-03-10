package fdv.formatter.alignments;

import fdv.formatter.interfaces.LineAlignment;

/**
 * RightAlignment aligns text to the right side of the line.

 * The purpose of this class:
 * - right alignment is one of the required formatting algorithms
 * - keeping it separate keeps the logic focused and easy to test

 * How it works:
 * - if the line is already wide enough, it returns it unchanged
 * - otherwise, it adds spaces on the left until the target width is reached
 */
public class RightAlignment implements LineAlignment {

    /**
     * Right-aligns the given line by padding spaces on the left.

     * What it does:
     * - shifts the text to the right edge of the available width

     * Why it does it:
     * - right alignment means the text should end at the right boundary

     * How it works:
     * - calculate how many characters are missing
     * - insert that many spaces before the text
     *
     * @param line the line to align
     * @param width the target line width
     * @param isLastLine unused for right alignment, but included to satisfy the interface
     * @return the right-aligned line
     */
    @Override
    public String align(String line, int width, boolean isLastLine) {
        if (line.length() >= width) {
            return line;
        }

        return " ".repeat(width - line.length()) + line;
    }
}