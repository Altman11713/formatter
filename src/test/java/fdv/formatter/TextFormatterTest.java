package fdv.formatter;

import fdv.formatter.helpers.AlignmentMode;
import fdv.formatter.workers.TextFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TextFormatterTest verifies that the formatter produces the expected output.

 * The purpose of this class:
 * - formatting logic is easy to get slightly wrong because spaces matter
 * - tests let us confirm exact output for each alignment mode

 * How it works:
 * - create a TextFormatter
 * - call format(...) with known input
 * - compare the real output with the expected output using assertEquals(...)
 */
class TextFormatterTest {

    /**
     * Verifies left alignment with width 10.

     * What it checks:
     * - normal whitespace is normalized into words
     * - wrapping happens correctly
     * - missing spaces are added on the right
     */
    @Test
    void shouldFormatLeftAlignedWidth10() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be left  aligned",
                AlignmentMode.LEFT,
                10
        );

        String expected = String.join(System.lineSeparator(),
                "This text ",
                "should be ",
                "left      ",
                "aligned   "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies left alignment with width 20.

     * What it checks:
     * - a larger width changes where the line breaks occur
     * - left padding behavior remains correct
     */
    @Test
    void shouldFormatLeftAlignedWidth20() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be left  aligned",
                AlignmentMode.LEFT,
                20
        );

        String expected = String.join(System.lineSeparator(),
                "This text should be ",
                "left aligned        "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies right alignment with width 10.

     * What it checks:
     * - line wrapping still works
     * - missing spaces are added on the left
     */
    @Test
    void shouldFormatRightAlignedWidth10() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be right  aligned",
                AlignmentMode.RIGHT,
                10
        );

        String expected = String.join(System.lineSeparator(),
                " This text",
                " should be",
                "     right",
                "   aligned"
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies right alignment with width 20.

     * What it checks:
     * - wrapping changes with the width
     * - right alignment still pads correctly on the left
     */
    @Test
    void shouldFormatRightAlignedWidth20() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be right  aligned",
                AlignmentMode.RIGHT,
                20
        );

        String expected = String.join(System.lineSeparator(),
                " This text should be",
                "       right aligned"
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies center alignment with width 10.

     * What it checks:
     * - center padding is split across both sides
     * - odd and even padding amounts are handled as expected
     */
    @Test
    void shouldFormatCenterAlignedWidth10() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be center  aligned",
                AlignmentMode.CENTER,
                10
        );

        String expected = String.join(System.lineSeparator(),
                "This text ",
                "should be ",
                "  center  ",
                " aligned  "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies center alignment with width 20.

     * What it checks:
     * - center alignment works on wider wrapped lines too
     */
    @Test
    void shouldFormatCenterAlignedWidth20() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should    be center  aligned",
                AlignmentMode.CENTER,
                20
        );

        String expected = String.join(System.lineSeparator(),
                "This text should be ",
                "   center aligned   "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies justify alignment with width 20.

     * What it checks:
     * - non-last lines are fully justified
     * - the final line falls back to left alignment
     */
    @Test
    void shouldFormatJustifyWidth20() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This text should be justified nicely",
                AlignmentMode.JUSTIFY,
                20
        );

        String expected = String.join(System.lineSeparator(),
                "This  text should be",
                "justified nicely    "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies hard wrapping with width 10.

     * What it checks:
     * - hard mode slices by character count
     * - repeated spaces are preserved
     * - the last line is padded on the right
     */
    @Test
    void shouldFormatHardWidth10() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "This   text should be hard",
                AlignmentMode.HARD,
                10
        );

        String expected = String.join(System.lineSeparator(),
                "This   tex",
                "t should b",
                "e hard    "
        );

        assertEquals(expected, result);
    }

    /**
     * Verifies empty input handling.

     * What it checks:
     * - formatting an empty string returns an empty string
     * - no unnecessary spaces or line breaks are added
     */
    @Test
    void shouldReturnEmptyStringForEmptyInput() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format("", AlignmentMode.LEFT, 10);

        assertEquals("", result);
    }

    /**
     * Verifies that hard mode preserves repeated spaces.

     * What it checks:
     * - unlike normal modes, hard mode does not normalize whitespace
     * - it wraps by raw character count instead
     */
    @Test
    void shouldPreserveMultipleSpacesInHardMode() {
        TextFormatter formatter = new TextFormatter();

        String result = formatter.format(
                "ab   cd",
                AlignmentMode.HARD,
                4
        );

        String expected = String.join(System.lineSeparator(),
                "ab  ",
                " cd "
        );

        assertEquals(expected, result);
    }
}