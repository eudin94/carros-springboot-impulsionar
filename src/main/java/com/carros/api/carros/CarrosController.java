package com.carros.api.carros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService service;

    @GetMapping
    public ResponseEntity get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getCarros(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        CarroDTO carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);

        //return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); //LAMBDA 1

        //return carro.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build()); //LAMBDA 2

        //return carro.isPresent() ? ResponseEntity.ok(carro.get()) : ResponseEntity.notFound().build(); //IF TERNÁRIO

        /*
        if(carro.isPresent()) {
            return ResponseEntity.ok(carro.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
         */
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<CarroDTO> carros = service.getCarrosByTipo(tipo, PageRequest.of(page, size));

        return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity post(@RequestBody Carro carro) {

            CarroDTO c = service.insert(carro);
            URI location = getUri(c.getId());
            return ResponseEntity.created(location).build();

    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {

        carro.setId(id);

        CarroDTO c = service.update(carro, id);

        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
