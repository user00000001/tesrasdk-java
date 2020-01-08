package demo.ledger.p2p;

import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 *
 */
public class PongRsp {
    public long height;
    public PongRsp(){

    }
    public PongRsp(long height){
        this.height = height;
    }
    public byte[] serialization(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryWriter bw = new BinaryWriter(baos);
        try {
            bw.writeLong(height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
    public byte[] msgSerialization(){
        Message msg = new Message(serialization());
        msg.header = new MessageHeader(Message.NETWORK_MAGIC_MAINNET,"pong".getBytes(),msg.message.length,Message.checkSum(msg.message));
        return msg.serialization();
    }
    public void deserialization(byte[] data){
        ByteArrayInputStream ms = new ByteArrayInputStream(data);
        BinaryReader reader = new BinaryReader(ms);
        try {
            height = reader.readLtsg();
            System.out.println(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
