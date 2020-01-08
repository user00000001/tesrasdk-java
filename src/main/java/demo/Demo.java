/*
 * Copyright (C) 2019-2020 The TesraSupernet Authors
 * This file is part of The TesraSupernet library.
 *
 *  The TesraSupernet is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The TesraSupernet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with The TesraSupernet.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package demo;

import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.block.Block;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Demo {
    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();


            if(false){
                 Account account = tstSdk.getWalletMgr().importAccount("UcZl6tYzuUwikWnlSBkv1aDrFG80RadPqwoKINRe0+wzCR5o5QqBrrklFXgC5uz9",
                        "LUlu@665211","AKSTMpAL1MWujdmE1oZNBXLTo4yr4sdF3g",Base64.getDecoder().decode("2kMG+JvubSz5Llw07ewiWQ=="));
                System.out.println(account.address);
                com.github.TesraSupernet.account.Account account1 = tstSdk.getWalletMgr().getAccount(account.address,"LUlu@665211");
                System.out.println(account1.exportWif());
                return;
            }


//            System.out.println(tstSdk.getConnect().getBalance("TA5NzM9iE3VT9X8SGk5h3dii6GPFQh2vme"));
//            System.out.println(Helper.toHexString(tstSdk.getConnect().getBlock(1).transactions[0].sigs[0].sigData[0]));
            System.out.println(tstSdk.getConnect().getBlock(15));
            System.out.println(tstSdk.getConnect().getBlockHeight());
            System.out.println(tstSdk.getConnect().getBlockJson(15));
//            System.out.println(tstSdk.getConnect().getBlockJson("ee2d842fe7cdf48bc39b34d616a9e8f7f046970ed0a988dde3fe05c9126cce74"));
            System.out.println(tstSdk.getConnect().getNodeCount());
//            System.out.println(((InvokeCodeTransaction)tstSdk.getConnect().getRawTransaction("c2592940837c2347f6a7b391d4940abb7171dd5dd156b7c031d20a5940142b5a")));
//            System.out.println((tstSdk.getConnect().getTransaction("d441a967315989116bf0afad498e4016f542c1e7f8605da943f07633996c24cc")));
            System.out.println(tstSdk.getConnect().getSmartCodeEvent(0));
//            System.out.println(tstSdk.getConnect().getContractJson("803ca638069742da4b6871fe3d7f78718eeee78a"));
//            System.out.println(tstSdk.getConnect().getMerkleProof("0087217323d87284d21c3539f216dd030bf9da480372456d1fa02eec74c3226d"));
            //System.out.println(tstSdk.getConnect().getBlockHeightByTxHash("7c3e38afb62db28c7360af7ef3c1baa66aeec27d7d2f60cd22c13ca85b2fd4f3"));
            //String v = (String)tstSdk.getConnect().getStorage("ff00000000000000000000000000000000000001", Address.decodeBase58("TA63xZXqdPLtDeznWQ6Ns4UsbqprLrrLJk").toHexString());
            //System.out.println(v);
            Block block = tstSdk.getConnect().getBlock(tstSdk.getConnect().getBlockHeight());
            String hash = block.transactions[0].hash().toHexString();
            System.out.println(tstSdk.getConnect().getMerkleProof(hash));
            Object proof = tstSdk.nativevm().tstId().getMerkleProof(hash);
            System.out.println(proof);
            System.out.println(tstSdk.nativevm().tstId().verifyMerkleProof(JSON.toJSONString(proof)));
            System.exit(0);
            List list = (List) tstSdk.getConnect().getSmartCodeEvent("a12117c319aa6906efd8869ba65c221f4e2ee44a8a2766fd326c8d7125beffbf");

            List states = (List) ((Map) (list.get(0))).get("States");
            List state1 = (List) states.get(0);

            byte[] bys = new byte[state1.toArray().length];
            for (int i = 0; i < bys.length; i++) {
                bys[i] = (byte) ((int) state1.get(i) & 0xff);
            }
            System.out.println(Address.parse(Helper.toHexString(bys)).toBase58());
            System.exit(0);


            tstSdk.getWalletMgr().createAccount("123456");
            System.out.println(tstSdk.getWalletMgr().getWallet());
            System.out.println(tstSdk.getWalletMgr().getWalletFile());
            System.exit(0);
            System.out.println(tstSdk.getWalletMgr().getWallet().getAccounts().get(0));
            tstSdk.getWalletMgr().getWallet().removeAccount(tstSdk.getWalletMgr().getWallet().getAccounts().get(0).address);
            tstSdk.getWalletMgr().writeWallet();
            System.out.println(tstSdk.getWalletMgr().getWallet());
            tstSdk.getWalletMgr().getWallet().setName("name");

            System.exit(0);
            Account acct = tstSdk.getWalletMgr().createAccount("password");
            Identity identity = tstSdk.getWalletMgr().createIdentity("password");
            //Block block = tstSdk.getConnectManager().getBlock(757);
            System.out.println(tstSdk.getConnect().getNodeCount());
            // tstSdk.getOepMgr().getAccount(tstSdk.getOepMgr().getAccounts().get(0).address,"1234567");

            Account info = tstSdk.getWalletMgr().createAccount("123456");
            tstSdk.getWalletMgr().writeWallet();
            //   tstSdk.getOepMgr().createTstId("123456");
            //  AccountInfo info2 = tstSdk.getWalletMgr().getAccountInfo(info.address,"123456");
            //  System.out.println(info2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://52.184.29.110";
        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.6";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("Demo3.json");

        return wm;
    }
}
