package fdv.formatter.interfaces;

/**
 * LineAlignment defines how one already-wrapped line should be aligned.

 * The purpose of this interface:
 * - each alignment type should live in its own class
 * - this makes the code easier to test and easier to expand
 * - TextFormatter can work with all alignment classes through one common "contract"

 * How it fits into the application:
 * - LineWrapper creates raw lines
 * - a LineAlignment implementation turns each raw line into its final aligned form
 */
public interface LineAlignment {

    /**
     * Aligns one line to the requested width.

     * What it does:
     * - takes a raw line from the wrapper
     * - returns the final aligned version of that line

     * Why it does it:
     * - each alignment mode has different spacing behavior
     * - this method gives every alignment class one shared entry point

     * How it works:
     * - the concrete implementation decides what to do
     * - left/right/center ignore isLastLine
     * - justify uses isLastLine to avoid fully justifying the final line
     *
     * @param line the raw line to align
     * @param width the target line width
     * @param isLastLine true when this is the final line of the output
     * @return the aligned line
     */
    String align(String line, int width, boolean isLastLine);
}