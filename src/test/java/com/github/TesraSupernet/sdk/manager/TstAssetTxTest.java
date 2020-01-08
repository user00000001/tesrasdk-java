package com.github.TesraSupernet.sdk.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.TstSdkTest;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.exception.SDKException;
import com.github.TesraSupernet.sdk.wallet.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class TstAssetTxTest {

    TstSdk tstSdk;
    Account info1 = null;
    Account info2 = null;
    Account info3 = null;
    String password = "111111";
    String wallet = "TstAssetTxTest.json";

    Account payer;
    @Before
    public void setUp() throws Exception {
        tstSdk = TstSdk.getInstance();
        String restUrl = TstSdkTest.URL;
        tstSdk.setRestful(restUrl);
        tstSdk.setDefaultConnect(tstSdk.getRestful());
        tstSdk.openWalletFile(wallet);
        info1 = tstSdk.getWalletMgr().createAccountFromPriKey(TstSdkTest.PASSWORD, TstSdkTest.PRIVATEKEY);
        info2 = tstSdk.getWalletMgr().createAccount(password);
        info3 = tstSdk.getWalletMgr().createAccount(password);

        payer = tstSdk.getWalletMgr().createAccount(password);
    }
    @After
    public void removeWallet(){
        File file = new File(wallet);
        if(file.exists()){
            if(file.delete()){
                System.out.println("delete wallet file success");
            }
        }
    }
    @Test
    public void sendTransfer() throws Exception {
        com.github.TesraSupernet.account.Account sendAcct = tstSdk.getWalletMgr().getAccount(info1.address,password,info1.getSalt());
        com.github.TesraSupernet.account.Account payerAcct = tstSdk.getWalletMgr().getAccount(payer.address,password,payer.getSalt());
        String res= tstSdk.nativevm().tst().sendTransfer(sendAcct,info2.address,100L,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);


        Assert.assertNotNull(res);
    }

    @Test
    public void makeTransfer() throws Exception {

        Transaction tx = tstSdk.nativevm().tst().makeTransfer(info1.address,info2.address,100L,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        Assert.assertNotNull(tx);
    }

    @Test
    public void sendApprove() throws Exception {
        com.github.TesraSupernet.account.Account sendAcct1 = tstSdk.getWalletMgr().getAccount(info1.address,password,info1.getSalt());
        com.github.TesraSupernet.account.Account sendAcct2 = tstSdk.getWalletMgr().getAccount(info2.address,password,info2.getSalt());
        com.github.TesraSupernet.account.Account payerAcct = tstSdk.getWalletMgr().getAccount(payer.address,password,payer.getSalt());
        tstSdk.nativevm().tst().sendApprove(sendAcct1,sendAcct2.getAddressU160().toBase58(),10L,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        long info1balance = tstSdk.nativevm().tst().queryBalanceOf(sendAcct1.getAddressU160().toBase58());
        long info2balance = tstSdk.nativevm().tst().queryBalanceOf(sendAcct2.getAddressU160().toBase58());
        Thread.sleep(6000);

        long allo = tstSdk.nativevm().tst().queryAllowance(sendAcct1.getAddressU160().toBase58(),sendAcct2.getAddressU160().toBase58());
        Assert.assertTrue(allo == 10);
        tstSdk.nativevm().tst().sendTransferFrom(sendAcct2,info1.address,sendAcct2.getAddressU160().toBase58(),10L,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        long info1balance2 = tstSdk.nativevm().tst().queryBalanceOf(info1.address);
        long info2balance2 = tstSdk.nativevm().tst().queryBalanceOf(info2.address);

        Assert.assertTrue((info1balance - info1balance2) == 10);
        Assert.assertTrue((info2balance2 - info2balance) == 10);


    }

    @Test
    public void sendTsgTransferFrom() throws Exception {
        String unboundTsgStr = tstSdk.nativevm().tsg().unboundTsg(info1.address);
        long unboundTsg = Long.parseLong(unboundTsgStr);
        String res = tstSdk.nativevm().tsg().withdrawTsg(tstSdk.getWalletMgr().getAccount(info1.address,password,info1.getSalt()),info2.address,unboundTsg/100,tstSdk.getWalletMgr().getAccount(payer.address,password,payer.getSalt()),tstSdk.DEFAULT_GAS_LIMIT,0);
        Assert.assertNotNull(res);
    }

    @Test
    public void queryTest() throws Exception {

        long decimal = tstSdk.nativevm().tst().queryDecimals();
        long decimal2 = tstSdk.nativevm().tsg().queryDecimals();
        Assert.assertNotNull(decimal);
        Assert.assertNotNull(decimal2);

        String tstname = tstSdk.nativevm().tst().queryName();
        String tsgname = tstSdk.nativevm().tsg().queryName();
        Assert.assertNotNull(tstname);
        Assert.assertNotNull(tsgname);

        String tstsym = tstSdk.nativevm().tst().querySymbol();
        String tsgsym = tstSdk.nativevm().tsg().querySymbol();
        Assert.assertNotNull(tstsym);
        Assert.assertNotNull(tsgsym);

        long tsttotal = tstSdk.nativevm().tst().queryTotalSupply();
        long tsgtotal = tstSdk.nativevm().tsg().queryTotalSupply();
        Assert.assertNotNull(tsttotal);
        Assert.assertNotNull(tsgtotal);
    }
}