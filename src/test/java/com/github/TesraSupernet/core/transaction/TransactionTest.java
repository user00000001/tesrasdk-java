package com.github.TesraSupernet.core.transaction;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.exception.SDKException;
import com.github.TesraSupernet.smartcontract.Vm;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TransactionTest {

    TstSdk tstSdk;
    Vm vm;
    String tstContract = "ff00000000000000000000000000000000000001";

    @Before
    public void setUp(){
        tstSdk = TstSdk.getInstance();
        vm = new Vm(tstSdk);
    }

    @Test
    public void serialize() throws Exception {
        Transaction tx = vm.buildNativeParams(Address.parse(tstContract),"init","1".getBytes(),null,0,0);
        Account account = new Account(Helper.hexToBytes("0bc8c1f75a028672cd42c221bf81709dfc7abbbaf0d87cb6fdeaf9a20492c194"),SignatureScheme.SHA256WITHECDSA);
        tstSdk.signTx(tx,new Account[][]{{account}});

        String t = tx.toHexString();
        System.out.println(t);

        Transaction tx2 = Transaction.deserializeFrom(Helper.hexToBytes(t));
        System.out.println(tx2.json());


    }
}