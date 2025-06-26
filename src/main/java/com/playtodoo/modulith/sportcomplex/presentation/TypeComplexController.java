package com.playtodoo.modulith.sportcomplex.presentation;

import com.playtodoo.modulith.sportcomplex.application.TypeComplexService;
import com.playtodoo.modulith.sportcomplex.validation.CreateTypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/type-complex")
@RequiredArgsConstructor
@Tag(name = "Tipos de Complejo", description = "Gestión de tipos de complejo deportivo")
public class TypeComplexController {
    private final TypeComplexService typeComplexService;

    @PostMapping
    @Operation(summary = "Crear tipo de complejo", description = "Registra un nuevo tipo de complejo deportivo")
    public ResponseEntity<TypeComplexDto> create(@RequestBody CreateTypeComplexDto request){
        return ResponseEntity.ok(typeComplexService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar tipos de complejo", description = "Obtiene todos los tipos de complejo")
    public ResponseEntity<List<TypeComplexDto>> getAllTypeComplex(){
        return ResponseEntity.ok(typeComplexService.getAllTypeComplex());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de complejo", description = "Obtiene un tipo de complejo por su identificador")
    public ResponseEntity<TypeComplexDto> getTypeComplexById(@PathVariable UUID id){
        return ResponseEntity.ok(typeComplexService.typeComplexById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de complejo", description = "Elimina un tipo de complejo por su identificador")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        typeComplexService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de complejo", description = "Modifica un tipo de complejo existente")
    public ResponseEntity<TypeComplexDto> update(@PathVariable UUID id, @RequestBody CreateTypeComplexDto request){
        return ResponseEntity.ok(typeComplexService.update(id, request));
    }
}
