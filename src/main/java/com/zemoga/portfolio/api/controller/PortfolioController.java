package com.zemoga.portfolio.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.zemoga.portfolio.api.exception.NotFoundException;
import com.zemoga.portfolio.api.model.Portfolio;
import com.zemoga.portfolio.api.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/portfolio")
@Tag(name = "Portfolio", description = "This API allows to consult/modify profile information")
public class PortfolioController {

  private final PortfolioService service;

  public PortfolioController(PortfolioService service) {
    this.service = service;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Gets the profile information based on an id ",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Portfolio.class))
            }),
        @ApiResponse(responseCode = "404", description = "profile not found"),
        @ApiResponse(responseCode = "500", description = "error pulling information")
      },
      parameters = {@Parameter(name = "id", description = "identifier of profile")})
  public ResponseEntity<JsonNode> getProfile(@PathVariable int id,
                                             @RequestParam(value = "numTweets", required = false, defaultValue = "5") Integer numTweets) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(service.getPortfolio(id, numTweets));
    } catch (NotFoundException e) {
      responseEntity = ResponseEntity.notFound().build();
    }
    return responseEntity;
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Updated profile data based on the identifier",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Portfolio.class))
            }),
        @ApiResponse(responseCode = "404", description = "profile not found"),
        @ApiResponse(responseCode = "400", description = "invalid data")
      },
      parameters = {@Parameter(name = "id", description = "identifier of profile")})
  public ResponseEntity<String> updateProfile(
      @PathVariable int id, @RequestBody @NotNull @NotEmpty Portfolio portfolio) {
    ResponseEntity responseEntity;
    try {
      responseEntity = ResponseEntity.ok(service.updatePortfolio(id, portfolio));
    } catch (NotFoundException e) {
      responseEntity = ResponseEntity.notFound().build();
    }
    return responseEntity;
  }
}
