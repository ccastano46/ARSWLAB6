package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filteredPoints = new ArrayList<>();
        Point prevPoint = null;

        for (Point p : bp.getPoints()) {
            if (prevPoint == null || !(p.getX() == prevPoint.getX() && p.getY() == prevPoint.getY())) {
                filteredPoints.add(p);
            }
            prevPoint = p;
        }

        return new Blueprint(bp.getAuthor(), bp.getName(), filteredPoints.toArray(new Point[0]));
    }
}
