package com.clouway.singleconnection.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class ServerMessages {



  public String displayServerMessage(){

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    Calendar calendar = Calendar.getInstance();

    calendar.set(2012, Calendar.DECEMBER, 21);

    return ("Hello " + format.format(calendar.getTime()));


  }


  public String clientConnected(){

    return "Client has just connected";

  }

  public String serverStarted(){

    return "Server is started";

  }


  public String serverStopped(){

    return "Server is Stopped";

  }

  public String errorAlreadyStarted(){

    return "Server is already started";
  }


  public String errorAlreadyStopped(){


    return "Server is already stopped";

  }

  public String errorNullPointer(){

    return "There is nothing that can be done";

  }

}
