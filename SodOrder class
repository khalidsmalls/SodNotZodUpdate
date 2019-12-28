/*AUTHOR: Khalid Smalls
 *PURPOSE: makes and tracks one order at a time 
 *CREATEDATE: 12/18/2019 */





package sodNotZod;

public class SodOrder 
{
	//declare and initialize class constants
	private double ZERO = 0.0;
	private double TAX_RATE = .075;

	//declare and initialize non-constant attributes
	private int[] itemCounts;
	private int[] discountCounts;
	private int[] prizeCounts;
	private char discountType = ' ';
	private String itemName = ""; 
	private double itemPrice = 0.0;
	private String discountName = "";
	private double discountRate = 0.0; 
	private int howMany = 0;
	private int lastItemSelectedIndex = 0;
	private String prizeName = ""; 

	//SodOrder constructor
	public SodOrder() 
	{
	}    

	//setter behaviors

	//set item count array
	public void setItemCountArray(int borrowedRecordCount)
	{
		if(itemCounts == null || borrowedRecordCount > itemCounts.length) 
		{
			itemCounts = new int[borrowedRecordCount];
		}
	}//END set item counts array
	
	
	//set item selection and increment item counter
	public void setItemSelection(int borrowedSearchIndex) 
	{
		lastItemSelectedIndex = borrowedSearchIndex;
		itemCounts[lastItemSelectedIndex]++;
	}

	
	//set item name
	public void setItemName(String[] borrowedItemNames) 
	{
		itemName = borrowedItemNames[lastItemSelectedIndex];
	}
	
	
	//set item price
	public void setItemPrice(double[] borrowedItemPrices) 
	{
		itemPrice = borrowedItemPrices[lastItemSelectedIndex];
	}
	
	
	//set how many
	public void setHowMany(int borrowedHowMany) 
	{
		howMany = borrowedHowMany;
	}
	
	
	//set discount type
	public void setDiscountType(char borrowedMenuSelection) 
	{
		discountType = borrowedMenuSelection;
	}
	
	
	//set discount name
	public void setDiscountName(String[] borrowedDiscountNames) 
	{
		if(discountCounts == null) 
		{
			discountCounts = new int[borrowedDiscountNames.length];
		}
		
		if(discountType == 'A') 
		{
			discountName = borrowedDiscountNames[0]; 
			discountCounts[0]++;
		}
		else if(discountType == 'B') 
		{
			discountName = borrowedDiscountNames[1];
			discountCounts[1]++;
		}
		else 
		{
			discountName = borrowedDiscountNames[2];
			discountCounts[2]++;
		}
	}//END set discount name
	
	
	//set discount rate
	public void setDiscountRate(double[] borrowedDiscountRates) 
	{
		if(discountType == 'A') 
		{
			discountRate = borrowedDiscountRates[0];
		}
		else if(discountType == 'B') 
		{
			discountRate = borrowedDiscountRates[1];
		}
		else 
		{
			discountRate = borrowedDiscountRates[2]; 
		}
	}
	
	
	//set prize name
	public void setPrizeName(String[] borrowedPrizeNames, int borrowedPrizeIndex) 
	{
		if(prizeCounts == null) 
		{
			prizeCounts = new int[borrowedPrizeNames.length];
		}
		
		prizeName = borrowedPrizeNames[borrowedPrizeIndex];
		prizeCounts[borrowedPrizeIndex]++;
	}//END set prize name
	
	
	//set decrease in stock
	public void setDecreaseInStock(Inventory borrowedInventoryObject) 
	{
		borrowedInventoryObject.setReduceStock(getHowMany(), lastItemSelectedIndex);
	}
	
	
	//getter behaviors
	
	//get in stock count
	public int getInStockCount(int[] borrowedInStockCounts) 
	{
		return borrowedInStockCounts[lastItemSelectedIndex]; 
	}
	
	
	//get item name
	public String getItemName() 
	{
		return itemName; 
	}
	
	
	//get item price
	public double getItemPrice() 
	{
		return itemPrice;
	}
	
	
	//get item counts array
	public int[] getItemCounts() 
	{
		return itemCounts;
	}
	
	
	//get how many (current order)
	public int getHowMany() 
	{
		return howMany;
	}
	
	
	//get discount name
	public String getDiscountName() 
	{
		return discountName;
	}
	
	
	//get discount rate
	public double getDiscountRate() 
	{
		return discountRate;
	}
	
	
	//get discount counts
	public int[] getDiscountCounts() 
	{
		return discountCounts;
	}
	
	
	//get discount amount
	public double getDiscountAmt() 
	{
		return discountRate * itemPrice;
	}
	
	
	//get discount price
	public double getDiscountPrice() 
	{
		return itemPrice - getDiscountAmt(); 
	}
	
	
	//get prize name
	public String getPrizeName() 
	{
		return prizeName;
	}
	
	
	//get prize counts
	public int[] getPrizeCounts() 
	{
		return prizeCounts;
	}
	
	
	//get sub total
	public double getSubTotal() 
	{
		if(getDiscountRate() > ZERO) 
		{
			return howMany * getDiscountPrice(); 
		}
		else 
		{
			return howMany * itemPrice; 
		}
	}//END get sub total
	
	
	//get tax rate
	public double getTaxRate() 
	{
		return TAX_RATE;
	}
	
	
	//get tax amount
	public double getTaxAmt() 
	{
		return TAX_RATE * getSubTotal(); 
	}
	
	
	//get total cost
	public double getTotalCost() 
	{
		if(discountType == ' ') 
		{
			return ZERO;
		}
		else 
		{
			return getSubTotal() + getTaxAmt();
		}
	}//END get total cost
	
}//END of class






















