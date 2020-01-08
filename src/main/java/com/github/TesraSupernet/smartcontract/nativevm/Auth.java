package com.github.TesraSupernet.smartcontract.nativevm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.ErrorCode;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.VmType;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;
import com.github.TesraSupernet.sdk.exception.SDKException;
import com.github.TesraSupernet.smartcontract.nativevm.abi.NativeBuildParams;
import com.github.TesraSupernet.smartcontract.nativevm.abi.Struct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Auth {
    private TstSdk sdk;
    private final String contractAddress = "0000000000000000000000000000000000000006";
    public Auth(TstSdk sdk) {
        this.sdk = sdk;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String sendInit(String adminTstId,String password,byte[] salt, String contractAddr,Account payerAcct,long gaslimit,long gasprice) throws Exception {
        if(adminTstId ==null || adminTstId.equals("")){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryWriter bw = new BinaryWriter(bos);
        bw.writeVarBytes(adminTstId.getBytes());
        Transaction tx = sdk.vm().makeInvokeCodeTransaction(contractAddr,"initContractAdmin",null, payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,adminTstId,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (!b) {
            throw new SDKException(ErrorCode.SendRawTxError);
        }
        return tx.hash().toHexString();
    }


    /**
     *
     * @param adminTstId
     * @param password
     * @param contractAddr
     * @param newAdminTstID
     * @param keyNo
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String sendTransfer(String adminTstId, String password,byte[] salt,long keyNo,  String contractAddr, String newAdminTstID, Account payerAcct, long gaslimit, long gasprice) throws Exception {
        if(adminTstId ==null || adminTstId.equals("") || contractAddr == null || contractAddr.equals("") || newAdminTstID==null || newAdminTstID.equals("")||payerAcct==null){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo <0 || gaslimit <0 || gasprice <0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gasprice should not be less than 0"));
        }
        Transaction tx = makeTransfer(adminTstId,contractAddr,newAdminTstID,keyNo,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,adminTstId,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (!b) {
            throw new SDKException(ErrorCode.SendRawTxError);
        }
        return tx.hash().toHexString();
    }

    /**
     *
     * @param adminTstID
     * @param contractAddr
     * @param newAdminTstID
     * @param keyNo
     * @param payer
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws SDKException
     */
    public Transaction makeTransfer(String adminTstID,String contractAddr, String newAdminTstID,long keyNo,String payer,long gaslimit,long gasprice) throws SDKException {
        if(adminTstID ==null || adminTstID.equals("") || contractAddr == null || contractAddr.equals("") || newAdminTstID==null || newAdminTstID.equals("")){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo <0 || gaslimit <0 || gasprice <0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gasprice should not be less than 0"));
        }

        List list = new ArrayList();
        list.add(new Struct().add(Helper.hexToBytes(contractAddr),newAdminTstID.getBytes(),keyNo));
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"transfer",arg,payer,gaslimit,gasprice);
        return tx;
    }

    /**
     *
     * @param tstid
     * @param password
     * @param contractAddr
     * @param funcName
     * @param keyNo
     * @return
     * @throws Exception
     */
    public String verifyToken(String tstid,String password,byte[] salt,long keyNo, String contractAddr,String funcName) throws Exception {
        if(tstid ==null || tstid.equals("") || password ==null || password.equals("")|| contractAddr == null || contractAddr.equals("") || funcName==null || funcName.equals("")){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo < 0){
            throw new SDKException(ErrorCode.ParamErr("key or gaslimit or gas price should not be less than 0"));
        }
        Transaction tx = makeVerifyToken(tstid,contractAddr,funcName,keyNo);
        sdk.signTx(tx,tstid,password,salt);
        Object obj = sdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
        if (obj == null){
            throw new SDKException(ErrorCode.OtherError("sendRawTransaction PreExec error: "));
        }
        return ((JSONObject)obj).getString("Result");
    }

    /**
     *
     * @param tstid
     * @param contractAddr
     * @param funcName
     * @param keyNo
     * @return
     * @throws SDKException
     */
    public Transaction makeVerifyToken(String tstid,String contractAddr,String funcName,long keyNo) throws SDKException {
        if(tstid ==null || tstid.equals("")|| contractAddr == null || contractAddr.equals("") || funcName==null || funcName.equals("")){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo < 0){
            throw new SDKException(ErrorCode.ParamErr("key or gaslimit or gas price should not be less than 0"));
        }
        List list = new ArrayList();
        list.add(new Struct().add(Helper.hexToBytes(contractAddr),tstid.getBytes(),funcName.getBytes(),keyNo));
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"verifyToken",arg,null,0,0);
        return tx;
    }

    /**
     *
     * @param adminTstID
     * @param password
     * @param contractAddr
     * @param role
     * @param funcName
     * @param keyNo
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String assignFuncsToRole(String adminTstID,String password,byte[] salt, long keyNo,String contractAddr,String role,String[] funcName,Account payerAcct,long gaslimit,long gasprice) throws Exception {
        if(adminTstID ==null || adminTstID.equals("") || contractAddr == null || contractAddr.equals("") || role==null || role.equals("") || funcName == null || funcName.length == 0||payerAcct==null){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo < 0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gas price should not be less than 0"));
        }
        Transaction tx = makeAssignFuncsToRole(adminTstID,contractAddr,role,funcName,keyNo,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,adminTstID,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if(b){
            return tx.hash().toHexString();
        }
        return null;
    }

    /**
     *
     * @param adminTstID
     * @param contractAddr
     * @param role
     * @param funcName
     * @param keyNo
     * @param payer
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws SDKException
     */
    public Transaction makeAssignFuncsToRole(String adminTstID,String contractAddr,String role,String[] funcName,long keyNo,String payer,long gaslimit,long gasprice) throws SDKException {
        if(adminTstID ==null || adminTstID.equals("") || contractAddr == null || contractAddr.equals("") || role==null || role.equals("") || funcName == null || funcName.length == 0
                || payer==null || payer.equals("")){
            throw new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo < 0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gas price should not be less than 0"));
        }

        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(Helper.hexToBytes(contractAddr),adminTstID.getBytes(),role.getBytes());
        struct.add(funcName.length);
        for (int i = 0; i < funcName.length; i++) {
            struct.add(funcName[i]);
        }
        struct.add(keyNo);
        list.add(struct);
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"assignFuncsToRole",arg,payer,gaslimit,gasprice);
        return tx;
    }

    /**
     *
     * @param adminTstId
     * @param password
     * @param contractAddr
     * @param role
     * @param tstIDs
     * @param keyNo
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String assignTstIdsToRole(String adminTstId,String password,byte[] salt,long keyNo, String contractAddr,String role,String[] tstIDs,Account payerAcct,long gaslimit,long gasprice) throws Exception {
        if(adminTstId == null || adminTstId.equals("") || password==null || password.equals("") || contractAddr== null || contractAddr.equals("") ||
                role == null || role.equals("") || tstIDs==null || tstIDs.length == 0){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo<0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gasprice should not be less than 0"));
        }
        Transaction tx = makeAssignTstIDsToRole(adminTstId,contractAddr,role,tstIDs,keyNo,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,adminTstId,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if(b){
            return tx.hash().toHexString();
        }
        return null;
    }

    /**
     *
     * @param adminTstId
     * @param contractAddr
     * @param role
     * @param tstIDs
     * @param keyNo
     * @param payer
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws SDKException
     */
    public Transaction makeAssignTstIDsToRole(String adminTstId,String contractAddr,String role,String[] tstIDs,long keyNo,String payer,long gaslimit,long gasprice) throws SDKException {
        if(adminTstId == null || adminTstId.equals("") || contractAddr== null || contractAddr.equals("") ||
                role == null || role.equals("") || tstIDs==null || tstIDs.length == 0){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo <0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gasprice should not be less than 0"));
        }
        byte[][] tstId = new byte[tstIDs.length][];
        for(int i=0; i< tstIDs.length ; i++){
            tstId[i] = tstIDs[i].getBytes();
        }
        List list = new ArrayList();
        Struct struct = new Struct();
        struct.add(Helper.hexToBytes(contractAddr),adminTstId.getBytes(),role.getBytes());
        struct.add(tstId.length);
        for(int i =0;i<tstId.length;i++){
            struct.add(tstId[i]);
        }
        struct.add(keyNo);
        list.add(struct);
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"assignTstIDsToRole",arg,payer,gaslimit,gasprice);
        return tx;
    }

    /**
     *
     * @param tstid
     * @param password
     * @param contractAddr
     * @param toTstId
     * @param role
     * @param period
     * @param level
     * @param keyNo
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String delegate(String tstid,String password,byte[] salt, long keyNo,String contractAddr,String toTstId,String role,long period,long level,Account payerAcct,long gaslimit,long gasprice) throws Exception {
        if(tstid == null || tstid.equals("") ||password == null || password.equals("") || contractAddr == null || contractAddr.equals("") ||toTstId==null || toTstId.equals("")||
                role== null || role.equals("") ||payerAcct==null){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(period<0 || level <0 || keyNo <0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("period level key gaslimit or gasprice should not be less than 0"));
        }
        Transaction tx = makeDelegate(tstid,contractAddr,toTstId,role,period,level,keyNo,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,tstid,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if(b){
            return tx.hash().toHexString();
        }
        return null;
    }

    /**
     *
     * @param tstid
     * @param contractAddr
     * @param toAddr
     * @param role
     * @param period
     * @param level
     * @param keyNo
     * @param payer
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws SDKException
     */
    public Transaction makeDelegate(String tstid,String contractAddr,String toAddr,String role,long period,long level,long keyNo,String payer,long gaslimit,long gasprice) throws SDKException {
        if(tstid == null || tstid.equals("")|| contractAddr == null || contractAddr.equals("") ||toAddr==null || toAddr.equals("")||
                role== null || role.equals("") || payer ==null || payer.equals("")){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(period<0 || level <0 || keyNo <0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("period level keyNo gaslimit or gasprice should not be less than 0"));
        }

        List list = new ArrayList();
        list.add(new Struct().add(Helper.hexToBytes(contractAddr),tstid.getBytes(),toAddr.getBytes(),role.getBytes(),period,level,keyNo));
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"delegate",arg,payer,gaslimit,gasprice);
        return tx;
    }

    /**
     *
     * @param initiatorTstid
     * @param password
     * @param contractAddr
     * @param delegate
     * @param role
     * @param keyNo
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String withdraw(String initiatorTstid,String password,byte[] salt,long keyNo, String contractAddr,String delegate, String role,Account payerAcct,long gaslimit,long gasprice) throws Exception {
        if(initiatorTstid == null || initiatorTstid.equals("")|| password ==null|| password.equals("") || contractAddr == null || contractAddr.equals("") ||
                role== null || role.equals("") || payerAcct==null){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo <0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("keyNo or gaslimit or gasprice should not be less than 0"));
        }
        Transaction tx = makeWithDraw(initiatorTstid,contractAddr,delegate,role,keyNo,payerAcct.getAddressU160().toBase58(),gaslimit,gasprice);
        sdk.signTx(tx,initiatorTstid,password,salt);
        sdk.addSign(tx,payerAcct);
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if(b){
            return tx.hash().toHexString();
        }
        return null;
    }

    /**
     *
     * @param tstid
     * @param contractAddr
     * @param delegate
     * @param role
     * @param keyNo
     * @param payer
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws SDKException
     */
    public Transaction makeWithDraw(String tstid,String contractAddr,String delegate, String role,long keyNo,String payer,long gaslimit,long gasprice) throws SDKException {
        if(tstid == null || tstid.equals("")|| contractAddr == null || contractAddr.equals("") ||
                role== null || role.equals("") || payer ==null || payer.equals("")){
            throw  new SDKException(ErrorCode.ParamErr("parameter should not be null"));
        }
        if(keyNo <0 || gaslimit < 0 || gasprice < 0){
            throw new SDKException(ErrorCode.ParamErr("key gaslimit or gasprice should not be less than 0"));
        }
        List list = new ArrayList();
        list.add(new Struct().add(Helper.hexToBytes(contractAddr),tstid.getBytes(),delegate.getBytes(),role.getBytes(),keyNo));
        byte[] arg = NativeBuildParams.createCodeParamsScript(list);

        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),"withdraw",arg,payer,gaslimit,gasprice);
        return tx;
    }

    public Object queryAuth(String contractAddr, String role, String tstid) throws Exception {
        Object obj = sdk.getConnect().getStorage(contractAddr,contractAddr+Helper.toHexString(role.getBytes())+Helper.toHexString(tstid.getBytes()));
        return obj;
    }
}
class TransferParam implements Serializable {
    byte[] contractAddr;
    byte[] newAdminTstID;
    long KeyNo;
    TransferParam(byte[] contractAddr,byte[] newAdminTstID,long keyNo){
        this.contractAddr = contractAddr;
        this.newAdminTstID = newAdminTstID;
        KeyNo = keyNo;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.contractAddr = reader.readVarBytes();
        this.newAdminTstID = reader.readVarBytes();
        KeyNo = reader.readVarInt();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.newAdminTstID);
        writer.writeVarInt(KeyNo);
    }
}
class VerifyTokenParam implements Serializable{
    byte[] contractAddr;
    byte[] caller;
    byte[] fn;
    long keyNo;
    VerifyTokenParam(byte[] contractAddr,byte[] caller,byte[] fn,long keyNo){
        this.contractAddr = contractAddr;
        this.caller = caller;
        this.fn = fn;
        this.keyNo = keyNo;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {

    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.caller);
        writer.writeVarBytes(this.fn);
        writer.writeVarInt(keyNo);
    }
}

