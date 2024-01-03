package sicredi.votacao.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.AssociadoCadastroDTO;
import sicredi.votacao.dto.AssociadoDTO;
import sicredi.votacao.service.implementations.AssociadosServiceImpl;

@RequestMapping("api/associado")
@RestController
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadosServiceImpl associateImpl;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody AssociadoCadastroDTO associadoCadastroDTO) {
        associateImpl.create(associadoCadastroDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssociadoDTO>> list(
            @RequestParam(required = false, value = "id") Long id,
            @RequestParam(required = false, value = "cpf") String cpf,
            @RequestParam(required = false, value = "nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(associateImpl.list(id, cpf, nome));
    }
}
