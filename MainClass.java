# SodNotZod MainClass

/*AUTHOR: Khalid Smalls
 *PURPOSE: SodNotZod MainClass 
 *CREATEDATE: 12/19/2019 */


package sodNotZod;

import java.util.Scanner;

public class MainClass 
{
	//class constants
	public static final String MASTER_FILE_NAME = "masterFile.txt";
	public static final String[] BUTTONS = {" [A] ", " [B] ", " [C] ", " [Q] "}; 
	public static final String[][] MAIN_MENU = {{BUTTONS[0], BUTTONS[1], BUTTONS[3]}, {"Load Inventory", "Create Order", "Quit"}}; 
	public static final String $ = "$"; 
	public static final String SQFT = "/sq.ft.";
	public static final String PERCENT_SIGN = "%";
	public static final String PERCENT_OFF = "% off";
	public static final String ITEM_NAME_LABEL = "Item Name"; 
	public static final String ITEM_PRICE_LABEL = "Item Price"; 
	public static final String HOW_MANY_LABEL = "Quantity"; 
	public static final String DISCOUNT_NAME_LABEL = "Discount Type"; 
	public static final String DISCOUNT_RATE_LABEL = "Discount Rate";
	public static final String DISCOUNT_AMT_LABEL = "Discount Amount"; 
	public static final String DISCOUNT_PRICE_LABEL = "Discount Price"; 
	public static final String SUB_TOTAL_LABEL = "Sub Total"; 
	public static final String TAX_RATE_LABEL = "Tax Rate";
	public static final String TAX_AMT_LABEL = "Tax Amount"; 
	public static final String TOTAL_COST_LABEL = "Total Cost"; 
	public static final String PRIZE_NAME_LABEL = "Prize";


