package com.clouway.chat;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerTest {


  private List<Display> displayList = new ArrayList<Display>();

  private StringBuilder receivedMessage;

  private ClientMessages messages;

  private Server server;

  private List<Socket> clientList;


  @Before
  public void setUp() throws IOException, InterruptedException {

    clientList = Lists.newLinkedList();

    messages = new ClientMessages();

    receivedMessage = new StringBuilder();

    Display mockedDisplay = mock(Display.class);

    displayList.add(mockedDisplay);

    server = new Server(displayList,messages);

    server.startServer(1950);
  }


  @After
  public void tearDown() throws IOException {

    server.stopServer();

  }

  @Test
  public void serverNotifiesThereAreNoPreviousConnections() throws IOException, InterruptedException {

    initializeClients(1);

    assertClientIsNotifiedWith(1, messages.displayTotalClients(0));

  }

  @Test
  public void serverNotifiesClientAboutTotalConnectedClients() throws IOException, InterruptedException {

    initializeClients(5);

    assertClientIsNotifiedWith(5, messages.displayTotalClients(4));

  }



  @Test
  public void serverNotifiesParticipantsOnNewConnectedClient() throws IOException, InterruptedException {

    initializeClients(5);

    assertClientIsNotifiedWith(2, messages.onClientConnect(5));

    assertClientIsNotifiedWith(3, messages.onClientConnect(5));

    assertClientIsNotifiedWith(4, messages.onClientConnect(5));


  }

  @Test
  public void serverStopsClientStopsToo() throws IOException, InterruptedException {

   initializeClients(2);

   server.stopServer();

   assertClientIsNotifiedWith(2,messages.onServerStop());


  }




  private void initializeClients(int clientsNumber) throws IOException {

    for (int i = 1; i <= clientsNumber; i++) {

      Socket newClient = new Socket("localhost", 1950);

      clientList.add(newClient);

    }

  }

  private void assertClientIsNotifiedWith(final int clientNumber, String sentMessage) throws InterruptedException {

    new Thread(new Runnable() {

      @Override

      public void run() {

        try {

          Scanner scan = new Scanner(clientList.get(clientNumber-1).getInputStream());

          while (scan.hasNextLine()) {

            receivedMessage.delete(0,receivedMessage.length());

            receivedMessage.append(scan.nextLine());

          }
          receivedMessage.delete(0,receivedMessage.length());

          receivedMessage.append(messages.onServerStop());


        } catch (IOException ignored) {}

      }
    }).start();

    Thread.sleep(50);

    assertThat(receivedMessage.toString(), is(sentMessage));

  }


}
