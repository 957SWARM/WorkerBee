package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class WorkerBee {
    int DEVICE_PORT;
    I2C arduino;

    char[] charBuffer;
    RGB[] rgbBuffer;

    public WorkerBee(){
        arduino = new I2C(Port.kMXP, DEVICE_PORT);
        charBuffer = new char[4];
        rgbBuffer = new RGB[4];
    }

    public void setChar(char input, int position){
        charBuffer[position] = input;
    }

    public void setRGB(byte r, byte g, byte b, int position){
        rgbBuffer[position] = new RGB(r, g, b);
    }

    public void send(){
        // Array to convert char[] into byte[]
        byte[] charToByteArray = charBuffer.toString().getBytes();
        
        // Len of first array
        int fal = charToByteArray.length;
        
        // Len of second array
        int sal = rgbBuffer.length;
        
        // New assembled array of rgb + char array
        byte[] assembledArray = new byte[fal + sal];

        // Jank merging of arrays
        System.arraycopy(charToByteArray, 0, assembledArray, 0, fal);
        System.arraycopy(rgbBuffer, 0, assembledArray, fal, sal);
        
        // Send off to the arduino
        arduino.writeBulk(assembledArray);
    }

    public class RGB {
        public byte r;
        public byte g;
        public byte b;
    
        public RGB(byte r, byte g, byte b){
            this.r = r;
            this.g = g;
            this.b = b;
        }
    
        public byte[] toBytes(){
            byte[] rgbArray = {r, g, b};
            return rgbArray;
        }
    }

}