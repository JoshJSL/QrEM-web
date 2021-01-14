package entidades;

import BD.SQLDatabaseConnection;
/*import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;*/
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Paramedico
        extends Persona
        implements Serializable {

    private String cedula;

    public Paramedico(String cedula, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        // this.path = "C:\\Users\\joshu\\Documents\\QrEM\\";
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.contrasenia = contrasenia;
        //this.reportes = new ArrayList<>();
        //this.paramedicos = new ArrayList<>();
    }

    public Paramedico(String cedula, String contrasenia) {
        //this.path = "C:\\Users\\joshu\\Documents\\QrEM\\";
        this.cedula = cedula;
        this.contrasenia = contrasenia;
        //this.reportes = new ArrayList<>();
        //this.paramedicos = new ArrayList<>();
    }

    public ArrayList<Reporte> getBitacora() {
        ArrayList<Reporte> reportes = new ArrayList<>();
        /*abrirFicheroReportes();
        for (int i = 0; i < this.reportes.size(); i++) {
            if (((Reporte) this.reportes.get(i)).getCedulaParamedico().equals(this.cedula)) {
                reportes.add(this.reportes.get(i));
            }
        }*/
        String consulta = "select * from reporte where cedulaParamedico = '" + this.cedula + "'";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            ResultSet res = db.consulta(consulta);
            while (res.next()) {
                reportes.add(new Reporte(res.getString("fecha"), res.getString("descripcion"), res.getString("cedulaParamedico"), res.getString("paciente")));
            }
            db.cierra();
        } catch (SQLException e) {
            return null;
        }
        return reportes;
    }

    public boolean hacerReporte(Reporte reporte) {
        String insertSql = "insert into reporte (fecha,descripcion,cedulaParamedico, paciente) values (cast(getdate() as date),'" + reporte.getDescripcion() + "','" + reporte.getCedulaParamedico() + "','" + reporte.getPaciente() + "')";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            if (db.insert(insertSql) == 1) {
                db.cierra();
                return true;
            }
            db.cierra();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean guardar() {
        String insertSql = "INSERT INTO paramedico (nombre,apellidoPaterno,apellidoMaterno,pass,cedula) VALUES "
                + "('" + this.nombre + "', '" + this.apellidoPaterno + "', '" + this.apellidoMaterno + "', '" + this.contrasenia + "', '" + this.cedula + "');";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            if (db.insert(insertSql) == 1) {
                db.cierra();
                return true;
            }
            db.cierra();
        } catch (SQLException e) {
            
            System.out.println(e);
        }
        
        return false;
    }

    public boolean buscar() {
        /*abrirFicheroParamedicos();
        int indice = this.paramedicos.indexOf(this);
        if (indice == -1) {
            return -1;
        }
        Paramedico auxiliar = this.paramedicos.get(indice);
        this.nombre = auxiliar.getNombre();
        this.apellidoPaterno = auxiliar.getApellidoPaterno();
        this.apellidoMaterno = auxiliar.getApellidoMaterno();
        this.contrasenia = auxiliar.getContrasenia();
        return indice;*/
        String consulta = "select * from paramedico where cedula = '" + this.cedula + "' and pass = '" + this.contrasenia + "'";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            ResultSet res = db.consulta(consulta);
            if (res.next()) {
                this.nombre = res.getString("nombre");
                this.apellidoPaterno = res.getString("apellidoPaterno");
                this.apellidoMaterno = res.getString("apellidoMaterno");
            }
            db.cierra();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public static Paciente buscarPaciente(int id){
        Paciente pas=null;
    String consulta = "select * from paciente where id='" + id + "'";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            ResultSet res = db.consulta(consulta);
            if (res.next()) {
                pas=new Paciente(res.getString("nombre"), res.getString("apellidoPaterno"), res.getString("apellidoMaterno")
                        , "", res.getInt("edad"), res.getInt("peso"), res.getString("tipoSangre"), res.getString("alergias")
                        , res.getString("seguro"), res.getString("contacto"));

            }
            db.cierra();
        } catch (SQLException e) {
            return null;
        }
        return pas;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    //ArrayList<Paramedico> paramedicos;
    //String path;

    /*public void verTodos() {
        abrirFicheroParamedicos();
        for (Paramedico p : this.paramedicos) {
            System.out.println(p.getNombre() + p.getCedula() + p.getContrasenia());
        }
    }

    public void verReportes() {
        abrirFicheroReportes();
        for (Reporte reporte : this.reportes) {
            System.out.println(reporte.getCedulaParamedico() + " " + reporte.getDescripcion() + " " + reporte.getFecha());
        }
    }*/
 /*
    public void abrirFicheroReportes() {
        try {
            ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(this.path + "reportes.dat"));

            this.reportes = (ArrayList<Reporte>) leyendoFichero.readObject();
            leyendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void guardarFicheroReportes() {
        try {
            ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(this.path + "reportes.dat"));

            escribiendoFichero.writeObject(this.reportes);
            escribiendoFichero.flush();
            escribiendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void guardarFicheroParamedicos() {
        try {
            ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(this.path + "paramedicos.dat"));

            escribiendoFichero.writeObject(this.paramedicos);
            escribiendoFichero.flush();
            escribiendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void abrirFicheroParamedicos() {
        try {
            ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(this.path + "paramedicos.dat"));

            this.paramedicos = (ArrayList<Paramedico>) leyendoFichero.readObject();
            leyendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean equals(Object paremdico) {
        return (this.cedula.equals(((Paramedico) paremdico).getCedula()) && this.contrasenia.equals(((Paramedico) paremdico).getContrasenia()));
    }*/
}
