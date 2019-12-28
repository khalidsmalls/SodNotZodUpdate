/*AUTHOR: Khalid Smalls
 *PURPOSE: reads inventory from file, sorts by itemID, loads arrays, tracks sales and inventory 
 *CREATEDATE: 12/18/2019*/



package sodNotZod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Inventory 
{
	//declare and initialize class constants
	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.25, 0.15, 0.0};
	private final String[] PRIZE_NAMES = {"Fender P-Bass", "Fender Jaguar", "Roland Jupiter 50"};
	private final int NOT_FOUND = -1;
	private final int RESET_VALUE = 0;
	private final int MAX_ITEMS = 25;

	//declare and initialize non-constant class attributes
	private int[] itemIDs = new int[MAX_ITEMS]; 
	private String[] itemNames = new String[MAX_ITEMS];
	private double[] itemPrices = new double[MAX_ITEMS];
	private int[] inStockCounts = new int[MAX_ITEMS];
	private int[] orderQuantity = new int[MAX_ITEMS];
	private double[] orderTotals = new double[MAX_ITEMS];
	private int itemSearchIndex = 0; 
	private int recordCount = 0;
	private Random prizeGenerator = new Random();

	//constructor
	public Inventory() 
	{
	}

	//methods

	//load items from file
	public void setLoadItems(String borrowedFileName) 
	{
		recordCount = RESET_VALUE;

		try 
		{
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));

			while(infile.hasNext() == true && recordCount < MAX_ITEMS)  
			{
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next(); 
				itemPrices[recordCount] = infile.nextDouble(); 
				inStockCounts[recordCount] = infile.nextInt();
				recordCount++;
			}

			infile.close();

			//sort parallel arrays by itemID
			setBubbleSort();

		}//END try

		catch(IOException ex) 
		{
			recordCount = NOT_FOUND;
		}
	}//END setLoadItems()


	//load sales from masterFile.txt
	public void setLoadItems(String borrowedFileName, int borrowedSize) 
	{
		recordCount = RESET_VALUE;

		try 
		{
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));

			while(infile.hasNext() == true && recordCount < MAX_ITEMS && recordCount < borrowedSize) 
			{
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				orderQuantity[recordCount] = infile.nextInt();
				orderTotals[recordCount] = infile.nextDouble(); 
				recordCount++;
			}

			//close file
			infile.close();

			//sort by itemID
			setBubbleSort();
		}//END try

		catch(IOException ex) 
		{
			recordCount = NOT_FOUND;
		}
	}//END sort sales from masterFile.txt

	//bubble sort
	public void setBubbleSort() 
	{
		int last = recordCount + NOT_FOUND; //array.length() - 1

		while(last > RESET_VALUE) //last > 0
		{
			int localIndex = RESET_VALUE; 
			boolean swap = false;

			while(localIndex < last) 
			{
				if(itemIDs[localIndex] > itemIDs[localIndex + 1]) 
				{
					setSwapArrayElements(localIndex);
					swap = true;
				}
				localIndex++;
			}

			if(swap == false) 
			{
				last = RESET_VALUE;
			}
			else 
			{
				last = last + NOT_FOUND;
			}

		}//END while(last > RESET_VALUE)
		return;
	}//END set bubble sort

	//swap array elements at index and index + 1
	public void setSwapArrayElements(int borrowedIndex) 
	{
		int localIntHolder = 0; 
		String localStringHolder = "";
		double localDoubleHolder = 0.0;

		if(orderTotals[borrowedIndex] > 0.0) 
		{
			localIntHolder = itemIDs[borrowedIndex];
			itemIDs[borrowedIndex] = itemIDs[borrowedIndex + 1];
			itemIDs[borrowedIndex + 1] = localIntHolder;

			localStringHolder = itemNames[borrowedIndex];
			itemNames[borrowedIndex] = itemNames[borrowedIndex + 1];
			itemNames[borrowedIndex + 1] = localStringHolder;

			localDoubleHolder = itemPrices[borrowedIndex];
			itemPrices[borrowedIndex] = itemPrices[borrowedIndex + 1];
			itemPrices[borrowedIndex + 1] = localDoubleHolder;

			localIntHolder = orderQuantity[borrowedIndex];
			orderQuantity[borrowedIndex] = orderQuantity[borrowedIndex + 1];
			orderQuantity[borrowedIndex + 1] = localIntHolder; 

			localDoubleHolder = orderTotals[borrowedIndex];
			orderTotals[borrowedIndex] = orderTotals[borrowedIndex + 1];
			orderTotals[borrowedIndex + 1] = localDoubleHolder;

		}
		else 
		{

			localIntHolder = itemIDs[borrowedIndex];
			itemIDs[borrowedIndex] = itemIDs[borrowedIndex + 1];
			itemIDs[borrowedIndex + 1] = localIntHolder;

			localStringHolder = itemNames[borrowedIndex];
			itemNames[borrowedIndex] = itemNames[borrowedIndex + 1];
			itemNames[borrowedIndex + 1] = localStringHolder;

			localDoubleHolder = itemPrices[borrowedIndex];
			itemPrices[borrowedIndex] = itemPrices[borrowedIndex + 1];
			itemPrices[borrowedIndex + 1] = localDoubleHolder;

			localIntHolder = inStockCounts[borrowedIndex];
			inStockCounts[borrowedIndex] = inStockCounts[borrowedIndex + 1];
			inStockCounts[borrowedIndex + 1] = localIntHolder;
		}
	}//END swap array elements


	//set reduce stock
	public void setReduceStock(int borrowedHowMany, int itemSearchIndex) 
	{
		inStockCounts[itemSearchIndex] -= borrowedHowMany; 
	}

	//set search index
	public void setSearchIndex(int borrowedTarget) 
	{
		itemSearchIndex = getSearchResult(borrowedTarget);
	}


	//get binary search result
	public int getSearchResult(int borrowedBorrowedTarget) 
	{
		int first = 0;
		int last = recordCount + NOT_FOUND;
		int mid = 0;
		boolean found = false;

		while(first <= last && found == false) 
		{
			mid = (first + last) / 2;

			if(itemIDs[mid] == borrowedBorrowedTarget) 
			{
				found = true;
			}
			else 
			{
				if(itemIDs[mid] < borrowedBorrowedTarget)  
				{
					first = mid + 1;
				}
				else 
				{
					last = mid -1;
				}
			}
		}//END while

		if(found == false) 
		{
			mid = NOT_FOUND;
		}

		return mid;
	}//END get binary search result


	//other getters

	//get search index
	public int getSearchIndex() 
	{
		return itemSearchIndex;
	}

	public int getRecordCount() 
	{
		return recordCount;
	}

	public int[] getInStockCounts() 
	{
		return inStockCounts;
	}

	public int[] getItemIDs() 
	{
		return itemIDs;
	}

	public String[] getItemNames() 
	{
		return itemNames;
	}

	public double[] getItemPrices() 
	{
		return itemPrices; 
	}

	public String[] getDiscountNames() 
	{
		return DISCOUNT_NAMES; 
	}

	public double[] getDiscountRates() 
	{
		return DISCOUNT_RATES;
	}

	public int[] getOrderQuantity() 
	{
		return orderQuantity; 
	}

	public double[] getOrderTotals() 
	{
		return orderTotals;
	}

	public String[] getPrizeNames() 
	{
		return PRIZE_NAMES; 
	}

	public int getRandomNumber() 
	{
		return prizeGenerator.nextInt(PRIZE_NAMES.length);
	}

}//END of class




















