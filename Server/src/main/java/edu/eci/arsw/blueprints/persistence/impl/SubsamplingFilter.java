package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component

public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filteredPoints = new ArrayList<>();
        List<Point> points = bp.getPoints();

        for (int i = 0; i < points.size(); i += 2) {
            filteredPoints.add(points.get(i));
        }

        return new Blueprint(bp.getAuthor(), bp.getName(), filteredPoints.toArray(new Point[0]));
    }
}
