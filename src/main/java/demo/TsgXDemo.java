package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.sidechain.smartcontract.tsgx.Swap;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.manager.ConnectMgr;
import com.github.TesraSupernet.sdk.wallet.Identity;
import com.github.TesraSupernet.sidechain.smartcontract.tsgx.TsgX;

import java.util.Base64;

public class TsgXDemo {

    public static void main(String[] args) throws Exception {
//        String mainChainUrl = "http://dapp1.tesra.me:25768";
        String sideChainUrl = "http://23.99.137.227:30336";
        TstSdk sdk = TstSdk.getInstance();
        sdk.openWalletFile("tsgx.dat");
        sdk.setSideChainRpc(sideChainUrl);
//        sdk.setRpc("http://dapp2.tesra.me:25768");
//        sdk.setRpc("http://127.0.0.1:25768");
//        sdk.setRpc(sideChainUrl);

        String password = "111111";
        Account account = sdk.getWalletMgr().getAccount("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe",password);
        Identity identity = sdk.getWalletMgr().getWallet().getIdentity("did:tst:Abrc5byDEZm1CnQb3XjAosEt34DD4w5Z1o");
        String sideChainContractAddr = "0000000000000000000000000000000000000008";
//        tsgX.setRpcUrl(sideChainUrl, "rpc");
        if(false){
            System.out.println(sdk.sidechainVm().tsgX().queryBalanceOf(account.getAddressU160().toBase58()));
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
        Account[] accounts1 = new Account[]{account1,account2,account3,account4,account5};
        byte[][] pks = new byte[accounts.length][];
        for(int i=0;i<pks.length;i++){
            pks[i] = accounts[i].serializePublicKey();
        }

        if(false){
//            String txhash = tsgX.tsgxSetSyncAddr(accounts1,pks,5,account.getAddressU160().toBase58(),account,20000,0);
            String txhash = sdk.sidechainVm().tsgX().tsgxSetSyncAddr(account,account.getAddressU160().toBase58(),account,20000,0);
            System.out.println("txhash: " + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
            return;
        }
        if(false){
            System.out.println(sdk.sidechainVm().tsgX().queryBalanceOf(account.getAddressU160().toBase58()));
            return;
        }
        if(false){
//            sdk.setRpc(mainChainUrl);
//            System.out.println(sdk.getConnect().getBlockHeight());
            sdk.setRpc(sideChainUrl);
            System.out.println(sdk.getSideChainConnectMgr().getBlockHeight());
//            return;
        }
        if(false){
            System.out.println(sdk.sidechainVm().tsgX().queryBalanceOf(account.getAddressU160().toBase58()));
            return;
        }
        if(true){
            Swap swap = new Swap(account.getAddressU160(),100);
            String txhash = sdk.sidechainVm().tsgX().tsgxSwap(account, swap,account,20000,0);
            System.out.println("txhash: " + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getSideChainConnectMgr().getSmartCodeEvent(txhash));
            System.out.println(sdk.sidechainVm().tsgX().queryBalanceOf(account.getAddressU160().toBase58()));
            return;
        }
        if(false){
            Swap swap = new Swap(account.getAddressU160(),(long)1000*1000000000);
            String txhash = sdk.sidechainVm().tsgX().tsgSwap(account,new Swap[]{swap},account,20000,0);
            System.out.println("txhash: " + txhash);
            Thread.sleep(6000);
            System.out.println(sdk.getSideChainConnectMgr().getSmartCodeEvent(txhash));
        }
        if(false){
//            子链启动的时候调用，
            String txhash = sdk.sidechainVm().tsgX().tsgxSetSyncAddr(accounts,pks,5,account.getAddressU160().toBase58(),account1,20000,0);
            Thread.sleep(6000);
            System.out.println(sdk.getSideChainConnectMgr().getSmartCodeEvent(txhash));
            return;
        }

    }

    public static Account getAccount(String enpri,String password,String address,String salt) throws Exception {
        String privateKey = Account.getGcmDecodedPrivateKey(enpri,password,address, Base64.getDecoder().decode(salt),16384, SignatureScheme.SHA256WITHECDSA);
        Account account = new Account(Helper.hexToBytes(privateKey),SignatureScheme.SHA256WITHECDSA);
//        System.out.println(Helper.toHexString(account.serializePublicKey()));
        return account;
    }
}
