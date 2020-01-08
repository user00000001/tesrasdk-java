package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.asset.State;
import com.github.TesraSupernet.core.transaction.Transaction;

import java.math.BigInteger;
import java.util.*;

class UserAcct{
    String id;
    String address;
    String withdrawAddr;
    byte[] privkey;
    BigInteger tstBalance;
    BigInteger tsgBalance;
}

class Balance{
    @JSONField(name="tst")
    String tst;
    @JSONField(name="tsg")
    String tsg;

    public String getTst() {
        return tst;
    }

    public void setTst(String tst) {
        this.tst = tst;
    }

    public String getTsg() {
        return tsg;
    }

    public void setTsg(String tsg) {
        this.tsg = tsg;
    }
}


class States{
    @JSONField(name="States")
    Object[] states;

    @JSONField(name="ContractAddress")
    String contractAddress;

    public Object[] getStates() {
        return states;
    }

    public void setStates(Object[] states) {
        this.states = states;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

}

class Event{
    @JSONField(name="GasConsumed")
    int gasConsumed;


    @JSONField(name="TxHash")
    String txHash;

    @JSONField(name="State")
    int state;

    @JSONField(name="Notify")
    States[] notify;

    public int getGasConsumed() {
        return gasConsumed;
    }

    public void setGasConsumed(int gasConsumed) {
        this.gasConsumed = gasConsumed;
    }


    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public States[] getNotify() {
        return notify;
    }

    public void setNotify(States[] notify) {
        this.notify = notify;
    }
}



public class ExchangeDemo {

    //init account should have some tsts
    public static final String INIT_ACCT_ADDR = "Ad4pjz2bqep4RhQrUAzMuZJkBC3qJ1tZuT";
    public static final String INIT_ACCT_SALT = "OkX96EG0OaCNUFD3hdc50Q==";

    public static final String FEE_PROVIDER = "AS3SCXw8GKTEeXpdwVw7EcC4rqSebFYpfb";
    public static final String FEE_PROVIDER_SALT = "KvKkxNOGm4q4bLkD8TS2PA==";

    //for test all account's pwd is the same
    public static final String PWD = "123456";

    //for generate a multi-sig address
    public static final String MUTI_SIG_ACCT_SEED1_ADDR = "AK98G45DhmPXg4TFPG1KjftvkEaHbU8SHM";
    public static final String MUTI_SIG_ACCT_SEED1_SALT = "rD4ewxv4qHH8FbUkUv6ePQ==";

    public static final String MUTI_SIG_ACCT_SEED2_ADDR = "ALerVnMj3eNk9xe8BnQJtoWvwGmY3x4KMi";
    public static final String MUTI_SIG_ACCT_SEED2_SALT = "1K8a7joYQ+iwj3/+wGICrw==";

    public static final String MUTI_SIG_ACCT_SEED3_ADDR = "AKmowTi8NcAMjZrg7ZNtSQUtnEgdaC65wG";
    public static final String MUTI_SIG_ACCT_SEED3_SALT = "b9oBYBIPvZMw66q1ky+JDQ==";

    //withdraw address for test user
    public static final String WITHDRAW_ADDRESS = "AZbcPX7HyJTWjqogZhnr2qDTh6NNksGSE6";


    public static  String TST_NATIVE_ADDRESS = "";
    public static  String TSG_NATIVE_ADDRESS = "";

