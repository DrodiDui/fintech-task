package by.kapitonov.fintech.task.exception;

public class UserAccountException extends Exception {

    public UserAccountException() {
        super();
    }

    public UserAccountException(String s) {
        super(s);
    }

    public UserAccountException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UserAccountException(Throwable throwable) {
        super(throwable);
    }

    protected UserAccountException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
