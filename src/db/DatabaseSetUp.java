package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetUp {
    private Connection connection;

    public DatabaseSetUp() throws SQLException {
        this.connection = DatabaseConnection.getConnect();
    }

    public void createTables() {
        try (Statement statement = connection.createStatement()) {
            String doctorsTable = """
                CREATE TABLE IF NOT EXISTS doctors(
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100) NOT NULL,
                    specialty VARCHAR(100),
                    phone_number VARCHAR(20),
                    email VARCHAR(150) UNIQUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;

            String patientsTable = """
                CREATE TABLE IF NOT EXISTS patients(
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100) NOT NULL,
                    date_of_birth DATE,
                    gender VARCHAR(10),
                    phone_number VARCHAR(20),
                    email VARCHAR(150) UNIQUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;

            String appointmentsTable = """
                CREATE TABLE IF NOT EXISTS appointments(
                    id SERIAL PRIMARY KEY,
                    patient_id INT REFERENCES patients(id) ON DELETE CASCADE,
                    doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                    appointment_date DATE NOT NULL,
                    status VARCHAR(20),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                     
                )
            """;

            String medicalRecords = """
                CREATE TABLE IF NOT EXISTS medical_records(
                    id SERIAL PRIMARY KEY,
                    patient_id INT REFERENCES patients(id) ON DELETE CASCADE,
                    doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                    diagnosis TEXT,
                    treatment TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;

            String doctorPatient = """
                CREATE TABLE IF NOT EXISTS doctor_patient(
                    doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                    patient_id INT REFERENCES patients(id) ON DELETE CASCADE,
                    PRIMARY KEY(doctor_id, patient_id)
                )
            """;

            String index = """
                CREATE INDEX IF NOT EXISTS idx_appointment_date ON appointments(appointment_date)
            """;

            statement.execute(doctorsTable);
            statement.execute(patientsTable);
            statement.execute(appointmentsTable);
            statement.execute(medicalRecords);
            statement.execute(doctorPatient);
            statement.execute(index);

            System.out.println("All tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}