class FuncsToRoleParam implements Serializable{
    byte[] contractAddr;
    byte[] adminTstID;
    byte[] role;
    String[] funcNames;
    long keyNo;

    FuncsToRoleParam(byte[] contractAddr,byte[] adminTstID,byte[] role,String[] funcNames,long keyNo){
        this.contractAddr =contractAddr;
        this.adminTstID = adminTstID;
        this.role =role;
        this.funcNames = funcNames;
        this.keyNo = keyNo;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.contractAddr = reader.readVarBytes();
        this.adminTstID = reader.readVarBytes();
        this.role = reader.readVarBytes();
        int length = (int)reader.readVarInt();
        this.funcNames = new String[length];
        for(int i = 0;i< length;i++){
            this.funcNames[i] = reader.readVarString();
        }
        this.keyNo = reader.readVarInt();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.adminTstID);
        writer.writeVarBytes(this.role);
        writer.writeVarInt(this.funcNames.length);
        for(String name:this.funcNames){
            writer.writeVarString(name);
        }
        writer.writeVarInt(this.keyNo);
    }
}
class TstIDsToRoleParam implements Serializable{
    byte[] contractAddr;
    byte[] adminTstID;
    byte[] role;
    byte[][] persons;
    long keyNo;
    TstIDsToRoleParam( byte[] contractAddr,byte[] adminTstID,byte[] role,byte[][] persons,long keyNo){
        this.contractAddr = contractAddr;
        this.adminTstID = adminTstID;
        this.role = role;
        this.persons = persons;
        this.keyNo = keyNo;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.contractAddr = reader.readVarBytes();
        this.adminTstID = reader.readVarBytes();
        this.role = reader.readVarBytes();
        int length = (int)reader.readVarInt();
        this.persons = new byte[length][];
        for(int i = 0; i< length;i++){
            this.persons[i] = reader.readVarBytes();
        }
        this.keyNo = reader.readVarInt();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.adminTstID);
        writer.writeVarBytes(this.role);
        writer.writeVarInt(this.persons.length);
        for(byte[] p: this.persons){
            writer.writeVarBytes(p);
        }
        writer.writeVarInt(this.keyNo);
    }
}

