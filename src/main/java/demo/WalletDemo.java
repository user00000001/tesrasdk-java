package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;

public class WalletDemo {
    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();
            if (tstSdk.getWalletMgr().getWallet().getAccounts().size() > 0) {
                tstSdk.getWalletMgr().getWallet().clearAccount();
                tstSdk.getWalletMgr().getWallet().clearIdentity();
                tstSdk.getWalletMgr().writeWallet();
            }
            tstSdk.getWalletMgr().createAccounts(1, "passwordtest");
            tstSdk.getWalletMgr().writeWallet();

            System.out.println("init size: "+tstSdk.getWalletMgr().getWallet().getAccounts().size()+" " +tstSdk.getWalletMgr().getWalletFile().getAccounts().size());
            System.out.println(tstSdk.getWalletMgr().getWallet().toString());
            System.out.println(tstSdk.getWalletMgr().getWalletFile().toString());

            System.out.println();
            tstSdk.getWalletMgr().getWallet().removeAccount(tstSdk.getWalletMgr().getWallet().getAccounts().get(0).address);
            tstSdk.getWalletMgr().getWallet().setVersion("2.0");
            System.out.println("removeAccount size: "+tstSdk.getWalletMgr().getWallet().getAccounts().size()+" " +tstSdk.getWalletMgr().getWalletFile().getAccounts().size());
            System.out.println(tstSdk.getWalletMgr().getWallet().toString());
            System.out.println(tstSdk.getWalletMgr().getWalletFile().toString());

            System.out.println();
            tstSdk.getWalletMgr().resetWallet();
            System.out.println("resetWallet size: "+tstSdk.getWalletMgr().getWallet().getAccounts().size()+" " +tstSdk.getWalletMgr().getWalletFile().getAccounts().size());
            System.out.println(tstSdk.getWalletMgr().getWallet().toString());
            System.out.println(tstSdk.getWalletMgr().getWalletFile().toString());


            System.out.println();
            tstSdk.getWalletMgr().getWallet().removeAccount(tstSdk.getWalletMgr().getWallet().getAccounts().get(0).address);
            tstSdk.getWalletMgr().getWallet().setVersion("2.0");
            System.out.println("removeAccount size: "+tstSdk.getWalletMgr().getWallet().getAccounts().size()+" " +tstSdk.getWalletMgr().getWalletFile().getAccounts().size());
            System.out.println(tstSdk.getWalletMgr().getWallet().toString());
            System.out.println(tstSdk.getWalletMgr().getWalletFile().toString());

            //write wallet
            tstSdk.getWalletMgr().writeWallet();
            System.out.println();
            System.out.println("writeWallet size: "+tstSdk.getWalletMgr().getWallet().getAccounts().size()+" " +tstSdk.getWalletMgr().getWalletFile().getAccounts().size());
            System.out.println(tstSdk.getWalletMgr().getWallet().toString());
            System.out.println(tstSdk.getWalletMgr().getWalletFile().toString());
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

        wm.openWalletFile("WalletDemo.json");

        return wm;
    }
}
