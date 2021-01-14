package entidades;

import BD.SQLDatabaseConnection;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Paciente
        extends Persona
        implements Serializable {

    private int id;
    private int edad;
    private int peso;
    private String tipoSangre;
    private String alergias;
    private String seguro;
    private String contacto;
    public ArrayList<Paciente> pacientes;
    String path;

    /*public void verTodos() {
        abrirFichero();
        for (Paciente p : this.pacientes) {
            System.out.println(p.getNombre() + " " + p.getId());
        }
    }*/

    public Paciente(String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia, int edad, int peso, String tipoSangre, String alergias, String seguro, String contacto) {
       // this.path = "C:\\Users\\joshu\\Documents\\QrEM\\";
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.contrasenia = contrasenia;
        this.edad = edad;
        this.peso = peso;
        this.tipoSangre = tipoSangre;
        this.alergias = alergias;
        this.seguro = seguro;
        this.contacto = contacto;
    //    this.pacientes = new ArrayList<>();
    }

    public Paciente(int id, String contrasenia) {
      //  this.path = "C:\\Users\\joshu\\Documents\\QrEM\\";
        this.id = id;
        this.contrasenia = contrasenia;
      //  this.pacientes = new ArrayList<>();
    }

    /*public int guardar() {
        abrirFichero();
        if (this.pacientes.size() == 0) {
            this.id = 0;
        } else {
            this.id = ((Paciente) this.pacientes.get(this.pacientes.size() - 1)).getId() + 1;
        }
        this.pacientes.add(this);
        guardarFichero();
        return this.id;
    }*/
    public int guardar() {
        String insertSql = "insert into paciente(nombre,apellidoMaterno,apellidoPaterno,pass,edad,peso,tipoSangre,alergias,seguro,contacto) OUTPUT Inserted.id values("
                + "'" + this.nombre + "', '" + this.apellidoPaterno + "', '" + this.apellidoMaterno + "', '" + this.contrasenia +
                "', '" + this.edad +"', '" + this.peso +"', '" + this.tipoSangre +  "', '" + this.alergias +"','" + this.seguro +"','" + this.contacto + "');";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            ResultSet res = db.consulta(insertSql);
            if (res.next()) {
                this.id = res.getInt("id");
            }
            db.cierra();
        } catch (SQLException e) {
            return -1;
        }
        return this.id;
    }

    

    public boolean modificar() {
        /*System.out.println(((Paciente) this.pacientes.get(indice)).getContrasenia() + "    " + this.contrasenia);
        if (indice == -1 || !((Paciente) this.pacientes.get(indice)).getContrasenia().equals(this.contrasenia)) {
            return false;
        }
        /*update paciente set nombre='Josh', apellidoPaterno='Soria', apellidoMaterno='Láscares', edad='20',
	peso='100', tipoSangre='o+', alergias='$$', seguro='imss', contacto='5512369874' where id=1 and pass='123'
        this.pacientes.remove(indice);
        this.pacientes.add(indice, this);
        guardarFichero();
        return true;*/
        String insertSql = "update paciente set "
                + "nombre='" + this.nombre + "', apellidoPaterno='" + this.apellidoPaterno + "', apellidoMaterno='" + this.apellidoMaterno + 
                "', edad='" + this.edad +"', peso='" + this.peso +"', tipoSangre='" + this.tipoSangre +
                "', alergias='" + this.alergias +"', seguro='" + this.seguro +"', contacto='" + this.contacto +"' where id='" + this.id +"' and pass= '" 
                + this.contrasenia + "';";
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
        /*abrirFichero();
        int indice = this.pacientes.indexOf(this);
        if (indice == -1) {
            return -1;
        }
        if (tipo == 0 || tipo == 2) {
            Paciente auxiliar = this.pacientes.get(indice);
            if (tipo == 2 && !auxiliar.getContrasenia().equals(this.contrasenia)) {
                return -2;
            }
            this.nombre = auxiliar.getNombre();
            this.apellidoPaterno = auxiliar.getApellidoPaterno();
            this.apellidoMaterno = auxiliar.getApellidoMaterno();
            this.edad = auxiliar.getEdad();
            this.peso = auxiliar.getPeso();
            this.tipoSangre = auxiliar.getTipoSangre();
            this.alergias = auxiliar.getAlergias();
            this.seguro = auxiliar.getSeguro();
            this.contacto = auxiliar.getContacto();
        }
        return indice;*/
        String consulta = "select * from paciente where id='" + this.id + "' and pass = '" + this.contrasenia + "'";
        SQLDatabaseConnection db = new SQLDatabaseConnection();
        try {
            ResultSet res = db.consulta(consulta);
            if (res.next()) {
                this.nombre = res.getString("nombre");
                this.apellidoPaterno = res.getString("apellidoPaterno");
                this.apellidoMaterno = res.getString("apellidoMaterno");
                this.edad = res.getInt("edad");
                this.peso = res.getInt("peso");
                this.tipoSangre = res.getString("tipoSangre");
                this.alergias = res.getString("alergias");
                this.seguro = res.getString("seguro");
                this.contacto = res.getString("contacto");
            }
            db.cierra();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getTipoSangre() {
        return this.tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getAlergias() {
        return this.alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getSeguro() {
        return this.seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getContacto() {
        return this.contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean equals(Object objeto) {
        objeto = objeto;
        return (this.id == ((Paciente) objeto).getId());
    }
    /*public void abrirFichero() {
        try {
            ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(this.path + "pacientes.dat"));

            this.pacientes = (ArrayList<Paciente>) leyendoFichero.readObject();
            leyendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void guardarFichero() {
        try {
            ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(this.path + "pacientes.dat"));

            escribiendoFichero.writeObject(this.pacientes);
            escribiendoFichero.flush();
            escribiendoFichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/
}
