package com.github.TesraSupernet.core.governance;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;
import com.github.TesraSupernet.io.utils;

import java.io.IOException;

public class NodeToSideChainParams implements Serializable {
    public String peerPubkey;
    public Address address;
    public String sideChainId;
    public NodeToSideChainParams(){}

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.peerPubkey = reader.readVarString();
        this.address = utils.readAddress(reader);
        this.sideChainId = reader.readVarString();
    }

    @Override
    public void serialize(BinaryWriter binaryWriter) throws IOException {

    }
}
