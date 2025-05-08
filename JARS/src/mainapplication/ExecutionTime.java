
package mainapplication;
import java.util.concurrent.TimeUnit;

public class ExecutionTime {
  public static void main(String[] args) throws InterruptedException {

        long lStartTime = System.currentTimeMillis();

        calculation();

        long lEndTime = System.currentTimeMillis();

        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output);

    }

    public static void calculation() throws InterruptedException {

        //Sleep 2 seconds
        TimeUnit.SECONDS.sleep(2);
            
    }
}