	//main method
	public static void main(String[] args) 
	{
		//instantiate objects
		Scanner input = new Scanner(System.in); //Scanner object

		SodOrder mySodOrder = new SodOrder(); //SodOrder object

		Inventory currentInventory = new Inventory(); //Inventory object
		
		OrderWriter writtenOrders = new OrderWriter(MASTER_FILE_NAME); 

		//declare and initialize variables
		String userName = "";
		char menuSelection = ' ';

		//welcome!
		displayWelcomeBanner();

		//get user name
		userName = getUserName(input); 

		//main menu display and validation
		menuSelection = validateMainMenu(input);

		//run-while not quit
		while(menuSelection != 'Q') //user chooses not to quit
		{
			if(menuSelection == 'A') //user chooses to load inventory
			{
				currentInventory.setLoadItems(getFileName(input));

				if(currentInventory.getRecordCount() <= 0) 
				{
					displayFileNotFound();
				}
				else 
				{
					mySodOrder.setItemCountArray(currentInventory.getRecordCount());
					displayInventoryConfirmation();
				}
			}//END load inventory

			else //user chooses to search for an itemID
			{
				currentInventory.setSearchIndex(validateSearchValue(input));

				if(currentInventory.getSearchIndex() == -1) //search result was NOT_FOUND
				{
					displayItemNotFound();
				}
				else //set item selection, name, price, how many
				{
					mySodOrder.setItemSelection(currentInventory.getSearchIndex());
					mySodOrder.setItemName(currentInventory.getItemNames());
					mySodOrder.setItemPrice(currentInventory.getItemPrices());
					mySodOrder.setHowMany(validateHowMany(input)); 

					//check stock
					if(mySodOrder.getInStockCount(currentInventory.getInStockCounts()) < mySodOrder.getHowMany())
					{
						displayOutOfStock();
					}
					else 
					{
						//set discount type, name, rate, adjust stock, and set prize
						mySodOrder.setDiscountType(validateDiscountMenu(input, currentInventory.getDiscountNames(),
								currentInventory.getDiscountRates()));
						mySodOrder.setDiscountName(currentInventory.getDiscountNames());
						mySodOrder.setDiscountRate(currentInventory.getDiscountRates());
						mySodOrder.setDecreaseInStock(currentInventory);
						mySodOrder.setPrizeName(currentInventory.getPrizeNames(), currentInventory.getRandomNumber());
						
						//write order to masterFile.txt
						writtenOrders.setWriteOrder(currentInventory.getItemIDs()[currentInventory.getSearchIndex()],
								mySodOrder.getItemName(), mySodOrder.getItemPrice(), mySodOrder.getHowMany(),
								mySodOrder.getTotalCost());
						
						//output receipt  
						if(mySodOrder.getDiscountRate() > 0) 
						{
							//with discount
							displayItemReceipt(mySodOrder.getItemName(), mySodOrder.getItemPrice(), mySodOrder.getHowMany(),
									mySodOrder.getDiscountName(), mySodOrder.getDiscountRate(), mySodOrder.getDiscountAmt(), 
									mySodOrder.getDiscountPrice(), mySodOrder.getSubTotal(), mySodOrder.getTaxRate(), 
									mySodOrder.getTaxAmt(), mySodOrder.getTotalCost(), mySodOrder.getPrizeName()); 
						}
						else 
						{
							//user chose no discount
							displayItemReceipt(mySodOrder.getItemName(), mySodOrder.getItemPrice(), mySodOrder.getHowMany(),
									mySodOrder.getSubTotal(), mySodOrder.getTaxRate(), mySodOrder.getTaxAmt(), mySodOrder.getTotalCost(),
									mySodOrder.getPrizeName());
						}
					}//END set discount, adjust stock, and set prize
				
				}//END set item and discount selections
				
			}//END else user chooses to search for itemID
			
			//update loop-control variable
			menuSelection = validateMainMenu(input);
			
		}//END run-while not quit
		
		//user chooses to quit - if sodOrder exists print final report
		if(mySodOrder.getTotalCost() > 0.0) 
		{
			//display final report and written order reports
			displayFinalReport(userName, currentInventory.getItemNames(), mySodOrder.getItemCounts(),
					currentInventory.getDiscountNames(), mySodOrder.getDiscountCounts(), 
					currentInventory.getPrizeNames(), mySodOrder.getPrizeCounts(), currentInventory.getInStockCounts());
			
			//load sales from masterFile.txt 
			currentInventory.setLoadItems(writtenOrders.getFileName(), writtenOrders.getRecordCount());
			
			displayWrittenOrders(userName, currentInventory.getRecordCount(), currentInventory.getItemIDs(), currentInventory.getItemNames(), 
					currentInventory.getItemPrices(), currentInventory.getOrderQuantity(), currentInventory.getOrderTotals()); 
			

		}
		
		//thank you come again!
		displayFarewell();
		
		//close Scanner
		input.close();


	}//END main method


	//VOID methods

	//display Welcome Banner
	public static void displayWelcomeBanner() 
	{
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|              *** Welome to SodNotZod ***                 |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.println("|   SodNotZod is your one-stop Sod Shop for SodNotZods'    |");
		System.out.println("| Sods and more. Read inventory from a file, track and     |");
		System.out.println("|     write sales to the pretend SodNotZod company         |");
		System.out.println("|         database with this updated version.              |");
		System.out.println("|__________________________________________________________|");

	}//END of display Welcome Banner


	//display main menu
	public static void displayMainMenu() 
	{
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|              *** SodNotZod Main Menu ***                 |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");

		int ROWS = MAIN_MENU[0].length;
		int COLUMNS = MAIN_MENU[1].length; 
		int row = 0; 
		int col = 0; 

		while(row < ROWS) 
		{
			while(col < COLUMNS) 
			{
				System.out.printf("%s%s%-15s%39s\n", "|", MAIN_MENU[row][col], MAIN_MENU[row + 1][col], "|");   
				col++;
			}
			row++;
		}
		System.out.println("|__________________________________________________________|");
		System.out.print(" >>> "); 
	}//END display main menu


	//display discount menu
	public static void displayDiscountMenu(String[] borrowedDiscountNames, double[] borrowedDiscountRates)  
	{         
		int localIndex = 0;
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|           *** SodNotZod Discount Menu ***                |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		while(localIndex < borrowedDiscountNames.length) 
		{
			System.out.printf("%s%s%-12s%19.0f%5s%18s\n", "|", BUTTONS[localIndex], borrowedDiscountNames[localIndex],
					borrowedDiscountRates[localIndex] * 100, PERCENT_OFF, "|");
			localIndex++;
		}

		System.out.println("|__________________________________________________________|");
		System.out.print(" >>> ");
	}//END display main menu


