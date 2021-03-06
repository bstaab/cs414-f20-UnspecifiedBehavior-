package com.mrpowers.requests;

import com.mrpowers.QueryBuilder;
import com.mrpowers.exceptions.IllegalMoveException;
import com.mrpowers.exceptions.IllegalPositionException;

public class Reject extends RequestData{
    public String to;
    public String from;
    public Boolean valid;

    public void Do(){
        valid=false;
        QueryBuilder.connectDb();
        QueryBuilder.connectDb();
        QueryBuilder.getMessagesTable();
        QueryBuilder.removeMessage(to, from, "INVITATION");
        QueryBuilder.addMessage(to, from, "REJECTED");
        QueryBuilder.disconnectDb();
        valid=true;
    }

    @Override
    public void buildResponse() throws RequestException, IllegalMoveException, IllegalPositionException {
        try{this.Do();}
        catch (Exception e){

        }
    }
}
