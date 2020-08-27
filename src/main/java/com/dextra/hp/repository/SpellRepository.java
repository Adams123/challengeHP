package com.dextra.hp.repository;

import com.dextra.hp.entity.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellRepository extends JpaRepository<Spell, String>, JpaSpecificationExecutor<Spell> {

}
