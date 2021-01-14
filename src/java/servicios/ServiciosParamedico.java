/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

/**
 *
 * @author joshu
 */
import entidades.Paciente;
import entidades.Paramedico;
import entidades.Reporte;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("paramedico")
public class ServiciosParamedico {

    @Context
    private UriInfo context;

    @GET
    @Path("login")
    @Produces({"application/json"})
    public String logInParamedico(@QueryParam("contrasenia") String contrasenia, @QueryParam("cedula") String cedula) {
        Paramedico paramedico = new Paramedico(cedula, contrasenia);

        String mensaje = (paramedico.buscar()) ? "Bienvendio" : "Cedula o contraseña incorrecto";
        JsonObjectBuilder res = Json.createObjectBuilder();
        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("registro")
    @Produces({"application/json"})
    public String registroParamedico(@QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("contrasenia") String contrasenia, @QueryParam("cedula") String cedula) {
        Paramedico paramedico = new Paramedico(cedula, nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        String mensaje = paramedico.guardar() ? "Registrado exitosamente" : "Ya se registró esa cédula";
        JsonObjectBuilder res = Json.createObjectBuilder();
        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("consultaPaciente")
    @Produces({"application/json"})
    public String consultaPaciente(@QueryParam("id") int id) {
        String mensaje;
        JsonObjectBuilder res = Json.createObjectBuilder();
        Paciente paciente = Paramedico.buscarPaciente(id);
        if (paciente!=null) {
            res.add("nombre", paciente.getNombre())
                    .add("apellidoPaterno", paciente.getApellidoPaterno())
                    .add("apellidoMaterno", paciente.getApellidoMaterno())
                    .add("edad", paciente.getEdad())
                    .add("peso", paciente.getPeso())
                    .add("tipoSangre", paciente.getTipoSangre())
                    .add("alergias", paciente.getAlergias())
                    .add("seguro", paciente.getSeguro())
                    .add("contacto", paciente.getContacto());
            mensaje = "Consultado exitosamente";
        } else {
            mensaje = "No se encuentra";
        }
        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("reporte")
    @Produces({"application/json"})
    public String guardaReporte(@QueryParam("paciente") String paciente, @QueryParam("cedula") String cedula, @QueryParam("descripcion") String descripcion, @QueryParam("contrasenia") String contrasenia) {
        String mensaje, fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        JsonObjectBuilder res = Json.createObjectBuilder();
        Paramedico paramedico = new Paramedico(cedula, contrasenia);
        if (paramedico.buscar()) {
            if(paramedico.hacerReporte(new Reporte(descripcion, cedula, paciente)))
                mensaje = "Guardado correctamente";
            else
                mensaje="Ocurrió un error al guardar el reporte";
        } else {
            mensaje = "Cedula o contraseña incorrecto";
        }
        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("bitacora")
    @Produces({"application/json"})
    public String consultaBitacora(@QueryParam("cedula") String cedula, @QueryParam("contrasenia") String contrasenia) {
        String mensaje;
        JsonObjectBuilder rep = Json.createObjectBuilder();
        JsonArrayBuilder res = Json.createArrayBuilder();
        Paramedico paramedico = new Paramedico(cedula, contrasenia);
        ArrayList<Reporte> reportes = null;

        if (paramedico.buscar()) {
            reportes = paramedico.getBitacora();
            for (Reporte reporte : reportes) {

                rep.add("paciente", reporte.getPaciente())
                        .add("fecha", reporte.getFecha())
                        .add("descripcion", reporte.getDescripcion());
                res.add(rep);
                rep = Json.createObjectBuilder();
            }
            mensaje = "Consultado exitosamente";
        } else {
            mensaje = "Cedula o contraseña incorrecto";
        }
        rep.add("paciente", mensaje)
                .add("fecha", paramedico.getNombre() + " " + paramedico.getApellidoPaterno() + " " + paramedico.getApellidoMaterno());
        res.add(rep);
        JsonArray rspuesta = res.build();
        return rspuesta.toString();
    }
}