    public static void main(String[] args) {
        try{
            //simulate a database using hashmap
            HashMap<String,UserAcct> database = new HashMap<String,UserAcct>();

            TstSdk tstSdk = getTstSdk();
            TST_NATIVE_ADDRESS = Helper.reverse(tstSdk.nativevm().tst().getContractAddress());
            TSG_NATIVE_ADDRESS = Helper.reverse(tstSdk.nativevm().tsg().getContractAddress());

            printlog("++++ starting simulate exchange process ...========");
            printlog("++++ 1. create a random account for user ====");
            String id1 = "id1";
            Account acct1 = new Account(tstSdk.defaultSignScheme);
            String pubkey =  acct1.getAddressU160().toBase58();
            byte[] privkey = acct1.serializePrivateKey();
            printlog("++++ public key is " + acct1.getAddressU160().toBase58());

            UserAcct usr =getNewUserAcct(id1,pubkey,privkey,BigInteger.valueOf(0),BigInteger.valueOf(0));
            usr.withdrawAddr = WITHDRAW_ADDRESS;
            database.put(acct1.getAddressU160().toBase58(),usr);
            //all transfer fee is provide from this account
            Account feeAct = tstSdk.getWalletMgr().getAccount(FEE_PROVIDER,PWD,Base64.getDecoder().decode(FEE_PROVIDER_SALT));

            //create a multi-sig account as a main account
            Account mutiSeedAct1 = tstSdk.getWalletMgr().getAccount(MUTI_SIG_ACCT_SEED1_ADDR,PWD,Base64.getDecoder().decode(MUTI_SIG_ACCT_SEED1_SALT));
            Account mutiSeedAct2 = tstSdk.getWalletMgr().getAccount(MUTI_SIG_ACCT_SEED2_ADDR,PWD,Base64.getDecoder().decode(MUTI_SIG_ACCT_SEED2_SALT));
            Account mutiSeedAct3 = tstSdk.getWalletMgr().getAccount(MUTI_SIG_ACCT_SEED3_ADDR,PWD,Base64.getDecoder().decode(MUTI_SIG_ACCT_SEED3_SALT));

            Address mainAccountAddr = Address.addressFromMultiPubKeys(3,mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey());
            printlog("++++ Main Account Address is :" + mainAccountAddr.toBase58());


            //monitor the charge and withdraw thread
            Thread t = new Thread(new Runnable() {

                long lastblocknum = 0 ;

                @Override
                public void run() {
                    while(true){
                        try{
                            //get latest blocknum:
                            //TODO fix lost block
                            int height = tstSdk.getConnect().getBlockHeight();
                            if (height > lastblocknum){
                                printlog("====== new block sync :" + height);

                                Object  event = tstSdk.getConnect().getSmartCodeEvent(height);
                                if(event == null){
                                    lastblocknum = height;
                                    Thread.sleep(1000);
                                    continue;
                                }
                                printlog("====== event is " + event.toString());

                                List<Event> events = JSON.parseArray(event.toString(), Event.class);
                                if(events == null){
                                    lastblocknum = height;
                                    Thread.sleep(1000);
                                    continue;
                                }
                                if (events.size()> 0){
                                    for(Event ev:events){
                                        printlog("===== State:" + ev.getState());
                                        printlog("===== TxHash:" + ev.getTxHash());
                                        printlog("===== GasConsumed:" + ev.getGasConsumed());

                                        for(States state:ev.notify){

                                            printlog("===== Notify - ContractAddress:" + state.getContractAddress());
                                            printlog("===== Notify - States[0]:" + state.getStates()[0]);
                                            printlog("===== Notify - States[1]:" + state.getStates()[1]);
                                            printlog("===== Notify - States[2]:" + state.getStates()[2]);
                                            printlog("===== Notify - States[3]:" + state.getStates()[3]);

                                            if (ev.getState() == 1){  //exec succeed
                                                Set<String> keys = database.keySet();
                                                //
                                                if ("transfer".equals(state.getStates()[0]) && keys.contains(state.getStates()[2])){
                                                    BigInteger amount = new BigInteger(state.getStates()[3].toString());
                                                    if (TSG_NATIVE_ADDRESS.equals(state.getContractAddress())){
                                                        printlog("===== charge TSG :"+state.getStates()[2] +" ,amount:"+amount);
                                                        database.get(state.getStates()[2]).tstBalance = amount.add(database.get(state.getStates()[2]).tstBalance);
                                                    }
                                                    if (TSG_NATIVE_ADDRESS.equals(state.getContractAddress())){
                                                        printlog("===== charge TSG :"+state.getStates()[2] +" ,amount:"+amount);
                                                        database.get(state.getStates()[2]).tsgBalance = amount.add(database.get(state.getStates()[2]).tsgBalance);
                                                    }
                                                }

                                                //withdraw case
                                                if("transfer".equals(state.getStates()[0]) && mainAccountAddr.toBase58().equals(state.getStates()[1])){

                                                    for(UserAcct ua: database.values()){
                                                        if (ua.withdrawAddr.equals((state.getStates()[2]))){
                                                            BigInteger amount = new BigInteger(state.getStates()[3].toString());
                                                            if (TSG_NATIVE_ADDRESS.equals(state.getContractAddress())){
                                                                printlog("===== widtdraw "+ amount +" tst to " + ua.withdrawAddr + " confirmed!");
                                                            }
                                                            if (TSG_NATIVE_ADDRESS.equals(state.getContractAddress())){
                                                                printlog("===== widtdraw "+ amount +" tsg to " + ua.withdrawAddr + " confirmed!");
                                                            }

                                                        }
                                                    }

                                                }

                                            }

                                        }

                                    }
                                }

                                lastblocknum = height;


                            }
                            Thread.sleep(1000);

                        }catch(Exception e){
                            printlog("exception 1:"+ e.getMessage());
                        }
                    }
                }
            });

            //monitor the collect
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (true){

                            Set<String> keys = database.keySet();

                            List<Account> tstAccts = new ArrayList<Account>() ;
                            List<State> tstStates = new ArrayList<State>();
                            List<Account> tsgAccts = new ArrayList<Account>() ;
                            List<State> tsgStates = new ArrayList<State>();


                            for(String key:keys){
                                Object balance = tstSdk.getConnect().getBalance(key);
                                printlog("----- balance of " + key + " : " + balance);
                                Balance b = JSON.parseObject(balance.toString(),Balance.class);
                                BigInteger tstbalance = new BigInteger(b.tst);
                                BigInteger tsgbalance = new BigInteger(b.tsg);

                                if (tstbalance.compareTo(new BigInteger("0")) > 0){
                                    //transfer tst to main wallet
                                    UserAcct ua = database.get(key);
                                    Account acct = new Account(ua.privkey,tstSdk.defaultSignScheme);
                                    tstAccts.add(acct);
                                    State st = new State(Address.addressFromPubKey(acct.serializePublicKey()),mainAccountAddr,ua.tstBalance.longValue());
                                    tstStates.add(st);
                                }

                                if (tsgbalance.compareTo(new BigInteger("0")) > 0){
                                    //transfer tsg to main wallet
                                    UserAcct ua = database.get(key);
                                    Account acct = new Account(ua.privkey,tstSdk.defaultSignScheme);
                                    tsgAccts.add(acct);
                                    State st = new State(Address.addressFromPubKey(acct.serializePublicKey()),mainAccountAddr,ua.tsgBalance.longValue());
                                    tsgStates.add(st);
                                }
                            }

                            //construct tst transfer tx
                            if (tstStates.size() > 0) {
                                printlog("----- Will collect tst to main wallet");
                                Transaction tstTx = tstSdk.nativevm().tst().makeTransfer(tstStates.toArray(new State[tstStates.size()]), FEE_PROVIDER, 30000, 0);
                                for (Account act : tstAccts) {
                                    tstSdk.addSign(tstTx, act);
                                }
                                //add fee provider account sig
                                tstSdk.addSign(tstTx, feeAct);
                                tstSdk.getConnect().sendRawTransaction(tstTx.toHexString());
                            }

                            //construct tsg transfer tx
                            if(tsgStates.size() > 0) {
                                printlog("----- Will collect tsg to main wallet");
                                Transaction tsgTx = tstSdk.nativevm().tsg().makeTransfer(tsgStates.toArray(new State[tsgStates.size()]), FEE_PROVIDER, 30000, 0);
                                for (Account act : tsgAccts) {
                                    tstSdk.addSign(tsgTx, act);
                                }
                                //add fee provider account sig
                                tstSdk.addSign(tsgTx, feeAct);
                                tstSdk.getConnect().sendRawTransaction(tsgTx.toHexString());
                            }

                            Thread.sleep(10000);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        printlog("exception 2:"+e.getMessage());
                    }

                }
            });

