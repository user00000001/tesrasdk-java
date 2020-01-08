package com.github.TesraSupernet.sdk.manager;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.TstSdkTest;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;
import com.github.TesraSupernet.sdk.wallet.Wallet;
import com.github.TesraSupernet.smartcontract.nativevm.TstId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class WalletMgrTest {
    private TstSdk tstSdk;
    private WalletMgr walletMgr;
    private Wallet wallet;
    private TstId tstIdTx;

    String password = "111111";
    byte[] salt = new byte[]{};
    Account payer;

    String walletFile = "wallet.json";

    @Before
    public void setUp() throws Exception {
        tstSdk = TstSdk.getInstance();
        tstSdk.setRestful(TstSdkTest.URL);
        tstSdk.openWalletFile(walletFile);
        walletMgr = tstSdk.getWalletMgr();
        wallet = walletMgr.getWallet();
        tstIdTx = tstSdk.nativevm().tstId();
        payer = tstSdk.getWalletMgr().createAccount(password);

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
    public void openWallet() {
        tstSdk.openWalletFile("wallet.json");
        walletMgr = tstSdk.getWalletMgr();
        assertNotNull(walletMgr);
    }

    @Test
    public void getWallet() {
    }

    @Test
    public void writeWallet() throws Exception {
        walletMgr.writeWallet();
        File f = new File("wallet.json");
        boolean isExist = f.exists() && !f.isDirectory();
        assertTrue(isExist);
    }

    @Test
    public void createIdentity() throws Exception {
         Identity identity = walletMgr.createIdentity(password);
         com.github.TesraSupernet.account.Account account = walletMgr.getAccount(identity.tstid,password,identity.controls.get(0).getSalt());
         assertNotNull(account);
         assertNotNull(identity);
         assertNotNull(identity.tstid);
         assertNotEquals(identity.tstid,"");
    }

    @Test
    public void importIdentity() throws Exception {
        List<Identity> identities = wallet.getIdentities();
        identities.clear();
        walletMgr.writeWallet();
        assertEquals(identities.size(), 0);

        Identity identity = walletMgr.createIdentity(password);
        com.github.TesraSupernet.account.Account account = walletMgr.getAccount(identity.tstid,password,identity.controls.get(0).getSalt());
        String prikeyStr = account.exportGcmEncryptedPrikey(password,identity.controls.get(0).getSalt(),16384);
        assertTrue(identities.size() == 1);
        identities.clear();
        walletMgr.writeWallet();
        assertTrue(identities.size() == 0);

        String addr = identity.tstid.substring(8);
        walletMgr.importIdentity(prikeyStr,password,identity.controls.get(0).getSalt(),addr);
        assertTrue(identities.size() == 1);
        Identity identity1 = identities.get(0);
        assertEquals(identity.tstid,identity1.tstid);
    }

    @Test
    public void importAccount() throws Exception {
        List<Account> accounts = walletMgr.getWallet().getAccounts();
        accounts.clear();
        assertEquals(accounts.size(), 0);
        walletMgr.writeWallet();
        Account account = walletMgr.createAccount(password);
        com.github.TesraSupernet.account.Account accountDiff = walletMgr.getAccount(account.address,password,account.getSalt());
        String prikeyStr = accountDiff.exportGcmEncryptedPrikey(password,account.getSalt(),16384);
       assertTrue(accounts.size() == 1);
       accounts.clear();
       assertTrue(accounts.size() == 0);
       walletMgr.writeWallet();

       Account account1 = walletMgr.importAccount(prikeyStr,password,account.address,account.getSalt());
       assertTrue(accounts.size() == 1);
       assertEquals(account.address, account1.address);

    }
}