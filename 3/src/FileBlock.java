//文件块，与磁盘块一一对应
public class FileBlock {

    private String fileName;

    private int fileAddress;

    private DiskBlock diskBlock;

    private int fileNum;

    private int fileSize;

    private FileBlock next;

    public FileBlock(String fileName, int fileAddress, int fileNum,int fileSize,FileBlock next) {
        this.fileName = fileName;
        this.fileAddress = fileAddress;
        this.fileNum = fileNum;
        this.fileSize = fileSize;
        if (fileNum >= fileSize) {
            this.next = null;
        } else {
            this.next = next;
        }
        int t = fileAddress + fileNum;
        while (true) {
            if (DiskBlock.DiskBlockList[t] != 1) {
                DiskBlock.DiskBlockList[t] = 1;
                this.diskBlock = new DiskBlock(t);
                break;
            } else {
                t = (t + 1) % 64;
            }
        }
    }

    public void freeBlock(FileBlock next)
    {
        int num = diskBlock.getNum();
        DiskBlock.DiskBlockList[num] = 0;
        diskBlock = null;
        if(next != null)
        {
            next.freeBlock(next.next);
        }
        next = null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(int fileAddress) {
        this.fileAddress = fileAddress;
    }

    public DiskBlock getDiskBlock() {
        return diskBlock;
    }

    public void setDiskBlock(DiskBlock diskBlock) {
        this.diskBlock = diskBlock;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public FileBlock getNext() {
        return next;
    }

    public void setNext(FileBlock next) {
        this.next = next;
    }
}
