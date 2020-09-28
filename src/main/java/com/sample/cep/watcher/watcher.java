package com.sample.cep.watcher;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;

public class watcher {

    private final EPStatement epStatement;
    private final EPRuntime runtime;

    public watcher(Class eventClass, String query) {
        // Compile
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        Configuration configuration = new Configuration();
        configuration.getCommon().addEventType(eventClass);

        CompilerArguments args = new CompilerArguments(configuration);

        EPCompiled epCompiled;
        try {
            epCompiled = compiler.compile(query, args);
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }
        // Compile  END

        // Runtime and deploy
        runtime = EPRuntimeProvider.getDefaultRuntime(configuration);
        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(epCompiled);
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }
        epStatement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "my-statement");
    }

    public void addListener(UpdateListener listener) {
        epStatement.addListener(listener);
    }
    public void sendEventBean(Object bean,String eventStreamName) {
        runtime.getEventService().sendEventBean(bean,eventStreamName);
    }
}
