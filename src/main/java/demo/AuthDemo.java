package demo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;
import com.github.TesraSupernet.smartcontract.neovm.abi.AbiFunction;
import com.github.TesraSupernet.smartcontract.neovm.abi.AbiInfo;
import com.github.TesraSupernet.smartcontract.neovm.abi.BuildParams;

import java.util.ArrayList;
import java.util.List;

public class AuthDemo {

    public static void main(String[] args){
        TstSdk tstSdk;
        String password = "111111";
        String abi = "{\"hash\":\"0x4d0d780599010f943c37c795a22f6161d49436cf\",\"entrypoint\":\"Main\",\"functions\":[{\"name\":\"Main\",\"parameters\":[{\"name\":\"operation\",\"type\":\"String\"},{\"name\":\"token\",\"type\":\"Array\"},{\"name\":\"args\",\"type\":\"Array\"}],\"returntype\":\"Any\"},{\"name\":\"foo\",\"parameters\":[{\"name\":\"operation\",\"type\":\"ByteArray\"},{\"name\":\"token\",\"type\":\"Integer\"}],\"returntype\":\"Boolean\"},{\"name\":\"init\",\"parameters\":[],\"returntype\":\"Boolean\"}],\"events\":[]}";
        Account payer;
        try {
            tstSdk = getTstSdk();
//            System.out.println(Helper.toHexString("initContractAdmin".getBytes()));
//            System.exit(0);
            // 8007c33f29a892e3a36e2cfec657eff1d7431e8f
            String privatekey0 = "523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f";
            String privatekey1 ="83614c773f668a531132e765b5862215741c9148e7b2f9d386b667e4fbd93e39";
            com.github.TesraSupernet.account.Account acct0 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0), tstSdk.defaultSignScheme);

            com.github.TesraSupernet.account.Account account = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1), tstSdk.defaultSignScheme);

            payer = tstSdk.getWalletMgr().createAccount(password);
            com.github.TesraSupernet.account.Account payerAcct = tstSdk.getWalletMgr().getAccount(payer.address,password,payer.getSalt());
            Identity identity = null;
            Identity identity2 = null;
            Identity identity3 = null;
            List<Identity> dids = tstSdk.getWalletMgr().getWallet().getIdentities();
            if(tstSdk.getWalletMgr().getWallet().getIdentities().size() < 3){
               // Identity identity1 = tstSdk.getWalletMgr().importIdentity("",password,"".getBytes(),acct0.getAddressU160().toBase58());
                identity = tstSdk.getWalletMgr().createIdentityFromPriKey(password,privatekey0);

                tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
              //  tstSdk.nativevm().tstId().sendRegister(identity1,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                identity2 = tstSdk.getWalletMgr().createIdentity(password);
                tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

                identity3 = tstSdk.getWalletMgr().createIdentity(password);
                tstSdk.nativevm().tstId().sendRegister(identity3,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);

                tstSdk.getWalletMgr().writeWallet();

                Thread.sleep(6000);
            }else {
                identity = tstSdk.getWalletMgr().getWallet().getIdentity(dids.get(0).tstid);
                identity2 = tstSdk.getWalletMgr().getWallet().getIdentity(dids.get(1).tstid);
                identity3 = tstSdk.getWalletMgr().getWallet().getIdentity(dids.get(2).tstid);
            }



            System.out.println("tstid1:" +dids.get(0).tstid+" "+Helper.toHexString(dids.get(0).tstid.getBytes()));
            System.out.println("tstid2:" +dids.get(1).tstid);
            System.out.println("tstid3:" +dids.get(2).tstid);
            Account account1 = tstSdk.getWalletMgr().createAccount(password);
            System.out.println("####" + account1.address);

