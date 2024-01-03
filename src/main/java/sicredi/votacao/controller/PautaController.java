package sicredi.votacao.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sicredi.votacao.dto.PautaContabilizacaoDTO;
import sicredi.votacao.dto.PautaDTO;
import sicredi.votacao.dto.PautaCadastroDTO;
import sicredi.votacao.service.implementations.PautaServiceImpl;

@RequestMapping("api/pauta")
@RestController
@RequiredArgsConstructor
public class PautaController {

    private final PautaServiceImpl pautaServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PautaCadastroDTO pautaDTO) {
        pautaServiceImpl.create(pautaDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PautaDTO>> list(
            @RequestParam(required = false, value = "id") Long id,
            @RequestParam(required = false, value = "titulo") String titulo,
            @RequestParam(required = false, value = "descricao") String descricao) {
        return ResponseEntity.status(HttpStatus.OK).body(pautaServiceImpl.list(id, titulo, descricao));
    }

    @PostMapping("/count/{id}")
    public ResponseEntity<PautaContabilizacaoDTO> count(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pautaServiceImpl.accounting(id));
    }

}
