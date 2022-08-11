package com.gregperlinli.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * Eight kinds of locks <br/>
 * 1. Standard access, print SMS or email first <br/>
 *      ====> sendSMS <br/>
 *      ====> sendEmail <br/>
 * 2. Stop for 3 seconds. In the SMS method, print the SMS or email first <br/>
 *      ====> sendSMS <br/>
 *      ====> sendEmail <br/>
 * 3. Add a common Hello method, whether to send text messages or hello first <br/>
 *      ====> Hello ... <br/>
 *      ====> sendSMS <br/>
 * 4. Now there are two mobile phones. Print text messages or emails first <br/>
 *      ====> sendEmail <br/>
 *      ====> sendSMS <br/>
 * 5. Two static synchronization methods, one mobile phone, print SMS or email first <br/>
 *      ====> sendSMS <br/>
 *      ====> sendEmail <br/>
 * 6. Two static synchronization methods, two mobile phones, print SMS or email first <br/>
 *      ====> sendSMS <br/>
 *      ====> sendEmail <br/>
 * 7. One static synchronization method, one ordinary synchronization method, one mobile phone, print SMS or email first <br/>
 *      ====> sendEmail <br/>
 *      ====> sendSMS <br/>
 * 8. One static synchronization method, one ordinary synchronization method, two mobile phones, print SMS or email first <br/>
 *      ====> sendEmail <br/>
 *      ====> sendSMS <br/>
 *
 * @author gregPerlinLi
 * @date 2022-08-11
 */
public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }, "a").start();
        // Pause the thread for a few seconds, ensure that thread a starts first
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e ) { e.printStackTrace(); }
        new Thread(() -> {
            phone.sendEmail();
            // phone.hello();
        }, "b").start();
    }
}

/**
 * Resource class
 */
class Phone {
    public static synchronized void sendEmail() {
        System.out.println("====> Send Email ...");
    }

    public synchronized void sendSms() {
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(3); } catch ( InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> Send SMS ...");
    }

    public void hello() {
        System.out.println("====> Hello ...");
    }
}