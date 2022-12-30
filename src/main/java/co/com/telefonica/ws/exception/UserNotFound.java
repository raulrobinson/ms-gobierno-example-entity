package co.com.telefonica.ws.exception;

public class UserNotFound extends RuntimeException
{
    public UserNotFound(String message)
    {
        super(message);
    }
}
