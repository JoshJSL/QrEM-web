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
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("paciente")
public class ServiciosPaciente {

    @Context
    private UriInfo context;

    @GET
    @Path("registro")
    @Produces({"application/json"})
    public String registroPaciente(@QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("contrasenia") String contrasenia, @QueryParam("edad") int edad, @QueryParam("peso") int peso, @QueryParam("tipoSangre") String tipoSangre, @QueryParam("alergias") String alergias, @QueryParam("contacto") String contacto, @QueryParam("seguro") String seguro) {
        Paciente paciente = new Paciente(nombre, apellidoPaterno, apellidoMaterno, contrasenia, edad, peso, tipoSangre, alergias, seguro, contacto);
        int id = paciente.guardar();

        JsonObjectBuilder res = Json.createObjectBuilder();
        res.add("mensaje", id);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("consulta")
    @Produces({"application/json"})
    public String consultaPaciente(@QueryParam("id") int id, @QueryParam("contrasenia") String contrasenia) {
        String mensaje;
        JsonObjectBuilder res = Json.createObjectBuilder();
        Paciente paciente = new Paciente(id, contrasenia);

        if (!paciente.buscar()) {
            mensaje = "Código Qr o Contraseña incorrecta";
        } else {
            res.add("nombre", paciente.getNombre());
            res.add("apellidoPaterno", paciente.getApellidoPaterno());
            res.add("apellidoMaterno", paciente.getApellidoMaterno());
            res.add("edad", paciente.getEdad());
            res.add("peso", paciente.getPeso());
            res.add("tipoSangre", paciente.getTipoSangre());
            res.add("alergias", paciente.getAlergias());
            res.add("seguro", paciente.getSeguro());
            res.add("contacto", paciente.getContacto());
            mensaje = "Consultado exitosamente";
        }

        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @GET
    @Path("modifica")
    @Produces({"application/json"})
    public String modificaPaciente(@QueryParam("id") int id, @QueryParam("contrasenia") String contrasenia, @QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("edad") int edad, @QueryParam("peso") int peso, @QueryParam("tipoSangre") String tipoSangre, @QueryParam("alergias") String alergias, @QueryParam("contacto") String contacto, @QueryParam("seguro") String seguro) {
        String mensaje;
        JsonObjectBuilder res = Json.createObjectBuilder();
        Paciente paciente = new Paciente(id, contrasenia);

        if (paciente.buscar()) {
            paciente = new Paciente(nombre, apellidoPaterno, apellidoMaterno, contrasenia, edad, peso, tipoSangre, alergias, seguro, contacto);
            paciente.setId(id);
            if (paciente.modificar()) {
                mensaje = "Modificado exitosamente";
            } else {
                mensaje = "Error al modificar";
            }
        } else {
            mensaje = "Código Qr o Contraseña incorrecta";
        }

        res.add("mensaje", mensaje);
        JsonObject rspuesta = res.build();
        return rspuesta.toString();
    }

    @PUT
    @Consumes({"application/json"})
    public void putJson(String content) {
    }
}

