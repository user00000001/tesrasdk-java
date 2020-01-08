package demo;

import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.sidechaingovernance.*;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.wallet.Identity;
import com.github.TesraSupernet.smartcontract.nativevm.SideChainGovernance;

import java.util.Base64;

public class SideChainGovernanceDemo {
    public static void main(String[] args) throws Exception {
        TstSdk sdk = TstSdk.getInstance();
        sdk.openWalletFile("wallet2.dat");
        sdk.setRpc("http://52.184.29.110:25768");
        SideChainGovernance sideChainGovernance = new SideChainGovernance(sdk);
        String password = "111111";
        Account account = sdk.getWalletMgr().getAccount("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe",password);
        Identity identity = sdk.getWalletMgr().getWallet().getIdentity("did:tst:Abrc5byDEZm1CnQb3XjAosEt34DD4w5Z1o");
        String sideChainContractAddr = "0000000000000000000000000000000000000008";
        if(false){
            System.out.println(sideChainGovernance.getSideChain("123456"));
//            System.out.println(sdk.getConnect().getBalance(account.getAddressU160().toBase58()));
            return;
        }
        if(true){
            System.out.println(sdk.getConnect().getBalance(account.getAddressU160().toBase58()));
            return;
        }

        //梦航
//        Account adminTstIdAcct = getAccount("cCQnie0Dd8aQPyY+9UBFw2x2cLn2RMogKqhM8OkyjJNrNTvlcVaYGRENfy2ErF7Q","passwordtest","ARiwjLzjzLKZy8V43vm6yUcRG9b56DnZtY","3e1zvaLjtVuPrQ1o7oJsQA==");
//        String adminPrivateKey =Helper.toHexString(adminTstIdAcct.serializePrivateKey());
        Identity adminIndentity = sdk.getWalletMgr().getWallet().getIdentity("did:tst:ARiwjLzjzLKZy8V43vm6yUcRG9b56DnZtY");
        //梦航
        Account account1 = getAccount("wR9S/JYwMDfCPWFGEy5DEvWfU14k9suZuL4+woGtfhZJf5+KyL9VJqMi/wGTOd1i","passwordtest","AZqk4i7Zhfhc1CRUtZYKrLw4YTSq4Y9khN","ZaIL8DxNaQ91fkMHAdiBjQ==");
        Account account2 = getAccount("PCj/a4zUgYnOBNZUVEaXBK61Sq4due8w2RUzrumO3Bm0hZ/3v4mlDiXYYvmmBZUk","passwordtest","ARpjnrnHEjXhg4aw7vY6xsY6CfQ1XEWzWC","wlz1h439j0GwsWhGBByMxg==");
        Account account3 = getAccount("4U6qYhRUxGYTcvDvBKKCu2C1xUyd0A+pHXsK1YVY1Hbxd8TcbyvmfOcqx7N+f+BH","passwordtest","AQs2BmzzFVk7pQPfTQQi9CTEz43ejSyBnt","AFDFoZAlLGJdB4yVQqYVhw==");
        Account account4 = getAccount("i6n+FTACzRF5y0oeo6Wm3Zbv68bfjmyRyNfKB5IArK76RCG8b/JgRqnHgMtHixFx","passwordtest","AKBSRLbFNvUrWEGtKxNTpe2ZdkepQjYKfM","FkTZ6czRPAqHnSpEqVEWwA==");
        Account account5 = getAccount("IoEbJXMPlxNLrAsDYKGD4I6oFYgJl1j603c8oHQl+82yET+ibKgJdZjgdw39pr2K","passwordtest","AduX7odaWGipkdvzBwyaTgsumRbRzhhiwe","lc7ofKCBkNUmjTLrZYmStA==");
        Account account6 = getAccount("6hynBJVTAhmMJt9bIYSDoz+GL5EFaUGhn3Pd6HsF+RQ1tFyZoFRhT+JNMGAb+B6a","passwordtest","ANFfWhk3A5iFXQrVBHKrerjDDapYmLo5Bi","DTmbW9wzGA8pi4Dcj3/Cpg==");
        Account account7 = getAccount("EyXxszzKh09jszQXMIFJTmbujnojOzYzPU4cC0wOpuegDgVcRFllATQ81zD0Rp8s","passwordtest","AK3YRcRvKrASQ6nTfW48Z4iMZ2sDTDRiMC","jbwUF7JxgsiJq5QAy5dfug==");
        Address multiAddress = Address.addressFromMultiPubKeys(5,account1.serializePublicKey(),account2.serializePublicKey(),account3.serializePublicKey(),account4.serializePublicKey(),account5.serializePublicKey(),account6.serializePublicKey(),account7.serializePublicKey());
        Account[] accounts = new Account[]{account1,account2,account3,account4,account5,account6,account7};
        byte[][] pks = new byte[accounts.length][];
        for(int i=0;i<pks.length;i++){
            pks[i] = accounts[i].serializePublicKey();
        }


        if(false){
            sdk.nativevm().tsg().sendTransfer(account1,account.getAddressU160().toBase58(),10000*1000000000L,account,20000,0);

            return;
        }
        if(false){
            String txhash1 = sdk.nativevm().tstId().sendRegister(identity,password,account,20000,0);
            String txhash2 = sdk.nativevm().tstId().sendRegister(adminIndentity,password,account,20000,0);
            Thread.sleep(6000);

            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash1));
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash2));

