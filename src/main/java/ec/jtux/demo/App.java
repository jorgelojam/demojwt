package ec.jtux.demo;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/api")
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"admin","user"})
public class App extends Application{

    public App(){

    }
    
}
