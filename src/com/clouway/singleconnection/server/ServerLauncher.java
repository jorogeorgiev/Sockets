package com.clouway.singleconnection.server;

import com.clouway.singleconnection.Application;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerLauncher {

  public static void main(String[] args) throws IOException {

    ServerMessages messages = new ServerMessages();

    List<Application> serverList = Lists.newArrayList();

    Server server = new Server(1910,messages);

    Server server2 = new Server(1920,messages);

    serverList.add(server);

    serverList.add(server2);

    ServerDispatcher serverDispatcher = new ServerDispatcher(serverList);

    server.addServeDispatcher(serverDispatcher);

    server.addServeDispatcher(serverDispatcher);

  }
}
