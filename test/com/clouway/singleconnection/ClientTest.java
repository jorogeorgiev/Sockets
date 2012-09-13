package com.clouway.singleconnection;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientTest {

  private final String SERVER_MESSAGE = "Hello";

  private final int HOST_PORT = 1910;

  private Server server;

  private Display mockedDisplay;

  private List<Display> displayList = Lists.newArrayList();


  @Before
  public void setUp() throws IOException, InterruptedException {

    mockedDisplay = mock(Display.class);

    displayList.add(mockedDisplay);

    startServer(SERVER_MESSAGE);

    String HOST_ADDRESS = "localhost";

    connectClientTo(HOST_ADDRESS, HOST_PORT , displayList);

  }


  @Test
  public void clientReceivesMessagesFromServer() throws IOException, InterruptedException {

    verify(mockedDisplay).show(SERVER_MESSAGE);

    server.stopServer();

  }

  @Test
  public void clientNotifiesOnServerStop() throws IOException, InterruptedException {

    server.stopServer();

    Thread.sleep(5000);

    verify(mockedDisplay, times(1)).show("Connection Lost");

  }

  private void startServer(String message) throws IOException {

    server = new Server(HOST_PORT, message, new ArrayList<Display>());

    server.startServer();

  }

  private void connectClientTo(String hostAddress, int port, List<Display> displayList) throws IOException, InterruptedException {

    Client client = new Client(displayList);

    client.connectTo(hostAddress, port);

  }

}
