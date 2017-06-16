/*
 *Zane Gray
 *Homework 3
 *Advanced Java
 *9/5/2015
 *
*/
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
public class SortAndMerge{ 
	public static void main(String [] args) 
	throws IOException,FileNotFoundException
	{
		Scanner input = new Scanner(System.in);
		/*prompt the user for the name of the first file,
		 *ensuring it is valid,
		 *and create a scanner to read it
		 */
		 
		System.out.print("Welcome to the Sort and Merge program!\n"+
			"Please enter a file name, containing a list of numbers:");
		boolean isInvalid;
		File file = new File("");
		int [] firstArray = new int[0];
		int [] secondArray = new int[0];
		do{
			isInvalid = false;
			try{
				file = new File(input.next());
				firstArray = mySort(fileToArray(file));
			}catch(FileNotFoundException e){
				isInvalid = true;
			}
			if (isInvalid){
				System.out.print("Invalid file\n"+
					"Please enter a file name, containing a list of numbers:"
				);
			}
		}while(isInvalid);
		
		System.out.println("Please enter a second file name "+
			"in the same format:");
		
		do{
			isInvalid = false;
			try{
				file = new File(input.next());
				secondArray = mySort(fileToArray(file));
			}catch(FileNotFoundException e){
				isInvalid = true;
			}
			if (isInvalid){
				System.out.print("Invalid file\n"+
					"Please enter a file name, containing a list of numbers:"
				);
			}
		}while(isInvalid);
		
		int [] result = merge (firstArray,secondArray);
		
		System.out.println("File preview:");
		displayList(result);
		System.out.println("Please enter a file name to be written:");
		
		String temp = input.next();
		file = new File(temp);
		file.createNewFile();
		PrintWriter out = new PrintWriter(file);
		
		out.println(result.length);
		for(int count = 0; count < result.length/5; count++){
			for(int innerCount = 0; innerCount < 5; innerCount++){
				out.print(result[count*5+innerCount]+"\t");
			}
			out.println();
		}
		out.close();
	}
	
	public static int[] fileToArray(File file) throws FileNotFoundException{
			
		Scanner fileScanner = new Scanner(file);
		int [] result = new int[fileScanner.nextInt()];
		int pos = 0;
		while(fileScanner.hasNextInt()){
			result[pos++]=fileScanner.nextInt();
		}
		return result;
	}
	
	public static int[] merge(int[]arr1,int[]arr2){
		int[] result = new int[arr1.length+arr2.length];
		int index1=0;
		int index2=0;
		int i = 0;
		
		while(index1<arr1.length&&index2<arr2.length){
			if(arr1[index1]>arr2[index2]){
				result[i++]=arr1[index1++];
			}
			else
				result[i++]=arr2[index2++];
		}
		while(index1 < arr1.length){
			result[i++]=arr1[index1++];
		}
		while(index2 < arr2.length){
			result[i++]=arr2[index2++];
		}
		return result;
	}
	public static int[] mySort(int[]arr){
		//check for base case
		if(arr.length==0){
			return arr;
		}
		
		//set indexes
		int i = 0;
		int j = arr.length - 1;
		int pivotIndex;

		
		//choose the pivot based on the median of three rule
		if((arr[i]>arr[j]&&arr[i]<arr[j/2])||
					(arr[i]>arr[j/2]&&arr[i]<arr[j])){
			pivotIndex = i;
		}
		else if((arr[j]>arr[i]&&arr[j]<arr[j/2])||
					(arr[j]<arr[i]&&arr[j]>arr[j/2])){
			pivotIndex =j;
		}
		else
			pivotIndex = j/2;
			
		//put the pivot out of the way
		int temp = arr[j];
		arr[j] = arr[pivotIndex];
		arr[pivotIndex] = temp;
		pivotIndex = j--;
		
		//move everything to the proper side
		while(i<j){
			if(arr[i]>=arr[pivotIndex]&&arr[pivotIndex]>=arr[j]){
				temp = arr[i];
				arr[i++] = arr[j];
				arr[j--] = temp;
			}
			else if(arr[i]<arr[pivotIndex])
				i++;
			else if(arr[j]>arr[pivotIndex])
				j--;
		}
		//put the pivot in its final position
		temp = arr[pivotIndex];
		arr[pivotIndex] = arr[i];
		arr[i] = temp;
		pivotIndex = i;
		
		//partition and make recursive call
		int [] lower = mySort(Arrays.copyOfRange(arr,0,pivotIndex));
		int [] upper = mySort(Arrays.copyOfRange(arr,pivotIndex+1,arr.length));
		
		//merge the sorted upper and lower partitions with the pivot index
		int [] result = new int [arr.length];
		result = merge(merge(lower,new int [] {arr[pivotIndex]}),upper);
		return result;
	}
	public static void displayList(int [] arr){
		System.out.println(arr.length);
		if(arr.length<=100){
			for(int outerCount = 0; outerCount<10; outerCount++){
				for(int innerCount = 0; innerCount<10; innerCount++){
					if(arr.length>outerCount*10+innerCount){
						System.out.print(arr[outerCount*10+innerCount]+"\t");
					}
				}
				System.out.println();
			}
		}else{
			for(int outerCount = 0; outerCount<5; outerCount++){
				for(int innerCount = 0; innerCount<10; innerCount++){
					System.out.print(arr[outerCount*10+innerCount]+"\t");
				}
				System.out.println();
			}
			System.out.println("...");
			for(int outerCount = 0; outerCount<5; outerCount++){
				for(int innerCount = 0; innerCount<10; innerCount++){
					System.out.print(arr[arr.length-50+outerCount*10+innerCount]
								+"\t");
				}
				System.out.println();
			}
		}
	}
}