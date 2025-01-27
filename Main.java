import java.util.Random;
import java.util.*;


public class Main{

    public static void InitQueue(FIFOQueue Q, PCB [] P)
    {
        //Initialize ready queue. Do not modify.
        for(int i = 0; i < P.length; i++)
        {
            Q.enQueue(P[i]);
        }
    }

    public static void InitQueue(BinaryHeap<PCB> Q, PCB [] P)
    {
        //Initialize ready queue. Do not modify.
        for(int i = 0; i < P.length; i++)
        {
            Q.insert(P[i]);
        }
    }
    public static void ViewProcesses(PCB [] P, int size)
    {
        //View all process objects. Do not modify.
        System.out.println("Process ID         Duration");
        System.out.println(" __________________________");
        for (int i = 0; i < size; i++)
        {
            System.out.println(P[i].getID() + "            " + P[i].getCycles());
        }
    }
    public static void InitializeProcesses(PCB [] P)
    {
        //Instantiates process objects. Do not modify.
        for(int i = 0; i < P.length; i++)
            P[i] = new PCB();
    }
    public static void GenerateProcesses(PCB [] P)
    {
        //Initializes PCB objects. Do not modify.
        int val;
        for(int i = 1; i <= 5; i++)
        {
            P[i-1].setID(i);
            val = 5 + (int)(Math.random() * 60);
            P[i-1].setCycles(val); //Generate random cycle time
            P[i-1].setArrivalTime(0);
        }


    }
    public static void FCFS(FIFOQueue Q) {
        //Your code here
        //You will implement the First Come First Served scheduling algorithm
        //You may assume all jobs arrive at time 0.
        PCB head ;

        while(!Q.isEmpty()){
            head = Q.deQueue();
            System.out.println("Running process " + head.getID() + ". " + head.getCycles()+ " cycles remain.");
        }
    }

    public static void SJN(BinaryHeap<PCB> Q)
    {
        //Your code here
        //You will implement the Shortest Job Next scheduling algorithm
        //You may assume all jobs arrive at time 0.
        PCB shortest;

        while(!Q.isEmpty()){
            shortest = Q.findMin();
            System.out.println("Running process " + shortest.getID() + ". " + shortest.getCycles()+ " cycles remain.");
            Q.deleteMin();
        }

    }

    public static void RoundRobin(FIFOQueue Q, int quantum)
    {
        //Simulate Round Robin scheduling on processes in P using the
        //given time quantum
        int ticks = 0;
        PCB p;

        while(!Q.isEmpty()) //While more processes remain in queue
        {
            p = new PCB();
            p = Q.deQueue(); //Fetch and remove process at head of queue
            System.out.println("Running process " + p.getID() + ". " + p.getCycles() + " cycles remain.");

            //"Running" process here
            for(int i = p.getCycles(); i > 0; i--)
            {

                ticks++;
                if(p.getCycles() > 0 && ticks <= quantum)
                {
                    //Use 1 cpu cycle
                    p.decrementCycles();    //Use one cycle
                    System.out.println(p.getCycles() + " cycles remain.");
                }
                if(ticks > quantum)
                {
                    //Time's up. Go to next process.
                    System.out.println("Process " + p.getID() + " preempted");
                    ticks = 0;
                    Q.enQueue(p); //Add preempted process to back of queue
                    break;

                }
                else if(p.getCycles() == 0)
                {
                    System.out.println("Process " + p.getID() + " has finished.");
                    ticks = 0;
                    break;
                }
            }

        }


    }

    public static void main(String []args)
    {

        int choice;
        PCB [] P = new PCB[5];
        FIFOQueue Q = new FIFOQueue();
        BinaryHeap<PCB> PQ = new BinaryHeap<PCB>();
        InitializeProcesses(P);
        GenerateProcesses(P);
        InitQueue(Q, P);
        InitQueue(PQ, P);
        Scanner sc = new Scanner(System.in);
        do
        {
            System.out.println("Process scheduling simulator. Make your choice:");
            System.out.println("1) View Processes");
            System.out.println("2) Run processes (FCFS)");
            System.out.println("3) Run processes (SJN)");
            System.out.println("4) Run processes (Round Robin)");
            System.out.println("5) Quit");
            choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    ViewProcesses(P, P.length);
                    break;
                case 2:
                   FCFS(Q);
                    break;
                case 3:
                    SJN(PQ);
                    break;
                case 4:
                    RoundRobin(Q, 5);
                    break;


            }


        }while(choice != 5);

    }

}
