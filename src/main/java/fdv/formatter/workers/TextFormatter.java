package fdv.formatter.workers;

import fdv.formatter.alignments.CenterAlignment;
import fdv.formatter.alignments.JustifyAlignment;
import fdv.formatter.alignments.LeftAlignment;
import fdv.formatter.alignments.RightAlignment;
import fdv.formatter.helpers.AlignmentMode;
import fdv.formatter.helpers.LineWrapper;
import fdv.formatter.interfaces.LineAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * TextFormatter coordinates the full formatting process.

 * The purpose of this class:
 * - Main should not contain formatting logic
 * - wrapping and alignment should be coordinated in one place

 * How it works:
 * 1. ask LineWrapper to break the input into raw lines
 * 2. choose the correct LineAlignment
 * 3. align each line
 * 4. join the final lines into one output string
 */
public class TextFormatter {

    private final LineWrapper lineWrapper;

    /**
     * Creates a formatter with the default LineWrapper.

     * What it does:
     * - initializes the helper needed for line wrapping

     * Why it does it:
     * - TextFormatter depends on LineWrapper to decide where line breaks go

     * How it works:
     * - creates one LineWrapper instance and stores it
     */
    public TextFormatter() {
        this.lineWrapper = new LineWrapper();
    }

    /**
     * Formats the input text using the selected mode and width.

     * What it does:
     * - produces the final formatted multiline string

     * Why it does it:
     * - this is the main service method of the formatter
     * - Main delegates the real formatting work here

     * How it works:
     * - get raw lines from LineWrapper
     * - choose the correct alignment implementation
     * - align each line
     * - join the aligned lines using the system line separator
     *
     * @param input the raw input text
     * @param mode the chosen formatting mode
     * @param width the target width
     * @return the fully formatted output
     */
    public String format(String input, AlignmentMode mode, int width) {
        List<String> rawLines = lineWrapper.wrap(input, mode, width);
        LineAlignment alignment = getAlignment(mode);

        List<String> alignedLines = new ArrayList<>();

        for (int i = 0; i < rawLines.size(); i++) {
            String line = rawLines.get(i);
            boolean isLastLine = (i == rawLines.size() - 1);

            alignedLines.add(alignment.align(line, width, isLastLine));
        }

        return String.join(System.lineSeparator(), alignedLines);
    }

    /**
     * Returns the correct line alignment object for the selected mode.

     * What it does:
     * - maps the requested mode to the alignment implementation that handles it

     * Why it does it:
     * - TextFormatter should decide which alignment object to use
     * - Main should not need to know about all alignment classes

     * How it works:
     * - uses a switch on the mode enum
     * - returns a new alignment object for each mode
     * - HARD maps to LeftAlignment because hard mode is special in wrapping, not in padding
     *
     * @param mode the selected formatting mode
     * @return the alignment object that handles that mode
     */
    private LineAlignment getAlignment(AlignmentMode mode) {
        return switch (mode) {
            case LEFT, HARD -> new LeftAlignment();
            case RIGHT -> new RightAlignment();
            case CENTER -> new CenterAlignment();
            case JUSTIFY -> new JustifyAlignment();
        };
    }
}