package com.clouway.singleconnection;

import com.clouway.singleconnection.server.Server;
import com.clouway.singleconnection.server.ServerMessages;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientTest {

  private ServerMessages messages;

  private final int HOST_PORT = 1910;

  private Server server;

  private Display mockedDisplay;

  private List<Display> displayList = Lists.newArrayList();


  @Before
  public void setUp() throws IOException, InterruptedException {
    messages = new ServerMessages();

    mockedDisplay = mock(Display.class);

    displayList.add(mockedDisplay);

    startServer();

    String HOST_ADDRESS = "localhost";

    connectClientTo(HOST_ADDRESS, HOST_PORT , displayList);

  }

  @After
  public void tearDown() throws IOException {

    server.stop();

  }



  @Test
  public void clientReceivesMessagesFromServer() throws IOException, InterruptedException {

    verify(mockedDisplay).show(messages.displayServerMessage());

  }

  @Test
  public void clientNotifiesOnServerStop() throws IOException, InterruptedException {

    server.stop();

    Thread.sleep(500);

    verify(mockedDisplay, times(1)).show("Connection Lost");

  }

  private void startServer() throws IOException {

   // server = new Server(HOST_PORT,new ArrayList<Display>(),messages);

    server.start();

  }

  private void connectClientTo(String hostAddress, int port, List<Display> displayList) throws IOException, InterruptedException {

//    Client client = new Client(hostAddress, port,displayList);

//    client.start();

  }

}
