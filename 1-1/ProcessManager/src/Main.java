public class Main
{
    public static void main(String[] args)
    {
        System.out.println("[Information] 进程调度实验，模拟开始！");
        CPU cpu = new CPU();
        System.out.println("[Information] 请依次输入五个进程的优先数以及运行时间");
        for(int i=1;i<=5;i++)
        {
            PCB p;
            int priority;
            int runTime;
            String proName = "P"+i;
            boolean isBlock;
            int blockStartTime;
            int blockTime;
            try
            {
                System.out.print("[In] "+proName+"的优先数:");
                priority = cpu.s.nextInt();
                System.out.print("[In] "+proName+"的运行时间:");
                runTime = cpu.s.nextInt();
                System.out.print("[In] 是否阻塞？(y/n)");
                try
                {
                    String b = cpu.s.next();
                    isBlock = b.equals("y");
                }
                catch (Exception e)
                {
                    isBlock = false;
                }
                if(isBlock)
                {
                    System.out.print("[In] "+proName+"开始阻塞的周期:");
                    blockStartTime = cpu.s.nextInt();
                    System.out.print("[In] "+proName+"阻塞的周期:");
                    blockTime = cpu.s.nextInt();
                    p = new PCB(priority,runTime,proName,blockStartTime,blockTime);
                }
                else
                {
                    p = new PCB(priority,runTime,proName);
                }
            }
            catch(Exception e)
            {
                System.out.print("[WARNING] 输入错误");
                return;
            }
            System.out.println("------------------------");
            System.out.println("["+p.status+"] 进程"+proName+"创建成功");
            p.enQueueR(cpu.readyQueue);
            System.out.println("["+p.status+"] 进程"+proName+"进入就绪队列");
            System.out.println("------------------------");
            System.out.println();
        }
//        对5个进程按优先数大小进行排列
//        用指针指出下一个PCB的首地址
        cpu.adjustQueue(cpu.readyQueue);
        System.out.print("[Information] 就绪");
        cpu.printQueueInfo(cpu.readyQueue);

        while(!cpu.readyQueue.isEmpty())
        {
            cpu.runProcess(cpu.readyQueue);
        }

        System.out.println("[Information] 就绪队列为空，模拟结束！");

    }
}
