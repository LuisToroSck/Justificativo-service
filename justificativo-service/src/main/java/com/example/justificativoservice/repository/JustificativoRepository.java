package com.example.justificativoservice.repository;

import com.example.justificativoservice.entity.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "update justificativo j set j.justificada = :just where j.id_justificativo=:id",nativeQuery = true)
    public void actualizarJustificativo(@Param("just") int just, @Param("id") Long id);

    @Query("select j from JustificativoEntity j where j.idJustificativo=:id ")
    JustificativoEntity getById(@Param("id") Long id);
}