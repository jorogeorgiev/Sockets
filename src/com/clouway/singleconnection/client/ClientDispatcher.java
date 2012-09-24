package com.clouway.singleconnection.client;

import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Dispatcher;
import com.clouway.singleconnection.Display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author georgi.hristov@clouway.com
 */
public class ClientDispatcher implements Dispatcher {

  private List<Application> clientList;

  private Map<Application, Display> clientDisplay = new HashMap<Application, Display>();


  public ClientDispatcher(List<Application> clientList){

    this.clientList = clientList;

    bingDisplayToServer();

  }

  private void bingDisplayToServer(){

    for(Application client : clientList){

      Display display = new ClientDisplay(client);

     clientDisplay.put(client, display);


    }

  }

  public void dispatchMessage(String message,Application user) {

    clientDisplay.get(user).show(message);

  }

}
