import java.util.Scanner;

public class Work
{
    Scanner scan = new Scanner(System.in);
    /*
    * 作业名
    * 申请的空间大小
    * 页表
    * */
    public String workName;
    public int memoryRequest;

    public PageTable pageTable = new PageTable();

    Work()
    {
        System.out.print("[In] 请输入作业名：");
        this.workName = scan.next();
    }


    public void requestMemory(BitMap bitMap)
    {
        System.out.print("[In] 请输入作业"+workName+"需要申请的空间大小（kb）:");
        int blockNum = scan.nextInt();
        if(blockNum%8 != 0)
        {
            setMemoryRequest(blockNum/8+1);
        }
        else
        {
            setMemoryRequest(blockNum/8);
        }
        if(memoryRequest > bitMap.freeBlockNum)
        {
            System.out.println("[Information] 空间不足，分配失败");
        }
        else
        {
            //调用位示图相关函数
            bitMap.DecreaseFreeBlock(memoryRequest,pageTable.blockNum);
        }

    }

    public void freeMemory(BitMap bitMap)
    {
        //调用位示图相关函数
        if(getMemoryRequest()==0)
        {
            System.out.println("[Information] 释放失败，无申请内存");
        }
        else
        {
            bitMap.addFreeBlock(pageTable.blockNum);
            setMemoryRequest(0);
            System.out.println("[Information] 释放成功");
        }
    }

    public void printWorkInfo(BitMap bitMap)
    {
        System.out.println();
        System.out.println("****************************");
        System.out.println("[Out] 作业名："+ getWorkName());
        System.out.println("[Out] 位示图空闲块数为："+ bitMap.freeBlockNum);
        bitMap.printBitMap();
        System.out.println("[Out] 该进程的页表为：");
        pageTable.printPageTable();
        System.out.println("****************************");
        System.out.println();
    }

    public void chooseFunc(BitMap bitMap)
    {
        System.out.print("[In] 请选择对"+getWorkName()+"的操作(r/f/n)");
        String s1 = scan.next();
        if(s1.equals("r"))
        {
            requestMemory(bitMap);
            printWorkInfo(bitMap);
        }
        else if (s1.equals("f"))
        {
            freeMemory(bitMap);
            printWorkInfo(bitMap);
        }
    }


    public String getWorkName() {
        return workName;
    }

    public int getMemoryRequest() {
        return memoryRequest;
    }

    public void setMemoryRequest(int memoryRequest) {
        this.memoryRequest = memoryRequest;
    }
}