	//display file not found
	public static void displayFileNotFound() 
	{
		System.out.println("\n  *** The file was not found or could not be opened. ***");
		System.out.println("  *** *** **** *** *** ***** ** ***** *** ** ******* ***");
	}


	//display itemID not found
	public static void displayItemNotFound() 
	{
		System.out.println("\n\t\t *** ItemID not found ***");
		System.out.println("\t\t *** ****** *** ***** ***");
	}


	//display out of stock
	public static void displayOutOfStock() 
	{
		System.out.println("\n\t*** The item is currently unavailable. ***");
		System.out.println("\t***     Please reload inventory.       ***");
		System.out.println("\t******************************************");
	}


	//display load inventory confirmation
	public static void displayInventoryConfirmation() 
	{
		System.out.println("\n\t   *** Inventory loaded successfully ***");
		System.out.println("\t   *** ********* ****** ************ ***");
	}
	
	
	//display item receipt
	public static void displayItemReceipt(String borrowedItemName, double borrowedItemPrice,
			int borrowedHowMany, double borrowedSubTotal, double borrowedTaxRate, double borrowedTaxAmt, double borrowedTotalCost, String borrowedPrizeName) 
	{
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|            *** SodNotZod Order Summary ***               |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.printf("%-2s%-16s%-14s%-20s%8s\n", "|", ITEM_NAME_LABEL, "|", borrowedItemName, "|"); 
		System.out.printf("%-2s%-16s%-14s%s%8.2f%s%12s\n",  "|", ITEM_PRICE_LABEL, "|", $, borrowedItemPrice, SQFT, "|"); 
		System.out.printf("%-2s%-16s%-14s%6s%22s\n", "|", HOW_MANY_LABEL, "|", borrowedHowMany, "|"); 
		System.out.println("|----------------------------------------------------------|");
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", SUB_TOTAL_LABEL, "|", $, borrowedSubTotal, "|"); 
		System.out.printf("%-2s%-16s%-14s%8.1f%s%19s\n", "|", TAX_RATE_LABEL, "|", borrowedTaxRate * 100, PERCENT_SIGN, "|" );  
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", TAX_AMT_LABEL, "|", $, borrowedTaxAmt, "|"); 
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", TOTAL_COST_LABEL, "|", $, borrowedTotalCost, "|"); 
		System.out.printf("%-2s%-16s%-14s%-20s%8s\n", "|", PRIZE_NAME_LABEL, "|", borrowedPrizeName, "|"); 
		System.out.println("|__________________________________________________________|");

	}//END of display item receipt


	//display item receipt overloaded method w/discount
	public static void displayItemReceipt(String borrowedItemName, double borrowedItemPrice,
			int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate,
			double borrowedDiscountAmt, double borrowedDiscountPrice, double borrowedSubTotal,
			double borrowedTaxRate, double borrowedTaxAmt, double borrowedTotalCost, String borrowedPrizeName) 
	{
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|            *** SodNotZod Order Summary ***               |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.printf("%-2s%-16s%-14s%-20s%8s\n", "|", ITEM_NAME_LABEL, "|", borrowedItemName, "|"); 
		System.out.printf("%-2s%-16s%-14s%s%8.2f%s%12s\n",  "|", ITEM_PRICE_LABEL, "|", $, borrowedItemPrice, SQFT, "|"); 
		System.out.printf("%-2s%-16s%-14s%6s%22s\n", "|", HOW_MANY_LABEL, "|", borrowedHowMany, "|"); 
		System.out.println("|----------------------------------------------------------|");
		System.out.printf("%-2s%-16s%-14s%-15s%13s\n", "|", DISCOUNT_NAME_LABEL, "|", borrowedDiscountName, "|"); 
		System.out.printf("%-2s%-16s%-14s%8.1f%s%15s\n", "|", DISCOUNT_RATE_LABEL, "|", borrowedDiscountRate * 100, PERCENT_OFF, "|"); 
		System.out.printf("%-2s%-16s%-14s%s%8.2f%s%12s\n", "|", DISCOUNT_AMT_LABEL,"|", $, borrowedDiscountAmt, SQFT, "|");
		System.out.printf("%-2s%-16s%-14s%s%8.2f%s%12s\n", "|", DISCOUNT_PRICE_LABEL, "|", $, borrowedDiscountPrice, SQFT, "|"); 
		System.out.println("|----------------------------------------------------------|");
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", SUB_TOTAL_LABEL, "|", $, borrowedSubTotal, "|"); 
		System.out.printf("%-2s%-16s%-14s%8.1f%s%19s\n", "|", TAX_RATE_LABEL, "|", borrowedTaxRate * 100, PERCENT_SIGN, "|" ); 
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", TAX_AMT_LABEL, "|", $, borrowedTaxAmt, "|"); 
		System.out.printf("%-2s%-16s%-14s%1s%8.2f%19s\n", "|", TOTAL_COST_LABEL, "|", $, borrowedTotalCost, "|"); 
		System.out.printf("%-2s%-16s%-14s%-20s%8s\n", "|", PRIZE_NAME_LABEL, "|", borrowedPrizeName, "|"); 
		System.out.println("|__________________________________________________________|");

	}//END of display item receipt


