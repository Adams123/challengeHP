package com.dextra.hp.repository.specification;

import com.dextra.hp.entity.BaseEntity_;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.entity.HpCharacter_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Objects;

public class HpCharacterSpecification {

    private HpCharacterSpecification(){}

    public static Specification<HpCharacter> hasHouseId(String houseId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(HpCharacter_.belongingHouse).get(BaseEntity_._id), houseId));
    }

    public static Specification<HpCharacter> isNotDeleted() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseEntity_.DELETED), false));
    }

    public static Specification<HpCharacter> buildFromParamsAndIsNotDeleted(Map<String, String> params) {
        Specification<HpCharacter> specification = Specification.where(isNotDeleted());
        Specification<HpCharacter> fromParams = buildFromParams(params);
        if (Objects.nonNull(fromParams)) {
            return specification.and(fromParams);
        }
        return specification;
    }

    public static Specification<HpCharacter> buildFromParams(Map<String, String> params) {
        Specification<HpCharacter> specification = Specification.where(null);
        if(Objects.nonNull(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if ("house".equals(entry.getKey())) {
                    if (Objects.nonNull(specification)) {
                        specification = specification.and(hasHouseId(entry.getValue()));
                    } else {
                        specification = Specification.where(hasHouseId(entry.getValue()));
                    }
                }
            }
        }
        return specification;
    }
}