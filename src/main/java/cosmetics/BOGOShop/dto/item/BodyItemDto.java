package cosmetics.BOGOShop.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class BodyItemDto {
    private String name; //바디제품 이름

    private int price;

    //@Data은 기본생성자를 만들어 주지 않기 때문에
    //별도 생성자를 만들었으면 추가적으로 기본생성자를 만들어준다.
    //혹은 @NoArgsConstructor 사용해서 기본생성자를 만들어준다.
//    public BodyItemDto(){
//
//    }

    @QueryProjection
    public BodyItemDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
