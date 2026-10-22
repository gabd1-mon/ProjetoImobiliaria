package com.example.imobiliaria.service;


import org.springframework.stereotype.Service;
import java.util.List;
import com.example.imobiliaria.model.Aluguel;
import com.example.imobiliaria.repository.AluguelRepository;

@Service
public class AluguelService {
    private final AluguelRepository aluguelRepository;

    public AluguelService( AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public Aluguel salvarAluguel(Aluguel aluguel) {
        return aluguelRepository.save(aluguel);
    }

    public List<Aluguel> listarTodos() {
        return aluguelRepository.findAll();
    }

}
