package info.markhillman.Exceptions;

/**
 * Class: SingletonException
 * Description: This exception is thrown when an instance
 * is created after a previous instance has been created
 * Created by Mark on 12/03/2016.
 */
public class SingletonException extends Exception {

    public SingletonException() {
        super();
    }
    public SingletonException(String message) {
        super(message);
    }
}