	//display written orders
	public static void displayWrittenOrders(String userName, int borrowedRecordCount, int[] borrowedItemIDs,
			String[] borrowedItemNames, double[] borrowedItemPrices, int[] borrowedOrderQuantity, 
			double[] borrowedTotalCost) 
	{
		int localIndex = 0;
		
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|               *** Written Order Summary ***              |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.printf("%-2s%-10s%-20s%28s\n","|", "UserName:", userName, "|");
		System.out.println("|----------------------------------------------------------|");
		System.out.printf("%-2s%-8s%-16s%-9s%-12s%10s%3s\n","|", "ItemID", "Item Name", "Price", "Quantity", "Total Cost", "|");
		System.out.println("|----------------------------------------------------------|");
		
		while(localIndex < borrowedRecordCount) 
		{
			System.out.printf("%-2s%-8d%-16s%s%-9.2f%-5d%7s%8.2f%4s\n", "|", borrowedItemIDs[localIndex],
					borrowedItemNames[localIndex], "$", borrowedItemPrices[localIndex], borrowedOrderQuantity[localIndex],"$", borrowedTotalCost[localIndex], "|"); 
			localIndex++;
		}
		System.out.println("|__________________________________________________________|");
	}//END display written orders
	
	//display final order report
	public static void displayFinalReport(String userName, String[] borrowedItemNames, int[] borrowedItemCounts,
			String[] borrowedDiscountNames, int[] borrowedDiscountCounts, String[] borrowedPrizeNames, 
			int[] borrowedPrizeCounts, int[] borrowedInStockCounts) 
	{
		int localIndex;
		System.out.println("\n|**********************************************************|");
		System.out.println("|**********************************************************|");
		System.out.println("|            *** SodNotZod Order Report ***                |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.printf("%-2s%-10s%-38s%10s\n","|", "UserName:", userName, "|");
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                    Order Counts:       In Stock:         |");
		System.out.println("|----------------------------------------------------------|");
		
		localIndex = 0;
		while(localIndex < borrowedItemCounts.length)  
		{
			System.out.printf("%-2s%-20s%7d%18d%13s\n", "|", borrowedItemNames[localIndex], borrowedItemCounts[localIndex],
					borrowedInStockCounts[localIndex], "|"); 
			localIndex++;
		}
		
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                   Discount Counts:                       |");
		System.out.println("|                                                          |");
		
		System.out.printf("%-7s","|");
		localIndex = 0;
		while(localIndex < borrowedDiscountCounts.length) 
		{
			System.out.printf("%-17s", borrowedDiscountNames[localIndex]);
			localIndex++;
		}
		System.out.printf("%2s\n", "|");
		
		System.out.printf("%-7s%-17s%-17s%-18s%s\n", "|", "------", "------", "-----------", "|" );
		
		localIndex = 0;
		System.out.printf("%-8s","|");
		while(localIndex < borrowedDiscountCounts.length) 
		{
			System.out.printf("%-17s", borrowedDiscountCounts[localIndex]);
			localIndex++;
		}
		System.out.println("|");
		
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                    Prize Counts:                         |");
		System.out.println("|                                                          |");
		
		localIndex = 0;
		System.out.printf("%-4s","|");
		while(localIndex < borrowedPrizeCounts.length) 
		{
			System.out.printf("%-17s", borrowedPrizeNames[localIndex]);
			localIndex++;
		}
		System.out.printf("%5s\n", "|");
		
		System.out.printf("%-4s%-17s%-17s%-18s%4s\n", "|", "-------------", "-------------", "-----------------", "|" );
		
		localIndex = 0;
		System.out.printf("%-8s","|");
		while(localIndex < borrowedPrizeCounts.length) 
		{
			System.out.printf("%-17s", borrowedPrizeCounts[localIndex]);
			localIndex++;
		}
		System.out.println("|");
		
		System.out.println("|__________________________________________________________|");

	}//END final order report



	//display farewell
	public static void displayFarewell() 
	{
		System.out.println("\n\n\t  **********************************");
		System.out.println("\t  Thank you for visisting SodNotZod!");
		System.out.println("\t     Please, come back and visit");
		System.out.println("\t       for all your sod needs!");
		System.out.println("\t  **********************************");
	}//END of display farewell



	//VR methods

	//get user name
	public static String getUserName(Scanner borrowedInput) 
	{
		String localUserName = "";
		System.out.print("\n UserName >>> ");
		localUserName = borrowedInput.next();
		return localUserName;
	}


	//get file name
	public static String getFileName(Scanner borrowedInput) 
	{
		String localFileName = "";
		System.out.print("\n Enter File Name (with extension): ");
		localFileName = borrowedInput.next();
		return localFileName; 
	}



	//validate main menu
	public static char validateMainMenu(Scanner borrowedInput) 
	{
		char localMenuSelection = ' ';
		displayMainMenu();
		localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);

		while(localMenuSelection != 'A' && localMenuSelection != 'B' && localMenuSelection != 'Q') 
		{
			System.out.println("\n\t\t*** Invalid Selection ***");
			displayMainMenu();
			localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		}
		return localMenuSelection;
	}//END validate main menu


