package cosmetics.BOGOShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private int count;
    private T data;
    //코드 유연성 증가 > 배열로 바로 받는 것을 방지하기 위해
}
