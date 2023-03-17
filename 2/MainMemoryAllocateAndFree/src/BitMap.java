import java.util.ArrayList;

public class BitMap
{
    /*
    * 存储位示图的二维矩阵
    * 空闲块数,总共有64块，每块大小为8kb
    * 空闲块头指针
    * */
    public int[][] data = new int[8][8];
    public int freeBlockNum = 64;
    public int freeBlockPoint = 0;



    public void printBitMap()
    {
        System.out.println("┌─────────────────┐");
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(j==0)
                {
                    System.out.print("│ "+data[i][j]+" ");
                }
                else if (j==7)
                {
                    System.out.print(data[i][j]+" │");
                }
                else
                {
                    System.out.print(data[i][j]+" ");
                }
            }
            System.out.println();
        }
        System.out.println("└─────────────────┘");
    }

    public void DecreaseFreeBlock(int requestMemory, ArrayList<Integer> list)
    {
        while(requestMemory>0)
        {
            int t = getFreeBlockPoint();
            int i = getFreeBlockPointI();
            int j = getFreeBlockPointJ();
            if(data[i][j]==0)
            {
                data[i][j]=1;
                list.add(t);
                requestMemory--;
                freeBlockNum--;
            }
            setFreeBlockPoint((t+1)%64);
        }
    }

    public void addFreeBlock(ArrayList<Integer> list)
    {
        int p = 0;
        while(!list.isEmpty())
        {
            int t = list.get(p);
            int i = t/8;
            int j = t%8;
            if(data[i][j]==1)
            {
                data[i][j]=0;
                list.remove(p);
                freeBlockNum++;
            }
        }
    }




    public void setFreeBlockPoint(int freeBlockPoint) {
        this.freeBlockPoint = freeBlockPoint;
    }

    public int getFreeBlockPoint() {
        return freeBlockPoint;
    }

    public int getFreeBlockPointI() {
        return freeBlockPoint/8;
    }

    public int getFreeBlockPointJ() {
        return freeBlockPoint%8;
    }
}
