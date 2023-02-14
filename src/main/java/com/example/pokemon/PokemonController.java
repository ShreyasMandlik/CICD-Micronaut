package com.example.pokemon;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/pokemon")
public class PokemonController {

  private final PokemonService pokemonService;

  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @Get
  public List<Pokemon> test() {
    return pokemonService.get();
  }

  @Get("/{id}")
  public Pokemon getById(@PathVariable("id") Integer id) {
    return pokemonService.getById(id);
  }

  @Post
  public HttpResponse<Pokemon> create(@Body PokemonCreationForm pokemonForm) {

    return HttpResponse.created(pokemonService.create(pokemonForm));
  }

  @Put
  public HttpResponse<Pokemon> update(@Body Pokemon pokemon) {

    pokemonService.update(pokemon);
    return HttpResponse.ok();
  }

  @Delete("/{id}")
  public HttpResponse<Pokemon> delete(@PathVariable Integer id) {

    pokemonService.delete(id);
    return HttpResponse.ok();
  }
}
