
import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class Lcd {
	
	private I2CBus bus;
	private I2CDevice dev;
	private byte backlight = LCD_BACKLIGHT_ON;
	
	public static final byte LCD_CHR = 1;
	public static final byte LCD_CMD = 0;
	
	public static final byte LCD_LINE_1 = (byte) 0x80;
	public static final byte LCD_LINE_2 = (byte) 0xC0;
	public static final byte LCD_LINE_3 = (byte) 0x94;
	public static final byte LCD_LINE_4 = (byte) 0xD4;
	
	public static final byte LCD_BACKLIGHT_ON = 0x08;
	public static final byte LCD_BACKLIGHT_OFF = 0x00;
	
	private static final byte ENABLE = 0b00000100;
	private static final byte CLEAR = (byte) 0x01;
	
	private static final int time = 1;
	
	
	public Lcd(byte adr) throws UnsupportedBusNumberException, IOException{
		bus = I2CFactory.getInstance(I2CBus.BUS_1);
		dev = bus.getDevice(adr);
	}
	
	//init
	public void init() throws IOException{
		sendByte((byte) 0x33, LCD_CMD);
		sendByte((byte) 0x32, LCD_CMD);
		sendByte((byte) 0x06, LCD_CMD);
		sendByte((byte) 0x0c, LCD_CMD);
		sendByte((byte) 0x28, LCD_CMD);
		sendByte(CLEAR, LCD_CMD);
		try{Thread.sleep(time);}catch (InterruptedException e){e.printStackTrace();}
	}
	
	//sends byte to lcd(mode can be LCD_CMD or LCD_CHR)
	public void sendByte(byte b, byte mode) throws IOException{
		byte high = (byte) (mode | (b & 0xF0) | backlight);
		byte low = (byte) (mode | ((b<<4) & 0xF0) | backlight);
		dev.write(high);
		toggleEnable(high);
		dev.write(low);
		toggleEnable(low);
	}
	
	//toogles enable pin on display
	public void toggleEnable(byte bits) throws IOException{
		dev.write((byte) (bits | ENABLE));
		try{Thread.sleep(time);}catch (InterruptedException e){e.printStackTrace();}
		dev.write((byte) (bits & ~ENABLE));
	}
	
	//sets backlight(true = on, false = off)
	public void setBacklight(boolean state) throws IOException{
		if(state) backlight = LCD_BACKLIGHT_ON;
		else backlight = LCD_BACKLIGHT_OFF;
		dev.write(backlight);
	}
	
	//displays string in specified row and column
	public void string(String str, int row, int col) throws IOException{
		if(str.isEmpty()) return;
		byte[] b = str.getBytes("US-ASCII");
		byte line;
		if(row == 0) line = LCD_LINE_1;
		else if(row == 1) line = LCD_LINE_2;
		else if(row == 2) line = LCD_LINE_3;
		else if(row == 3) line = LCD_LINE_4;
		else return;
		line += col;
		sendByte(line, LCD_CMD);
		for(byte bb : b){
			sendByte(bb, LCD_CHR);
		}
	}
	
	//displays string in specified row and column 0
	public void string(String str, int row) throws IOException{
		if(str.isEmpty()) return;
		byte[] b = str.getBytes("US-ASCII");
		byte line;
		if(row == 0) line = LCD_LINE_1;
		else if(row == 1) line = LCD_LINE_2;
		else if(row == 2) line = LCD_LINE_3;
		else if(row == 3) line = LCD_LINE_4;
		else return;
		sendByte(line, LCD_CMD);
		for(byte bb : b){
			sendByte(bb, LCD_CHR);
		}
	}
	
	//displays custom char(0-7) in specified row and column
	public void custom(int i, int row, int col) throws IOException{
		byte line;
		if(row == 0) line = LCD_LINE_1;
		else if(row == 1) line = LCD_LINE_2;
		else if(row == 2) line = LCD_LINE_3;
		else if(row == 3) line = LCD_LINE_4;
		else return;
		line += col;
		sendByte(line, LCD_CMD);
		sendByte((byte) i, Lcd.LCD_CHR);
	}
	
	//displays custom char(0-7) in specified row and column 0
	public void custom(int i, int row) throws IOException{
		if(i > 7) return;
		byte line;
		if(row == 0) line = LCD_LINE_1;
		else if(row == 1) line = LCD_LINE_2;
		else if(row == 2) line = LCD_LINE_3;
		else if(row == 3) line = LCD_LINE_4;
		else return;
		sendByte(line, LCD_CMD);
		sendByte((byte) i, Lcd.LCD_CHR);
	}
	
	//clears the screen
	public void clear() throws IOException{
		sendByte(CLEAR, LCD_CMD);
	}
	
	//loads custom chars to display ram
	public void loadCustom(byte[][] fontdata) throws IOException{
		sendByte((byte) 0x40, LCD_CMD);
		
		for(byte[] xd : fontdata){
			for(byte line : xd){
				sendByte(line, LCD_CHR);
			}
		}
	}
	
}










