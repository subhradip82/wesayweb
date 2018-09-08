package com.wesayweb.util;
import java.util.*;
public class HelperUtil {

	 public static double findMedian(Integer a[], int n)
	    {
	        Arrays.sort(a);
	        if (n % 2 != 0) {
	        return (double)a[n / 2];
	        }
	        return (double)(a[(n - 1) / 2] + a[n / 2]) / 2.0;
	    }
}
