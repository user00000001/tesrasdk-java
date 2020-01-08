package com.github.TesraSupernet.core.payload;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.core.transaction.TransactionType;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;

import java.io.IOException;

public class InvokeWasmCode extends Transaction {

    public byte[] invokeCode;

    public InvokeWasmCode(byte[] invokeCode) {
        super(TransactionType.InvokeWasm);
        this.invokeCode = invokeCode;
    }

    @Override
    protected void deserializeExclusiveData(BinaryReader reader) throws IOException {
        try {
            invokeCode = reader.readVarBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void serializeExclusiveData(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(invokeCode);
    }
}
