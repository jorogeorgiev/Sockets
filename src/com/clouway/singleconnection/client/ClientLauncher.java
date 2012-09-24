package com.clouway.singleconnection.client;

import com.clouway.singleconnection.Application;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientLauncher {

  public static void main(String[] args) {




    List<Application> clientList = Lists.newArrayList();

    Client client = new Client("localhost", 1910);

    Client client2 = new Client("localhost" ,1920);

    clientList.add(client);

    clientList.add(client2);



    ClientDispatcher dispatcher = new ClientDispatcher(clientList);

    client.addclientDispatcher(dispatcher);

    client.addclientDispatcher(dispatcher);

  }

}
