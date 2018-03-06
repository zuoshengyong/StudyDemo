package daily.cn.studydemo.algorithm;

import java.util.Arrays;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * desc:选择排序
 * </pre>
 */

public class SelectSort {

    public static void main(String[] args) {
        int [] arrays={50,10,90,30,70,40,80,60,20};
        System.out.println(Arrays.toString(arrays));
        System.out.println("------------------");
        selectSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    private static void selectSort(int[] arrays) {
        int length=arrays.length;
        for (int i=0;i<length;i++){
            for (int j=i+1;j<length;j++){
                if(arrays[j]<arrays[i]){
                    int tmp=arrays[i];
                    arrays[i]=arrays[j];
                    arrays[j]=tmp;
                }
            }
        }
    }
}
