package com.clouway.singleconnection.server;

import com.clouway.singleconnection.Application;
import com.clouway.singleconnection.Dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Server implements Application {

  private Dispatcher serverDispatcher;

  private ServerMessages messages;

  private int portNumber;

  private ServerSocket serverSocket;

  private Socket clientSocket;

  public Server(int portNumber, ServerMessages messages) {

    this.portNumber = portNumber;

    this.messages = messages;

  }

  @Override
  public void start() throws IOException {

    serverSocket = new ServerSocket(portNumber);


    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (true) {
            clientSocket = serverSocket.accept();

            serverDispatcher.dispatchMessage(messages.clientConnected(), Server.this);

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            writer.println(messages.displayServerMessage());

            writer.flush();
          }

        } catch (IOException ignored) {
        }

      }
    }).start();

  }

  @Override
  public void stop() throws IOException {

    serverSocket.close();

  }

  public void addServeDispatcher(Dispatcher serverDispatcher) {

    this.serverDispatcher = serverDispatcher;

  }


}
