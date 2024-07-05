package cosmetics.BOGOShop.jwt.exception;

import org.springframework.http.HttpStatus;

public class CheckTokenException extends ApiException{
    public CheckTokenException(String msg){
        super(msg);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
