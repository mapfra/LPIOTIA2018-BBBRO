package org.eclipse.om2m.sdt.home.smarterkettle.communication;


import java.util.Scanner;

public class SmarterKettleMain {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Smart Kettle 2.0");
		System.out.println("1 - wlacz, 2 - wylacz, 3- checkStatus");
		
		
		TCPConnection.setAddress("10.0.1.27");
		TCPConnection.setPort(2081);
		
		while(true){
			SmarterKettleCommunication kettle = new SmarterKettleCommunication("10.0.1.27", 2081);
			
			int action = 100;
			
			
			Scanner input = new Scanner(System.in);
			String inputString = input.nextLine();
			action = Integer.parseInt(inputString);
			
			switch(action){
			case 1:
				System.out.println("Temperatura: ");
				int temperature = 100;
				inputString = input.nextLine();
				temperature = Integer.parseInt(inputString);
				kettle.startKettle(temperature);
				action = 100;
				break;
			case 2: 
				kettle.stopKettle();
				action = 100;
				break;
			case 3:
				kettle.checkStatus();
				action = 100;
				break;
			
			
				
				
			}
		}

		
		
		


		
	}
		
	


}
