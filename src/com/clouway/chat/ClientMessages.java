package com.clouway.chat;

import java.net.Socket;
import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientMessages {



  public String displayTotalClients(int count){

    return "There are " + count + " connected users.";

  }


  public String onClientConnect(int number){

    return "Client #" + number + " has just connected";

  }


  public String onClientDisconnect(){

    return "Client just got disconnected!";

  }

  public String onServerStop(){

    return "Connection Lost";

  }




}
