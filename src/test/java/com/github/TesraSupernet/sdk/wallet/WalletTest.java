package com.github.TesraSupernet.sdk.wallet;

import com.github.TesraSupernet.TstSdk;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class WalletTest {

    TstSdk tstSdk;
    Identity id1;
    Identity id2;
    Account acct1;
    Account acct2;

    String walletFile = "WalletTest.json";

    @Before
    public void setUp() throws Exception {
        tstSdk = TstSdk.getInstance();
        tstSdk.openWalletFile(walletFile);


        id1 = tstSdk.getWalletMgr().createIdentity("passwordtest");
        id2 = tstSdk.getWalletMgr().createIdentity("passwordtest");

        acct1 = tstSdk.getWalletMgr().createAccount("passwordtest");
        acct2 = tstSdk.getWalletMgr().createAccount("passwordtest");
    }

    @After
    public void removeWallet(){
        File file = new File(walletFile);
        if(file.exists()){
            if(file.delete()){
                System.out.println("delete wallet file success");
            }
        }
    }


    @Test
    public void getAccount() throws Exception {
        Account acct = tstSdk.getWalletMgr().getWallet().getAccount(acct1.address);
        Assert.assertNotNull(acct);

        tstSdk.getWalletMgr().getWallet().setDefaultIdentity(id1.tstid);
        tstSdk.getWalletMgr().getWallet().setDefaultIdentity(1);
        tstSdk.getWalletMgr().getWallet().setDefaultAccount(acct1.address);
        tstSdk.getWalletMgr().getWallet().setDefaultAccount(1);
        Identity did = tstSdk.getWalletMgr().getWallet().getIdentity(id1.tstid);
        Assert.assertNotNull(did);
        boolean b = tstSdk.getWalletMgr().getWallet().removeIdentity(id1.tstid);
        Assert.assertTrue(b);

        boolean b2 = tstSdk.getWalletMgr().getWallet().removeAccount(acct1.address);
        Assert.assertTrue(b2);


    }


}