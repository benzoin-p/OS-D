import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CPU
{
    public List<PCB> readyQueue = new LinkedList<>();
    public List<PCB> endingQueue = new LinkedList<>();
    public List<PCB> blockQueue = new LinkedList<>();
    public int CPUTime = 0;

    Scanner s = new Scanner(System.in);
    public void runProcess(List<PCB> readyQueue)
    {
        CPUTime++;
//        进程状态变化并打印pcb内容
        PCB p = readyQueue.get(0);
        readyQueue.remove(0);
        p.setStatus("Run");

//        判断是否到达需要阻塞的时间周期
        if(p.blockStartTime!=-1 && p.totalRunTime-p.runTime==p.blockStartTime-1)
        {
            p.blockStartTime = -1;
            //不计算本周期
            p.blockTime++;
            p.enQueueB(blockQueue);
            printBlockInfo(p);
        }
        else
        {
            if(p.priority>0)
            {
                p.priority--;
            }
            p.runTime--;
            printCPU(p);
//        通过键盘输入判断是否阻塞进程，判断运行时间是否结束
            if(p.runTime>0)
            {
                p.enQueueR(readyQueue);
                changeQueuePoint(readyQueue,p);
                System.out.println("["+p.status+"] "+p.proName+"进入就绪队列");
            }
            else
            {
                p.enQueueE(endingQueue);
                changeQueuePoint(endingQueue,p);
                System.out.println("["+p.status+"] "+p.proName+"进入结束队列");
            }
        }
//        恢复已经结束阻塞的进程
        for (PCB pcb : blockQueue) {
            pcb.blockTime--;
            if(pcb.blockTime == 0)
            {
                blockQueue.remove(pcb);
                pcb.enQueueR(readyQueue);
                changeQueuePoint(readyQueue,pcb);
                System.out.println("["+pcb.status+"] "+pcb.proName+"阻塞结束，进入就绪队列");
            }
        }
//        每次对队列进行更新，保证优先度最高的先运行
        adjustQueue(readyQueue);
//        打印队列与集合信息
        printInformation();
    }

    public void printQueueInfo(List<PCB> Queue)
    {
        System.out.print("队列中的进程为：head->");
        if(!Queue.isEmpty())
        {
            PCB p = Queue.get(0);
            while(p!=null)
            {
                System.out.print(p.proName+"->");
                p=p.next;
            }
        }
        System.out.println("^");
    }

    public void printSetInfo(List<PCB> Queue)
    {
        System.out.print("集合中的进程为：{");
        int i=1;
        for (PCB pcb : Queue) {
            if(i == Queue.size())
            {
                System.out.print(pcb.proName);
            }
            else
            {
                System.out.print(pcb.proName+",");
                i++;
            }
        }
        System.out.println("}");
    }

    public void changeQueuePoint(List<PCB> Queue,PCB p)
    {
        p.setNext(null);
        if(Queue.size()>1)
        {
            Queue.get(Queue.indexOf(p)-1).next=p;
        }
    }

    public void adjustQueue(List<PCB> readyQueue)
    {
        readyQueue.sort(Comparator.comparingInt(PCB::getPriority).reversed());
//        用指针指出下一个PCB的首地址
        if(readyQueue.size()>1)
        {
            int j=0;
            for(;j<readyQueue.size()-1;j++)
            {
                readyQueue.get(j).next=readyQueue.get(j+1);
            }
            readyQueue.get(j).next=null;
        }
    }

    public void printInformation()
    {
        System.out.println();
        System.out.print("[Information] 就绪");
        printQueueInfo(readyQueue);
        System.out.print("[Information] 阻塞");
        printSetInfo(blockQueue);
        System.out.print("[Information] 结束");
        printQueueInfo(endingQueue);
        System.out.println("------------------------");
        System.out.println();
    }

    public void printCPU(PCB p)
    {
        System.out.println("------------------------");
        System.out.println("[Information CPU Time:"+CPUTime+"]");
        System.out.print("["+p.status+"]正在运行的进程为："+p.proName);
        System.out.print(" 优先数为："+p.priority);
        System.out.println(" 运行时间为："+p.runTime);
        System.out.println();
    }

    public void printBlockInfo(PCB p)
    {
        System.out.println("------------------------");
        System.out.println("[Information CPU Time:"+CPUTime+"]");
        System.out.println("["+p.status+"] "+p.proName+"进入阻塞集合");
        System.out.println();
    }


}
