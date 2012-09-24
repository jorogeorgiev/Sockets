package com.clouway.singleconnection;

import com.clouway.singleconnection.server.Server;
import com.clouway.singleconnection.server.ServerMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerTest {

  private ServerMessages messages;

  private final int SERVER_PORT = 1910;
  
  private final String SERVER_ADDRESS = "localhost";

  private Server server;

  private Dispatcher mockedDispatcher;



  @Before
  public void setUp() throws IOException {

    mockedDispatcher = mock(Dispatcher.class);

    messages = new ServerMessages();

    startServer(SERVER_PORT,mockedDispatcher ,messages);

  }




  @After
  public void tearDown() throws IOException {

     server.stop();


  }
  @Test
  public void serverNotifiesOnConnectedClient() throws IOException, InterruptedException {

    new Socket(SERVER_ADDRESS,SERVER_PORT);

    Thread.sleep(100);

    verify(mockedDispatcher).dispatchMessage(messages.clientConnected(), server);

  }




  @Test
  public void serverSendsHelloAndDateToClient() throws IOException, InterruptedException {

    final StringBuilder receivedMessage = new StringBuilder();

    final Socket client = new Socket(SERVER_ADDRESS,SERVER_PORT);

    new Thread(new Runnable() {
      @Override
      public void run() {

        int readByte;

        try {

          while((char)(readByte = client.getInputStream().read())!='\n'){

               receivedMessage.append((char)readByte);

          }

        } catch (IOException ignored) {

        }

      }
    }).start();

    Thread.sleep(100);

    assertThat(receivedMessage.toString(), is(messages.displayServerMessage()));

  }


  private void startServer(int port , Dispatcher serverDispatcher,ServerMessages messages) throws IOException {

    server = new Server(port,messages);

    server.addServeDispatcher(serverDispatcher);

    server.start();

  }

}
