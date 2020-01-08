package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.smartcontract.neovm.oep5.Oep5Param;

public class Oep5Demo {
    public static void main(String[] args) {
        try {
            TstSdk sdk = getTstSdk();
            Account account = sdk.getWalletMgr().getAccount("AQf4Mzu1YJrhz9f3aRkkwSm9n3qhXGSh4p", "xinhao");
            Account account1 = sdk.getWalletMgr().getAccount("ARR5ywDEx3ybXkMGmZPFYu9hiC8J4xvNdc", "xinhao");
            Account account2 = sdk.getWalletMgr().getAccount("AacHGsQVbTtbvSWkqZfvdKePLS6K659dgp", "xinhao");
            if (false){
//                success
                System.out.println(sdk.getConnect().getContract("c112e68684f23af1181ed6457f7640d161b8e0dc"));
                String txhash = sdk.neovm().oep5().sendInit(account, account,281803, 0);
                return;
            }
            if (false){
//                success
                String name = sdk.neovm().oep5().queryName();
                System.out.println("name: " + name);
                String totalsupply = sdk.neovm().oep5().queryTotalSupply();
                System.out.println("symbol: " + sdk.neovm().oep5().querySymbol());
                System.out.println("totalsupply: " + totalsupply);

                String balance = sdk.neovm().oep5().queryBalanceOf(account.getAddressU160().toBase58());
                System.out.println("balance: " + balance);

                return;
            }
            if(false){
//success
                System.out.println("tokenId:" + sdk.neovm().oep5().queryTokenIDByIndex(1));
                return;
            }
            if (false){
//success
                String tokenId2 = sdk.neovm().oep5().queryTokenIDByIndex(2);
                String tokenId3 = sdk.neovm().oep5().queryTokenIDByIndex(3);
                String res = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId2));
                System.out.println("owner2: " + new Address(Helper.hexToBytes(res)).toBase58());
                String res3 = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId3));
                System.out.println("owner3: " + new Address(Helper.hexToBytes(res3)).toBase58());
                return;
            }

            if (false){
//                success
                byte[] tokenId = Helper.hexToBytes("468c0424abd5fb0ba5f0cbbe64a538a80e1e8d45e5735e41ad38059eb8031b76");
                Oep5Param param = new Oep5Param(account1.getAddressU160().toArray(), tokenId);
                String txhash = sdk.neovm().oep5().transfer(account,param,account,20000,0);
                System.out.println("txhash: " + txhash);
                Thread.sleep(6000);
                String res = sdk.neovm().oep5().ownerOf(Helper.hexToBytes("468c0424abd5fb0ba5f0cbbe64a538a80e1e8d45e5735e41ad38059eb8031b76"));
                System.out.println("owner: " + new Address(Helper.hexToBytes(res)).toBase58());
                return;
            }
            if (false){
//              success
                String tokenId2 = sdk.neovm().oep5().queryTokenIDByIndex(2);
                String tokenId3 = sdk.neovm().oep5().queryTokenIDByIndex(3);
//                Account[] accounts = new Account[]{account};
//                Oep5Param param = new Oep5Param(account1.getAddressU160().toArray(), Helper.hexToBytes(tokenId2));
//                Oep5Param param2 = new Oep5Param(account1.getAddressU160().toArray(), Helper.hexToBytes(tokenId3));
//                String res = sdk.neovm().oep5().transferMulti(accounts,new Oep5Param[]{param,param2},  account, 27195, 0);
//                System.out.println("res: " + res);
//                Thread.sleep(3000);
//                System.out.println(sdk.getConnect().getSmartCodeEvent(res));


                String res2 = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId2));
                System.out.println("owner2: " + new Address(Helper.hexToBytes(res2)).toBase58());
                String res3 = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId3));
                System.out.println("owner3: " + new Address(Helper.hexToBytes(res3)).toBase58());
                return;
            }
            if (false){
                String tokenId1 = sdk.neovm().oep5().queryTokenIDByIndex(1);
//                Oep5Param param = new Oep5Param(account1.getAddressU160().toArray(), Helper.hexToBytes(tokenId1));
//                String res = sdk.neovm().oep5().approve(account,param, account, 20000, 0);
//                System.out.println("res: " + res);
//                Thread.sleep(6000);
//                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                System.out.println(new Address(Helper.hexToBytes(sdk.neovm().oep5().getApproved(tokenId1))).toBase58());
                return;
            }
            if (true){
                String tokenId1 = sdk.neovm().oep5().queryTokenIDByIndex(1);
                String res0 = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId1));
                System.out.println("owner1: " + new Address(Helper.hexToBytes(res0)).toBase58());

                Oep5Param param = new Oep5Param(account1.getAddressU160().toArray(), Helper.hexToBytes(tokenId1));
                String res = sdk.neovm().oep5().takeOwnership(account1,param, account1, 20000, 0);
                System.out.println("res: " + res);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                String res1 = sdk.neovm().oep5().ownerOf(Helper.hexToBytes(tokenId1));
                System.out.println("owner1: " + new Address(Helper.hexToBytes(res1)).toBase58());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://52.184.29.110";
        String ip = "http://127.0.0.1";
//        ip= "http://139.219.138.201";
//        String ip = "http://52.229.166.6";
//        String ip = "http://dapp2.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.neovm().oep5().setContractAddress("6d16074febe9a2db0099570916bcb412787265ea");
        wm.openWalletFile("oep8.dat");
        return wm;
    }
}