package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.sdk.wallet.Identity;

public class RecordTxDemo {


    public static void main(String[] args){
        try {
            TstSdk tstSdk = getTstSdk();

            if(tstSdk.getWalletMgr().getWallet().getIdentities().size() < 1) {

                tstSdk.getWalletMgr().createIdentity("passwordtest");
                tstSdk.getWalletMgr().writeWallet();
            }


            Identity id = tstSdk.getWalletMgr().getWallet().getIdentities().get(0);

            String hash = tstSdk.neovm().record().sendPut(id.tstid,"passwordtest",new byte[]{},"key","value-test",0,0);
            System.out.println(hash);
            Thread.sleep(6000);
            String res = tstSdk.neovm().record().sendGet(id.tstid,"passwordtest",new byte[]{},"key");
            System.out.println("result:"+res);

            //System.out.println(tstSdk.getConnectMgr().getSmartCodeEvent(hash));

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

        wm.openWalletFile("RecordTxDemo.json");

        wm.neovm().record().setContractAddress("80f6bff7645a84298a1a52aa3745f84dba6615cf");
        return wm;
    }
}
