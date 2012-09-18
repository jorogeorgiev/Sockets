package com.clouway.singleconnection;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ClientMain {

  public static void main(String[] args){

    Client client = new Client(new ArrayList<Display>());
    Client client2 = new Client(new ArrayList<Display>());
    try {
      client.connectTo("localhost",1910);

    } catch (InterruptedException e) {
      System.out.println("Process is interrupterd");
    } catch (IOException e) {
      System.out.println("Cannot connect to server 1");
    }


    try {
      client2.connectTo("localhost",1920);
    } catch (InterruptedException e) {
      System.out.println("Process is interrupterd");
    } catch (IOException e) {
      System.out.println("Cannot connect to server 2");
    }

  }
}
