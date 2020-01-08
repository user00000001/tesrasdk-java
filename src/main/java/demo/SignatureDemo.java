package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.core.DataSignature;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;

public class SignatureDemo {
    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();
            if(true) {
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(tstSdk.defaultSignScheme);
                byte[] data = "12345".getBytes();
                byte[] signature = tstSdk.signatureData(acct, data);

                System.out.println(tstSdk.verifySignature(acct.serializePublicKey(), data, signature));
            }
            if(true) {
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(tstSdk.defaultSignScheme);
                byte[] data = "12345".getBytes();
                DataSignature sign = new DataSignature(tstSdk.defaultSignScheme, acct, data);
                byte[] signature = sign.signature();


                com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(false,acct.serializePublicKey());
                DataSignature sign2 = new DataSignature();
                System.out.println(sign2.verifySignature(acct2, data, signature));
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.46;
//        String ip = "http://52.229.166.6";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("AccountDemo.json");

        return wm;
    }
}
