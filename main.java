import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String userName, password, redo;
        int options;
        boolean repeat = true;
        Scanner sc = new Scanner(System.in);
        connection actions = new connection();

        System.out.print("Introduce el nombre de usuario: ");
        userName = sc.nextLine();
        System.out.print("Introduce la contraseña: ");
        password = sc.nextLine();
        //Validación de login
        actions.login(userName,password);
        
        do {
            System.out.println("Selecciona una opción: \n1-Dar de alta doctores.\n2-Dar de alta pacientes\n" +
                    "3-Crear una cita\n4-Relacionar cita con un doctor y un paciente");
            options = sc.nextInt();
            switch (options) {
                case 1 -> {
                    String nameDoc;
                    String speciality;
                    System.out.print("Introduce el nombre del doctor: ");
                    nameDoc = sc.next();
                    System.out.print("Introduce la especialidad del doctor: ");
                    speciality = sc.next();
                    actions.addDoctor(nameDoc, speciality);
                }
                case 2 -> {
                    String namePat;
                    System.out.print("Introduce el nombre del Paciente: ");
                    namePat = sc.next();
                    actions.addPatient(namePat);
                }
                case 3 -> {
                    String date, time, reason;
                    System.out.println("Introduce la fecha en el siguiente formato: AAAA-MM-DD\nEjemplo: 2016-01-01");
                    date = sc.next();
                    System.out.println("Introduce la hora en el siguiente formato: HH:MM:SS");
                    time = sc.next();
                    System.out.println("Introduce el motivo de la cita (Opcional)");
                    reason = sc.next();
                    actions.addAppointment(date, time, reason);
                }
                case 4 -> actions.createUnion();
            }
            System.out.println("Seleccionar otra opcion? (s/n)");
            redo = sc.next();
            if (redo.equals("s")) {
                main(null);
            }
            else{
                repeat = false;
            }
            
        }while (repeat);
    }
}
