package com.clouway.chat;

import com.clouway.singleconnection.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerMain {

  public static void main(String[] args) throws IOException, InterruptedException {

   ClientMessages messages = new ClientMessages();

   Server server = new Server(new ArrayList<Display>(),messages);

   server.startServer(1930);



  }
}
