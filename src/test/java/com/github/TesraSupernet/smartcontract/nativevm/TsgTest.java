package com.github.TesraSupernet.smartcontract.nativevm;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.TstSdkTest;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.exception.SDKException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TsgTest {

    public String password = "111111";
    TstSdk tstSdk;
    Account account;
    Account receiveAcc;

    @Before
    public void setUp() throws Exception {
        tstSdk=TstSdk.getInstance();
        tstSdk.setRestful(TstSdkTest.URL);
        account = new Account(Helper.hexToBytes(TstSdkTest.PRIVATEKEY),SignatureScheme.SHA256WITHECDSA);
        receiveAcc = new Account(SignatureScheme.SHA256WITHECDSA);
        tstSdk.nativevm().tst().sendTransfer(account,receiveAcc.getAddressU160().toBase58(),10L,account,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);

        String accountTsg = tstSdk.nativevm().tsg().unboundTsg(account.getAddressU160().toBase58());
        tstSdk.nativevm().tsg().withdrawTsg(account,account.getAddressU160().toBase58(),1000,account,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        Object obj = tstSdk.getConnect().getBalance(account.getAddressU160().toBase58());
        System.out.println(obj);
    }
    @Test
    public void sendTransfer() throws Exception {
        long accountTsg = tstSdk.nativevm().tsg().queryBalanceOf(account.getAddressU160().toBase58());
        long receiveAccTsg = tstSdk.nativevm().tsg().queryBalanceOf(receiveAcc.getAddressU160().toBase58());
        tstSdk.nativevm().tsg().sendTransfer(account,receiveAcc.getAddressU160().toBase58(),10L,account,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        long accountTsg2 = tstSdk.nativevm().tsg().queryBalanceOf(account.getAddressU160().toBase58());
        long receiveAccTsg2 = tstSdk.nativevm().tsg().queryBalanceOf(receiveAcc.getAddressU160().toBase58());
        Assert.assertTrue(accountTsg-accountTsg2 == 10);
        Assert.assertTrue(receiveAccTsg2-receiveAccTsg == 10);
    }


    @Test
    public void sendApprove() throws Exception {
        long allowance = tstSdk.nativevm().tsg().queryAllowance(account.getAddressU160().toBase58(),receiveAcc.getAddressU160().toBase58());
        tstSdk.nativevm().tsg().sendApprove(account,receiveAcc.getAddressU160().toBase58(),10,account,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        long allowance2 = tstSdk.nativevm().tsg().queryAllowance(account.getAddressU160().toBase58(),receiveAcc.getAddressU160().toBase58());
        Assert.assertTrue(allowance2-allowance == 10);

        long acctbalance = tstSdk.nativevm().tsg().queryBalanceOf(account.getAddressU160().toBase58());
        long reciebalance = tstSdk.nativevm().tsg().queryBalanceOf(receiveAcc.getAddressU160().toBase58());
        tstSdk.nativevm().tsg().sendTransferFrom(receiveAcc,account.getAddressU160().toBase58(),receiveAcc.getAddressU160().toBase58(),10,receiveAcc,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        long acctbalance2 = tstSdk.nativevm().tsg().queryBalanceOf(account.getAddressU160().toBase58());
        long reciebalance2 = tstSdk.nativevm().tsg().queryBalanceOf(receiveAcc.getAddressU160().toBase58());
        Assert.assertTrue(acctbalance-acctbalance2 == 10);
        Assert.assertTrue(reciebalance2 - reciebalance == 10);
        long allowance3 = tstSdk.nativevm().tsg().queryAllowance(account.getAddressU160().toBase58(),receiveAcc.getAddressU160().toBase58());
        Assert.assertTrue(allowance3 == allowance);
    }

    @Test
    public void queryName() throws Exception {
        String name = tstSdk.nativevm().tsg().queryName();
        Assert.assertTrue(name.contains("TSG"));
        String symbol = tstSdk.nativevm().tsg().querySymbol();
        Assert.assertTrue(symbol.contains("TSG"));
        long decimals = tstSdk.nativevm().tsg().queryDecimals();
        Assert.assertTrue(decimals == 9);
        long total = tstSdk.nativevm().tsg().queryTotalSupply();
        Assert.assertFalse(total < 0);
    }
}