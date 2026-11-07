package br.udesc.edu.demandatech.controller;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.projection.DemandaProjection;
import br.udesc.edu.demandatech.service.DemandaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin("*")
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/demanda")
public class DemandaController {

    private DemandaService demandaService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public ResponseEntity<List<DemandaProjection>> getAll(   ){
        try{
            return ResponseEntity.ok(demandaService.buscarTudo());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
