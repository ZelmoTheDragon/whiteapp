package com.github.zelmothedragon.whiteapp.dynamic;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class RestConfiguration extends Application {

    public RestConfiguration() {
        // Ne pas appeler explicitement
    }

}
