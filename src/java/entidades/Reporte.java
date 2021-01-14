package entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Reporte
        implements Serializable {

    private String fecha;
    private String descripcion;
    private String cedulaParamedico;
    private String paciente;

    public Reporte(String descripcion, String cedulaParamedico, String paciente) {
        this.descripcion = descripcion;
        this.cedulaParamedico = cedulaParamedico;
        this.paciente = paciente;    
    }
    
    public Reporte(String fecha, String descripcion, String cedulaParamedico, String paciente) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.cedulaParamedico = cedulaParamedico;
        this.paciente = paciente;
    }

    public String getFecha() {
        return this.fecha;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getCedulaParamedico() {
        return this.cedulaParamedico;
    }

    public String getPaciente() {
        return this.paciente;
    }

    public boolean equals(Object cedula) {
        return this.cedulaParamedico.equals(cedula);
    }
}
