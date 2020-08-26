package com.dextra.hp.repository;

import com.dextra.hp.entity.HpCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HpCharacterRepository extends JpaRepository<HpCharacter, String>, JpaSpecificationExecutor<String> {
    Page<HpCharacter> findAllByDeletedFalse(Pageable pageable);

    Page<HpCharacter> findAllByDeletedFalseAndBelongingHouse__id(Pageable pageable, String houseId);
}
