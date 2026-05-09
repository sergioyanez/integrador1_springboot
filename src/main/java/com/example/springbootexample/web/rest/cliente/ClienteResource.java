package com.example.springbootexample.web.rest.cliente;

import com.example.springbootexample.service.ClienteService;
import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import com.example.springbootexample.service.dto.cliente.response.ClienteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService clienteService;

    @GetMapping("")
    public List<ClienteResponseDTO> findAll(){
        return this.clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO findById( @PathVariable Long id ){
        return this.clienteService.findById( id );
    }

    @GetMapping("/search")
    public List<ClienteResponseDTO> findById( ClienteRequestDTO request ){
        return this.clienteService.search( request );
    }

    @GetMapping("/bests")
    public List<ClienteResponseDTO> bestClients( ClienteRequestDTO request ){
        return this.clienteService.bestClients( request );
    }

    @PostMapping("")
    public ResponseEntity<ClienteResponseDTO> save( @RequestBody @Valid ClienteRequestDTO request ){
        final var result = this.clienteService.save( request );
        return ResponseEntity.accepted().body( result );
    }

    @GetMapping("/lucky")
    public ClienteResponseDTO luckyClient(){
        return this.clienteService.luckyClient();
    }
}
