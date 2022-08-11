package com.gregperlinli.sync;

import java.util.concurrent.TimeUnit;

/**
 * Eight kinds of locks <br/>
 * 1. Standard access, print SMS or email first <br/>
 *      ======sendSMS <br/>
 *      ======sendEmail <br/>
 * 2. Stop for 4 seconds. In the SMS method, print the SMS or email first <br/>
 *      ======sendSMS <br/>
 *      ======sendEmail <br/>
 * 3. Add a common Hello method, whether to send text messages or hello first <br/>
 *      ======getHello <br/>
 *      ======sendSMS <br/>
 * 4. Now there are two mobile phones. Print text messages or emails first <br/>
 *      ======sendEmail <br/>
 *      ======sendSMS <br/>
 * 5. Two static synchronization methods, one mobile phone, print SMS or email first <br/>
 *      ======sendSMS <br/>
 *      ======sendEmail <br/>
 * 6. Two static synchronization methods, two mobile phones, print SMS or email first <br/>
 *      ======sendSMS <br/>
 *      ======sendEmail <br/>
 * 7. One static synchronization method, one ordinary synchronization method, one mobile phone, print SMS or email first <br/>
 *      ======sendEmail <br/>
 *      ======sendSMS <br/>
 * 8. One static synchronization method, one ordinary synchronization method, two mobile phones, print SMS or email first <br/>
 *      ======sendEmail <br/>
 *      ======sendSMS <br/>
 * @author gregPerlinLi
 * @date 2022-07-27
 */
public class Lock8 {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }, "AA").start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                phone.sendEmail();
                // phone.getHello();
                // phone2.sendEmail();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }, "BB").start();
    }
}

class Phone {
    public static synchronized void sendSMS() throws Exception {
        // Stay for 4 seconds
        TimeUnit.SECONDS.sleep(4);
        System.out.println("======sendSMS");
    }
    public /*static*/ synchronized void sendEmail() throws Exception {
        System.out.println("======sendEmail");
    }
    public void getHello() {
        System.out.println("======getHello");
    }

}
