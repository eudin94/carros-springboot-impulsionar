package com.carros;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosServiceTest {

    @Autowired
    private CarroService service;

    @Test
    public void testSave() {
        Carro carro = new Carro();
        carro.setNome("Ferrari");
        carro.setTipo("esportivos");

        CarroDTO c = service.insert(carro);

        assertNotNull(c);

        Long id = c.getId();
        assertNotNull(id);

        c = service.getCarroById(id);
        assertNotNull(c);

        assertEquals("Ferrari", c.getNome());
        assertEquals("esportivos", c.getTipo());

        service.delete(id);

        try {
            assertNull(service.getCarroById(id));
            fail("O carro não foi excluído");
        }
        catch (ObjectNotFoundException e) {}
    }

    @Test
    public void testList() {

        List<CarroDTO> carros = service.getCarros();

        assertEquals(30, carros.size());

    }

    @Test
    public void testGet() {

        CarroDTO c = service.getCarroById(11L);
        assertNotNull(c);

        assertEquals("Ferrari FF", c.getNome());
    }

    @Test
    public void testListaPorTipo() {

        assertEquals(10, service.getCarroByTipo("classicos").size());
        assertEquals(10, service.getCarroByTipo("esportivos").size());
        assertEquals(10, service.getCarroByTipo("luxo").size());

        assertEquals(0, service.getCarroByTipo("x").size());
    }
}
