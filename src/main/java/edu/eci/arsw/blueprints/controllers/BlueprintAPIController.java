package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Set<Blueprint> allBlueprints = services.getAllBlueprints(filterType);
            return new ResponseEntity<>(allBlueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(
            @PathVariable String author,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filterType) {
        try {
            Set<Blueprint> authorBlueprints = services.getBlueprintsByAuthor(author, filterType);
            return new ResponseEntity<>(authorBlueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getSpecificBlueprint(
            @PathVariable String author,
            @PathVariable String bpname,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filterType) {
        try {
            Blueprint blueprint = services.getBlueprint(author, bpname, filterType);
            return new ResponseEntity<>(blueprint, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint blueprint) {
        try {

            if (blueprint == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (blueprint.getAuthor() == null || blueprint.getAuthor().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (blueprint.getName() == null || blueprint.getName().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (blueprint.getPoints() == null ) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            services.addNewBlueprint(blueprint);


            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(
            @PathVariable String author,
            @PathVariable String bpname,
            @RequestBody Blueprint updatedBlueprint) {
        try {

            if (updatedBlueprint == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            if (!author.equals(updatedBlueprint.getAuthor()) ||
                    !bpname.equals(updatedBlueprint.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            if (updatedBlueprint.getPoints() == null ) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            services.getBlueprint(author, bpname, "");


            services.addNewBlueprint(updatedBlueprint);


            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BlueprintPersistenceException e) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}