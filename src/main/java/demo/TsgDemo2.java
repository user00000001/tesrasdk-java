package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.asset.State;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class TsgDemo2 {
    public static String privatekey0 = "523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f";
    public static String privatekey1 = "49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221";
    public static String privatekey2 = "1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96";
    public static String privatekey3 = "bc254cf8d3910bc615ba6bf09d4553846533ce4403bc24f58660ae150a6d64cf";
    public static String privatekey4 = "06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8";
    public static String privatekey5 = "f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549";
    static Account acct1 = null;
    static Account acct2 = null;
    static Account acct3 = null;
    static Account acct4 = null;
    static Account acct5 = null;
    static Account account = null;
    static Account acct = null;

    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();

            Account account = new Account(Helper.hexToBytes(privatekey0), SignatureScheme.SHA256WITHECDSA);
            acct1 = new Account(Helper.hexToBytes(privatekey1), tstSdk.defaultSignScheme);
            acct2 = new Account(Helper.hexToBytes(privatekey2), tstSdk.defaultSignScheme);
            acct3 = new Account(Helper.hexToBytes(privatekey3), tstSdk.defaultSignScheme);
            acct4 = new Account(Helper.hexToBytes(privatekey4), tstSdk.defaultSignScheme);
            acct5 = new Account(Helper.hexToBytes(privatekey5), tstSdk.defaultSignScheme);
            acct = new Account(Helper.hexToBytes(privatekey0), tstSdk.defaultSignScheme);
            System.out.println("recv:" + acct.getAddressU160().toBase58());
            System.out.println("send:" + account.getAddressU160().toBase58());


//            sendTransfer(tstSdk);
//            sendTransferFromMultiToMulti(tstSdk);
//            sendTransferFromMultiSignAddr(tstSdk);
            getTransferSmartCodeEvent(tstSdk);
//            accountInfo(tstSdk);
//            convert(tstSdk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTransfer(TstSdk tstSdk) throws Exception {
        // send tsg from acct1 to acct2(单发单收)
        String txhash = tstSdk.nativevm().tsg().sendTransfer(acct1, acct2.getAddressU160().toBase58(), 1000, acct1, 20000, 500);
        return;
    }


    public static void sendTransferFromMultiToMulti(TstSdk tstSdk) throws Exception {
        // acct1,acct2 send to acct3,acct4(单发多收，多发单收，多发多收)
        Account[] accounts = new Account[]{acct1, acct2};
        State state = new State(acct1.getAddressU160(), acct3.getAddressU160(), 100);
        State state2 = new State(acct2.getAddressU160(), acct4.getAddressU160(), 200);
        State[] states = new State[]{state, state2};
        String txhash = tstSdk.nativevm().tsg().sendTransferMulti(accounts, states, acct1, 20000, 0);
        return;

    }

    public static void sendTransferFromMultiSignAddr(TstSdk tstSdk) throws Exception {
        // send tsg from multiSignatureAddress to acct2（多签地址转账）
        Address multiSignatureAddress = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(), acct2.serializePublicKey());
        Account payerAcct = acct1;
        Transaction tx = tstSdk.nativevm().tsg().makeTransfer(multiSignatureAddress.toBase58(), acct2.getAddressU160().toBase58(), 1000, payerAcct.getAddressU160().toBase58(), 20000, 500);
        tstSdk.signTx(tx, new com.github.TesraSupernet.account.Account[][]{{payerAcct}});
        tstSdk.addMultiSign(tx, 2, new byte[][]{acct1.serializePublicKey(), acct2.serializePublicKey()}, acct1);
        tstSdk.addMultiSign(tx, 2, new byte[][]{acct1.serializePublicKey(), acct2.serializePublicKey()}, acct2);
        boolean b = tstSdk.getConnect().sendRawTransaction(tx.toHexString());
        System.out.println(tx.hash().toString());

    }

    public static void getTransferSmartCodeEvent(TstSdk tstSdk) throws Exception {
        // parse smartcontract event transfer information(交易结果查看)
        Map obj = (Map) tstSdk.getConnect().getSmartCodeEvent("26f7d807d6256ed614cb2129b26bf6ce47642f62cd5c8b7d16d23de637476570");
        List list = (List) obj.get("Notify");
        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);
            String ContractAddress = (String) tmp.get("ContractAddress");
            List states = (List) tmp.get("States");
            if (ContractAddress.equals("0200000000000000000000000000000000000000")) {
                String transfer = (String) states.get(0);
                String from = (String) states.get(1);
                String to = (String) states.get(2);
                System.out.println(transfer + " " + from + " " + to + " " + states.get(3));

            }
        }
        return;

    }

    public static void accountInfo(TstSdk tstSdk) throws Exception {
        //query tsg token info(账户状态查看)
        System.out.println(tstSdk.nativevm().tsg().queryDecimals());
        System.out.println(tstSdk.nativevm().tsg().queryName());
        System.out.println(tstSdk.nativevm().tsg().querySymbol());
        System.out.println(tstSdk.nativevm().tsg().queryTotalSupply());
        System.out.println(acct1.getAddressU160().toBase58() + ": " + tstSdk.nativevm().tsg().queryBalanceOf(acct1.getAddressU160().toBase58()));
        return;

    }

    public static void convert(TstSdk tstSdk) throws Exception {

        //583e0f  hex金额转string
        BigInteger amount = Helper.BigIntFromNeoBytes(Helper.hexToBytes("583e0f"));
        System.out.println(amount.toString());
        BigDecimal amount2 = new BigDecimal(amount);
        System.out.println(amount2.divide(new BigDecimal("1000000000")).doubleValue());
        return;

    }


    public static void showBalance(TstSdk tstSdk, Account[] accounts) throws Exception {
        for (int i = 0; i < accounts.length; i++) {
            int a = i + 1;
            System.out.println("account" + a + ":" + tstSdk.nativevm().tsg().queryBalanceOf(accounts[i].getAddressU160().toBase58()));
        }
    }


    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://52.184.29.110";
        String ip = "http://127.0.0.1";
        ip = "http://dapp2.tesra.me";
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
        wm.openWalletFile("tsg.json");
        return wm;
    }
}
