package com.github.TesraSupernet.smartcontract;

import com.github.TesraSupernet.OntSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.sdk.exception.SDKException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VmTest {

    OntSdk ontSdk;
    Vm vm;
    @Before
    public void setUp(){
        ontSdk = OntSdk.getInstance();
        vm = new Vm(ontSdk);

    }

    @Test
    public void buildNativeParams() throws SDKException {
//        Address addr = Address.decodeBase58("TA9MXtwAcXkUMuujJh2iNRaWoXrvzfrmZb");
//        vm.buildNativeParams(addr,"init","1".getBytes(),null,0,0);
    }
}