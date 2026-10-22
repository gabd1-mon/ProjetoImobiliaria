package com.example.imobiliaria.service;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.model.Cliente;
import com.example.imobiliaria.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado com o ID: " + id));
    }

    public Cliente atualizarCliente(Integer id, Cliente clienteAtualizado) {
        Cliente clienteExistente = buscarPorId(id);

        clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setDtNascimento(clienteAtualizado.getDtNascimento());

        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Integer id) {
        buscarPorId(id); // Se o cliente não existir, lança 404
        clienteRepository.deleteById(id);
    }
}
