package demo;

import com.github.TesraSupernet.TstSdk;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeTstIdDemo {

    public static void main(String[] args) {

        String password = "111111";

        try {
            TstSdk tstSdk = getTstSdk();

            Account payer = tstSdk.getWalletMgr().createAccount(password);

            com.github.TesraSupernet.account.Account payerAcct = tstSdk.getWalletMgr().getAccount(payer.address,password,tstSdk.getWalletMgr().getWallet().getAccount(payer.address).getSalt());
            String privatekey0 = "c19f16785b8f3543bbaf5e1dbb5d398dfa6c85aaad54fc9d71203ce83e505c07";
            String privatekey1 = "2ab720ff80fcdd31a769925476c26120a879e235182594fbb57b67c0743558d7";
            com.github.TesraSupernet.account.Account account1 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1),SignatureScheme.SHA256WITHECDSA);

            if(true){
                Account account = tstSdk.getWalletMgr().createAccount(password);
                com.github.TesraSupernet.account.Account account2 = tstSdk.getWalletMgr().getAccount(account.address,password,account.getSalt());
                Identity identity = tstSdk.getWalletMgr().createIdentityFromPriKey(password, Helper.toHexString(account2.serializePrivateKey()));
                tstSdk.nativevm().tstId().sendRegister(identity,password,account2,20000,0);
                Thread.sleep(6000);
                System.out.println(tstSdk.nativevm().tstId().sendGetDDO(identity.tstid));
                return;
            }


            if(false){
                Identity identity3 = tstSdk.getWalletMgr().createIdentity(password);
                Attribute[] attributes = new Attribute[1];
                attributes[0] = new Attribute("key1".getBytes(),"String".getBytes(),"value1".getBytes());
                tstSdk.nativevm().tstId().sendRegisterWithAttrs(identity3,password,attributes,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.getWalletMgr().writeWallet();
                Thread.sleep(6000);
                String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity3.tstid);
                System.out.println(ddo);
                System.exit(0);
            }
            if(true){
                if(tstSdk.getWalletMgr().getWallet().getIdentities().size() < 1){
//                    Identity identity = tstSdk.getWalletMgr().createIdentity(password);
                    Identity identity = tstSdk.getWalletMgr().createIdentityFromPriKey(password,privatekey0);
                    tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                    tstSdk.getWalletMgr().writeWallet();
                    Thread.sleep(6000);
                    return;
                }
                Identity identity = tstSdk.getWalletMgr().getWallet().getIdentities().get(0);
//                String ddo = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
//                System.out.println(ddo);

                Attribute[] attributes = new Attribute[1];
                attributes[0] = new Attribute("key1".getBytes(),"String".getBytes(),"value1".getBytes());
                byte[] salt = identity.controls.get(0).getSalt();
//                tstSdk.nativevm().tstId().sendAddAttributes(identity.tstid,password,identity.controls.get(0).getSalt(),attributes,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
//                tstSdk.nativevm().tstId().sendRemoveAttribute(identity.tstid,password,identity.controls.get(0).getSalt(),"key1",payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
//                tstSdk.nativevm().tstId().sendAddRecovery(identity.tstid,password,salt,account1.getAddressU160().toBase58(),payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
//                tstSdk.nativevm().tstId().sendAddPubKey(identity.tstid,password,salt,Helper.toHexString(account1.serializePublicKey()),payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.nativevm().tstId().sendRemovePubKey(identity.tstid,password,salt,Helper.toHexString(account1.serializePublicKey()),payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                Thread.sleep(6000);
                String ddo2 = tstSdk.nativevm().tstId().sendGetDDO(identity.tstid);
                System.out.println(ddo2);
                System.out.println(account1.getAddressU160().toBase58());
                System.exit(0);
            }
            Account account = tstSdk.getWalletMgr().createAccountFromPriKey(password,privatekey0);
            if(tstSdk.getWalletMgr().getWallet().getIdentities().size() < 3){
                Identity identity = tstSdk.getWalletMgr().createIdentity(password);
                Transaction tx = tstSdk.nativevm().tstId().makeRegister(identity.tstid,identity.controls.get(0).publicKey,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.signTx(tx,identity.tstid,password,new byte[]{});
                tstSdk.addSign(tx,payerAcct);
                tstSdk.getConnect().sendRawTransaction(tx);

                Identity identity2 = tstSdk.getWalletMgr().createIdentity(password);
                tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

                Identity identity3 = tstSdk.getWalletMgr().createIdentity(password);
                Attribute[] attributes = new Attribute[1];
                attributes[0] = new Attribute("key1".getBytes(),"String".getBytes(),"value1".getBytes());
                tstSdk.nativevm().tstId().sendRegisterWithAttrs(identity3,password,attributes,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.getWalletMgr().writeWallet();
                Thread.sleep(6000);

            }
            List<Identity> dids = tstSdk.getWalletMgr().getWallet().getIdentities();
            System.out.println("dids.get(0).tstid:" + dids.get(0).tstid);
//            System.out.println("dids.get(1).tstid:" + dids.get(1).tstid);
//            System.out.println("dids.get(2).tstid:" + dids.get(2).tstid);
            String ddo1 = tstSdk.nativevm().tstId().sendGetDDO(dids.get(0).tstid);
//            String publicKeys = tstSdk.nativevm().tstId().sendGetPublicKeys(dids.get(0).tstid);
//            String ddo2 = tstSdk.nativevm().tstId().sendGetDDO(dids.get(1).tstid);
//            String ddo3 = tstSdk.nativevm().tstId().sendGetDDO(dids.get(2).tstid);

            System.out.println("ddo1:" + ddo1);
//            System.out.println("ddo2:" + ddo2);
//            System.out.println("ddo3:" + ddo3);

            IdentityInfo info2 = tstSdk.getWalletMgr().getIdentityInfo(dids.get(1).tstid,password,new byte[]{});
            IdentityInfo info3 = tstSdk.getWalletMgr().getIdentityInfo(dids.get(2).tstid,password,new byte[]{});

            com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0),SignatureScheme.SHA256WITHECDSA);
            com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1),SignatureScheme.SHA256WITHECDSA);
            Address multiAddr = Address.addressFromMultiPubKeys(2,acct.serializePublicKey(),acct2.serializePublicKey());

            if(false){
                Account account2 = tstSdk.getWalletMgr().createAccountFromPriKey(password, privatekey1);
//                tstSdk.nativevm().tstId().sendChangeRecovery(dids.get(0).tstid,account2.address,account.address,password,tstSdk.DEFAULT_GAS_LIMIT,0);
                String txhash2 = tstSdk.nativevm().tstId().sendAddRecovery(dids.get(0).tstid,password,new byte[]{},multiAddr.toBase58(),payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                Thread.sleep(6000);
                Object obj = tstSdk.getConnect().getSmartCodeEvent(txhash2);
                System.out.println(obj);
                System.out.println(tstSdk.nativevm().tstId().sendGetDDO(dids.get(0).tstid));
            }

            if(false){
                tstSdk.nativevm().tstId().sendAddPubKey(dids.get(0).tstid,password,new byte[]{},info3.pubkey,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.nativevm().tstId().sendRemovePubKey(dids.get(0).tstid,account.address,password,new byte[]{},info2.pubkey,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                tstSdk.nativevm().tstId().sendAddPubKey(dids.get(0).tstid,account.address,password,new byte[]{},info2.pubkey,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                Transaction tx = tstSdk.nativevm().tstId().makeAddPubKey(dids.get(0).tstid,multiAddr.toBase58(),null,info2.pubkey,payer.address,tstSdk.DEFAULT_GAS_LIMIT,0);
    //          tstSdk.signTx(tx,new com.github.TesraSupernet.account.Account[][]{{acct,acct2}});
    //          tstSdk.addSign(tx,payerAcc.address,password);
    //          tstSdk.getConnect().sendRawTransaction(tx.toHexString());
            }


            if(false){
                String ddo4 = tstSdk.nativevm().tstId().sendGetDDO(dids.get(0).tstid);
                System.out.println("ddo4:" + ddo4);
                System.exit(0);
                System.out.println("ddo1:" + ddo1);
                System.out.println("publicKeysState:" + tstSdk.nativevm().tstId().sendGetKeyState(dids.get(0).tstid,1));
                System.out.println("attributes:" + tstSdk.nativevm().tstId().sendGetAttributes(dids.get(0).tstid));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static TstSdk getTstSdk() throws Exception {
        String ip = "http://127.0.0.1";
//        String ip = "http://dapp1.tesra.me";
//        String ip = "http://dapp2.tesra.me";
//        String ip = "http://dapp3.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("NativeTstIdDemo.json");
        return wm;
    }
}
