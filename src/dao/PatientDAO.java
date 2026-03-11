package dao;

import model.Patient;

import java.util.List;

public interface PatientDAO {
    void addPatient(Patient patient);

    Patient getPatientById(int id);

    List<Patient> getAllPatients();

    void deletePatient(int id);
}

