package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.VmType;
import com.github.TesraSupernet.core.asset.Contract;
import com.github.TesraSupernet.core.asset.State;
import com.github.TesraSupernet.core.asset.Transfers;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;

import java.security.Signature;

public class TstAssetSM2Demo {
    public static String privatekey1 = "49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221";
    public static String privatekey2 = "1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96";
    public static String privatekey3 = "bc254cf8d3910bc615ba6bf09d4553846533ce4403bc24f58660ae150a6d64cf";
    public static String privatekey4 = "06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8";
    public static String privatekey5 = "f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549";
    public static String tstContractAddr = "ff00000000000000000000000000000000000001";

    public static void main(String[] args) throws Exception {
        TstSdk tstSdk = getTstSdk();
        String password = "111111";
        String privatekey0 = "d6aae3603a82499062fe2ddd68840dce417e2e9e7785fbecb3100dd68c4e2d44";

        com.github.TesraSupernet.account.Account acct0 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0), SignatureScheme.SM3WITHSM2);
        com.github.TesraSupernet.account.Account acct1 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1), SignatureScheme.SM3WITHSM2);
        com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey2), SignatureScheme.SM3WITHSM2);
        com.github.TesraSupernet.account.Account acct3 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey3), SignatureScheme.SHA256WITHECDSA);
        com.github.TesraSupernet.account.Account acct4 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey4), SignatureScheme.SHA256WITHECDSA);
        com.github.TesraSupernet.account.Account acct5 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey5), SignatureScheme.SHA256WITHECDSA);

        tstSdk.setSignatureScheme(SignatureScheme.SM3WITHSM2);
        if (false) {

            //transer
            Address sender = acct0.getAddressU160();
//            Address recvAddr = acct5.getAddressU160();
//                Address recvAddr = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(), acct3.serializePublicKey());
//                recvAddr = Address.decodeBase58("TA5SgQXTeKWyN4GNfWGoXqioEQ4eCDFMqE");
            Address recvAddr123 = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(), acct2.serializePublicKey(),acct3.serializePublicKey());
            System.out.println("senderAd:" + sender.toBase58());
            System.out.println("recvAddr:" + recvAddr123.toBase58());

            System.out.println("senderAd : tst:" + tstSdk.nativevm().tst().queryBalanceOf(sender.toBase58()));
            System.out.println("recvAddr : tst:" + tstSdk.nativevm().tst().queryBalanceOf(recvAddr123.toBase58()));

            int amount = 10;

            State state = new State(acct0.getAddressU160(), recvAddr123, amount);
            Transfers transfers = new Transfers(new State[]{state});
            Contract contract = new Contract((byte) 0, Address.parse(tstContractAddr), "transfer", transfers.toArray());
            Transaction tx = tstSdk.vm().makeInvokeCodeTransaction(tstContractAddr, null, contract.toArray(), sender.toBase58(),0,0);
            System.out.println(tx.json());
            tstSdk.signTx(tx, new com.github.TesraSupernet.account.Account[][]{{acct0}});

            System.out.println(tx.hash().toHexString());
//                tstSdk.getConnectMgr().sendRawTransaction(tx.toHexString());

        }

        if (true) {
            //sender address From MultiPubKeys
            Address multiAddr = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(),acct2.serializePublicKey(), acct3.serializePublicKey());
            System.out.println("sender:" + multiAddr);
            Address recvAddr = acct5.getAddressU160();
            System.out.println("recvAddr:" + recvAddr.toBase58());


            System.out.println("senderAd : tst:" + tstSdk.nativevm().tst().queryBalanceOf(multiAddr.toBase58()));
            System.out.println("recvAddr : tst:" + tstSdk.nativevm().tst().queryBalanceOf(recvAddr.toBase58()));

            int amount = 1;

            State state = new State(multiAddr, recvAddr, amount);
            Transfers transfers = new Transfers(new State[]{state});
            Contract contract = new Contract((byte) 0, Address.parse(tstContractAddr), "transfer", transfers.toArray());
            String addr = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(),acct3.serializePublicKey()).toBase58();
            Transaction tx = tstSdk.vm().makeInvokeCodeTransaction(tstContractAddr, null, contract.toArray(), addr,0,0 );
//            System.out.println(tx.json());
            tstSdk.signTx(tx, new com.github.TesraSupernet.account.Account[][]{{acct1, acct3}});
            System.out.println("tx.sigs.length:" + tx.sigs.length);
            System.out.println("tx.sigs.length:" + tx.sigs[0].pubKeys.length);
//            System.out.println(tx.hash().toHexString());
            tstSdk.getConnect().sendRawTransaction(tx.toHexString());


        }

        if (false) {
            //2 sender transfer to 1 reveiver
            Address sender1 = acct0.getAddressU160();
            Address sender2 = Address.addressFromMultiPubKeys(2, acct1.serializePublicKey(), acct2.serializePublicKey(),acct3.serializePublicKey());
            Address recvAddr = acct5.getAddressU160();
            System.out.println("sender1:" + sender1.toBase58());
            System.out.println("sender2:" + sender2.toBase58());
            System.out.println("recvAddr:" + recvAddr.toBase58());

            System.out.println("sender1 : tst:" + tstSdk.nativevm().tst().queryBalanceOf(sender1.toBase58()));
            System.out.println("sender2 : tst:" + tstSdk.nativevm().tst().queryBalanceOf(sender2.toBase58()));
            System.out.println("recvAddr : tst:" + tstSdk.nativevm().tst().queryBalanceOf(recvAddr.toBase58()));

            int amount = 10;
            int amount2 = 20;
            State state = new State(sender1, recvAddr, amount);
            State state2 = new State(sender2, recvAddr, amount2);

            Transfers transfers = new Transfers(new State[]{state, state2});
            Contract contract = new Contract((byte) 0, Address.parse(tstContractAddr), "transfer", transfers.toArray());

            Transaction tx = tstSdk.vm().makeInvokeCodeTransaction(tstContractAddr, null, contract.toArray(),sender1.toBase58(),0,0);
            System.out.println(tx.json());
            tstSdk.signTx(tx, new com.github.TesraSupernet.account.Account[][]{{acct0}, {acct1, acct2}});

            System.out.println(tx.hash().toHexString());
            tstSdk.getConnect().sendRawTransaction(tx.toHexString());

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
        wm.openWalletFile("TstAssetSM2Demo.json");
        return wm;
    }
}
