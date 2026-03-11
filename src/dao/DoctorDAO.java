package dao;
import java.util.List;

import model.Doctor;

public interface DoctorDAO {
 void addDoctor(Doctor doctor);
List<Doctor>getAllDoctors();
Doctor getDoctorById(int id);
void deleteDoctor(int id);
}
