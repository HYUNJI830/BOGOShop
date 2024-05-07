package cosmetics.BOGOShop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cosmetics.BOGOShop.domain.item.HairCare;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.domain.item.SkinCare;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

//    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
//    private List<Makeup> makeups = new ArrayList<>();
//
//    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
//    private List<HairCare> haircares = new ArrayList<>();
//
//    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
//    private List<SkinCare> skincares = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; //대분류 카테고리

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); //중분류 카테고리


    //==연관관계 메서드==//
//    public void addChildCategory(Category child){
//        this.child.add(child); //현재 카테고리의 자식 카테고리 목록에 새로운 자식 카테고리를 추가
//        child.setParent(this); //새로 추가된 자식 카테고리의 부모 카테고리를 현재 카테고리로 설정
//    }
}
