//假设目录项均占一个块，文件目录项，含有读写指针信息
public class File {
    /*
    * 文件名
    * 保护字段（R,W,X,B）,其中B表示RW
    * 文件长度 （块的数量）
    * 文件存放地址（起始块号）
    * 系统打开文件标识
    * 读指针，指示文件内容第零个块（为目录）,为偏移量
    * 写指针，指示文件内容最后一个块，为偏移量
    * 所占磁盘块
    * 块号
    * 指向下一块的指针
    */
    private String fileName;
    private Character protectChar;
    private int fileSize;
    private int fileAddress;

    private boolean isOpen;

    private FileBlock read;

    private FileBlock write;

    private DiskBlock diskBlock;

    private int fileNum=0;

    private FileBlock next;


    public File(String fileName, Character protectChar, int fileSize, int fileAddress,boolean isOpen) {
        this.fileName = fileName;
        this.protectChar = protectChar;
        this.fileSize = fileSize;
        this.fileAddress = fileAddress;
        this.isOpen = false;
        this.next = initNext(fileSize-fileNum-1);
        diskBlock = new DiskBlock(fileAddress);
        DiskBlock.DiskBlockList[fileAddress] = 1;
    }

    //通过递归建立，这里count是fileSize-fileNum-1
    public FileBlock initNext(int count)
    {
        fileNum++;
        if(count <= 0)
        {
            return new FileBlock(fileName,fileAddress,fileNum,fileSize,null);
        }
        else
        {
            return new FileBlock(fileName,fileAddress,fileNum,fileSize,initNext(count-1));
        }
    }

    public void freeBlock(FileBlock next)
    {
        int num = diskBlock.getNum();
        DiskBlock.DiskBlockList[num] = 0;
        diskBlock = null;
        if(next != null)
        {
            next.freeBlock(next.getNext());
        }
        next = null;
    }

    //初始化文件读写指针
    public void initRW()
    {
        this.isOpen = true;
        this.read = next;
        this.write = next;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Character getProtectChar() {
        return protectChar;
    }

    public void setProtectChar(Character protectChar) {
        this.protectChar = protectChar;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(int fileAddress) {
        this.fileAddress = fileAddress;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public FileBlock getRead() {
        return read;
    }

    public void setRead(FileBlock read) {
        this.read = read;
    }

    public FileBlock getWrite() {
        return write;
    }

    public void setWrite(FileBlock write) {
        this.write = write;
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
