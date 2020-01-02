package com.github.TesraSupernet.core.asset;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.sdk.exception.SDKException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class StateTest {
    @Test
    public void deserialize() throws SDKException, IOException {
//        State state = new State(Address.decodeBase58("TRj9g9kvq8pL3L8M1F2KpsqWQHiC7u4xLP"),Address.decodeBase58("TRj9g9kvq8pL3L8M1F2KpsqWQHiC7u4xLP"),1000L);
//        ByteArrayOutputStream bais = new ByteArrayOutputStream();
//        BinaryWriter bw = new BinaryWriter(bais);
//        Transfers transfers = new Transfers(new State[]{state});
//        System.out.println(Helper.toHexString(transfers.toArray()));
//
//
//        state.serialize(bw);
//
//        State state1 = new State();
//        ByteArrayInputStream baos = new ByteArrayInputStream(bais.toByteArray());
//        BinaryReader br = new BinaryReader(baos);
//        state1.deserialize(br);
    }
}