package sicredi.votacao.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.VotoCadastroDTO;
import sicredi.votacao.dto.VotoDTO;
import sicredi.votacao.service.implementations.VotosServiceImpl;

@RequestMapping("api/votos")
@RestController
@RequiredArgsConstructor
public class VotosController {

    private final VotosServiceImpl votosImpl;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody VotoCadastroDTO votoCadastro) {
        votosImpl.create(votoCadastro);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<VotoDTO>> list(){
        return ResponseEntity.status(HttpStatus.OK).body(votosImpl.list());
    }

}
