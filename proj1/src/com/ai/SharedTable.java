package com.ai;

/**
 * Created by lucas on 18/03/15.
 */
public class SharedTable{
    int[] hash;
    int timeA=0;
    int timeB=0;
    public SharedTable(){
        this.hash = new int[3];
        hash[1]=1;
        hash[2]=2;
    }

    public int[] Compare(int id,int code){
        int[] ans = new int[2];
        if (timeA>timeB+1 || timeB>timeA){
            ans[0]=0;
            ans[1]=1;
            System.out.println("---wait");
            return ans;
        }
        hash[0] = id;
        System.out.println("id ="+id);
        System.out.println(code);
        if (id==0){
            hash[1] = code;
            timeA++;
        }
        if (id==1){
            timeB++;
            hash[2] = code;
        }
        if (hash[1]==hash[2]){
            ans[0]=1;
            ans[1]=0;
            System.out.println("---ahoi");
            return ans;
        }
        else{
            ans[0]=0;
            ans[1]=0;
            System.out.println("---different");
            return ans;
        }
    }
}
