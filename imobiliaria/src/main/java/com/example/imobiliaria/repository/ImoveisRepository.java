package com.example.imobiliaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.imobiliaria.model.Imoveis;

public interface ImoveisRepository extends JpaRepository<Imoveis, Integer> {
}