//            System.out.println("ddo1:" + tstSdk.nativevm().tstId().sendGetDDO(dids.get(0).tstid));
//            System.out.println("ddo2:" + tstSdk.nativevm().tstId().sendGetDDO(dids.get(1).tstid));
//            System.out.println("ddo3:" + tstSdk.nativevm().tstId().sendGetDDO(dids.get(2).tstid));

            String contractAddr = "b93f1d81a00f95d09228f1f8934a71dd0e89999f";

            if(false){
                 identity = tstSdk.getWalletMgr().createIdentityFromPriKey(password,privatekey1);
                tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcct,tstSdk.DEFAULT_GAS_LIMIT,0);
                System.out.println(Helper.toHexString(identity.tstid.getBytes()));

            }
            //Identity identity = tstSdk.getWalletMgr().createIdentityFromPriKey(password,privatekey1);
            System.out.println(account.getAddressU160().toBase58());
            System.out.println(identity.tstid);
            System.out.println(Helper.toHexString(account.getAddressU160().toArray()));
            System.out.println(Helper.toHexString(identity.tstid.getBytes()));

            if(false){
                AbiInfo abiinfo = JSON.parseObject(abi, AbiInfo.class);
                String name = "init";
                AbiFunction func = abiinfo.getFunction(name);
                func.name = name;
                System.out.println(func);
                func.setParamsValue();
                //Object obj =  tstSdk.neovm().sendTransaction(Helper.reverse(contractAddr),null,null,0,0,func, true);
                Object obj =  tstSdk.neovm().sendTransaction(Helper.reverse(contractAddr),acct0,acct0,30000,0,func, false);
                System.out.println(obj);
            }

            if(false){

//                String txhash = tstSdk.nativevm().auth().sendInit(dids.get(0).tstid,password,codeaddress,account,tstSdk.DEFAULT_GAS_LIMIT,0);

                //String txhash = tstSdk.nativevm().auth().sendTransfer(identity.tstid,password,identity.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),identity2.tstid,account,tstSdk.DEFAULT_GAS_LIMIT,0);

              //  String txhash = tstSdk.nativevm().auth().assignFuncsToRole(identity2.tstid,password,identity2.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),"role",new String[]{"foo"},account,tstSdk.DEFAULT_GAS_LIMIT,0);
                String txhash = tstSdk.nativevm().auth().assignTstIdsToRole(identity2.tstid,password,identity2.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),"role",new String[]{identity2.tstid},account,tstSdk.DEFAULT_GAS_LIMIT,0);
                //String txhash = tstSdk.nativevm().auth().delegate(identity2.tstid,password,identity2.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),identity3.tstid,"role",6000,1,account,tstSdk.DEFAULT_GAS_LIMIT,0);
               //String txhash = tstSdk.nativevm().auth().withdraw(identity2.tstid,password,identity2.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),identity3.tstid,"role",account,tstSdk.DEFAULT_GAS_LIMIT,0);
              //  String txhash = tstSdk.nativevm().auth().verifyToken(identity2.tstid,password,identity2.controls.get(0).getSalt(),1,Helper.reverse(contractAddr),"foo");
//                Thread.sleep(6000);
//                Object object = tstSdk.getConnect().getSmartCodeEvent(txhash);
//                System.out.println(object);


//     String txhash2 = tstSdk.nativevm().auth().withdraw(dids.get(0).tstid,password,contractAddr,dids.get(1).tstid,"role",1,payer.address,password,tstSdk.DEFAULT_GAS_LIMIT,0);
                Thread.sleep(6000);
                Object object2 = tstSdk.getConnect().getSmartCodeEvent(txhash);
                System.out.println(object2);
            }
            if(true){
                tstSdk.nativevm().auth().queryAuth(contractAddr,"role",identity2.tstid);
            }
            if(false){
                AbiInfo abiinfo = JSON.parseObject(abi, AbiInfo.class);
                String name = "foo";
                AbiFunction func = abiinfo.getFunction(name);
                func.name = name;
                System.out.println(func);
                func.setParamsValue(identity2.tstid.getBytes(),Long.valueOf(1));

                acct0 = tstSdk.getWalletMgr().getAccount(identity2.tstid,password,identity2.controls.get(0).getSalt());
                System.out.println("pk:"+Helper.toHexString(acct0.serializePublicKey()));
                Object obj =  tstSdk.neovm().sendTransaction(Helper.reverse(contractAddr),acct0,account,30000,0,func, true);
                System.out.println(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static TstSdk getTstSdk() throws Exception {

//        String ip = "http://polaris1.ont.io";
//        String ip = "http://139.219.129.55";
//        String ip = "http://101.132.193.149";
        String ip = "http://127.0.0.1";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        TstSdk wm = TstSdk.getInstance();
        wm.setRestful(restUrl);

//        wm.setRestful("http://polaris1.ont.io:20334");
//        wm.setRestful("http://192.168.50.121:9099");
        //
        wm.setDefaultConnect(wm.getRestful());
        wm.openWalletFile("AuthDemo.json");
        return wm;
    }
}
