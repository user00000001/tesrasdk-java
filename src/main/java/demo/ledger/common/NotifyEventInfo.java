package demo.ledger.common;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 */
public class NotifyEventInfo {
    public byte[] ContractAddress;
    public List<Object> States;
}
