package com.github.TesraSupernet.smartcontract.nativevm;

import com.alibaba.fastjson.JSONObject;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.TstSdkTest;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Common;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.tstid.Attribute;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.info.IdentityInfo;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NativeTstIdTxTest {
    TstSdk tstSdk;
    String password = "111111";
    Account payer;
    com.github.TesraSupernet.account.Account payerAcct;
    Identity identity;
    String walletFile = "NativeTstIdTxTest.json";
    @Before
    public void setUp() throws Exception {
        tstSdk = TstSdk.getInstance();
        tstSdk.setRestful(TstSdkTest.URL);
        tstSdk.setDefaultConnect(tstSdk.getRestful());
        tstSdk.openWalletFile(walletFile);
//        tstSdk.setSignatureScheme(SignatureScheme.SHA256WITHECDSA);
        payer = tstSdk.getWalletMgr().createAccount(password);
        payerAcct = tstSdk.getWalletMgr().getAccount(payer.address,password,payer.getSalt());
        identity = tstSdk.getWalletMgr().createIdentity(password);
        tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);

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
    public void sendRegister() throws Exception {
        Transaction tx = tstSdk.nativevm().tstId().makeRegister(identity.tstid,identity.controls.get(0).publicKey,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx, identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx);

        Identity identity2 = tstSdk.getWalletMgr().createIdentity(password);
        tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Identity identity3 = tstSdk.getWalletMgr().createIdentity(password);
        Attribute[] attributes = new Attribute[1];
        attributes[0] = new Attribute("key2".getBytes(),"value2".getBytes(),"type2".getBytes());
        tstSdk.nativevm().tstId().sendRegisterWithAttrs(identity3,password,attributes,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Thread.sleep(6000);
        String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertTrue(ddo.contains(identity.tstid));

        String dd02 = tstSdk.nativevm().tstId().sendGetDDO(identity3.tstid);
        Assert.assertTrue(dd02.contains("key2"));

        String keystate = tstSdk.nativevm().tstId().sendGetKeyState(identity.tstid,1);
        Assert.assertNotNull(keystate);

        //merkleproof
        Object merkleproof = tstSdk.nativevm().tstId().getMerkleProof(tx.hash().toHexString());
        boolean b = tstSdk.nativevm().tstId().verifyMerkleProof(JSONObject.toJSONString(merkleproof));
        Assert.assertTrue(b);

        //claim
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Issuer", identity.tstid);
        map.put("Subject", identity2.tstid);

        Map clmRevMap = new HashMap();
        clmRevMap.put("typ","AttestContract");
        clmRevMap.put("addr",identity.tstid.replace(Common.didont,""));

        String claim = tstSdk.nativevm().tstId().createTstIdClaim(identity.tstid,password,identity.controls.get(0).getSalt(), "claim:context", map, map,clmRevMap,System.currentTimeMillis()/1000 +100000);
        boolean b2 = tstSdk.nativevm().tstId().verifyTstIdClaim(claim);
        Assert.assertTrue(b2);
    }
    @Test
    public void sendAddPubkey() throws Exception {
        IdentityInfo info = tstSdk.getWalletMgr().createIdentityInfo(password);
        IdentityInfo info2 = tstSdk.getWalletMgr().createIdentityInfo(password);
        Transaction tx = tstSdk.nativevm().tstId().makeAddPubKey(identity.tstid,password,identity.controls.get(0).getSalt(),info.pubkey,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx, identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx);

        tstSdk.nativevm().tstId().sendAddPubKey(identity.tstid,password,identity.controls.get(0).getSalt(),info2.pubkey,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Thread.sleep(6000);
        String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertTrue(ddo.contains(info.pubkey));
        Assert.assertTrue(ddo.contains(info2.pubkey));

        String publikeys = tstSdk.nativevm().tstId().sendGetPublicKeys(identity.tstid);
        Assert.assertNotNull(publikeys);

        Transaction tx2 = tstSdk.nativevm().tstId().makeRemovePubKey(identity.tstid,password,identity.controls.get(0).getSalt(),info.pubkey,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx2,identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx2,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx2);

        tstSdk.nativevm().tstId().sendRemovePubKey(identity.tstid,password,identity.controls.get(0).getSalt(),info2.pubkey,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);
        String ddo3 = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertFalse(ddo3.contains(info.pubkey));
        Assert.assertFalse(ddo3.contains(info2.pubkey));
    }

    @Test
    public void sendAddAttributes() throws Exception {
        Attribute[] attributes = new Attribute[1];
        attributes[0] = new Attribute("key1".getBytes(),"value1".getBytes(),"String".getBytes());
        Transaction tx = tstSdk.nativevm().tstId().makeAddAttributes(identity.tstid,password,identity.controls.get(0).getSalt(),attributes,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx, identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx);

        Attribute[] attributes2 = new Attribute[1];
        attributes2[0] = new Attribute("key99".getBytes(),"value99".getBytes(),"String".getBytes());
        tstSdk.nativevm().tstId().sendAddAttributes(identity.tstid,password,identity.controls.get(0).getSalt(),attributes2,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Thread.sleep(6000);
        String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertTrue(ddo.contains("key1"));
        Assert.assertTrue(ddo.contains("key99"));

        String attribute = tstSdk.nativevm().tstId().sendGetAttributes(identity.tstid);
        Assert.assertTrue(attribute.contains("key1"));

        Transaction tx2= tstSdk.nativevm().tstId().makeRemoveAttribute(identity.tstid,password,identity.controls.get(0).getSalt(),"key1",payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx2,identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx2,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx2);

        tstSdk.nativevm().tstId().sendRemoveAttribute(identity.tstid,password,identity.controls.get(0).getSalt(),"key99",payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);

        String ddo2 = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertFalse(ddo2.contains("key1"));
        Assert.assertFalse(ddo2.contains("key99"));

    }

    @Test
    public void sendAddRecovery() throws Exception {
        Identity identity = tstSdk.getWalletMgr().createIdentity(password);
        tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Identity identity2 = tstSdk.getWalletMgr().createIdentity(password);
        tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Thread.sleep(6000);

        Account account = tstSdk.getWalletMgr().createAccount(password);

        Transaction tx = tstSdk.nativevm().tstId().makeAddRecovery(identity.tstid,password,identity.controls.get(0).getSalt(),account.address,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx, identity.tstid,password,identity.controls.get(0).getSalt());
        tstSdk.addSign(tx,payerAcct);
        tstSdk.getConnect().sendRawTransaction(tx);

        tstSdk.nativevm().tstId().sendAddRecovery(identity2.tstid,password,identity2.controls.get(0).getSalt(),account.address,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

        Thread.sleep(6000);
        String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertTrue(ddo.contains(account.address));
        String ddo2 = tstSdk.nativevm().tstId().sendGetDDO(identity2.tstid);
        Assert.assertTrue(ddo2.contains(account.address));

        AccountInfo info2 = tstSdk.getWalletMgr().createAccountInfo(password);

        Transaction tx2 = tstSdk.nativevm().tstId().makeChangeRecovery(identity.tstid,info2.addressBase58,account.address,password,payerAcct.getAddressU160().toBase58(),tstSdk.DEFAULT_GAS_LIMIT,0);
        tstSdk.signTx(tx2,account.address,password,account.getSalt());

        tstSdk.nativevm().tstId().sendChangeRecovery(identity2.tstid,info2.addressBase58,account.address,password,account.getSalt(),payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
        Thread.sleep(6000);

        String ddo3 = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
        Assert.assertTrue(ddo3.contains(account.address));
        String ddo4 = tstSdk.nativevm().tstId().sendGetDDO(identity2.tstid);
        Assert.assertTrue(ddo4.contains(info2.addressBase58));
    }
}