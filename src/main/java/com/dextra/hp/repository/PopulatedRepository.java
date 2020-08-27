package com.dextra.hp.repository;

import com.dextra.hp.entity.Populated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PopulatedRepository extends JpaRepository<Populated, String>, JpaSpecificationExecutor<Populated> {
}
