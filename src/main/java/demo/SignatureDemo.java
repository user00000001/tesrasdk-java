package demo;

import com.github.TesraSupernet.OntSdk;
import com.github.TesraSupernet.core.DataSignature;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;

public class SignatureDemo {
    public static void main(String[] args) {
        try {
            OntSdk ontSdk = getOntSdk();
            if(true) {
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(ontSdk.defaultSignScheme);
                byte[] data = "12345".getBytes();
                byte[] signature = ontSdk.signatureData(acct, data);

                System.out.println(ontSdk.verifySignature(acct.serializePublicKey(), data, signature));
            }
            if(true) {
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(ontSdk.defaultSignScheme);
                byte[] data = "12345".getBytes();
                DataSignature sign = new DataSignature(ontSdk.defaultSignScheme, acct, data);
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

    public static OntSdk getOntSdk() throws Exception {

        String ip = "http://127.0.0.1";
//        String ip = "http://54.222.182.88;
//        String ip = "http://101.132.193.149";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("AccountDemo.json");

        return wm;
    }
}
