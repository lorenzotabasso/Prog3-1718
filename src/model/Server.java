package model;

public class Server {

    private String status;

    public ServerModel(){
        this.status = "online";
    }

    // GETTERS ---------------------------------------------------------------------------------------------------------

    public String getStatus(){
        return this.status;
    }

    // SETTERS ---------------------------------------------------------------------------------------------------------

    private void setStatus(String newStatus){
        this.status = newStatus;
    }

    // UTILITY ---------------------------------------------------------------------------------------------------------

    private void initializeSocket(){

    }
}
