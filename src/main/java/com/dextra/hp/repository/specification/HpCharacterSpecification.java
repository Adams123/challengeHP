package com.dextra.hp.repository.specification;

import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.entity.HpCharacter_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class HpCharacterSpecification{
    public static Specification<HpCharacter> house(String house) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(HpCharacter_.BELONGING_HOUSE), house));
    }
}