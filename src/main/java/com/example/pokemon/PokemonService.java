package com.example.pokemon;

import com.example.exception.PokemonValidationException;
import com.example.power.Power;
import com.example.power.PowerService;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Singleton
public class PokemonService {

  private static final Logger log = LoggerFactory.getLogger(Pokemon.class);

  private final PokemonRepository pokemonRepository;

  private final PowerService powerService;


  public PokemonService(PokemonRepository pokemonRepository, PowerService powerService) {

    this.pokemonRepository = pokemonRepository;
    this.powerService = powerService;
  }

  public List<Pokemon> get() {
    return (List<Pokemon>) pokemonRepository.findAll();
  }

  @Transactional
  public Pokemon create(PokemonCreationForm pokemonForm) {
    boolean isPokemonExist = pokemonRepository.existsByNameIgnoreCase(pokemonForm.getName());
    if (isPokemonExist) {
      throw new PokemonValidationException(
          "Pokemon With name: " + pokemonForm.getName() + " Already Exist");
    }

    Power power=powerService.get(pokemonForm.getPowerId());
    Pokemon pokemon = new Pokemon();
    pokemon.setName(pokemonForm.getName());
    pokemon.setPower(power);
    Pokemon createdPokemon = pokemonRepository.save(pokemon);
    pokemon.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+pokemon.getId()+".png");

    return createdPokemon;
  }

  public Pokemon getById(Integer id) {
    return pokemonRepository
        .findById(id)
        .orElseThrow(() -> new PokemonValidationException("Pokemon with id: " + id + " Not found"));
  }
  public Pokemon update(Pokemon pokemon) {
    Pokemon pokemonWithId = pokemonRepository.findById(pokemon.getId()).orElseThrow(()->new PokemonValidationException("Pokemon With id: " + pokemon.getId() + " Not exist"));
    Pokemon pokemanWithNameExist = pokemonRepository.findByNameIgnoreCase(pokemon.getName()).orElse(null);
    if(pokemanWithNameExist!=null && !Objects.equals(pokemanWithNameExist.getId(), pokemonWithId.getId())){
      throw new PokemonValidationException("Pokemon with name: " +pokemon.getName() +" Already exist");
    }
    else{
      pokemonRepository.update(pokemon);
    }
    return pokemon;

  }
  public void delete(Integer id) {

    boolean isPokemonExist = pokemonRepository.existsById(id);
    if (!isPokemonExist) {
      throw new PokemonValidationException("Pokemon with id " + id + " Not Found");
    }
    pokemonRepository.deleteById(id);
  }
}
