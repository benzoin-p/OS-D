#include <sys/types.h>
#include <sys/msg.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <string.h>
#include <signal.h>
 
 
 
struct msg_buf
 
    {
 
        int mtype;
 
        char data[255];
 
    };
 
 
 
int main()
 
{
 
        key_t key;
 
        pid_t pid;
 
        int msgid;
 
        int ret;
 
        struct msg_buf msgbuf;
 
        struct msg_buf msgbuf1;
 
        struct msg_buf msgbuf2;
 
        struct msg_buf msgbuf3;
 
        int i = 0;
 
 
 
        key=ftok(".",'a');
 
        printf("key =[%x]\n",key);
 
        msgid=msgget(key,IPC_CREAT|0666); /*通过文件对应*/
 
 
 
        if(msgid==-1)
 
        {
 
                printf("create error\n");
 
                return -1;
 
        }
 
         if((pid = fork()) < 0)
 
         {
 
             perror("fork error");
 
             exit(0);
 
         }
 
 
 
         if( pid > 0)
 
       {
 
            msgbuf1.mtype = 1;
 
            strcpy(msgbuf1.data,"test haha1");
 
            msgbuf2.mtype = 2;
 
            strcpy(msgbuf2.data,"test haha2");
 
            msgbuf3.mtype = 3;
 
            strcpy(msgbuf3.data,"test haha3");
 
 
 
            ret=msgsnd(msgid,&msgbuf1,sizeof(msgbuf1.data),IPC_NOWAIT);
 
            if(ret==-1)
 
            {
 
                printf("send message err\n");
 
                return -1;
 
            }
 
            //sleep(3);
 
            ret=msgsnd(msgid,&msgbuf2,sizeof(msgbuf2.data),IPC_NOWAIT);
 
            if(ret==-1)
 
            {
 
                printf("send message err\n");
 
                return -1;
 
            }
 
           // sleep(3);
 
            ret=msgsnd(msgid,&msgbuf3,sizeof(msgbuf3.data),IPC_NOWAIT);
 
            if(ret==-1)
 
            {
 
                printf("send message err\n");
 
                return -1;
 
            }
 
           // sleep(3);
 
           // kill(pid,SIGSTOP);
 
            waitpid(pid,NULL,0);
 
            exit(0);
 
       }
 
 
 
         if( pid == 0)
 
         {
 
            while(i < 3)
 
            {
 
                i++;
 
                memset(&msgbuf,0,sizeof(msgbuf));
 
                ret=msgrcv(msgid,&msgbuf,sizeof(msgbuf.data),i,0);
 
                if(ret==-1)
 
                {
 
                    printf("recv message err\n");
 
                    return -1;
 
                }
 
                printf("recv msg =[%s]\n",msgbuf.data);
 
                sleep(1);
 
            }
 
               exit(0);
 
         }
 
     return 0;
 
}
