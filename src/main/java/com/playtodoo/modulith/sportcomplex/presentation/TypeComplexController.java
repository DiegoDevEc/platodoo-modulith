package com.playtodoo.modulith.sportcomplex.presentation;

import com.playtodoo.modulith.sportcomplex.application.TypeComplexService;
import com.playtodoo.modulith.sportcomplex.validation.CreateTypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/type-complex")
@RequiredArgsConstructor
public class TypeComplexController {
    private final TypeComplexService typeComplexService;

    @PostMapping
    public ResponseEntity<TypeComplexDto> create(@RequestBody CreateTypeComplexDto request){
        return ResponseEntity.ok(typeComplexService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<TypeComplexDto>> getAllTypeComplex(){
        return ResponseEntity.ok(typeComplexService.getAllTypeComplex());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeComplexDto> getTypeComplexById(@PathVariable UUID id){
        return ResponseEntity.ok(typeComplexService.typeComplexById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        typeComplexService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeComplexDto> update(@PathVariable UUID id, @RequestBody CreateTypeComplexDto request){
        return ResponseEntity.ok(typeComplexService.update(id, request));
    }
}
