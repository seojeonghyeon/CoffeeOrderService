package com.justin.teaorderservice.modules.teacategory;

import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.tea.Tea;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "tea_category") @Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeaCategory {
    @Id
    @GeneratedValue
    @Column(name = "tea_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tea_id")
    private Tea tea;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static TeaCategory createTeaCategory(Category category){
        TeaCategory teaCategory = new TeaCategory();
        teaCategory.setCategory(category);
        return teaCategory;
    }
}
