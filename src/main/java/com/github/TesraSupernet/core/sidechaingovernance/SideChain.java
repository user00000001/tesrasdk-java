package com.github.TesraSupernet.core.sidechaingovernance;

import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SideChain implements Serializable {
    public String sideChainId;
    public Address address;
    public long ratio;
    public long deposit;
    public long tsgNum;
    public long tsgPool;
    public byte status;

    public SideChain(){}
    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.sideChainId = reader.readVarString();
        try {
            this.address = reader.readSerializable(Address.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.ratio = reader.readLtsg();
        this.deposit = reader.readLtsg();
        this.tsgNum = reader.readLtsg();
        this.tsgPool = reader.readLtsg();
        this.status = reader.readByte();
    }

    @Override
    public void serialize(BinaryWriter binaryWriter) throws IOException {

    }
    public String toJson(){
        Map map = new HashMap<>();
        map.put("sideChainId",this.sideChainId);
        map.put("address", this.address.toBase58());
        map.put("ratio", this.ratio);
        map.put("deposit", this.deposit);
        map.put("tsgNum", this.tsgNum);
        map.put("tsgPool", this.tsgPool);
        map.put("status", this.status);
        return JSON.toJSONString(map);
    }
}
