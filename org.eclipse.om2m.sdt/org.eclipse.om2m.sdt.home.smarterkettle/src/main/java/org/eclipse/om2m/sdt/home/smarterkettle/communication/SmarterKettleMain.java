/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

import java.util.Scanner;

public class SmarterKettleMain {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Smart Kettle 2.0");
		System.out.println("1 - start, 2 - stop, 3- checkStatus");
		
		while (true) {
			SmarterKettleCommunication kettle = new SmarterKettleCommunication("10.0.1.27", 2081);
			Scanner input = new Scanner(System.in);
			String inputString = input.nextLine();
			int action = Integer.parseInt(inputString);
			
			switch (action) {
			case 1:
				System.out.println("Temperatura: ");
				inputString = input.nextLine();
				int temperature = Integer.parseInt(inputString);
				kettle.startKettle(temperature);
				break;
			case 2: 
				kettle.stopKettle();
				break;
			case 3:
				kettle.checkStatus();
				break;
			}
			input.close();
		}
	}

}
