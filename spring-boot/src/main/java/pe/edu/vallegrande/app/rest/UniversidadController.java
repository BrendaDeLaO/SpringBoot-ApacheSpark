package pe.edu.vallegrande.app.rest;

import pe.edu.vallegrande.app.service.SparkUniversidadService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/universidad")
public class UniversidadController {

    private final SparkUniversidadService sparkService;

    public UniversidadController(SparkUniversidadService sparkService) {
        this.sparkService = sparkService;
    }

    @GetMapping("/matriculas")
    public List<Map<String, Object>> obtenerMatriculas() {

        Dataset<Row> resultado = sparkService.obtenerResumen();

        return resultado.collectAsList()
                .stream()
                .map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", row.getAs("codigo"));
                    map.put("nombre", row.getAs("nombre"));
                    map.put("apellido", row.getAs("apellido"));
                    map.put("carrera", row.getAs("carrera"));
                    map.put("universidad", row.getAs("universidad"));
                    map.put("curso", row.getAs("curso"));
                    map.put("creditos", row.getAs("creditos"));
                    map.put("nota", row.getAs("nota"));
                    return map;
                })
                .toList();
    }

}