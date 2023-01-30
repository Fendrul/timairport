package be.technifutur.java.timairport.exception;

public class RessourceNotFoundException extends RuntimeException {

    public RessourceNotFoundException() {
        super("The requested ressource was not found");
    }

}
