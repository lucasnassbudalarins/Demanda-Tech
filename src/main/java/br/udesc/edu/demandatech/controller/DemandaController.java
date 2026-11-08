package br.udesc.edu.demandatech.controller;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.enumerators.Prioridade;
import br.udesc.edu.demandatech.model.enumerators.Status;
import br.udesc.edu.demandatech.model.projection.ColaboradorProjection;
import br.udesc.edu.demandatech.model.projection.DemandaProjection;
import br.udesc.edu.demandatech.service.DemandaService;
import br.udesc.edu.demandatech.service.FuncionarioService;
import lombok.AllArgsConstructor;

import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/demanda")
public class DemandaController {

    private final DemandaService demandaService;
    private final FuncionarioService funcionarioService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<PagedModel<DemandaProjection>> getAll(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) Long idTipo,
            @PageableDefault(size = 10, sort = {"data", "idDemanda"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(demandaService.buscarTudo(status, prioridade, idTipo, pageable)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/criar")
    public ResponseEntity<Demanda> criarDemanda(
            @RequestBody DemandaCriarDTO demandaDTO) {
        return ResponseEntity.ok(demandaService.criar(demandaDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/criar")
    public ResponseEntity<PagedModel<ColaboradorProjection>> listarColabodores (
            @PageableDefault(size = 10, sort = {"nome", "matricula"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(funcionarioService.buscarTudo()));
    }
}
