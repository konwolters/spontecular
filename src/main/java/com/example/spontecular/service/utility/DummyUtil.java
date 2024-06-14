package com.example.spontecular.service.utility;

import com.example.spontecular.dto.formDtos.ClassItem;

import java.util.Arrays;
import java.util.List;

public class DummyUtil {

    public static List<ClassItem> getClassesDummyData () {
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
}
