package daily.cn.studydemo.algorithm;

import java.util.Arrays;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * desc:归并排序
 * </pre>
 */

public class MergeSort {

    public static void main(String[] args) {
        int [] arrays={50,10,90,30,70,40,80,60,20};
        System.out.println(Arrays.toString(arrays));
        System.out.println("------------------");
        mergeSort(arrays,0,arrays.length-1);
        System.out.println(Arrays.toString(arrays));
    }
    public static void mergeSort(int[] arrays,int low,int high){
        if(low<high){
            int mid=low+(high-low)/2;
            //左边
            mergeSort(arrays,low,mid);
            //右边
            mergeSort(arrays,mid+1,high);
            //合并
            merge(arrays,low,mid,high);
        }


    }

    /**
     * 合并两个已排序好的数组
     */
    public static void merge(int[] arrays,int low,int mid,int high){
        int[] temp=new int[high-low+1];//临时数组
        int i=low;//左指针
        int j=mid+1;//右指针
        int k=0;
        //把较小的数先移动到新数组中
        while (i<=mid&&j<=high){
            if(arrays[i]<arrays[j]){
                temp[k++]=arrays[i++];
            }else {
                temp[k++]=arrays[j++];
            }
        }
        //把左边剩余的数移入数组中
        while (i<=mid){
            temp[k++]=arrays[i++];
        }
        //把右边剩余的数移入数组中
        while (j<=high){
            temp[k++]=arrays[j++];
        }
        //把新数组中的数覆盖原数组
        for (int k2=0;k2<temp.length;k2++){
            arrays[k2+low]=temp[k2];
        }
    }
}
