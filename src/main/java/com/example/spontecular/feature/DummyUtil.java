package com.example.spontecular.feature;

import com.example.spontecular.feature.classes.ClassItem;
import com.example.spontecular.feature.constraints.ConstraintsItem;
import com.example.spontecular.feature.hierarchy.HierarchyItem;
import com.example.spontecular.feature.relations.RelationItem;

import java.util.Arrays;
import java.util.List;

public class DummyUtil {

    public static List<ClassItem> getClassesDummyData() {
        return Arrays.asList(
                new ClassItem("Satellite", false),
                new ClassItem("Chassis", false),
                new ClassItem("Framework", false),
                new ClassItem("Rail", false),
                new ClassItem("Sidewall", false),
                new ClassItem("Circuit board", false),
                new ClassItem("Solar cell", false),
                new ClassItem("Sensor wire", false),
                new ClassItem("Magnetic coil", false),
                new ClassItem("Groove", false),
                new ClassItem("Attitude Determination and Control System", false),
                new ClassItem("Connector", false),
                new ClassItem("Module", false),
                new ClassItem("Bus connector", false),
                new ClassItem("Cable", false)
        );
    }

    public static List<HierarchyItem> getHierarchyDummyData() {
        return Arrays.asList(
                new HierarchyItem("Sidewall", "Rail", false),
                new HierarchyItem("Framework", "Chassis", false),
                new HierarchyItem("Component", "Framework", false),
                new HierarchyItem("Component", "Sidewall", false),
                new HierarchyItem("Component", "Circuit board", false),
                new HierarchyItem("Component", "Elastic blushing", false),
                new HierarchyItem("Circuit board", "Double-sided circuit board", false),
                new HierarchyItem("Circuit board", "FR-4", false),
                new HierarchyItem("Circuit board", "Printed Circuit Board", false),
                new HierarchyItem("Component", "Connector", false),
                new HierarchyItem("Connector", "Bus connector", false)
        );
    }

    public static List<RelationItem> getRelationsDummyData() {
        return Arrays.asList(
                new RelationItem("Chassis", "consistsOf", "Framework", false),
                new RelationItem("Sidewall", "isMadeFrom", "Circuit board", false),
                new RelationItem("Sidewall", "servesAs", "Circuit board", false),
                new RelationItem("Double-sided circuit board", "mayServeAs", "Circuit board", false),
                new RelationItem("Solar cell", "isMountedOn", "Printed circuit board", false),
                new RelationItem("Satellite", "needs", "Connector", false),
                new RelationItem("Internal module", "consistOf", "FR-4", false),
                new RelationItem("Internal module", "consistOf", "Circuit board", false),
                new RelationItem("Module", "isStackedInside", "Satellite", false),
                new RelationItem("Elastic bushing", "isPlacedIn", "Groove", false)
        );
    }

    public static List<ConstraintsItem> getConstraintsDummyData() {
        return Arrays.asList(
                new ConstraintsItem("Chassis", "consistsOf", "Framework", "1", "1", false),
                new ConstraintsItem("Sidewall", "isMadeFrom", "Circuit board", "1", "1", false),
                new ConstraintsItem("Sidewall", "servesAs", "Circuit board", "1", "1", false),
                new ConstraintsItem("Double-sided circuit board", "mayServeAs", "Circuit board", "1", "1", false),
                new ConstraintsItem("Solar cell", "isMountedOn", "Printed circuit board", "1", "1", false),
                new ConstraintsItem("Satellite", "needs", "Connector", "1", "1", false),
                new ConstraintsItem("Internal module", "consistOf", "FR-4", "1", "1", false),
                new ConstraintsItem("Internal module", "consistOf", "Circuit board", "1", "1", false),
                new ConstraintsItem("Module", "isStackedInside", "Satellite", "1", "1", false),
                new ConstraintsItem("Elastic bushing", "isPlacedIn", "Groove", "1", "1", false)
        );
    }
}
