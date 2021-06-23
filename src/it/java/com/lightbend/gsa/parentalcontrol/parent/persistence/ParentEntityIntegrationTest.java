package com.lightbend.gsa.parentalcontrol.parent.persistence;

import com.lightbend.gsa.parentalcontrol.parent.Main;
import com.lightbend.gsa.parentalcontrol.parent.ParentServiceClient;
import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestkitResource;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class ParentEntityIntegrationTest {
    
    /**
     * The test kit starts both the service container and the Akka Serverless proxy.
     */
    @ClassRule
    public static final AkkaServerlessTestkitResource testkit = new AkkaServerlessTestkitResource(Main.SERVICE);
    
    /**
     * Use the generated gRPC client to call the service through the Akka Serverless proxy.
     */
    private final ParentServiceClient client;
    
    public ParentEntityIntegrationTest() {
        client = ParentServiceClient.create(testkit.getGrpcClientSettings(), testkit.getActorSystem());
    }
    
    @Test
    public void createParentOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.createParent(PcParentApi.CreateParentCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void updateParentOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.updateParent(PcParentApi.UpdateParentCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void deleteParentOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.deleteParent(PcParentApi.DeleteParentCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void getParentOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.getParent(PcParentApi.GetParentCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void addChildDeviceOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.addChildDevice(PcParentApi.AddChildDeviceCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void removeChildDeviceOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.removeChildDevice(PcParentApi.RemoveChildDeviceCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
}