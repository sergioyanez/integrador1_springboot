package com.example.springbootexample.service;

import com.example.springbootexample.domain.Cliente;
import com.example.springbootexample.domain.Producto;
import com.example.springbootexample.repository.ClienteRepository;
import com.example.springbootexample.repository.ProductoRepository;
import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import com.example.springbootexample.service.dto.cliente.response.ClienteResponseDTO;
import com.example.springbootexample.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public ClienteResponseDTO save(ClienteRequestDTO request) {
        final var cliente = new Cliente(request);
        final var result = this.clienteRepository.save(cliente);
        return new ClienteResponseDTO(result.getId(), result.getNombre(), result.getEmail(), result.getDni());
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return this.clienteRepository.findAll().stream().map(ClienteResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        return this.clienteRepository.findById(id)
                .map(ClienteResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Cliente", id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> search(ClienteRequestDTO request) {
        return this.clienteRepository.search(request.getNombre(), request.getEmail())
                .stream().map(ClienteResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> bestClients(ClienteRequestDTO request) {
        return this.clienteRepository.bestClients()
                .stream().map(ClienteResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO luckyClient() {
        List<Cliente> bestClients = clienteRepository.bestClients();
        ClienteResponseDTO luckyClient = null;
        for (Cliente cliente : bestClients) {
            int dni = cliente.getDni();
            if (dni % 10 == 8) {
                luckyClient = new ClienteResponseDTO(cliente);
                return luckyClient;
                }
        }
        // Manejar el caso en el que no se encontró ningún cliente con DNI terminado en "8"
        throw new NotFoundException("No se encontró ningún cliente afortunado con DNI terminado en '8'");
    }
}







