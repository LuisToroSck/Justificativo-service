package com.example.justificativoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.justificativoservice.entity.JustificativoEntity;
import com.example.justificativoservice.repository.JustificativoRepository;
import com.example.justificativoservice.service.JustificativoService;

@RestController
@RequestMapping("/justificativo")
public class JustificativoController {
    
    @Autowired
    JustificativoService justificativoService;

    @Autowired
    JustificativoRepository justificativoRepository;

    @GetMapping
    public ResponseEntity<List<JustificativoEntity>> listarJustificativos(){
        List<JustificativoEntity> autorizaciones = justificativoService.listarJustificativos();
        return ResponseEntity.ok(autorizaciones);
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<JustificativoEntity> actualizarJustificativo(@PathVariable Long id){
        JustificativoEntity justificativo = justificativoRepository.getById(id);
        if(justificativo.getJustificada()==0){
            justificativoService.actualizarJustificativo(1, id);
        }else{
            justificativoService.actualizarJustificativo(0, id);
        }
        return ResponseEntity.ok(justificativo);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<List<JustificativoEntity>> eliminarJustificativo(){
        justificativoService.eliminarInasistencias();
        List<JustificativoEntity> justificativos = justificativoService.listarJustificativos();
        return ResponseEntity.ok(justificativos);
    }

}
