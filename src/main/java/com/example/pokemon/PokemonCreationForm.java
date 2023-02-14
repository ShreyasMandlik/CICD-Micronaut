package com.example.pokemon;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonCreationForm {
  private final String name;

  private final Long powerId;
  @JsonCreator
  public PokemonCreationForm(
      @JsonProperty("name") String name, @JsonProperty("powerId") Long powerId) {
    this.name = name;
    this.powerId = powerId;
  }

  public String getName() {
    return name;
  }

  public Long getPowerId() {
    return powerId;
  }
}