            t.start();
            t2.start();

            Thread.sleep(2000);
            printlog("++++ 2. charge some tst to acct1 from init account");
            Account initAccount = tstSdk.getWalletMgr().getAccount(INIT_ACCT_ADDR,PWD,Base64.getDecoder().decode(INIT_ACCT_SALT));
            State st = new State(initAccount.getAddressU160(),acct1.getAddressU160(),1000L);
            Transaction tx = tstSdk.nativevm().tst().makeTransfer(new State[]{st}, FEE_PROVIDER, 30000, 0);
            tstSdk.addSign(tx,initAccount);
            tstSdk.addSign(tx, feeAct);

            tstSdk.getConnect().sendRawTransaction(tx.toHexString());
            // test is the tx in txpool
            String txhash = tx.hash().toHexString();
            printlog("++++ txhash :"+txhash);
            Object event = tstSdk.getConnect().getMemPoolTxState(txhash);
            printlog(event.toString());


            printlog("++++ 3. charge some tsg to acct1 from init account");
            st = new State(initAccount.getAddressU160(),acct1.getAddressU160(),1200L);
            tx = tstSdk.nativevm().tsg().makeTransfer(new State[]{st}, FEE_PROVIDER, 30000, 0);
            tstSdk.addSign(tx,initAccount);
            tstSdk.addSign(tx, feeAct);
            tstSdk.getConnect().sendRawTransaction(tx.toHexString());

