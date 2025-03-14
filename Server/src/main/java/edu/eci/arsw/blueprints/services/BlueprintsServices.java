package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlueprintsServices {

    @Autowired
    private BlueprintsPersistence bpPersistence;

    @Autowired
    private BlueprintFilter redundancyFilter; // Redundancy filter
    @Autowired
    private BlueprintFilter subsamplingFilter; // Subsampling filter

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpPersistence.saveBlueprint(bp);
    }

    public Blueprint getBlueprint(String author, String name, String filterType) throws BlueprintNotFoundException {
        Blueprint bp = bpPersistence.getBlueprint(author, name);
        return applyFilter(bp, filterType);
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author, String filterType) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpPersistence.getBlueprintsByAuthor(author);
        return blueprints.stream()
                .map(bp -> applyFilter(bp, filterType))
                .collect(Collectors.toSet());
    }

    // New method to get all blueprints with optional filtering
    public Set<Blueprint> getAllBlueprints(String filterType) throws BlueprintNotFoundException {
        // Method to get unique authors (you might want to improve this in a real implementation)
        Set<String> authors = getUniqueAuthors();

        Set<Blueprint> allBlueprints = new HashSet<>();

        for (String author : authors) {
            Set<Blueprint> authorBlueprints = getBlueprintsByAuthor(author, filterType);
            allBlueprints.addAll(authorBlueprints);
        }

        if (allBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found");
        }

        return allBlueprints;
    }

    private Blueprint applyFilter(Blueprint bp, String filterType) {
        if ("redundancy".equalsIgnoreCase(filterType)) {
            return redundancyFilter.filter(bp);
        } else if ("subsampling".equalsIgnoreCase(filterType)) {
            return subsamplingFilter.filter(bp);
        } else {
            return bp;
        }
    }

    // Temporary method to get unique authors
    private Set<String> getUniqueAuthors() {
        Set<String> authors = new HashSet<>();
        try {
            // This is a temporary solution. In a real application,
            // you'd want a more robust way to get all authors
            authors.add("author1");
            authors.add("author2");
            authors.add("author3");
        } catch (Exception e) {
            // Handle potential errors
        }
        return authors;
    }
}