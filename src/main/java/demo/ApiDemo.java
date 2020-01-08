package demo;

import com.github.TesraSupernet.TstSdk;

/**
 * @Description:
 * @date 2019/12/12
 */
public class ApiDemo {

    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();
            if (true) {
                System.out.println(tstSdk.getConnect().getBalance("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe"));
                System.out.println(tstSdk.getConnect().getNodeSyncStatus());
                System.exit(0);
            }
        }catch (Exception ex){
        }
    }
    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://polaris1.ont.io";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRpc());
        wm.openWalletFile("wallet2.dat");

        return wm;
    }
}
