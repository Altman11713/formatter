package fdv.formatter.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * LineWrapper is responsible for breaking raw input text into lines.

 * The purpose of this class:
 * - line breaking is a separate responsibility from alignment
 * - TextFormatter should coordinate formatting, not contain all wrapping details

 * How it works:
 * - for HARD mode, it preserves the raw input and slices by character count
 * - for all other modes, it normalizes whitespace into words and wraps greedily
 */
public class LineWrapper {

    /**
     * Breaks the input text into raw lines based on the selected mode.

     * What it does:
     * - chooses the correct wrapping behavior
     * - returns the line contents before alignment is applied

     * Why it does it:
     * - hard mode and non-hard modes have fundamentally different wrapping rules

     * How it works:
     * - validate width
     * - if HARD, use character-based wrapping
     * - otherwise, split into words and wrap by words
     *
     * @param input the raw input text
     * @param mode the selected formatting mode
     * @param width the target width
     * @return a list of raw lines before alignment
     */
    public List<String> wrap(String input, AlignmentMode mode, int width) {
        validateWidth(width);

        if (mode == AlignmentMode.HARD) {
            return wrapHard(input, width);
        }

        return wrapWords(splitIntoWords(input), width);
    }

    /**
     * Validates that the width is greater than zero.

     * What it does:
     * - rejects invalid widths

     * Why it does it:
     * - formatting with width 0 or a negative width is not meaningful

     * How it works:
     * - throws an exception when width is less than or equal to zero
     *
     * @param width the target width
     */
    private void validateWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0.");
        }
    }

    /**
     * Splits input into words for non-hard modes.

     * What it does:
     * - turns the full input string into a list of words

     * Why it does it:
     * - left, right, center, and justify should treat repeated spaces as separators
     * - these modes are word-based, not character-based

     * How it works:
     * - trims leading and trailing whitespace
     * - returns an empty list if nothing remains
     * - splits on one or more whitespace characters using "\\s+"
     *
     * @param input the raw input text
     * @return a list of words
     */
    private List<String> splitIntoWords(String input) {
        String trimmed = input == null ? "" : input.trim();

        if (trimmed.isEmpty()) {
            return List.of();
        }

        return List.of(trimmed.split("\\s+"));
    }

    /**
     * Wraps words greedily into lines.

     * What it does:
     * - places as many words as possible on each line without exceeding width

     * Why it does it:
     * - this is a simple and standard word-wrapping strategy
     * - it matches the assignment examples well

     * How it works:
     * - keep a current line being built
     * - test whether the next word still fits (including one separating space)
     * - if it fits, append it
     * - if not, finish the current line and start a new one
     *
     * @param words the words to wrap
     * @param width the maximum width
     * @return a list of raw lines
     */
    private List<String> wrapWords(List<String> words, int width) {
        List<String> lines = new ArrayList<>();

        if (words.isEmpty()) {
            return lines;
        }

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (word.length() > width) {
                throw new IllegalArgumentException(
                        "Word is longer than width: '" + word + "'"
                );
            }

            if (currentLine.isEmpty()) {
                currentLine.append(word);
            } else {
                int possibleLength = currentLine.length() + 1 + word.length();

                if (possibleLength <= width) {
                    currentLine.append(" ").append(word);
                } else {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                }
            }
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    /**
     * Wraps the input by raw character count for HARD mode.

     * What it does:
     * - slices the original string into chunks of size width

     * Why it does it:
     * - hard mode preserves the original characters and spacing
     * - it does not care about words or word boundaries

     * How it works:
     * - iterate through the string in steps of width
     * - take a substring from start to end for each chunk
     * - the final chunk may be shorter than width
     *
     * @param input the raw input text
     * @param width the target width
     * @return a list of raw character-based lines
     */
    private List<String> wrapHard(String input, int width) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        List<String> lines = new ArrayList<>();

        for (int start = 0; start < input.length(); start += width) {
            int end = Math.min(start + width, input.length());
            lines.add(input.substring(start, end));
        }

        return lines;
    }
}