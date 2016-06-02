/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.openejb.config;

import org.apache.openejb.OpenEJBException;
import org.apache.openejb.assembler.classic.Assembler;
import org.apache.openejb.assembler.classic.ResourceInfo;
import org.apache.openejb.assembler.classic.SecurityServiceInfo;
import org.apache.openejb.assembler.classic.TransactionServiceInfo;
import org.apache.openejb.config.sys.JSonConfigReader;
import org.apache.openejb.config.sys.Openejb;
import org.apache.openejb.config.sys.Resource;
import org.apache.openejb.core.ivm.naming.InitContextFactory;
import org.apache.openejb.loader.Files;
import org.apache.openejb.loader.IO;
import org.apache.openejb.loader.SystemInstance;
import org.apache.openejb.loader.provisining.ProvisioningResolver;
import org.apache.openejb.util.Join;
import org.apache.openejb.util.PropertyPlaceHolderHelper;
import org.apache.openejb.util.SimpleJSonParser;
import org.apache.xbean.asm5.ClassWriter;
import org.apache.xbean.asm5.MethodVisitor;
import org.apache.xbean.asm5.Opcodes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.xbean.asm5.Opcodes.ACC_PUBLIC;
import static org.apache.xbean.asm5.Opcodes.ACC_SUPER;
import static org.apache.xbean.asm5.Opcodes.ALOAD;
import static org.apache.xbean.asm5.Opcodes.INVOKESPECIAL;
import static org.apache.xbean.asm5.Opcodes.RETURN;

/**
 * @version $Rev$ $Date$
 */
public class ServiceClasspathReadCheckerTest extends Assert {

	private File json;
	private String className;
	private File jar;
    @After
    @Before
    public void reset() {
        SystemInstance.reset();
        PropertyPlaceHolderHelper.reset();
    }

    @Before
    public void createJsonConfigFileAndInitJar() throws Exception {
    	this.className = "org.superbiz.foo.Orange";
        this.jar = ServiceClasspathTest.subclass(ServiceClasspathTest.Color.class, className);
    	this.json = File.createTempFile("config-", ".json");
        json.deleteOnExit();

        final PrintStream out = new PrintStream(IO.write(json));
        out.println("{\n" +
            "    \"resources\":{\n" +
            "        \"Orange\":{\n" +
            "            \"type\":\"org.superbiz.foo.Orange\",\n" +
            "            \"class-name\":\"org.superbiz.foo.Orange\",\n" +
            "            \"classpath\":\"" + jar.getAbsolutePath() + "\",\n" +
            "            \"properties\":{\n" +
            "                \"red\":\"FF\",\n" +
            "                \"green\":\"99\",\n" +
            "                \"blue\":\"00\n" +  // missing closing quote here
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n");
        out.close();
    }

    private void createEnvrt() {
        new File(SystemInstance.get().getBase().getDirectory(), ProvisioningResolver.cache()).mkdirs();
    }

    /**
     * Top level building the container system
     * @throws Exception
     */
    @Test(expected=OpenEJBException.class)
    public void testJson() throws Exception {
        System.setProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY, InitContextFactory.class.getName());

        final ConfigurationFactory config = new ConfigurationFactory();
        final Assembler assembler = new Assembler();

        createEnvrt();
        assembler.buildContainerSystem(config.getOpenEjbConfiguration(this.json));
    }

    /**
     * second level: JsonConfigReader parse a missing quote .json file
     * @throws Exception
     */
    @Test(expected=OpenEJBException.class)
    public void testJsonConfiger() throws Exception {
        InputStream in = IO.read(new File(this.json.getAbsolutePath()));
        JSonConfigReader.read(Openejb.class, in);
    }

    /**
     * third level: SimpleJSonParser should throw IllegalArgumentException 
     * but it stuck in infinite loop instead
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSimpleJSonParser() throws Exception {
        InputStream in = IO.read(new File(this.json.getAbsolutePath()));
        SimpleJSonParser.read(in);
    }

}
