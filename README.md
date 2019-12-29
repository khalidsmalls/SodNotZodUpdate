# SodNotZodUpdate


This new and improved version adds file input/output functionality to the pretend e-commerce SodNotZod sod selling program from CPT 187. 
New features include sorting and searching algorithms as well as the file input and output behaviors.
Inventory class has a method for loading inventory associated arrays from the test file and an overloaded version of that method for reading records from the written file, masterFile.txt. Arrays are sorted by itemID so that a binary search may return a search result from the itemId array. Inventory also handles the random prize selection from the prize array.
SodOrder class sets data for each individual order including item selection, discount selection, and prize award. SodOrder also calculates the cost of each order including tax.
OrderWriter class writes one record at a time to masterfile.txt consisting of five fields: itemID, item name, price, order quantity, and total order cost.
