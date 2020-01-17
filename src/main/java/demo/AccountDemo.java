package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.crypto.Digest;
import com.github.TesraSupernet.crypto.ECC;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDemo {
    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();
            com.github.TesraSupernet.account.Account account = tstSdk.getWalletMgr().getAccount("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe","111111");

            if(false){
                tstSdk.nativevm().tsg().withdrawTsg(account,account.getAddressU160().toBase58(),53620575000000000L,account,20000,0);
                Thread.sleep(6000);
                System.out.println(tstSdk.getConnect().getBalance(account.getAddressU160().toBase58()));
                return;
            }

            if(true){
                System.out.println(tstSdk.getConnect().getBalance("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe"));

                tstSdk.nativevm().tst().sendTransfer(account,"APrfMuKrAQB5sSb5GF8tx96ickZQJjCvwG",10000,account,20000,0);
                Thread.sleep(6000);
                System.out.println(tstSdk.nativevm().tsg().unboundTsg(account.getAddressU160().toBase58()));

                return;
            }
            byte[] saltt = Base64.getDecoder().decode("0X3NC1UHQGltHc4ikzgzmA==");
            String prikeyg = com.github.TesraSupernet.account.Account.getGcmDecodedPrivateKey("7a1ccOWFQUGl0HQmc+PSLeKMwbVZ45/YDHTH/+um4O1z/YAWuv+vsr9zusvYXWbj", "1","ANH5bHrrt111XwNEnuPZj6u95Dd6u7G4D6",saltt,16384,tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account a = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(prikeyg),tstSdk.defaultSignScheme);
            System.out.println(Helper.toHexString(a.serializePrivateKey()));
            System.out.println(a.getAddressU160().toBase58());
            //com.github.TesraSupernet.account.Account b = new com.github.TesraSupernet.account.Account(false,a.serializePublicKey());

            //System.out.println(Helper.toHexString(b.serializePublicKey()));
            System.out.println( a.exportGcmEncryptedPrikey("1",saltt,16384));
            //            tstSdk.getWalletMgr().createAccount("password");
//            tstSdk.getWalletMgr().writeWallet();
            //tstSdk.getWalletMgr().getAccount("AUxEWKBM7zaU8iPSdymNSaZt7Dt9yB1KU6","1", Base64.getDecoder().decode("q6FCsP3XKxaeZaj15QZRqA=="));
           // tstSdk.getWalletMgr().getAccount("AHvSop5MbUX6pnqbXnFC5t3yjqVV5DiL7w","password", Base64.getDecoder().decode("ylsxIy8xq0uh4KjjbhxVLw=="));
          // tstSdk.getWalletMgr().getAccount("ANRoMGmxSLtWyzcDcnfCVnJw3FXdNuC9Vq","passwordtest", Base64.getDecoder().decode("ACm4B8Jr1oBPu++e7YIHow=="));
            System.exit(0);
            if(true){
                tstSdk.getWalletMgr().createAccount("1");
                System.exit(0);
            }

            byte[] salt0 = java.util.Base64.getDecoder().decode("+AX/Aa8VXp0h74PZySZ9RA==");
            String key0 = "+TDw5opWl5HfGEWUpxblVa5BqVKF2962DoCwi1GYidwWMKvOj7mqaUVx3k/utGLx";
            System.out.println(Helper.toHexString(salt0)+" "+salt0.length);
            System.out.println(Helper.toHexString(java.util.Base64.getDecoder().decode(key0)));
            String prikey0 = com.github.TesraSupernet.account.Account.getGcmDecodedPrivateKey(key0,"1","APrfMuKrAQB5sSb5GF8tx96ickZQJjCvwG", salt0,16384,tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct11 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(prikey0), tstSdk.defaultSignScheme);
            System.out.println(acct11.getAddressU160().toBase58());
           // System.exit(0);
            if (false){
                AccountInfo info0 = tstSdk.getWalletMgr().createAccountInfo("passwordtest");
                AccountInfo info = tstSdk.getWalletMgr().createAccountInfoFromPriKey("passwordtest","e467a2a9c9f56b012c71cf2270df42843a9d7ff181934068b4a62bcdd570e8be");
                System.out.println(info.addressBase58);
                Account accountInfo = tstSdk.getWalletMgr().importAccount("3JZLD/X45qSFjmRRvRVhcEjKgCJQDPWOsjx2dcTEj58=", "passwordtest",info.addressBase58,new byte[]{});

                com.github.TesraSupernet.account.Account acct0 = tstSdk.getWalletMgr().getAccount(info.addressBase58, "passwordtest",new byte[]{});
            }
            System.out.println();
            if(true){

                byte[] salt = salt0;
//                salt = ECC.generateKey(16);
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("a1a38ccff49fa6476e737d66ef9f18c7507b50eb4804ed8e077744a4a2a74bb6"),tstSdk.defaultSignScheme);
                String key = acct.exportGcmEncryptedPrikey("1",salt,16384);
                System.out.println(key);
                System.out.println(acct.getAddressU160().toBase58());
                String prikey = com.github.TesraSupernet.account.Account.getGcmDecodedPrivateKey(key, "1",acct.getAddressU160().toBase58(),salt,16384,tstSdk.defaultSignScheme);
                System.out.println(prikey);
            }

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://127.0.0.1";
//        String ip = "http://dapp1.tesra.me;
//        String ip = "http://dapp2.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("wallet2.dat");

        return wm;
    }
}
