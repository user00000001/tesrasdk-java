package com.github.TesraSupernet.sidechain.smartcontract.tsgx;

import com.alibaba.fastjson.JSONObject;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.ErrorCode;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.asset.State;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.utils;
import com.github.TesraSupernet.network.exception.ConnectorException;
import com.github.TesraSupernet.sdk.exception.SDKException;
import com.github.TesraSupernet.smartcontract.nativevm.abi.NativeBuildParams;
import com.github.TesraSupernet.smartcontract.nativevm.abi.Struct;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TsgX {

    private TstSdk sdk;
    private final String tsgXContract = "0000000000000000000000000000000000000002";

    public TsgX(TstSdk sdk) {
        this.sdk = sdk;
    }
    public String getContractAddress() {
        return tsgXContract;
    }


    public String sendTransfer(Account sendAcct, String recvAddr, long amount, Account payerAcct, long gaslimit, long gasprice) throws Exception {
        Transaction tx = sdk.nativevm().tsg().makeTransfer(sendAcct.getAddressU160().toBase58(),recvAddr,amount,
                payerAcct.getAddressU160().toBase58(), gaslimit,gasprice);
        sdk.signTx(tx, new Account[][]{{sendAcct}});
        if(!sendAcct.equals(payerAcct)){
            sdk.addSign(tx, payerAcct);
        }
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.toHexString();
    }

    public Transaction makeTransfer(String sendAddr, String recvAddr, long amount, String payer, long gaslimit, long gasprice) throws Exception {
        return sdk.nativevm().tsg().makeTransfer(sendAddr,recvAddr,amount,payer,gaslimit,gasprice);
    }
    public Transaction makeTransfer(State[] states, String payer, long gaslimit, long gasprice) throws Exception {
        return sdk.nativevm().tsg().makeTransfer(states,payer,gaslimit,gasprice);
    }

    public String sendApprove(Account sendAcct, String recvAddr, long amount, Account payerAcct, long gaslimit, long gasprice) throws Exception {
        Transaction tx = sdk.nativevm().tsg().makeApprove(sendAcct.getAddressU160().toBase58(),recvAddr,amount,
                payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx, new Account[][]{{sendAcct}});
        if(!sendAcct.equals(payerAcct)){
            sdk.addSign(tx, payerAcct);
        }
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();
    }
    public Transaction makeApprove(String sendAddr,String recvAddr,long amount,String payer,long gaslimit,long gasprice) throws Exception {
        return sdk.nativevm().tsg().makeApprove(sendAddr,recvAddr,amount,payer,gaslimit,gasprice);
    }

    public String sendTransferFrom(Account sendAcct, String fromAddr, String toAddr, long amount, Account payerAcct, long gaslimit, long gasprice) throws Exception {
        Transaction tx = sdk.nativevm().tsg().makeTransferFrom(sendAcct.getAddressU160().toBase58(),fromAddr,toAddr,amount,
                payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx, new Account[][]{{sendAcct}});
        if(!sendAcct.equals(payerAcct)){
            sdk.addSign(tx, payerAcct);
        }
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();
    }

    public Transaction makeTransferFrom(String sendAddr, String fromAddr, String toAddr,long amount,String payer,long gaslimit,long gasprice) throws Exception {
        return sdk.nativevm().tsg().makeTransferFrom(sendAddr,fromAddr,toAddr,amount,payer,gaslimit,gasprice);
    }

    public String queryName() throws Exception {
        Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "name", new byte[]{0}, (String)null, 0L, 0L);
        Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
        String res = ((JSONObject)obj).getString("Result");
        return new String(Helper.hexToBytes(res));
    }

    /**
     * @return
     * @throws Exception
     */
    public String querySymbol() throws Exception {
        Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "symbol", new byte[]{0}, (String)null, 0L, 0L);
        Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
        String res = ((JSONObject)obj).getString("Result");
        return new String(Helper.hexToBytes(res));
    }

    /**
     * @return
     * @throws Exception
     */
    public long queryDecimals() throws Exception {
        Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "decimals", new byte[]{0}, (String)null, 0L, 0L);
        Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
        String res = ((JSONObject)obj).getString("Result");
        return "".equals(res) ? 0L : Long.valueOf(Helper.reverse(res), 16);
    }

    public long queryTotalSupply() throws Exception {
        Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "totalSupply", new byte[]{0}, (String)null, 0L, 0L);
        Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
        String res = ((JSONObject)obj).getString("Result");
        return res != null && !res.equals("") ? Long.valueOf(Helper.reverse(res), 16) : 0L;
    }

    public String unboundTsg(String address) throws Exception {
        if (address != null && !address.equals("")) {
            String unboundTsgStr = sdk.getSideChainConnectMgr().getAllowance("tsg", Address.parse("0000000000000000000000000000000000000001").toBase58(), address);
            long unboundTsg = Long.parseLong(unboundTsgStr);
            return unboundTsgStr;
        } else {
            throw new SDKException(ErrorCode.OtherError("address should not be null"));
        }
    }
    public long queryBalanceOf(String address) throws Exception {
        if (address != null && !address.equals("")) {
            List list = new ArrayList();
            list.add(Address.decodeBase58(address));
            byte[] arg = NativeBuildParams.createCodeParamsScript(list);
            Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "balanceOf", arg, (String)null, 0L, 0L);
            Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
            String res = ((JSONObject)obj).getString("Result");
            return res != null && !res.equals("") ? Long.valueOf(Helper.reverse(res), 16) : 0L;
        } else {
            throw new SDKException(ErrorCode.OtherError("address should not be null"));
        }
    }

    public long queryAllowance(String fromAddr, String toAddr) throws Exception {
        if (fromAddr != null && !fromAddr.equals("") && toAddr != null && !toAddr.equals("")) {
            List list = new ArrayList();
            list.add((new Struct()).add(new Object[]{Address.decodeBase58(fromAddr), Address.decodeBase58(toAddr)}));
            byte[] arg = NativeBuildParams.createCodeParamsScript(list);
            Transaction tx = this.sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)), "allowance", arg, (String)null, 0L, 0L);
            Object obj = sdk.getSideChainConnectMgr().sendRawTransactionPreExec(tx.toHexString());
            String res = ((JSONObject)obj).getString("Result");
            return res != null && !res.equals("") ? Long.valueOf(Helper.reverse(res), 16) : 0L;
        } else {
            throw new SDKException(ErrorCode.OtherError("parameter should not be null"));
        }
    }

    public String tsgxSetSyncAddr(Account[] accounts,byte[][] allPubkeys,int M,String address, Account payer, long gaslimit, long gasprice) throws Exception {
        if(accounts == null || accounts.length ==0 || address==null|| address.equals("")|| allPubkeys == null || allPubkeys.length < accounts.length
                ||payer == null || gaslimit < 0||gasprice < 0){
            throw new SDKException(ErrorCode.ParamError);
        }
        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(Address.decodeBase58(address));
        list.add(struct);
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)),"setSyncAddr",args,payer.getAddressU160().toBase58(),gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{payer}});
        for(int i=0;i<accounts.length;i++){
            sdk.addMultiSign(tx, M,allPubkeys, accounts[i]);
        }
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();
    }

    public String tsgxSetSyncAddr(Account account,String address, Account payer, long gaslimit, long gasprice) throws Exception {
        if(account == null || address==null|| address.equals("") ||payer == null || gaslimit < 0||gasprice < 0){
            throw new SDKException(ErrorCode.ParamError);
        }
        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(Address.decodeBase58(address));
        list.add(struct);
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)),"setSyncAddr",args,payer.getAddressU160().toBase58(),gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if(!account.equals(payer)){
            sdk.addSign(tx, payer);
        }
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();
    }

    public String tsgSwap(Account account, Swap[] swaps, Account payer, long gaslimit, long gasprice) throws Exception {
        if(account == null || swaps == null|| swaps.length == 0 || payer == null || gaslimit < 0||gasprice < 0){
            throw new SDKException(ErrorCode.ParamError);
        }
        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(swaps.length);
        for(Swap swap : swaps) {
            struct.add(swap.address, swap.value);
        }
        list.add(struct);
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)),"tsgSwap",args,payer.getAddressU160().toBase58(),gaslimit, gasprice);
        sdk.addSign(tx, account);
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();

    }

    public String getSyncAddress() throws ConnectorException, IOException, IllegalAccessException, InstantiationException {
        Object obj = sdk.getSideChainConnectMgr().getStorage(Helper.reverse(tsgXContract), Helper.toHexString("syncAddress".getBytes()));
        if(obj == null) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(Helper.hexToBytes((String)obj));
        BinaryReader reader = new BinaryReader(in);
        Address address = utils.readAddress(reader);
        return address.toBase58();
    }

    public String tsgxSwap(Account account, Swap swap, Account payer, long gaslimit, long gasprice) throws Exception {
        if(account == null || swap == null|| swap.value <=0 || payer == null || gaslimit < 0||gasprice < 0){
            throw new SDKException(ErrorCode.ParamError);
        }
        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(swap.address, swap.value);
        list.add(struct);
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(tsgXContract)),"tsgxSwap",args,payer.getAddressU160().toBase58(),gaslimit, gasprice);
        sdk.addSign(tx, account);
        sdk.getSideChainConnectMgr().sendRawTransaction(tx.toHexString());
        return tx.hash().toHexString();
    }
}
