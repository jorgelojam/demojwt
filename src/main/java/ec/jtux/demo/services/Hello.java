package ec.jtux.demo.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/saludos")
public class Hello {

    @GET
    @Path("/hola")
    @Produces({MediaType.TEXT_PLAIN})
    @RolesAllowed("user")
    public String decirHola(){
        return "Hola";
    }

    @GET
    @Path("/chao")
    @Produces({MediaType.TEXT_PLAIN})
    @RolesAllowed("user")
    public String decirChao(){
        return "chao";
    }
    
}
