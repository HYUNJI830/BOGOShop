package cosmetics.BOGOShop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    GUEST,USER,ADMIN
}
