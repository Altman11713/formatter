package fdv.formatter.alignments;

import fdv.formatter.interfaces.LineAlignment;

/**
 * JustifyAlignment spreads spaces between words so the line fills the full width.

 * The purpose of this class:
 * - justify is a distinct alignment behavior
 * - it is more complex than left/right/center, so isolating it improves readability

 * How it works:
 * - if the line is the last line, it falls back to left alignment
 * - if the line has only one word, it also falls back to left alignment
 * - otherwise, it distributes spaces across the gaps between words
 * - extra spaces are given to the leftmost gaps first
 */
public class JustifyAlignment implements LineAlignment {

    /**
     * Justifies the given line.

     * What it does:
     * - expands the spaces between words so the line reaches the exact target width

     * Why it does it:
     * - justify alignment aims to make both left and right edges line up
     * - the last line is usually not justified in standard text formatting

     * How it works:
     * - skip justification for the last line
     * - split the line into words
     * - count the total number of letters
     * - compute how many spaces are needed overall
     * - distribute those spaces across the gaps between words
     *
     * @param line the line to align
     * @param width the target line width
     * @param isLastLine true when this is the final line of the output
     * @return the justified line
     */
    @Override
    public String align(String line, int width, boolean isLastLine) {
        if (line.length() >= width) {
            return line;
        }

        if (isLastLine) {
            return padRight(line, width);
        }

        String[] words = line.split(" ");

        if (words.length == 1) {
            return padRight(line, width);
        }

        int totalWordLength = 0;
        for (String word : words) {
            totalWordLength += word.length();
        }

        StringBuilder result = getStringBuilder(width, totalWordLength, words);

        return result.toString();
    }

    private StringBuilder getStringBuilder(int width, int totalWordLength, String[] words) {
        int totalSpacesNeeded = width - totalWordLength;
        int gaps = words.length - 1;
        int spacesPerGap = totalSpacesNeeded / gaps;
        int extraSpaces = totalSpacesNeeded % gaps;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            result.append(words[i]);

            if (i < gaps) {
                int spacesToInsert = spacesPerGap;

                if (extraSpaces > 0) {
                    spacesToInsert++;
                    extraSpaces--;
                }

                result.append(" ".repeat(spacesToInsert));
            }
        }
        return result;
    }

    /**
     * Pads the line with spaces on the right.

     * What it does:
     * - behaves like left alignment

     * Why it does it:
     * - justify falls back to normal left alignment for the last line
     * - justify also falls back to left alignment when there is only one word

     * How it works:
     * - compare line length to width
     * - append enough spaces to fill the remaining width
     *
     * @param line the line to pad
     * @param width the target width
     * @return the padded line
     */
    private String padRight(String line, int width) {
        if (line.length() >= width) {
            return line;
        }

        return line + " ".repeat(width - line.length());
    }
}