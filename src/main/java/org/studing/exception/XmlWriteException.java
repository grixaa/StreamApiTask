package org.studing.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class XmlWriteException extends Exception {
    public XmlWriteException(String message) {
        super(message);
    }

    public XmlWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlWriteException(Throwable cause) {
        super(cause);
    }
}
