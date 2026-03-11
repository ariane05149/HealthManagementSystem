package daoImpl;

import dao.DoctorDAO;
import db.DatabaseConnection;
import model.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
    private Connection connection;

    public DoctorDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getConnect();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        String sql = "insert into doctors(first_name,last_name,specialty,phone_number,email) values(?,?,?,?,?)";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setString(1, doctor.getFirstName());
            pr.setString(2, doctor.getLastName());
            pr.setString(3, doctor.getSpecialty());
            pr.setString(4, doctor.getPhoneNumber());
            pr.setString(5, doctor.getEmail());
            pr.executeUpdate();
            System.out.println("Doctor inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "select * from doctors";
        try (PreparedStatement pr = connection.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setPhoneNumber(rs.getString("phone_number"));
                doctor.setEmail(rs.getString("email"));
                doctors.add(doctor);
            }
            return doctors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Doctor getDoctorById(int id) {
        String sql = "Select * from doctors where id=?";
        Doctor doctor = null;
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setPhoneNumber(rs.getString("phone_number"));
                doctor.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Doctor deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}