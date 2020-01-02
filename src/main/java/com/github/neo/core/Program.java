package com.github.neo.core;


import com.github.TesraSupernet.common.Common;
import com.github.TesraSupernet.common.ErrorCode;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.scripts.ScriptBuilder;
import com.github.TesraSupernet.core.scripts.ScriptOp;
import com.github.TesraSupernet.crypto.ECC;
import com.github.TesraSupernet.crypto.KeyType;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.io.Serializable;
import com.github.TesraSupernet.sdk.exception.SDKException;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 */
public class Program implements Serializable {
    public byte[] parameter;
    public byte[] code;
    public Program(){}
    @Override
    public void deserialize(BinaryReader reader) throws IOException {
    	parameter = reader.readVarBytes();	// sign data
    	code = reader.readVarBytes();		// pubkey
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
    	writer.writeVarBytes(parameter);
    	writer.writeVarBytes(code);
    }
    public static byte[] ProgramFromParams(byte[][] sigData) throws IOException {
        return com.github.TesraSupernet.core.program.Program.ProgramFromParams(sigData);
    }
    public static byte[] ProgramFromPubKey(byte[] publicKey) throws Exception {
        return com.github.TesraSupernet.core.program.Program.ProgramFromPubKey(publicKey);
    }
    public static byte[] ProgramFromMultiPubKey(int m, byte[]... publicKeys) throws Exception {
        return com.github.TesraSupernet.core.program.Program.ProgramFromMultiPubKey(m,publicKeys);
    }

}