            Thread.sleep(15000);

            //simulate a withdraw
            //todo must add check the user balance of database
            printlog("++++ withdraw 500 tsts to " + usr.withdrawAddr );
            //reduce the withdraw amount first
            BigInteger wdAmount = new BigInteger("500");
            if(usr.tstBalance.compareTo(wdAmount) > 0) {
                database.get(usr.address).tstBalance = database.get(usr.address).tstBalance.subtract(wdAmount);
                printlog("++++  " + usr.address + " tst balance : " + database.get(usr.address).tstBalance);
                State wdSt = new State(mainAccountAddr, Address.decodeBase58(usr.withdrawAddr), 500);
                Transaction wdTx = tstSdk.nativevm().tst().makeTransfer(new State[]{wdSt}, FEE_PROVIDER, 30000, 0);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct1);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct2);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct3);
                tstSdk.addSign(wdTx, feeAct);
                tstSdk.getConnect().sendRawTransaction(wdTx.toHexString());

            }


            //simulate a withdraw
            printlog("++++ withdraw 500 tsgs to " + usr.withdrawAddr );
            wdAmount = new BigInteger("500");
            //reduce the withdraw amount first
            if(usr.tsgBalance.compareTo(wdAmount) > 0) {
                database.get(usr.address).tsgBalance = database.get(usr.address).tsgBalance.subtract(wdAmount);
                printlog("++++  " + usr.address + " tsg balance : " + database.get(usr.address).tsgBalance);

                State wdSt = new State(mainAccountAddr, Address.decodeBase58(usr.withdrawAddr), 500);
                Transaction wdTx = tstSdk.nativevm().tsg().makeTransfer(new State[]{wdSt}, FEE_PROVIDER, 30000, 0);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct1);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct2);
                tstSdk.addMultiSign(wdTx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct3);
                tstSdk.addSign(wdTx, feeAct);
                tstSdk.getConnect().sendRawTransaction(wdTx.toHexString());
            }



            //claim tsg
            Object balance = tstSdk.getConnect().getBalance(mainAccountAddr.toBase58());
            printlog("++++ before claime tsg ,balance of "+ mainAccountAddr.toBase58() +" is " + balance);
            String uTsgAmt = tstSdk.nativevm().tsg().unboundTsg(mainAccountAddr.toBase58());
            printlog("++++ unclaimed tsg is " + uTsgAmt);
            if(new BigInteger(uTsgAmt).compareTo(new BigInteger("0")) > 0) {
                tx = tstSdk.nativevm().tsg().makeWithdrawTsg(mainAccountAddr.toBase58(), mainAccountAddr.toBase58(), new BigInteger(uTsgAmt).longValue(), FEE_PROVIDER, 30000, 0);
                tstSdk.addMultiSign(tx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct1);
                tstSdk.addMultiSign(tx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct2);
                tstSdk.addMultiSign(tx, 3, new byte[][]{mutiSeedAct1.serializePublicKey(),mutiSeedAct2.serializePublicKey(),mutiSeedAct3.serializePublicKey()},mutiSeedAct3);
                tstSdk.addSign(tx, feeAct);
                tstSdk.getConnect().sendRawTransaction(tx.toHexString());
                balance = tstSdk.getConnect().getBalance(mainAccountAddr.toBase58());

                Thread.sleep(10000);
                printlog("++++ after claime tsg ,balance of " + mainAccountAddr.toBase58() + " is " + balance);
                //distribute tsg to users in database
            }

            t.join();
            t2.join();
        }catch (Exception e){
            e.printStackTrace();
            printlog("exception 3:" + e.getMessage());
        }


    }




    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://127.0.0.1";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        String walletfile = "wallet.dat";
        wm.openWalletFile(walletfile);

        return wm;
    }

    public static void printlog(String msg){
        System.out.println(msg);
    }

    public  static UserAcct getNewUserAcct(String id ,String pubkey,byte[] privkey,BigInteger tst,BigInteger tsg){
        UserAcct acct = new UserAcct();
        acct.id = id;
        acct.privkey = privkey;
        acct.address = pubkey;
        acct.tstBalance = tst;
        acct.tsgBalance = tsg;

        return acct;
    }
}
