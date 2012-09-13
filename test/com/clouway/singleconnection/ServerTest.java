package com.clouway.singleconnection;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerTest {

  private final int SERVER_PORT = 1910;
  
  private final String SERVER_ADDRESS = "localhost";

  private Server server;
  
  private Display mockedDisplay;

  private  List<Display> displayList;



  @Before
  public void setUp(){

    mockedDisplay = mock(Display.class);

    displayList = Lists.newArrayList();

    displayList.add(mockedDisplay);

  }


  @Test
  public void serverNotifiesOnConnectedClient() throws IOException {

    startServer(SERVER_PORT , "", displayList);

    new Socket(SERVER_ADDRESS,SERVER_PORT);

    verify(mockedDisplay).show("Client Connected");

    server.stopServer();

  }

  @Test
  public void serverSendsHelloAndDateToClient() throws IOException, InterruptedException {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    String serverMessage = "Hello "+ format.format(december(31,2012));

    startServer(SERVER_PORT, serverMessage,new ArrayList<Display>());

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

    Thread.sleep(1000);

    assertThat(receivedMessage.toString(), is(serverMessage));

    server.stopServer();

  }

  @Test
  public void serverClosesServerDisplaysOnStop() throws IOException {

    startServer(SERVER_PORT,"",displayList);

    server.stopServer();

    verify(mockedDisplay).closeDisplay();

  }

  private Date december(int day, int year) {

    Calendar calendarDate = Calendar.getInstance();

    calendarDate.set(year, Calendar.DECEMBER, day);

    return calendarDate.getTime();

  }

  private void startServer(int port , String message , List<Display> displayList) throws IOException {

    server = new Server(port,message,displayList);

    server.startServer();

  }

}
