package com.github.TesraSupernet.core.payload;

import com.github.TesraSupernet.core.VmType;
import com.github.TesraSupernet.io.BinaryReader;
import com.github.TesraSupernet.io.BinaryWriter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class InvokeCodeTest {

    @Test
    public void serializeExclusiveData() throws IOException {
        InvokeCode invokeCode = new InvokeCode();
        invokeCode.code = "test".getBytes();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryWriter binaryWriter = new BinaryWriter(byteArrayOutputStream);
        invokeCode.serializeExclusiveData(binaryWriter);

        assertNotNull(byteArrayOutputStream);

        InvokeCode invokeCode1 = new InvokeCode();
        invokeCode1.deserializeExclusiveData(new BinaryReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
    }
}