/*AUTHOR: Khalid Smalls
 *PURPOSE: writes sod orders to file "masterFile.txt" 
 *CREATEDATE: 12/24/2019 */



package sodNotZod;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderWriter 
{
	//declare class constant
	private int NOT_FOUND = -1;
	
	//declare and initialize non-constant attributes
	private String masterFileName = "masterFile.txt";
	private int recordCount = 0;
	
	//OrderWriter constructor
	public OrderWriter(String borrowedFileName) 
	{
	}

	//behaviors
	
	//write one record to masterFile.txt 
	public void setWriteOrder(int borrowedItemID, String borrowedItemName, double borrowedItemPrice,
			int borrowedQuantity, double borrowedOrderCost) 
	{
		try 
		{
			//instantiate FileWriter and PrintWriter
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, true));
			
			//write one record
			filePW.printf("%d\t%s\t%.2f\t%d\t%.2f\n", borrowedItemID, borrowedItemName, borrowedItemPrice,
					borrowedQuantity, borrowedOrderCost);
			
			//close masterFile.txt
			filePW.close();
			
			recordCount++;
		}//END try
		
		catch(IOException ex) 
		{
			recordCount = NOT_FOUND;
		}
	}//END set write order
	
	
	//return masterFileName(masterFile.txt) 
	public String getFileName() 
	{
		return masterFileName;
	}
	
	
	//get record count
	public int getRecordCount() 
	{
		return recordCount;  
	}
}//END of class








