package com.github.neo.core;



import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Fixed8;
import com.github.TesraSupernet.common.UInt256;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;

import java.io.IOException;

/**
 *
 */
public class TransactionOutput implements Serializable {
    /**
     *
     */
    public UInt256 assetId;
    /**
     *
     */
    public Fixed8 value;
    /**
     *
     */
    public Address scriptHash;
    
    /**
	 * byte
	 */
	@Override
	public void serialize(BinaryWriter writer) throws IOException {
		writer.writeSerializable(assetId);
		writer.writeSerializable(value);
		writer.writeSerializable(scriptHash);
	}
	
	@Override
	public void deserialize(BinaryReader reader) throws IOException {
		try {
			assetId = reader.readSerializable(UInt256.class);
			value = reader.readSerializable(Fixed8.class);
			scriptHash = reader.readSerializable(Address.class);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IOException();
		}
	}


	@Override
	public String toString() {
		return "TransactionOutput [assetId=" + assetId + ", value=" + value
				+ ", scriptHash=" + scriptHash + "]";
	}
}
