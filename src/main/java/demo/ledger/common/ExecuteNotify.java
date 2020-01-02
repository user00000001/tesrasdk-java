package demo.ledger.common;

import com.github.TesraSupernet.common.UInt256;
import com.github.TesraSupernet.io.BinaryReader;

import java.io.IOException;


/**
 *
 *
 */
public class ExecuteNotify {
    public byte[] TxHash;
    public byte State;
    public long GasConsumed;
    public NotifyEventInfo[] Notify;
}
