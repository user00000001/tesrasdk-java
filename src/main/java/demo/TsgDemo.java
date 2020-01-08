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
import com.github.TesraSupernet.core.asset.Sig;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.crypto.SignatureScheme;


/**
 *
 */
public class TsgDemo {
    public static String privatekey1 = "49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221";
    public static String privatekey2 = "1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96";
    public static String privatekey3 = "bc254cf8d3910bc615ba6bf09d4553846533ce4403bc24f58660ae150a6d64cf";
    public static String privatekey4 = "06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8";
    public static String privatekey5 = "f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549";
    public static String privatekey6 = "0bc8c1f75a028672cd42c221bf81709dfc7abbbaf0d87cb6fdeaf9a20492c194";
    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();
            String privatekey0 = "c19f16785b8f3543bbaf5e1dbb5d398dfa6c85aaad54fc9d71203ce83e505c07";
            privatekey0 = "523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f";
            com.github.TesraSupernet.account.Account payerAcct = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0),tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct0 = payerAcct;
            com.github.TesraSupernet.account.Account acct1 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey2), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct3 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey3), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct4 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey4), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct5 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey5), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct6 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey6), tstSdk.defaultSignScheme);
            System.out.println("acct0:" + acct0.getAddressU160().toBase58());
            System.out.println("acct1:" + acct1.getAddressU160().toBase58());
            System.out.println("acct2:" + acct2.getAddressU160().toBase58());


            if(true){//sendTransferFromMultiSignAddr
                com.github.TesraSupernet.account.Account acct00 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("dcb22fdeb1cd57c4ad82c8dc21dd6792d4b1e90b5aa06d6698c03eacddabeb1f"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct01 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct02 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct03 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct04 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("0638dff2f03964883471e1dac3df9e7738f21fd2452aef4846c11a53be6feb0e"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct05 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("46027c9786e24ecc1b4d7b406dfe90ec30b2c2fa6ad2f7963df251200e7f003d"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct06 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct07 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96"),tstSdk.defaultSignScheme);

//            System.out.println("##"+Helper.toHexString(acct00.serializePublicKey()));
//            System.out.println(Helper.toHexString(acct11.serializePublicKey()));
//            System.out.println(Helper.toHexString(acct22.serializePublicKey()));
//            System.out.println(Helper.toHexString(acct33.serializePublicKey()));
                int M = 5;
                com.github.TesraSupernet.account.Account[] accounts = new com.github.TesraSupernet.account.Account[]{acct00,acct01,acct02,acct03,acct04,acct05,acct06,acct07};
                byte[][] pks = new byte[accounts.length][];
                for(int i=0;i<pks.length;i++){
                    pks[i] = accounts[i].serializePublicKey();
                }
                Address multiAddr = Address.addressFromMultiPubKeys(M,pks);

                tstSdk.nativevm().tst().sendTransferFromMultiSignAddr(M,pks,new com.github.TesraSupernet.account.Account[]{acct00,acct01,acct02,acct03,acct04},acct1.getAddressU160().toBase58(),5,acct0,tstSdk.DEFAULT_GAS_LIMIT,0);
                System.exit(0);
            }
            if(true){ //M < n
                com.github.TesraSupernet.account.Account acct00 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("dcb22fdeb1cd57c4ad82c8dc21dd6792d4b1e90b5aa06d6698c03eacddabeb1f"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct01 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct02 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct03 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8"),SignatureScheme.SM3WITHSM2);
                com.github.TesraSupernet.account.Account acct04 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("0638dff2f03964883471e1dac3df9e7738f21fd2452aef4846c11a53be6feb0e"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct05 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("46027c9786e24ecc1b4d7b406dfe90ec30b2c2fa6ad2f7963df251200e7f003d"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct06 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account acct07 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96"),tstSdk.defaultSignScheme);
                com.github.TesraSupernet.account.Account[] accounts = new com.github.TesraSupernet.account.Account[]{acct00,acct01,acct02,acct03,acct04,acct05,acct06,acct07};
                byte[][] pks = new byte[accounts.length][];
                for(int i=0;i<pks.length;i++){
                    pks[i] = accounts[i].serializePublicKey();
                }
                int M = 5;
                Address multiAddr = Address.addressFromMultiPubKeys(M,pks);
                System.out.println("multiAddr:"+multiAddr.toBase58());
                Transaction tx = tstSdk.nativevm().tst().makeTransfer(multiAddr.toBase58(), acct1.getAddressU160().toBase58(), 2, payerAcct.getAddressU160().toBase58(), 30000, 0);

                for (int i = 0; i< M; i++) {
                    tstSdk.addMultiSign(tx, M, pks, accounts[i]);
                }
                tstSdk.addSign(tx, payerAcct);
                boolean b = tstSdk.getConnect().sendRawTransaction(tx.toHexString());
                System.exit(0);
            }
            if (false) {
                String hash = tstSdk.nativevm().tsg().sendApprove(acct0,acct1.getAddressU160().toBase58(),100,payerAcct,30000,0);
                System.out.println(hash);
//                Thread.sleep(6000);
                System.out.println(tstSdk.nativevm().tsg().queryAllowance(acct6.getAddressU160().toBase58(), acct1.getAddressU160().toBase58()));
//                System.out.println("acct0:" + tstSdk.getConnect().getBalance(acct0.getAddressU160().toBase58()));
//                System.out.println("acct1:" + tstSdk.getConnect().getBalance(acct1.getAddressU160().toBase58()));
//                System.out.println("acct2:" + tstSdk.getConnect().getBalance(acct2.getAddressU160().toBase58()));
//                System.out.println(tstSdk.getConnect().getAllowance("tst",acct0.getAddressU160().toBase58(), acct1.getAddressU160().toBase58()));
            }
            if (false) {
                String hash = tstSdk.nativevm().tsg().sendTransferFrom(acct1,acct6.getAddressU160().toBase58(),acct1.getAddressU160().toBase58(),100,payerAcct,30000,0);
                Thread.sleep(6000);
                System.out.println(tstSdk.getConnect().getSmartCodeEvent(hash));
            }
            if(false){
                System.out.println(tstSdk.nativevm().tsg().queryBalanceOf(acct6.getAddressU160().toBase58()));
                //System.out.println(tstSdk.nativevm().tst().queryTotalSupply());
//                System.exit(0);
                //String hash = tstSdk.nativevm().tst().sendTransfer(acct0,acct1.getAddressU160().toBase58(),11,acct0,tstSdk.DEFAULT_GAS_LIMIT,0);
                //System.out.println(hash);
                //Thread.sleep(6000);

               // tstSdk.nativevm().tsg().claimTsg(acct0,acct0.getAddressU160().toBase58(),49520000000000L,acct0,tstSdk.DEFAULT_GAS_LIMIT,0);
            }
            if(false){
                System.out.println(tstSdk.nativevm().tsg().unboundTsg(acct6.getAddressU160().toBase58()));
                System.out.println(tstSdk.nativevm().tsg().queryName());
                System.out.println(tstSdk.nativevm().tsg().querySymbol());
                System.out.println(tstSdk.nativevm().tsg().queryDecimals());
                System.out.println(tstSdk.nativevm().tsg().queryTotalSupply());
                System.out.println(tstSdk.nativevm().tsg().queryBalanceOf(acct0.getAddressU160().toBase58()));
                System.out.println(tstSdk.nativevm().tsg().queryAllowance(acct0.getAddressU160().toBase58(),acct0.getAddressU160().toBase58()));

            }
            if(false){

                String hash = tstSdk.nativevm().tst().sendTransfer(acct6,acct1.getAddressU160().toBase58(),1000L,payerAcct,30000,0);

//                String hash = tstSdk.nativevm().tst().sendTransfer(acct0, acct1.getAddressU160().toBase58(), 30L, payerAcct, 30000, 0);
                Thread.sleep(6000);
                System.out.println(tstSdk.getConnect().getSmartCodeEvent(hash));
            }
            if(true){
                System.out.println(tstSdk.nativevm().tsg().unboundTsg(acct0.getAddressU160().toBase58()));
                String hash = tstSdk.nativevm().tsg().withdrawTsg(acct0, acct0.getAddressU160().toBase58(), 1459535000000000L, payerAcct, 30000, 0);
                Thread.sleep(6000);
                System.out.println(tstSdk.getConnect().getSmartCodeEvent(hash));
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
        wm.setDefaultConnect(wm.getRestful());
        wm.openWalletFile("TstAssetDemo.json");
        return wm;
    }
}
