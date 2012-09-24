package com.clouway.singleconnection.client;


import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Dispatcher;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Client implements Application {

  private Socket clientSocket;

  private final String hostAddress;
  private final int serverPort;

  private Scanner scanner;

  private Dispatcher clientDispatcher;

  public Client(String hostAddress, int serverPort) {

    this.hostAddress = hostAddress;

    this.serverPort = serverPort;


  }


  @Override
  public void start() throws IOException {


    clientSocket = new Socket(hostAddress, serverPort);

    new Thread(new Runnable() {

      @Override
      public void run() {


        try {
          scanner = new Scanner(clientSocket.getInputStream());
        } catch (IOException ignored) {

        }

        while (scanner.hasNextLine()) {

          clientDispatcher.dispatchMessage(scanner.nextLine(), Client.this);

        }

          clientDispatcher.dispatchMessage("Connection Lost", Client.this);

        try {
          stop();
        } catch (IOException ignored) {

        }




      }

    }).start();
  }


  public void addclientDispatcher(Dispatcher clientDispatcher){


    this.clientDispatcher = clientDispatcher;
  }

  @Override
  public void stop() throws IOException {

    clientSocket.close();

  }

}
