package com.clouway.singleconnection.server;

import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Dispatcher;
import com.clouway.singleconnection.Display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author georgi.hristov@clouway.com
 */
public class ServerDispatcher implements Dispatcher {

  private List<Application> serverList;

  private Map<Application, Display> serverDisplay = new HashMap<Application, Display>();

  public ServerDispatcher(List<Application> serverList){

    this.serverList = serverList;

    bingDisplayToServer();

  }


  private void bingDisplayToServer(){

    for(Application server : serverList){

      Display display = new ServerDisplay(server);

      serverDisplay.put(server, display);

    }

  }


  public void dispatchMessage(String message,Application user) {

    serverDisplay.get(user).show(message);

  }

}