//            Transaction tx = sdk.nativevm().tst().makeTransfer(multiAddress.toBase58(),"AMAx993nE6NEqZjwBssUfopxnnvTdob9ij",10000, account.getAddressU160().toBase58(),200000,0);
////            Transaction tx = sdk.nativevm().tsg().makeWithdrawTsg(multiAddress.toBase58(),"AMAx993nE6NEqZjwBssUfopxnnvTdob9ij",35478934750000L,account.getAddressU160().toBase58(),20000,0);
//            for(int i=0;i<5;i++){
//                sdk.addMultiSign(tx, 5, pks, accounts[i]);
//            }
//            sdk.addSign(tx, account);
//            sdk.getConnect().sendRawTransaction(tx.toHexString());
//            System.out.println(tx.hash().toHexString());
//
//
//            Thread.sleep(3000);
            System.out.println(sdk.nativevm().tsg().unboundTsg(multiAddress.toBase58()));
            System.out.println(sdk.getConnect().getBalance(multiAddress.toBase58()));
            System.out.println(sdk.getConnect().getBalance(account.getAddressU160().toBase58()));
            return;
        }
        if(false){
            String txhash = sdk.nativevm().auth().assignFuncsToRole(adminIndentity.tstid,password,adminIndentity.controls.get(0).getSalt(),1,"0000000000000000000000000000000000000007","role",new String[]{"registerSideChain"},account,20000,0);
//            String txhash = sdk.nativevm().auth().assignTstIdsToRole(adminIndentity.tstid,password,adminIndentity.controls.get(0).getSalt(),1,
//                    sideChainContractAddr,"role",new String[]{identity.tstid},account,20000,0);

            Thread.sleep(3000);
            System.out.println(txhash);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(false){
//            String txhash = sdk.nativevm().auth().assignFuncsToRole(adminIndentity.tstid,password,adminIndentity.controls.get(0).getSalt(),1,sideChainContractAddr,"role",new String[]{"registerSideChain"},account,20000,0);
            String txhash = sdk.nativevm().auth().assignTstIdsToRole(adminIndentity.tstid,password,adminIndentity.controls.get(0).getSalt(),1,
                    sideChainContractAddr,"role",new String[]{identity.tstid},account,20000,0);

            Thread.sleep(3000);
            System.out.println(txhash);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(false){
            String res = sdk.nativevm().auth().verifyToken(identity.tstid,password,identity.controls.get(0).getSalt(),1,sideChainContractAddr,
                    "registerSideChain");
            System.out.println(res);
            return;
        }
        if(false){
//success
            RegisterSideChainParam param = new RegisterSideChainParam("123456", account.getAddressU160(),1,(long)100*1000000000,(long)100*1000000000,identity.tstid.getBytes(),1);
            String txhash = sideChainGovernance.registerSideChain(account,param, identity,password, account,20000,0);
            System.out.println("txhash: " + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            System.out.println(sideChainGovernance.getSideChain("123456"));
            return;
        }
        if(false){
//            success
            System.out.println(sideChainGovernance.getSideChain("12345678"));
//            String txhash = sideChainGovernance.rejectSideChain(accounts, pks,5,"12345678",account,20000,0);
            String txhash = sideChainGovernance.approveSideChain(accounts, pks,5,"123456",account,20000,0);
            System.out.println(txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            System.out.println(sideChainGovernance.getSideChain("12345678"));
            return;
        }

        if(false){
//            success
            InflationParam param = new InflationParam("123456",account.getAddressU160(),(long)1000*1000000000,(long)1000*1000000000);
            String txhash = sideChainGovernance.inflation(account,param,account,20000,0);
            System.out.println(txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(false){
//            success
//            String txhash = sideChainGovernance.rejectInflation(accounts,pks,5,"12345678",account,20000,0);
            String txhash = sideChainGovernance.approveInflation(accounts, pks,5,"12345678",account,20000,0);
            System.out.println(txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }

        if(true){
//            success
            SwapParam param = new SwapParam("123456",account.getAddressU160(), 100*1000000000L);
            String txhash = sideChainGovernance.tsgSwap(account,param,account,20000,0);
            System.out.println("txhash:" + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getBlockHeight());
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(true){
//            success
            String txhash = sideChainGovernance.setGlobalParams(accounts,pks,5,account.getAddressU160(),account,20000,0);
            System.out.println(txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(false){
//            success
            SwapParam param = new SwapParam("12345678",account.getAddressU160(), 1000);
            String txhash = sideChainGovernance.tsgxSwap(account,new SwapParam[]{param},account,20000,0);
            System.out.println(txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        String privatekey = Account.getGcmDecodedPrivateKey("gSiSguflRJN5bItiP4Jo0zZJRhj3bbO9Pj1gSAztLKAfnB6bZ5ohqpo6JZuzV70m","passwordtest","AZonXUcUgzWb2KYdSiLapgqCMEfWGCDTw5",Base64.getDecoder().decode("ZAIkGt7qn7drlGAZ20MVQw=="),16384,SignatureScheme.SHA256WITHECDSA);
        Account account8 = new Account(Helper.hexToBytes(privatekey),SignatureScheme.SHA256WITHECDSA);
        if(false){
//            success
            SideChainNodeInfo info = sideChainGovernance.getSideChainNodeInfo("123456");
            System.out.println(JSON.toJSONString(info));

            NodeToSideChainParams params = new NodeToSideChainParams("03acea758e49b87a03b3dbf29e3055857ce7a4673ea864e640ed8f13d43861da41",account1.getAddressU160(),"123456");
            String txhash = sideChainGovernance.registerNodeToSideChain(account1,params,account1,20000,0);
//            String txhash = sideChainGovernance.quitNodeToSideChain(account1, params,account,20000,0);
//            Thread.sleep(6000);
//            QuitSideChainParam param = new QuitSideChainParam("123456",account8.getAddressU160());
//            String txhash = sideChainGovernance.approveQuitSideChain(accounts,pks,5,param,account,20000,0);
            System.out.println("txhash:" + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            System.out.println(JSON.toJSONString(sideChainGovernance.getSideChainNodeInfo("123456")));
        }

        if(true){
            System.out.println(sideChainGovernance.getSideChain("123456"));
        }
    }

    public static Account getAccount(String enpri,String password,String address,String salt) throws Exception {
        String privateKey = Account.getGcmDecodedPrivateKey(enpri,password,address,Base64.getDecoder().decode(salt),16384,SignatureScheme.SHA256WITHECDSA);
        Account account = new Account(Helper.hexToBytes(privateKey),SignatureScheme.SHA256WITHECDSA);
//        System.out.println(Helper.toHexString(account.serializePublicKey()));
        return account;
    }
}
