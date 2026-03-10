package fdv.formatter.alignments;

import fdv.formatter.interfaces.LineAlignment;

/**
 * LeftAlignment aligns text to the left side of the line.

 * The purpose of this class:
 * - left alignment is one of the required formatting algorithms
 * - keeping it in its own class makes it easy to test and easy to replace

 * How it works:
 * - if the line is already wide enough, it returns it unchanged
 * - otherwise, it adds spaces on the right until the target width is reached
 */
public class LeftAlignment implements LineAlignment {

    /**
     * Left-aligns the given line by padding spaces on the right.

     * What it does:
     * - keeps the text at the left edge
     * - fills the remaining width with trailing spaces

     * Why it does it:
     * - left alignment means the content starts immediately and extra space comes after it

     * How it works:
     * - compare line length to width
     * - if padding is needed, append exactly that many spaces
     *
     * @param line the line to align
     * @param width the target line width
     * @param isLastLine unused for left alignment, but included to satisfy the interface
     * @return the left-aligned line
     */
    @Override
    public String align(String line, int width, boolean isLastLine) {
        if (line.length() >= width) {
            return line;
        }

        return line + " ".repeat(width - line.length());
    }
}