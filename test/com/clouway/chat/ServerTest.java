package com.clouway.chat;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerTest {

  private String receivedMessage;

  @Test
  public void serverAcceptMultiConnections() throws IOException, InterruptedException {

    Display mockedDisplay = mock(Display.class);

    List<Display> displayList = new ArrayList<Display>();

    displayList.add(mockedDisplay);

    Server  server = new Server(displayList);

    server.startServer(1910);

    Socket client  = new Socket("localhost",1910);

    Socket client2 = new Socket("localhost",1910);

    verify(mockedDisplay,times(2)).show("Connected");

    server.stopServer();

  }


  @Test
  public void serverNotifiesThereAreNoPreviousConnections() throws IOException, InterruptedException {

    Display mockedDisplay = mock(Display.class);

    List<Display> displayList = new ArrayList<Display>();

    displayList.add(mockedDisplay);

    Server  server = new Server(displayList);

    server.startServer(1910);

    final Socket client = new Socket("localhost" , 1910);

    new Thread(new Runnable() {

      @Override

      public void run() {

        try {
          Scanner scan = new Scanner(client.getInputStream());

          while(scan.hasNext()){

           receivedMessage=scan.nextLine();

          }

        } catch (IOException ignored) {}

      }
    }).start();

    Thread.sleep(5000);

    assertThat(receivedMessage, is("There are 0 connected users."));


  }



}
