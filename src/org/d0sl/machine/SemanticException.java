package org.d0sl.machine;

/**
 * General Semantic Exception class.
 *
 * @version %I%,%G%
 */
public class SemanticException extends Exception {
    public SemanticException() {
    }

    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticException(Throwable cause) {
        super(cause);
    }

    public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
