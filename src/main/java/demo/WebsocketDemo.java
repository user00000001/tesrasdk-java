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

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.block.Block;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.io.Serializable;
import com.github.TesraSupernet.smartcontract.neovm.abi.AbiFunction;
import com.github.TesraSupernet.smartcontract.neovm.abi.AbiInfo;
import com.github.TesraSupernet.sdk.wallet.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;
import com.github.TesraSupernet.sdk.wallet.Wallet;
import com.github.TesraSupernet.network.websocket.MsgQueue;
import com.github.TesraSupernet.network.websocket.Result;
import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */

public class WebsocketDemo {
    public static Object lock = new Object();

    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();
            String password = "passwordtest";
            Account payer = tstSdk.getWalletMgr().createAccount(password);

            tstSdk.getWebSocket().startWebsocketThread(false);

            Thread thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            waitResult(lock);
                        }
                    });
            thread.start();
            Thread.sleep(5000);

            Wallet oep6 = tstSdk.getWalletMgr().getWallet();
            System.out.println("oep6:" + JSON.toJSONString(oep6));
            //System.exit(0);

            //System.out.println("================register=================");
//            Identity ident = null;
//            if (tstSdk.getWalletMgr().getIdentitys().size() == 0) {
//                ident = tstSdk.neovm().tstId().sendRegister("passwordtest","payer",0);
//            } else {
//                ident = tstSdk.getWalletMgr().getIdentitys().get(0);
//            }

//            String tstid = ident.tstid;


            for (int i = 0; i >= 0; i++) {

                tstSdk.getConnect().getNetworkId();
                tstSdk.getConnect().getGrantTsg("AHX1wzvdw9Yipk7E9MuLY4GGX4Ym9tHeDe");

                //System.out.println(tstid);
                //String hash = tstSdk.getTstIdTx().updateAttribute(tstid, "passwordtest", attri.getBytes(), "Json".getBytes(), JSON.toJSONString(recordMap).getBytes());
                //System.out.println("hash:" + hash);



                if (false) {
                    Account info1 = null;
                    Account info2 = null;
                    Account info3 = null;
                    if (tstSdk.getWalletMgr().getWallet().getAccounts().size() < 3) {
                        info1 = tstSdk.getWalletMgr().createAccountFromPriKey("passwordtest", "9a31d585431ce0aa0aab1f0a432142e98a92afccb7bcbcaff53f758df82acdb3");
                        info2 = tstSdk.getWalletMgr().createAccount("passwordtest");
                        info3 = tstSdk.getWalletMgr().createAccount("passwordtest");
                        tstSdk.getWalletMgr().writeWallet();
                    }
                    info1 = tstSdk.getWalletMgr().getWallet().getAccounts().get(0);
                    info2 = tstSdk.getWalletMgr().getWallet().getAccounts().get(1);
                    Transaction tx = tstSdk.nativevm().tst().makeTransfer( info1.address, info2.address, 100L,payer.address, tstSdk.DEFAULT_GAS_LIMIT,0);
                    tstSdk.signTx(tx, info1.address, password,new byte[]{});
                    System.out.println(tx.toHexString());
                    tstSdk.getConnect().sendRawTransaction(tx.toHexString());
                }


                //waitResult(tstSdk, lock);

                if (false) {
                    tstSdk.getConnect().getBalance("TA63xZXqdPLtDeznWQ6Ns4UsbqprLrrLJk");
                    tstSdk.getConnect().getBlockJson("c8c165bf0ac6107f7f324b0badb60af4dc4e1157b5eb9d3163c8f332a8612c98");
                    tstSdk.getConnect().getNodeCount();
                    tstSdk.getConnect().getContractJson("80e7d2fc22c24c466f44c7688569cc6e6d6c6f92");
                    tstSdk.getConnect().getSmartCodeEvent("7c3e38afb62db28c7360af7ef3c1baa66aeec27d7d2f60cd22c13ca85b2fd4f3");
                    tstSdk.getConnect().getBlockHeightByTxHash("7c3e38afb62db28c7360af7ef3c1baa66aeec27d7d2f60cd22c13ca85b2fd4f3");
                    tstSdk.getConnect().getStorage("ff00000000000000000000000000000000000001", Address.decodeBase58("TA63xZXqdPLtDeznWQ6Ns4UsbqprLrrLJk").toHexString());
                    tstSdk.getConnect().getTransactionJson("7c3e38afb62db28c7360af7ef3c1baa66aeec27d7d2f60cd22c13ca85b2fd4f3");
                }
                if (false) {

                    InputStream is = new FileInputStream("C:\\ZX\\huguanjun.abi.json");//IdContract
                    byte[] bys = new byte[is.available()];
                    is.read(bys);
                    is.close();
                    String abi = new String(bys);

                    AbiInfo abiinfo = JSON.parseObject(abi, AbiInfo.class);
                    //System.out.println("Entrypoint:" + abiinfo.getEntrypoint());
                    //System.out.println("Functions:" + abiinfo.getFunctions());

                    AbiFunction func0 = abiinfo.getFunction("Put");
                    Identity did0 = tstSdk.getWalletMgr().getWallet().getIdentities().get(0);
                    func0.setParamsValue("key".getBytes(), "value".getBytes());
                }
                if(true){
                    Map map = new HashMap();
                    if(i >0) {
                        map.put("SubscribeEvent", true);
                        map.put("SubscribeRawBlock", false);
                    }else{
                        map.put("SubscribeJsonBlock", false);
                        map.put("SubscribeRawBlock", true);
                    }
                    //System.out.println(map);
                    tstSdk.getWebSocket().setReqId(i);
                    tstSdk.getWebSocket().sendSubscribe(map);
//                    tstSdk.getWebSocket().getBlockHeight();
                }
                Thread.sleep(6000);
            }

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitResult(Object lock) {
        try {
            synchronized (lock) {
                while (true) {
                    lock.wait();
                    for (String e : MsgQueue.getResultSet()) {
                        System.out.println("RECV: " + e);
                        Result rt = JSON.parseObject(e, Result.class);
                        //TODO
                        MsgQueue.removeResult(e);
                        if (rt.Action.equals("getblockbyheight")) {
                            Block bb = Serializable.from(Helper.hexToBytes((String) rt.Result), Block.class);
                            //System.out.println(bb.json());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {
        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.46";
//        String ip = "http://52.229.166.6";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setWesocket(wsUrl, lock);
        wm.setDefaultConnect(wm.getWebSocket());
        wm.openWalletFile("TstAssetDemo.json");
        return wm;
    }
}
