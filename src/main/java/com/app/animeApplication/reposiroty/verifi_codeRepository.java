package com.app.animeApplication.reposiroty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.animeApplication.entity.Verification_code;

@Repository
public interface verifi_codeRepository extends JpaRepository<Verification_code, Long>{

	@Query("SELECT cd FROM Verification_code cd WHERE cd.mail = :mail")
    Optional<Verification_code> findByEmail(@Param("mail") String mail);
}
