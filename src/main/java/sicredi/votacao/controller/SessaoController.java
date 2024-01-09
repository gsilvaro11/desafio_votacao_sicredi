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
import sicredi.votacao.dto.SessaoCadastroDTO;
import sicredi.votacao.dto.SessaoDTO;
import sicredi.votacao.service.interfaces.SessoesService;

@RequestMapping("api/sessao")
@RestController
@RequiredArgsConstructor
public class SessaoController {

    private final SessoesService sessoesService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SessaoCadastroDTO sessaoDTO) {
        sessoesService.create(sessaoDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SessaoDTO>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(sessoesService.list());
    }
}
