package com.carros.domain;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {

        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

        /*List<CarroDTO> list = new ArrayList<>();
        for (Carro c : carros) {
            list.add(new CarroDTO(c));
        }
        return list;*/
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");

        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");
        Optional<Carro> optional = rep.findById(id);

        //CarroDTO temp = getCarroById(id).get();

        if(optional.isPresent()) {

            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            System.out.println("Carro id " + db.getId());
            rep.save(db);
            return CarroDTO.create(db);
        }
        else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
