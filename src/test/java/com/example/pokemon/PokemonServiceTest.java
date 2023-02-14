package com.example.pokemon;

import com.example.power.Power;
import com.example.power.PowerRepository;
import com.example.power.PowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;


class PokemonServiceTest {
    List<Pokemon> pokemon_list;
    Pokemon pokemon_example1, pokemon_example2, pokemon_example3;
    PokemonService pokemonService;
    PokemonRepository pokemonRepository;
    PowerRepository powerRepository;
    PowerService powerService;

    PokemonCreationForm pokemonCreationForm;

    Power power_example1,power_example2;
    @BeforeEach
    void setUp() {
        power_example1 = new Power(1L, "Grass");
        power_example2 = new Power(2L, "electric");
        pokemon_example1 = new Pokemon(1, "Pikachu", power_example1, "123");
        pokemon_example2 = new Pokemon(2, "Paras", power_example2, "1234");
        pokemon_example3 = new Pokemon(3, "Bulbasaur", power_example1, "12345");
        this.pokemon_list = List.of(pokemon_example1, pokemon_example2, pokemon_example3);
        pokemonRepository = Mockito.mock(PokemonRepository.class);
        powerRepository = Mockito.mock(PowerRepository.class);
        powerService = new PowerService(powerRepository);
        pokemonService= new PokemonService(pokemonRepository,powerService);
        pokemonCreationForm=new PokemonCreationForm("Bulbasaur",1L);

    }
    @Test
    void getTest() {
        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemon_list);
        Assertions.assertThat(pokemon_list).isEqualTo(pokemonService.get());
    }
    @Test
    void getByIdTest() {
        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable((pokemon_list.get(0))));
        Assertions.assertThat(pokemon_list.get(0)).isEqualTo(pokemonService.getById(1));
    }

    @Test
    void createTest() {
        Mockito.when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon_example3);
        Mockito.when(powerRepository.findById(1L)).thenReturn(Optional.ofNullable(power_example1));
        Assertions.assertThat(pokemonService.create(pokemonCreationForm)).isEqualTo(pokemon_example3);
    }

    @Test
    void updatetest() {
        Mockito.when(pokemonRepository.findById(anyInt())).thenReturn(Optional.ofNullable(pokemon_example1));
        Mockito.when(pokemonRepository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(pokemon_example1));


        Mockito.when(pokemonRepository.update(Mockito.any())).thenReturn(pokemon_example1);
        Pokemon updatedPokemon=pokemonService.update(pokemon_example1);

        Mockito.verify(pokemonRepository).update(Mockito.any());
        Mockito.verify(pokemonRepository).findByNameIgnoreCase(anyString());
        Mockito.verify(pokemonRepository).findById(anyInt());

        Assertions.assertThat(updatedPokemon).isEqualTo(pokemon_example1);

    }
}