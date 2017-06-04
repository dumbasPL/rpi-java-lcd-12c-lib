package me.dumbasPL.lcd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	
	static byte[][] data = {{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x02 }, //lg
	        				{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0x00 }, //g
	        				{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x08 }, //pg
	        				{ 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02 }, //l
	        				{ 0x08, 0x08, 0x08, 0x08, 0x08, 0x08, 0x08, 0x08 }, //p
	        				{ 0x02, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, //ld
	        				{ 0x08, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, //pd
	        				{ 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }};//d



	public static void main(String[] args) throws Exception {
		System.out.println("LCD TEST!");
        
		Lcd lcd = new Lcd((byte) 0x3F);
		lcd.init();
		lcd.loadCustom(data);
		lcd.custom(0, 0, 0);
		for(int i = 1; i < 19; i++) lcd.custom(1, 0, i);
		lcd.custom(2, 0, 19);
		lcd.custom(3, 1, 0);
		lcd.custom(3, 2, 0);
		lcd.custom(4, 1, 19);
		lcd.custom(4, 2, 19);
		lcd.custom(5, 3, 0);
		for(int i = 1; i < 19; i++) lcd.custom(7, 3, i);
		lcd.custom(6, 3, 19);
		
		while(true){
			String date = new SimpleDateFormat("dd.MM.yyy").format(new Date());
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			
			lcd.string(date, 1, 5);
			lcd.string(time, 2, 6);
			
			
		}
		
	}

}
