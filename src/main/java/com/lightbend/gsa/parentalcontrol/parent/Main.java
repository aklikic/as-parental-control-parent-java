package com.lightbend.gsa.parentalcontrol.parent;

import com.akkaserverless.javasdk.AkkaServerless;
import com.lightbend.gsa.parentalcontrol.childdevice.persistence.PcChilddeviceDomain;
import com.lightbend.gsa.parentalcontrol.parent.persistence.PcParentDomain;
import com.lightbend.gsa.parentalcontrol.parent.subscribe.PcChilddeviceSubscribe;
import com.lightbend.gsa.parentalcontrol.parent.view.PcParentByNameView;

public final class Main {
    
    public static void main(String[] args) throws Exception {
        new AkkaServerless()
            .registerEventSourcedEntity(
                ParentServiceImpl.class,
                PcParentApi.getDescriptor().findServiceByName("ParentService"),
                PcParentDomain.getDescriptor()
            )
            .registerView(
                ParentByNameView.class,
                PcParentByNameView.getDescriptor().findServiceByName("ParentByName"),
                "parent",
                PcParentDomain.getDescriptor(),
                PcParentByNameView.getDescriptor()
            )
            .registerAction(
                ChildDeviceSubscribeAction.class,
                PcChilddeviceSubscribe.getDescriptor().findServiceByName("ChildDeviceSubscribe"),
                PcChilddeviceDomain.getDescriptor()
            )
            .start().toCompletableFuture().get();
    }
    
}