class DelegateParam implements  Serializable{
    byte[] contractAddr;
    byte[] from;
    byte[] to;
    byte[] role;
    long period;
    long level;
    long keyNo;
    DelegateParam(byte[] contractAddr,byte[] from,byte[] to,byte[] role, long period, long level,long keyNo){
        this.contractAddr = contractAddr;
        this.from = from;
        this.to = to;
        this.role = role;
        this.period = period;
        this.level = level;
        this.keyNo = keyNo;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {

    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.from);
        writer.writeVarBytes(this.to);
        writer.writeVarBytes(this.role);
        writer.writeVarInt(this.period);
        writer.writeVarInt(this.level);
        writer.writeVarInt(this.keyNo);
    }
}

class AuthWithdrawParam implements Serializable{
    byte[] contractAddr;
    byte[] initiator;
    byte[] delegate;
    byte[] role;
    long keyNo;
    public AuthWithdrawParam(byte[] contractAddr,byte[] initiator, byte[] delegate,byte[] role,long keyNo){
        this.contractAddr = contractAddr;
        this.initiator = initiator;
        this.delegate = delegate;
        this.role = role;
        this.keyNo = keyNo;
    }
    @Override
    public void deserialize(BinaryReader reader) throws IOException {

    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(this.contractAddr);
        writer.writeVarBytes(this.initiator);
        writer.writeVarBytes(this.delegate);
        writer.writeVarBytes(this.role);
        writer.writeVarInt(this.keyNo);
    }
}


