package robots;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
in scenario 2 : Three scheduled Thread Pool is defined. schedulers are called with one
Runnable Method and one iterator that causes race conditions. resource1 defines here as BigDecimal as wanted
in this scenario ThreadSafety is  considered with synchronized variable and we receive regular messages with unique iterator
 **/
public class RobotMain2 {

    public static RobotFactory robotFactory=new RobotFactory();

    static Publisher publisher = new Publisher();

   public static BigDecimal resource1=new BigDecimal(1);


    private static final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService scheduler2= Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService scheduler3= Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        //deploying factory Method Design Pattern
        Observer robot1 = robotFactory.constructRobotObserver(RobotTypes.ROBOT1,publisher);
        Observer robot2 = robotFactory.constructRobotObserver(RobotTypes.SMALLROBOT,publisher);
        Observer robot3 =  robotFactory.constructRobotObserver(RobotTypes.BIGROBOT,publisher);

        //above really three robot observer has been subscribed to one available publisher

        scheduler1.scheduleAtFixedRate(new RunnableClass2(publisher), 0, 3, TimeUnit.SECONDS);
        scheduler2.scheduleAtFixedRate(new RunnableClass2(publisher), 0, 3, TimeUnit.SECONDS);
        scheduler3.scheduleAtFixedRate(new RunnableClass2(publisher), 0, 3, TimeUnit.SECONDS);

        //defining 3 thread pool each with one core pool to define seperated schedule with fixed rate timing
        //to mock race condition
    }

    }

class RunnableClass2 implements Runnable{
    public Publisher publisher;

    public RunnableClass2(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run() { //Race Condition on this method occurs

        synchronized(RobotMain2.resource1){
            System.out.println("command "+ RobotMain2.resource1);
            publisher.setState(String.valueOf(RobotMain2.resource1));
            RobotMain2.resource1= RobotMain2.resource1.add(new BigDecimal(1));

        }

    }

}