	//validate search value
	public static int validateSearchValue(Scanner borrowedInput) 
	{
		int localSearchValue = 0;
		System.out.print("\n Enter itemID number: ");
		localSearchValue = borrowedInput.nextInt();

		while(localSearchValue < 0) 
		{
			System.out.println("\n\t *** ItemID must be a number greater than 0 ***");
			System.out.print("\n Enter itemID number: ");
			localSearchValue = borrowedInput.nextInt();
		}
		return localSearchValue; 
	}//END validate search value



	//validate how many items
	public static int validateHowMany(Scanner borrowedInput) 
	{
		int localHowMany = 0;
		System.out.print("\n Enter number of items: ");
		localHowMany = borrowedInput.nextInt(); 

		while(localHowMany <= 0) 
		{
			System.out.println("\n\t\t*** Invalid Entry ***\n");
			System.out.print(" Enter number of items: ");
			localHowMany = borrowedInput.nextInt(); 
		}
		return localHowMany; 
	}//END of validate how many



	//validate discount menu
	public static char validateDiscountMenu(Scanner borrowedInput, String[] borrowedDiscountNames,
			double[] borrowedDiscountPrices) 
	{
		char localMenuSelection = ' '; 
		displayDiscountMenu(borrowedDiscountNames, borrowedDiscountPrices);
		localMenuSelection = borrowedInput.next().toUpperCase().charAt(0); 

		while(localMenuSelection != 'A' && localMenuSelection != 'B' && localMenuSelection != 'C') 
		{
			System.out.println("\n\t\t*** Invalid Selection ***");
			displayDiscountMenu(borrowedDiscountNames, borrowedDiscountPrices);
			localMenuSelection = borrowedInput.next().toUpperCase().charAt(0); 
		}
		return localMenuSelection;
	}//END of display discount menu



}//END of class


















