package messagingstompwebsocket;

public class InvalidName extends RuntimeException  {
    public InvalidName(String errorMessage) {
        super(errorMessage,null,false,false);
    }
}
