package fdv.formatter.alignments;

import fdv.formatter.interfaces.LineAlignment;

/**
 * CenterAlignment centers text within the target width.

 * The purpose of this class:
 * - center alignment is one of the required formatting algorithms
 * - the padding logic is different enough to deserve its own class

 * How it works:
 * - calculate how many spaces are missing in total
 * - split them across left and right
 * - if the number is odd, the extra space ends up on the right
 */
public class CenterAlignment implements LineAlignment {

    /**
     * Centers the given line within the target width.

     * What it does:
     * - places the text as close to the middle as possible

     * Why it does it:
     * - center alignment should visually balance the text inside the line width

     * How it works:
     * - compute total padding
     * - put half on the left
     * - put the rest on the right
     *
     * @param line the line to align
     * @param width the target line width
     * @param isLastLine unused for center alignment, but included to satisfy the interface
     * @return the centered line
     */
    @Override
    public String align(String line, int width, boolean isLastLine) {
        if (line.length() >= width) {
            return line;
        }

        int totalPadding = width - line.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(leftPadding) + line + " ".repeat(rightPadding);
    }
}