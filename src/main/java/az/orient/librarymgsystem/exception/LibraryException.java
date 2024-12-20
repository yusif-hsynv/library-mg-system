package az.orient.librarymgsystem.exception;

public class LibraryException extends RuntimeException {
    private Integer code;

    public LibraryException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
