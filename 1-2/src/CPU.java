import java.util.*;

public class CPU {

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

        p.runTime--;
        printCPU(p);
//        通过键盘输入判断是否阻塞进程，判断运行时间是否结束
        while(true)
        {
            System.out.println("[Information] 请选择引起进程状态变化的原因:");
            System.out.println("a.时间片用完");
            System.out.println("b.等待输入输出设备");
            System.out.println("c.进程完成，进入结束队列");
            String changeReason = s.next();
            if(p.runTime<=0)
            {
                if(changeReason.equals("c"))
                {
                    p.enQueueE(endingQueue);
                    System.out.println("["+p.status+"] "+p.proName+"进入结束队列");
                    break;
                }
                else
                {
                    System.out.println(p.proName+"进程已结束，无法进行其他选择，请选择c");
                }
            }
            else
            {
                if(changeReason.equals("b"))
                {
                    p.enQueueB(blockQueue);
                    System.out.println("["+p.status+"] "+p.proName+"进入阻塞集合");
                    break;
                }
                else if(changeReason.equals("a"))
                {
                    p.enQueueR(readyQueue);
                    System.out.println("["+p.status+"] "+p.proName+"进入就绪队列");
                    break;
                }
                else
                {
                    System.out.println("[Information] 无该选项，请重新选择");
                }
            }
            //选择恢复已经结束阻塞的进程
        }
        while(true)
        {
            try
            {
                if(!blockQueue.isEmpty())
                {
                    List<String> stringList = new ArrayList<>();
                    System.out.println("[Information] 请选择进程名以及引起阻塞进程状态变化的原因:");
                    for(PCB pcb:blockQueue)
                    {
                        stringList.add(pcb.proName);
                        System.out.println(1+blockQueue.indexOf(pcb)+" "+pcb.proName+" ");
                    }
                    String processName = s.next();
                    PCB chosenPCB = null;
                    for(PCB pcb:blockQueue)
                    {
                        if(processName.equals(pcb.proName))
                        {
                            chosenPCB = pcb;
                        }
                    }
                    System.out.println("a.输入输出结束");
                    System.out.println("b.继续等待空闲输入输出设备");
                    String changeReason2 = s.next();
                    if(changeReason2.equals("a") && chosenPCB !=null)
                    {
                        blockQueue.remove(chosenPCB);
                        chosenPCB.enQueueR(readyQueue);
                        System.out.println("["+chosenPCB.status+"] "+chosenPCB.proName+"阻塞结束，进入就绪队列");
                    }
                }
            }catch (Exception e)
            {
                System.out.println("[Information]输入错误");
            }
            System.out.println("[In] 是否继续(y/n)");
            String isContinue = s.next();
            if(isContinue.equals("y")) {}
            else
            {
                break;
            }
            //打印队列与集合信息
        }
        printInformation();
    }



    public void printQueueInfo(List<PCB> Queue)
    {
        System.out.print("队列中的进程为：head->");
        if(!Queue.isEmpty())
        {
            for(int i=0;i<Queue.size();i++)
            {
                PCB p = Queue.get(i);
                System.out.print(p.proName+"->");
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
