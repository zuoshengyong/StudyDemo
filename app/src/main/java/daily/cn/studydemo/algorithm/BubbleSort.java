package daily.cn.studydemo.algorithm;

import java.util.Arrays;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * desc:冒泡排序
 * </pre>
 */

public class BubbleSort {

    public static void main(String[] args) {
        int [] arrays={50,10,90,30,70,40,80,60,20};
        System.out.println(Arrays.toString(arrays));
        System.out.println("------------------");
        bubbleSort(arrays);
       System.out.println(Arrays.toString(arrays));
    }

    private static void bubbleSort(int[] arrays) {
        int length=arrays.length;
        for (int i=0;i<length;i++){
            for (int j=length-1;j>0;j--){
                if(arrays[j]<arrays[j-1]){
                    int tmp=arrays[j];
                    arrays[j]=arrays[j-1];
                    arrays[j-1]=tmp;
                }
            }
        }
    }
}
