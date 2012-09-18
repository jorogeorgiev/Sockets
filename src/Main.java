import com.google.common.collect.Lists;

import java.util.LinkedList;

/**
 * Created by Georgi Georgiev , Clouway Ltd.
 * email: georgi.hristov@clouway.com
 */
public class Main {
  public static void main(String[] args) {


    final LinkedList<Integer> integerList = Lists.newLinkedList();

    new Thread(new Runnable() {
      public Integer index = 1;

      @Override
      public void run() {

        synchronized (integerList) {


          while (!Thread.currentThread().isInterrupted()) {


            try {

              integerList.add(index);

              index++;

              System.out.println("Index is: " + index);

              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

          }
        }
      }
    }).start();


    new Thread(new Runnable() {


      @Override
      public void run() {
        synchronized (integerList) {


          while (!Thread.currentThread().isInterrupted()) {

            for (Integer listElement : integerList) {


              try {

                System.out.println("Integer element is: " + listElement);
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              }


            }

          }

        }


      }

    }).start();


  }


}
