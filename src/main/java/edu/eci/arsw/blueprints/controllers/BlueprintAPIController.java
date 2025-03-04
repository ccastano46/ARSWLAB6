package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {

    private final BlueprintsServices services;

    @Autowired
    public BlueprintAPIController(BlueprintsServices services) {
        this.services = services;
    }

    @GetMapping()
    public ResponseEntity<?> getAllBlueprints(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filterType) {
        try {
            // Get all blueprints with optional filtering
            Set<Blueprint> allBlueprints = services.getAllBlueprints(filterType);

            // Return the blueprints with OK status
            return new ResponseEntity<>(allBlueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            // If no blueprints are found, return NOT_FOUND status
            return new ResponseEntity<>("No blueprints found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle any other unexpected errors
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(
            @PathVariable String author,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filterType) {
        try {
            // Get blueprints for the specific author with optional filtering
            Set<Blueprint> authorBlueprints = services.getBlueprintsByAuthor(author, filterType);

            // Return the blueprints with OK status
            return new ResponseEntity<>(authorBlueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            // If no blueprints found for the author, return NOT_FOUND status
            return new ResponseEntity<>("No blueprints found for author: " + author, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle any other unexpected errors
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}