package com.clouway.chat;

import com.google.common.collect.Lists;
import org.junit.Before;
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


  private List<Display> displayList = new ArrayList<Display>();

  private Display mockedDisplay;

  private String receivedMessage;

  private Server server;

  private List<Socket> clientList ;



  @Before
  public void setUp() throws IOException {

    clientList = Lists.newLinkedList();

    mockedDisplay = mock(Display.class);

    displayList.add(mockedDisplay);

    server = new Server(displayList);

    server.startServer(1910);

  }






  @Test
  public void serverAcceptMultiConnections() throws IOException, InterruptedException {

    initializeClients(2);

    verify(mockedDisplay,times(2)).show("Connected");

    server.stopServer();

  }



  @Test
  public void serverNotifiesThereAreNoPreviousConnections() throws IOException, InterruptedException {

    initializeClients(1);

    new Thread(new Runnable() {

      @Override

      public void run() {

        try {

          Scanner scan = new Scanner(clientList.get(0).getInputStream());

          while(scan.hasNext()){

           receivedMessage=scan.nextLine();

          }



        } catch (IOException ignored) {}

      }
    }).start();
    Thread.sleep(2000);
    assertThat(receivedMessage, is("There are 0 connected uses."));
  }


 @Test
 public void serverNotifiesClientAboutTotalCountConnectedCLients() throws IOException, InterruptedException {

   initializeClients(5);

   new Thread(new Runnable() {

     @Override

     public void run() {

       try {

         Scanner scan = new Scanner(clientList.get(4).getInputStream());

         while(scan.hasNext()){

           receivedMessage=scan.nextLine();

         }

       } catch (IOException ignored) {}

     }
   }).start();

   Thread.sleep(2000);

   assertThat(receivedMessage, is("There are 4 connected users."));

 }


  private void initializeClients(int clientNumber) throws IOException {

    for(int i=0;i<clientNumber;i++){

      Socket client  = new Socket("localhost",1910);

      clientList.add(client);

    }

  }









}
