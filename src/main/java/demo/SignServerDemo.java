package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.crypto.SignatureScheme;

import java.util.Map;

public class SignServerDemo {
    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();
//            String txHex = "00d1f8d253d500000000000000003075000000000000c6aa6cf361b3470ac4ee8350b821644bf1aeaec47600c66b14c6aa6cf361b3470ac4ee8350b821644bf1aeaec46a7cc81478e7342fe3823d37be1c890b6fca1213bc53f0536a7cc80864000000000000006a7cc86c07617070726f766514ff000000000000000000000000000000000000010068164f6e746f6c6f67792e4e61746976652e496e766f6b650000";
//            Map map = (Map)tstSdk.getSignServer().sendSigRawTx(txHex);
//            System.out.println(map.get("signed_tx"));
//            String[] signs = new String[]{"1202039b196d5ed74a4d771ade78752734957346597b31384c3047c1946ce96211c2a7",
//                    "120203428daa06375b8dd40a5fc249f1d8032e578b5ebb5c62368fc6c5206d8798a966"};
//            tstSdk.getSignServer().sendMultiSigRawTx(txHex,2,signs);
            String privateKey = "75de8489fcb2dcaf2ef3cd607feffde18789de7da129b5e97c81e001793cb7cf";
            String privateKey2 = "ca53fa4f53ed175e39da86f4e02cd87638652cdbdcdae594c81d2e2f2f673745";
            Account account = new Account(Helper.hexToBytes(privateKey),SignatureScheme.SHA256WITHECDSA);
            Account account2 = new Account(Helper.hexToBytes(privateKey2),SignatureScheme.SHA256WITHECDSA);
            System.out.println("account:" +  tstSdk.getConnect().getBalance(account.getAddressU160().toBase58()));
            System.out.println("account2:" +  tstSdk.getConnect().getBalance(account2.getAddressU160().toBase58()));
            tstSdk.getSignServer().sendSigTransferTx("tst",account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(),10,30000,0);
            Thread.sleep(6000);
            System.out.println("account:" +  tstSdk.getConnect().getBalance(account.getAddressU160().toBase58()));
            System.out.println("account2:" +  tstSdk.getConnect().getBalance(account2.getAddressU160().toBase58()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://52.184.29.110";
        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.6";
        String url = ip + ":" + "20000/cli";
        TstSdk wm = TstSdk.getInstance();
        wm.setSignServer(url);
        wm.setRpc("http://127.0.0.1:25768");
        return wm;
    }
}
