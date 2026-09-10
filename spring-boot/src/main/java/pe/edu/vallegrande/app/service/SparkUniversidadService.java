package pe.edu.vallegrande.app.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SparkUniversidadService {

        private final SparkSession spark;

        // MySQL (estudiantes)
        private final String mysqlUrl;
        private final String mysqlUsername;
        private final String mysqlPassword;
        private final String mysqlDriver;

        // MariaDB (carrera_universidad)
        private final String mariadbUrl;
        private final String mariadbUsername;
        private final String mariadbPassword;
        private final String mariadbDriver;

        // PostgreSQL (matriculas)
        private final String postgresUrl;
        private final String postgresUsername;
        private final String postgresPassword;
        private final String postgresDriver;

        public SparkUniversidadService(
                        @Value("${app.mysql.url}") String mysqlUrl,
                        @Value("${app.mysql.username}") String mysqlUsername,
                        @Value("${app.mysql.password}") String mysqlPassword,
                        @Value("${app.mysql.driver}") String mysqlDriver,

                        @Value("${app.mariadb.url}") String mariadbUrl,
                        @Value("${app.mariadb.username}") String mariadbUsername,
                        @Value("${app.mariadb.password}") String mariadbPassword,
                        @Value("${app.mariadb.driver}") String mariadbDriver,

                        @Value("${app.postgres.url}") String postgresUrl,
                        @Value("${app.postgres.username}") String postgresUsername,
                        @Value("${app.postgres.password}") String postgresPassword,
                        @Value("${app.postgres.driver}") String postgresDriver) {
                this.mysqlUrl = mysqlUrl;
                this.mysqlUsername = mysqlUsername;
                this.mysqlPassword = mysqlPassword;
                this.mysqlDriver = mysqlDriver;

                this.mariadbUrl = mariadbUrl;
                this.mariadbUsername = mariadbUsername;
                this.mariadbPassword = mariadbPassword;
                this.mariadbDriver = mariadbDriver;

                this.postgresUrl = postgresUrl;
                this.postgresUsername = postgresUsername;
                this.postgresPassword = postgresPassword;
                this.postgresDriver = postgresDriver;

                this.spark = SparkSession.builder()
                                .appName("UniversidadSparkDemo")
                                .master("local[*]")
                                .getOrCreate();
        }

        /**
         * JOIN entre las 3 bases de datos:
         * MySQL (estudiantes) + MariaDB (carrera_universidad) + PostgreSQL (matriculas)
         */
        public Dataset<Row> obtenerResumen() {
                Dataset<Row> estudiantes = leerEstudiantes();
                Dataset<Row> carreras = leerCarreras();
                Dataset<Row> matriculas = leerMatriculas();

                return estudiantes
                                .join(carreras,
                                                estudiantes.col("codigo").equalTo(carreras.col("codigo_estudiante")),
                                                "inner")
                                .join(matriculas,
                                                estudiantes.col("id").equalTo(matriculas.col("estudiante_id")),
                                                "inner")
                                .select(
                                                estudiantes.col("codigo"),
                                                estudiantes.col("nombre"),
                                                estudiantes.col("apellido"),
                                                estudiantes.col("semestre").alias("semestre_estudiante"),
                                                carreras.col("carrera"),
                                                carreras.col("universidad"),
                                                matriculas.col("curso"),
                                                matriculas.col("creditos"),
                                                matriculas.col("semestre").alias("semestre_curso"),
                                                matriculas.col("nota"));
        }

        // ========== 1. MySQL → estudiantes ==========
        private Dataset<Row> leerEstudiantes() {
                return spark.read()
                                .format("jdbc")
                                .option("url", mysqlUrl)
                                .option("dbtable", "estudiantes")
                                .option("user", mysqlUsername)
                                .option("password", mysqlPassword)
                                .option("driver", mysqlDriver)
                                .load();
        }

        // ========== 2. MariaDB → carrera_universidad ==========
        private Dataset<Row> leerCarreras() {
                return spark.read()
                                .format("jdbc")
                                .option("url", mariadbUrl)
                                .option("query", "SELECT codigo_estudiante, carrera, universidad FROM carrera_spark")
                                .option("user", mariadbUsername)
                                .option("password", mariadbPassword)
                                .option("driver", mariadbDriver)
                                .load();
        }

        // ========== 3. PostgreSQL → matriculas ==========
        private Dataset<Row> leerMatriculas() {
                return spark.read()
                                .format("jdbc")
                                .option("url", postgresUrl)
                                .option("dbtable", "public.matriculas")
                                .option("user", postgresUsername)
                                .option("password", postgresPassword)
                                .option("driver", postgresDriver)
                                .load();
        }


}