package cosmetics.BOGOShop.jwt.exception;

import org.springframework.http.HttpStatus;

public class BlackLisToken extends ApiException{
    public BlackLisToken(String msg){
        super(msg);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
