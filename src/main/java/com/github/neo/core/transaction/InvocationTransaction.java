package com.github.neo.core.transaction;



import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Fixed8;
import com.github.TesraSupernet.core.transaction.TransactionType;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;


import java.io.IOException;

public class InvocationTransaction extends TransactionNeo {
	public byte[] script;
	public Fixed8 gas;

	public InvocationTransaction() {
		super(TransactionType.InvokeCode);
	}

	@Override
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException {
		try {
			script = reader.readVarBytes();
			gas = reader.readSerializable(Fixed8.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void serializeExclusiveData(BinaryWriter writer) throws IOException {
		writer.writeVarBytes(script);
		writer.writeSerializable(gas);
	}
	@Override
	public Address[] getAddressU160ForVerifying() {
		return null;
	}
}
