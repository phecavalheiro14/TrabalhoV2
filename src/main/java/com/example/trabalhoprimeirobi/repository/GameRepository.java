package com.example.trabalhoprimeirobi.repository;

import com.example.trabalhoprimeirobi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE " +
            "(:titulo IS NULL OR LOWER(g.titulo) LIKE LOWER(CONCAT('%', CAST(:titulo AS string), '%'))) AND " +
            "(:genero IS NULL OR LOWER(g.genero) = LOWER(CAST(:genero AS string))) AND " +
            "(:plataforma IS NULL OR LOWER(g.plataforma) = LOWER(CAST(:plataforma AS string)))")
    List<Game> findByFiltros(@Param("titulo") String titulo,
                             @Param("genero") String genero,
                             @Param("plataforma") String plataforma);
}