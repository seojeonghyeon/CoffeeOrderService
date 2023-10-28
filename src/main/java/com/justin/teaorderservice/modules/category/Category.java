package com.justin.teaorderservice.modules.category;

import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @AllArgsConstructor
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<TeaCategory> teaCategories;

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }


}
