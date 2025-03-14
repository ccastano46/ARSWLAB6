package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        // Initial test data
        Point[] pts1 = new Point[]{ new Point(140, 140), new Point(115, 115) };
        Blueprint bp1 = new Blueprint("author1", "blueprint1", pts1);

        Point[] pts2 = new Point[]{ new Point(200, 200), new Point(250, 250) };
        Blueprint bp2 = new Blueprint("author1", "blueprint2", pts2);

        Point[] pts3 = new Point[]{ new Point(300, 300), new Point(350, 350) };
        Blueprint bp3 = new Blueprint("author2", "blueprint3", pts3);

        // New blueprints
        Point[] pts4 = new Point[]{ new Point(10, 10), new Point(50, 50), new Point(100, 100) };
        Blueprint bp4 = new Blueprint("author3", "blueprint4", pts4);

        Point[] pts5 = new Point[]{ new Point(500, 500), new Point(550, 550) };
        Blueprint bp5 = new Blueprint("author2", "blueprint5", pts5);

        Point[] pts6 = new Point[]{ new Point(700, 700), new Point(750, 750), new Point(800, 800) };
        Blueprint bp6 = new Blueprint("author3", "blueprint6", pts6);

        // Add all blueprints to the map
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);
        blueprints.put(new Tuple<>(bp5.getAuthor(), bp5.getName()), bp5);
        blueprints.put(new Tuple<>(bp6.getAuthor(), bp6.getName()), bp6);
    }

    @Override
    public void saveBlueprint(Blueprint bp) {
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + " - " + bprintname);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                authorBlueprints.add(entry.getValue());
            }
        }
        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return authorBlueprints;
    }
}