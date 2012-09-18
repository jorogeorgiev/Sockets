package com.clouway.singleconnection;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerController {

  public static void main(String[] args) throws IOException {

    List<Display> displayList = Lists.newArrayList();

    ServerUI serverUI = new ServerUI();

    displayList.add(serverUI);


    Server server = new Server(1910,displayList,"Connected on Server 1", "User Connected on Server 1");

    Server server2 = new Server(1920,displayList,"Connected on Server 2", "User Connected on Server 2");

    ArrayList<Server> serverList = new ArrayList<Server>();

    serverList.add(server);

  //  serverList.add(server2);


    serverUI.listenServer(serverList);

    server.startServer();

 //   server2.startServer();





  }
}
