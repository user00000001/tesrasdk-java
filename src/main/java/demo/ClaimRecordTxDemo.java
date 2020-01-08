package demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Common;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.block.Block;
import com.github.TesraSupernet.crypto.SignatureScheme;
import com.github.TesraSupernet.sdk.info.AccountInfo;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaimRecordTxDemo {
    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();




            String password = "111111";

            Account payerAccInfo = tstSdk.getWalletMgr().createAccount(password);
            com.github.TesraSupernet.account.Account payerAcc = tstSdk.getWalletMgr().getAccount(payerAccInfo.address,password,payerAccInfo.getSalt());


            if (tstSdk.getWalletMgr().getWallet().getIdentities().size() < 2) {
                Identity identity = tstSdk.getWalletMgr().createIdentity(password);

                tstSdk.nativevm().tstId().sendRegister(identity,password,payerAcc,tstSdk.DEFAULT_GAS_LIMIT,0);

                Identity identity2 = tstSdk.getWalletMgr().createIdentity(password);

                tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcc,tstSdk.DEFAULT_GAS_LIMIT,0);

                tstSdk.getWalletMgr().writeWallet();

                Thread.sleep(6000);
            }

            List<Identity> dids = tstSdk.getWalletMgr().getWallet().getIdentities();


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Issuer", dids.get(0).tstid);
            map.put("Subject", dids.get(1).tstid);

            Map clmRevMap = new HashMap();
            clmRevMap.put("typ","AttestContract");
            clmRevMap.put("addr",dids.get(1).tstid.replace(Common.didtst,""));

            String claim = tstSdk.nativevm().tstId().createTstIdClaim(dids.get(0).tstid,password,dids.get(0).controls.get(0).getSalt(), "claim:context", map, map,clmRevMap,System.currentTimeMillis()/1000 +100000);
            System.out.println(claim);

            boolean b = tstSdk.nativevm().tstId().verifyTstIdClaim(claim);
            System.out.println(b);

//            System.exit(0);

            Account account = tstSdk.getWalletMgr().importAccount("blDuHRtsfOGo9A79rxnJFo2iOMckxdFDfYe2n6a9X+jdMCRkNUfs4+C4vgOfCOQ5","111111","AazEvfQPcQ2GEFFPLF1ZLwQ7K5jDn81hve",Base64.getDecoder().decode("0hAaO6CT+peDil9s5eoHyw=="));
            AccountInfo info = tstSdk.getWalletMgr().getAccountInfo(account.address,"111111",account.getSalt());
            com.github.TesraSupernet.account.Account account1 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("75de8489fcb2dcaf2ef3cd607feffde18789de7da129b5e97c81e001793cb7cf"),SignatureScheme.SHA256WITHECDSA);


            String[] claims = claim.split("\\.");

            JSONObject payload = JSONObject.parseObject(new String(Base64.getDecoder().decode(claims[1].getBytes())));

            System.out.println("ClaimId:" + payload.getString("jti"));

//            tstSdk.neovm().claimRecord().setContractAddress("9a4c79ee4379a0b5d10db03553ca7e61e17a8977");
            //
//            String getstatusRes9 = tstSdk.neovm().claimRecord().sendGetStatus(payload.getString("jti"));
//            System.out.println("getstatusResBytes:" + getstatusRes9);

            String commitHash = tstSdk.neovm().claimRecord().sendCommit(dids.get(0).tstid,password,dids.get(0).controls.get(0).getSalt(),dids.get(1).tstid,payload.getString("jti"),account1,tstSdk.DEFAULT_GAS_LIMIT,0);
            System.out.println("commitRes:" + commitHash);
            Thread.sleep(6000);
            Object obj = tstSdk.getConnect().getSmartCodeEvent(commitHash);
            System.out.println(obj);


            System.out.println(Helper.toHexString(dids.get(0).tstid.getBytes()));
            System.out.println(Helper.toHexString(dids.get(1).tstid.getBytes()));
            System.out.println(Helper.toHexString(payload.getString("jti").getBytes()));
            System.out.println(payload.getString("jti"));


            String getstatusRes = tstSdk.neovm().claimRecord().sendGetStatus(payload.getString("jti"));
            System.out.println("getstatusResBytes:" + getstatusRes);
            Thread.sleep(6000);

//            System.exit(0);

            String revokeHash = tstSdk.neovm().claimRecord().sendRevoke(dids.get(0).tstid,password,dids.get(0).controls.get(0).getSalt(),payload.getString("jti"),account1,tstSdk.DEFAULT_GAS_LIMIT,0);
            System.out.println("revokeRes:" + revokeHash);
            Thread.sleep(6000);
            System.out.println(tstSdk.getConnect().getSmartCodeEvent(revokeHash));


            String getstatusRes2 = tstSdk.neovm().claimRecord().sendGetStatus(payload.getString("jti"));

            System.out.println("getstatusResBytes2:" + getstatusRes2);

            System.exit(0);


//            boolean b = tstSdk.getTstIdTx().verifyTstIdClaim(claim);
//            System.out.println(b);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://dapp2.tesra.me";
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

        wm.openWalletFile("ClaimRecordTxDemo.json");

        return wm;
    }
}

