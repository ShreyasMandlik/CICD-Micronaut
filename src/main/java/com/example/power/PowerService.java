package com.example.power;

import com.example.exception.PokemonValidationException;
import jakarta.inject.Singleton;

@Singleton
public class PowerService {

    private final PowerRepository powerRepository;


    public PowerService(PowerRepository powerRepository) {
        this.powerRepository = powerRepository;
    }


    public Power get(Long id){
        return powerRepository.findById(id).orElseThrow(()-> new PokemonValidationException("Power does not exists"));
    }
}
