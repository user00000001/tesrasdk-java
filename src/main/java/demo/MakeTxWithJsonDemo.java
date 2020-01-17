package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.payload.InvokeCode;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;

import java.util.*;

//
//
public class MakeTxWithJsonDemo {
    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();
            String str = "{\"action\":\"invoke\",\"params\":{\"login\":true,\"url\":\"http://127.0.0.1:80/rawtransaction/txhash\",\"message\":\"will pay 1 TSG in this transaction\",\"invokeConfig\":{\"contractHash\":\"16edbe366d1337eb510c2ff61099424c94aeef02\",\"functions\":[{\"operation\":\"method name\",\"args\":[{\"name\":\"arg0-list\",\"value\":[true,100,\"Long:100000000000\",\"Address:AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\",\"ByteArray:aabb\",\"String:hello\",[true,100],{\"key\":6}]},{\"name\":\"arg1-map\",\"value\":{\"key\":\"String:hello\",\"key1\":\"ByteArray:aabb\",\"key2\":\"Long:100000000000\",\"key3\":true,\"key4\":100,\"key5\":[100],\"key6\":{\"key\":6}}},{\"name\":\"arg2-str\",\"value\":\"String:test\"}]}],\"payer\":\"AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\",\"gasLimit\":20000,\"gasPrice\":500,\"signature\":{\"m\":1,\"signers\":[\"AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\"]}}}}";
            Transaction[] txs = tstSdk.makeTransactionByJson(str);
            if(true) {
                str = "{\n" +
                        "\t\"action\": \"invoke\",\n" +
                        "\t\"params\": {\n" +
                        "\t\t\"login\": true,\n" +
                        "\t\t\"url\": \"http://127.0.0.1:80/rawtransaction/txhash\",\n" +
                        "\t\t\"message\": \"will pay 1 TSG in this transaction\",\n" +
                        "\t\t\"invokeConfig\": {\n" +
                        "\t\t\t\"contractHash\": \"cd948340ffcf11d4f5494140c93885583110f3e9\",\n" +
                        "\t\t\t\"functions\": [{\n" +
                        "\t\t\t\t\"operation\": \"transferNativeAsset\",\n" +
                        "\t\t\t\t\"args\": [{\n" +
                        "\t\t\t\t\t\"name\": \"arg0\",\n" +
                        "\t\t\t\t\t\"value\": \"String:tst\"\n" +
                        "\t\t\t\t}, {\n" +
                        "\t\t\t\t\t\"name\": \"arg1\",\n" +
                        "\t\t\t\t\t\"value\": \"Address:AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\"\n" +
                        "\t\t\t\t}, {\n" +
                        "\t\t\t\t\t\"name\": \"arg2\",\n" +
                        "\t\t\t\t\t\"value\": \"Address:AecaeSEBkt5GcBCxwz1F41TvdjX3dnKBkJ\"\n" +
                        "\t\t\t\t}, {\n" +
                        "\t\t\t\t\t\"name\": \"arg3\",\n" +
                        "\t\t\t\t\t\"value\": 10\n" +
                        "\n" +
                        "\t\t\t\t}]\n" +
                        "\t\t\t}],\n" +
                        "\t\t\t\"payer\": \"AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\",\n" +
                        "\t\t\t\"gasLimit\": 20000,\n" +
                        "\t\t\t\"gasPrice\": 500,\n" +
                        "\t\t\t\"signature\": {\n" +
                        "\t\t\t\t\"m\": 1,\n" +
                        "\t\t\t\t\"signers\": [\"AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ\"]\n" +
                        "\t\t\t}\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "}";
                //System.out.println(str);
                com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b"), tstSdk.defaultSignScheme);
                Transaction[] txs1 = tstSdk.makeTransactionByJson(str);
                //System.out.println(tx.json());
                tstSdk.addSign(txs1[0], acct);
                Object obj = tstSdk.getConnect().sendRawTransactionPreExec(txs1[0].toHexString());
                System.out.println(obj);
                System.exit(0);
            }

            List paramList = new ArrayList<>();
            paramList.add("method name");

            List args2 = new ArrayList();
            Map map = new HashMap<>();
            Map map2 = new HashMap<>();
            map.put("key1",Helper.hexToBytes("aabb"));
            map.put("key2",100000000000L);
            map.put("key3",true);
            map.put("key4",100);
            map.put("key","hello".getBytes());
            List list0 = new ArrayList();
            list0.add(100);
            map.put("key5",list0);
            map2.put("key",6);
            map.put("key6",map2);
            List list = new ArrayList();
            List list2 = new ArrayList();
            list.add(true);
            list.add(100);
            list.add(100000000000L);
            list.add(Address.decodeBase58("AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ").toArray());
            list.add(Helper.hexToBytes("aabb"));
            list.add("hello".getBytes());
            list2.add(true);
            list2.add(100);
            list.add(list2);
            list.add(map2);
            args2.add(list);
            args2.add(map);
            args2.add("test".getBytes());

            paramList.add(args2);
            System.out.println("########make by self##############");
            System.out.println(paramList);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://127.0.0.1";
        ip = "http://dapp1.tesra.me";
        ip = "http://dapp2.tesra.me";
//        String ip = "http://dapp3.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRpc());

        wm.openWalletFile("wallet2.dat");

        return wm;
    }
}
