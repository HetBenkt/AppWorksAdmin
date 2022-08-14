package nl.bos.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralAppException extends RuntimeException {

    public GeneralAppException(Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
    }
}
