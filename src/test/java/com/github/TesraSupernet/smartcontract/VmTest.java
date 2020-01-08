package com.github.TesraSupernet.smartcontract;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.sdk.exception.SDKException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VmTest {

    TstSdk tstSdk;
    Vm vm;
    @Before
    public void setUp(){
        tstSdk = TstSdk.getInstance();
        vm = new Vm(tstSdk);

    }

    @Test
    public void buildNativeParams() throws SDKException {
//        Address addr = Address.decodeBase58("TA9MXtwAcXkUMuujJh2iNRaWoXrvzfrmZb");
//        vm.buildNativeParams(addr,"init","1".getBytes(),null,0,0);
    }
}