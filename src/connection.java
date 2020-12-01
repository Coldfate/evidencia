import java.sql.*;
import java.util.Scanner;


public class connection {
    private Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = null;
        String url = "jdbc:sqlite:E://Evidencia_final/src/db/DB.db";

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void login(String username, String password) {
        String query = "SELECT username, password FROM Admin WHERE username = ? and password = ?";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection conn = null;
        try  {
            conn = connect();
            pstm = conn.prepareStatement(query);
            pstm.setString(1,username);
            pstm.setString(2,password);
            rs = pstm.executeQuery();

            if (rs.next())
            {
                System.out.println("Bienvenido: " + rs.getString("username"));
            }
            else {
                System.out.println("Usuario o contraseña incorrectos");
                main.main(null);
            }

        }catch  (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
              if (pstm != null) {
                  try {
                      pstm.close();
                  } catch (SQLException e) { }
              }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
            }
        }
    }
    public void addDoctor (String name, String speciality) {
        String query = "INSERT INTO doctors (name,speciality) VALUES (?,?)";
        Connection conn;
        PreparedStatement pstm = null;

        try {
            conn = connect();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setString(2, speciality);
            pstm.executeUpdate();

            System.out.println("Registro añadido correctamente");
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                pstm.close();

            }catch (Exception e) {

            }
        }
    }
    public void addPatient (String name) {
        String query = "INSERT INTO patients (name) VALUES (?)";
        Connection conn;
        PreparedStatement pstm = null;

        try {
            conn = connect();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, name);
            pstm.executeUpdate();

            System.out.println("Registro añadido correctamente");
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                pstm.close();

            }catch (Exception e) {

            }
        }
    }
    public void addAppointment (String date, String time, String reason) {


        String query = "INSERT INTO appointments (date,time,reason) VALUES (?,?,?)";
        Connection conn;
        Statement stmt;
        ResultSet rs;
        PreparedStatement pstm = null;

        try {
            conn = connect();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, date);
            pstm.setString(2, time);
            pstm.setString(3, reason);
            pstm.executeUpdate();

            System.out.println("Registro añadido correctamente");
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                pstm.close();

            }catch (Exception e) {

            }
        }
    }
    public void createUnion () {
        Scanner sc = new Scanner(System.in);
        int cita,doctor,paciente;
        String listAppointments = "SELECT * FROM appointments";
        String listDoctors = "SELECT * FROM doctors";
        String listPatients = "SELECT * FROM patients";

        String query = "INSERT INTO unitedAppointments (cita,doctor,paciente) VALUES (?,?,?)";

        Connection conn;
        Statement statement = null;
        Statement doctors = null;
        Statement patients = null;
        PreparedStatement pstm = null;
        ResultSet rsDoctors = null;
        ResultSet rsPatients = null;
        ResultSet rsCitas = null;
        ResultSet rsApointments = null;

        try {
            conn = connect();
            pstm = conn.prepareStatement(query);
            statement = conn.createStatement();
            doctors = conn.createStatement();
            patients = conn.createStatement();

            rsCitas = statement.executeQuery(listAppointments);
            System.out.println("Citas disponibles");
            System.out.println("ID\tFecha\t\tHora\tMotivo");
            while (rsCitas.next()) {

                System.out.println(rsCitas.getInt("id") +  "\t" +
                        rsCitas.getString("date") + "\t" +
                        rsCitas.getString("time") + "\t" +
                                rsCitas.getString("reason") + "\t");
            }
            System.out.print("Selecciona una cita por su ID: ");
            cita = sc.nextInt();

            rsDoctors = statement.executeQuery(listDoctors);
            System.out.println("Doctores disponibles");
            System.out.println("ID\tNombre\t\tEspecialidad");
            while (rsDoctors.next()) {

                System.out.println(rsDoctors.getInt("id") +  "\t" +
                        rsDoctors.getString("name") + "\t" +
                        rsDoctors.getString("speciality"));
            }
            System.out.print("Selecciona un doctor por su ID: ");
            doctor = sc.nextInt();

            rsPatients = statement.executeQuery(listPatients);
            System.out.println("Pacientes en cola");
            System.out.println("ID\tNombre");
            while (rsPatients.next()) {

                System.out.println(rsPatients.getInt("id") +  "\t" +
                        rsPatients.getString("name"));
            }
            System.out.print("Selecciona un paciente por su ID: ");
            paciente = sc.nextInt();

            pstm.setInt(1,cita);
            pstm.setInt(2,doctor);
            pstm.setInt(3,paciente);
            pstm.executeUpdate();
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                statement.close();
                rsCitas.close();
                rsApointments.close();
                rsDoctors.close();
                rsPatients.close();
            }catch (Exception e) {

            }
        }
    }
}
