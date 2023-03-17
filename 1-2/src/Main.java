public class Main {

    public static void main(String[] args) {

        System.out.println("[Information] 进程转换实验，模拟开始！");
        CPU cpu = new CPU();
        System.out.println("[Information] 请依次输入三个进程的运行时间");
        for(int i=1;i<=3;i++)
        {
            PCB p;
            int runTime;
            String proName = "P"+i;
            try
            {
                System.out.print("[In] "+proName+"的运行时间:");
                runTime = cpu.s.nextInt();
                p = new PCB(runTime,proName);
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
        System.out.print("[Information] 就绪");
        cpu.printQueueInfo(cpu.readyQueue);

        while(!cpu.readyQueue.isEmpty())
        {
            cpu.runProcess(cpu.readyQueue);
        }

        System.out.println("[Information] 就绪队列为空，模拟结束！");
    }
}
