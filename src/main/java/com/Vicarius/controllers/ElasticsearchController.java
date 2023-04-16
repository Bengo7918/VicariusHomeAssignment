package com.Vicarius.controllers;

import com.Vicarius.controllers.advice.ErrorResponse;
import com.Vicarius.modules.BookStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("v1/elastic")
@Tag(name = "Elasticsearch Vicarius service", description = "Handle elastic search actions")
public interface ElasticsearchController {


    @PostMapping("/createIndex")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created - index created"),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})})
    @Operation(description = "Endpoint for creating a new index")
    void createIndex();

    @PostMapping("/createDocument")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created - document created"),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})})
    @Operation(description = "Endpoint for creating a new document")
    ResponseEntity<String> createDocument();

    @GetMapping("/documentInfo/{documentId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok - document retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - no document", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})})
    @Operation(description = "Endpoint for creating a new document")
    ResponseEntity<String> getDocument(@PathVariable String documentId);
}