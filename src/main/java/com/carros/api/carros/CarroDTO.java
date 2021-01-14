package com.carros.api.carros;

import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {
    private long id;
    private String nome;
    private String tipo;

    public static CarroDTO create(Carro c) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c,CarroDTO.class);
    }

    public static Carro carroConverter(CarroDTO carro) {
//      return new Carro(carro.getId(), carro.getNome(), carro.getTipo());
        return new ModelMapper().map(carro, Carro.class);
    }
}
