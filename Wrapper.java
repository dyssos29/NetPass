public class Wrapper
{
    public static void main(String[] args)
    {
        Thread first = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(new String[] {"5356"});
                //System.out.println("First thread");
            }
        });

        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
//                if (first != null && first.isAlive()) {
//                    try {
//                        first.join();
//                    } catch (InterruptedException e) {}
//                }
                Client.main(new String[] {"10.10.12.123","5356"});
                //System.out.println("Second thread");
            }
        });

        first.start();
        second.start();
    }
}
