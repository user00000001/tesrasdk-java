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

package com.github.TesraSupernet.core.payload;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.VmType;
import com.github.TesraSupernet.core.transaction.Attribute;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.core.transaction.TransactionType;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;

import java.io.IOException;
import java.util.Random;

public class DeployWasmCode extends Transaction {

    public byte[] code;
    public VmType vmType;
    public String name;
    public String version;
    public String author;
    public String email;
    public String description;

    public DeployWasmCode() {
        super(TransactionType.DeployCode);
        this.vmType = VmType.WASMVM;
    }

    public DeployWasmCode(String codeStr, String name, String codeVersion, String author, String email,
                          String description, Address payer, long gasLimit, long gasPrice) {
        super(TransactionType.DeployCode);
        this.vmType = VmType.WASMVM;
        this.attributes = new Attribute[0];
        this.code = Helper.hexToBytes(codeStr);
        this.name = name;
        this.version = codeVersion;
        this.author = author;
        this.email = email;
        this.nonce = new Random().nextInt();
        this.description = description;
        if (payer != null) {
            this.payer = payer;
        }
        this.gasLimit = gasLimit;
        this.gasPrice = gasPrice;
    }

    @Override
    public void deserializeExclusiveData(BinaryReader reader) throws IOException {
        try {
            code = reader.readVarBytes();
            vmType = VmType.valueOf(reader.readByte());
            name = reader.readVarString();
            version = reader.readVarString();
            author = reader.readVarString();
            email = reader.readVarString();
            description = reader.readVarString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void serializeExclusiveData(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(code);
        writer.writeByte(vmType.value());
        writer.writeVarString(name);
        writer.writeVarString(version);
        writer.writeVarString(author);
        writer.writeVarString(email);
        writer.writeVarString(description);
    }

}
