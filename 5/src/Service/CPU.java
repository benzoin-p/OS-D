package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CPU {

    /*程序计数器
    * 存放生产者指令的入口地址
    * 存放消费者指令的入口地址
    * 生产者进程
    * 消费者进程
    * 两个信号量
    * 互斥访问的产品区
    * 生产产品指针
    * 消费产品指针
    */
    public static int PC;
    private final String[] PA = new String[5];
    private final String[] SA = new String[5];
    private PCB producer;
    private PCB consumer;
    public static Semaphore s1;
    public static Semaphore s2;
    public static Character[] products = new Character[10];
    public static int in;
    public static int out;

    static public List<PCB> readyList = new ArrayList<>();
    static public List<PCB> blockList = new ArrayList<>();
    static public List<PCB> endingList = new ArrayList<>();
    static public PCB runPCB;
    CPUInstruction cpuInstruction = new CPUInstruction();

    public void printCPU()
    {
        /*System.out.println();
        System.out.print("就绪队列为head->");
        for(PCB pcb:readyList)
        {
            System.out.print(pcb.getProcessName()+"->");
        }
        System.out.println("^");
        System.out.print("阻塞队列为head->");
        for(PCB pcb:blockList)
        {
            System.out.print(pcb.getProcessName()+"->");
        }
        System.out.println("^");*/
        System.out.print("[");
        for(int i=0;i<9;i++)
        {
            if(products[i] != null)
            {
                System.out.print(products[i]+",");
            }
            else
            {
                System.out.print(" ,");
            }

        }
        if(products[9] != null)
        {
            System.out.println(products[9]+"]");
        }
        else
        {
            System.out.println(" ]");
        }
    }

    //初始化流程
   public void initlizeProgram()
   {
       //初始化信号量s1，s2 s1:=10 s2:=0
       s1 = new Semaphore(10,"s1");
       s2 = new Semaphore(0,"s2");
       //生产者和消费者进程的PCB中状态为就绪，断点为0
       producer = new PCB("producer","ready",0);
       consumer = new PCB("consumer","ready",0);
       readyList.add(consumer);
       //将现行进程置位生产者进程 PC:=0
       PC = 0;
       createSA();
       createPA();
       runPCB = producer;
       dispatchProgram(runPCB,true);
   }

   //模拟处理器调度
   public void dispatchProgram(PCB pcb,boolean firstTime)
   {
       while(true) {
           //保护现场：PC->当前进程PCB的断点
           pcb.setBreakPoint(PC);
           //无就绪进程就结束方法
           if (readyList.isEmpty()) {
               System.out.println("已无就绪进程");
               return;
           } else {
               if(firstTime)
               {
                   runPCB = pcb;
                   //将进程状态改为运行态
                   pcb.setStatus("run");
                   //现行进程断点值->PC
                   PC = pcb.getBreakPoint();
                   implementProgram(runPCB);
                   firstTime = false;
               }
               else
               {
                   //随机选取就绪队列中的进程
                   Random random = new Random();
                   int rand = random.nextInt(2);
                   PCB chosenPcb;
                   if(readyList.size() == 2)
                   {
                       chosenPcb = readyList.remove(rand);
                   }
                   else
                   {
                       chosenPcb = readyList.remove(0);
                   }
                   runPCB = chosenPcb;
                   //将进程状态改为运行态
                   chosenPcb.setStatus("run");
                   //现行进程断点值->PC
                   PC = chosenPcb.getBreakPoint();
                   implementProgram(runPCB);
               }
           }
       }
   }

   //模拟处理器指令执行
   public void implementProgram(PCB pcb)
   {
       int i = PC;
       String args;
       //判断现行进程是否为生产者
       if(pcb.getProcessName().equals("producer"))
       {
           args = PA[i];
       }
       else
       {
           args = SA[i];
       }
       PC = i+1;
       //转向各模拟指令对应的过程
       List<Object> param = new ArrayList<>();
       param.add(pcb);
       param.add('p');
       //打印输出CPU情况
       System.out.println();
       System.out.println("****************************");
       System.out.print("[INFORMATION]此时产品列表的情况为:");
       printCPU();
       System.out.println("[INFORMATION]"+pcb.getProcessName() + "正在执行" + args);
       cpuInstruction.process(args,param);
       System.out.print("[INFORMATION]执行后产品列表的情况为:");
       printCPU();
       System.out.println("****************************");
       System.out.println();
       //判断生产者进程是否完成
       if(pcb.getProcessName().equals("producer") && PC == 0)
       {
           System.out.println("[IN]生产者运行结束？(y/n)");
           Scanner scan = new Scanner(System.in);
           String s = scan.next();
           if(s.equals("y"))
           {
                endingList.add(pcb);
                return;
           }
       }
       //置现行进程为就绪态
       if(!args.startsWith("p(") && !args.startsWith("v("))
       {
           pcb.setStatus("ready");
           readyList.add(pcb);
       }
       pcb.setBreakPoint(PC);
       producer.printPCB();
       consumer.printPCB();
   }

    public void createPA()
    {
        PA[0] = "produce";
        PA[1] = "p(s1)";
        PA[2] = "PUT";
        PA[3] = "v(s2)";
        PA[4] = "goto 0";
    }

    public void createSA()
    {
        SA[0] = "p(s2)";
        SA[1] = "GET";
        SA[2] = "v(s1)";
        SA[3] = "consume";
        SA[4] = "goto 0";
    }




}
